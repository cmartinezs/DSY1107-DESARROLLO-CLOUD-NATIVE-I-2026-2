# 09 · Pruebas negativas y troubleshooting

## Objetivo

Una práctica robusta no demuestra solo el camino feliz. Debe poder provocar errores controlados, identificar la capa responsable y volver al último estado conocido.

## Regla de diagnóstico por capas

No cambiar varias cosas a la vez. Aislar en este orden:

```text
1. proceso/herramienta local
2. navegador/frontend
3. Entra / adquisición de token
4. claims del Access Token
5. API Gateway / JWT Authorizer
6. integración Gateway → backend
7. Spring Security
8. autorización de negocio
```

## Matriz de pruebas negativas

| Caso | Cómo provocarlo | Esperado | Capa principal |
|---|---|---|---|
| frontend no disponible | detener dev server/hosting | error de carga | frontend/hosting |
| redirect URI inválida | usar URI no registrada | login rechazado | Entra |
| client ID inválido | alterar config SPA | autenticación no inicia correctamente | Entra/MSAL |
| scope solicitado inexistente | alterar scope completo | adquisición/consent falla | Entra/MSAL |
| request protegida fuera del map | quitar entrada de `protectedResourceMap` | request sale sin Bearer | MSAL Angular config |
| sin token | llamar protegida sin `Authorization` | 401 | Gateway/backend |
| token alterado | cambiar un carácter | 401 | validación JWT |
| token expirado | utilizar token expirado de prueba | 401 | validación JWT |
| audience incorrecta | token para otro recurso | 401 | authorizer/resource server |
| ID Token como Bearer | enviar ID Token | rechazo | authorizer/resource server |
| sin `tasks.read` | GET con token sin permiso | 403/rechazo | autorización |
| sin `tasks.write` | POST con token sin permiso | 403/rechazo | autorización |
| recurso ajeno | DELETE con write scope pero otro owner | 403 | backend/negocio |
| ★ sin `Admin` | llamar admin con token sin rol | 403 | backend/roles |
| origin CORS inválido | origin no permitido | navegador bloquea | navegador/Gateway |
| backend caído | detener app EC2 | 5xx/integration error | integración/backend |

## Árbol: la aplicación ni siquiera compila

Frontend:

```text
ng version
→ npm dependency tree
→ primer error TypeScript
→ provider/import faltante
```

Backend:

```text
mvnw test
→ primer error Java
→ package/import
→ dependencia
```

No diagnosticar OAuth/CORS cuando el proyecto no compila.

## Árbol: login no comienza o no vuelve

Revisar:

```text
SPA_CLIENT_ID
MSAL_AUTHORITY
redirect URI exacta
user flow asociado a SPA
Console
Network redirects
```

Un error de redirect URI no se corrige modificando API Gateway.

## Árbol: login funciona pero request API sale sin Bearer

Revisar:

```text
URL efectiva HttpClient
protectedResourceMap
wildcard de DELETE
método HTTP
scope asociado
MsalInterceptor registrado
provideHttpClient(withInterceptorsFromDi())
```

Si el login funciona, no reconfigurar el tenant antes de comprobar el interceptor.

## Árbol: adquisición de token falla

Distinguir:

```text
scope completo solicitado
vs
claim scp que se espera recibir
```

Revisar:

```text
Expose an API
API permissions SPA
consent
SCOPE_READ/SCOPE_WRITE completos
recurso solicitado
```

## Árbol: navegador dice CORS

1. Repetir la URL con `curl`/Postman.
2. Si también falla, probablemente hay un problema HTTP/backend/networking anterior.
3. Si HTTP funciona fuera del navegador, inspeccionar `OPTIONS`, `Origin` y `Access-Control-*`.
4. Confirmar que el origin permitido es la **SPA**, no EC2 ni el Gateway.

Postman funcionando no demuestra CORS correcto.

## Árbol: 401

```text
¿existe Authorization: Bearer?
¿es Access Token y no ID Token?
¿firma/JWKS válidos?
¿iss exacto?
¿aud exacto?
¿exp vigente?
```

Un 401 no se corrige agregando `Access-Control-Allow-Origin: *`.

## Árbol: 403

```text
JWT ya fue aceptado
↓
¿authority SCOPE_tasks.read/write existe?
↓
★ ¿ROLE_Admin existe si esa ruta lo requiere?
↓
¿ownership permite ESTE recurso?
```

Diferencia clave:

```text
401 → autenticación/token no aceptado
403 → identidad aceptada, operación no autorizada
```

## Árbol: 5xx en Gateway

Probar primero:

```text
BACKEND_CLOUD_URL directo
```

Luego revisar:

```text
proceso/container EC2
puerto
Security Group/networking
integration URI Gateway
logs backend
```

No tocar issuer/audience si `health` público tampoco llega al backend.

## Árbol: funciona local pero no cloud

Comparar exclusivamente valores que cambian entre entornos:

```text
API base URL
redirect URI
CORS origin
backend host
bundle desplegado/cache
```

Identidad, scopes y contrato de endpoints deberían seguir siendo los mismos.

## Recuperación a estado conocido

Después de cada prueba negativa:

1. revertir exactamente el cambio introducido;
2. repetir la prueba positiva previa;
3. marcar nuevamente el checkpoint `PASS`;
4. recién entonces continuar.

Ejemplo:

```text
origin válido
→ retirar origin
→ comprobar bloqueo
→ restaurar origin
→ comprobar request 200
→ continuar
```

## Registro recomendado

```text
Escenario:
Último checkpoint PASS:
Síntoma:
Request/acción:
Status/error:
Capa responsable:
Causa:
Corrección:
Prueba positiva posterior:
```

## Puerta de validación 09

El estudiante debe poder recibir un fallo no anunciado de alguna capa y decidir **qué revisar primero y por qué**, sin cambiar simultáneamente Entra, Angular, Gateway y Spring.

Usar también:

→ [09A · Runbook de estado conocido](./09a-runbook-checkpoints-estado-conocido.md)
