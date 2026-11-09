# 3.3.1 · Escalamiento, replicación, retención y consumer lag

## Objetivo

Comprender cómo Kafka distribuye el procesamiento, protege los datos ante fallos, conserva los eventos y permite detectar consumidores atrasados.

## 1. El problema operativo

En la semana 13 construiste un flujo que publica compras y las procesa mediante consumidores. Mientras el volumen es bajo, puede parecer suficiente dejar la configuración por defecto. En producción, sin embargo, la tasa de eventos, la capacidad de procesamiento, la disponibilidad y el almacenamiento cambian.

Imagina que una tienda recibe 10.000 eventos por segundo y sus consumidores procesan 1.000 por segundo. Los eventos pendientes se acumulan. Aumentar consumidores sin revisar las particiones no necesariamente resuelve el problema; aumentar particiones tampoco protege los datos si el broker falla.

Kafka aborda problemas distintos mediante mecanismos diferentes:

| Mecanismo | Pregunta que responde | Propósito |
|---|---|---|
| Escalamiento | ¿Cómo proceso más eventos en paralelo? | Capacidad y throughput. |
| Replicación | ¿Qué ocurre si un broker deja de funcionar? | Disponibilidad y durabilidad. |
| Retención | ¿Durante cuánto tiempo conservo los eventos? | Ciclo de vida y almacenamiento. |
| Consumer lag | ¿Cuánto trabajo tiene pendiente un grupo? | Observabilidad y diagnóstico. |

## 2. Escalamiento: particiones y paralelismo

Un topic se divide en particiones. Cada partición es un log ordenado e independiente, con sus propios offsets. Kafka garantiza orden dentro de una partición, no un orden global entre todas las particiones.

En un consumer group, una partición puede estar asignada a un solo consumidor del grupo a la vez. Por eso, **el máximo paralelismo útil de un grupo sobre un topic está limitado por el número de particiones**.

Ejemplo con un topic de tres particiones:

| Consumidores en el mismo grupo | Resultado posible |
|---:|---|
| 1 | Un consumidor lee las tres particiones. |
| 2 | Las tres particiones se distribuyen entre dos consumidores. |
| 3 | Cada consumidor puede recibir una partición. |
| 4 | Uno de los consumidores queda sin particiones asignadas. |

Más consumidores no garantizan una mejora lineal. La capacidad también depende del broker, la red, el tamaño de los mensajes, la lógica del consumidor y los recursos disponibles.

### Escalamiento vertical y horizontal

- **Vertical:** asignar más CPU, memoria o capacidad al mismo proceso/servidor.
- **Horizontal:** agregar instancias de consumidores o brokers y distribuir el trabajo.

En Kafka, aumentar el número de consumidores de un mismo grupo solo aporta paralelismo si hay particiones disponibles. Agregar brokers permite distribuir particiones y réplicas entre más servidores, pero no aumenta automáticamente el número de particiones de un topic existente.

### Ejemplo

Un topic tiene 3 particiones y el grupo `analytics` tiene 5 instancias. Como máximo 3 instancias podrán consumir simultáneamente ese topic dentro de ese grupo. Si se necesitan 5 consumidores activos, se debe evaluar aumentar las particiones y revisar las consecuencias sobre claves, orden y recursos.

> **No confundas:** dos consumer groups diferentes pueden leer el mismo topic de forma independiente. La limitación de una instancia por partición aplica a los miembros de un mismo grupo.

## 3. Replicación: disponibilidad y durabilidad

Kafka puede mantener varias copias de cada partición en brokers distintos.

- **Replication factor (RF):** número de réplicas de cada partición.
- **Leader:** réplica que atiende las operaciones de lectura y escritura de la partición.
- **Follower:** réplica que sigue al leader y replica sus registros.
- **ISR (In-Sync Replicas):** conjunto de réplicas que se mantienen suficientemente sincronizadas con el leader.

Ejemplos:

- **RF=1:** existe una sola copia. Si el broker que la contiene falla, no hay otra réplica desde la cual continuar.
- **RF=3:** existen tres copias de cada partición, distribuidas entre brokers distintos. Si una réplica falla y las restantes cumplen las condiciones, Kafka puede elegir un nuevo leader entre las réplicas sincronizadas.

### Confirmación de escrituras: `acks`

El productor decide qué confirmación espera:

| Configuración | Comportamiento | Compromiso |
|---|---|---|
| `acks=0` | No espera confirmación del broker. | Menor espera, mayor riesgo de pérdida no detectada. |
| `acks=1` | Espera confirmación del leader. | No espera a todas las réplicas. |
| `acks=all` | Espera confirmación de las réplicas que están en ISR. | Mayor protección, con posible costo de latencia. |

`acks=all` debe analizarse junto con `min.insync.replicas`. Por ejemplo, RF=3 y `min.insync.replicas=2` requieren que existan al menos dos réplicas sincronizadas para aceptar escrituras con esa política. Esto no significa que cualquier configuración evite toda pérdida de datos: también importan la disponibilidad, la elección de leader y la configuración del productor.

### Límite del laboratorio principal

El laboratorio de compras usa un único broker KRaft. Por eso:

- sus topics se crean con RF=1;
- no se puede crear un topic con RF=3 en ese broker;
- no es posible demostrar elección de un nuevo leader ante caída de un broker.

La extensión opcional de tres brokers permite observar replicación real entre procesos Kafka. Al ejecutarla en un solo computador, demuestra caída de un contenedor/broker, **no** tolerancia a la pérdida física del computador completo.

## 4. Retención: conservar eventos después del consumo

