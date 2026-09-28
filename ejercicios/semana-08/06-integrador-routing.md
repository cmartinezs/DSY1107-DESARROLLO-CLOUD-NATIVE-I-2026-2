# Ejercicio · Enrutamiento con DirectExchange

## Escenario

Una plataforma registra pedidos.

- Al crear un pedido se debe notificar al cliente y registrar auditoría.
- Al cancelar un pedido se debe registrar auditoría.
- La notificación de creación no debe ejecutarse al cancelar.

## Requisitos

1. Crear un `DirectExchange`.
2. Definir al menos dos queues.
3. Utilizar `pedido.creado` y `pedido.cancelado` como routing keys.
4. Configurar bindings coherentes.
5. Implementar un producer.
6. Implementar consumers independientes.
7. Publicar ambos eventos.
8. Verificar la topología desde Management UI.

## Restricción

La configuración de RabbitMQ debe mantenerse separada de Controller y lógica de aplicación.

## Evidencia

- diagrama;
- nombres de exchange, queues y routing keys;
- código ejecutable;
- mensajes publicados y consumidos;
- explicación breve de qué problema resuelve la asincronía.

## Desafío opcional

Hacer que la queue de auditoría reciba tanto `pedido.creado` como `pedido.cancelado` sin duplicar consumers.
