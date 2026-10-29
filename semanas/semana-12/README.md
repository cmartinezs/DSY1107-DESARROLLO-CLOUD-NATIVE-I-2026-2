# Semana 12 · Streaming, arquitectura de Kafka y procesamiento en tiempo real

**Periodo:** 26 al 31 de octubre de 2026 · **Unidad:** 3 · **Secciones:** 002D y 003D.

## Propósito
Transitar desde RabbitMQ (mensajería asíncrona) hacia el log de eventos distribuido de Apache Kafka. Explicar conceptos con ejemplos aislados, practicar con Docker y luego evaluar su transferencia a RegistrApp; esta última no forma parte del laboratorio conceptual.

## Material curricular oficial
- **3.1.1:** Streaming vs colas; arquitectura Kafka: producer, broker, topic, partition, offset, consumer groups, retención y replicación.
- **3.1.2:** Particiones, consumer groups y procesamiento en tiempo real: Kafka Streams, topologías, operaciones stateless/stateful, windowing y state stores.
Los PDF originales se conservan en la biblioteca institucional, no se duplican aquí.

## Resultados observables (propuestos; no reemplazan IL oficiales)
1. Comparar cola de trabajo y log distribuido retenido.
2. Interpretar claves, offsets, orden por partición, grupos y paralelismo.
3. Crear un topic, producir/consumir eventos y demostrar lectura independiente.
4. Diseñar una topología Kafka Streams para agregación por ventanas.

## Secuencia en cuatro bloques
| Bloque | Concepto → actividad → evidencia |
|---|---|
| 1 | RabbitMQ vs Kafka → discusión de necesidades de replay y distribución → matriz de decisión |
| 2 | Topics, keys, partitions y offsets → laboratorio 01 → registros de partición/offset |
| 3 | Consumer groups y replay → laboratorio 02 → evidencia de asignación y relectura |
| 4 | Streams, estado y ventanas → diseño guiado → topología y cálculos por intervalo |

## Entorno del laboratorio práctico
**Windows 10 + Docker Desktop + PowerShell** (también sirve CMD). No se solicita instalar Make, Java, Linux ni ejecutar comandos desde WSL. Docker Desktop debe estar configurado previamente por el laboratorio para usar contenedores Linux; los alumnos solo ejecutan `docker compose` y `docker exec` desde Windows.

## Rutas
- [Ejemplos](../../examples/semana-12/)
- [Ejercicios](../../ejercicios/semana-12/)
- [Laboratorio local Kafka — instrucciones para Windows](../../labs/kafka-streaming/)
- [Guía docente](./01-guia-docente.md)

## Checkpoint
La semana define contenido planificado. La ejecución efectiva y el punto de avance se registran por separado para 002D y 003D; no suponer ejecución por la sola publicación del material.

## Precauciones conceptuales
- RabbitMQ no garantiza exactamente una vez por defecto: duplicados e idempotencia importan.
- Kafka mantiene orden **dentro** de la partición, no global.
- Exactly-once en Kafka Streams requiere configuración y alcance definido; no implica efectos externos únicos.
- En broker local único con replicación 1 **no** hay tolerancia demostrada a caída de broker.
