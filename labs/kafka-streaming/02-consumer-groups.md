# Laboratorio 02 · Consumer groups, rebalance y replay

**Prerequisito:** [laboratorio 01](./01-primeros-eventos.md).

## A · Grupos independientes
En tres terminales:

- A: `make group-a` (grupo analytics).
- B: `make group-b` (grupo notificaciones).
- C: `make producer`; enviar:

```text
cliente-3:{"cliente":"cliente-3","monto":80,"id":"c-005"}
cliente-4:{"cliente":"cliente-4","monto":150,"id":"c-006"}
cliente-3:{"cliente":"cliente-3","monto":30,"id":"c-007"}
```

**Evidencia:** ambos grupos pueden recibir cada registro producido después de que se suscriban; conservan offsets independientes.

## B · Miembros del mismo grupo
Abrir otra terminal con `make group-a` y generar seis eventos más. Ejecutar `make groups`. Observar miembros, asignaciones y lag; puede haber rebalances. No asumir repartición 50/50 por cantidad de registros.

Preguntas: ¿por qué los dos miembros de analytics no procesan ambos todos los eventos? ¿Qué sucede si tenemos cuatro miembros para un topic de tres particiones?

## C · Replay controlado
1. Detener **todos** los miembros analytics (Ctrl+C).
2. Consultar `make groups`.
3. Ejecutar `make reset` con analytics inactivo (restaura offsets a earliest).
4. Reiniciar `make group-a` y comprobar relectura de registros aún retenidos.

## Checkpoint
Registrar observaciones antes y después del reset. Explicar por qué replay exige idempotencia cuando existen efectos externos.
