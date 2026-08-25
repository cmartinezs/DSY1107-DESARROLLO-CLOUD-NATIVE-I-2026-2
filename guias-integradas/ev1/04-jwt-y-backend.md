# 04 · JWT, scopes, ownership y Spring Security

## Objetivo

Convertir el backend en un **Resource Server** real que acepte Access Tokens válidos y aplique autorización sin implementar criptografía JWT manualmente.

El estudiante configura y explica:

```text
issuer
audience
scopes
subject
ownership
401 vs 403
```

Spring Security se encarga de la validación técnica del token.

## Antes de comenzar

Debe existir:

```text
OIDC_ISSUER validado
API_AUDIENCE validado contra Access Token real
Access Token obtenible desde Angular/MSAL
```

Usar:

- [00C · Matriz de valores](./00c-matriz-valores-y-checkpoints.md)
- [00D · Scaffolding vs código del estudiante](./00d-scaffolding-vs-codigo-estudiante.md)

## Starter operativo

Seguir:

→ [04A · Starter reproducible Spring Security Resource Server](./04a-starter-spring-security.md)

El starter incluye código exacto para:

```text
SecurityFilterChain
AudienceValidator
JwtDecoder con issuer + audience
/api/me
Task en memoria
GET/POST/DELETE
ownership por jwt.sub
```

No inventar una segunda implementación paralela.

## 1. Dependencias

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

Después de modificarlas, compilar **antes** de escribir más código.

PowerShell:

```powershell
.\mvnw.cmd test
```

Git Bash/Linux/macOS:

```bash
./mvnw test
```

## 2. Configuración externa

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=${OIDC_ISSUER}
cloudtasks.security.audience=${API_AUDIENCE}
```

No hardcodear esos valores en varias clases.

## 3. Validación JWT

La cadena conceptual es:

```text
Access Token recibido
↓
Spring Resource Server
↓
issuer/discovery
↓
JWKS / firma
↓
validaciones temporales
↓
issuer esperado
↓
audience esperada
↓
autenticación válida
```

Decodificar el payload de un JWT no reemplaza este proceso.

## 4. Audience explícita

Un token puede tener firma válida y venir del issuer correcto, pero estar destinado a otro recurso.

Por eso el starter agrega:

```text
jwt.aud contiene API_AUDIENCE
```

Caso esperado:

```text
firma válida
+ issuer válido
+ audience incorrecta
→ 401
```

## 5. Política mínima

```text
GET    /api/public/health → público
GET    /api/me            → authenticated
GET    /api/tasks         → tasks.read
POST   /api/tasks         → tasks.write
DELETE /api/tasks/{id}    → tasks.write + ownership
```

La ruta base no obliga a implementar `Admin`. Roles queda como extensión después de que scopes y ownership funcionen correctamente.

## 6. Scopes

Con el mapping estándar esperado:

```text
tasks.read  → SCOPE_tasks.read
tasks.write → SCOPE_tasks.write
```

Observar primero el token real. No escribir un converter custom si Spring ya transforma correctamente `scp`/`scope`.

## 7. `/api/me`

Debe devolver solo información sanitizada:

```json
{
  "subject": "...",
  "issuer": "...",
  "audience": ["..."],
  "scopes": ["tasks.read"],
  "roles": []
}
```

Nunca devolver el token completo.

## 8. Datos deliberadamente mínimos

```text
Task
- id
- title
- ownerId
```

Persistencia:

```text
memoria del proceso
```

No agregar JPA, base de datos, migrations, repositories ni Docker para resolver esta etapa.

## 9. Ownership

Al crear una tarea:

```text
ownerId = JWT.sub
```

No aceptar `ownerId` enviado por Angular.

Al eliminar:

```text
tasks.write
+
JWT.sub == task.ownerId
→ permitido
```

Si el scope es correcto pero el recurso pertenece a otro sujeto:

```text
403
```

Esto distingue:

```text
scope       → autorización sobre una clase de operación
ownership   → autorización sobre un recurso concreto
```

## Checkpoint 04-0 · build

```text
Maven Wrapper PASS
Java compile PASS
variables runtime definidas PASS
```

## Checkpoint 04-1 · frontera pública/protegida

```text
GET /api/public/health sin token → 200
GET /api/tasks sin token         → 401
```

## Checkpoint 04-2 · validación JWT

| Caso | Esperado |
|---|---|
| token alterado | 401 |
| token expirado | 401 |
| issuer incorrecto | 401 |
| audience incorrecta | 401 |
| Access Token correcto | autenticación válida |

## Checkpoint 04-3 · scopes

| Caso | Esperado |
|---|---|
| GET sin `tasks.read` | 403/rechazo |
| GET con `tasks.read` | 200 |
| POST sin `tasks.write` | 403/rechazo |
| POST con `tasks.write` | 201 |

## Checkpoint 04-4 · ownership

```text
usuario A crea tarea
usuario B posee tasks.write
usuario B intenta borrar tarea de A
→ 403
```

## Qué NO se programa

```text
generación/firma JWT
RSA
parser JWT
rotación JWKS
Authorization Code
PKCE
refresh-token flow
password database
```

Cada una de esas responsabilidades pertenece a un framework/servicio ya utilizado.

## Roles · profundización posterior

Si se desea observar `roles`, primero comprobar el claim real. Spring no debe suponerse configurado para `ROLE_Admin` solo porque el token contiene una lista `roles`.

Agregar un converter de roles únicamente como extensión consciente, sin bloquear la ruta principal.

## Puerta de validación 04

No desplegar a EC2 hasta obtener:

```text
04-0 build PASS
04-1 200/401 PASS
04-2 JWT issuer/audience PASS
04-3 scopes PASS
04-4 ownership PASS
```

**SI FALLA** · diagnosticar en orden:

```text
Access Token real
→ iss
→ aud
→ exp
→ authority SCOPE_...
→ ownership
```

No agregar otra librería JWT como primera reacción.

## Contenido relacionado

- [04A · Starter Spring Security](./04a-starter-spring-security.md)
- [JWT y claims](../../semanas/semana-03/01-jwt-claims.md)
- [Gateway vs backend](../../semanas/semana-03/02-seguridad-api/01-gateway-vs-backend.md)
