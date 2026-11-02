# Ejercicios · Semana 13

**Duración sugerida:** 5–20 minutos por ejercicio, práctica no sumativa previa al laboratorio. No requieren crear servicios ni levantar infraestructura adicional.

1. **Contrato (10 min).** Dado `{"id":"c-01","cliente":"Ana","monto":100}`, proponer un DTO Java y dos reglas de validación. ¿Qué ocurre con un campo ausente?
2. **Identificación técnica (10 min).** Inspeccionar el código de `compras-api` y localizar `KafkaTemplate`, DTO, `ObjectMapper`, topic y serializer. Explicar la diferencia entre serialización manual y `JsonSerializer`.
3. **Flujo (10 min).** Dibujar `compras` → procesador → `compras-validas` → consumidor, indicando qué actor tiene cada responsabilidad y qué key conservarías.
4. **Transformación (10 min).** Para montos 200, 1000 y -5, usar regla ALTA si >=1000; inválidos no deben producir salida normal. Indicar salidas esperadas y causa de rechazo.
5. **Diagnóstico (15 min).** Comparar un payload malformado con caída temporal del broker: ¿qué se reintenta, qué se rechaza, qué evidencia queda y qué riesgo de duplicados existe?
6. **Offsets (10 min).** Explicar por qué dos grupos independientes leen el mismo evento y por qué reiniciar un grupo con offsets confirmados no reejecuta toda la historia.

**Evidencias:** DTO/tabla de casos, diagrama y respuesta breve por ejercicio. **No confundir respuestas de diseño con ejecución end-to-end.**

→ [Semana 13](../../semanas/semana-13/) · [Ejemplos](../../examples/semana-13/) · [Laboratorio](../../labs/kafka-pipeline-s13/)
