package cl.duoc.dsy1107.procesador;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaultSimulatorTest {
    private final CompraProcessor.CompraEvento eventoNormal =
            new CompraProcessor.CompraEvento("id-0", "Ana", 100, null);

    @Test
    void modoNormalNuncaInyectaFallos() {
        var simulador = new FaultSimulator(false);
        var evento = new CompraProcessor.CompraEvento("id-1", "SIM-DLT-Ana", 100, null);
        assertDoesNotThrow(() -> simulador.verificar(record(1L), evento));
    }

    @Test
    void clienteNormalNoInyectaFallos() {
        var simulador = new FaultSimulator(true);
        assertDoesNotThrow(() -> simulador.verificar(record(1L), eventoNormal));
    }

    @Test
    void fallaDosVecesYLuegoSeRecupera() {
        var simulador = new FaultSimulator(true);
        var evento = new CompraProcessor.CompraEvento("id-2", "SIM-RETRY-Ana", 100, null);
        var record = record(2L);
        assertThrows(FaultSimulator.FalloTransitorioSimulado.class, () -> simulador.verificar(record, evento));
        assertThrows(FaultSimulator.FalloTransitorioSimulado.class, () -> simulador.verificar(record, evento));
        assertDoesNotThrow(() -> simulador.verificar(record, evento));
    }

    @Test
    void fallaSiempreYNoSeRecupera() {
        var simulador = new FaultSimulator(true);
        var evento = new CompraProcessor.CompraEvento("id-3", "SIM-DLT-Ana", 100, null);
        var record = record(3L);
        for (int i=0; i<3; i++)
            assertThrows(FaultSimulator.FalloPermanenteSimulado.class, () -> simulador.verificar(record, evento));
    }

    private ConsumerRecord<String,String> record(long offset) {
        return new ConsumerRecord<>("compras", 0, offset, "key", "{}");
    }
}
