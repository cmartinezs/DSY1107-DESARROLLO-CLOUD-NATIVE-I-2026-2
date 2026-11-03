# Guion docente · Construir en vivo la integración Kafka, no la aplicación

**DSY1107 · Semana 13 · Complemento 3.2.1/3.2.2.**

**Guía operativa con código y comandos:** [Clase en vivo — integrar Kafka](../../labs/kafka-compras-webapp/CLASE-EN-VIVO-INTEGRAR-KAFKA.md).

## Decisión metodológica

**El objetivo no es «hacer un CRUD en Kafka».** La aplicación comercial, los endpoints REST, el frontend React, el proxy y los cálculos del tablero vienen implementados. La clase comienza con dos microservicios que responden por separado y termina con comunicación asíncrona **añadiendo dos piezas**:

- `KafkaTemplate<String,String>` en `compras-api` (produce compras).
- `@KafkaListener` en `resumen-api` (consume eventos y llama a una función de negocio existente).

**Visualización desde Kafka UI** y reacción observable en React. Los alumnos no editan la web ni programan cálculos. Mantener el laboratorio B original en `master`; para la clase usar la rama `clase-kafka-starter`, que contiene servicios HTTP operativos pero sin conexión Kafka en los controladores.

## ¿Qué digo al abrir? (2–3 minutos)

> «Tenemos una página React y dos microservicios que funcionan. La API de compras recibe una venta, pero el tablero del segundo microservicio no se entera. Hoy construiremos únicamente la comunicación entre ellos. No vamos a programar React ni rehacer los servicios. Primero publicaremos un evento, luego lo consumiremos y finalmente veremos qué pasa si el consumidor se apaga. En Kafka UI observaremos los mensajes reales y sus offsets.»

**Pregunta para activar:** «¿Qué inconveniente tendría que `compras-api` llamara por REST de forma síncrona a `resumen-api` cada vez que alguien compra?». Aceptar respuestas: acoplamiento temporal, indisponibilidad, escalabilidad del consumo y recuperación desde log; **no** afirmar que Kafka es siempre superior a REST/RabbitMQ.

## Secuencia común: checkpoints independientes

| Hito | Qué se toca | Evidencia que no depende de los siguientes pasos |
|---|---|---|
| C0 | Nada, solo HTTP | APIs responden; web recibe compra; resumen vacío |
| C1 | Kafka UI / topic | `compras` tiene tres particiones |
| C2 | Solo `CompraController` | Mensaje JSON en Kafka UI, clave/partición/offset |
| C3 | Solo `ResumenController` | Resumen React actualizado por un mensaje consumido |
| C4 | Nada de código: stop/start | Lag y entrega posterior al reiniciar consumidor |
| C5 opcional | Topic `compras-alertas`, regla | Evento derivado para compras altas |

**Detente al completar un checkpoint. No abras otro frente cinco minutos antes del cierre.** Si el equipo tarda en construir imágenes, usa la instancia preparada en el computador docente y realiza observación guiada con la clase.

## Sección con 2 + 2 + 4 bloques

Los minutos son **presupuestos de tiempo efectivo** (ajustar a llegadas e instalación del laboratorio), no una promesa de que el bloque dure siempre 45 minutos.

| Encuentro | Núcleo obligatorio | Cierre seguro | Si hay margen |
|---|---|---|---|
| 2 bloques · 1 | C0 y C1: contrato, infraestructura, topic y tres particiones | Kafka UI mostrando topic creado | C2 hasta serialización |
| 2 bloques · 2 | C2 completo: `KafkaTemplate`, primera compra en Kafka UI | Mensaje real con key, partición y offset | Lectura guiada del listener por implementar |
| 4 bloques · 3 | C3 y C4: `@KafkaListener`, dashboard, pausa y recuperación | Flujo end-to-end y lag observados | C5: segundo topic y alerta |

**Regla ante retrasos:** si una sesión de 2 bloques pierde 15–20 minutos, reduce las preguntas, **no** abras Kafka Streams ni el segundo topic. El mínimo del primer encuentro es broker + topic verificable; el del segundo, **productor enviando un evento**.

## Sección con 4 + 4 bloques

