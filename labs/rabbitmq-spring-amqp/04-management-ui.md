# Etapa 4 · Observar y diagnosticar desde Management UI

## Objetivo

No depender solamente de logs de consola.

## Identificar

- exchange;
- tipo de exchange;
- queues;
- bindings;
- routing keys;
- consumers activos;
- mensajes Ready;
- mensajes Unacked cuando corresponda.

## Prueba controlada

1. Detén temporalmente el consumer.
2. Publica un mensaje.
3. Observa el mensaje pendiente.
4. Levanta nuevamente el consumer.
5. Confirma que el mensaje se procesa.

## Checkpoint

Debes ser capaz de explicar la diferencia entre “el producer publicó” y “el consumer procesó”.
