# Laboratorio Semana 14 · Operación y rendimiento de Kafka

**Objetivo:** configurar y administrar topics, observar consumer groups y lag, generar una carga controlada y comparar parámetros del productor.

**Duración orientativa:** 60–100 minutos, según equipo y ritmo.  
**Entorno:** Windows 10 + Docker Desktop + PowerShell + navegador.  
**Punto de partida:** aplicación de compras y pipeline de semanas 12 y 13.

> No desarrolles una aplicación nueva. Reutiliza el laboratorio B y el procesador existente. Este laboratorio agrega tareas de operación y medición.

## Antes de empezar

1. Actualiza tu copia del repositorio del curso con **git pull**.
2. Comprueba que Docker Desktop está iniciado.
3. Si el laboratorio A de fundamentos está activo, deténlo desde su propia carpeta con **docker compose down**. No ejecutes dos brokers locales que compitan por el puerto 9092.
4. Abre una PowerShell en la carpeta **labs\kafka-operations-s14**. Los scripts de esta guía se ejecutan desde esa ubicación, salvo cuando se indique cambiar de carpeta.

Comprueba:

~~~powershell
docker --version
docker compose version
~~~

**Checkpoint 0:** ambos comandos muestran una versión y Docker está operativo.

## 1. Iniciar la aplicación de compras de semanas 12 y 13

Abre otra PowerShell y cambia a:

~~~powershell
cd ..\kafka-compras-webapp
~~~

Si aún no tienes un archivo **.env**, créalo desde la plantilla:

~~~powershell
Copy-Item .env.example .env
notepad .env
~~~

Completa o revisa los valores:

~~~dotenv
KAFKA_BROKER=kafka:9094
KAFKA_TOPIC=compras
KAFKA_GROUP=resumen-s14
WEB_PORT=5173
~~~

El grupo **resumen-s14** identifica al consumidor de resumen en esta práctica. Si ya tenías otro valor de la semana 12, actualízalo antes de iniciar los servicios.

Levanta la aplicación y el procesador de semana 13:

~~~powershell
docker compose --profile app --profile pipeline up -d --build
docker compose --profile app --profile pipeline ps
~~~

Espera a que los servicios estén iniciados. Comprueba:

- Web: http://localhost:5173
- Kafka UI: http://localhost:8083
- API de compras: http://localhost:8081/api/compras/salud
- API de resumen: http://localhost:8082/api/resumen/salud

**Checkpoint 1:** Kafka, Kafbat UI, compras-api, resumen-api, procesador-api y web están disponibles.

Si cambiaste el grupo en el archivo **.env** después de haber iniciado los contenedores, recrea los servicios para que reciban la nueva variable:

~~~powershell
docker compose --profile app --profile pipeline up -d --force-recreate resumen-api
~~~

## 2. Crear y configurar topics

Vuelve a la PowerShell ubicada en **labs\kafka-operations-s14**.

Ejecuta el script:

~~~powershell
.\scripts\crear-topics.ps1
~~~

Si Windows bloquea la ejecución de scripts, utiliza:

~~~powershell
powershell -ExecutionPolicy Bypass -File .\scripts\crear-topics.ps1
~~~

El script crea si faltan y configura:

| Topic | Particiones | RF | Retención temporal | Límite por partición | Política |
|---|---:|---:|---:|---:|---|
| compras | 3 | 1 | 7 días | 100 MiB | delete |
| compras-validas | 3 | 1 | 3 días | 50 MiB | delete |
| compras-rechazadas | 3 | 1 | 7 días | 50 MiB | delete |
| rendimiento-base-s14 | 3 | 1 | 1 hora | 100 MiB | delete |
| rendimiento-tuned-s14 | 3 | 1 | 1 hora | 100 MiB | delete |

Los valores son parámetros didácticos para el laboratorio. En un sistema real, los límites deben justificarse según volumen, auditoría, replay, almacenamiento y requisitos del negocio.

El script también muestra la descripción de los topics y sus configuraciones efectivas.

**Checkpoint 2:** guarda la salida del script y verifica en http://localhost:8083 → Topics que aparecen los topics configurados.

### Inspección manual de un topic

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic compras
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics --entity-name compras --describe
~~~

Debes identificar:

- número de particiones;
- replication factor;
- leader y réplicas;
- retention.ms;
- retention.bytes;
- cleanup.policy.

