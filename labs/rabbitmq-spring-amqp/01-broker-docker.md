# Etapa 1 · Levantar RabbitMQ con Docker Compose

## Objetivo

Levantar un broker reproducible y observarlo antes de conectarlo a Spring.

## `compose.yaml`

```yaml
services:
  rabbitmq:
    image: rabbitmq:4-management
    container_name: dsy1107-rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
    healthcheck:
      test: ["CMD", "rabbitmq-diagnostics", "-q", "ping"]
      interval: 10s
      timeout: 5s
      retries: 5
```

El puerto `5672` corresponde al tráfico AMQP del cliente. El `15672` corresponde a la interfaz HTTP de administración.

## Ejecución

```bash
docker compose up -d
docker compose ps
docker compose logs rabbitmq --tail=50
```

Esperar hasta que el contenedor esté `healthy` o, como mínimo, que el broker informe inicio correcto.

## Management UI

Abrir `http://localhost:15672` e ingresar con las credenciales locales definidas en Compose.

Reconocer las pestañas:

- **Connections**: conexiones TCP/AMQP;
- **Channels**: canales AMQP abiertos;
- **Exchanges**: puntos de publicación/routing;
- **Queues and Streams**: colas y consumidores.

## Diagnóstico rápido

Si `15672` abre pero la aplicación no conecta a `5672`, no asumir que el broker está completamente accesible: comprobar ambos listeners.

```bash
docker exec dsy1107-rabbitmq rabbitmq-diagnostics -q ping
```

## Checkpoint 1

- [ ] contenedor levantado;
- [ ] UI accesible;
- [ ] broker responde al diagnóstico;
- [ ] estudiante puede explicar la diferencia entre 5672 y 15672.
