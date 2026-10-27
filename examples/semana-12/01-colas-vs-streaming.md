# Ejemplo 01 · ¿Cola o streaming?

**Caso A:** se debe ejecutar una única tarea de generación de PDF en un conjunto de workers. Una cola de trabajo con ACK, reintentos e idempotencia es una opción natural.

**Caso B:** un evento de pago debe alimentar auditoría, recomendaciones y métricas independientemente, y debe poder reprocesarse después de corregir un bug. Un topic de Kafka retenido con grupos de consumidores diferentes es una opción natural.

| Pregunta | Cola de trabajo | Log Kafka |
|---|---|---|
| ¿Trabajo entre workers? | Consumidores competidores | Miembros del mismo grupo |
| ¿Varios procesadores independientes? | Routing/fanout con colas separadas | Grupos de consumidores diferentes |
| ¿Releer historia? | Necesita mecanismo adicional | Offsets + retención |
| ¿Entrega exactamente una vez universal? | No | No; depende de alcance/configuración |

**Debate:** ¿en qué escenario la complejidad de Kafka no se justifica?
