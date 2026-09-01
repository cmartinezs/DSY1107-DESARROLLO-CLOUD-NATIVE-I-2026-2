# Plan de código base de seguridad para EV1

## Estado

- ✅ **Etapa 1 — Modelo conceptual OAuth 2.0 / OIDC con IDaaS:** implementada en `evaluaciones/ev1/starters/01-flujo-authorization-code-pkce.md`.
- ✅ **Etapa 2 — Toolkit JWT conceptual:** implementada en `evaluaciones/ev1/starters/jwt-conceptos/`.
- ✅ **Etapa 3 — Spring Security Resource Server Starter:** implementada en `evaluaciones/ev1/starters/spring-resource-server/`.
- ✅ **Etapa 4 — Starter MSAL para React y Angular:** implementada en `evaluaciones/ev1/starters/react-msal/` y `evaluaciones/ev1/starters/angular-msal/`.
- ✅ **Etapa 5 — Kit de integración y pruebas:** implementada en `evaluaciones/ev1/starters/testing/`.
- ⏭️ **Etapa 6 — Aplicaciones mínimas de referencia:** siguiente etapa.

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

Incluye configuración MSAL externalizada, `MsalProvider`, login/logout por redirect, cuenta autenticada, adquisición silenciosa de Access Token con fallback interactivo, componente protegido y cliente HTTP Bearer.

### Angular

Entregable: `evaluaciones/ev1/starters/angular-msal/`.

Incluye `PublicClientApplication`, providers, manejo de redirect, cuenta activa, login/logout, adquisición silenciosa de Access Token con fallback, guard e interceptor pedagógicos y configuración de entorno.

### Contrato pedagógico común

```text
login
logout
usuario autenticado
cuenta actual
adquisición de Access Token
llamada autenticada a API
```

MSAL ejecuta Authorization Code + PKCE contra el proveedor de identidad. Una SPA no utiliza `client_secret`, el Access Token se envía a la API y la protección frontend no sustituye autorización backend.

## Etapa 5 — Kit de integración y pruebas — ✅ COMPLETADA

Entregable: `evaluaciones/ev1/starters/testing/`.

Incluye:

- `README.md` como guía central;
- `matriz-200-401-403.md` con escenarios reproducibles;
- `requests.http` para pruebas directas;
- `inspeccion-claims.md` para observar `iss`, `aud`, `exp`, scopes y roles sin exponer tokens;
- `checklist-diagnostico.md` para aislar problemas por capas: frontend, token, Spring Resource Server y Gateway.

### Evidencia mínima requerida

```text
200  recurso público
401  recurso protegido sin token
401  recurso protegido con token inválido
200  recurso protegido con token válido
403  token válido sin permiso suficiente
2xx  token válido con permiso suficiente
```

La evidencia debe asociar precondición, request, permisos, status observado y explicación. Nunca debe publicar un Access Token completo.

## Etapa 6 — Aplicaciones mínimas de referencia — ⏭️ SIGUIENTE

Se validarán clientes mínimos React y Angular contra el mismo IDaaS y la misma Spring API antes de declarar los starters estables para uso en EV1.

El objetivo no es construir una aplicación de negocio completa, sino comprobar end-to-end:

```text
login
→ Authorization Code + PKCE
→ Access Token
→ Bearer
→ API/Gateway
→ Spring Resource Server
→ autorización
→ 200 / 401 / 403
```

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
6. ✅ kit de pruebas y evidencia;
7. ⬜ aplicación mínima React validada end-to-end;
8. ⬜ aplicación mínima Angular validada end-to-end;
9. ⬜ documentación de incorporación paso a paso para estudiantes.

Hasta completar todos los puntos, este documento actúa como plan canónico de construcción del código base de seguridad para EV1.
