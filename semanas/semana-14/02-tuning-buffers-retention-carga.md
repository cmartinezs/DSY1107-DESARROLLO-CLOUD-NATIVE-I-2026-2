# 3.3.2 · Tuning, buffers, retención y pruebas de carga

## Objetivo

Ajustar parámetros de productor y retención de topics, ejecutar pruebas controladas y comparar resultados para justificar una configuración.

## 1. ¿Qué significa tuning?

Tuning es ajustar una configuración para un objetivo medible, considerando los recursos y los compromisos involucrados.

No existe una configuración universal de alto rendimiento. Una modificación puede aumentar throughput, pero también subir la latencia, consumir más memoria o exigir más CPU.

Antes de cambiar parámetros, define:

- qué operación estás midiendo;
- cuántos mensajes enviarás;
- tamaño aproximado de cada mensaje;
- cuántas particiones tiene el topic;
- cuántos productores y consumidores participan;
- qué métrica compararás;
- qué configuración mantendrás constante.

## 2. Productor: batching y buffers

Kafka permite agrupar registros antes de enviarlos al broker.

### `batch.size`

Tamaño objetivo máximo de un batch por partición, expresado en bytes. Los registros se agrupan por partición. Un valor mayor puede reducir la cantidad de solicitudes de red cuando existe suficiente tráfico, pero reserva más espacio y no garantiza que todos los batches alcancen ese tamaño.

### `linger.ms`

Tiempo máximo que el productor espera para dar oportunidad a que se acumulen más registros en un batch. Un valor mayor puede mejorar batching y throughput, a cambio de agregar espera a mensajes que podrían enviarse antes.

### `buffer.memory`

Memoria total aproximada que el productor reserva para almacenar registros pendientes de envío. Si el productor produce más rápido de lo que puede transmitir, ese buffer puede llenarse y las llamadas a `send` pueden bloquearse o fallar al superar `max.block.ms`.

### `compression.type`

Permite comprimir los batches antes de enviarlos. Puede reducir tráfico de red y almacenamiento, pero utiliza CPU. La conveniencia depende del tamaño y contenido de los mensajes y de los recursos disponibles.

| Parámetro | Qué controla | Posible beneficio | Costo o riesgo |
|---|---|---|---|
| `batch.size` | Tamaño objetivo de batch por partición | Menos solicitudes y mejor throughput | Más memoria reservada; batches pueden no llenarse. |
| `linger.ms` | Espera para acumular registros | Más batching | Más latencia de publicación. |
| `buffer.memory` | Buffer total del productor | Absorber ráfagas | Mayor consumo de RAM; no corrige un broker saturado. |
| `compression.type` | Compresión de batches | Menos bytes en red/disco | Mayor uso de CPU. |

## 3. Consumidor: lectura por lotes

El consumidor también tiene parámetros de rendimiento. Algunos importantes son:

- `fetch.min.bytes`: cantidad mínima de datos que el broker intenta acumular para responder una lectura.
- `fetch.max.wait.ms`: espera máxima asociada a `fetch.min.bytes`.
- `max.poll.records`: máximo de registros devueltos en una llamada a `poll`.
- `max.partition.fetch.bytes`: límite de datos por partición en una respuesta de fetch.

Aumentar estos valores no garantiza que el consumidor procese más rápido. Si la lógica de negocio es lenta, traer lotes mayores puede aumentar la memoria ocupada o el tiempo de procesamiento. Ajusta primero con una hipótesis y mide.

## 4. Retención también es una decisión de rendimiento y almacenamiento

La retención no es un buffer de productor ni una cola de mensajes pendientes.

- `retention.ms` establece una ventana temporal.
- `retention.bytes` establece un límite de tamaño por partición.
- `cleanup.policy=delete` elimina segmentos antiguos.
- `cleanup.policy=compact` conserva el último valor conocido por clave.

Al configurar tiempo y tamaño, documenta ambos. El límite que se alcance primero puede hacer que Kafka elimine segmentos antiguos. La limpieza es asíncrona y opera sobre segmentos; no esperes que el tamaño del topic cambie exactamente al segundo en que vence un registro.

## 5. Diseñar una prueba de carga

