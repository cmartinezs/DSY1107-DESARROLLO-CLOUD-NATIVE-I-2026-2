# Semana 07 · Cierre Evaluación Parcial 1 + baseline arquitectónico

**Periodo:** 21 al 26 de septiembre de 2026  
**RA:** RA1 · IL1.1, IL1.2, IL1.3

## Propósito

Cerrar las defensas de la Evaluación Parcial 1 y dejar una línea base reproducible antes de iniciar mensajería asíncrona.

No se introduce RA2 todavía.

## Ruta

1. [Defensa técnica](./01-defensa-tecnica.md)
2. [Evidencia y trazabilidad](./02-evidencia-y-trazabilidad.md)
3. [Cierre de la Experiencia 1](./03-cierre-ea1.md)

## Proyecto formativo

→ [RegistrApp · Semana 07](../../proyecto-formativo/semana-07/)

## Salida esperada

```text
Experiencia 1 cerrada
→ baseline conocido
→ deuda explícita
→ entrada segura a Semana 08
```


## Sentido de la semana

Semana 07 no agrega una nueva capa tecnológica. Su objetivo es cerrar correctamente la Experiencia de Aprendizaje 1 y evitar que Semana 08 comience sobre una base ambigua.

El cierre debe dejar tres cosas explícitas:

1. qué funciona y fue demostrado;
2. qué deuda quedó pendiente;
3. qué arquitectura se considera baseline para continuar.

## Criterio de baseline

Una línea base útil no significa “todo perfecto”. Significa que el estado del proyecto es conocido, reproducible y explicable.

La entrada a Semana 08 debería responder:

- ¿qué flujo seguro quedó operativo?;
- ¿qué componentes están realmente integrados?;
- ¿qué fallas siguen abiertas?;
- ¿qué decisiones son intencionales?;
- ¿qué evidencia permite reconstruir el estado?

## Puente conceptual

La Experiencia 1 trabajó principalmente request/response protegido.

Semana 08 introduce procesamiento desacoplado.

El cambio no elimina lo anterior. La nueva arquitectura suma otra forma de comunicación sobre una base que debe seguir siendo segura y comprensible.

## Regla de cierre

No arrastrar deuda silenciosa.

Toda deuda relevante debe quedar nombrada antes de abrir la siguiente experiencia.
