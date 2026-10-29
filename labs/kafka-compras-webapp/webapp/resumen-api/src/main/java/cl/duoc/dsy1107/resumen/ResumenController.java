package cl.duoc.dsy1107.resumen;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@RestController
public class ResumenController {
    private final ObjectMapper mapper;
    private final Map<String, LongAdder> montos = new ConcurrentHashMap<>();
    private final List<CompraRecibida> recibidas = new CopyOnWriteArrayList<>();
    private final java.util.Set<String> ids = ConcurrentHashMap.newKeySet();
    private final AtomicInteger cantidad = new AtomicInteger();

    public ResumenController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public record CompraEvento(String id, String cliente, int monto, Instant fechaEvento) {}
    public record CompraRecibida(String id, String cliente, int monto, Instant fechaEvento,
                                 int particion, long offset) {}
    public record Resumen(int cantidad, Map<String, Long> montoPorCliente,
                          List<CompraRecibida> ultimosEventos) {}

    @KafkaListener(topics = "${app.kafka.topic}")
    public synchronized void consumir(ConsumerRecord<String, String> record) throws Exception {
        CompraEvento evento = mapper.readValue(record.value(), CompraEvento.class);
        // Idempotencia demostrativa por ID, solo dentro de la vida de este proceso.
        if (!ids.add(evento.id())) {
            return;
        }
        montos.computeIfAbsent(evento.cliente(), k -> new LongAdder()).add(evento.monto());
        cantidad.incrementAndGet();
        // Compatible con los eventos de los laboratorios 01/02 (sin fechaEvento).
        Instant fecha = evento.fechaEvento() != null
                ? evento.fechaEvento() : Instant.ofEpochMilli(record.timestamp());
        recibidas.add(new CompraRecibida(evento.id(), evento.cliente(), evento.monto(),
                fecha, record.partition(), record.offset()));
        if (recibidas.size() > 30) {
            recibidas.remove(0);
        }
        System.out.printf("CONSUMIDO cliente=%s particion=%d offset=%d%n",
                evento.cliente(), record.partition(), record.offset());
    }

    @GetMapping("/api/resumen")
    public Resumen obtener() {
        Map<String, Long> copia = new TreeMap<>();
        montos.forEach((cliente, monto) -> copia.put(cliente, monto.sum()));
        List<CompraRecibida> ultimos = new ArrayList<>(recibidas);
        ultimos.sort(Comparator.comparing(CompraRecibida::fechaEvento).reversed());
        return new Resumen(cantidad.get(), copia, ultimos);
    }

    @GetMapping("/api/resumen/salud")
    public Map<String, String> salud() {
        return Map.of("servicio", "resumen-api", "estado", "ok");
    }
}
