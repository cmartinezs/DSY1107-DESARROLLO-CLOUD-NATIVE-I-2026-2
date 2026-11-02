# procesador-api · Referencia funcional Semana 13

Microservicio Spring Boot sin endpoint HTTP. Consume `compras` con el grupo `procesador-s13`, valida/transforma eventos y publica resultados.

## Flujo

```text
compras
  │
  ├─ válido ──> CompraProcessor ──> compras-validas
  │
  ├─ JSON inválido / regla de negocio ──> compras-rechazadas
  │
  └─ error técnico ──> 2 reintentos (1 s) ──> recuperación en compras-rechazadas
```

## Responsabilidades

- `KafkaPipelineListener`: adaptador de entrada/salida Kafka.
- `CompraProcessor`: validación y transformación sin dependencia Kafka.
- `KafkaErrorConfig`: política explícita para fallos técnicos no manejados.
- `CompraProcessorTest`: reglas de negocio.
- `KafkaPipelineListenerTest`: ruta válida, rechazo de negocio y JSON malformado.

Se conserva `ObjectMapper + StringSerializer/StringDeserializer` para mantener continuidad con Semana 12. **No es Kafka Streams ni usa JsonSerializer.**

## Ejecución

Los topics `compras-validas` y `compras-rechazadas` deben existir antes de iniciar este profile porque el broker tiene deshabilitada la creación automática.

Desde `labs\kafka-compras-webapp`:

```powershell
docker compose --profile app --profile pipeline up -d --build
docker compose --profile app --profile pipeline ps
docker compose --profile pipeline logs --tail=100 procesador-api
```

El profile `app` mantiene el laboratorio de Semana 12; `pipeline` agrega únicamente el procesador de Semana 13.

## Alcances

El mecanismo de recuperación usa el mismo topic `compras-rechazadas` para simplificar el laboratorio. Es una ruta de recuperación explícita, **no una arquitectura DLQ de producción**. La publicación de salida y el commit del offset de entrada no forman una transacción Kafka; discutir duplicados e idempotencia.
