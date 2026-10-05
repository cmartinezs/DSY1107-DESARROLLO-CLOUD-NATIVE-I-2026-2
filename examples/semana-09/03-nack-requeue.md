# NACK y requeue

Error transitorio:
```text
servicio temporalmente no disponible → NACK + requeue
```

Error permanente:
```text
payload inválido definitivo → NACK/reject sin requeue → DLX
```

Requeue sin criterio puede convertirse en loop infinito.
