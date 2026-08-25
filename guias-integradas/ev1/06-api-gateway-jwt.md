# 06 · AWS API Gateway + JWT Authorizer

## Objetivo

Crear la frontera pública de la práctica: rutas, integración hacia el backend y validación JWT antes de que la petición llegue a Spring Boot.

## Antes de comenzar

Deben estar validados:

```text
BACKEND_CLOUD_URL
OIDC_ISSUER
API_AUDIENCE
SCOPE_READ_CLAIM
SCOPE_WRITE_CLAIM
```

Los dos últimos son los valores **reales observados dentro del claim `scp`/`scope` del Access Token**, por ejemplo:

```text
tasks.read
tasks.write
```

No confundirlos con los scopes completos que solicita MSAL, por ejemplo:

```text
api://<API_CLIENT_ID>/tasks.read
```

Usar [00C · Matriz de valores](./00c-matriz-valores-y-checkpoints.md).

## 1. Crear HTTP API

En AWS API Gateway crear una **HTTP API** para CloudTasks.

Nombre sugerido:

```text
cloudtasks-api-gateway
```

## 2. Crear integración

Crear una integración HTTP hacia:

```text
<BACKEND_CLOUD_URL>
```

No inventar un host nuevo. El backend directo debe haber pasado la etapa 05 antes de integrar el Gateway.

## 3. Crear rutas

Ruta base:

```text
GET    /api/public/health
GET    /api/me
GET    /api/tasks
POST   /api/tasks
DELETE /api/tasks/{id}
```

Si se implementó ★04B Roles:

```text
GET /api/admin/stats
```

Verificar primero:

```text
GET /api/public/health sin authorizer → 200
```

Solo después registrar:

```text
API_GATEWAY_URL=<Invoke URL real>
```

## 4. Crear JWT Authorizer

Configurar literalmente:

```text
Issuer   = <OIDC_ISSUER>
Audience = <API_AUDIENCE>
```

`API_AUDIENCE` debe provenir del `aud` del Access Token real validado previamente. No usar `SPA_CLIENT_ID` por intuición.

## 5. Asociar authorizer

Mantener pública:

```text
GET /api/public/health
```

Proteger con JWT Authorizer:

```text
GET /api/me
GET /api/tasks
POST /api/tasks
DELETE /api/tasks/{id}
```

Si existe ★04B:

```text
GET /api/admin/stats
```

La ruta `/api/me` requiere token válido, pero no necesita un scope adicional en el Gateway para esta práctica.

## 6. Authorization scopes por ruta

Configurar usando los valores del claim real:

```text
GET /api/tasks         → <SCOPE_READ_CLAIM>  → normalmente tasks.read
POST /api/tasks        → <SCOPE_WRITE_CLAIM> → normalmente tasks.write
DELETE /api/tasks/{id} → <SCOPE_WRITE_CLAIM> → normalmente tasks.write
```

Regla:

```text
MSAL solicita scope completo
→ Entra emite claim scp/scope
→ API Gateway compara authorization scope con ese claim
→ Spring vuelve a mapearlo a SCOPE_tasks.read / SCOPE_tasks.write
```

No configurar en API Gateway:

```text
SCOPE_tasks.read
SCOPE_tasks.write
```

porque esos son nombres de authorities de Spring, no valores del claim JWT.

## 7. Ownership y roles no pertenecen al authorization scope del Gateway

El Gateway puede comprobar token válido y scopes de entrada. El ownership sigue siendo una regla de negocio del backend:

```text
JWT.sub == task.ownerId
```

Si se implementó ★04B `Admin`, la comprobación de `ROLE_Admin` permanece en Spring. No intentar convertir el claim `roles` en authorization scopes del HTTP API.

## 8. Probar sin navegador

### Health

```bash
curl -i <API_GATEWAY_URL>/api/public/health
```

Esperado: `200`.

### Sin token

```bash
curl -i <API_GATEWAY_URL>/api/tasks
```

Esperado: `401`.

### Con Access Token

```bash
curl -i \
  -H "Authorization: Bearer <ACCESS_TOKEN>" \
  <API_GATEWAY_URL>/api/tasks
```

Esperado: `200` si JWT y `tasks.read` son correctos.

Para escritura repetir con un token que contenga `tasks.write`.

## 9. Diferenciar responsabilidades

```text
Microsoft Entra
  autentica y emite token

AWS API Gateway
  valida JWT de entrada
  valida issuer/audience
  exige scopes por ruta

Spring Boot
  vuelve a validar JWT
  aplica scopes
  aplica ownership
  aplica roles si se activó ★04B
```

El Gateway no reemplaza la seguridad del backend.

## Puerta de validación 06

| Prueba | Esperado |
|---|---|
| health vía Gateway | 200 |
| protegida sin token | 401 |
| token alterado | 401 |
| audience incorrecta | 401 |
| token válido sin scope requerido | rechazo |
| Access Token válido + `tasks.read` | GET llega al backend |
| Access Token válido + `tasks.write` | POST/DELETE llegan al backend |
| ownership ajeno | backend devuelve 403 |

No configurar CORS para corregir fallas de `curl`: CORS es política del navegador y se trata en 07.

## Contenido relacionado

- [00C · Matriz de valores](./00c-matriz-valores-y-checkpoints.md)
- [API Gateway/Management](../../semanas/semana-01/01-api-manager.md)
- [Rutas e integraciones](../../semanas/semana-01/02-primer-api-manager.md)
- [Seguridad API/gateway](../../semanas/semana-03/02-seguridad-api.md)
