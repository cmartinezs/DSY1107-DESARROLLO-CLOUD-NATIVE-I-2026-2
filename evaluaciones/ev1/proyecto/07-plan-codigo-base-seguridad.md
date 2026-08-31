# Plan de código base de seguridad para EV1

## Estado

- ✅ **Etapa 1 — Modelo conceptual OAuth 2.0 / OIDC con IDaaS:** implementada en `evaluaciones/ev1/starters/01-flujo-authorization-code-pkce.md`.
- ✅ **Etapa 2 — Toolkit JWT conceptual:** implementada en `evaluaciones/ev1/starters/jwt-conceptos/`.
- ⏭️ **Etapa 3 — Spring Security Resource Server Starter:** siguiente etapa.
- ⬜ Etapa 4 — Starter MSAL para React y Angular.
- ⬜ Etapa 5 — Kit de integración y pruebas.
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
        ├── 01-flujo-authorization-code-pkce.md
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
        │   └── ...
        ├── angular-msal/
        │   └── ...
        ├── jwt-conceptos/
        │   ├── README.md
        │   ├── pom.xml
        │   ├── JwtCreateAndSignExample.java
        │   ├── JwtDecodeExample.java
        │   ├── JwtVerifyExample.java
        │   └── JwtTamperExample.java
        │
        └── testing/
            └── ...
```

La estructura puede ajustarse durante la implementación, pero debe preservar la separación entre frontend, Resource Server, material conceptual JWT y pruebas.

# Plan de implementación

## Etapa 1 — Modelo conceptual OAuth 2.0 / OIDC con IDaaS — ✅ COMPLETADA

Entregable: `evaluaciones/ev1/starters/01-flujo-authorization-code-pkce.md`.

Cubre flujo Authorization Code + PKCE, responsabilidades de SPA/IDaaS/Gateway/Resource Server, Bearer Token y diferencias 401/403/2xx.

## Etapa 2 — Toolkit JWT conceptual — ✅ COMPLETADA

Entregable: `evaluaciones/ev1/starters/jwt-conceptos/`.

Incluye:

- creación de claims y construcción de JWT;
- firma local didáctica;
- decodificación de header/payload sin validación;
- validación de firma;
- validación automática de expiración;
- comprobación de issuer y audience;
- alteración del payload para demostrar fallo de integridad;
- proyecto Maven autocontenido;
- instrucciones paso a paso y preguntas de comprobación.

### Restricción preservada

> Estos ejemplos permiten comprender JWT. No deben utilizarse para emitir los Access Tokens de la solución EV1 cuando se utiliza IDaaS.

## Etapa 3 — Spring Security Resource Server Starter — ⏭️ SIGUIENTE

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

El starter no debe implementar login con usuario/password, almacenamiento de credenciales, generación/firma propia de Access Tokens ni endpoint `/oauth2/token`.

## Etapa 4 — Starter MSAL para React y Angular

Debe permitir login, logout, usuario autenticado, cuenta actual, adquisición de Access Token y llamada autenticada a API. MSAL ejecutará Authorization Code + PKCE contra el proveedor de identidad.

## Etapa 5 — Kit de integración y pruebas

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
3. ⬜ starter Spring Resource Server;
4. ⬜ starter React + MSAL;
5. ⬜ starter Angular + MSAL;
6. ⬜ kit de pruebas y evidencia;
7. ⬜ aplicación mínima React validada end-to-end;
8. ⬜ aplicación mínima Angular validada end-to-end;
9. ⬜ documentación de incorporación paso a paso para estudiantes.

Hasta completar todos los puntos, este documento actúa como plan canónico de construcción del código base de seguridad para EV1.