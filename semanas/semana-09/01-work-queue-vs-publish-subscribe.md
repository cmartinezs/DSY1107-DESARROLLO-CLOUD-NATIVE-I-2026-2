# Work Queue vs Publish/Subscribe

## Work Queue
Varios workers compiten por mensajes de una misma cola.

```text
Producer
   ↓
 Queue
 ↙   ↘
C1    C2
```

## Publish/Subscribe
Un evento puede interesar a múltiples capacidades independientes.

```text
             ┌→ Queue A → Consumer A
Producer → Exchange
             └→ Queue B → Consumer B
```

Conectar dos consumers a la **misma queue** no convierte el flujo en Publish/Subscribe: sigue siendo competing consumers.

Usa Work Queue para repartir el mismo trabajo. Usa Publish/Subscribe cuando el mismo hecho debe activar capacidades distintas e independientes.
