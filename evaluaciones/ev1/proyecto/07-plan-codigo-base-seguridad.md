# Plan de código base de seguridad para EV1

## Estado

- ✅ **Etapa 1 — Modelo conceptual OAuth 2.0 / OIDC con IDaaS:** implementada en `evaluaciones/ev1/starters/01-flujo-authorization-code-pkce.md`.
- ✅ **Etapa 2 — Toolkit JWT conceptual:** implementada en `evaluaciones/ev1/starters/jwt-conceptos/`.
- ✅ **Etapa 3 — Spring Security Resource Server Starter:** implementada en `evaluaciones/ev1/starters/spring-resource-server/`.
- ✅ **Etapa 4 — Starter MSAL para React y Angular:** implementada en `evaluaciones/ev1/starters/react-msal/` y `evaluaciones/ev1/starters/angular-msal/`.
- ⏭️ **Etapa 5 — Kit de integración y pruebas:** siguiente etapa.
- ⬜ Etapa 6 — Aplicaciones mínimas de referencia.

## Objetivo

Proveer a los estudiantes código base reutilizable que reduzca complejidad accidental al implementar los aprendizajes de seguridad de EV1, sin ocultar los conceptos que deben comprender, configurar y demostrar.

El código base debe permitir trabajar de forma consistente con:

- frontend SPA en React o Angular;
- Authorization Code + PKCE;
- Identity as a Service (IDaaS);
- MSAL en frontend;
- Access Token JWT;
- API Manager/Gateway;
- Spring Boot como Resource Server;
- validación de issuer, audience, expiración, scopes y/o roles;
- respuestas 401 y 403 demostrables.

> Este documento define únicamente el material visible y utilizable por estudiantes. Las referencias internas utilizadas por el equipo docente para diseñar, contrastar o validar técnicamente los starters no forman parte del material de la asignatura y no deben exponerse en documentación, ejemplos ni código entregable a los alumnos.

## Frontera arquitectónica obligatoria

```text
Usuario
   │
   ▼
Angular / React SPA
   │ Authorization Code + PKCE
   ▼
IDaaS
   │ Access Token JWT
   ▼
Frontend SPA
   │ Authorization: Bearer <token>
   ▼
API Manager / Gateway
   │
   ▼
Spring Boot API
Resource Server
```

> Con IDaaS, el backend de la aplicación EV1 **no autentica credenciales ni crea o firma el Access Token**. La API actúa como Resource Server y valida tokens emitidos por el proveedor de identidad.

# Plan de implementación

## Etapa 1 — Modelo conceptual OAuth 2.0 / OIDC con IDaaS — ✅ COMPLETADA

Entregable: `evaluaciones/ev1/starters/01-flujo-authorization-code-pkce.md`.

Cubre flujo Authorization Code + PKCE, responsabilidades de SPA/IDaaS/Gateway/Resource Server, Bearer Token y diferencias 401/403/2xx.

## Etapa 2 — Toolkit JWT conceptual — ✅ COMPLETADA

Entregable: `evaluaciones/ev1/starters/jwt-conceptos/`.

Incluye creación de claims, firma local didáctica, decodificación sin validación, validación de firma/expiración/issuer/audience y alteración del payload para demostrar fallo de integridad.

> Estos ejemplos permiten comprender JWT. No deben utilizarse para emitir los Access Tokens de la solución EV1 cuando se utiliza IDaaS.

## Etapa 3 — Spring Security Resource Server Starter — ✅ COMPLETADA

Entregable: `evaluaciones/ev1/starters/spring-resource-server/`.

Incluye dependencias Maven, `SecurityFilterChain`, `NimbusJwtDecoder`, validación de issuer/audience/tiempo, conversión de scopes y roles, CORS, handlers 401/403, endpoints de demostración y configuración externa.

### Política demostrativa

```text
/public/**
→ permitAll

/api/**
→ authenticated

/api/write/**
→ SCOPE_recurso.write

/api/admin/**
→ ROLE_ADMIN
```

El starter no implementa login con usuario/password, almacenamiento de credenciales, generación/firma propia de Access Tokens ni endpoint `/oauth2/token`.

## Etapa 4 — Starter MSAL para React y Angular — ✅ COMPLETADA

### React

Entregable: `evaluaciones/ev1/starters/react-msal/`.

Incluye:

- configuración MSAL externalizada mediante variables Vite;
- `MsalProvider`;
- login y logout por redirect;
- detección de cuenta autenticada;
- `acquireTokenSilent()` con fallback interactivo;
- componente protegido de ejemplo;
- cliente HTTP que envía `Authorization: Bearer`;
- guía de incorporación y variables de entorno.

### Angular

Entregable: `evaluaciones/ev1/starters/angular-msal/`.

Incluye:

- configuración de `PublicClientApplication`;
- ejemplo de providers para aplicación standalone;
- manejo del resultado de redirect;
- cuenta activa;
- login y logout por redirect;
- adquisición silenciosa de Access Token con fallback interactivo;
- guard pedagógico mínimo;
- interceptor pedagógico que agrega Bearer Token solo a la API configurada;
- configuración de entorno y guía de incorporación.

### Contrato pedagógico común

React y Angular deben exponer conceptualmente las mismas capacidades:

```text
login
logout
usuario autenticado
cuenta actual
adquisición de Access Token
llamada autenticada a API
```

MSAL es responsable de ejecutar Authorization Code + PKCE contra el proveedor de identidad. Los estudiantes no generan manualmente authorization codes, PKCE ni Access Tokens en su aplicación productiva.

### Fronteras preservadas

- una SPA no utiliza `client_secret`;
- el Access Token se envía a la API, no el ID Token;
- proteger una ruta/componente frontend no sustituye autorización en backend;
- scopes y endpoints son ejemplos y deben adaptarse al dominio del proyecto.

## Etapa 5 — Kit de integración y pruebas — ⏭️ SIGUIENTE

Debe convertir la implementación en evidencia verificable: 200 público, 401 sin/invalid token, 403 con token sin permiso y 2xx con permiso correcto; además observación segura de claims relevantes.

## Etapa 6 — Aplicaciones mínimas de referencia

Se validarán clientes mínimos React y Angular contra el mismo IDaaS y la misma Spring API antes de declarar los starters estables para uso en EV1.

# Principios didácticos

- entender antes de abstraer;
- reducir complejidad accidental, no eliminar aprendizaje;
- separar demostración conceptual de arquitectura productiva;
- mantener el mismo aprendizaje independientemente de React o Angular;
- exigir seguridad demostrable, no solo configuración declarada.

# Criterio de término

1. ✅ documentación del flujo Authorization Code + PKCE con IDaaS;
2. ✅ ejemplos conceptuales JWT;
3. ✅ starter Spring Resource Server;
4. ✅ starter React + MSAL;
5. ✅ starter Angular + MSAL;
6. ⬜ kit de pruebas y evidencia;
7. ⬜ aplicación mínima React validada end-to-end;
8. ⬜ aplicación mínima Angular validada end-to-end;
9. ⬜ documentación de incorporación paso a paso para estudiantes.

Hasta completar todos los puntos, este documento actúa como plan canónico de construcción del código base de seguridad para EV1.