Kafka no elimina un registro solo porque un consumidor lo haya leído. La retención define cuánto tiempo o cuánto espacio ocupa el log antes de que sus segmentos puedan eliminarse.

Los dos límites más utilizados son:

- `retention.ms`: tiempo de conservación.
- `retention.bytes`: límite de almacenamiento por partición.

Cuando se configuran ambos, el límite que se alcance primero puede provocar la eliminación de segmentos antiguos. La eliminación no ocurre necesariamente en el instante exacto en que se cumple el límite: Kafka limpia segmentos completos de forma periódica.

### Ejemplo

Un topic puede conservar eventos durante siete días o hasta alcanzar 100 MB por partición. Si alcanza antes el límite de tamaño, comenzará a eliminar segmentos antiguos aunque todavía no hayan transcurrido siete días.

La retención debe responder al uso de los datos:

- auditoría y trazabilidad: conservar una ventana suficiente para investigar;
- analítica: conservar el historial requerido para recalcular indicadores;
- integración entre servicios: permitir que un consumidor temporalmente detenido recupere eventos mientras sigan retenidos.

No configures retención solo para reducir disco: una ventana demasiado corta puede impedir replay o reconstrucción.

## 5. Políticas de limpieza: delete y compact

La propiedad `cleanup.policy` define cómo se limpia el log.

### `delete`

Elimina segmentos antiguos según las políticas de tiempo o tamaño. Es apropiada cuando interesa conservar una ventana de eventos:

- compras realizadas;
- telemetría;
- logs;
- eventos de auditoría con plazo de conservación definido.

### `compact`

Conserva el último valor conocido para cada clave, mediante compactación asíncrona. Es útil para representar el estado más reciente de una entidad:

- configuración por identificador;
- perfil de usuario;
- estado actual de una cuenta.

Ejemplo de clave `cliente-7`:

| Orden | Key | Value |
|---:|---|---|
| 1 | cliente-7 | plan=basico |
| 2 | cliente-7 | plan=pro |
| 3 | cliente-7 | plan=empresa |

Después de la compactación, Kafka puede conservar el valor más reciente para esa clave. La compactación no es una eliminación inmediata ni una garantía de que el log contenga solo una entrada por clave en todo momento.

Un registro tombstone (valor nulo) permite indicar que una clave debe eliminarse del estado compactado; el borrado definitivo también depende del proceso de limpieza.

| Necesidad | Política recomendada |
|---|---|
| Conservar historial de compras durante una ventana | `delete` |
| Mantener el último estado de cada cliente | `compact` |
| Mantener estado actual y además limitar antigüedad | Evaluar `compact,delete` y sus consecuencias |

**No uses compactación para reemplazar un historial de auditoría** si necesitas conservar todos los cambios.

## 6. Consumer lag: cuánto trabajo está pendiente

El consumer lag expresa la diferencia entre el final del log y el offset confirmado por un grupo, normalmente calculada por partición:

```text
Lag = LogEndOffset - CurrentOffset
```

- **Current offset:** posición confirmada del grupo.
- **Log end offset:** siguiente posición disponible al final del log.
- **Lag:** registros que el grupo aún no ha alcanzado, según sus offsets.

Ejemplo:

| Partición | Current offset | Log end offset | Lag |
|---:|---:|---:|---:|
| 0 | 120 | 150 | 30 |
| 1 | 80 | 100 | 20 |
| 2 | 200 | 200 | 0 |
| **Total** | | | **50** |

Interpretación:

- lag cercano a cero y estable: el grupo sigue el ritmo de producción;
- lag creciente: el productor está generando eventos más rápido que el grupo los procesa;
- lag alto y decreciente: el grupo está recuperando trabajo pendiente;
- lag alto y sin avance: revisar si los consumidores están detenidos, bloqueados o fallando.

**Lag es una cantidad de registros, no una medida directa de segundos.** Para estimar retraso temporal también se necesita observar timestamps y la tasa de llegada/procesamiento.

### Causas frecuentes y diagnóstico

| Causa | Qué revisar |
|---|---|
| Procesamiento lento | Tiempo de negocio, llamadas externas, CPU y espera de I/O. |
| Pocas particiones | Número de particiones frente a consumidores del grupo. |
| Consumidor detenido | Estado de instancias y logs. |
| Rebalances frecuentes | Reinicios, tiempos de sesión y procesamiento que excede los límites. |
| Ráfaga de producción | Throughput de entrada frente a throughput de salida. |

### Consultar lag por CLI

Con el laboratorio de compras activo, ejecuta en PowerShell:

```powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group resumen-s14
```

Busca las columnas `CURRENT-OFFSET`, `LOG-END-OFFSET` y `LAG`. También puedes abrir Kafbat UI en **http://localhost:8083 → Consumer Groups**.

## 7. Lo que debes recordar

1. Las particiones determinan el paralelismo máximo de un consumer group.
2. Replicar protege frente a la caída de un broker; particionar distribuye el trabajo. Son objetivos distintos.
3. RF=1 no proporciona tolerancia a fallos.
4. Kafka conserva eventos según la política de retención, no según si fueron consumidos.
5. `delete` conserva una ventana de historial; `compact` conserva el último valor por clave.
6. Lag mide registros pendientes según offsets, no segundos.
7. Antes de escalar, identifica si el cuello de botella está en producción, broker, red o consumo.

## Siguiente paso

Continúa con [3.3.2 · Tuning, buffers y pruebas de carga](./02-tuning-buffers-retention-carga.md) y después realiza el [Kafka Operations Lab](../../labs/kafka-operations-s14/).
