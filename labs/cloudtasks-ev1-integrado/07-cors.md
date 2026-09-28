# 07 · CORS con URLs reales

## Objetivo

Configurar CORS cuando **ya existen** el frontend local y la URL real del API Gateway. Esta guía no pide una URL que todavía no fue creada.

## Orígenes conocidos en este punto

```text
FRONTEND_LOCAL_URL=http://localhost:4200
API_GATEWAY_URL=<valor obtenido en etapa 06>
```

Todavía no existe necesariamente `FRONTEND_CLOUD_URL`. Se agregará en la etapa 08.

## 1. Frontera CORS de esta etapa

El navegador llama a:

```text
Angular localhost:4200
→ API Gateway
→ Spring Boot EC2
```

Por lo tanto, la política CORS observable por el navegador se configura en **API Gateway**.

La configuración CORS local de Spring utilizada en 01C sirvió para aprender el problema cuando Angular llamaba directamente al backend. No debe utilizarse como parche para ocultar una configuración incorrecta del Gateway.

## 2. Configuración inicial

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

No usar `*` como solución automática cuando se conocen los orígenes.

No agregar:

```text
API_GATEWAY_URL
BACKEND_CLOUD_URL
```

como allowed origins. El origin relevante es la SPA que ejecuta el navegador.

## 3. Comprobar health desde Angular

Antes de probar JWT, configurar temporalmente/actualmente el frontend para usar:

```text
apiConfig.baseUrl = API_GATEWAY_URL
```

Desde:

```text
http://localhost:4200
```

la llamada:

```text
GET /api/public/health
```

debe llegar a API Gateway y responder correctamente.

DevTools → Network debe mostrar la URL del Gateway, no EC2 directo.

## 4. Comprobar request protegida

Después del login:

```text
Angular
  ↓ Authorization: Bearer <Access Token>
API Gateway
  ↓ JWT Authorizer + authorization scope
Spring Boot
```

Comprobar al menos:

```text
GET /api/tasks → 200 con tasks.read
```

## 5. Observar preflight real

Una petición con `Authorization` normalmente obliga al navegador a realizar preflight.

En DevTools → Network identificar la request `OPTIONS` y revisar:

```text
Origin
Access-Control-Request-Method
Access-Control-Request-Headers
Access-Control-Allow-Origin
Access-Control-Allow-Methods
Access-Control-Allow-Headers
```

El estudiante debe distinguir:

```text
OPTIONS/preflight
≠
GET/POST/DELETE real
```

## 6. Prueba negativa controlada

Cambiar temporalmente el origen permitido a un valor distinto de:

```text
http://localhost:4200
```

y repetir desde navegador.

Registrar:

- mensaje de Console;
- request `OPTIONS`, si existe;
- si la operación real llegó o no a ejecutarse;
- diferencia con un 401/403.

Después restaurar exactamente:

```text
http://localhost:4200
```

y repetir una prueba positiva.

## 7. Matriz de diagnóstico

| Síntoma | Primera hipótesis |
|---|---|
| funciona en curl/Postman, falla navegador | CORS/preflight |
| 401 en curl y navegador | token/authorizer, no CORS |
| 403 con request recibida | scope/ownership/role, no CORS |
| OPTIONS falla | configuración CORS/preflight |
| origin devuelto no coincide | allowed origins |
| `Authorization` bloqueado | allowed headers |
| Network muestra EC2 directo | frontend aún usa URL incorrecta |

## 8. Qué NO hacer

```text
NO agregar Access-Control-Allow-Origin: * para ocultar el problema
NO modificar issuer/audience por un error CORS
NO usar Postman como prueba de CORS
NO agregar API_GATEWAY_URL como allowed origin
NO cambiar simultáneamente Angular + Gateway + Spring
```

## Regla de oro

CORS **no autentica ni autoriza usuarios**. Es una política del navegador sobre requests cross-origin.

```text
401 → autenticación/token
403 → autorización
CORS → política del navegador
```

## Puerta de validación 07

- [ ] Angular local consume health vía API Gateway.
- [ ] Network muestra `API_GATEWAY_URL` como destino.
- [ ] request protegida usa Access Token.
- [ ] preflight es observable.
- [ ] `Origin` es `http://localhost:4200`.
- [ ] `Authorization` está permitido.
- [ ] error CORS fue provocado conscientemente.
- [ ] configuración correcta fue restaurada.
- [ ] prueba positiva vuelve a PASS.

## Contenido relacionado

- [CORS en Semana 1](../../semanas/semana-01/04-cors-api-gateway.md)
- [Preflight paso a paso](../../semanas/semana-01/04-cors-api-gateway/02-preflight-paso-a-paso.md)
- [08 · Frontend cloud + E2E](./08-frontend-cloud-e2e.md)
