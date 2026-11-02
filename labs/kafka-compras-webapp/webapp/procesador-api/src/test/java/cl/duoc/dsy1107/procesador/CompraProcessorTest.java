package cl.duoc.dsy1107.procesador;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class CompraProcessorTest {
    private final CompraProcessor processor = new CompraProcessor();
    private final Instant now = Instant.parse("2026-11-02T12:00:00Z");

    @Test void clasificaMontoAlto() {
        var salida = processor.procesar(new CompraProcessor.CompraEvento("c-1", "Ana", 1500, null), now);
        assertEquals("ALTA", salida.categoria()); assertEquals("c-1", salida.idOrigen()); assertEquals(now, salida.procesadoEn());
    }
    @Test void clasificaMontoNormalYRecortaCliente() {
        var salida = processor.procesar(new CompraProcessor.CompraEvento("c-2", "  Luis  ", 250, null), now);
        assertEquals("Luis", salida.cliente()); assertEquals("NORMAL", salida.categoria());
    }
    @Test void rechazaClienteVacio() {
        var e = assertThrows(CompraProcessor.EventoInvalidoException.class,
            () -> processor.procesar(new CompraProcessor.CompraEvento("c-3", " ", 100, null), now));
        assertEquals("CLIENTE_REQUERIDO", e.getMessage());
    }
    @Test void rechazaMontoNoPositivo() {
        var e = assertThrows(CompraProcessor.EventoInvalidoException.class,
            () -> processor.procesar(new CompraProcessor.CompraEvento("c-4", "Ana", 0, null), now));
        assertEquals("MONTO_DEBE_SER_POSITIVO", e.getMessage());
    }
    @Test void rechazaIdAusente() {
        var e = assertThrows(CompraProcessor.EventoInvalidoException.class,
            () -> processor.procesar(new CompraProcessor.CompraEvento(null, "Ana", 100, null), now));
        assertEquals("ID_REQUERIDO", e.getMessage());
    }
}
