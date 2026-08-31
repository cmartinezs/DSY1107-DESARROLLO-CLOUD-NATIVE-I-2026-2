# Plan de código base de seguridad para EV1

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

### Modelo objetivo EV1

```text
                       ┌──────────────────┐
                       │      IDaaS       │
                       │ Authorization    │
                       │ Token endpoint   │
                       │ firma JWT        │
                       └────────▲─────────┘
                                │
                   Authorization Code + PKCE
                                │
┌──────────┐       ┌────────────┴────────────┐
│ Usuario  │──────►│ Angular / React SPA     │
└──────────┘       │ MSAL                    │
                   └────────────┬────────────┘
                                │ Access Token
                                ▼
                   ┌─────────────────────────┐
                   │ API Manager / Gateway   │
                   └────────────┬────────────┘
                                │ Bearer JWT
                                ▼
                   ┌─────────────────────────┐
                   │ Spring Boot API         │
                   │ Resource Server         │
                   │                         │
                   │ valida                  │
                   │ - firma                 │
                   │ - issuer                │
                   │ - audience              │
                   │ - expiración            │
                   │ - scopes / roles        │
                   └─────────────────────────┘
```

### Regla principal

> Con IDaaS, el backend de la aplicación EV1 **no autentica credenciales ni crea o firma el Access Token**. La API actúa como Resource Server y valida tokens emitidos por el proveedor de identidad.

La creación y firma manual de JWT podrá utilizarse como material conceptual y demostrativo, no como mecanismo de autenticación productivo de la solución evaluada.

## Estrategia de entrega

Para EV1 se privilegiarán **starter kits de código fuente** por sobre una librería propia que oculte la implementación.

Esto permite que los estudiantes puedan:

- leer el código;
- modificarlo;
- depurarlo;
- relacionarlo con los conceptos vistos en clases;
- incorporarlo directamente a su proyecto;
- explicar qué responsabilidad cumple cada pieza.

No se debe entregar una dependencia que reduzca todo el flujo a llamadas opacas como `login()` o `secureApi()` sin que la arquitectura sea observable.

## Estructura objetivo

```text
evaluaciones/
└── ev1/
    ├── proyecto/
    │   └── ...
    │
    └── starters/
        ├── README.md
        │
        ├── spring-resource-server/
        │   ├── README.md
        │   ├── pom-dependencies.xml
        │   ├── SecurityConfig.java
        │   ├── AudienceValidator.java
        │   ├── AuthoritiesConverter.java
        │   ├── RestAuthenticationEntryPoint.java
        │   ├── RestAccessDeniedHandler.java
        │   └── application.example.yml
        │
        ├── react-msal/
        │   ├── README.md
        │   ├── msalConfig.ts
        │   ├── AuthProvider.tsx
        │   ├── ProtectedRoute.tsx
        │   ├── useAccessToken.ts
        │   ├── apiClient.ts
        │   └── .env.example
        │
        ├── angular-msal/
        │   ├── README.md
        │   ├── auth.config.ts
        │   ├── auth.service.ts
        │   ├── auth.guard.ts
        │   ├── auth.interceptor.ts
        │   └── environment.example.ts
        │
        ├── jwt-conceptos/
        │   ├── README.md
        │   ├── JwtCreateExample.java
        │   ├── JwtSignExample.java
        │   ├── JwtVerifyExample.java
        │   └── JwtDecodeExample.java
        │
        └── testing/
            ├── README.md
            ├── matriz-401-403.md
            └── requests.http
```

La estructura podrá ajustarse durante la implementación, pero debe preservar la separación entre frontend, Resource Server, material conceptual JWT y pruebas.

# Plan de implementación

## Etapa 1 — Modelo conceptual OAuth 2.0 / OIDC con IDaaS

### Objetivo

Documentar el flujo Authorization Code + PKCE y delimitar claramente las responsabilidades del frontend, proveedor de identidad, API Gateway y Resource Server.

### Flujo conceptual

```text
Authorization Request
        ↓
code_challenge
        ↓
autenticación en IDaaS
        ↓
authorization_code
        ↓
code_verifier
        ↓
token endpoint del IDaaS
        ↓
access_token
```

### Entregable esperado

Documento de arquitectura y responsabilidades del flujo IDaaS.

Debe permitir identificar claramente qué componente ejecuta cada responsabilidad y por qué el backend de negocio no debe implementar un Authorization Server.

## Etapa 2 — Toolkit JWT conceptual

### Objetivo

Permitir experimentar de manera aislada con JWT antes de utilizar tokens emitidos por el IDaaS.

### Ejemplos mínimos

- creación de claims;
- construcción de JWT;
- firma;
- decodificación;
- verificación de firma;
- verificación de expiración;
- modificación del payload para demostrar fallo de integridad.

### Restricción

El material debe indicar explícitamente:

> Estos ejemplos permiten comprender JWT. No deben utilizarse para emitir los Access Tokens de la solución EV1 cuando se utiliza IDaaS.

