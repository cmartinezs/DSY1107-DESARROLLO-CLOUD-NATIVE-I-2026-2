# Work Queue vs Pub/Sub

```text
Work Queue:
jobs.queue → worker-1
          ↘ worker-2

Pub/Sub:
domain.events
├→ notifications.queue
└→ audit.queue
```

La diferencia no es cuántos consumers existen, sino la semántica de entrega.
