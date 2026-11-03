package cl.duoc.dsy1107.procesador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Configuration
public class KafkaErrorConfig {
    private static final Logger log = LoggerFactory.getLogger(KafkaErrorConfig.class);

    @Bean
    CommonErrorHandler kafkaErrorHandler(
            KafkaTemplate<String, String> kafka,
            ObjectMapper mapper,
            @Value("${app.kafka.rejected-topic}") String rejectedTopic) {
        return new DefaultErrorHandler(
                (record, exception) -> recuperar(kafka, mapper, rejectedTopic, record, exception),
                new FixedBackOff(1_000L, 2L));
    }

    private void recuperar(
            KafkaTemplate<String, String> kafka,
            ObjectMapper mapper,
            String rejectedTopic,
            ConsumerRecord<?, ?> record,
            Exception exception) {
        try {
            var rechazo = new EventoTecnicoRechazado(
                    extraerIdOrigen(mapper, record),
                    "ERROR_TECNICO_AGOTADO",
                    record.topic(),
                    record.partition(),
                    record.offset(),
                    exception.getClass().getSimpleName(),
                    exception.getMessage(),
                    Instant.now());

            String json = mapper.writeValueAsString(rechazo);
            kafka.send(rejectedTopic, rechazo.idOrigen(), json).get(10, TimeUnit.SECONDS);
            log.error("Evento enviado a {} tras agotar reintentos. Origen {}-{}@{}",
                    rejectedTopic, record.topic(), record.partition(), record.offset(), exception);
        } catch (Exception recoveryException) {
            if (recoveryException instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            log.error("No fue posible registrar el evento fallido en {}", rejectedTopic, recoveryException);
            IllegalStateException failure = new IllegalStateException(
                    "Falló la publicación del evento técnico rechazado en " + rejectedTopic,
                    recoveryException);
            failure.addSuppressed(exception);
            // No declarar recuperado el registro original si tampoco pudimos persistir el rechazo.
            throw failure;
        }
    }

    private String extraerIdOrigen(ObjectMapper mapper, ConsumerRecord<?, ?> record) {
        if (record.value() == null) {
            return null;
        }
        try {
            JsonNode root = mapper.readTree(record.value().toString());
            if (root == null || !root.isObject()) {
                return null;
            }
            JsonNode id = root.get("id");
            return id != null && id.isTextual() ? id.asText() : null;
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    private record EventoTecnicoRechazado(
            String idOrigen,
            String motivo,
            String topicOrigen,
            int particionOrigen,
            long offsetOrigen,
            String tipoError,
            String detalleError,
            Instant recibidoEn) {}
}
