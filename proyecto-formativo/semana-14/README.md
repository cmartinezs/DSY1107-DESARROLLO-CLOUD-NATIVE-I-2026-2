# RegistrApp · Semana 14

## Estado de entrada

Recibe el estado real de Semana 13. El checkpoint de Semana 13 habilita el diseño de un flujo de eventos para el proyecto, pero **no confirma que Kafka ya esté implementado en RegistrApp**.

Antes de modificar código, inspeccionar el estado vigente del proyecto y registrar qué componentes existen, qué integraciones funcionan y qué deuda permanece. No copiar el laboratorio de compras dentro de RegistrApp.

## Capacidad transferible

Las actividades 3.3.1 y 3.3.2 permiten evaluar cómo administrar un flujo Kafka:

- particiones y capacidad de un consumer group;
- replicación y disponibilidad;
- retención y políticas delete/compact;
- consumer groups y responsabilidades independientes;
- observación y recuperación de lag;
- tuning y medición de carga.

## Decisión de diseño

Identificar si RegistrApp tiene un evento de dominio cuya distribución, retención, replay o consumo independiente aporte valor real. Por ejemplo, un evento de registro o asistencia podría alimentar auditoría o reportería, siempre que la necesidad esté justificada.

No introducir Kafka para reemplazar una operación síncrona simple ni para duplicar RabbitMQ sin motivo.

## Incremento esperado

Si el caso de uso está justificado y existen prerrequisitos:

1. Identificar productor, evento y contrato.
2. Definir al menos dos topics con propósito diferenciado.
3. Justificar particiones, replication factor, retención y cleanup policy.
4. Identificar al menos dos consumidores y sus consumer groups.
5. Definir cómo observar lag.
6. Preparar una operación administrativa reproducible.
7. Implementar y probar el incremento sin alterar flujos existentes.

Si aún no existe un caso de uso válido o no hay tiempo/prerrequisitos, documentar la decisión y consultarla con el docente antes de inventar una funcionalidad. La evidencia de Semana 14 debe corresponder al proyecto semestral real de cada equipo.

## Evidencia obligatoria

- Diagrama antes/después o diseño propuesto con su estado claramente indicado.
- Dos topics y configuración efectiva o, si la implementación aún no está autorizada, diseño justificable pendiente de validación.
- Productores, consumidores y grupos.
- Evidencia de lag cuando exista ejecución.
- Operación administrativa reproducible.
- Pruebas funcionales cuando exista implementación.
- DevLog personal, commits y referencias a archivos.
- Deudas y decisiones pendientes.

## Estado de salida

El estado de salida debe distinguir claramente:

- implementado y probado;
- implementado pero no probado;
- diseñado y pendiente;
- descartado con justificación.

No declarar que RegistrApp integra Kafka, que posee replicación real o que es tolerante a fallos sin evidencia técnica verificable.
