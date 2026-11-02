# Ejemplo 02 · Transformación separada del listener

**Objetivo:** separar entrada de eventos y regla de negocio. Pseudocódigo ilustrativo, **no implementación compilable**:

```text
listener(record):
    evento = parsearYValidar(record.value)
    resultado = clasificar(evento)  // monto >= 1000 => ALTA
    publicar("compras-validas", record.key, resultado)

clasificar(evento):
    return {idOrigen: evento.id, categoria: evento.monto >= 1000 ? ALTA : NORMAL}
```

`@KafkaListener` recibe; un servicio de dominio clasifica; un publisher produce. Una transformación escrita en Java común **no es** `mapValues()` de Kafka Streams. No afirmar garantías de procesamiento hasta configurar ACK, reintentos y comprobarlos.

**Pregunta:** ¿por qué conviene tener la lógica de clasificación fuera del método listener?
