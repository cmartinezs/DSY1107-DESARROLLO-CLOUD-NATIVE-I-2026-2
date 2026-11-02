# Semana 12 · Streaming, arquitectura de Kafka y procesamiento en tiempo real

**Periodo:** 26 al 31 de octubre de 2026 (confirmado por el docente) · **Unidad:** 3 · **Secciones:** 002D y 003D.

## Estado de evaluación y transición curricular

**Evaluación parcial 2:** aplicada a todos los estudiantes de ambas secciones (002D y 003D), según confirmación docente. No reservar bloques de semana 12 para rendirla nuevamente. La experiencia de aprendizaje 3 se desarrolla entre las semanas 12 y 16; priorizar 3.1.1 y avanzar en 3.1.2 según tiempo y resultados observables. La aplicación de la evaluación no implica que su corrección o publicación de notas esté finalizada.

## Propósito

Partir de lo aprendido con RabbitMQ para comprender Kafka mediante **dos laboratorios separados y autónomos**: (A) fundamentos con Kafka y consola; (B) una **aplicación real ya desarrollada** con React + dos microservicios Spring Boot + Kafka. En el laboratorio B, el estudiante integra mediante **configuración guiada y checkpoints**, sin desarrollar la aplicación desde cero. Ninguno forma parte de RegistrApp.

## Material curricular oficial

- **3.1.1:** Streaming vs colas; arquitectura Kafka: producer, broker, topic, partition, offset, consumer groups, retención y replicación.
- **3.1.2:** Particiones, consumer groups y procesamiento en tiempo real: Kafka Streams, topologías, operaciones stateless/stateful, windowing y state stores.

Los PDF oficiales se mantienen en la biblioteca institucional.

## Complemento de guías AVA

- **3.2.1 · Crear topic, productor y consumidor; offsets y particiones:** se refuerza el [laboratorio A con Kafka UI](../../labs/kafka-fundamentos/04-kafka-ui-guia-ava.md).
- **3.2.2 · Pipeline de eventos del caso end-to-end:** se vincula con el [laboratorio B y el seguimiento visual de eventos](../../labs/kafka-compras-webapp/01-observar-eventos-kafka-ui.md). La detección antifraude de la guía bancaria es un **caso diferente** de nuestra aplicación de compras, no una funcionalidad implementada.

Ambas guías de AVA (2025) usan Kafka/infraestructura anteriores. En el repositorio se mantienen **KRaft + Kafbat UI**, sin ZooKeeper, para Windows 10 + Docker Desktop.

## Resultados observables (propuestos; no reemplazan IL oficiales)

1. Comparar colas RabbitMQ con log de eventos retenidos Kafka.
2. Configurar y ejecutar aplicaciones contenerizadas en Windows 10 + Docker Desktop.
3. Mostrar publicación REST → Kafka y lectura de eventos en Spring Boot, identificando claves, particiones y offsets **también desde Kafka UI**.
4. Diferenciar consumer groups independientes y ejecutar replay para reconstruir el tablero.
5. Diseñar, a nivel conceptual, un pipeline de Kafka Streams con agregaciones por ventanas.

## Organización pedagógica

**A** permite practicar topics/particiones/grupos/replay únicamente con Kafka. **B** permite observar esas mismas ideas en un sistema distribuido (React, REST, productor, Kafka, consumidor). **No son etapas técnicas dependientes**; se puede comenzar por B, aunque A es una buena introducción. Para pasar de uno al otro hay que detener el primero: ambos ocupan el puerto `9092` en Windows, pero sus brokers y datos son distintos.

## Secuencia de clase sugerida

| Etapa | Actividad | Evidencia |
|---|---|---|
| 1 | Comparar RabbitMQ vs Kafka y explicar arquitectura de la demo | Diagrama del flujo |
| 2 | Checkpoints 0–3: Docker, topic, variables de entorno y rutas HTTP | Topic y configuración |
| 3 | Checkpoints 4–6: levantar React y ambos MS, publicar eventos y comparar grupos | Web con particiones y offsets |
| 4 | Checkpoints 7–8: replay y diseño de ventanas | Tablero reconstruido y croquis |

**Adaptación por sección:** **002D (martes 12:11–15:10, cuatro bloques)** dispone de mayor tiempo: contraste conceptual, laboratorio A y, si el entorno responde, avance en laboratorio B y consumer groups. **003D (viernes 12:11–14:20, dos bloques)** debe priorizar 3.1.1, arquitectura, particiones y una prueba funcional simple en laboratorio A; dejar laboratorio B, replay y diseño de ventanas para continuidad. El ritmo real y los checkpoints alcanzados se registran por sección; publicar material no implica haberlo ejecutado.

## Rutas

- **[Laboratorio A · Fundamentos y consola Kafka](../../labs/kafka-fundamentos/)** (contenedor propio).
- **[Laboratorio B · Integración webapp, con checkpoints](../../labs/kafka-compras-webapp/LAB-WEBAPP-PASO-A-PASO.md)** (otro Kafka independiente).
- [Código base React + dos microservicios](../../labs/kafka-compras-webapp/webapp/)
- [Ejemplos](../../examples/semana-12/)
- [Ejercicios](../../ejercicios/semana-12/)
- [Guía docente](./01-guia-docente.md)

## Entorno de ejecución

**Windows 10 + Docker Desktop preinstalado + PowerShell + navegador + editor de texto.** La compilación de React y Spring Boot ocurre dentro de Docker. No requerir instalaciones locales de Make, Java, Maven, Node o Bash; no pedir al estudiante abrir una distribución WSL. Docker Desktop debe estar correctamente configurado previamente por la institución y disponer de acceso a Internet para las descargas de imágenes y dependencias.

## Recordatorio administrativo para el docente

- **AWS:** monitorear consumo de créditos y preparar curso/entorno de respaldo antes de alcanzar **50 USD**, pues puede perderse el acceso.
- **Azure:** vigilar límite de **100 USD**. Si un estudiante se aproxima, reportar **nombre del estudiante, sección y sede** según instrucción de coordinación.
- Estos límites corresponden al aviso de coordinación; no son prerequisitos técnicos para los laboratorios Kafka locales.

## Precauciones conceptuales

- Kafka mantiene orden **por partición**, no entre todas las particiones.
- Los consumer groups tienen offsets independientes; varios miembros de un grupo se reparten particiones.
- El resumen en memoria no es Kafka Streams ni tiene persistencia; se reconstruye con replay.
- Exactly-once de Kafka Streams tiene alcances y configuraciones específicos; **no** garantiza «cobrar exactamente una vez» en servicios externos.
- Un broker con replication-factor=1 **no** es tolerante a caída del broker.
