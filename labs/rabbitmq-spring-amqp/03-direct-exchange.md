# Etapa 3 · DirectExchange, bindings y routing keys

## Objetivo

Hacer explícito el routing y comprobar que el producer no publica directamente a una queue de negocio.

## Topología

```text
pedidos.exchange

pedido.creado
  ├─> pedidos.notificaciones.queue
  └─> pedidos.auditoria.queue

pedido.cancelado
  └─> pedidos.auditoria.queue
```

## Configuración

```java
@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "pedidos.exchange";
    public static final String NOTIFICACIONES = "pedidos.notificaciones.queue";
    public static final String AUDITORIA = "pedidos.auditoria.queue";

    @Bean
    DirectExchange pedidosExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    Queue notificacionesQueue() {
        return QueueBuilder.durable(NOTIFICACIONES).build();
    }

    @Bean
    Queue auditoriaQueue() {
        return QueueBuilder.durable(AUDITORIA).build();
    }

    @Bean
    Binding creadoNotificacion(DirectExchange pedidosExchange) {
        return BindingBuilder.bind(notificacionesQueue())
                .to(pedidosExchange)
                .with("pedido.creado");
    }

    @Bean
    Binding creadoAuditoria(DirectExchange pedidosExchange) {
        return BindingBuilder.bind(auditoriaQueue())
                .to(pedidosExchange)
                .with("pedido.creado");
    }

    @Bean
    Binding canceladoAuditoria(DirectExchange pedidosExchange) {
        return BindingBuilder.bind(auditoriaQueue())
                .to(pedidosExchange)
                .with("pedido.cancelado");
    }
}
```

## Publicación explícita

```java
rabbitTemplate.convertAndSend(
        RabbitMQConfig.EXCHANGE,
        "pedido.creado",
        evento
);
```

## Preguntas de control

- ¿Por qué un `DirectExchange` requiere coincidencia exacta de routing key?
- ¿Por qué `pedido.creado` puede llegar a dos queues?
- ¿Por qué `pedido.cancelado` no debe llegar a notificaciones con esta topología?

## Checkpoint 3

En Management UI demostrar exchange, bindings, queues y el efecto de ambas routing keys.
