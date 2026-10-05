# 10 · Dead Letter Exchange y Dead Letter Queue

```text
lab.main.exchange
      ↓
lab.main.queue
      ↓ reject/NACK sin requeue
lab.dlx
      ↓
lab.dlq
```

Configura la queue principal con su DLX, provoca un error conocido y rechaza sin requeue.

Checkpoint: mensaje fuera de la queue principal, presente en DLQ, ruta explicable y sin loop infinito.
