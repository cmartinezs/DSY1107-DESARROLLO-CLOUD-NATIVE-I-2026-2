# 05 · Pruebas, evidencias y cierre · Laboratorio A (Semana 12)

**Propósito:** verificar de forma observable el trabajo con Kafka **sin microservicios**. Antes de comenzar: completar [01](./01-primeros-eventos.md) y [02](./02-consumer-groups.md); para interpretación visual usar [Kafka UI](./04-kafka-ui-guia-ava.md).

## Checklist de salida

- [ ] `docker compose ps` muestra Kafka y Kafka UI en ejecución.
- [ ] El topic `compras` existe con tres particiones y factor de replicación 1.
- [ ] Un productor publica al menos dos JSON válidos y un consumidor los observa.
- [ ] Kafka UI permite identificar **key, value, partition y offset** de un evento.
- [ ] Dos grupos independientes reciben nuevos eventos sin competir entre sí.
- [ ] Dos miembros del mismo grupo se reparten particiones (no necesariamente igual cantidad de mensajes).
- [ ] Se detienen **todos** los miembros del grupo antes de resetear offsets; el replay muestra mensajes aún retenidos.
- [ ] El estudiante explica que Kafka conserva orden por partición, no globalmente.

## Evidencia mínima

Captura del topic y sus tres particiones; mensaje con clave/offset visible; pantalla de grupos; comando de reset y relectura; respuestas de una o dos frases sobre la diferencia con una cola RabbitMQ. Registrar comandos y resultados en DevLog; no incluir tokens ni credenciales.

## Diagnóstico

| Síntoma | Qué inspeccionar | Recuperación acotada |
|---|---|---|
| `docker` no responde | Docker Desktop/daemon | Iniciar Docker Desktop; comprobar `docker version` |
| Kafka UI sin conexión | `docker compose ps`, `docker compose logs kafka kafka-ui` | Esperar servicio saludable y recargar UI |
| Topic no existe | `kafka-topics.sh --list` | Crearlo una vez según 01 |
| No hay nuevos eventos | Topic, productor y momento de inicio del consumidor | Publicar un nuevo evento con ambos consumidores activos |
| Replay no funciona | Miembros activos, offsets confirmados y retención | Detener todos los miembros y ejecutar reset según 02 |
| Puerto 9092/8083 ocupado | Otro laboratorio Kafka activo | Detener el otro Compose desde **su carpeta** |

## Cierre y persistencia

```powershell
docker compose ps
docker compose down
```

**Atención:** este entorno no declara volumen persistente. `docker compose down` elimina el contenedor y los eventos; conservar capturas y respuestas antes de cerrar. No utilizar `down -v` ni limpiar globalmente Docker sin instrucción docente.

## Transferencia

El laboratorio es de dominio neutral. Después de demostrar comprensión, contrastar la [transferencia a RegistrApp](../../proyecto-formativo/semana-12/), sin copiar este caso directamente.
