package cl.duoc.dsy1107.procesador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Component
public class KafkaPipelineListener {
    private final ObjectMapper mapper;
    private final CompraProcessor processor;
    private final FaultSimulator faultSimulator;
    private final KafkaTemplate<String, String> kafka;
    private final String outputTopic;
    private final String rejectedTopic;

    // Solo la variante de diagnostico: simula un servicio externo con capacidad limitada.
    // No modifica el procesamiento normal (ausente = 0 ms). No es un fallo Kafka.
    @Value("${APP_INCIDENT_DEPENDENCY_LATENCY_MS:0}")
    private long dependencyLatencyMs;

    public KafkaPipelineListener(
            ObjectMapper mapper,
            CompraProcessor processor,
            FaultSimulator faultSimulator,
            KafkaTemplate<String, String> kafka,
            @Value("${app.kafka.output-topic}") String outputTopic,
            @Value("${app.kafka.rejected-topic}") String rejectedTopic) {
        this.mapper = mapper;
        this.processor = processor;
        this.faultSimulator = faultSimulator;
        this.kafka = kafka;
        this.outputTopic = outputTopic;
        this.rejectedTopic = rejectedTopic;
    }

    @KafkaListener(topics = "${app.kafka.input-topic}")
    public void consumir(ConsumerRecord<String, String> record) throws Exception {
        if (record.value() == null) {
            publicarRechazo(null, "MENSAJE_SIN_VALOR");
            return;
        }

        CompraProcessor.CompraEvento evento;
        try {
            evento = mapper.readValue(record.value(), CompraProcessor.CompraEvento.class);
        } catch (JsonProcessingException ex) {
            publicarRechazo(null, "JSON_INVALIDO");
            return;
        }

        try {
            var salida = processor.procesar(evento, Instant.now());
            faultSimulator.verificar(record, evento); // Desactivado en la variante de carga.
            if (dependencyLatencyMs > 0) {
                // Espera bloqueante deliberada: simula una llamada externa lenta.
                // El listener consume secuencialmente y acumula lag ante una avalancha.
                Thread.sleep(dependencyLatencyMs);
            }
            publicar(outputTopic, record.key(), mapper.writeValueAsString(salida));
            System.out.printf("PROCESADO id=%s key=%s entrada=%s-%d@%d salida=%s%n",
                    evento.id(), record.key(), record.topic(),
                    record.partition(), record.offset(), outputTopic);
        } catch (CompraProcessor.EventoInvalidoException ex) {
            String idOrigen = evento == null ? null : evento.id();
            publicarRechazo(idOrigen, ex.getMessage());
        }
    }

    private void publicarRechazo(String idOrigen, String motivo) throws Exception {
        var rechazo = new CompraRechazadaEvento(idOrigen, motivo, Instant.now());
        publicar(rejectedTopic, idOrigen, mapper.writeValueAsString(rechazo));
        System.out.printf("RECHAZADO id=%s motivo=%s topic=%s%n", idOrigen, motivo, rejectedTopic);
    }

    private void publicar(String topic, String key, String value) throws Exception {
        kafka.send(topic, key, value).get(10, TimeUnit.SECONDS);
    }

    public record CompraRechazadaEvento(String idOrigen, String motivo, Instant recibidoEn) {}
}
