# Revisión técnica de materiales 3.1.1 y 3.1.2

**Fuente institucional (no reescrita):**
- 3.1.1 *Streaming vs Colas; arquitectura de Kafka*, 21 páginas: streaming, colas, brokers, topics, particiones, offsets, grupos y flujo de datos.
- 3.1.2 *Particiones, consumer groups y procesamiento en tiempo real*, 20 páginas: Kafka Streams, operaciones sin/con estado, windowing, state stores.

## Aclaraciones al enseñar
1. **3.1.1, páginas 6–8:** una cola RabbitMQ no garantiza universalmente exactly-once; consumo/ACK/reintentos pueden producir duplicados. Tampoco es correcto asumir siempre FIFO global observable con múltiples consumidores.
2. **3.1.1, página 8:** la retención/replay de Kafka no equivale a replicación entre brokers, que es otro mecanismo.
3. **3.1.1, páginas 12–14:** offset identifica posición dentro de una partición; distintos consumer groups pueden consumir los mismos registros de forma independiente.
4. **3.1.2, página 4 y 16:** exactly-once en Kafka Streams tiene configuración, límites y semántica transaccional concretos; no cubre automáticamente servicios externos.
5. **3.1.2, página 8:** la coordinación de grupos es administrada por el protocolo/brokers, no por asumir que la primera instancia de la app es coordinadora del grupo.
6. **3.1.2, página 14:** algunos fragmentos usan API antigua (`TimeWindows.of`); para código productivo verificar API de la versión elegida, por ejemplo `TimeWindows.ofSizeWithNoGrace` o `ofSizeAndGrace`.
7. **3.1.2, páginas 10–11:** varios snippets son ilustrativos; no publicarlos como ejemplos Java probados.

Estas observaciones complementan el material oficial sin reemplazarlo. El curso conserva los PDF en su biblioteca institucional.
