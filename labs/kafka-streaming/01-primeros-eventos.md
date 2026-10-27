# Laboratorio 01 · Topics, keys, particiones y offsets

## Objetivo
Crear un topic de tres particiones, publicar eventos con clave y observar offsets. Evidencia: salida de terminal y explicación por escrito.

## Paso 1 · Preparación
Desde `labs/kafka-streaming`:

```bash
make up
make check
make topics
make describe
```

Confirmar `compras` con tres particiones y replication-factor=1.

## Paso 2 · Producción y consumo
Terminal A: `make consumer`. Terminal B: `make producer`. Escribir y pulsar ENTER tras cada línea:

```text
cliente-1:{"cliente":"cliente-1","monto":100,"id":"c-001"}
cliente-2:{"cliente":"cliente-2","monto":50,"id":"c-002"}
cliente-1:{"cliente":"cliente-1","monto":200,"id":"c-003"}
cliente-2:{"cliente":"cliente-2","monto":75,"id":"c-004"}
```

`parse.key=true` hace que lo anterior sea `key:value`.

## Preguntas
1. ¿En qué partición aparecen los registros de cliente-1? ¿Comparten partición?
2. ¿Qué significa offset=0 en P0 comparado con offset=0 en P1?
3. ¿Existe orden global en todo el topic? Argumentar.
4. ¿Qué diferencia existe entre un consumer leyendo desde el inicio y un worker ACK de RabbitMQ?

## Checkpoint
Adjuntar `make describe` y registros con `key`, `partition` y `offset`. No asociar replication-factor=1 con tolerancia a caída del broker.
