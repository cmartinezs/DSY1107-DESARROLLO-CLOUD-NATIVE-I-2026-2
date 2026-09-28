# Ejemplo 1 · Producer y Consumer mínimos

## Objetivo

Mostrar la idea de trabajo desacoplado sin routing complejo.

```java
@Component
class DemoPublisher {
    private final RabbitTemplate rabbitTemplate;

    DemoPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    void enviar(String mensaje) {
        rabbitTemplate.convertAndSend("hello.queue", mensaje);
    }
}
```

```java
@Component
class DemoConsumer {
    @RabbitListener(queues = "hello.queue")
    void recibir(String mensaje) {
        System.out.println("Recibido: " + mensaje);
    }
}
```

## Qué observar

El publisher no llama directamente al consumer.
