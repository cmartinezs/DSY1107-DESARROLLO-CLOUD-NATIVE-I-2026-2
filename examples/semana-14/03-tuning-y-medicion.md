# Ejemplo 03 · Tuning y medición del productor

## Hipótesis

Si hay suficientes registros para formar batches, aumentar **batch.size** y **linger.ms** y habilitar compresión podría mejorar el throughput del productor. La prueba debe confirmar o descartar esa hipótesis.

## Línea base

~~~properties
acks=1
batch.size=16384
linger.ms=0
compression.type=none
buffer.memory=33554432
~~~

## Configuración ajustada

~~~properties
acks=1
batch.size=32768
linger.ms=20
compression.type=snappy
buffer.memory=67108864
~~~

## Cómo comparar

Mantén constantes:

- topic y número de particiones;
- cantidad de registros;
- tamaño de cada registro;
- equipo y estado de los contenedores;
- valor de **acks**.

Registra throughput, latencia y, si está disponible, CPU/memoria. Ejecuta más de una ronda si el tiempo lo permite.

## Interpretación

- Más throughput puede implicar más latencia por el tiempo de batching.
- Compresión puede reducir bytes de red, pero aumentar CPU.
- Más **buffer.memory** ayuda a absorber ráfagas; no arregla un cuello de botella sostenido.
- Los resultados dependen del equipo. No existe un resultado numérico universal.

→ [Teoría 3.3.2](../../semanas/semana-14/02-tuning-buffers-retention-carga.md) · [Laboratorio](../../labs/kafka-operations-s14/)
