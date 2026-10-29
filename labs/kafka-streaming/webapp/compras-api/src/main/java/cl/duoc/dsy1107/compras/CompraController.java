package cl.duoc.dsy1107.compras;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class CompraController {
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper mapper;
    private final String topic;

    public CompraController(KafkaTemplate<String, String> kafka, ObjectMapper mapper,
                            @Value("${app.kafka.topic}") String topic) {
        this.kafka = kafka;
        this.mapper = mapper;
        this.topic = topic;
    }

    public record NuevaCompra(@NotBlank String cliente, @Positive int monto) {}
    public record CompraEvento(String id, String cliente, int monto, Instant fechaEvento) {}

    @GetMapping("/api/compras/salud")
    public Map<String, String> salud() {
        return Map.of("servicio", "compras-api", "estado", "ok");
    }

    @PostMapping("/api/compras")
    @ResponseStatus(HttpStatus.CREATED)
    public CompraEvento crear(@Valid @RequestBody NuevaCompra entrada) {
        CompraEvento evento = new CompraEvento(UUID.randomUUID().toString(),
                entrada.cliente().trim(), entrada.monto(), Instant.now());
        try {
            // Clave = cliente: los eventos de un mismo cliente comparten partición.
            String json = mapper.writeValueAsString(evento);
            kafka.send(topic, evento.cliente(), json).get(10, TimeUnit.SECONDS);
            return evento;
        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se pudo serializar el evento", ex);
        } catch (Exception ex) {
            Thread.currentThread().interrupt(); // se preserva señal en caso de interrupción
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "No fue posible publicar en Kafka", ex);
        }
    }
}
