# Guía visual con Kafka UI · Complemento AVA 3.2.1

**Este documento complementa la guía institucional «3.2.1 Crear topic, productor y consumidor; offsets y particiones» (Duoc UC, 2025)**. No sustituye su obligatoriedad en AVA. Usa **Windows 10, Docker Desktop y navegador**. No necesita ZooKeeper, terminal Linux, Maven ni un proyecto Spring Boot nuevo.

## Antes: ¿qué estás mirando?

Piensa en Kafka como un **registro de eventos**. El **topic** es el nombre del registro, por ejemplo `compras`. Cada **partición** es una secuencia ordenada independiente; sus mensajes tienen un **offset** que comienza en cero y avanza dentro de esa partición. El **producer** escribe; el **consumer** lee. Un **consumer group** almacena el progreso de lectura y permite distribuir particiones entre sus miembros.

- **Kafka** es el servidor que almacena los eventos.
- **Kafka UI (Kafbat UI)** permite inspeccionarlos desde el navegador; no reemplaza al broker.
- **KRaft** administra los metadatos en versiones actuales de Kafka sin requerir el servicio adicional ZooKeeper que aparece en la guía AVA 2025.
- **CLI** son comandos para hacer lo mismo sin interfaz gráfica.

## Checkpoint visual 1 · Iniciar y entrar

1. Abre Docker Desktop.
2. Entra a `labs\kafka-fundamentos` y abre PowerShell allí.
3. Ejecuta `docker compose up -d`, seguido de `docker compose ps`.
4. Abre **http://localhost:8083**. Debe aparecer un clúster llamado `fundamentos`. Si aún aparece iniciando, espera un poco y vuelve a abrirlo.

**¿Por qué usamos 8083 y no 8080 como AVA?** Porque reservamos puertos conocidos y evitamos conflictos con otras herramientas del laboratorio; el puerto **interno** de Kafka UI sigue siendo 8080.

## Checkpoint visual 2 · Identificar topic y particiones

1. Abre **Topics**.
2. Si todavía no existe `compras`, usa la acción **Add a Topic** (el texto puede variar entre versiones).
3. Completa nombre **`compras`**, **3 particiones** y **replication factor 1**.
4. Confirma y abre el detalle del topic. Si `compras` ya existe por el laboratorio 01, **no lo vuelvas a crear**.

**¿Qué observar?** Particiones 0, 1 y 2; cada una tiene su propia secuencia. Un broker único permite factor 1, pero **no replica los datos a un segundo broker**.

## Checkpoint visual 3 · Publicar eventos con clave

1. Abre una ventana PowerShell. Inicia productor:

```powershell
docker exec -it dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic compras --property parse.key=true --property key.separator=:
```

2. Escribe una línea por vez:

```text
orden-555:Paso1-Creada
orden-555:Paso2-Pagada
orden-555:Paso3-Enviada
```

3. Abre **Topics → compras → Messages** en Kafka UI. Examina **Key, Value, Partition, Offset**.

**¿Por qué usamos una key común?** Kafka calcula la partición a partir de la clave, con la estrategia normal de particionado; así los eventos de una misma orden llegan a la **misma partición** mientras se mantengan esas condiciones. En esa partición conservan su orden. Los offsets **no necesitan** ser 0, 1, 2 porque puede haber eventos anteriores.

**Checkpoint:** registra la partición real y los tres offsets de `orden-555`. **No inventes** que será P1: depende de la clave y del particionador.

## Checkpoint visual 4 · Publicar eventos sin clave

1. Detén el productor anterior con **Ctrl+C**.
2. Desde otra PowerShell, inicia productor **sin** `parse.key=true`:

```powershell
docker exec -it dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic compras
```

3. Envía al menos **12 líneas** una a una: `Mensaje-1`, `Mensaje-2` ... `Mensaje-12`.
4. Inspecciona los mensajes en Kafka UI, fijándote en la columna **Key** (vacía / null) y la partición.

**Importante para interpretar AVA:** las versiones actuales de Kafka **no garantizan** que tres mensajes sin clave se repartan exactamente P0→P1→P2. La estrategia predeterminada puede agrupar lotes en la misma partición para mejorar rendimiento (*sticky batching*). **No confundas** «sin key» con una secuencia obligatoria round-robin. Tampoco se denomina sticky a la asociación de una **key** con una partición.

**Checkpoint:** documenta las particiones realmente observadas y explica por qué los resultados pueden diferir del ejemplo ilustrativo de AVA.

## Checkpoint visual 5 · Ver consumer groups y offsets

1. Completa [Laboratorio 02](./02-consumer-groups.md).
2. Abre Kafka UI → **Consumer Groups**.
3. Busca `analytics` y `notificaciones`.
4. Identifica miembros, particiones, offsets confirmados y lag, según lo que muestre la interfaz.
5. Detén los consumidores de `analytics`, reinicia sus offsets con el comando de la práctica y comprueba el replay.

**Regla clave:** si detienes y vuelves a iniciar un consumer group cuyos offsets ya fueron confirmados, continúa desde su avance registrado; **no** significa que se borren los mensajes. Para releer desde el inicio debes cambiar de grupo o restablecer offsets cuando esté inactivo.

## Errores frecuentes

| ¿Qué ves? | ¿Qué comprobar? |
|---|---|
| Kafka UI no abre | `docker compose ps` debe mostrar `kafka` y `kafka-ui`; comprueba http://localhost:8083 |
| Kafka UI abre pero el clúster no responde | Espera el arranque del broker; Kafka UI se conecta a `kafka:9094`, no a `localhost:9092` |
| No aparece `compras` | Confirma la creación del topic y refresca |
| No encuentras mensajes | Selecciona el topic correcto, revisa filtros, partición y rango de offsets |
| El grupo no relee desde cero | Tiene offsets confirmados y debes resetearlos con todos los miembros detenidos |
| Eventos «sin key» no rotan P0/P1/P2 | Comportamiento posible y válido: no se exige round-robin exacto |

## Relación con la guía AVA

La guía institucional usa tres servicios (ZooKeeper + Kafka Confluent + Provectus Kafka UI), topic `tutorial-particiones`, puerto UI 8080 y plantea un proyecto Spring Boot escrito desde cero. **Esta versión conserva el aprendizaje** pero usa Kafka KRaft, Kafbat UI y topic `compras` para reducir complejidad operativa. El código Spring Boot de una aplicación real se encuentra por separado en [Laboratorio B](../kafka-compras-webapp/).

**Fuentes técnicas para docentes:** [Kafka 4.1 Docker](https://kafka.apache.org/41/getting-started/docker/), [Kafbat UI](https://github.com/kafbat/kafka-ui) y [particionador de Kafka](https://github.com/apache/kafka/blob/trunk/config/producer.properties).
