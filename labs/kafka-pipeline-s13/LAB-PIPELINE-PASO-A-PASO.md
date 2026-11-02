# Laboratorio Semana 13 · Pipeline de eventos estructurados

**Estado:** extensión implementada en el repositorio mediante el profile `pipeline`, manteniendo intacta la ejecución normal del [laboratorio B](../kafka-compras-webapp/). El código de referencia está en [`webapp/procesador-api`](../kafka-compras-webapp/webapp/procesador-api/). Debe ejecutarse y verificarse en el entorno docente antes de declararlo validado en aula.

## 0. Prerequisitos y baseline

Windows 10 + Docker Desktop + PowerShell. Seguir [LAB-WEBAPP-PASO-A-PASO](../kafka-compras-webapp/LAB-WEBAPP-PASO-A-PASO.md) hasta checkpoint 5. Verificar web `http://localhost:5173`, Kafka UI `http://localhost:8083`, topics y compras visibles. No levantar simultáneamente el laboratorio A.

En PowerShell dentro de `labs\kafka-compras-webapp`:

```powershell
docker compose --profile app ps
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic compras
```

## 1. Contratos de eventos

**Entrada** (ya producida por `CompraController`, campos en español):

```json
{"id":"c-001","cliente":"Ana","monto":100,"fechaEvento":"2026-11-02T12:00:00Z"}
```

**Salida implementada** (`compras-validas`; DTO independiente):

```json
{"idOrigen":"c-001","cliente":"Ana","monto":100,"categoria":"NORMAL","procesadoEn":"2026-11-02T12:00:01Z"}
```

Regla del ejercicio: `cliente` no vacío, `monto > 0`, `id` presente. Categoría `ALTA` si monto >= 1000; de otro modo `NORMAL`. El procesador transforma con `map` de lógica Java o `mapValues` **solo si se usa realmente Kafka Streams**. Una operación en un listener de Spring Kafka **no** debe llamarse API Streams.

**Rechazo implementado** (`compras-rechazadas`): `idOrigen` cuando exista, `motivo`, `recibidoEn`; evitar incluir datos sensibles. Un JSON corrupto requiere tratar la falla de deserialización de forma explícita.

## 2. Preparar topics

```powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic compras-validas --partitions 3 --replication-factor 1
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic compras-rechazadas --partitions 3 --replication-factor 1
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```

**Checkpoint:** ambos topics aparecen también en Kafka UI. Crear topics no implica que exista un procesador activo.

## 2B. Levantar la extensión de Semana 13

Con los topics creados, desde `labs\kafka-compras-webapp`:

```powershell
docker compose --profile app --profile pipeline up -d --build
docker compose --profile app --profile pipeline ps
docker compose --profile pipeline logs --tail=100 procesador-api
```

**Checkpoint:** deben verse **seis servicios**: Kafka, Kafka UI, `compras-api`, `resumen-api`, web y `procesador-api`. El log del procesador debe iniciar sin errores de topic inexistente o configuración `PENDIENTE`.

Para ejecutar solo las pruebas del procesador durante el build, Docker ejecuta `mvn package`; si alguna prueba JUnit falla, la imagen no se construye.

## 3. Identificar y separar responsabilidades

Buscar en `webapp/compras-api/src/main/java/.../CompraController.java`:

- `record NuevaCompra` valida HTTP con Jakarta Validation.
- `record CompraEvento` representa evento.
- `ObjectMapper.writeValueAsString` transforma DTO a String JSON.
- `KafkaTemplate<String,String>.send` publica. En `application.yml`: **StringSerializer**, no **JsonSerializer**.

Buscar en `webapp/resumen-api/src/main/java/.../ResumenController.java`:

- `@KafkaListener` consume `compras`.
- `ObjectMapper.readValue` interpreta JSON y puede fallar.
- El cálculo del resumen se mezcla con el adaptador de mensajería. Proponer separar `CompraProcessor` (reglas) del listener (transporte).

