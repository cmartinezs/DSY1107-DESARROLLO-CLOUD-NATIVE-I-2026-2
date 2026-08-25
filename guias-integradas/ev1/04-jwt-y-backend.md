# 04 · JWT, scopes, roles y Spring Security

## Objetivo

Convertir el backend en un **Resource Server** real que acepte Access Tokens válidos y aplique autorización.

El alumno configura issuer, audience y scopes; no implementa criptografía JWT, parsers ni login propio.

## Starter operativo

Usar:

→ [04A · Starter mínimo Spring Security](./04a-starter-spring-security.md)

El starter reduce el código a:

```text
SecurityFilterChain
validación explícita audience
/api/me
Task en memoria
ownership
```

## Dependencias

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

Validar con wrapper:

```bash
./mvnw test
```

## Issuer

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=${OIDC_ISSUER}
```

Spring usa metadata/JWKS para validación. No copiar public keys manualmente si discovery funciona.

## Audience

Además del issuer, CloudTasks debe rechazar tokens dirigidos a otro recurso.

Validar explícitamente:

```text
jwt.aud contiene API_AUDIENCE
```

Esto permite observar:

```text
firma válida + issuer válido + audience incorrecta
→ rechazo / 401
```

## Política mínima

```text
GET    /api/public/health → público
GET    /api/me            → authenticated
GET    /api/tasks         → tasks.read
POST   /api/tasks         → tasks.write
DELETE /api/tasks/{id}    → tasks.write + ownership
GET    /api/admin/stats   → Admin, solo si sandbox permite roles
```

## Scopes

Observar primero el Access Token real. Confirmar si Entra emite `scp`/scope y cómo Spring lo traduce a authorities.

No escribir converter custom si el mapping estándar ya produce:

```text
SCOPE_tasks.read
SCOPE_tasks.write
```

## `/api/me`

Debe devolver únicamente claims sanitizados:

```json
{
  "subject": "...",
  "issuer": "...",
  "audience": ["..."],
  "scopes": ["tasks.read"],
  "roles": []
}
```

No devolver el token completo.

## Datos

Modelo mínimo:

```text
Task
- id
- title
- ownerId
```

Persistencia en memoria. Sin JPA, DB, migrations o repositories porque no aportan al objetivo de esta práctica.

## Ownership

```text
tasks.write
+
JWT.sub == task.ownerId
→ DELETE permitido
```

Si scope correcto pero recurso ajeno:

```text
403
```

Esto distingue autorización técnica por scope de autorización de negocio.

## Checkpoint 04-1 · seguridad básica

```text
health sin token → 200
tasks sin token  → 401
```

## Checkpoint 04-2 · JWT

| Caso | Esperado |
|---|---|
| token alterado | 401 |
| token expirado | 401 |
| issuer incorrecto | 401 |
| audience incorrecta | 401 |
| Access Token correcto | autenticación válida |

## Checkpoint 04-3 · autorización

| Caso | Esperado |
|---|---|
| sin `tasks.read` | 403/rechazo |
| con `tasks.read` | GET 200 |
| sin `tasks.write` | 403/rechazo |
| con `tasks.write` | POST permitido |
| recurso ajeno | 403 |

## Qué NO programa el alumno

```text
generación/firma JWT
RSA
JWKS rotation
Authorization Code
PKCE
refresh flow
password database
```

Debe explicar qué componente realiza cada responsabilidad.

## Puerta de validación 04

No desplegar a EC2 hasta que todos los checkpoints locales sean PASS.

**SI FALLA** · revisar en orden: Access Token real → iss → aud → exp → scope/authority → ownership. No agregar librerías JWT externas como primera reacción.

## Contenido relacionado

- [04A · Starter Spring Security](./04a-starter-spring-security.md)
- [Matriz de valores](./00c-matriz-valores-y-checkpoints.md)
- [JWT y claims](../../semanas/semana-03/01-jwt-claims.md)
- [Gateway vs backend](../../semanas/semana-03/02-seguridad-api/01-gateway-vs-backend.md)
