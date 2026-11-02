# RegistrApp · Semana 13

## Estado
Checkpoint curricular habilitado para **3.2.1 (topic, productor, consumidor, offsets, particiones)** y **3.2.2 (pipeline end-to-end)**, según la planificación [Semana 13](../../semanas/semana-13/README.md). **No implica que los estudiantes ya implementaron Kafka en RegistrApp.**

## Estado de entrada
Recibe todo el estado efectivo de Semana 12 y sus deudas explícitas. Registrar qué componentes realmente existen antes de proponer una integración.

## Decisión de transferencia
El trabajo principal se realiza en los laboratorios de Kafka. **No duplicar automáticamente en RegistrApp un broker, una SPA o un pipeline** por el solo hecho de aparecer en el programa. Proponer, **solo cuando la clase haya cubierto el patrón**, un evento de dominio de asistencia/registro que podría alimentar auditoría o notificaciones de forma asíncrona. Antes de codificar: identificar el evento, productores, consumidores, contrato JSON, política ante fallos y motivo de usar Kafka en lugar de una cola.

## Incremento condicionado
1. Presentar el diseño de un flujo mínimo de eventos de RegistrApp, si corresponde.
2. Separar una operación transaccional principal de las reacciones secundarias.
3. Evaluar consecuencias de duplicación, replay, orden por partición y error de datos.
4. Codificar **solo** si existen prerrequisitos, tiempo, valor pedagógico y evidencia funcional; registrar el incremento por commit.

## Evidencia obligatoria
- Comparación antes/después (aunque sea sin cambios de código).
- Evento y consumidores propuestos o implementados, con justificación Kafka vs RabbitMQ.
- Artefactos modificados y decisiones técnicas.
- Pruebas observables cuando exista implementación; no confundir propuesta con ejecución.
- DevLog, deudas, referencias a commits/archivos.

## Estado de salida
El estado realmente alcanzado alimenta Semana 14, sin reiniciar ni declarar avances supuestos.
