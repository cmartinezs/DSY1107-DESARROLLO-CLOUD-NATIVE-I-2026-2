import React, { useEffect, useState } from "react";
import { createRoot } from "react-dom/client";
import "./style.css";

const formato = new Intl.NumberFormat("es-CL");

function App() {
  const [cliente, setCliente] = useState("Ana");
  const [monto, setMonto] = useState("100");
  const [datos, setDatos] = useState({ cantidad: 0, montoPorCliente: {}, ultimosEventos: [] });
  const [conectado, setConectado] = useState(false);
  const [mensaje, setMensaje] = useState("");
  const [enviando, setEnviando] = useState(false);

  async function actualizar() {
    try {
      const respuesta = await fetch("/api/resumen", { cache: "no-store" });
      if (!respuesta.ok) throw new Error("Resumen no disponible");
      setDatos(await respuesta.json());
      setConectado(true);
    } catch {
      setConectado(false);
    }
  }

  useEffect(() => {
    actualizar();
    const intervalo = setInterval(actualizar, 2000);
    return () => clearInterval(intervalo);
  }, []);

  async function enviar(event) {
    event.preventDefault();
    setEnviando(true);
    setMensaje("");
    try {
      const respuesta = await fetch("/api/compras", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ cliente: cliente.trim(), monto: Number(monto) }),
      });
      if (!respuesta.ok) throw new Error("No se pudo publicar el evento. Revisa compras-api y Kafka.");
      const creada = await respuesta.json();
      setMensaje("Compra " + creada.id.slice(0, 8) + " publicada en Kafka. El resumen aparecerá en unos segundos.");
      await actualizar();
    } catch (error) {
      setMensaje(error.message);
    } finally {
      setEnviando(false);
    }
  }

  return (
    <main className="container">
      <header>
        <div className="eyebrow">DSY1107 · SEMANA 12 · DEMOSTRACIÓN LOCAL</div>
        <h1>Compras en tiempo real</h1>
        <p>React → Spring Boot → Kafka → Spring Boot. Envía compras y observa cómo llegan a un segundo microservicio.</p>
        <div className={"status " + (conectado ? "ok" : "off")} role="status">
          <span className="dot" />
          {conectado ? "Resumen conectado" : "Esperando al servicio de resumen"}
        </div>
      </header>

      <div className="layout">
        <section className="panel">
          <h2>1. Publicar una compra</h2>
          <p className="muted">La API publica un evento JSON en el topic <code>compras</code>; el cliente es la clave del mensaje.</p>
          <form onSubmit={enviar}>
            <label htmlFor="cliente">Cliente (key)</label>
            <select id="cliente" value={cliente} onChange={e => setCliente(e.target.value)}>
              <option>Ana</option>
              <option>Luis</option>
              <option>Sofía</option>
              <option>Pedro</option>
            </select>
            <label htmlFor="monto">Monto ($)</label>
            <input id="monto" type="number" min="1" max="1000000" required
                   value={monto} onChange={e => setMonto(e.target.value)} />
            <button type="submit" disabled={enviando || !cliente || Number(monto) <= 0}>
              {enviando ? "Publicando…" : "Enviar evento a Kafka"}
            </button>
          </form>
          {mensaje && <p className="notice" role="status">{mensaje}</p>}
          <div className="hint"><strong>Experimento:</strong> envía dos compras de Ana y dos de Luis. Compara las particiones y offsets en la tabla.</div>
        </section>

        <section className="panel">
          <h2>2. Resumen del consumidor</h2>
          <div className="counter"><strong>{datos.cantidad}</strong><span>compras procesadas</span></div>
          <h3>Total por cliente</h3>
          {Object.keys(datos.montoPorCliente || {}).length === 0
            ? <p className="muted">Todavía no se han procesado compras.</p>
            : <ul className="totals">{Object.entries(datos.montoPorCliente).map(([nombre, total]) =>
                <li key={nombre}><span>{nombre}</span><b>$ {formato.format(total)}</b></li>
              )}</ul>}
          <p className="muted small">El resumen está en memoria: se reconstruye desde Kafka al reiniciar y releer eventos.</p>
        </section>
      </div>

      <section className="panel events">
        <h2>3. Eventos consumidos: partición y offset</h2>
        <p className="muted">El mismo cliente normalmente conserva la partición mientras no cambie el número de particiones.</p>
        <div className="table-wrap">
          <table>
            <thead><tr><th>Cliente</th><th>Monto</th><th>Partición</th><th>Offset</th><th>ID</th></tr></thead>
            <tbody>
              {datos.ultimosEventos.length === 0
                ? <tr><td colSpan="5" className="muted">No hay eventos todavía.</td></tr>
                : datos.ultimosEventos.map(ev =>
                  <tr key={ev.id}>
                    <td>{ev.cliente}</td>
                    <td>$ {formato.format(ev.monto)}</td>
                    <td><span className="pill">P{ev.particion}</span></td>
                    <td>{ev.offset}</td>
                    <td><code>{ev.id.slice(0, 8)}</code></td>
                  </tr>
                )}
            </tbody>
          </table>
        </div>
      </section>
      <footer>Laboratorio académico · Dos microservicios Spring Boot · Un broker Kafka local · Sin base de datos</footer>
    </main>
  );
}

createRoot(document.getElementById("root")).render(<App />);
