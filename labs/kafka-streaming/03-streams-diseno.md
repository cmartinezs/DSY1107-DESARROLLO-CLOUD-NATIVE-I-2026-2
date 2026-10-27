# Laboratorio 03 · Diseñar Kafka Streams

**Tipo:** diseño y pseudocódigo, no aplicación Java runnable.

## Caso
Cada compra aporta `clienteId`, `monto` y `fechaEvento`. Procesar montos positivos, contar compras y acumular montos por cliente en ventanas tumbling de un minuto.

## A · Topología
Dibujar `stream("compras") → filter(monto>0) → selectKey(clienteId) → groupByKey → windowedBy(1m) → aggregate → topic("compras-resumen")`.

Marcar qué operaciones son **stateless** y cuáles **stateful**. Indicar state store y posibles changelog/repartition topics. Si el productor ya usa clienteId como key, discutir si hace falta `selectKey`.

## B · Cálculo
| Hora | Cliente | Monto |
|---|---|---:|
| 10:00:05 | A | 100 |
| 10:00:15 | B | 50 |
| 10:00:45 | A | 200 |
| 10:01:05 | B | 75 |
| 10:01:15 | A | -20 |

**Resultado esperado:** [10:00,10:01) A=300 y B=50; [10:01,10:02) B=75. Monto negativo se descarta.

## C · Discusión
- ¿Cómo cambian los resultados con eventos tardíos, timestamps y grace?
- ¿Cómo recupera un state store ante el reinicio de la aplicación?
- ¿La garantía exactly-once de Streams equivale a exactamente un cobro por una API externa? **No.**

## Entrega
Diagrama Mermaid o croquis, tabla de resultados y respuestas justificadas. Este laboratorio no demuestra una ejecución Kafka Streams.
