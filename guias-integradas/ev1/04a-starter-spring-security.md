# 04A · Starter mínimo Spring Security Resource Server

El objetivo es configurar y comprender seguridad, no programar un framework de autenticación.

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

Validar:

```bash
./mvnw test
```

## Configuración mínima

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=${OIDC_ISSUER}
```

Definir la variable en runtime. No duplicarla en código fuente.

## `SecurityConfig`

Crear una única configuración que:

```text
/api/public/**       permitAll
/api/me              authenticated
GET /api/tasks       SCOPE_tasks.read
POST /api/tasks      SCOPE_tasks.write
DELETE /api/tasks/** SCOPE_tasks.write
/api/admin/**        ROLE_Admin (solo si se implementa)
resto                authenticated
```

Habilitar `oauth2ResourceServer(jwt(...))` y CORS según la etapa correspondiente.

## Audience: validación explícita

La aceptación de un issuer correcto no debe hacer olvidar `aud`. La política de CloudTasks exige que el Access Token esté destinado a la API esperada.

Implementar un `OAuth2TokenValidator<Jwt>` pequeño que compruebe:

```text
jwt.getAudience().contains(<API_AUDIENCE>)
```

y combinarlo con los validadores por issuer provistos por Spring Security.

Esto permite observar directamente:

```text
firma/issuer válidos
pero audience incorrecta
→ 401
```

## Scopes

Spring Resource Server suele mapear scopes a authorities `SCOPE_<scope>`. Antes de personalizar conversores:

1. obtener Access Token real;
2. inspeccionar `scp`/`scope`;
3. ejecutar `/api/me`;
4. verificar authorities reales.

No crear un converter custom si el mapping estándar ya resuelve el token emitido.

## Modelo mínimo

```java
record Task(Long id, String title, String ownerId) {}
```

Repositorio en memoria:

```text
ConcurrentHashMap / List
```

Sin JPA, DB, DTO mapping framework ni Lombok como requisito.

## Ownership

DELETE exige dos controles distintos:

```text
Spring Security → posee tasks.write
controller/service → jwt.subject == task.ownerId
```

Si scope correcto pero owner incorrecto:

```text
403
```

## `/api/me`

Recibir `Jwt` autenticado y devolver solo:

```text
sub
iss
aud
scp
roles
```

Nunca devolver el JWT completo.

## Checkpoint 04A-1

```bash
./mvnw test
./mvnw spring-boot:run
```

- [ ] health sin token = 200.
- [ ] `/api/tasks` sin token = 401.

## Checkpoint 04A-2

- [ ] Access Token correcto = acceso esperado.
- [ ] token alterado = 401.
- [ ] `aud` incorrecto = 401.
- [ ] scope faltante = 403/rechazo de autorización.
- [ ] owner incorrecto = 403.

## Checkpoint 04A-3 · explicación

Cada integrante debe poder explicar:

```text
Entra firma/emite
Spring descarga/usa JWKS
issuer identifica emisor
aud identifica destinatario
scope habilita operación
sub identifica sujeto
ownership aplica regla de negocio
```

**SI FALLA** · no agregar librerías JWT externas. Revisar issuer, audience, token real, authorities y logs antes de modificar arquitectura.
