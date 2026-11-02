# Ejemplo 01 · Evento JSON y serialización

**Objetivo:** distinguir el evento como contrato del mecanismo de envío.

```java
record CompraEvento(String id, String cliente, int monto) {}

CompraEvento evento = new CompraEvento("c-001", "Ana", 1500);
String json = objectMapper.writeValueAsString(evento);
kafkaTemplate.send("compras", evento.cliente(), json);
```

**Contexto:** fragmento didáctico para mostrar dentro de un componente Spring donde `objectMapper` y `kafkaTemplate` ya existen; no es un programa autónomo.

En este ejemplo el value es **String JSON** y el serializer configurado debe ser `StringSerializer`. `ObjectMapper` realiza la serialización antes de enviar. Contrastar con `KafkaTemplate<String, CompraEvento>` + `JsonSerializer` correctamente configurado: no son idénticos.

**Discusión:** ¿qué sucede si el consumidor espera JSON distinto? ¿Qué campos mínimos debe validar aunque el productor ya tenga `@Valid`?
