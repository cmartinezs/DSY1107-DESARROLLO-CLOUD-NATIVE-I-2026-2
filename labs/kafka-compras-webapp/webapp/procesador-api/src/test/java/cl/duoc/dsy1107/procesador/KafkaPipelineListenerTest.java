package cl.duoc.dsy1107.procesador;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaPipelineListenerTest {
    @Mock KafkaTemplate<String, String> kafka;
    private KafkaPipelineListener listener;

    @BeforeEach
    void setUp() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        listener = new KafkaPipelineListener(mapper, new CompraProcessor(), kafka,
                "compras-validas", "compras-rechazadas");
        when(kafka.send(anyString(), nullable(String.class), anyString()))
                .thenReturn(CompletableFuture.completedFuture(null));
    }

    @Test
    void publicaEventoValidoTransformado() throws Exception {
        var record = new ConsumerRecord<String, String>("compras", 0, 12L, "Ana",
                "{\"id\":\"c-1\",\"cliente\":\"Ana\",\"monto\":1500,\"fechaEvento\":null}");

        listener.consumir(record);

        ArgumentCaptor<String> payload = ArgumentCaptor.forClass(String.class);
        verify(kafka).send(eq("compras-validas"), eq("Ana"), payload.capture());
        assertTrue(payload.getValue().contains("\"idOrigen\":\"c-1\""));
        assertTrue(payload.getValue().contains("\"categoria\":\"ALTA\""));
    }

    @Test
    void publicaRechazoPorReglaDeNegocio() throws Exception {
        var record = new ConsumerRecord<String, String>("compras", 1, 3L, "x",
                "{\"id\":\"c-2\",\"cliente\":\"\",\"monto\":10,\"fechaEvento\":null}");

        listener.consumir(record);

        ArgumentCaptor<String> payload = ArgumentCaptor.forClass(String.class);
        verify(kafka).send(eq("compras-rechazadas"), eq("x"), payload.capture());
        assertTrue(payload.getValue().contains("CLIENTE_REQUERIDO"));
        verify(kafka, never()).send(eq("compras-validas"), any(), anyString());
    }

    @Test
    void publicaRechazoPorJsonMalformado() throws Exception {
        var record = new ConsumerRecord<String, String>("compras", 2, 8L, null, "{mal-json");

        listener.consumir(record);

        ArgumentCaptor<String> payload = ArgumentCaptor.forClass(String.class);
        verify(kafka).send(eq("compras-rechazadas"), isNull(), payload.capture());
        assertTrue(payload.getValue().contains("JSON_INVALIDO"));
    }
}