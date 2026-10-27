# Guía docente · Semana 12

## Inicio: recordar RabbitMQ (15 minutos)
Caso: se confirma una compra; debemos despachar una tarea de correo, alimentar indicadores y poder recalcular estadísticas históricas. Dibujar dos arquitecturas y justificar necesidad de fanout, retención, replay e idempotencia. Evitar la falsa dicotomía de que Kafka sustituye siempre a RabbitMQ.

## Bloque 1
Explicar **producer → topic → partition → broker → consumer group**. Offset es posición relativa a una partición. Key determinista favorece afinidad de cliente. Pide al grupo dibujar un topic de tres particiones y dos grupos independientes.

## Bloque 2
Iniciar Kafka con Docker y hacer el [lab 01](../../labs/kafka-streaming/01-primeros-eventos.md). Preguntar por qué dos eventos con key igual van a la misma partición en la configuración vigente y qué ocurriría si cambia la cantidad de particiones.

## Bloque 3
Hacer el [lab 02](../../labs/kafka-streaming/02-consumer-groups.md). Dos consumidores con el mismo `group.id` comparten particiones; dos grupos independientes pueden observar cada registro. Revisar offset confirmado, lag y reset con grupo inactivo.

## Bloque 4
Introducir Kafka Streams: `filter`/ `map` (stateless); `groupByKey`/ `count`/ `aggregate` (stateful); ventanas tumbling, hopping y de sesión, state stores y changelogs. Resolver [diseño de topología](../../labs/kafka-streaming/03-streams-diseno.md). **Este módulo es diseño y pseudocódigo; no declarar una aplicación Java implementada.**

## Evaluación formativa
- Justifica cola versus log de eventos ante un escenario nuevo.
- Demuestra topic con tres particiones y eventos con key.
- Explica por qué cuatro consumidores de un grupo frente a tres particiones dejan al menos uno sin asignación (si no hay otros topics).
- Calcula totales por cliente para ventanas de un minuto.
- Describe una limitación de la configuración local (replicación=1, sin alta disponibilidad).

## Secciones
002D y 003D conservan checkpoints independientes. Completar evidencia después de cada sesión; no marcar lab hecho sin observación.
