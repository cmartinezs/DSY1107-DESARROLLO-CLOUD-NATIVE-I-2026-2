# Plan de código base de seguridad para EV1

## Estado

- ✅ **Etapa 1 — Modelo conceptual OAuth 2.0 / OIDC con IDaaS:** implementada en `evaluaciones/ev1/starters/01-flujo-authorization-code-pkce.md`.
- ✅ **Etapa 2 — Toolkit JWT conceptual:** implementada en `evaluaciones/ev1/starters/jwt-conceptos/`.
- ✅ **Etapa 3 — Spring Security Resource Server Starter:** implementada en `evaluaciones/ev1/starters/spring-resource-server/`.
- ✅ **Etapa 4 — Starter MSAL para React y Angular:** implementada en `evaluaciones/ev1/starters/react-msal/` y `evaluaciones/ev1/starters/angular-msal/`.
- ✅ **Etapa 5 — Kit de integración y pruebas:** implementada en `evaluaciones/ev1/starters/testing/`.
- 🟡 **Etapa 6 — Aplicaciones mínimas de referencia:** código construido en `evaluaciones/ev1/referencias/`; validación E2E real pendiente de un tenant/configuración IDaaS real.

## Objetivo

Proveer a los estudiantes código base reutilizable que reduzca complejidad accidental al implementar los aprendizajes de seguridad de EV1, sin ocultar los conceptos que deben comprender, configurar y demostrar.

El código base debe permitir trabajar de forma consistente con frontend SPA React o Angular, Authorization Code + PKCE, IDaaS, MSAL, Access Token JWT, API Manager/Gateway y Spring Boot Resource Server.

> Las referencias internas utilizadas por el equipo docente para diseñar, contrastar o validar técnicamente estos materiales no forman parte del material estudiantil.

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

> Con IDaaS, el backend EV1 no autentica credenciales ni crea o firma Access Tokens. Actúa como Resource Server y valida tokens emitidos por el proveedor de identidad.

# Etapas

## 1 — OAuth 2.0 / OIDC + PKCE — ✅

`evaluaciones/ev1/starters/01-flujo-authorization-code-pkce.md`

## 2 — JWT conceptual — ✅

`evaluaciones/ev1/starters/jwt-conceptos/`

## 3 — Spring Security Resource Server — ✅

`evaluaciones/ev1/starters/spring-resource-server/`

Política demostrativa:

```text
/public/**        → permitAll
/api/**           → authenticated
/api/write/**     → SCOPE_recurso.write
/api/admin/**     → ROLE_ADMIN
```

## 4 — React + MSAL / Angular + MSAL — ✅

`evaluaciones/ev1/starters/react-msal/`

`evaluaciones/ev1/starters/angular-msal/`

Contrato común:

```text
login
logout
usuario autenticado
cuenta actual
adquisición de Access Token
llamada autenticada a API
```

## 5 — Integración, pruebas y evidencia — ✅

`evaluaciones/ev1/starters/testing/`

Evidencia mínima:

```text
200  público
401  protegido sin token
401  token inválido
200  token válido
403  token válido sin permiso
2xx  token válido con permiso
```

## 6 — Aplicaciones mínimas de referencia — 🟡 CÓDIGO CONSTRUIDO / E2E PENDIENTE

Entregable: `evaluaciones/ev1/referencias/`.

Incluye:

- backend Spring Boot mínimo compartido;
- cliente React mínimo;
- cliente Angular mínimo;
- mismo conjunto de endpoints y política de autorización;
- configuración externalizada;
- protocolo `VALIDACION-E2E.md` para ejecutar y registrar la prueba real.

Arquitectura:

```text
React ───┐
         ├──► mismo IDaaS
Angular ─┘
              │
              ▼
       mismo Access Token
              │
              ▼
      Spring Boot API
      Resource Server
```

La construcción del código no equivale a validación real. El cierre final de esta etapa exige ejecutar ambos clientes contra el mismo tenant, registros de aplicaciones, scopes/roles y backend configurado realmente.

# Principios didácticos

- entender antes de abstraer;
- reducir complejidad accidental, no eliminar aprendizaje;
- separar demostración conceptual de arquitectura productiva;
- mantener el mismo aprendizaje independientemente de React o Angular;
- exigir seguridad demostrable, no solo configuración declarada.

# Criterio de término

1. ✅ documentación Authorization Code + PKCE;
2. ✅ ejemplos conceptuales JWT;
3. ✅ starter Spring Resource Server;
4. ✅ starter React + MSAL;
5. ✅ starter Angular + MSAL;
6. ✅ kit de pruebas y evidencia;
7. 🟡 aplicación mínima React construida; validación E2E real pendiente;
8. 🟡 aplicación mínima Angular construida; validación E2E real pendiente;
9. ✅ documentación de incorporación/prueba disponible en starters y referencias.

El único pendiente para declarar el plan completamente cerrado es la validación E2E real de ambas referencias contra una configuración IDaaS efectiva.