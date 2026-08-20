# Tipos de API y por qué importa la elección

En Amazon API Gateway pueden existir alternativas como **HTTP API**, **REST API** y **WebSocket API**. No son simples nombres distintos para la misma cosa.

## HTTP API

Suele ser apropiada para escenarios HTTP modernos donde se busca una configuración más directa y menor complejidad operacional.

## REST API

Ofrece un conjunto más amplio de capacidades y configuraciones históricamente asociadas al servicio. Algunos tutoriales institucionales pueden asumir este tipo y por eso mostrar opciones que no aparecen igual en HTTP API.

## WebSocket API

Está orientada a comunicación bidireccional persistente y no debe confundirse con una API HTTP convencional.

## Consecuencia pedagógica

Antes de seguir un tutorial pregunta:

```text
¿Qué tipo de API está creando?
¿Qué capacidades necesita?
¿La instrucción aplica al tipo de API que estoy usando?
```

Esto explica por qué dos alumnos pueden ver consolas diferentes aun estando ambos dentro de Amazon API Gateway.

## Transferencia del concepto

En Spring Cloud Gateway no existe esta misma taxonomía comercial. Lo transferible es la idea de:

```text
entrada HTTP → ruta → políticas → integración/destino
```

La tecnología cambia; las responsabilidades arquitectónicas permanecen.