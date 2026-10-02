# Capacidad · Soporte y mesa de ayuda

## Contexto

Un cliente crea un ticket de soporte.

## Comunicación síncrona posible

```text
ms-tickets -> ms-clientes
```

El sistema puede necesitar validar que el cliente exista o que tenga un contrato activo.

## Comunicaciones asíncronas posibles

Evento:

```text
TicketCreado
```

Consumers posibles:

- `ms-notificaciones`: confirma recepción;
- `ms-clasificacion`: asigna categoría o prioridad;
- `ms-metricas`: actualiza indicadores;
- `ms-asignacion`: busca un equipo responsable.

## Pregunta de diseño

¿El usuario necesita esperar la clasificación completa del ticket para recibir su número de atención?
