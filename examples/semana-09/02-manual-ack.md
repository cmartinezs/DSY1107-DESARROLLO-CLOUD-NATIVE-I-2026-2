# ACK manual

```java
try {
    service.process(message);
    channel.basicAck(tag, false);
} catch (RecoverableException ex) {
    channel.basicNack(tag, false, true);
} catch (Exception ex) {
    channel.basicNack(tag, false, false);
}
```

Adapta la API exacta a la versión de Spring AMQP usada.