**Checkpoint:** tabla `origen HTTP → DTO → JSON → topic → listener → regla → resultado` con archivos reales.

## 4. Extensión de implementación (sin romper semana 12)

El repositorio incorpora **un procesador independiente `procesador-api`** suscrito a `compras` mediante el grupo `procesador-s13`, distinto de `resumen-app`. Está aislado por el profile Compose `pipeline`, por lo que el laboratorio de semana 12 conserva su recorrido normal.

Contrato conceptual que corresponde a la implementación de referencia:

```text
onMessage(record):
    intentar leer JSON a CompraEvento
    si faltan id/cliente o monto <= 0:
        publicar rechazo con razón en compras-rechazadas
        confirmar entrada solo después de publicación satisfactoria
        terminar
    resultado = transformar(evento)
    publicar resultado en compras-validas con key=cliente
    confirmar entrada según estrategia de entrega elegida
```

**Decisiones necesarias:** usar `ObjectMapper` + `StringSerializer` (consistente con semana 12) **o** configurar `JsonSerializer/JsonDeserializer` y tipos concretos en ambos extremos; no mezclar contratos. Usar `KafkaTemplate` para salida y `@KafkaListener` para entrada. Separar `CompraProcessor` (validación/transformación) de infraestructura y DTOs.

**Importante:** si la publicación de salida falla, no confirmar el offset de entrada como exitoso. Un reintento puede duplicar la salida si hubo publicación pero falló la confirmación; discutir idempotencia, transacciones Kafka y sus límites. Para errores permanentes, definir retries acotados + política de recuperación. **No usar un `catch` que solo registre y descarte sin evidencia.**

## 5. Probar positivo y negativo

**Positivo:** desde PowerShell publicar compra válida por API de semana 12:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8081/api/compras -ContentType "application/json" -Body '{"cliente":"Ana","monto":1500}'
```

**Esperado con el profile `pipeline` activo:** evento en `compras`, después evento `categoria=ALTA` en `compras-validas` con el mismo ID origen.

**Negativo de contrato HTTP:** 

```powershell
try { Invoke-RestMethod -Method Post -Uri http://localhost:8081/api/compras -ContentType "application/json" -Body '{"cliente":"","monto":-2}' } catch { $_.Exception.Message }
```

Debe ser rechazado en el límite HTTP, **sin crear evento Kafka**. Esta prueba **no** verifica por sí sola la ruta de rechazo del consumidor.

**Negativo del consumidor:** insertar directamente un evento sintácticamente válido pero inválido para negocio (ej. `{"id":"invalido-01","cliente":"","monto":-1}`) usando Kafka UI o productor de consola. Debe aparecer una evidencia en `compras-rechazadas` y el grupo debe seguir procesando eventos válidos posteriores.

**Falla de infraestructura:** apagar transitoriamente el procesador y reiniciarlo con eventos retenidos; observar offsets y distinguir replay de procesamiento duplicado. No apagar el broker con `docker compose down` durante la prueba, pues el entorno no declara volumen persistente.

## 6. Gate de entrega

Para las evidencias, diagnóstico, políticas de fallo y cierre del entorno, completar la [guía de validación de Semana 13](./01-validacion-evidencia-cierre.md).


- Topics verificados, key/partición/offset documentados.
- DTO JSON de entrada y salida con prueba de serialización.
- Listener, servicio de transformación y publicación en topic derivado identificables por archivo y commit.
- Caso válido: entrada → salida observada en UI.
- Caso inválido: motivo y acción explícita; un evento siguiente válido se procesa.
- Manejo de errores descrito y probado: JSON/reglas inválidas van a `compras-rechazadas`; errores técnicos no manejados usan 2 reintentos de 1 s y luego recuperación al mismo topic. No presentar este mecanismo didáctico como DLQ de producción.
- DevLog y capturas sin credenciales.

**Alcance:** puede completarse en más de una sesión; si solo se cubre 3.2.1 y diseño de pipeline, registrar pendiente de 3.2.2, sin reportar implementación completa.
