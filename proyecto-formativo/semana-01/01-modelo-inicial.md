# 01 · Modelo inicial de RegistrApp

## Objetivo

Definir una primera solución mínima antes de incorporar infraestructura cloud.

## Trabajo

1. describir el problema en 3–5 líneas;
2. identificar actores;
3. elegir un recurso principal;
4. definir operaciones que realmente necesita ese recurso;
5. traducirlas a endpoints HTTP coherentes.

## Artefacto esperado

| Método | Ruta | Propósito | Respuesta esperada |
|---|---|---|---|
| GET | /api/v1/<recurso> | listar | 200 |
| GET | /api/v1/<recurso>/{id} | consultar | 200/404 |
| POST | /api/v1/<recurso> | crear | 201 |
| PUT/PATCH | /api/v1/<recurso>/{id} | modificar | 200/204 |
| DELETE | /api/v1/<recurso>/{id} | eliminar | 204/404 |

## Checkpoint

El grupo puede explicar el recurso, el contrato HTTP y por qué aún no necesita gateway, identidad ni mensajería.