Una prueba útil compara escenarios equivalentes. Si cambias simultáneamente el número de particiones, tamaño de mensajes, cantidad de productores y parámetros de batching, no sabrás qué produjo la diferencia.

### Procedimiento recomendado

1. Define una línea base.
2. Usa el mismo topic o topics equivalentes con el mismo número de particiones.
3. Mantén constante el número y tamaño de registros.
4. Ejecuta la prueba con la configuración inicial.
5. Registra throughput y latencia.
6. Cambia solo los parámetros seleccionados.
7. Repite exactamente la misma carga.
8. Compara y explica los resultados.
9. Repite al menos una vez si el tiempo lo permite para identificar variaciones.

### Métricas

- **Throughput:** registros procesados o publicados por segundo.
- **Latencia:** tiempo que tarda una operación; puede analizarse mediante promedio y percentiles si la herramienta los entrega.
- **Tamaño:** bytes por segundo o tamaño de los registros.
- **Recursos:** CPU y memoria de broker/productor/consumidor.
- **Consumer lag:** registros pendientes de un grupo.

Una mayor tasa de envío no significa automáticamente que toda la aplicación procese más rápido. Diferencia el throughput del productor del throughput de consumo.

## 6. Ejemplo de comparación

La guía institucional propone contrastar una configuración inicial con otra que aumenta el batching y agrega compresión:

| Parámetro | Línea base del ejercicio | Configuración ajustada |
|---|---:|---:|
| `batch.size` | 16 KB | 32 KB |
| `linger.ms` | 0 ms | 20 ms |
| `compression.type` | `none` | `snappy` |
| `buffer.memory` | 32 MiB | 64 MiB |

Estos valores son **escenarios de laboratorio**, no recomendaciones universales para producción. La configuración ajustada puede mejorar el throughput si hay suficiente volumen para llenar batches; también puede aumentar la latencia y la memoria utilizada.

Para que la comparación sea válida, mantén constantes el topic, las particiones, el tamaño y la cantidad de registros, y registra la salida real de la herramienta. No inventes un resultado esperado: el rendimiento depende del equipo y del entorno.

## 7. Relación entre tuning y consumer lag

Supón que un productor genera 2.000 eventos/s y un grupo procesa 1.000 eventos/s. Mientras esa diferencia persista, el lag tenderá a crecer.

Hay distintas respuestas posibles:

- reducir el trabajo innecesario del consumidor;
- optimizar llamadas externas o acceso a base de datos;
- aumentar instancias del grupo, si hay particiones disponibles;
- revisar el número de particiones cuando el diseño lo justifique;
- ajustar batches y fetches;
- controlar la tasa de producción si el sistema no puede absorberla.

**No resuelvas todo aumentando buffers.** Un buffer más grande puede absorber temporalmente una ráfaga, pero no soluciona una diferencia sostenida entre la tasa de entrada y la capacidad de procesamiento.

## 8. Qué debes registrar

Cada prueba debe dejar una tabla similar a esta:

| Campo | Línea base | Ajustada |
|---|---:|---:|
| Registros enviados | | |
| Tamaño de registro | | |
| Particiones | | |
| `batch.size` | | |
| `linger.ms` | | |
| `buffer.memory` | | |
| Compresión | | |
| Tiempo total | | |
| Throughput informado | | |
| Latencia informada | | |
| CPU / memoria (si se midió) | | |
| Observación | | |

Añade una conclusión: ¿qué mejoró?, ¿qué empeoró?, ¿qué parámetro explica razonablemente el cambio?, ¿la evidencia permite afirmar que la aplicación completa mejoró?

## 9. Errores frecuentes

- Cambiar muchos parámetros al mismo tiempo y atribuir el resultado a uno solo.
- Confundir throughput de publicación con throughput de consumo.
- Interpretar lag como segundos.
- Aumentar consumidores cuando no hay particiones suficientes.
- Aumentar particiones sin evaluar claves, orden y distribución.
- Confundir retención con borrado al consumir.
- Afirmar replicación real con RF=1.
- Presentar la duración de una solicitud HTTP como throughput de Kafka sin explicar qué se midió.

## Siguiente paso

Ejecuta el [Kafka Operations Lab](../../labs/kafka-operations-s14/). Sus checkpoints te permitirán configurar dos topics, observar dos grupos, provocar lag y comparar una prueba de carga.
