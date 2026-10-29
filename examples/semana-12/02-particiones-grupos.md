# Ejemplo 02 · Orden, claves, offsets y consumer groups

Topic `compras` con particiones P0, P1 y P2. Los offsets son independientes:

```text
P0: offset 0 (cliente-A), offset 1 (cliente-A)
P1: offset 0 (cliente-B)
P2: offset 0 (cliente-C)
```

No hay orden global entre P0, P1 y P2. Una misma clave normalmente se asigna a la misma partición mientras permanezcan estables la estrategia de particionado y el número de particiones.

Dos grupos `analytics` y `notificaciones` pueden leer independientemente las tres particiones. Dentro de `analytics`, tres instancias pueden repartirse P0, P1 y P2; una cuarta instancia queda sin particiones de ese topic.

**Verificar en terminal:** [laboratorio 02](../../labs/kafka-fundamentos/02-consumer-groups.md).
