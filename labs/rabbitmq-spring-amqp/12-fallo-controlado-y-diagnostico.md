# 12 · Fallo controlado y diagnóstico

## Escenarios
1. éxito: `publish → consume → ACK`;
2. consumer detenido: `publish → Ready → consumer vuelve → ACK`;
3. error definitivo: `publish → NACK/reject → DLX → DLQ`;
4. expiración, cuando aplique: `publish → TTL → DLX → DLQ`.

Incluye topología, logs, Management UI, estados Ready/Unacked cuando correspondan y mensaje final en DLQ.
