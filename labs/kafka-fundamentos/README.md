# Laboratorio A · Fundamentos de Apache Kafka

**Semana 12 · DSY1107**. Práctica inicial de **Kafka sin microservicios ni frontend**. Se ejecutan productor y consumidor desde herramientas incluidas en el contenedor Kafka y se estudian topics, claves, particiones, offsets, consumer groups y replay.

Este laboratorio es **autónomo**: no depende de la aplicación React/Spring Boot ni comparte su broker, eventos o estado.

## Para estudiantes (Windows 10)

**Necesitas:** Docker Desktop funcionando, PowerShell y navegador para leer la guía. No necesitas instalar Kafka, Linux, WSL como terminal, Make, Java, Maven ni Node.

Desde la **raíz del repositorio**:

```powershell
cd labs\kafka-fundamentos
docker compose up -d
docker compose ps
```

Comprobar que Kafka responde (puede tardar en iniciar):

```powershell
docker exec dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-broker-api-versions.sh --bootstrap-server localhost:9092
```

## Actividades

1. **[01 · Topics, claves, particiones y offsets](./01-primeros-eventos.md)**: producir y leer eventos.
2. **[02 · Consumer groups y replay](./02-consumer-groups.md)**: comparar consumidores independientes, asignación y relectura.
3. **[03 · Diseñar Kafka Streams](./03-streams-diseno.md)**: topologías y ventanas **en papel**, sin aplicación Streams ejecutable.

**Checkpoint final:** describe las tres particiones de `compras`, observa offsets y explica qué sucede con dos consumer groups diferentes.

## Apagado y relación con el otro laboratorio

```powershell
docker compose down
```

> Este broker conserva datos solo mientras existe su contenedor (no se declara volumen persistente). `docker compose down` **borra los eventos locales**. No lo ejecutes entre actividades si debes conservar mensajes.

**No ejecutes este y el laboratorio B al mismo tiempo**: ambos utilizan el puerto `9092` de Windows. Para cambiar de laboratorio, detén el primero con `docker compose down` desde su carpeta; entra al otro y sigue sus propias instrucciones.

La versión de «compras con aplicación real» está en el **[Laboratorio B · React + Spring Boot + Kafka](../kafka-compras-webapp/)**.

**Pendiente de contraste con AVA:** se incorporarán actividades/herramientas de la guía institucional cuando esté disponible, sin dar por obligatoria Kafka UI hasta revisar el material.
