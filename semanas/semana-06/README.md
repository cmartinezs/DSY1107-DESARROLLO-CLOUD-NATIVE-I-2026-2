# Semana 06 · Evaluación Parcial 1

**Periodo:** 14 al 19 de septiembre de 2026  
**RA:** RA1 · IL1.1, IL1.2, IL1.3

## Propósito

Semana de evaluación. No se introduce contenido conceptual nuevo. El foco es demostrar y defender la arquitectura base e integración cloud construida durante la primera experiencia de aprendizaje.

## Ruta

1. [Alcance evaluado](./01-alcance-evaluado.md)
2. [Checklist técnico de preparación](./02-checklist-tecnico.md)
3. [Defensa y evidencia](./03-defensa-y-evidencia.md)

## Relación con recursos previos

- [Semana 05](../semana-05/)
- [Laboratorio Full Stack seguro](../../labs/fullstack-seguro/)
- [Guía de Identity & Access](../../docs/identity/)
- [Evaluaciones](../../evaluaciones/README.md)

## Criterio de salida

El estudiante debe ser capaz de demostrar un flujo funcional y justificar cada frontera: identidad, token, gateway, backend y seguridad.


## Enfoque de la semana

Semana 06 no introduce una nueva tecnología. El trabajo consiste en integrar, demostrar y defender lo aprendido durante la Experiencia de Aprendizaje 1.

La evaluación debe observar tres dimensiones:

1. funcionamiento técnico;
2. trazabilidad de la implementación;
3. comprensión individual.

Una solución que funciona pero no puede explicarse está incompleta. Una explicación correcta sin evidencia reproducible también está incompleta.

## Preparación recomendada

Antes de la evaluación:

- ejecutar el flujo completo desde cero;
- verificar configuración sin secretos;
- probar casos positivos y negativos;
- actualizar el diagrama;
- identificar deuda conocida;
- revisar commits y DevLog;
- preparar una explicación breve por frontera.

## Modelo de defensa

El estudiante debería poder recorrer:

Usuario → SPA → Identity Provider → Access Token → Gateway → Backend protegido → Regla de aplicación.

En cada frontera debe explicar qué entra, qué se valida, qué puede fallar y qué evidencia existe.

## Qué NO hacer esta semana

- agregar funcionalidades nuevas para “mejorar” la entrega;
- rehacer arquitectura estable a último minuto;
- ocultar fallas con capturas antiguas;
- memorizar respuestas sin relacionarlas con la implementación;
- exponer tokens o secretos como evidencia.

## Cierre esperado

La evaluación debe dejar una línea base clara para Semana 07: qué está validado, qué quedó pendiente y qué decisiones fueron realmente comprendidas.
