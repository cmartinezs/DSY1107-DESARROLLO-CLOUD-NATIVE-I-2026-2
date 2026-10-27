# Laboratorio local · Kafka, particiones y consumer groups

Laboratorio independiente de RegistrApp. **Tipo:** local/autocontenido, broker único en KRaft, sin alta disponibilidad.

## Requisitos
Docker + Compose V2 en Linux/WSL2 y disponibilidad de la imagen `apache/kafka:4.1.2`. No requiere JDK para los laboratorios 01 y 02.

```bash
cd labs/kafka-streaming
make up
make check
make topics
make describe
```

Si Kafka aún está iniciando, repetir `make check`.

## Itinerario
1. [Productores, claves, topics, particiones y offsets](./01-primeros-eventos.md).
2. [Consumer groups, asignación, lag y replay](./02-consumer-groups.md).
3. [Diseño de Kafka Streams, ventanas y estado](./03-streams-diseno.md) (**solo diseño; sin implementación Java ejecutable**).

## Salida y limpieza
`make down` detiene el entorno; `make clean` elimina también volúmenes (pérdida de datos local).

## Límites
Broker único, replication-factor=1, puerto 9092 local sin autenticación. Solo desarrollo local; no exponer este puerto públicamente.
