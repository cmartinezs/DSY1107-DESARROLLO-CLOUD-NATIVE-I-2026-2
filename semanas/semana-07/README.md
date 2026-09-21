# Semana 07 · Cierre Evaluación Parcial 1

## Propósito

La Semana 07 está dedicada al cierre de la **Evaluación Parcial 1: Arquitectura Base e Integración Cloud del Sistema**.

No se introduce un bloque conceptual nuevo. El foco es:

- completar las defensas técnicas pendientes;
- evidenciar dominio individual dentro de una entrega grupal;
- retroalimentar inmediatamente sin informar la calificación durante la defensa;
- consolidar una arquitectura base verificable que será el punto de partida de la Semana 08.

## Evidencia esperada

Cada grupo debe poder demostrar, de forma breve y técnica:

1. API Manager actuando como intermediario entre frontend y backend.
2. Rutas correctamente configuradas.
3. CORS funcional y razonablemente restringido.
4. Tenant y aplicación configurados en el servicio IDaaS.
5. Flujo OIDC Authorization Code + PKCE.
6. Obtención y uso de JWT.
7. Validación de JWT mediante issuer/audience y políticas de acceso.
8. Evidencia de rutas válidas y rechazadas, incluyendo respuestas coherentes 200/401/403.
9. Integración completa frontend → API Manager → backend.

## Defensa técnica

La defensa se realiza como grupo, pero el dominio se evalúa de forma individual.

Para una defensa breve de aproximadamente cinco minutos con tres integrantes, se recomienda:

- una pregunta principal por integrante;
- 60–75 segundos máximos por respuesta;
- una repregunta corta únicamente cuando la respuesta no permite determinar el nivel de dominio;
- preguntas distintas entre grupos para reducir respuestas memorizadas.

La pregunta debe comprobar comprensión y razonamiento, no solo definición de conceptos.

## Criterios oficiales cubiertos

### IL 1.1 · API Manager · 20 %

- rutas entre frontend y backend: 13 %;
- CORS: 7 %.

### IL 1.2 · Identity as a Service · 45 %

- tenant: 10 %;
- registro/configuración de aplicación: 10 %;
- flujo de registro/login y tokens: 10 %;
- OIDC Authorization Code + PKCE: 15 %.

### IL 1.3 · Integración API Manager + IDaaS · 35 %

- validación JWT sobre rutas: 20 %;
- evidencia del funcionamiento y JSON esperado: 15 %.

## Recursos

- [Banco de preguntas de defensa](./banco-preguntas-defensa.md)
- [Dominio de identidad y acceso](../../docs/identity/)
- [Proyecto formativo](../../proyecto-formativo/)

## Salida de Semana 07

Al finalizar la semana, cada grupo debe quedar con:

- EV1 defendida;
- evidencia individual de dominio;
- retroalimentación técnica recibida;
- arquitectura base identificada;
- integración cloud demostrable;
- principales deudas o pendientes conocidos;
- baseline preparado para continuar desde Semana 08.
