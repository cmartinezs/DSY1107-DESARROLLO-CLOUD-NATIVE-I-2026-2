# 05 · Checkpoint y evidencia

## Antes
`Publisher → Exchange → Queue → Consumer → capacidad`

## Después
```text
Publisher → durable Exchange → durable Queue → Consumer
                                           ├─ OK → ACK
                                           └─ FAIL → DLX → DLQ
```

## Evidencia obligatoria
- topología antes/después;
- configuración relevante;
- logs;
- Management UI;
- mensaje confirmado;
- mensaje en DLQ;
- commits/archivos;
- decisión técnica;
- deuda pendiente;
- DevLog.

## Preguntas
1. ¿Qué diferencia hay entre durable y acknowledged?
2. ¿Qué error reencolarías y cuál no?
3. ¿Por qué existe tu DLQ?
4. ¿Qué observarías en producción?
5. ¿Qué cambiaría si RabbitMQ fuese reemplazado?