**Importante:** el broker obligatorio tiene una sola instancia. RF=1 es la única opción válida en este entorno. No significa que el topic esté replicado ni que tolere la caída del broker.

## 3. Identificar los consumer groups

Publica una compra desde la web o ejecuta:

~~~powershell
.\scripts\generar-compras.ps1 -Count 5
~~~

Consulta los grupos:

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
~~~

Debes identificar al menos:

- **resumen-s14:** grupo del servicio resumen-api, que calcula indicadores.
- **procesador-s13:** grupo del procesador-api, que valida y publica eventos derivados.

Ambos consumen el topic **compras**, pero son grupos independientes: mantienen offsets propios y cada uno recibe los eventos. El procesador no le quita mensajes al resumen.

Consulta cada grupo:

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group resumen-s14
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group procesador-s13
~~~

**Checkpoint 3:** identifica los dos grupos, el topic que leen y la responsabilidad de cada consumidor. Guarda una captura de Kafbat UI → Consumer Groups.

## 4. Observar escalamiento dentro de un grupo

Esta prueba utiliza consumidores de consola para aislar el reparto de particiones.

1. Abre dos ventanas nuevas de PowerShell.
2. En ambas ejecuta el mismo comando:

~~~powershell
docker exec -it dsy1107-kafka-s12 /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic compras --group escalamiento-s14 --consumer-property auto.offset.reset=latest --property print.key=true --property print.partition=true --property print.offset=true
~~~

3. Deja ambos consumidores activos.
4. Desde la PowerShell principal genera eventos nuevos:

~~~powershell
.\scripts\generar-compras.ps1 -Count 30
~~~

5. Observa qué eventos aparecen en cada ventana y qué particiones reciben.
6. Consulta la asignación:

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group escalamiento-s14
~~~

**Checkpoint 4:** explica por qué los dos miembros de **escalamiento-s14** se reparten las particiones y por qué un tercer miembro puede recibir trabajo, mientras que un cuarto puede quedar sin asignación si el topic sigue teniendo tres particiones.

Detén los consumidores de consola con **Ctrl+C**.

## 5. Provocar y observar consumer lag

Ahora haremos que un grupo deje de consumir mientras Kafka sigue recibiendo eventos.

### 5.1 Confirmar el grupo

Antes de detener el servicio, asegúrate de que **resumen-s14** ya consumió al menos una compra. Consulta:

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group resumen-s14
~~~

Si el grupo aún no tiene offsets confirmados, genera algunas compras y vuelve a consultar.

### 5.2 Detener solo el consumidor de resumen

En la PowerShell ubicada en **labs\kafka-compras-webapp**:

~~~powershell
docker compose --profile app stop resumen-api
~~~

No detengas Kafka ni compras-api. El procesador de semana 13 puede continuar consumiendo en su propio grupo.

### 5.3 Generar eventos mientras el grupo está detenido

En la PowerShell ubicada en **labs\kafka-operations-s14**:

~~~powershell
.\scripts\generar-compras.ps1 -Count 1000
~~~

El script publica compras mediante HTTP y espera la respuesta de cada solicitud. Registra el tiempo y la tasa de solicitudes HTTP que informa.

### 5.4 Consultar el lag

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group resumen-s14
~~~

Busca **CURRENT-OFFSET**, **LOG-END-OFFSET** y **LAG**. También puedes abrir http://localhost:8083 → Consumer Groups.

**Checkpoint 5:** captura el lag del grupo detenido. Explica por qué el grupo procesador puede tener un lag distinto: ambos grupos avanzan de manera independiente.

### 5.5 Recuperar el consumidor

Vuelve a la carpeta **labs\kafka-compras-webapp**:

~~~powershell
docker compose --profile app start resumen-api
~~~

Consulta el lag varias veces:

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group resumen-s14
~~~

**Checkpoint 6:** evidencia cómo disminuye el lag y explica qué permitió recuperar el procesamiento. No es necesario que llegue a cero instantáneamente; espera a que termine de procesar el backlog.

## 6. Comparar tuning mediante una prueba de carga

La prueba siguiente utiliza el ejecutable de rendimiento incluido en Kafka y topics separados de los eventos de negocio. Así no enviamos registros de prueba que puedan romper el JSON que consumen los microservicios.

Se compararán dos configuraciones con la misma cantidad y tamaño de registros.

