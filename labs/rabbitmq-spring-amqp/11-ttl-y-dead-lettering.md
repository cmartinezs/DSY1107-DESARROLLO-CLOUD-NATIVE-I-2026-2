# 11 · TTL y dead-lettering por expiración

Configura TTL en una queue o mensaje del laboratorio. Publica sin consumer disponible, espera la expiración y verifica el envío al DLX/DLQ.

Compara:
- mensaje rechazado por el consumer;
- mensaje expirado antes de ser procesado.

Ambos pueden llegar a DLQ por causas diferentes.
