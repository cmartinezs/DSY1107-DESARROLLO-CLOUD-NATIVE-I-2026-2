# Dead Letter Queue

```text
orders.exchange → orders.queue
                     ↓ fallo definitivo
                  orders.dlx → orders.dlq
```

La DLQ es aislamiento y diagnóstico; no implica reparación ni reproceso automático.
