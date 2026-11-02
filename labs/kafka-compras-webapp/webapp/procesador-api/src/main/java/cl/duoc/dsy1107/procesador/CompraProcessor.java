package cl.duoc.dsy1107.procesador;

import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class CompraProcessor {
    public CompraProcesadaEvento procesar(CompraEvento evento, Instant procesadoEn) {
        validar(evento);
        String categoria = evento.monto() >= 1000 ? "ALTA" : "NORMAL";
        return new CompraProcesadaEvento(evento.id(), evento.cliente().trim(), evento.monto(), categoria, procesadoEn);
    }

    public void validar(CompraEvento evento) {
        if (evento == null) throw new EventoInvalidoException("EVENTO_NULO");
        if (evento.id() == null || evento.id().isBlank()) throw new EventoInvalidoException("ID_REQUERIDO");
        if (evento.cliente() == null || evento.cliente().isBlank()) throw new EventoInvalidoException("CLIENTE_REQUERIDO");
        if (evento.monto() <= 0) throw new EventoInvalidoException("MONTO_DEBE_SER_POSITIVO");
    }

    public record CompraEvento(String id, String cliente, int monto, Instant fechaEvento) {}
    public record CompraProcesadaEvento(String idOrigen, String cliente, int monto, String categoria, Instant procesadoEn) {}

    public static class EventoInvalidoException extends RuntimeException {
        public EventoInvalidoException(String message) { super(message); }
    }
}
