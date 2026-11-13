package cl.duoc.dsy1107.procesador;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Inyeccion deliberada, habilitada SOLO en Game Day.
 * No depende de sleeps ni de aleatoriedad: cada evento tiene un resultado repetible.
 */
@Component
public class FaultSimulator {
    private static final Logger log = LoggerFactory.getLogger(FaultSimulator.class);
    private final boolean enabled;
    private final ConcurrentHashMap<String, AtomicInteger> attempts = new ConcurrentHashMap<>();

    public FaultSimulator(@Value("${app.chaos.enabled:false}") boolean enabled) {
        this.enabled = enabled;
    }

    public void verificar(ConsumerRecord<String, String> record, CompraProcessor.CompraEvento compra) {
        if (!enabled || compra == null || compra.cliente() == null) return;
        String cliente = compra.cliente();
        String eventKey = record.topic() + ":" + record.partition() + ":" + record.offset();

        if (cliente.startsWith("SIM-DLT-")) {
            log.warn("SIMULACION FALLO PERMANENTE origen={} cliente={}", eventKey, cliente);
            throw new FalloPermanenteSimulado("DEPENDENCIA_PERMANENTEMENTE_INDISPONIBLE");
        }
        if (cliente.startsWith("SIM-RETRY-")) {
            int intento = attempts.computeIfAbsent(eventKey, ignored -> new AtomicInteger()).incrementAndGet();
            if (intento <= 2) {
                log.warn("SIMULACION FALLO TRANSITORIO origen={} intento={}/3", eventKey, intento);
                throw new FalloTransitorioSimulado("SERVICIO_TEMPORALMENTE_INDISPONIBLE");
            }
            attempts.remove(eventKey);
            log.info("SIMULACION RECUPERADA origen={} intento=3/3", eventKey);
        }
    }

    public static class FalloTransitorioSimulado extends RuntimeException {
        public FalloTransitorioSimulado(String mensaje) { super(mensaje); }
    }

    public static class FalloPermanenteSimulado extends RuntimeException {
        public FalloPermanenteSimulado(String mensaje) { super(mensaje); }
    }
}
