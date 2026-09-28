# Etapa 2 · Hello World Producer → Queue → Consumer

## Objetivo

Demostrar el recorrido mínimo sin routing explícito complejo.

## Pasos

1. Agregar `spring-boot-starter-amqp`.
2. Configurar conexión local.
3. Declarar una queue `hello.queue`.
4. Crear un producer con `RabbitTemplate`.
5. Crear un consumer con `@RabbitListener`.
6. Publicar un mensaje simple.
7. Confirmar recepción.

## Checkpoint

Debes poder mostrar:

```text
mensaje enviado
→ queue
→ listener
→ mensaje procesado
```

y explicar qué responsabilidad cumple cada pieza.
