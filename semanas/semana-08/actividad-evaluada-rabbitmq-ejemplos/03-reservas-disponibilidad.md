# Capacidad · Reservas y disponibilidad

## Contexto

Una aplicación permite reservar horas, mesas, habitaciones o espacios.

## Comunicación síncrona posible

```text
ms-reservas -> ms-disponibilidad
```

El sistema debe conocer inmediatamente si el recurso sigue disponible antes de confirmar la reserva.

## Comunicaciones asíncronas posibles

Evento:

```text
ReservaConfirmada
```

Consumers posibles:

- `ms-notificaciones`: envía confirmación;
- `ms-calendario`: sincroniza agenda externa;
- `ms-auditoria`: registra trazabilidad;
- `ms-fidelizacion`: registra actividad del cliente.

## Topología posible

```text
Exchange: reservas.events
Routing key: reserva.confirmada
Queues:
- calendario.reserva-confirmada
- notificaciones.reserva-confirmada
```

## Pregunta de diseño

¿La sincronización con un calendario externo debe impedir que la reserva sea confirmada?
