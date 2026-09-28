# Etapa 3 · DirectExchange, bindings y routing keys

## Topología

Usar:

```text
exchange: pedidos.exchange

routing keys:
- pedido.creado
- pedido.cancelado

queues:
- pedidos.notificaciones.queue
- pedidos.auditoria.queue
```

## Requisito

Configurar bindings de forma que:

- notificaciones reciba `pedido.creado`;
- auditoría reciba `pedido.creado` y `pedido.cancelado`.

## Checkpoint

Desde Management UI debe poder demostrarse la relación exchange → binding → queue.
