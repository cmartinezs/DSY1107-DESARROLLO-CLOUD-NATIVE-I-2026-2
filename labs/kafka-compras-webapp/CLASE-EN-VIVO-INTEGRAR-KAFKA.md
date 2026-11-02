# Clase en vivo · Conectar dos microservicios con Kafka (desde el starter)

**DSY1107 · Semana 13 · 3.2.1 + introducción a 3.2.2.**

**Esta NO es la guía de configuración de [Laboratorio B](./LAB-WEBAPP-PASO-A-PASO.md).** Es otra modalidad, pensada para que el docente **programe en vivo únicamente la integración con Kafka** y los estudiantes sigan los checkpoints, sin desarrollar React, endpoints REST, modelos, agregaciones, Dockerfiles ni Nginx.

- **Código inicial:** rama [`clase-kafka-starter`](https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/clase-kafka-starter/labs/kafka-compras-webapp).
- **Código de referencia integrado:** rama [`master`](https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/master/labs/kafka-compras-webapp/webapp). Usa otra implementación de algunas clases, pero reproduce el mismo flujo Kafka.
- **Guion del docente y adaptación por bloques:** [Semana 13](../../semanas/semana-13/03-guion-clase-integracion.md).

> **IMPORTANTE:** no ejecutes los otros laboratorios Kafka simultáneamente; todos usan el puerto 9092. El starter es deliberadamente **solo HTTP** en sus dos controladores; la infraestructura Kafka y sus propiedades de aplicación están preparadas para que el tiempo de clase se use en `KafkaTemplate` y `@KafkaListener`. El proyecto de la rama inicial NO comparte sus cambios con `master` automáticamente.

## Qué verán al inicio y al final

**Inicio (antes de escribir integración):**

```text
React → HTTP POST /api/compras → compras-api (responde 201)
React → HTTP GET /api/resumen → resumen-api (responde 200; sin compras)
                                No hay eventos enviados ni listener Kafka
```

La web y los microservicios están sanos: el productor acepta la compra, y el resumen funciona incluso con una carga manual de demostración. **Sin mensajería todavía no se comunican entre sí**; el tablero permanece vacío. Esto es el problema didáctico, **no** un defecto oculto.

**Meta de la clase:**

```mermaid
flowchart LR
  W[React] -->|HTTP POST| P[compras-api]
  P -->|KafkaTemplate JSON key cliente| K[(Kafka topic compras)]
  K -->|@KafkaListener grupo resumen-app| R[resumen-api]
  R -->|HTTP GET por proxy| W
  UI[Kafka UI] -.observa mensajes y offsets.-> K
```

**Verificación observable:** una compra de Ana aparece en Kafka UI con `key=Ana`, una partición y un offset; el segundo microservicio la consume y React muestra la venta.

## Preparación previa — docente, NO en tiempo de clase

1. Revisa Docker Desktop y que el equipo tenga imágenes/dependencias necesarias. **La primera construcción descarga Maven/npm e imágenes**: precalienta el entorno antes de la primera clase.
2. Descarga la **rama `clase-kafka-starter`** (GitHub: selector de ramas → Code → Download ZIP), no `master`; si trabajas con Git, `git switch clase-kafka-starter`.
3. En Windows, abre PowerShell en `labs\kafka-compras-webapp`. Copia `.env.example` a `.env`:
   ```powershell
   Copy-Item .env.example .env
   docker compose --profile app up -d --build
   docker compose --profile app ps
   ```
4. El starter ya contiene valores de `.env.example`, proxy Nginx y bibliotecas Spring Kafka. No pedir al grupo configurar esa infraestructura como parte de esta clase.
5. Confirma **http://localhost:5173** (web), **http://localhost:8083** (Kafka UI), `GET http://localhost:8081/api/compras/salud` y `GET http://localhost:8082/api/resumen/salud`. El comando `docker compose --profile app ps` debe mostrar **cinco servicios**.
6. Confirma un `POST` de compra: HTTP 201, pero **cero ventas en el resumen**. Es el estado inicial esperado. Si deseas verificar la lógica del resumen independientemente:
   ```powershell
   Invoke-RestMethod -Method Post -Uri http://localhost:8082/api/resumen/demostracion -ContentType "application/json" -Body '{"id":"manual-1","cliente":"Ana","monto":20,"fechaEvento":"2026-11-02T12:00:00Z"}'
   ```
   El resumen aumenta a 1 **sin usar Kafka**. Reinicia solo `resumen-api` para vaciar ese estado de demostración antes de continuar: `docker compose --profile app restart resumen-api`.

**No usar `docker compose down` entre checkpoints**: este Kafka no tiene volumen persistente y se perderán los eventos al eliminarlo. Para limpieza después de clase, `docker compose --profile app down`.

---

## Checkpoint 0 · Mostrar que los microservicios ya funcionan (5–10 min efectivos)

**Pregunta inicial:** «Si la API de compras respondió HTTP 201, ¿por qué el tablero sigue en cero?».

