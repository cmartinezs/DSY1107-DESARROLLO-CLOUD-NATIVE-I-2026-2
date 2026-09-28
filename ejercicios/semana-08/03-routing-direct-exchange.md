# Ejercicio 3 · Routing con DirectExchange

Un sistema publica:

- `pedido.creado`;
- `pedido.cancelado`;
- `pedido.pagado`.

Existen dos queues:

- `notificaciones.queue`;
- `auditoria.queue`.

Diseña los bindings para lograr:

- notificaciones recibe creado y pagado;
- auditoría recibe los tres eventos.

Entrega una tabla:

| Exchange | Routing key | Queue |
|---|---|---|

Después explica por qué no necesitas tres consumers distintos en auditoría si una sola capacidad procesa los tres tipos.
