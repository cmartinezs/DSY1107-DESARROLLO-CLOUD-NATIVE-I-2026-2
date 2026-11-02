# Semana 13 · Productores, consumidores y pipeline Kafka

**Unidad:** 3 · **Secciones:** 002D (martes, cuatro bloques) y 003D (viernes, dos bloques). **Continuidad:** Semana 12; no repetir la evaluación parcial 2, ya aplicada en ambas secciones.

## Fuente de coordinación · actividades

- **3.2.1:** Crear topic, productor y consumidor; offsets y particiones.
- **3.2.2:** Pipeline de eventos del caso end-to-end.

**Meta:** intercambio asíncrono de eventos JSON entre microservicios, con procesamiento, transformación, validación y diagnóstico de fallos.

## Estado técnico real

El [laboratorio B de semana 12](../../labs/kafka-compras-webapp/) ya implementa un productor **KafkaTemplate<String,String>**, serialización explícita con **ObjectMapper** a JSON, consumidor con **@KafkaListener** y resumen en memoria. **No usa JsonSerializer/JsonDeserializer ni Kafka Streams**, y todavía **no contiene** un segundo topic de eventos procesados, un procesador de transformación, estrategia DLQ ni reintentos controlados. No presentarlos como implementados.

El [laboratorio A](../../labs/kafka-fundamentos/) permite comprobar topics, particiones, grupos y offsets sin aplicación Spring. Ambos laboratorios son independientes: no ejecutar sus brokers simultáneamente en el mismo host porque comparten puertos.

## Ruta de aprendizaje

1. **Recuperar (3.2.1):** crear y observar topic de tres particiones, producir y consumir; consultar key, partition, offset y grupos en Kafka UI.
2. **Leer el código real:** inspeccionar `CompraController` (`KafkaTemplate`, DTO, `ObjectMapper`, key) y `ResumenController` (`@KafkaListener`).
3. **Diseñar (3.2.2):** evento de entrada `compras` → validación/reglas → evento derivado en `compras-validas` → consumidor final, más ruta explícita para eventos inválidos.
4. **Implementar la extensión guiada:** seguir [actividad técnica de pipeline](./02-laboratorio-pipeline.md), preservando la demo de semana 12 como baseline.
5. **Verificar:** publicar eventos válidos e inválidos, revisar el topic de salida y la ruta de error sin perder el servicio, explicar cuándo hacer ACK/reintento y cómo prevenir duplicados.

## Evidencias mínimas

- Topic `compras` con tres particiones, evento JSON y captura de key/partition/offset.
- Identificación concreta de productor, DTO, serializer y listener de la demo, explicando por qué serialización manual a String no equivale a `JsonSerializer`.
- Diagrama y contrato entrada/salida de pipeline; implementación incremental cuando sea viable.
- Prueba positiva y negativa; indicar qué ocurre con mensaje inválido, cómo se evita reintentar permanentemente y si existe realmente una DLQ.
- DevLog con comandos, resultados y limitaciones. **No declarar flujo end-to-end completo si no se ejecutó.**

## Priorización por sección

- **002D (cuatro bloques):** repaso breve, inspección de código, creación de topic de salida, empezar implementación de transformación y pruebas.
- **003D (dos bloques):** demostrar 3.2.1, explicar productor/listener y diseñar el pipeline con contrato JSON. Dejar implementación profunda y manejo de errores para continuación si no alcanza tiempo.

## Rutas

- [Guía docente](./01-guia-docente.md)
- [Extensión de pipeline con checkpoints](./02-laboratorio-pipeline.md)
- [Laboratorio Kafka A](../../labs/kafka-fundamentos/)
- [Laboratorio Kafka B](../../labs/kafka-compras-webapp/)
- [RegistrApp · checkpoint semana 13](../../proyecto-formativo/semana-13/README.md)

## Trabajo autónomo

- [Kafka Streams — Spring Kafka para transformar eventos](https://www.youtube.com/watch?v=plFbXvuoAR8)
- [Profundizando en Kafka Streams](https://www.youtube.com/watch?v=mHkk6jd4Y5w&t=1484s)

Los videos son profundización; **no** implican que el repositorio ya ejecute Kafka Streams.

## Gestión de créditos

**AWS:** monitorear antes de alcanzar **50 USD** y preparar respaldo. **Azure:** monitorear **100 USD** y reportar casos cercanos con **nombre, sección y sede**. Son límites comunicados por coordinación. Los laboratorios locales no dependen de estos créditos.
