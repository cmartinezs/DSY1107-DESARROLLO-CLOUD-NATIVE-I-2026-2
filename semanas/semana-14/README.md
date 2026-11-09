# Semana 14 · Operación y rendimiento de plataformas Kafka

**Periodo:** 9–14 de noviembre de 2026 · **Unidad:** 3 · **RA:** RA3 · **IL:** IL3.3  
**Secciones:** 002D (martes 10, cuatro bloques) y 003D (viernes 13, dos bloques).

## Propósito

En las semanas 12 y 13 aprendiste a reconocer la arquitectura Kafka, crear topics, publicar y consumir eventos e integrar un pipeline. Ahora darás el siguiente paso: **administrar el comportamiento de esa plataforma cuando aumenta la carga y cuando los consumidores no procesan al mismo ritmo que los productores**.

Esta semana se concentra exclusivamente en las actividades institucionales:

- **3.3.1 · Escalamiento, replicación, retención y consumer lag.**
- **3.3.2 · Tuning (buffers, retention) y pruebas de carga.**
- **Orientación de la Evaluación Parcial 3 · Integración de Streaming y Analítica con Kafka.**

No se introduce otra aplicación desde cero. Continuarás sobre los conceptos y el caso de compras trabajados en las semanas 12 y 13.

## Al finalizar debes poder

1. Explicar cómo las particiones limitan el paralelismo de un consumer group.
2. Diferenciar escalamiento, replicación, retención y consumer lag.
3. Explicar RF, leader, followers, ISR y el efecto de `acks` en la confirmación de escrituras.
4. Definir cuánto tiempo y cuánto espacio conserva Kafka los registros, aunque ya hayan sido consumidos.
5. Diferenciar `cleanup.policy=delete` de `cleanup.policy=compact` y elegir según el caso.
6. Configurar al menos dos topics con particiones y políticas de retención justificadas.
7. Utilizar consumer groups para distribuir trabajo o separar responsabilidades.
8. Observar e interpretar el lag desde Kafbat UI o CLI.
9. Comparar una configuración de productor inicial con otra ajustada mediante una prueba repetible.

## Ruta de trabajo

| Orden | Recurso | Para qué sirve |
|---:|---|---|
| 1 | [Escalamiento, replicación, retención y consumer lag](./01-escalamiento-replicacion-retencion-consumer-lag.md) | Estudiar los conceptos base. |
| 2 | [Tuning, buffers y pruebas de carga](./02-tuning-buffers-retention-carga.md) | Comprender qué parámetros se ajustan y cómo medir. |
| 3 | [Ejemplos Semana 14](../../examples/semana-14/) | Observar configuraciones y decisiones pequeñas. |
| 4 | [Ejercicios Semana 14](../../ejercicios/semana-14/) | Comprobar comprensión antes del laboratorio. |
| 5 | [Kafka Operations Lab](../../labs/kafka-operations-s14/) | Configurar topics, generar carga y observar resultados. |
| 6 | [Checkpoint de RegistrApp](../../proyecto-formativo/semana-14/) | Transferir lo aprendido al proyecto sin inventar funcionalidades. |
| 7 | [Orientaciones Parcial 3](../../evaluaciones/ev3/instrucciones-encargo.md) | Preparar la integración que se presentará en semana 16. |

## Evidencia de aprendizaje de esta semana

En el proyecto semestral, cada equipo debe poder mostrar:

- Dos topics configurados y documentados: particiones, factor de replicación y parámetros de retención.
- Al menos dos consumidores con sus consumer groups identificados y responsabilidades explicadas.
- Consumer lag observable en Kafbat UI o mediante CLI.
- Una operación administrativa reproducible mediante comando, script o endpoint.

La configuración debe corresponder al caso de uso real del proyecto. No basta con escribir valores en un documento: se debe demostrar que la configuración existe y explicar por qué se eligió.

> **Importante sobre replicación:** el laboratorio principal reutiliza el broker único del laboratorio de compras, por lo que su replication factor es 1. Eso permite estudiar la configuración, pero **no demuestra tolerancia a fallos ni replicación real**. La extensión opcional de tres brokers permite observar leader, ISR y elección de nuevo leader.

## Continuidad con las semanas anteriores

- **Semana 12:** topics, particiones, offsets, consumer groups, Kafbat UI y replay.
- **Semana 13:** productor, consumidor, contrato JSON, transformación y topics de salida.
- **Semana 14:** administración de topics, capacidad, retención, replicación, lag, buffers y pruebas de carga.
- **Semana 15:** manejo de errores en arquitecturas de streaming.

No vuelvas a construir la webapp ni el pipeline. Reutiliza el laboratorio existente y amplía su operación.

## Entorno

Windows 10, Docker Desktop, PowerShell y navegador. Los comandos Kafka se ejecutan dentro del contenedor mediante `docker exec`; no necesitas instalar Java, Maven, Node, Kafka, Bash ni abrir WSL.

**Regla:** si una prueba no se ejecutó, se documenta como pendiente. No presentes una captura de configuración como evidencia de rendimiento, ni un RF=1 como alta disponibilidad.
