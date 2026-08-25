# 06 · AWS API Gateway + JWT Authorizer

## Objetivo

Crear la frontera pública de la práctica: rutas, integración hacia el backend y validación JWT antes de que la petición llegue a Spring Boot.

## 1. Crear HTTP API

En AWS API Gateway crear una **HTTP API** para CloudTasks.

Nombre sugerido:

```text
cloudtasks-api-gateway
```

## 2. Crear integración

Crear una integración HTTP hacia el backend desplegado y validado en la etapa anterior.

Origen conocido:

```text
<BACKEND_CLOUD_URL>
```

No inventar un host nuevo. Si el backend directo no responde desde fuera de AWS/API Gateway, corregir networking antes de continuar.

## 3. Crear rutas

Crear al menos:

```text
GET    /api/public/health
GET    /api/me
GET    /api/tasks
POST   /api/tasks
DELETE /api/tasks/{id}
GET    /api/admin/stats   # si se implementó rol Admin
```

Verificar primero `GET /api/public/health` **sin authorizer**.

Cuando responda 200 a través de API Gateway, registrar:

```text
API_GATEWAY_URL=<Invoke URL real>
```

## 4. Crear JWT Authorizer

Usar los valores ya comprobados:

```text
Issuer   = <OIDC_ISSUER>
Audience = <API_AUDIENCE>
```

No usar el `SPA_CLIENT_ID` como audience por intuición. Comparar con el claim `aud` del Access Token real.

## 5. Asociar authorizer

Mantener pública:

```text
GET /api/public/health
```

Proteger:

```text
GET /api/me
GET /api/tasks
POST /api/tasks
DELETE /api/tasks/{id}
GET /api/admin/stats
```

## 6. Scopes por ruta

Aplicar scopes donde la configuración del authorizer/ruta lo soporte:

```text
GET /api/tasks              → tasks.read
POST /api/tasks             → tasks.write
DELETE /api/tasks/{id}      → tasks.write
```

La regla de ownership permanece en el backend. El Gateway no tiene por qué conocer el propietario de cada tarea.

## 7. Probar sin navegador

### Health

```bash
curl -i <API_GATEWAY_URL>/api/public/health
```

Esperado: 200.

### Sin token

```bash
curl -i <API_GATEWAY_URL>/api/tasks
```

Esperado: 401.

### Con Access Token

```bash
curl -i \
  -H "Authorization: Bearer <ACCESS_TOKEN>" \
  <API_GATEWAY_URL>/api/tasks
```

Esperado: 200 si token y scope son correctos.

## 8. Diferenciar responsabilidades

```text
Microsoft Entra
  autentica/emite token

AWS API Gateway
  valida token y políticas de entrada

Spring Boot
  vuelve a validar como Resource Server y aplica reglas de negocio
```

El estudiante debe poder explicar que el Gateway **no reemplaza** la seguridad del backend. Son controles complementarios.

## Puerta de validación 06

| Prueba | Esperado |
|---|---|
| health vía Gateway | 200 |
| protegida sin token | 401 |
| token alterado | 401 |
| audience incorrecta | 401 |
| token válido sin scope requerido | rechazo |
| Access Token válido + scope | llega al backend |

No configurar CORS todavía para resolver fallas de `curl`: CORS es política del navegador.

## Contenido relacionado

- [API Gateway/Management](../../semanas/semana-01/01-api-manager.md)
- [Rutas e integraciones](../../semanas/semana-01/02-primer-api-manager.md)
- [Seguridad API/gateway](../../semanas/semana-03/02-seguridad-api.md)
