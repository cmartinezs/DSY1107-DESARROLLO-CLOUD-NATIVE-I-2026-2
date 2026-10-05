# DLX, DLQ y políticas de retención

Un Dead Letter Exchange recibe mensajes desviados por causas como reject/NACK sin requeue, TTL expirado o límites configurados.

```text
main.exchange
      ↓
  main.queue
      ↓ fallo definitivo
dead-letter.exchange
      ↓
dead-letter.queue
```

La DLQ no “arregla” el mensaje. Aísla fallos, evita pérdida silenciosa y permite diagnóstico.

Cuando sea posible, preferir políticas de RabbitMQ para aspectos operacionales que puedan cambiar sin recompilar.

Fuera de alcance: retry exponencial, parking lot avanzado, clustering e idempotencia distribuida.
