# Durabilidad y persistencia

ACK y durabilidad resuelven problemas diferentes.

| Problema | Mecanismo |
|---|---|
| Consumer cae antes de terminar | ACK/NACK |
| Broker reinicia | exchange/queue durable + mensaje persistente |
| Mensaje no puede procesarse | DLX/DLQ |
| Mensaje expira | TTL + DLX |
| Diagnóstico | DLQ + métricas/logs |

`ACK ≠ durabilidad`.

ACK gobierna el ciclo de entrega al consumer. La durabilidad gobierna qué infraestructura y mensajes sobreviven a reinicios del broker.
