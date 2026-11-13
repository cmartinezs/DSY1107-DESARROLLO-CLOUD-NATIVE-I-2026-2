package cl.duoc.dsy1107.procesador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Configuration
public class KafkaErrorConfig {
    private static final Logger log = LoggerFactory.getLogger(KafkaErrorConfig.class);

    @Bean
    CommonErrorHandler kafkaErrorHandler(
            KafkaTemplate<String, String> kafka,
            ObjectMapper mapper,
            @Value("${app.kafka.rejected-topic}") String rejectedTopic,
            @Value("${app.kafka.dlt-topic:}") String dltTopic) {
        return new DefaultErrorHandler(
                (record, exception) -> recuperar(kafka, mapper, rejectedTopic, dltTopic, record, exception),
                new FixedBackOff(1_000L, 2L)); // 1 intento original + 2 reintentos
    }

    private void recuperar(
            KafkaTemplate<String, String> kafka,
            ObjectMapper mapper,
            String rejectedTopic,
            String dltTopic,
            ConsumerRecord<?, ?> record,
            Exception exception) {
        boolean gameDayDlt = dltTopic != null && !dltTopic.isBlank();
        String destino = gameDayDlt ? dltTopic : rejectedTopic;
        try {
            if (gameDayDlt) {
                // Conserva payload original y key; metadatos de trazabilidad en headers.
                ProducerRecord<String, String> carta = new ProducerRecord<>(
                        destino,
                        record.key() == null ? null : record.key().toString(),
                        record.value() == null ? null : record.value().toString());
                header(carta, "x-original-topic", record.topic());
                header(carta, "x-original-partition", String.valueOf(record.partition()));
                header(carta, "x-original-offset", String.valueOf(record.offset()));
                header(carta, "x-error-type", exception.getClass().getSimpleName());
                kafka.send(carta).get(10, TimeUnit.SECONDS);
            } else {
                // Ruta didactica preexistente de Semana 13, intacta fuera del Game Day.
                var rechazo = new EventoTecnicoRechazado(
                        extraerIdOrigen(mapper, record), "ERROR_TECNICO_AGOTADO",
                        record.topic(), record.partition(), record.offset(),
                        exception.getClass().getSimpleName(), exception.getMessage(), Instant.now());
                kafka.send(destino, rechazo.idOrigen(), mapper.writeValueAsString(rechazo))
                        .get(10, TimeUnit.SECONDS);
            }
            log.error("RECUPERADO destino={} origen={}-{}@{}",
                    destino, record.topic(), record.partition(), record.offset(), exception);
        } catch (Exception recoveryException) {
            if (recoveryException instanceof InterruptedException) Thread.currentThread().interrupt();
            log.error("No fue posible publicar el evento fallido en {}", destino, recoveryException);
            IllegalStateException failure = new IllegalStateException(
                    "Fallo la recuperacion del evento en " + destino, recoveryException);
            failure.addSuppressed(exception);
            throw failure; // Nunca aceptar como recuperado si el destino falla.
        }
    }

    private void header(ProducerRecord<String, String> record, String nombre, String valor) {
        record.headers().add(nombre, valor.getBytes(StandardCharsets.UTF_8));
    }

    private String extraerIdOrigen(ObjectMapper mapper, ConsumerRecord<?, ?> record) {
        if (record.value() == null) return null;
        try {
            JsonNode root = mapper.readTree(record.value().toString());
            if (root == null || !root.isObject()) return null;
            JsonNode id = root.get("id");
            return id != null && id.isTextual() ? id.asText() : null;
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    private record EventoTecnicoRechazado(
            String idOrigen, String motivo, String topicOrigen, int particionOrigen,
            long offsetOrigen, String tipoError, String detalleError, Instant recibidoEn) {}
}
