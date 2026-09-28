# 2.1.2 · Queue, Producer y Consumer · Hello World

## Objetivo

Construir el recorrido mínimo antes de agregar routing avanzado.

```text
Producer → Queue → Consumer
```

## RabbitMQ local

El broker se ejecuta mediante Docker. Dos puertos importan:

- `5672`: protocolo AMQP;
- `15672`: Management UI.

## Spring AMQP

Dependencia principal:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### Producer

Usa `RabbitTemplate` para publicar.

### Consumer

Usa `@RabbitListener` para escuchar una queue.

## Checkpoint

Antes de continuar:

- broker visible;
- queue declarada;
- producer publica;
- consumer recibe;
- Management UI permite observar la topología;
- el estudiante puede explicar qué quedó desacoplado.
