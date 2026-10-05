# 04 · Pruebas de fallo

## A · Éxito
`mensaje válido → procesamiento → ACK`

## B · Consumer detenido
`publish → pendiente → consumer vuelve → procesamiento`

## C · Error definitivo
`mensaje diseñado para fallar → NACK/reject sin requeue → DLX → DLQ`

## D · Expiración
Solo si aplica: `mensaje → TTL → DLX → DLQ`.

Para cada prueba registra expectativa, resultado, evidencia y explicación.
