# 01 · Validación, evidencias y cierre · Semana 13

## Estado y prerrequisitos

Este lab **todavía no tiene procesador implementado**. Su guía especifica implementación incremental sobre [el laboratorio B](../kafka-compras-webapp/). No se considera completo por crear topics. Antes de comenzar, lograr checkpoint 5 de [Semana 12 B](../kafka-compras-webapp/LAB-WEBAPP-PASO-A-PASO.md).

## Gates

| Gate | Resultado y evidencia |
|---|---|
| 0 · Base | Cinco servicios y compra visible en web, topic `compras` en Kafka UI |
| 1 · Contratos | DTO de entrada y salida; JSON real, reglas para valores faltantes/inválidos |
| 2 · Infraestructura | `compras-validas` y `compras-rechazadas` creados, particiones inspeccionadas |
| 3 · Código | Listener distinto de `resumen-app`, servicio de reglas y productor del topic derivado; archivos y commits |
| 4 · Positivo | Evento válido → JSON transformado observable en topic de salida, mismo ID origen |
| 5 · Negativo | JSON válido con negocio inválido → rechazo documentado y evento válido posterior procesado |
| 6 · Fallas | Falla de publicación y error de parseo tienen política explícita: retry acotado/recuperación; comprobar ausencia de bloqueo indefinido |
| 7 · Entrega | DevLog, capturas, comandos, resultados y limitaciones reales |

## Distinciones obligatorias

- `@Valid` de un controller HTTP no protege contra mensajes publicados directamente en Kafka.
- `ObjectMapper` + `StringSerializer` no es `JsonSerializer` de Kafka.
- Un listener Spring Kafka no es un pipeline Kafka Streams.
- Topic `compras-rechazadas` no equivale automáticamente a DLQ operativa con reintentos y recovery.
- Duplicación posible entre publicación a topic de salida y confirmación de offset; no prometer exactly-once.

## Diagnóstico

Si el topic de salida está vacío: comprobar productor de entrada, grupo del procesador, logs, tópico/serialización y offsets antes de resetear. Si un mensaje defectuoso bloquea la partición: detener la prueba y analizar error/retry; no eliminar datos para ocultar el fallo. Si el broker cae: recopilar logs y recuperar desde el último gate verde. Los datos no están persistidos al hacer `docker compose down`.

## Cierre

Detener servicios con los comandos **del laboratorio B** desde `labs\kafka-compras-webapp`; tomar evidencias primero. Documentar si se completó solo diseño, creación de topics, implementación o pruebas. Nunca presentar la extensión como funcional sin evidencia de ejecución.
