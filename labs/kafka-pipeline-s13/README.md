# Laboratorio · Semana 13 · Pipeline Kafka incremental

**Tipo:** local/autocontenido, extensión formativa sobre un baseline existente. **Estado del repositorio:** incluye una implementación de referencia de `procesador-api`, separación de reglas/listener, pruebas unitarias y una política de 2 reintentos para errores técnicos. **Validación de ejecución en laboratorio físico:** pendiente; no se declara Kafka Streams ni DLQ de producción.

**RA/IL:** RA3 · 3.2.1–3.2.2. **Modalidad:** local, incremental sobre la aplicación de semana 12. **Evidencia final:** [gates de validación y cierre](./01-validacion-evidencia-cierre.md).

## Fuente única de código

La aplicación de partida es [Kafka compras webapp](../kafka-compras-webapp/). Conservar intacto su baseline de semana 12. No duplicar React, Docker Compose ni los microservicios en esta carpeta.

## Secuencia

1. Ejecutar checkpoints 0–5 del [lab B](../kafka-compras-webapp/LAB-WEBAPP-PASO-A-PASO.md).
2. Verificar `compras`, `KafkaTemplate<String,String>` y `@KafkaListener` en el código real.
3. Seguir la [guía de pipeline con contratos, comandos, implementación y pruebas](./LAB-PIPELINE-PASO-A-PASO.md). La referencia funcional vive en [`webapp/procesador-api`](../kafka-compras-webapp/webapp/procesador-api/).
4. Entregar evidencias diferenciando **baseline ejecutado**, **extensión implementada** y **diseño pendiente**.

**Entorno:** Windows 10 + Docker Desktop + PowerShell. No requerir Make, WSL como terminal ni Java/Node locales. Los dos laboratorios de semana 12 tienen brokers independientes; no deben arrancarse simultáneamente en el mismo host por conflicto de puertos.

## Diagnóstico, validación y cierre

Seguir [01 · Gates, evidencias, errores y cierre](./01-validacion-evidencia-cierre.md). Cada gate debe respaldarse con ejecución y capturas: la guía por sí sola no certifica funcionamiento.

## Criterios de salida

Topic entrada/salida; DTO JSON; regla de validación; listener y publisher separados; caso positivo y negativo; offsets inspeccionados; comportamiento ante errores; DevLog. **No marcar el criterio como logrado solo porque está descrito aquí.**
