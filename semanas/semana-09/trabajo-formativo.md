# Trabajo formativo · Confiabilidad sobre la capacidad asíncrona existente

## Regla
No selecciones una nueva capacidad.

Semana 09 recibe exactamente la capacidad asíncrona implementada en Semana 08 y la fortalece.

## Incremento
Sobre ese mismo flujo incorpora:
- topología durable cuando corresponda;
- estrategia ACK/NACK;
- manejo diferenciado de errores;
- DLX;
- DLQ;
- prueba controlada de fallo o expiración.

```text
Semana 08:
Publisher → Exchange → Queue → Consumer → capacidad

Semana 09:
Publisher → durable Exchange → durable Queue → Consumer
                                           ├─ OK → ACK
                                           └─ FAIL → DLX → DLQ
```

El checkpoint canónico vive en [RegistrApp · Semana 09](../../proyecto-formativo/semana-09/).