## Etapa 3 — Spring Security Resource Server Starter

### Objetivo

Entregar una base mínima pero observable para proteger el backend Spring Boot.

### Dependencias principales

- `spring-boot-starter-security`;
- `spring-boot-starter-oauth2-resource-server`.

### Capacidades incluidas

- `SecurityFilterChain`;
- configuración de Resource Server JWT;
- `JwtDecoder` cuando sea necesario configurarlo explícitamente;
- validación de issuer;
- validación de audience;
- conversión de scopes/roles a `GrantedAuthority`;
- CORS;
- manejo REST de 401;
- manejo REST de 403;
- configuración externa mediante variables de entorno.

### Política mínima de ejemplo

```text
/public/**         → permitAll
/api/**            → authenticated
/api/admin/**      → scope o rol requerido
```

### No incluir

El starter no debe implementar:

- login con usuario/password;
- almacenamiento de credenciales;
- `generateToken()`;
- firma propia de Access Tokens;
- endpoint `/oauth2/token` propio.

## Etapa 4 — Starter MSAL para React y Angular

### Objetivo

Permitir que ambas tecnologías frontend implementen el mismo contrato de autenticación sin exigir implementación manual de OAuth 2.0.

### React

Base prevista:

- `@azure/msal-browser`;
- `@azure/msal-react`.

### Angular

Base prevista:

- `@azure/msal-browser`;
- `@azure/msal-angular`.

### Contrato pedagógico común

Ambas variantes deben permitir reconocer conceptualmente:

```text
login
logout
usuario autenticado
cuenta actual
adquisición de Access Token
llamada autenticada a API
```

MSAL será responsable de ejecutar Authorization Code + PKCE contra el proveedor de identidad.

La generación manual de `code_verifier` y `code_challenge` puede mostrarse de forma aislada para comprender PKCE, pero no será código obligatorio de producción para los estudiantes.

## Etapa 5 — Kit de integración y pruebas

### Objetivo

Convertir la implementación de seguridad en evidencia verificable de EV1.

### Casos mínimos

```text
GET /public/health
→ 200

GET /api/recurso
sin Authorization
→ 401

GET /api/recurso
token inválido
→ 401

GET /api/admin/recurso
token válido sin permiso requerido
→ 403

GET /api/admin/recurso
token válido con permiso requerido
→ 2xx
```

### Claims a observar

Según la configuración del proveedor:

- `iss`;
- `aud`;
- `exp`;
- `scp` y/o `roles`;
- identificador del sujeto (`sub`, `oid` u otro claim equivalente).

Nunca se deben publicar tokens completos como evidencia.

## Etapa 6 — Aplicaciones mínimas de referencia

### Objetivo

Validar los starters antes de declararlos material canónico para estudiantes.

Se deben construir dos clientes mínimos:

```text
React ───┐
         ├── mismo IDaaS
Angular ─┘
              │
              ▼
       misma Spring API
```

La funcionalidad de negocio debe ser deliberadamente mínima. El propósito es demostrar el flujo:

```text
login
→ Authorization Code + PKCE
→ Access Token
→ Authorization: Bearer
→ API Manager/Gateway cuando corresponda
→ Spring Resource Server
→ autorización
→ 200 / 401 / 403
```

Solo después de validar ambos caminos los starters deben considerarse estables para uso en EV1.

# Principios didácticos

## Entender antes de abstraer

Los estudiantes deben comprender los elementos visibles del flujo OAuth 2.0/OIDC y las responsabilidades de cada componente antes de utilizar las abstracciones del SDK.

## Reducir complejidad accidental, no eliminar aprendizaje

El código base debe resolver boilerplate y configuraciones repetitivas, pero debe mantener visibles las decisiones arquitectónicas relevantes.

## Separar demostración conceptual de arquitectura de producción

Crear y firmar JWT manualmente es útil para aprender integridad, claims y firma. Emitir manualmente el Access Token de una aplicación integrada con IDaaS es arquitectónicamente incorrecto para el objetivo de EV1.

## Mismo aprendizaje independiente del framework

React y Angular pueden diferir en implementación, pero deben cubrir el mismo flujo, mismos conceptos y mismas evidencias.

## Seguridad demostrable

Una integración se considera completa cuando el estudiante puede explicar y demostrar el comportamiento de autenticación y autorización, incluyendo al menos diferencias observables entre 200, 401 y 403.

# Criterio de término

El plan se considera implementado cuando existen y han sido probados:

1. documentación del flujo Authorization Code + PKCE con IDaaS;
2. ejemplos conceptuales JWT;
3. starter Spring Resource Server;
4. starter React + MSAL;
5. starter Angular + MSAL;
6. kit de pruebas y evidencia;
7. aplicación mínima React validada end-to-end;
8. aplicación mínima Angular validada end-to-end;
9. documentación de incorporación paso a paso para estudiantes.

Hasta entonces, este documento actúa como plan canónico de construcción del código base de seguridad para EV1.