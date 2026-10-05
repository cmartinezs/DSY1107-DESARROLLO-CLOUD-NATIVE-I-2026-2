# Semana 09 · Confiabilidad en mensajería asíncrona

**RA/IL:** RA2 · IL2.2 / IL2.3  
**Foco:** Publish/Subscribe, acknowledgements, durabilidad, DLX/DLQ, retención y observabilidad.

## Propósito
Semana 08 dejó un flujo funcional: `Producer → Exchange → Binding → Queue → Consumer`.

Semana 09 agrega una pregunta más exigente: **¿qué ocurre cuando el procesamiento falla?**

## Resultados esperados
- distinguir Work Queue de Publish/Subscribe;
- explicar la diferencia entre ACK y durabilidad;
- implementar ACK/NACK conscientemente;
- configurar DLX y DLQ;
- provocar un fallo o expiración y encontrar el mensaje en DLQ;
- justificar por qué efectos secundarios no deberían bloquear el flujo principal.

## Ruta
1. [Work Queue vs Publish/Subscribe](./01-work-queue-vs-publish-subscribe.md)
2. [ACK, NACK y fallos](./02-acknowledgements-y-fallos.md)
3. [Durabilidad y persistencia](./03-durabilidad-y-persistencia.md)
4. [DLX, DLQ y retención](./04-dlx-dlq-y-retencion.md)
5. [Observabilidad y evidencia](./05-observabilidad-alertas-y-evidencia.md)
6. [Trabajo formativo](./trabajo-formativo.md)
7. [Evaluación Parcial 2](../../evaluaciones/02-parcial-2.md)

> ACK confirma procesamiento; no reemplaza la durabilidad.
