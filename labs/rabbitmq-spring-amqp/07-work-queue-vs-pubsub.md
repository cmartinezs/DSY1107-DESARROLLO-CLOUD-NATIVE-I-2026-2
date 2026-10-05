# 07 · Work Queue vs Publish/Subscribe

## Objetivo
Observar la diferencia entre reparto de trabajo y suscripción múltiple.

### A · Work Queue
Dos consumers sobre una misma queue. Publica varios mensajes y observa el reparto.

### B · Publish/Subscribe
```text
evento.reserva.creada
├→ reserva.notificaciones
└→ reserva.auditoria
```

Cada queue debe recibir una copia. Explica por qué A no es Pub/Sub y B sí.
