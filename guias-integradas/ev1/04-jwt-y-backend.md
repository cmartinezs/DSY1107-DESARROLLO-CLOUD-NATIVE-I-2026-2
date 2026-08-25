# 04 · JWT, scopes, roles y Spring Security

## Objetivo

Convertir el backend ya creado en un **Resource Server** real que acepte Access Tokens válidos del tenant y aplique autorización.

> El alumno no implementará criptografía JWT, parsers de tokens ni un sistema de autenticación propio. Spring Security realizará esa tarea. El trabajo EV1 consiste en **configurar correctamente issuer/audience/scopes, aplicar políticas y demostrar los resultados**.

## 1. Agregar solo las dependencias de seguridad necesarias

En `pom.xml` agregar:

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

Después recargar Maven en IntelliJ.

Validar con el wrapper, no con Maven global.

Windows:

```powershell
.\mvnw.cmd test
```

Linux/macOS:

```bash
./mvnw test
```

No agregar librerías JWT manuales mientras Spring Security Resource Server cubra el requisito.

## 2. Configurar por issuer

Preferir `issuer-uri` cuando el issuer real ya fue verificado en la etapa Entra:

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=<OIDC_ISSUER>
```

Spring utilizará metadata OIDC/JWKS para verificar firma y claims estándar.

No copiar claves públicas manualmente si discovery funciona.

## 3. Política mínima de rutas

Mantener público:

```text
GET /api/public/health
```

Requerir autenticación para:

```text
GET /api/me
```

Y autorización específica para tareas:

```text
GET    /api/tasks       → tasks.read
POST   /api/tasks       → tasks.write
DELETE /api/tasks/{id}  → tasks.write + ownership
GET    /api/admin/stats → Admin, solo si el entorno permite trabajar roles
```

La política se implementa con Spring Security, no con `if` manuales que intenten validar el JWT.

## 4. Inspeccionar el claim de scopes antes de mapearlo

No asumir ciegamente si el token utiliza:

```text
scp
scope
```

Primero inspeccionar un Access Token real emitido por el tenant.

Luego configurar Spring para traducir esos scopes a authorities de forma consistente.

El alumno debe poder explicar:

```text
claim del token
→ authority de Spring
→ regla de autorización
```

Ese mapeo sí aporta directamente a EV1.

## 5. `/api/me`: endpoint didáctico mínimo

Crear un endpoint pequeño que permita demostrar qué identidad recibió realmente el backend.

Debe devolver solo información útil/sanitizada, por ejemplo:

```json
{
  "subject": "...",
  "issuer": "...",
  "audience": ["..."],
  "scopes": ["tasks.read"],
  "roles": []
}
```

No retornar el token completo.

La finalidad del endpoint es observar:

```text
JWT aceptado
→ claims disponibles
→ backend conoce la identidad
```

No construir un perfil de usuario ni persistencia asociada.

## 6. Datos de tareas: mantenerlos en memoria

Para EV1 la persistencia no es el foco.

No agregar:

- PostgreSQL;
- MySQL;
- H2 como requisito;
- JPA;
- repositories;
- migraciones;
- Docker Compose.

Una colección en memoria es suficiente para demostrar autorización.

Modelo mínimo conceptual:

```text
Task
- id
- title
- ownerId
```

No agregar más campos si no aportan a una prueba EV1.

## 7. Regla de ownership

Para eliminar una tarea:

```text
scope tasks.write válido
        +
sub del token == ownerId de la tarea
        ↓
permitir
```

Si el usuario posee `tasks.write` pero intenta eliminar una tarea de otro usuario:

```text
403 Forbidden
```

Esta pequeña regla existe porque permite distinguir:

```text
autorización técnica por scope
vs
regla de autorización de negocio
```

No se necesita un dominio más complejo.

## 8. Pruebas locales

Con backend en `localhost:8080`:

### Health público

```bash
curl -i http://localhost:8080/api/public/health
```

Esperado:

```text
200
```

### Ruta protegida sin token

```bash
curl -i http://localhost:8080/api/tasks
```

Esperado:

```text
401
```

### Access Token real

```bash
curl -i \
  -H "Authorization: Bearer <ACCESS_TOKEN>" \
  http://localhost:8080/api/tasks
```

Esperado:

```text
200
```

solo cuando token y scope sean correctos.

> El token utilizado para pruebas no se guarda en Git, capturas públicas ni archivos versionados.

## 9. Qué NO programa el alumno

No implementar manualmente:

- generación de JWT;
- firma de JWT;
- descarga/rotación de JWKS;
- validación RSA;
- parsing criptográfico del token;
- Authorization Code;
- PKCE;
- pantalla de login;
- base de usuarios;
- refresh token flow propio.

Todo eso ya corresponde a responsabilidades de Entra, MSAL o Spring Security.

El alumno sí debe saber explicar **qué servicio realiza cada responsabilidad**.

## Puerta de validación 04

Probar y explicar:

| Caso | Resultado esperado |
|---|---|
| health sin token | 200 |
| tasks sin token | 401 |
| token alterado | 401 |
| audience incorrecta | 401 |
| token válido sin scope | 403 |
| token válido + scope | 200 |
| write sobre recurso ajeno | 403 |

## Regla importante

`401` significa que la petición no logra establecer una autenticación válida para continuar.

`403` significa que existe identidad/token suficientemente válido, pero falta autorización para la operación solicitada.

## Criterio de código mínimo

El backend de referencia solo debe contener el código necesario para demostrar:

```text
endpoint
+ token válido
+ scope
+ claim
+ 401/403
+ ownership
```

Si una clase o dependencia no ayuda a demostrar alguno de esos elementos, probablemente no pertenece a la solución mínima de EV1.

## Contenido relacionado

- [JWT y claims](../../semanas/semana-03/01-jwt-claims.md)
- [Seguridad API](../../semanas/semana-03/02-seguridad-api.md)
- [Gateway vs backend](../../semanas/semana-03/02-seguridad-api/01-gateway-vs-backend.md)
