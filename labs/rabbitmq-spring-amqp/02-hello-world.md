# Etapa 2 · Hello World · Producer → Queue → Consumer

## Objetivo

Demostrar el recorrido mínimo de un mensaje antes de introducir exchanges propios y routing más rico.

## 1. Declarar la queue

```java
@Configuration
public class RabbitMQConfig {
    public static final String HELLO_QUEUE = "hello.queue";

    @Bean
    Queue helloQueue() {
        return QueueBuilder.durable(HELLO_QUEUE).build();
    }
}
```

Spring puede declarar el recurso en el broker al iniciar la aplicación.

## 2. Producer

```java
@Component
public class HelloPublisher {
    private final RabbitTemplate rabbitTemplate;

    public HelloPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.HELLO_QUEUE, message);
    }
}
```

En esta sobrecarga se utiliza el exchange por defecto y el nombre de la queue funciona como routing key. Más adelante haremos explícito el exchange para que el routing sea observable.

## 3. Consumer

```java
@Component
public class HelloConsumer {

    @RabbitListener(queues = RabbitMQConfig.HELLO_QUEUE)
    public void consume(String message) {
        System.out.println("Recibido: " + message);
    }
}
```

## 4. Disparar el envío

Puede utilizarse un `CommandLineRunner`, un endpoint REST de prueba o un test. El mecanismo de disparo no es el contenido principal; el foco es el mensaje.

Ejemplo mínimo:

```java
@Bean
CommandLineRunner demo(HelloPublisher publisher) {
    return args -> publisher.publish("hola-rabbit");
}
```

## Qué observar

1. la aplicación abre una conexión;
2. `hello.queue` existe;
3. el producer publica;
4. el listener consume;
5. el mensaje deja de estar pendiente.

## Checkpoint 2

- [ ] mensaje enviado;
- [ ] queue visible;
- [ ] consumer activo;
- [ ] mensaje recibido;
- [ ] estudiante diferencia producer, queue y consumer.
