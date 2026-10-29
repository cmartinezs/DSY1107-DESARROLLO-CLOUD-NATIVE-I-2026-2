# Laboratorio 02 · Consumer groups, distribución y replay

**Requisito previo:** completar [Laboratorio 01](./01-primeros-eventos.md). Continuar con Kafka levantado y el topic `compras` ya creado.

Solo se necesitan **PowerShell en Windows 10 y Docker Desktop**. Para cerrar cada consumidor o productor, usar **Ctrl+C**.

## A · Dos grupos independientes

Abrir **tres ventanas de PowerShell** y ejecutar cada comando en la ventana indicada.

**Ventana A — grupo `analytics`:**

```powershell
docker exec -it dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic compras --group analytics --property print.key=true --property print.partition=true --property print.offset=true
```

**Ventana B — grupo `notificaciones`:**

```powershell
docker exec -it dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic compras --group notificaciones --property print.key=true --property print.partition=true --property print.offset=true
```

**Ventana C — productor:**

```powershell
docker exec -it dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic compras --property parse.key=true --property key.separator=:
```

Enviar las siguientes líneas desde C, **después** de que los dos consumidores estén esperando eventos:

```text
cliente-3:{"cliente":"cliente-3","monto":80,"id":"c-005"}
cliente-4:{"cliente":"cliente-4","monto":150,"id":"c-006"}
cliente-3:{"cliente":"cliente-3","monto":30,"id":"c-007"}
```

**Observar:** los dos grupos pueden procesar los mismos eventos porque cada grupo administra sus propios offsets.

## B · Dos consumidores en un mismo grupo

Sin cerrar el consumidor del grupo `analytics` de la ventana A, abrir una **cuarta ventana D** y ejecutar **el mismo comando** que en A:

```powershell
docker exec -it dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic compras --group analytics --property print.key=true --property print.partition=true --property print.offset=true
```

Desde el productor (C), enviar algunos eventos nuevos con claves distintas, por ejemplo:

```text
cliente-1:{"cliente":"cliente-1","monto":20,"id":"c-008"}
cliente-2:{"cliente":"cliente-2","monto":40,"id":"c-009"}
cliente-3:{"cliente":"cliente-3","monto":60,"id":"c-010"}
cliente-4:{"cliente":"cliente-4","monto":80,"id":"c-011"}
cliente-5:{"cliente":"cliente-5","monto":100,"id":"c-012"}
cliente-6:{"cliente":"cliente-6","monto":120,"id":"c-013"}
```

En **otra ventana de PowerShell** (sin cerrar A ni D), consultar el grupo:

```powershell
docker exec dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group analytics
```

**Observar:** las particiones se distribuyen entre los consumidores de `analytics`. No se garantiza un reparto exacto 50/50 de la cantidad de registros.

Responder: ¿por qué A y D no reciben cada uno todos los eventos? ¿Qué pasaría al conectar cuatro consumidores del mismo grupo a un topic con tres particiones?

## C · Releer eventos (replay)

1. Detener con **Ctrl+C** los dos consumidores de `analytics` (A y D). **No detener Kafka**.
2. En otra ventana, ejecutar el siguiente comando para reiniciar los offsets del grupo:

```powershell
docker exec dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group analytics --topic compras --reset-offsets --to-earliest --execute
```

3. Volver a iniciar el consumidor del grupo `analytics`:

```powershell
docker exec -it dsy1107-kafka-fundamentos-s12 /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic compras --group analytics --property print.key=true --property print.partition=true --property print.offset=true
```

4. Comprobar que vuelve a recibir registros **todavía retenidos** en el topic. Si el reinicio de offsets falla, verificar que ya no quede ningún consumidor `analytics` ejecutándose.

## Checkpoint

Tomar capturas de los dos grupos, de la distribución de particiones de `analytics` y de la relectura después del reset. Explicar por qué, si un evento puede reprocesarse, una aplicación con efectos externos debe ser **idempotente**.

## Paso D · Inspeccionar grupos en Kafka UI

1. Abre **http://localhost:8083 → Consumer Groups**.
2. Busca `analytics` y `notificaciones`. Las pantallas pueden tardar unos segundos en reflejar los cambios.
3. Compara los offsets confirmados y el **lag** por grupo y partición. **Lag** significa registros pendientes según offsets confirmados, no segundos de retraso ni el número exacto de tareas de negocio.
4. Mientras hay dos consumidores de `analytics`, observa cómo se asignan particiones. Un cuarto consumidor de un grupo de tres particiones podría quedar sin asignación.
5. Después del reset del grupo `analytics` (con **todos** sus miembros detenidos), comprueba la relectura de eventos retenidos.

**Checkpoint:** captura de los grupos más un párrafo que diferencie **dos miembros del mismo grupo** de **dos grupos independientes**. Recuerda que `--from-beginning` / `auto-offset-reset=earliest` no sobrescriben offsets ya confirmados por un grupo existente.
