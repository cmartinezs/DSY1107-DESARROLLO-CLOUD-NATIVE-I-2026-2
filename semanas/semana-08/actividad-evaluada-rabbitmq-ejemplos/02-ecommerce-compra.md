# Capacidad · Comercio electrónico y compra

## Contexto

Un cliente confirma una compra en una tienda online.

## Comunicación síncrona posible

```text
ms-ordenes -> ms-stock
```

Antes de confirmar la compra, el sistema necesita saber si existe stock disponible.

También podría existir una validación síncrona con un servicio de pagos cuando el resultado sea necesario para aprobar la orden.

## Comunicaciones asíncronas posibles

Evento:

```text
OrdenConfirmada
```

Consumers posibles:

- `ms-notificaciones`: envía comprobante;
- `ms-despacho`: prepara la orden;
- `ms-analytics`: actualiza métricas de venta;
- `ms-fidelizacion`: acumula puntos.

## Topología posible

```text
Exchange: ordenes.events
Routing key: orden.confirmada
Queues:
- despacho.orden-confirmada
- notificaciones.orden-confirmada
```

## Payload mínimo

```json
{
  "ordenId": 8801,
  "clienteId": 42,
  "total": 45990,
  "fecha": "2026-10-02T12:45:00"
}
```

## Pregunta de diseño

¿Qué necesita estar confirmado antes de responder al cliente y qué tareas pueden comenzar después de creada la orden?
