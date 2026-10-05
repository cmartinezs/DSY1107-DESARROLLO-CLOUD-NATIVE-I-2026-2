# Observabilidad, alertas y evidencia

## Observar en Management UI
- Ready;
- Unacked;
- publish/deliver/ack;
- bindings;
- cantidad en DLQ;
- headers de mensajes dead-lettered cuando estén disponibles.

## Evidencia
```text
publish → processing → ACK
```

y:

```text
publish → error controlado → NACK/reject sin requeue → DLX → DLQ
```

No se exige plataforma externa de monitoreo, pero debes argumentar alertas razonables: DLQ > 0, crecimiento sostenido de Ready, consumers en 0 o aumento anormal de rechazos.

Capturas sin explicación no bastan.
