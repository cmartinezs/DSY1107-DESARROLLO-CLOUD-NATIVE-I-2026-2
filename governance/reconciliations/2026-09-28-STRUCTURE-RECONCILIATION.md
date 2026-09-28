# Reconciliación estructural · 2026-09-28

## Objetivo

Llevar DSY1107 al nivel de completitud operacional esperado por el canon transversal, usando DSY1102 como referencia de madurez estructural y no como plantilla temática.

## Hallazgos de entrada

- ausencia de `ejercicios/`;
- ausencia de Semana 06;
- datos semanales detenidos en Semana 05;
- examples con READMEs sin artefactos demostrativos;
- labs relevantes comprimidos en un único README;
- checkpoints de RegistrApp con placeholders;
- base académica estable incompleta;
- material EV1 profundo ubicado en una raíz transitoria `guias-integradas/`;
- ausencia de governance declarativa.

## Decisiones

1. mantener `examples/` como alias histórico permitido;
2. crear `ejercicios/`;
3. completar la ruta temporal hasta Semana 08;
4. descomponer Week 8 y labs vigentes en pasos reales;
5. reclasificar CloudTasks como `labs/cloudtasks-ev1-integrado/`;
6. usar `.work/` como workspace local no versionado;
7. eliminar `guia/` y `guias-integradas/` como raíces activas;
8. completar la base académica estable desde PDA/PA/cronograma;
9. no materializar semanas futuras antes de su preparación;
10. declarar gaps de web/publicación en vez de inventar cierre.

## Criterio de aceptación

```text
README = mapa
archivos internos = trabajo real
semana = contenido
example = demostración
ejercicio = práctica breve
lab = guía secuencial
proyecto-formativo = incremento
evaluación = orientación
docs = conocimiento estable
```

## Estado

Reconciliación en curso en PR #7. El cierre exige revisión de enlaces y navegación antes de merge.
