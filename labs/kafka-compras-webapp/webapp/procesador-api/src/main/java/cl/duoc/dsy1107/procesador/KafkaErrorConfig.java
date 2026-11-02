package cl.duoc.dsy1107.procesador;

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
import java.util.Map;
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
            String json = mapper.writeValueAsString(Map.of(
                    "idOrigen", "",
                    "motivo", "ERROR_TECNICO_AGOTADO",
                    "recibidoEn", Instant.now().toString(),
                    "origen", record.topic() + "-" + record.partition() + "@" + record.offset()
            ));
            kafka.send(rejectedTopic, record.key() == null ? null : record.key().toString(), json)
                    .get(10, TimeUnit.SECONDS);
            log.error("Evento enviado a {} tras agotar reintentos: {}", rejectedTopic, exception.getMessage());
        } catch (Exception recoveryException) {
            log.error("No fue posible registrar el evento fallido en {}", rejectedTopic, recoveryException);
        }
    }
}