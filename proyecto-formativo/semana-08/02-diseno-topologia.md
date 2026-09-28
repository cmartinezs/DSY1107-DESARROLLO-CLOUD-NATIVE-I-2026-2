# Paso 2 · Diseñar la topología

## Definir

- producer;
- exchange;
- routing key;
- queue;
- consumer;
- payload.

## Ejemplo de forma

```text
registrapp.exchange
  └─ reserva.creada
      └─ registrapp.notificaciones.queue
```

Los nombres reales dependen del dominio del grupo.

## Regla

La topología debe ser mínima. Semana 08 no exige DLQ, retries ni cluster.

## Checkpoint

Cada nombre debe tener significado y el estudiante debe explicar por qué existe cada componente.