| Encuentro | Núcleo obligatorio | Cierre seguro | Si hay margen |
|---|---|---|---|
| 4 bloques · 1 | C0 → C3: HTTP inicial, topic, productor, consumidor y actualización React | Demostración end-to-end completa | Repetir key y offset con más compras |
| 4 bloques · 2 | C4: pausar consumidor, observar lag y recuperar; discusión de particiones/grupos | Recuperación de consumidor documentada | C5: topic `compras-alertas`; fallos y duplicados |

**Importante:** ninguna sección debe terminar con menos oportunidad de observar un mensaje real en Kafka; el itinerario debe converger en C3 antes de profundizaciones. Si la primera clase de 4 bloques no alcanza a llegar a C3, comenzar allí en la segunda.

## Guion de pizarra (sin 30 diapositivas)

1. **Antes:** React → compras-api (201); resumen-api (200, sin datos). La diferencia entre aceptar HTTP y publicar un evento.
2. **Productor:** una `key=cliente`, un JSON, un topic con tres particiones. Mostrar `KafkaTemplate.send`.
3. **Broker:** en Kafka UI buscar el registro real. Preguntar «¿qué significan offset y partición?». Offset no es global.
4. **Consumidor:** `@KafkaListener` y grupo `resumen-app`; conectar a `registrar()`, que **ya existe**.
5. **Streaming continuo:** mientras el consumidor está detenido, Kafka sigue recibiendo. Al reiniciarlo procesa lo pendiente desde el offset confirmado, pero el resumen en memoria parte vacío; para reconstruir el historial se debe restablecer explícitamente el grupo a earliest y hacer replay.
6. **Límite técnico:** esto usa **Spring Kafka**, no una topología **Kafka Streams** con ventanas/state store. AVA 3.2.2 puede compararse o extenderse; no prometer DLQ ni exactly-once de negocio.

## Evidencia mínima por encuentro

- **Primeros 2 bloques:** captura topic/particiones y diagrama de arquitectura.
- **Segundos 2 bloques:** captura del evento JSON + key + offset en Kafka UI.
- **Cuatro bloques:** captura React con totales y demostración stop/start de consumidor.
- **4 + 4:** registrar qué checkpoint se completó al final de cada sesión; no duplicar trabajo por cumplir un calendario teórico.

## Lista de chequeo docente antes de proyectar

- [ ] Rama `clase-kafka-starter`, no `master`.
- [ ] Docker Desktop operativo con contenedores Linux; conectividad a imágenes/Maven/npm.
- [ ] `.env` copiado desde `.env.example` **del starter**.
- [ ] Web `http://localhost:5173` y Kafka UI `http://localhost:8083`.
- [ ] Cinco servicios levantados; ambas rutas de salud HTTP responden.
- [ ] Estado inicial verificado: web acepta compras pero resumen no se actualiza.
- [ ] Archivo `CompraController` listo para proyectar (checkpoint 2).
- [ ] Archivo `ResumenController` listo para proyectar (checkpoint 3).
- [ ] No abrir otra rama ni introducir Spring Cloud Gateway, nuevas APIs, Kafka Streams o DLQ como requisito de la sesión corta.

## Preguntas de salida

**Nivel base (2 bloques):** ¿Quién publica en Kafka: React o `compras-api`? ¿Topic y partición son lo mismo? ¿Dónde vive el offset?

**Nivel integración (4 bloques):** ¿Por qué puede seguir vendiendo el productor aunque el consumidor esté detenido? ¿Por qué reiniciar el servicio no reconstruye por sí solo el resumen? ¿Qué cambia al restablecer offsets y hacer replay? ¿En qué se diferencia Kafka de una llamada REST directa?

**Nivel extensión:** ¿Por qué volver a procesar un evento podría generar dos efectos? ¿Qué harías para procesar una compra inválida sin bloquear todas las siguientes? ¿Por qué nuestro listener no equivale a Kafka Streams?

## Fuente oficial y honestidad del alcance

- **AVA 3.2.1:** topic, productor, consumidor, key, particiones, offsets y Kafka UI.
- **AVA 3.2.2:** caso bancario end-to-end con propuesta de procesamiento Kafka Streams y topic de alertas.

Este material **complementa** las guías AVA; las aplicaciones usan un caso de compras y **un listener Spring Kafka**, no una implementación Kafka Streams DSL. Si se desarrolla C5, es una etapa de publicación derivada, no una garantía de error handling completo.

**Estado de prueba:** no marcar como ensayado el Docker end-to-end hasta realizar una prueba real con starter, productor y consumidor; la publicación en GitHub no verifica compilación ni ejecución.
