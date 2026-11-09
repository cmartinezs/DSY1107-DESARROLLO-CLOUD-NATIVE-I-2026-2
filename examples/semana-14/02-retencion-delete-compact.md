# Ejemplo 02 · Retención y políticas de limpieza

## Caso A: historial de compras

Se necesita conservar eventos durante siete días, con un límite de almacenamiento por partición. Los siguientes comandos se ejecutan desde PowerShell en Windows mediante el contenedor Kafka:

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic compras --partitions 3 --replication-factor 1
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics --entity-name compras --alter --add-config retention.ms=604800000,retention.bytes=104857600,cleanup.policy=delete
~~~

- Siete días: **retention.ms=604800000**.
- 100 MiB por partición: **retention.bytes=104857600**.
- **delete**: elimina segmentos antiguos.
- El límite de tiempo o tamaño que se alcance primero puede iniciar la limpieza.

## Caso B: estado actual de clientes

Si el objetivo es reconstruir el último estado conocido de cada cliente, puede convenir un topic compactado:

~~~powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic estado-clientes --partitions 3 --replication-factor 1 --config cleanup.policy=compact
~~~

Publicar varias actualizaciones con la misma key permite que la compactación conserve el último valor conocido de esa key.

**Diferencia central:** **delete** conserva una ventana de eventos; **compact** conserva el estado más reciente por clave. La compactación es asíncrona y no elimina inmediatamente cada versión anterior.

> Estos comandos son ejemplos de configuración. En el laboratorio de Semana 14, usa el script idempotente que configura los topics del caso de compras.

→ [Teoría 3.3.1](../../semanas/semana-14/01-escalamiento-replicacion-retencion-consumer-lag.md)