1. Abre la web: registra una compra de Ana por 100.
2. Comprueba que `compras-api` respondió bien.
3. Comprueba `GET http://localhost:8082/api/resumen`: no cambió porque son servicios independientes.
4. Dibuja las flechas actuales y la flecha que falta.

**PASS:** web responde; HTTP 201; resumen HTTP 200; todavía no hay mensaje de Kafka. **NO PASS:** si no arrancan ambos servicios, resolver Docker/compilación antes de introducir Kafka.

## Checkpoint 1 · Topic y observación (10–15 min)

1. Abre **http://localhost:8083 → Topics**.
2. Crea el topic `compras` con **3 particiones y replication factor 1**, o comprueba que ya existe.
3. También puedes usar PowerShell desde la carpeta del lab:
   ```powershell
   docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic compras --partitions 3 --replication-factor 1
   docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic compras
   ```
4. Explica: **topic = nombre del log**, **partición = secuencia ordenada**, **offset = posición dentro de esa partición**; los grupos de consumidores tienen offsets independientes.

**PASS:** `compras` tiene tres particiones y es visible en Kafka UI. **Si falla:** comprobar Kafka saludable, puerto 8083 y estado de Kafka UI.

## Checkpoint 2 · Conectar el productor Spring Boot (15–25 min)

**Archivo único que editaremos en este checkpoint:**

`webapp/compras-api/src/main/java/cl/duoc/dsy1107/compras/CompraController.java`

El controlador y su `record CompraEvento` ya existen. **No los reconstruyas**. En el starter ya están configurados `spring-kafka`, `StringSerializer`, `APP_KAFKA_TOPIC` y `kafka:9094`; identifica los archivos `pom.xml`, `application.yml` y `compose.yaml` brevemente.

**Paso A:** añade estos imports:

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.concurrent.TimeUnit;
```

**Paso B:** agrega campos y constructor a `CompraController` (dentro de la clase, fuera de los métodos):

```java
private final KafkaTemplate<String, String> kafka;
private final ObjectMapper mapper;
private final String topic;

public CompraController(KafkaTemplate<String, String> kafka, ObjectMapper mapper,
                        @Value("${app.kafka.topic}") String topic) {
    this.kafka = kafka;
    this.mapper = mapper;
    this.topic = topic;
}
```

**Paso C:** el método `crear` ya construye una variable `evento`. Cambia su firma a `public CompraEvento crear(@Valid @RequestBody NuevaCompra entrada) throws Exception`, y coloca **antes de `return evento;`** estas líneas:

```java
String json = mapper.writeValueAsString(evento);
var resultado = kafka.send(topic, evento.cliente(), json).get(10, TimeUnit.SECONDS);
System.out.printf("PUBLICADO key=%s particion=%d offset=%d%n",
        evento.cliente(), resultado.getRecordMetadata().partition(),
        resultado.getRecordMetadata().offset());
```

El envío espera confirmación del broker para poder hacer un checkpoint claro. Este método simplifica el manejo de excepciones para **demostración**; en producción se diseñan errores HTTP, tiempos de espera e idempotencia explícitamente.

**Paso D:** reconstruye **solo** ese servicio:

```powershell
docker compose --profile app up -d --build compras-api
docker compose --profile app logs --tail=30 compras-api
```

**Paso E:** publica una compra desde la web. Abre **Kafka UI → Topics → compras → Messages**. Busca `key=Ana` y el JSON de la compra.

**PASS:** evento visible con campos `id`, `cliente`, `monto`, `fechaEvento`, `partition` y `offset`. **Todavía es correcto** que el resumen siga en cero. Si el productor respondió error, revisa `APP_KAFKA_TOPIC=compras`, Kafka y el stacktrace del servicio; dentro de Docker el broker es `kafka:9094`, nunca `localhost:9092`.

**Punto seguro de cierre para una clase corta:** el productor ya emite eventos aunque el consumidor todavía no esté conectado.

## Checkpoint 3 · Conectar el consumidor (20–30 min)

**Archivo único que editaremos ahora:**

`webapp/resumen-api/src/main/java/cl/duoc/dsy1107/resumen/ResumenController.java`

La agregación `registrar(CompraEvento, particion, offset)` ya está implementada y funciona en el starter. **Solo conectamos Kafka a esa lógica existente**.

**Paso A:** añade imports:

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
```

**Paso B:** añade campo y constructor:

```java
private final ObjectMapper mapper;

public ResumenController(ObjectMapper mapper) {
    this.mapper = mapper;
}
```

**Paso C:** incorpora el listener dentro de la clase:

```java
@KafkaListener(topics = "${app.kafka.topic}")
public void recibir(ConsumerRecord<String, String> record) throws Exception {
    CompraEvento evento = mapper.readValue(record.value(), CompraEvento.class);
    registrar(evento, record.partition(), record.offset());
    System.out.printf("CONSUMIDO key=%s particion=%d offset=%d%n",
            record.key(), record.partition(), record.offset());
}
```

**Paso D:** reconstruye solo el consumidor:

```powershell
docker compose --profile app up -d --build resumen-api
docker compose --profile app logs --tail=50 resumen-api
```

