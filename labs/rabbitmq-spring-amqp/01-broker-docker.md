# Etapa 1 · Levantar RabbitMQ con Docker Compose

## Archivo mínimo

```yaml
services:
  rabbitmq:
    image: rabbitmq:4-management
    ports:
      - "5672:5672"
      - "15672:15672"
```

## Ejecución

```bash
docker compose up -d
docker compose ps
```

## Management UI

Abrir:

```text
http://localhost:15672
```

## Qué observar

- broker operativo;
- exchanges por defecto;
- cero o más queues según estado;
- conexiones y channels cuando la aplicación se conecte.

## Checkpoint

RabbitMQ debe estar visible y estable antes de escribir producer/consumer.
