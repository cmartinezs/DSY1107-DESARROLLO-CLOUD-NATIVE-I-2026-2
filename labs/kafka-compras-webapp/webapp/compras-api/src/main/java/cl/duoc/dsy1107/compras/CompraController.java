package cl.duoc.dsy1107.compras;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Baseline de la clase en vivo: API HTTP funcional, todavia sin productor Kafka.
 * Durante la clase se agregan KafkaTemplate y serializacion JSON.
 */
@RestController
public class CompraController {
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
        // Checkpoint 0: la API recibe la compra y devuelve 201, pero todavia NO
        // publica eventos ni informa al microservicio de resumen.
        System.out.printf("HTTP RECIBIDO id=%s cliente=%s monto=%d (sin Kafka)%n",
                evento.id(), evento.cliente(), evento.monto());
        return evento;
    }
}