**Paso E:** publica dos compras desde React: Ana=100 y Ana=200. La web consulta el resumen cada 2 segundos; debe mostrar **Ana=300** más cualquier compra anterior retenida que se haya procesado. Como el grupo es nuevo y tiene `auto-offset-reset: earliest`, puede consumir también las compras del checkpoint 2.

**PASS:** el segundo microservicio recibe registros y el tablero cambia; los mismos eventos son inspeccionables en Kafka UI. **Si falla:** grupo `resumen-app`, topic `compras`, logs del listener, JSON válido y offsets ya confirmados.

## Checkpoint 4 · Demostrar desacoplamiento y lag (10–20 min)

**No hay que escribir más Java.**

1. Detén únicamente el consumidor:
   ```powershell
   docker compose --profile app stop resumen-api
   ```
2. Publica desde React otra compra, por ejemplo Luis=700. El productor sigue trabajando y Kafka conserva el evento; el resumen no cambia.
3. En **Kafka UI → Consumer Groups → resumen-app**, observa la posición confirmada y el posible **lag**.
4. Inicia el consumidor:
   ```powershell
   docker compose --profile app start resumen-api
   ```
5. Observa cómo procesa el evento pendiente.

**PASS:** sin consumidor el productor continúa publicando; al volver el consumidor, el resumen se actualiza. **Punto clave:** «streaming» aquí significa **flujo continuo de eventos**; todavía no usamos la **biblioteca Kafka Streams** ni topologías DSL.

## Extensión opcional 3.2.2 · Evento derivado (20–35 min extra, SOLO si sobra tiempo)

Esta actividad es **opcional**, no un requisito para llegar al checkpoint 4. Usa la misma app (sin añadir un tercer microservicio).

1. Crea topic `compras-alertas` (1 partición basta para esta demostración).
2. Discute una regla: `monto >= 1000 → alerta ALTA`. La entrada continúa siendo `compras`.
3. En `resumen-api`, **después de registrar el evento y sin modificar los cálculos**, publica un JSON simple en `compras-alertas` **solo si supera el umbral**.
4. Verifica en Kafka UI que **una venta alta** genera un segundo mensaje en el topic derivado, mientras una venta normal no.
5. Pregunta: ¿qué puede pasar si falla publicar la alerta después de leer el evento? ¿Qué harías con errores permanentes o duplicados?

**Fragmento ilustrativo para agregar en `ResumenController` si el tiempo permite completar la implementación:** inyecta también `KafkaTemplate<String,String>` por constructor y, dentro de `recibir`, después de registrar:

```java
if (evento.monto() >= 1000) {
    String alerta = mapper.writeValueAsString(
        java.util.Map.of("idOrigen", evento.id(), "cliente", evento.cliente(),
                         "monto", evento.monto(), "nivel", "ALTA"));
    kafka.send("compras-alertas", evento.cliente(), alerta).get(10, TimeUnit.SECONDS);
}
```

En la clase de referencia esto **no está implementado**. Si no se programa, registra **solo diseño**. El fragmento no resuelve por sí mismo reintentos, DLQ, garantías de entrega ni exactly-once. Se trata de procesamiento en un listener Spring Kafka, **no de Kafka Streams**.

## Recuperación rápida

| Síntoma | Revisar |
|---|---|
| 502 en web | Proxy Nginx y estado de `compras-api` / `resumen-api` |
| Kafka UI no abre | http://localhost:8083, `docker compose --profile app ps` |
| Evento no aparece | Topic creado, nombre de topic, logs `compras-api` |
| Evento sí aparece y resumen en 0 | Es correcto **antes de checkpoint 3**; después, revisar consumidor |
| El resumen no parte en 0 | El grupo puede haber leído eventos previos; los eventos retenidos no se borran al reconstruir un servicio |
| La web tiene ventas que parecían anteriores | La UI consulta el estado en memoria del consumidor, que puede reconstruirse desde el topic |
| Compilar tarda mucho | Descargas de Maven/npm; usar caché previamente preparada en los equipos |
| Los mensajes no rotan P0–P1–P2 | Kafka no garantiza round-robin estricto sin clave; con la misma key, el particionador conserva afinidad mientras no cambien condiciones |

## Evidencias mínimas

- Captura 0: servicios sanos sin integración (resumen en cero).
- Captura 1: Kafka UI con topic y tres particiones.
- Captura 2: primer evento publicado desde `compras-api`, **antes de que exista listener**.
- Captura 3: tabla React + `resumen-api` actualizado después de incorporar `@KafkaListener`.
- Captura 4: consumidor detenido, lag visible si ya existen offsets confirmados, consumidor reiniciado y evento procesado.

**No confundir con el AVA:** 3.2.1 trabaja topic, particiones, producer/consumer y offsets; 3.2.2 presenta un pipeline bancario con Kafka Streams, filtros y alertas. Esta clase **enseña integración de microservicios mediante Spring Kafka y procesamiento continuo de eventos**, con un evento derivado opcional; **no afirma implementar Kafka Streams DSL**.
