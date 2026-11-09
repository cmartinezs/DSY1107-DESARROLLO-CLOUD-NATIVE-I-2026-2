# Guía docente · Semana 14

## Alcance curricular

Esta semana desarrolla exclusivamente las actividades institucionales:

- **3.3.1:** escalamiento, replicación, retención y consumer lag.
- **3.3.2:** tuning (buffers, retention) y pruebas de carga.
- **Orientación:** Evaluación Parcial 3, Integración de Streaming y Analítica con Kafka.

**RA3 · IL3.3:** escalando y monitoreando sistemas de streaming.

No adelantar el contenido institucional 3.3.3 (manejo de errores en streaming), que corresponde a semana 15. La orientación EP3 presenta el propósito y la preparación del encargo; AVA/Drive conserva la autoridad sobre rúbrica, ponderación y fecha oficial.

## Continuidad y baseline

- Semana 12: Kafka Fundamentals y aplicación React + Spring Boot + Kafka.
- Semana 13: extensión del pipeline con `procesador-api`, grupo `procesador-s13`, topics `compras-validas` y `compras-rechazadas`.
- Semana 14: administrar topics, grupos, retención y carga en el mismo broker de compras; no construir otra aplicación.

La extensión de Semana 13 debe distinguirse del baseline. La ejecución E2E del pipeline requiere validación en el entorno docente; la disponibilidad del código no demuestra que se haya ejecutado en aula.

## Material y entorno

- [Contenido conceptual 3.3.1](./01-escalamiento-replicacion-retencion-consumer-lag.md)
- [Contenido conceptual 3.3.2](./02-tuning-buffers-retention-carga.md)
- [Laboratorio Kafka Operations](../../labs/kafka-operations-s14/)
- [Checkpoint RegistrApp](../../proyecto-formativo/semana-14/)
- [Orientaciones EP3](../../evaluaciones/ev3/instrucciones-encargo.md)

Entorno obligatorio: Windows 10, Docker Desktop, PowerShell y navegador. Se reutiliza el broker del laboratorio B de Semana 12, con Kafbat UI. No pedir instalaciones locales de Java, Maven, Node, Kafka, Bash ni uso de WSL.

## Preparación docente

1. Confirmar Docker Desktop operativo y recursos suficientes.
2. Ejecutar el laboratorio de compras y comprobar que Kafka está saludable.
3. Confirmar que los topics `compras`, `compras-validas` y `compras-rechazadas` existen.
4. Revisar el valor de `KAFKA_GROUP=resumen-s14` en el archivo `.env` del laboratorio de compras.
5. Ejecutar el script de creación/configuración de topics.
6. Validar que los endpoints de `compras-api` respondan en `http://localhost:8081`.
7. Probar una carga pequeña antes de la clase.
8. Mantener la extensión de tres brokers como demostración opcional, no requisito de todos los equipos.

## Secuencia didáctica sugerida

### 3.3.1 · Conceptos y diagnóstico

1. Retomar el topic `compras` y sus tres particiones.
2. Preguntar cuántos consumidores del mismo grupo podrían trabajar en paralelo.
3. Contrastar dos grupos independientes con dos miembros de un mismo grupo.
4. Explicar RF, leader, followers, ISR y `acks`.
5. Revisar retención por tiempo/tamaño y `delete` versus `compact`.
6. Detener temporalmente `resumen-api`, generar eventos y observar lag.
7. Reiniciar el consumidor y observar la recuperación.

### 3.3.2 · Tuning y carga

1. Explicar `batch.size`, `linger.ms`, `buffer.memory` y compresión.
2. Definir una línea base para una carga fija.
3. Ejecutar la prueba de rendimiento del productor.
4. Repetir con los parámetros ajustados.
5. Comparar resultados sin asumir que la segunda configuración siempre será mejor.
6. Relacionar throughput y latencia con lag y capacidad de consumo.

## Adaptación por sección

**002D · martes 10 de noviembre, cuatro bloques.** Se puede completar el recorrido conceptual, los checkpoints de topics y consumer groups, la prueba de lag, una comparación de carga y la orientación EP3. La extensión de tres brokers se utiliza solo si hay recursos y tiempo.

**003D · viernes 13 de noviembre, dos bloques.** Priorizar los cuatro conceptos de 3.3.1, configuración de dos topics, consulta de consumer groups/lag y explicación de la prueba de carga. La comparación baseline/tuned puede quedar como continuidad autónoma si el tiempo de ejecución no alcanza.

No exigir que ambas secciones completen exactamente el mismo número de checkpoints en una sesión. Registrar por separado el avance real.

## Advertencias técnicas que deben explicitarse

- RF=1 no demuestra replicación ni tolerancia a fallos.
- La replicación requiere brokers distintos; tres contenedores en un mismo computador solo demuestran caída de un proceso/broker.
- La retención no depende del consumo y la limpieza de segmentos es asíncrona.
- `retention.bytes` se interpreta por partición.
- Lag es diferencia de offsets, no tiempo.
- Más consumidores que particiones pueden quedar ociosos.
- Una prueba de carga debe mantener constantes las condiciones de comparación.
- No atribuir una mejora de throughput de productor al sistema completo sin medir consumo y lag.

## Criterio de salida de la sesión

El estudiante debe poder presentar la configuración real de dos topics, explicar dos consumer groups, mostrar lag y reproducir una operación administrativa. Si una parte no se ejecutó, se registra como pendiente y no como evidencia lograda.
