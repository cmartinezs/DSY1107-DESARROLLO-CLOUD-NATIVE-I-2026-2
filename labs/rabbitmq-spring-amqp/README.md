# Laboratorio · RabbitMQ + Spring AMQP

Laboratorio local y autocontenido para Semana 08.

## Objetivo

Construir y verificar un sistema mínimo de mensajería asíncrona con RabbitMQ contenerizado y Spring Boot.

## Checkpoint 0 · Requisitos

- Docker Desktop operativo.
- Java y Maven/Gradle.
- Spring Boot con Spring AMQP.

## Checkpoint 1 · Broker

Levantar RabbitMQ con Docker Compose.

- `5672`: AMQP.
- `15672`: Management UI.

## Checkpoint 2 · Hello World

- declarar queue;
- producer con `RabbitTemplate`;
- consumer con `@RabbitListener`;
- publicar mensaje;
- demostrar recepción.

## Checkpoint 3 · Topología explícita

- `DirectExchange`;
- dos queues;
- bindings;
- routing keys;
- publicación mediante exchange + routing key.

## Checkpoint 4 · Observación

En Management UI identificar:

- exchange;
- queues;
- bindings;
- consumers;
- mensajes Ready / Unacked cuando corresponda.

## Checkpoint 5 · Separación

Organizar el código por responsabilidad:

```text
config/
messaging/
application/
web/
```

## Evidencia final

El estudiante debe poder publicar, consumir y explicar visualmente el recorrido del mensaje.

> No es objetivo de este laboratorio implementar DLQ, retries avanzados, publisher confirms ni una plataforma distribuida completa.
