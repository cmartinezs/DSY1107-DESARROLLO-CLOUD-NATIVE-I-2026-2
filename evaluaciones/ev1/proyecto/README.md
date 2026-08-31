# EV1 · Proyecto Web Full Stack

Este directorio define el **mínimo técnico obligatorio** que debe cumplir la webapp de cada equipo para quedar preparada para implementar, configurar y demostrar los aprendizajes evaluables de la Evaluación Parcial 1.

## Regla principal

El dominio funcional de la aplicación es **libre**. Cada equipo puede elegir su temática, nombre, problemática y diseño.

La libertad de dominio no elimina el contrato técnico mínimo: la solución debe tener suficiente estructura para demostrar de forma efectiva frontend SPA, API REST, persistencia, autenticación/autorización, JWT, permisos, API Manager/Gateway y seguridad backend cuando corresponda en EV1.

> **El dominio es libre. La arquitectura mínima y las capacidades técnicas requeridas no lo son.**

## Tecnologías base

- Frontend: **Angular o React**.
- Backend: **Java + Spring Boot**.
- Comunicación: **HTTP + API REST**.
- Persistencia: obligatoria; la tecnología concreta se ajusta a las instrucciones de la asignatura.

## Arquitectura base

```text
Usuario
   │
   ▼
Frontend SPA
Angular o React
   │
   │ HTTP / REST
   ▼
Backend API
Spring Boot
   │
   ▼
Persistencia
```

La solución debe poder evolucionar hacia una arquitectura con Identity Provider, Access Token, API Manager/Gateway y Spring Security sin tener que rehacer el proyecto desde cero.

## Documentos

1. [Arquitectura y alcance mínimo](./01-arquitectura-y-alcance.md)
2. [Requerimientos mínimos de frontend](./02-frontend.md)
3. [Requerimientos mínimos de backend y datos](./03-backend-y-datos.md)
4. [Seguridad, identidad y autorización](./04-seguridad-identidad-autorizacion.md)
5. [Preparación para API Manager y cloud](./05-api-manager-y-cloud.md)
6. [Demostrabilidad y checklist de preparación](./06-demostrabilidad-y-checklist.md)
7. [Plan de código base de seguridad y starters](./07-plan-codigo-base-seguridad.md)

## Código base de apoyo

Para los aprendizajes de seguridad se construirá material reutilizable orientado a estudiantes: starters para Spring Security Resource Server, React + MSAL, Angular + MSAL, ejemplos conceptuales de JWT y un kit de pruebas 200/401/403.

La estrategia y frontera arquitectónica están definidas en el [plan de código base de seguridad](./07-plan-codigo-base-seguridad.md). KeyGo se utilizará como implementación de referencia para estudiar internamente Authorization Code + PKCE, emisión/firma de tokens y validaciones propias de un Authorization Server; la solución EV1 delegará esas responsabilidades al IDaaS y mantendrá Spring Boot como Resource Server.

## Antes de la configuración cloud

Como mínimo, el equipo debe poder demostrar localmente:

- frontend Angular o React funcional;
- backend Spring Boot funcional;
- comunicación real frontend → backend;
- persistencia;
- al menos dos conceptos o entidades de dominio relacionadas;
- consulta y modificación de información;
- endpoints con diferentes necesidades de acceso;
- manejo coherente de HTTP;
- configuración separada del código;
- ausencia de secretos versionados.

Los ejercicios/laboratorios cloud oficiales permanecen en **AVA**. Este proyecto es la base propia del equipo sobre la cual se aplicarán los aprendizajes y configuraciones evaluables de EV1.

## Pedidos360

`Pedidos360` es una referencia nominal presente en documentación institucional. **No obliga a los equipos a utilizar ese dominio ni reemplaza su proyecto propio.** Los requerimientos evaluativos se trasladan al dominio elegido por cada equipo.