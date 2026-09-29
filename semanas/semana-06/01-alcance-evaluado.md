# Alcance evaluado · Parcial 1

La evaluación integra la primera experiencia de aprendizaje.

## Capacidades observables

- API Manager / API Gateway configurado;
- Identity as a Service integrado;
- SPA autenticada mediante MSAL / Authorization Code + PKCE;
- Access Token para la API propia;
- backend Spring Boot protegido;
- diferencia entre 401 y 403;
- arquitectura cloud explicable de extremo a extremo.

## Fuera de alcance

Semana 06 no exige mensajería RabbitMQ ni streaming Kafka. Tampoco corresponde ampliar CRUD para compensar una integración de seguridad incompleta.

## Pregunta guía

> ¿Puedes explicar qué componente valida identidad, qué componente enruta y qué componente aplica reglas de negocio?


## Qué significa “integrar”

La evaluación no observa piezas aisladas, sino la relación entre ellas.

No basta con mostrar:

- un frontend que inicia sesión;
- un token;
- un Gateway;
- una API protegida.

Debe existir un flujo coherente entre todos esos componentes.

## Evidencia por capacidad

### API Manager / Gateway

Debe poder demostrarse routing y, cuando corresponda, política de seguridad.

### Identity as a Service

Debe existir un proveedor configurado y un flujo de autenticación reproducible.

### SPA

Debe solicitar el token correcto para la API y enviarlo como Bearer.

### Backend

Debe actuar como Resource Server y aplicar autorización coherente.

### Diagnóstico

El estudiante debe distinguir 401, 403 y 2xx y relacionarlos con una frontera concreta.

## Profundidad esperada

No se espera memorizar internals del proveedor cloud. Sí se espera comprender conceptos transferibles:

- cliente público;
- recurso protegido;
- issuer;
- audience;
- scopes/roles;
- Gateway;
- Resource Server;
- defensa en profundidad.

## Cambios de contexto

Una buena defensa puede incluir preguntas hipotéticas:

- ¿qué cambia si el issuer cambia?;
- ¿qué cambia si el endpoint requiere otro scope?;
- ¿qué ocurre si el token está destinado a otro recurso?;
- ¿qué responsabilidad permanece en backend aunque exista Gateway?;
- ¿qué evidencia usarías para diagnosticar un 401?

## Criterio de suficiencia

La solución es suficiente cuando el estudiante puede demostrar el flujo y razonar sobre él sin depender de una receta textual.
