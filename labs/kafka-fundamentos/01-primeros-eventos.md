# Laboratorio 01 · Topics, claves, particiones y offsets

## Objetivo

Crear un topic de tres particiones, enviar eventos de compras con clave y observar particiones y offsets. Usaremos **PowerShell en Windows 10 y Docker Desktop**, sin Make ni terminal Linux.

## Paso 1 · Preparar Kafka

Abrir PowerShell en la raíz del repositorio. Ejecutar:

```powershell
cd labs\kafka-fundamentos
docker compose up -d
```

Comprobar que Kafka responde (si todavía está arrancando, repetir tras unos instantes):

```powershell
docker exec dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-broker-api-versions.sh --bootstrap-server localhost:9092
```

Crear el topic `compras` y revisar su configuración:

```powershell
docker exec dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic compras --partitions 3 --replication-factor 1
docker exec dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic compras
```

Comprobar **3 particiones** y **replication-factor=1**. La opción `--if-not-exists` permite repetir el comando sin crear un topic duplicado.

**Otra forma de crear el topic (visual, como en AVA):** abre **http://localhost:8083 → Topics → Add a Topic**, crea `compras` con **3 particiones** y **factor de replicación 1**. Si ya creaste el topic por consola, **no lo crees de nuevo**: solo verifica sus propiedades desde Kafka UI. El nombre de cada botón puede variar ligeramente según versión.

## Paso 2 · Leer y publicar eventos

Abrir **dos ventanas de PowerShell**. En ambas se puede trabajar desde la carpeta del laboratorio, aunque los comandos `docker exec` no dependen de la carpeta.

**Ventana A · Consumidor:** mostrar registros del topic desde el principio y sus metadatos.

```powershell
docker exec -it dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic compras --from-beginning --property print.key=true --property print.partition=true --property print.offset=true
```

**Ventana B · Productor:** iniciar la consola de escritura.

```powershell
docker exec -it dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic compras --property parse.key=true --property key.separator=:
```

En la ventana B, copiar **una línea por vez** y pulsar Enter:

```text
cliente-1:{"cliente":"cliente-1","monto":100,"id":"c-001"}
cliente-2:{"cliente":"cliente-2","monto":50,"id":"c-002"}
cliente-1:{"cliente":"cliente-1","monto":200,"id":"c-003"}
cliente-2:{"cliente":"cliente-2","monto":75,"id":"c-004"}
```

La parte anterior a `:` es la **clave** y el JSON posterior es el **valor**. Observar en la ventana A las claves, particiones y offsets. Para detener productor o consumidor, usar **Ctrl+C**.

## Preguntas de análisis

1. ¿En qué partición aparecen los eventos con clave `cliente-1`? ¿Comparten partición?
2. ¿Qué significa `offset=0` en la partición 0 comparado con `offset=0` en la partición 1?
3. ¿Existe orden global de eventos entre todas las particiones? Argumentar.
4. ¿Qué cambia al poder releer eventos desde el inicio, frente al procesamiento y confirmación de tareas en RabbitMQ?

## Checkpoint

Guardar una captura de la descripción del topic y otra con registros y sus campos `key`, `partition` y `offset`. Explicar por qué replicación 1 **no** significa tolerancia a fallos del broker.

## Paso 3 · Ver el resultado en Kafka UI

1. Abre **http://localhost:8083** en Windows.
2. Entra a **Topics → compras → Messages**.
3. Busca un evento con clave `cliente-1`; compara **Key, Value, Partition y Offset** con la salida de la consola.
4. Observa dos eventos con la misma clave. Deben conservar su partición **si se mantiene la configuración y el número de particiones**.
5. Para el contraste **sin clave**, haz [la guía visual complementaria](./04-kafka-ui-guia-ava.md). Sin clave no hay una garantía de secuencia fija P0→P1→P2.

**Checkpoint visual:** captura de Kafka UI mostrando las tres particiones y un evento. **Explica:** el offset identifica la posición **en su partición**, no un número global del topic.
