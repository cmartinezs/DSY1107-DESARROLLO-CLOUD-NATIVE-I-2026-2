# 02 · Estrategia de entrega

## ACK
Define cuándo se confirma, qué operación debe terminar y qué pasa si el proceso cae antes.

## Errores
Clasifica error recuperable y no recuperable. Para cada uno decide ACK, NACK con requeue o rechazo sin requeue.

## Durabilidad
Explica si exchange, queue y mensaje necesitan sobrevivir un reinicio y por qué.

Cada decisión debe conectarse al caso de uso de RegistrApp.
