# Semana 2 · Gestión de APIs + Identity as a Service

**Periodo:** 17 al 22 de agosto de 2026  
**Actividad institucional:** cierre 1.1 + inicio 1.2

← [Volver al índice de semanas](../README.md)

## Objetivo semanal

Cerrar los contenidos pendientes de gestión de APIs y avanzar hacia autenticación moderna mediante OAuth2, OpenID Connect e Identity as a Service, manteniendo el foco en conceptos portables entre proveedores cloud.

## Contenidos oficiales

### Finalizar 1.1 Explorando la gestión de APIs

- **1.1.1** Conociendo un API Manager.
- **1.1.2** Tutorial Creando Nuestro Primer API Manager.
- **1.1.3** Versionando APIs.
- **1.1.4** Configurando CORS en nuestro API Gateway.

### Continuar con 1.2 Implementando autenticación con Identity as a Service

- **1.2.1** OAuth2 y OIDC.
- **1.2.2** Servicio Identity as a Service y CIAM.
- **1.2.3** Configurando un Tenant.
- **1.2.4** Configurando apps en un IDaaS.

## Criterios técnicos de la semana

1. **No asumir que Semana 01 quedó cerrada.** Cada sección debe demostrar v1/v2, CORS y evidencias antes de avanzar completamente a identidad.
2. **OAuth2 no es autenticación por sí solo.** Explicar OAuth2 como framework de autorización y OIDC como capa de identidad/autenticación.
3. **Separar ID token y access token.** El estudiante debe comprender propósito, audiencia y uso conceptual de ambos.
4. **Explicar tenant, aplicación, usuario, issuer, redirect URI, scopes y claims antes de configurarlos.**
5. **Evitar vendor lock-in pedagógico.** Azure y AWS son plataformas principales, pero los conceptos deben poder reconocerse en otros proveedores.
6. **Azure External ID puede estar restringido en Azure for Students.** Si el flujo está bloqueado, se debe utilizar un IDaaS equivalente y documentar la correspondencia conceptual, en vez de perder la sesión intentando resolver permisos ajenos al aprendizaje esperado.

## Progresión sugerida

**API Gateway → versionado/CORS → autenticación vs autorización → OAuth2 → OIDC → IDaaS/CIAM → tenant → app registration → tokens/claims → integración conceptual con gateway/backend.**

## Planificación por sección

El avance real se mantiene separado por sección:

- [DSY1107-002D](./DSY1107-002D.md)
- [DSY1107-003D](./DSY1107-003D.md)

## Evidencia mínima

Al finalizar la semana cada grupo debería poder:

1. demostrar o explicar el cierre del laboratorio API Gateway;
2. diferenciar API, Gateway y API Management;
3. explicar versionado de contrato y CORS;
4. distinguir autenticación y autorización;
5. explicar OAuth2 vs OIDC;
6. distinguir access token e ID token;
7. explicar tenant, app, usuario, scope y claim;
8. mostrar una configuración básica en un IDaaS o una alternativa equivalente;
9. representar el flujo completo cliente → identidad → gateway/API → backend;
10. defender técnicamente las decisiones tomadas sin depender de una presentación PPT.

## Material

- [Biblioteca pública del curso](https://drive.google.com/drive/folders/1UOZMZcEbtfKFq4ygWKj3Yi8VEHkW7hx1?usp=sharing)
- [Semana 02 · 17 al 22 de agosto](https://drive.google.com/drive/folders/1Bi-w4_ZCWIffbYsgekkcvWE6oDV2rwDQ)

> La coordinación autoriza utilizar tecnologías equivalentes siempre que se conserve el resultado de aprendizaje. Esa flexibilidad se aplicará explícitamente ante restricciones de Azure External ID.