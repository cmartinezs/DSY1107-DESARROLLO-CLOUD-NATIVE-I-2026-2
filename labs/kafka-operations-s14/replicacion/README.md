# Extensión opcional · Replicación real con tres brokers KRaft

## Objetivo

Observar un topic con RF=3, las réplicas ISR y el cambio de leader cuando se detiene un broker.

Esta extensión es **opcional**. El laboratorio obligatorio utiliza el broker único de compras para no elevar los requisitos de hardware de todos los equipos.

## Requisitos y advertencias

- Docker Desktop operativo.
- Recursos suficientes para ejecutar tres brokers Kafka y Kafbat UI.
- El entorno usa Apache Kafka 4.1.2 en modo KRaft, sin ZooKeeper.
- La UI queda disponible en http://localhost:8084.
- Los brokers no publican puertos Kafka al host: se administran mediante **docker exec**.
- Los tres brokers se ejecutan en el mismo computador. La prueba demuestra la caída de un proceso/broker, no la pérdida física del host ni una arquitectura de alta disponibilidad entre zonas.

No ejecutes esta extensión junto con otra copia que utilice los mismos nombres de contenedor. El Compose es independiente del laboratorio de compras.

## 1. Iniciar los tres brokers

Desde PowerShell, dentro de **labs\kafka-operations-s14**:

~~~powershell
docker compose -f compose.replication.yaml up -d
docker compose -f compose.replication.yaml ps
~~~

Espera a que los tres brokers estén saludables. Abre http://localhost:8084 y comprueba que el clúster aparece conectado.

Si el clúster no alcanza quorum, revisa los logs:

~~~powershell
docker compose -f compose.replication.yaml logs --tail=100 kafka-1 kafka-2 kafka-3
~~~

## 2. Crear dos topics replicados

Crear **compras**:

~~~powershell
docker exec dsy1107-kafka-s14-1 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic compras --partitions 3 --replication-factor 3 --config min.insync.replicas=2 --config retention.ms=604800000 --config retention.bytes=104857600 --config cleanup.policy=delete
~~~

Crear **compras-validas**:

~~~powershell
docker exec dsy1107-kafka-s14-1 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic compras-validas --partitions 3 --replication-factor 3 --config min.insync.replicas=2 --config retention.ms=259200000 --config retention.bytes=52428800 --config cleanup.policy=delete
~~~

Consultar:

~~~powershell
docker exec dsy1107-kafka-s14-1 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic compras
docker exec dsy1107-kafka-s14-1 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic compras-validas
~~~

**Checkpoint:** cada partición debe mostrar tres réplicas y un conjunto ISR. Los leaders pueden distribuirse entre brokers.

## 3. Detener un broker

Detén **kafka-1**:

~~~powershell
docker compose -f compose.replication.yaml stop kafka-1
~~~

Consulta el topic desde **kafka-2**:

~~~powershell
docker exec dsy1107-kafka-s14-2 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe --topic compras
~~~

Observa:

- el leader de las particiones que estaban lideradas por el broker detenido puede cambiar;
- la réplica detenida deja de aparecer en ISR mientras está fuera;
- las réplicas sincronizadas restantes pueden mantener el servicio si cumplen **min.insync.replicas** y el quorum de control permanece disponible.

No prometas cero interrupción: puede existir un intervalo de elección/reconexión y el resultado depende del estado real de ISR.

## 4. Recuperar el broker

~~~powershell
docker compose -f compose.replication.yaml start kafka-1
~~~

Espera a que esté saludable y vuelve a describir el topic. Comprueba si la réplica se reincorpora al ISR.

## 5. Evidencia

Guarda:

1. Descripción inicial con RF=3 y ISR.
2. Descripción mientras **kafka-1** está detenido.
3. Descripción después de recuperar el broker.
4. Explicación de qué leader cambió y por qué.
5. Limitación del experimento al ejecutarse en un solo computador.

## 6. Apagado

~~~powershell
docker compose -f compose.replication.yaml down
~~~

No uses **docker system prune** ni elimines volúmenes globales de Docker.
