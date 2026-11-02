# Ejemplo 03 · Error de negocio versus error de infraestructura

**Caso A:** `{"id":"c-1","cliente":"","monto":-2}`. JSON válido; contrato de negocio inválido. Debe rechazarse de forma controlada, dejando evidencia y sin ciclos infinitos de retry.

**Caso B:** falla temporal de conectividad con Kafka al publicar el evento derivado. No se debe declarar éxito ni confirmar automáticamente el avance sin una política explícita de entrega.

**Caso C:** JSON sintácticamente incorrecto. La deserialización falla antes de evaluar reglas; necesita tratamiento de error y observabilidad distinto al caso A.

**Ejercicio oral:** explicar cuándo reintentar, cuándo rechazar y por qué reintentos pueden producir duplicados. Una DLQ es **solo propuesta** hasta implementarse y verificarse.
