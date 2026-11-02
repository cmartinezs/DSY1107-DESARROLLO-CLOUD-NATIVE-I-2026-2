# Laboratorio · Semana 13 · Pipeline Kafka incremental

**Tipo:** local/autocontenido, extensión formativa **pendiente de implementar** sobre un baseline existente. **Estado:** guía disponible; no se declara un tercer microservicio compilado, DLQ, retries ni Kafka Streams ejecutables.

## Fuente única de código

La aplicación de partida es [Kafka compras webapp](../kafka-compras-webapp/). Conservar intacto su baseline de semana 12. No duplicar React, Docker Compose ni los microservicios en esta carpeta.

## Secuencia

1. Ejecutar checkpoints 0–5 del [lab B](../kafka-compras-webapp/LAB-WEBAPP-PASO-A-PASO.md).
2. Verificar `compras`, `KafkaTemplate<String,String>` y `@KafkaListener` en el código real.
3. Seguir la [guía de pipeline con contratos, comandos, implementación propuesta y pruebas](./LAB-PIPELINE-PASO-A-PASO.md).
4. Entregar evidencias diferenciando **baseline ejecutado**, **extensión implementada** y **diseño pendiente**.

**Entorno:** Windows 10 + Docker Desktop + PowerShell. No requerir Make, WSL como terminal ni Java/Node locales. Los dos laboratorios de semana 12 tienen brokers independientes; no deben arrancarse simultáneamente en el mismo host por conflicto de puertos.

## Criterios de salida

Topic entrada/salida; DTO JSON; regla de validación; listener y publisher separados; caso positivo y negativo; offsets inspeccionados; comportamiento ante errores; DevLog. **No marcar el criterio como logrado solo porque está descrito aquí.**
