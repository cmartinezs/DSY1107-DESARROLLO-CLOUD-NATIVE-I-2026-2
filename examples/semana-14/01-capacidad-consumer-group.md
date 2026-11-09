# Ejemplo 01 · Capacidad de un consumer group

## Escenario

El topic **compras** tiene tres particiones. El grupo **analytics** ejecuta consumidores para calcular indicadores.

| Instancias en el grupo | Asignación posible | ¿Todas trabajan? |
|---:|---|---|
| 1 | P0, P1, P2 → C1 | Sí |
| 2 | P0, P1 → C1; P2 → C2 | Sí |
| 3 | P0 → C1; P1 → C2; P2 → C3 | Sí |
| 4 | P0 → C1; P1 → C2; P2 → C3; C4 sin asignación | No |

El reparto exacto puede variar según el asignador, pero una partición no se asigna simultáneamente a dos miembros del mismo grupo.

## Dos grupos diferentes

~~~mermaid
flowchart TB
  T[(Topic compras · P0 P1 P2)]
  T --> A[Grupo analytics]
  T --> N[Grupo notificaciones]
  A --> A1[Consumidores analytics]
  N --> N1[Consumidores notificaciones]
~~~

Ambos grupos leen el mismo topic con offsets independientes. Esto permite que analítica y notificaciones avancen a ritmos distintos.

## Decisión

Antes de aumentar consumidores, responde:

1. ¿Cuántas particiones tiene el topic?
2. ¿Cuántos consumidores pertenecen al mismo grupo?
3. ¿El lag está creciendo?
4. ¿El cuello de botella es la lógica del consumidor o la falta de paralelismo?

No aumentes particiones sin considerar las claves, el orden por partición y la distribución de carga.

→ [Teoría 3.3.1](../../semanas/semana-14/01-escalamiento-replicacion-retencion-consumer-lag.md)
