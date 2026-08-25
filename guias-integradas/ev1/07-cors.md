# 07 · CORS con URLs reales

## Objetivo

Configurar CORS cuando **ya existen** el frontend local y la URL real del API Gateway. Esta guía no pide una URL que todavía no fue creada.

## Orígenes conocidos en este punto

```text
FRONTEND_LOCAL_URL=http://localhost:4200
API_GATEWAY_URL=<valor obtenido en etapa 06>
```

Todavía no existe necesariamente `FRONTEND_CLOUD_URL`. Se agregará en la etapa 08.

## Configuración inicial

En API Gateway permitir exclusivamente:

```text
Allowed origins:
  http://localhost:4200

Allowed methods:
  GET
  POST
  DELETE
  OPTIONS

Allowed headers:
  Authorization
  Content-Type
```

No usar `*` como solución automática cuando se trabaja con orígenes conocidos.

## Qué debe ocurrir

Desde Angular:

```text
http://localhost:4200
        ↓
<API_GATEWAY_URL>/api/public/health
```

Luego una petición con Bearer token:

```text
Angular
  ↓ Authorization header
API Gateway
  ↓ JWT Authorizer
Backend
```

## Ver preflight

Abrir DevTools → Network y observar una operación que genere preflight.

Identificar:

```text
Origin
Access-Control-Request-Method
Access-Control-Request-Headers
OPTIONS
Access-Control-Allow-Origin
Access-Control-Allow-Methods
Access-Control-Allow-Headers
```

## Prueba negativa obligatoria

Cambiar temporalmente el origen permitido a un valor que no sea `http://localhost:4200` y repetir desde navegador.

Registrar:

- qué muestra Console;
- si API Gateway/backend recibió la operación real;
- diferencia entre fallo CORS y 401/403.

Restaurar la configuración correcta.

## Matriz de diagnóstico

| Síntoma | Primera hipótesis |
|---|---|
| funciona en curl/Postman, falla navegador | CORS/preflight |
| 401 en curl y navegador | token/authorizer, no CORS |
| 403 desde API | autorización, no CORS |
| OPTIONS falla | configuración preflight/ruta/política |
| origin devuelto no coincide | allowed origins |
| `Authorization` bloqueado | allowed headers |

## Regla de oro

CORS **no autentica ni autoriza usuarios**. Es una política del navegador sobre lectura/envío cross-origin. No usar CORS para explicar un 401 ni OAuth2 para explicar un preflight bloqueado.

## Puerta de validación 07

- health consumido por Angular vía API Gateway;
- petición protegida consumida con Bearer token;
- preflight observable;
- origin local explícitamente permitido;
- error CORS provocado y diagnosticado;
- configuración correcta restaurada.

## Contenido relacionado

- [CORS en Semana 1](../../semanas/semana-01/04-cors-api-gateway.md)
- [Preflight paso a paso](../../semanas/semana-01/04-cors-api-gateway/02-preflight-paso-a-paso.md)
