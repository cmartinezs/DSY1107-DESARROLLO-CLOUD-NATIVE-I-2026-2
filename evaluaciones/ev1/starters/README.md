# EV1 · Starter kits de seguridad

Este directorio reúne código base y material técnico de apoyo para incorporar los aprendizajes de seguridad de EV1 en proyectos propios.

## Propósito

Los starters reducen boilerplate y errores de configuración sin reemplazar la comprensión de los conceptos evaluables.

Cada estudiante debe ser capaz de explicar qué responsabilidad cumple cada pieza incorporada a su proyecto y demostrar su comportamiento.

## Arquitectura objetivo

```text
Usuario
  │
  ▼
Angular / React SPA
  │
  │ Authorization Code + PKCE
  ▼
IDaaS
  │
  │ Access Token JWT
  ▼
Frontend SPA
  │
  │ Authorization: Bearer <access_token>
  ▼
API Manager / Gateway
  │
  ▼
Spring Boot API
Resource Server
```

## Regla fundamental

La aplicación de negocio **no implementa su propio Authorization Server**.

- El IDaaS autentica al usuario.
- El IDaaS ejecuta el flujo de autorización y emite los tokens.
- El frontend obtiene un Access Token mediante MSAL.
- El frontend envía el Access Token a la API como Bearer Token.
- Spring Boot valida el token y aplica autorización.

## Material disponible

1. [Flujo Authorization Code + PKCE con IDaaS](./01-flujo-authorization-code-pkce.md)
2. [Toolkit conceptual JWT](./jwt-conceptos/README.md)
3. [Spring Boot Resource Server Starter](./spring-resource-server/README.md)
4. [React + MSAL Starter](./react-msal/README.md)
5. [Angular + MSAL Starter](./angular-msal/README.md)
6. [Kit de integración y pruebas](./testing/README.md)

## Contrato común de frontend

Independientemente de si el equipo usa React o Angular, debe poder reconocer y demostrar:

```text
login
logout
usuario autenticado
cuenta actual
adquisición de Access Token
llamada autenticada a API
```

El framework cambia la implementación, no el aprendizaje evaluable.

## Evidencia mínima de seguridad

La integración debe permitir reproducir deliberadamente:

```text
200  recurso público
401  recurso protegido sin token
401  recurso protegido con token inválido
200  recurso protegido con token válido
403  token válido sin permiso suficiente
2xx  token válido con permiso suficiente
```

La evidencia nunca debe publicar un Access Token completo.

## Estado

- ✅ Modelo conceptual Authorization Code + PKCE.
- ✅ Toolkit conceptual JWT.
- ✅ Spring Security Resource Server Starter.
- ✅ React + MSAL.
- ✅ Angular + MSAL.
- ✅ Kit de integración y pruebas.
- ⏭️ Aplicaciones mínimas de referencia end-to-end.

Los componentes se incorporan progresivamente y se declaran disponibles cuando su etapa queda cerrada.
