# 06 · AWS API Gateway + JWT Authorizer

## Objetivo

Completar la frontera pública de la práctica: rutas, integración hacia el backend y validación JWT antes de que la petición llegue a Spring Boot.

> Si ya se completó [03B · Checkpoint Semana 3](./03b-checkpoint-semana-03-idaas-jwt-api-manager.md), **no crear un segundo API Gateway ni un segundo authorizer**. Reutilizar `cloudtasks-api-gateway` y `cloudtasks-jwt-authorizer` creados allí.

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

## 1. Crear o reutilizar HTTP API

### Si 03B está en PASS

Abrir el API ya creado:

```text
cloudtasks-api-gateway
```

Confirmar que corresponde al `API_GATEWAY_ID` registrado en el checkpoint semanal.

### Si 03B no se ejecutó

Crear una **HTTP API** para CloudTasks:

```text
cloudtasks-api-gateway
```

Registrar su ID para evitar duplicados posteriores.

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

## 4. Crear o verificar JWT Authorizer

### Si 03B está en PASS

Abrir:

```text
cloudtasks-jwt-authorizer
```

Comprobar que conserva exactamente:

```text
Issuer   = <OIDC_ISSUER>
Audience = <API_AUDIENCE>
```

### Si no existe

Crearlo con esos mismos valores.

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
| mismo Gateway/authorizer de 03B reutilizado, si existía | sí |
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

- [03B · Checkpoint Semana 3](./03b-checkpoint-semana-03-idaas-jwt-api-manager.md)
- [00C · Matriz de valores](./00c-matriz-valores-y-checkpoints.md)
- [API Gateway/Management](../../semanas/semana-01/01-api-manager.md)
- [Rutas e integraciones](../../semanas/semana-01/02-primer-api-manager.md)
- [Seguridad API/gateway](../../semanas/semana-03/02-seguridad-api/README.md)
