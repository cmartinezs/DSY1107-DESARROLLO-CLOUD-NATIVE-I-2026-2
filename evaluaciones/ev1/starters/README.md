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
- El frontend obtiene un Access Token mediante el SDK correspondiente.
- El frontend envía el Access Token a la API como Bearer Token.
- Spring Boot valida el token y aplica autorización.

## Material disponible

1. [Flujo Authorization Code + PKCE con IDaaS](./01-flujo-authorization-code-pkce.md)

## Próximos starters planificados

- `jwt-conceptos/`
- `spring-resource-server/`
- `react-msal/`
- `angular-msal/`
- `testing/`

Estos componentes se incorporarán progresivamente y se declararán listos para uso una vez validados.