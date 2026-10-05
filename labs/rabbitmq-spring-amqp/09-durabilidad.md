# 09 · Durabilidad y persistencia

Verifica exchange y queue durables. Publica un mensaje persistente sin consumer.

1. confirma que queda Ready;
2. reinicia RabbitMQ;
3. verifica si el mensaje sigue disponible;
4. levanta el consumer y procésalo.

Responde qué protegió el mensaje durante el reinicio y por qué ACK todavía no había participado.
