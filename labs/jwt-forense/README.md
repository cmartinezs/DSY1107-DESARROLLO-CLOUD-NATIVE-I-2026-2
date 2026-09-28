# Laboratorio canónico · JWT forense

**Semana:** 03  
**RA/IL:** RA1 · IL1.2 / IL1.3  
**Dominio:** API ficticia `products-api`  
**Modalidad:** local, analítica y reproducible

## Propósito

Interpretar JWT y defender decisiones de acceso antes de depender de una consola cloud.

## Ruta

1. [00 · Contexto y contrato](./00-contexto.md)
2. [01 · Anatomía JWT](./01-anatomia-jwt.md)
3. [02 · Tokens sospechosos](./02-tokens-sospechosos.md)
4. [03 · Matriz HTTP 401/403/2xx](./03-matriz-http.md)
5. [04 · Decode vs verify](./04-decode-vs-verify.md)
6. [05 · Arquitectura y evidencia](./05-arquitectura-evidencia.md)

## Resultado esperado

El estudiante debe poder explicar:

```text
decode
≠ verify
≠ authorize
```

y relacionar `iss`, `aud`, `sub`, `exp` y scopes con el punto donde una request puede ser aceptada o rechazada.

## Evidencia

- tabla de análisis;
- matriz 401/403/2xx;
- diagrama;
- DevLog;
- explicación oral.

## Transferencia

Después de comprender el patrón:

→ [RegistrApp · Semana 03](../../proyecto-formativo/semana-03/)
