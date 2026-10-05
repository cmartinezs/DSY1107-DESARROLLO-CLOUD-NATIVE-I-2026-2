# Acknowledgements y fallos

```text
Queue → Consumer → procesamiento OK → ACK
```

El ACK significa: **este mensaje ya fue procesado y puede eliminarse de la cola**.

```text
procesamiento
├─ OK → ACK
└─ FAIL
   ├─ recuperable → NACK + requeue
   └─ no recuperable → NACK/reject sin requeue → DLX
```

Reencolar sin criterio puede crear ciclos infinitos.

Preguntas de defensa:
1. ¿En qué punto envías ACK?
2. ¿Qué ocurre si el proceso muere antes?
3. ¿Cuándo usarías requeue?
4. ¿Qué riesgo tiene requeue=true ante un error permanente?
