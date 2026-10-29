# Semana 12 · Streaming, arquitectura de Kafka y procesamiento en tiempo real

**Periodo:** 26 al 31 de octubre de 2026 · **Unidad:** 3 · **Secciones:** 002D y 003D.

## Propósito

Partir de lo aprendido con RabbitMQ para comprender Kafka mediante **dos laboratorios separados y autónomos**: (A) fundamentos con Kafka y consola; (B) una **aplicación real ya desarrollada** con React + dos microservicios Spring Boot + Kafka. En el laboratorio B, el estudiante integra mediante **configuración guiada y checkpoints**, sin desarrollar la aplicación desde cero. Ninguno forma parte de RegistrApp.

## Material curricular oficial

- **3.1.1:** Streaming vs colas; architecture Kafka: producer, broker, topic, partition, offset, consumer groups, retención y replicación.
- **3.1.2:** Particiones, consumer groups y procesamiento en tiempo real: Kafka Streams, topologías, operaciones stateless/stateful, windowing y state stores.

Los PDF oficiales se mantienen en la biblioteca institucional.

## Resultados observables (propuestos; no reemplazan IL oficiales)

1. Comparar colas RabbitMQ con log de eventos retenidos Kafka.
2. Configurar y ejecutar aplicaciones contenerizadas en Windows 10 + Docker Desktop.
3. Mostrar publicación REST → Kafka y lectura de eventos en Spring Boot, identificando claves, particiones y offsets.
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

**Adaptación por sección:** la sección 002D tiene menos bloques; priorizar checkpoints 0–5 y desarrollar grupos/replay en demostración o sesión posterior. La sección 003D puede recorrer también 6–8. Registrar ejecución real por sección: publicar no significa haberlo realizado.

## Rutas

- **[Laboratorio A · Fundamentos y consola Kafka](../../labs/kafka-fundamentos/)** (contenedor propio).
- **[Laboratorio B · Integración webapp, con checkpoints](../../labs/kafka-compras-webapp/LAB-WEBAPP-PASO-A-PASO.md)** (otro Kafka independiente).
- [Código base React + dos microservicios](../../labs/kafka-compras-webapp/webapp/)
- [Ejemplos](../../examples/semana-12/)
- [Ejercicios](../../ejercicios/semana-12/)
- [Guía docente](./01-guia-docente.md)

## Entorno de ejecución

**Windows 10 + Docker Desktop preinstalado + PowerShell + navegador + editor de texto.** La compilación de React y Spring Boot ocurre dentro de Docker. No requerir instalaciones locales de Make, Java, Maven, Node o Bash; no pedir al estudiante abrir una distribución WSL. Docker Desktop debe estar correctamente configurado previamente por la institución y disponer de acceso a Internet para las descargas de imágenes y dependencias.

## Precauciones conceptuales

- Kafka mantiene orden **por partición**, no entre todas las particiones.
- Los consumer groups tienen offsets independientes; varios miembros de un grupo se reparten particiones.
- El resumen en memoria no es Kafka Streams ni tiene persistencia; se reconstruye con replay.
- Exactly-once de Kafka Streams tiene alcances y configuraciones específicos; **no** garantiza «cobrar exactamente una vez» en servicios externos.
- Un broker con replication-factor=1 **no** es tolerante a caída del broker.