### 6.1 Línea base

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-producer-perf-test.sh --topic rendimiento-base-s14 --num-records 50000 --record-size 512 --throughput 10000 --producer-props bootstrap.servers=localhost:9092 acks=1 batch.size=16384 linger.ms=0 compression.type=none buffer.memory=33554432
~~~

Registra la salida completa: registros enviados, records/sec, MB/sec, latencia promedio y máxima, si la herramienta las informa.

### 6.2 Configuración ajustada

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-producer-perf-test.sh --topic rendimiento-tuned-s14 --num-records 50000 --record-size 512 --throughput 10000 --producer-props bootstrap.servers=localhost:9092 acks=1 batch.size=32768 linger.ms=20 compression.type=snappy buffer.memory=67108864
~~~

Registra las mismas métricas.

### 6.3 Comparar

Completa:

| Métrica | Línea base | Ajustada |
|---|---:|---:|
| Registros | 50.000 | 50.000 |
| Tamaño por registro | 512 bytes | 512 bytes |
| Particiones | 3 | 3 |
| batch.size | 16 KiB | 32 KiB |
| linger.ms | 0 ms | 20 ms |
| buffer.memory | 32 MiB | 64 MiB |
| compression.type | none | snappy |
| Tiempo total | | |
| Throughput (records/sec) | | |
| Throughput (MB/sec) | | |
| Latencia promedio | | |
| Latencia máxima | | |

**Checkpoint 7:** explica si la configuración ajustada mejoró el resultado en tu equipo. Si no mejoró, también es un resultado válido: analiza el volumen, CPU, memoria, red y duración de la prueba. No inventes ni extrapoles valores.

**Alcance de esta medición:** la prueba mide publicación en Kafka con parámetros de productor. No mide por sí sola el tiempo de procesamiento de los microservicios ni el rendimiento completo de la aplicación.

Si el equipo está limitado de recursos, puedes repetir con 10.000 registros manteniendo el mismo número y tamaño en ambas pruebas.

## 7. Evidencia final y reflexión

Entrega o conserva en tu repositorio personal:

1. Configuración efectiva de al menos dos topics: particiones, RF, retención y cleanup policy.
2. Dos consumer groups identificados con sus responsabilidades.
3. Captura de lag antes y después de reactivar el consumidor.
4. Salida de la prueba de carga base y ajustada.
5. Tabla comparativa con valores reales.
6. Una operación administrativa reproducible: el script de creación/configuración de topics o comandos equivalentes.
7. DevLog personal en **docs/devlog/semana-14.md**.

Responde en tu DevLog:

- ¿Qué parámetro tuvo mayor efecto en tu prueba y qué evidencia lo respalda?
- ¿Qué riesgo tendría una retención demasiado corta para auditoría o replay?
- ¿Qué cambiarías si el lag siguiera creciendo aun con todas las particiones ocupadas?
- ¿Qué demuestra RF=1 y qué necesitarías para demostrar RF=3?

## 8. Apagado seguro

Primero guarda las evidencias. Luego, desde **labs\kafka-compras-webapp**:

~~~powershell
docker compose --profile app --profile pipeline down
~~~

El broker del laboratorio de compras no tiene volumen persistente: al eliminar los contenedores se pierden sus eventos locales. No uses **docker system prune** ni elimines volúmenes globales de Docker.

La extensión opcional de replicación se apaga por separado:

~~~powershell
docker compose -f compose.replication.yaml down
~~~

## Troubleshooting

| Problema | Qué revisar |
|---|---|
| El script no encuentra el contenedor | Inicia el laboratorio de compras y revisa el nombre **dsy1107-kafka-s12**. |
| El topic ya existe | El script es idempotente; revisa su número de particiones y configuración efectiva. No intentes reducir particiones. |
| El grupo resumen-s14 no aparece | Revisa KAFKA_GROUP en el .env y confirma que resumen-api inició con ese valor. Publica al menos un evento. |
| No aparece lag | Confirma que el grupo ya tenía offsets, que resumen-api está detenido y que se generaron eventos después de detenerlo. |
| La API de compras falla | Comprueba que compras-api esté activo en el puerto 8081 y que Kafka esté saludable. |
| La prueba de carga falla | Comprueba que el topic de rendimiento exista y que el broker tenga recursos disponibles. Reduce registros si es necesario. |
| El resultado ajustado es peor | No es necesariamente un error. Registra el resultado y explica el costo de batching/compresión. |
