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
    private final KafkaTemplate<String, String> kafka;
    private final String outputTopic;
    private final String rejectedTopic;

    public KafkaPipelineListener(
            ObjectMapper mapper,
            CompraProcessor processor,
            KafkaTemplate<String, String> kafka,
            @Value("${app.kafka.output-topic}") String outputTopic,
            @Value("${app.kafka.rejected-topic}") String rejectedTopic) {
        this.mapper = mapper;
        this.processor = processor;
        this.kafka = kafka;
        this.outputTopic = outputTopic;
        this.rejectedTopic = rejectedTopic;
    }

    @KafkaListener(topics = "${app.kafka.input-topic}")
    public void consumir(ConsumerRecord<String, String> record) throws Exception {
        CompraProcessor.CompraEvento evento;
        try {
            evento = mapper.readValue(record.value(), CompraProcessor.CompraEvento.class);
        } catch (JsonProcessingException ex) {
            publicarRechazo(record.key(), null, "JSON_INVALIDO");
            return;
        }

        try {
            var salida = processor.procesar(evento, Instant.now());
            publicar(outputTopic, record.key(), mapper.writeValueAsString(salida));
            System.out.printf("PROCESADO id=%s key=%s entrada=%s-%d@%d salida=%s%n",
                    evento.id(), record.key(), record.topic(),
                    record.partition(), record.offset(), outputTopic);
        } catch (CompraProcessor.EventoInvalidoException ex) {
            publicarRechazo(record.key(), evento.id(), ex.getMessage());
        }
    }

    private void publicarRechazo(String key, String idOrigen, String motivo) throws Exception {
        var rechazo = new CompraRechazadaEvento(idOrigen, motivo, Instant.now());
        publicar(rejectedTopic, key, mapper.writeValueAsString(rechazo));
        System.out.printf("RECHAZADO id=%s motivo=%s topic=%s%n", idOrigen, motivo, rejectedTopic);
    }

    private void publicar(String topic, String key, String value) throws Exception {
        kafka.send(topic, key, value).get(10, TimeUnit.SECONDS);
    }

    public record CompraRechazadaEvento(String idOrigen, String motivo, Instant recibidoEn) {}
}