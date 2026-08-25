# 04 · JWT, scopes, roles y Spring Security

## Objetivo

Convertir el backend en un Resource Server real que acepte solo Access Tokens válidos del tenant y aplique autorización.

## 1. Dependencia

Agregar Spring Security y OAuth2 Resource Server al backend.

Dependencias Maven:

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

## 2. Configuración por issuer

Preferir `issuer-uri` cuando el issuer real ha sido verificado:

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=<OIDC_ISSUER>
```

Spring descubrirá metadata/JWKS y verificará firma y claims estándar. No pegar claves públicas manualmente si discovery funciona.

## 3. Rutas

Mantener público:

```text
GET /api/public/health
```

Requerir autenticación para `/api/me` y autorización específica para tareas.

Mapear `scp`/scopes a authorities según el formato real emitido por Entra. No asumir el nombre del claim sin inspeccionar un token real.

Política objetivo:

```text
GET    /api/tasks       → tasks.read
POST   /api/tasks       → tasks.write
DELETE /api/tasks/{id}  → tasks.write + ownership
GET    /api/admin/stats → Admin (si disponible)
```

## 4. `/api/me`

Crear un endpoint que devuelva solo claims útiles/sanitizados:

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

## 5. Datos en memoria

Para EV1 la persistencia no es el foco. Mantener tareas en memoria es suficiente para demostrar autorización.

Cada tarea debe incluir `ownerId`. Al borrar:

```text
scope tasks.write válido
        +
sub del token == ownerId de la tarea
        ↓
permitir
```

Si el scope existe pero la tarea pertenece a otro usuario, responder 403.

## 6. Pruebas locales

Con backend en `localhost:8080`:

```bash
curl http://localhost:8080/api/public/health
```

Debe retornar 200.

Sin token:

```bash
curl -i http://localhost:8080/api/tasks
```

Debe retornar 401.

Con Access Token real:

```bash
curl -i \
  -H "Authorization: Bearer <ACCESS_TOKEN>" \
  http://localhost:8080/api/tasks
```

Debe retornar 200 si el token y scope son correctos.

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

`401` significa que la petición no logra establecer una autenticación válida para continuar. `403` significa que hay identidad/token suficientemente válido, pero no autorización para la operación solicitada.

## Contenido relacionado

- [JWT y claims](../../semanas/semana-03/01-jwt-claims.md)
- [Seguridad API](../../semanas/semana-03/02-seguridad-api.md)
- [Gateway vs backend](../../semanas/semana-03/02-seguridad-api/01-gateway-vs-backend.md)
