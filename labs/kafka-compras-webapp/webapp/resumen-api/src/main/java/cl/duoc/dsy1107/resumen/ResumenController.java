package cl.duoc.dsy1107.resumen;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Baseline de la clase en vivo: API de resumen y calculo funcionan por HTTP.
 * Durante la clase se agrega el listener de Kafka que llama a registrar(...).
 */
@RestController
public class ResumenController {
    private final Map<String, LongAdder> montos = new ConcurrentHashMap<>();
    private final List<CompraRecibida> recibidas = new CopyOnWriteArrayList<>();
    private final java.util.Set<String> ids = ConcurrentHashMap.newKeySet();
    private final AtomicInteger cantidad = new AtomicInteger();

    public record CompraEvento(String id, String cliente, int monto, Instant fechaEvento) {}
    public record CompraRecibida(String id, String cliente, int monto, Instant fechaEvento,
                                 int particion, long offset) {}
    public record Resumen(int cantidad, Map<String, Long> montoPorCliente,
                          List<CompraRecibida> ultimosEventos) {}

    // Logica de negocio YA IMPLEMENTADA. El alumno no debe programarla de cero.
    public synchronized void registrar(CompraEvento evento, int particion, long offset) {
        if (evento == null) {
            throw new IllegalArgumentException("Compra nula");
        }
        if (evento.id() == null || evento.id().isBlank()) {
            throw new IllegalArgumentException("La compra requiere un ID");
        }
        if (evento.cliente() == null || evento.cliente().isBlank()) {
            throw new IllegalArgumentException("La compra requiere un cliente");
        }
        if (evento.monto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (!ids.add(evento.id())) {
            return; // deduplicacion en memoria, solo durante la vida del proceso
        }

        String cliente = evento.cliente().trim();
        montos.computeIfAbsent(cliente, k -> new LongAdder()).add(evento.monto());
        cantidad.incrementAndGet();
        Instant fecha = evento.fechaEvento() != null ? evento.fechaEvento() : Instant.now();
        recibidas.add(new CompraRecibida(evento.id(), cliente, evento.monto(),
                fecha, particion, offset));
        if (recibidas.size() > 30) {
            recibidas.remove(0);
        }
    }

    // Verificacion aislada PREVIA a Kafka; ruta didactica solo del starter.
    @PostMapping("/api/resumen/demostracion")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void pruebaManual(@RequestBody CompraEvento evento) {
        registrar(evento, -1, -1L);
    }

    @GetMapping("/api/resumen")
    public Resumen obtener() {
        Map<String, Long> copia = new TreeMap<>();
        montos.forEach((cliente, monto) -> copia.put(cliente, monto.sum()));
        List<CompraRecibida> ultimos = new ArrayList<>(recibidas);
        ultimos.sort(Comparator.comparing(CompraRecibida::fechaEvento).reversed());
        return new Resumen(cantidad.get(), copia, ultimos);
    }

    @GetMapping("/api/resumen/salud")
    public Map<String, String> salud() {
        return Map.of("servicio", "resumen-api", "estado", "ok");
    }
}
