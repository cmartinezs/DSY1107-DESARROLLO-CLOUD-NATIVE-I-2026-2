# 00D · Qué genera la herramienta y qué modifica el estudiante

## Objetivo

Evitar que CloudTasks se transforme accidentalmente en un curso paralelo de Spring, Angular o protocolos de identidad. La guía separa explícitamente el **scaffolding generado**, la **configuración que el estudiante debe comprender** y el **código mínimo que sí debe escribir o adaptar**.

## Regla general

```text
si una herramienta puede generar boilerplate correctamente
→ se usa la herramienta

si una librería implementa un protocolo estándar
→ se configura la librería

si el aprendizaje está en una decisión o política
→ el estudiante configura/explica esa decisión

si se necesita código de dominio solo para observar el flujo
→ se mantiene mínimo
```

## Matriz canónica

| Elemento | Quién lo resuelve | Qué debe hacer el estudiante | Qué debe poder explicar |
|---|---|---|---|
| estructura Maven/Spring Boot | IntelliJ + Spring Initializr | seleccionar Java 21, Maven y dependencias | qué artefactos fueron generados |
| `mvnw`, `mvnw.cmd`, `.mvn/` | Spring Initializr | conservarlos y usarlos | por qué no se necesita Maven global |
| clase `@SpringBootApplication` | Spring Initializr | no reescribirla | qué inicia Spring Boot |
| proyecto Angular | Angular CLI | ejecutar `ng new ... --skip-git` | qué archivos son scaffolding |
| build Angular | Angular CLI | ejecutar `npm start` / `ng build` | diferencia dev server vs build |
| OAuth2/OIDC Authorization Code + PKCE | MSAL Browser/MSAL Angular | configurar clientId, authority, redirect URI y scopes | authorization code, PKCE, ID Token y Access Token |
| adquisición/inyección del Access Token | `MsalInterceptor` | definir `protectedResourceMap` | por qué se envía Bearer solo a recursos conocidos |
| validación criptográfica JWT | Spring Security Resource Server | configurar issuer/audience/políticas | firma, `iss`, `aud`, `exp`, JWKS |
| JWT Authorizer de borde | AWS API Gateway | configurar issuer, audience y scopes por ruta | Gateway vs backend security |
| endpoint health | estudiante | escribir controller mínimo | request/response HTTP y JSON |
| CORS local | estudiante | configurar origen/métodos/headers explícitos | Same-Origin Policy y preflight |
| `/api/me` | starter + estudiante adapta package/config | mantener salida sanitizada | claims relevantes |
| `Task` en memoria | starter | adaptar solo lo necesario | por qué no se necesita BD |
| ownership | estudiante/starter | conservar regla `sub == ownerId` | scope vs autorización de negocio |
| despliegue JAR | estudiante siguiendo runbook | empaquetar/copiar/configurar runtime | artefacto, proceso y puerto |
| ★ Dockerfile | starter avanzado | construir y ejecutar imagen | imagen vs contenedor |
| tenant/user flow/apps/scopes | Microsoft Entra admin center | crear/configurar recursos | responsabilidad de cada recurso |
| EC2/API Gateway | AWS Console | crear/configurar recursos | responsabilidad de cada capa |

## Código que NO se escribe manualmente

No programar desde cero:

```text
OAuth2
OIDC
PKCE
firma JWT
verificación RSA
parser JWT
rotación JWKS
servidor de identidad
login con usuario/contraseña propio
Maven Wrapper
estructura Angular
framework de interceptores HTTP
```

## Código manual permitido porque hace observable el aprendizaje

```text
PublicController
CorsConfig local
configuración MSAL
ApiService mínimo
SecurityConfig
AudienceValidator
/api/me
Task + TaskService en memoria
ownership
UI mínima para ejecutar las llamadas
```

Aun en estos casos se entrega un starter conocido para que el tiempo se invierta en **configurar, ejecutar, diagnosticar y explicar**, no en adivinar APIs de framework.

## Checkpoint 00D

Antes de comenzar a programar, el estudiante debe poder responder:

1. ¿qué parte generará IntelliJ/Spring Initializr?
2. ¿qué parte generará Angular CLI?
3. ¿qué parte implementará MSAL?
4. ¿qué parte validará Spring Security?
5. ¿qué parte validará API Gateway?
6. ¿qué código sí deberá adaptar manualmente y por qué?

Si una tarea posterior parece pedir mucho código incidental, volver a esta matriz antes de agregar complejidad.
