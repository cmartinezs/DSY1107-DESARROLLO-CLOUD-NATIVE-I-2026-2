# Flujo Authorization Code + PKCE con IDaaS

## Objetivo

Comprender qué ocurre cuando una SPA Angular o React autentica a un usuario mediante un proveedor de identidad y luego consume una API Spring Boot protegida.

Este documento no pretende enseñar a implementar manualmente un Authorization Server. Su propósito es identificar las responsabilidades de cada componente antes de utilizar MSAL y Spring Security.

## Componentes

```text
Usuario
  │
  ▼
SPA Angular / React
  │
  ▼
Identity as a Service
  │
  ▼
API Manager / Gateway
  │
  ▼
Spring Boot Resource Server
```

### SPA

Responsabilidades principales:

- iniciar la autenticación;
- mantener el estado necesario del flujo cliente;
- solicitar los scopes requeridos;
- obtener el Access Token mediante el SDK;
- enviar el token a la API usando `Authorization: Bearer ...`;
- reaccionar correctamente frente a 401 y 403.

La SPA es un **cliente público**. No debe contener un `client_secret`.

### IDaaS

Responsabilidades principales:

- autenticar al usuario;
- validar la aplicación cliente y sus redirect URIs;
- procesar Authorization Code + PKCE;
- emitir el authorization code;
- validar el `code_verifier`;
- emitir y firmar tokens;
- publicar la metadata y claves necesarias para validar tokens;
- representar scopes, roles u otros permisos según su configuración.

### API Manager / Gateway

Puede asumir responsabilidades de exposición y gobierno de la API, por ejemplo:

- routing;
- políticas;
- CORS;
- observabilidad;
- controles de acceso compatibles con la arquitectura definida.

El gateway no reemplaza necesariamente la autorización del backend.

### Spring Boot API

La API actúa como **OAuth 2.0 Resource Server**.

Responsabilidades principales:

- recibir el Access Token como Bearer Token;
- validar criptográficamente el JWT utilizando las claves del emisor;
- comprobar issuer;
- comprobar audience cuando corresponda;
- comprobar vigencia temporal;
- interpretar scopes y/o roles;
- permitir o rechazar la operación solicitada.

La API de negocio **no solicita la contraseña del usuario, no autentica esas credenciales y no emite el Access Token**.

---

# Authorization Code + PKCE paso a paso

## 1. El usuario inicia sesión

El usuario selecciona la acción de iniciar sesión en la SPA.

La aplicación utiliza MSAL para iniciar el flujo de autorización contra el IDaaS.

## 2. Se prepara PKCE

Conceptualmente existe un valor aleatorio llamado:

```text
code_verifier
```

A partir de él se obtiene:

```text
code_challenge = BASE64URL(SHA256(code_verifier))
```

La petición de autorización contiene el `code_challenge`, pero no expone el `code_verifier`.

Con MSAL esta mecánica es gestionada por la biblioteca; el estudiante debe comprender su propósito aunque no tenga que programarla manualmente en la aplicación final.

## 3. Se envía la Authorization Request

Conceptualmente la solicitud contiene datos equivalentes a:

```text
client_id
redirect_uri
response_type=code
scope
code_challenge
code_challenge_method=S256
state
```

La SPA redirige al usuario al proveedor de identidad.

## 4. El IDaaS autentica al usuario

Las credenciales son procesadas por el proveedor de identidad.

```text
Usuario ── credenciales ──► IDaaS
```

No por el backend Spring Boot de la aplicación.

## 5. El IDaaS devuelve un authorization code

Si la autenticación y autorización son correctas, el navegador regresa a una `redirect_uri` registrada para la SPA.

Conceptualmente:

```text
/callback?code=...&state=...
```

El authorization code es temporal y no es el Access Token.

## 6. Se demuestra posesión mediante PKCE

Para intercambiar el authorization code, el cliente utiliza el `code_verifier` original.

El IDaaS comprueba que coincide con el `code_challenge` recibido al comenzar el flujo.

```text
SHA256(code_verifier)
       │
       ▼
comparación con code_challenge
```

Esto vincula el intercambio del código con el cliente que inició el flujo.

## 7. El IDaaS emite tokens

Después de validar el authorization code y PKCE, el proveedor devuelve los tokens que correspondan.

Para consumir la API interesa principalmente el:

```text
Access Token
```

El ID Token y el Access Token tienen propósitos diferentes y no deben intercambiarse conceptualmente.

## 8. La SPA llama a la API

La SPA incorpora el Access Token en la petición HTTP:

```http
Authorization: Bearer <access_token>
```

Conceptualmente:

```text
SPA
 │
 │ Bearer Access Token
 ▼
API Manager / Gateway
 │
 ▼
Spring Boot API
```

## 9. Spring Security valida el token

La API no confía en el token solo porque pueda decodificarlo.

Debe verificar, según la configuración:

- firma;
- `iss`;
- `aud`;
- `exp`;
- scopes y/o roles necesarios para la operación.

Decodificar un JWT permite leerlo. **Validarlo** determina si puede ser confiable para autorizar una operación.

## 10. La API autoriza la operación

Ejemplos conceptuales:

```text
/public/**
→ acceso público

/api/**
→ usuario autenticado

/api/admin/**
→ permiso adicional requerido
```

Esto permite observar comportamientos distintos:

```text
sin credenciales válidas
→ 401 Unauthorized

autenticado pero sin permiso suficiente
→ 403 Forbidden

autenticado y autorizado
→ 2xx
```

---

# Responsabilidades por componente

| Responsabilidad | SPA | IDaaS | Gateway | Spring API |
|---|:---:|:---:|:---:|:---:|
| iniciar Authorization Code + PKCE | ✓ | | | |
| autenticar credenciales | | ✓ | | |
| emitir authorization code | | ✓ | | |
| validar PKCE | | ✓ | | |
| emitir/firma Access Token | | ✓ | | |
| adjuntar Bearer Token | ✓ | | | |
| routing/API exposure | | | ✓ | |
| validar JWT para proteger negocio | | | opcional | ✓ |
| comprobar scopes/roles de la operación | | | opcional | ✓ |
| ejecutar lógica de negocio | | | | ✓ |

## Frontera que no debe romperse

En la solución EV1 no corresponde implementar en Spring Boot:

```text
POST /login con contraseña propia
POST /oauth2/token propio
generateAccessToken()
signAccessToken()
base de datos local de credenciales para reemplazar el IDaaS
```

Spring Boot debe concentrarse en ser API de negocio y Resource Server.

---

# Conceptos que el estudiante debe poder explicar

Al terminar esta etapa debería poder responder:

1. ¿Por qué una SPA usa Authorization Code + PKCE?
2. ¿Cuál es la diferencia entre `code_verifier` y `code_challenge`?
3. ¿Quién autentica las credenciales del usuario?
4. ¿Quién emite y firma el Access Token?
5. ¿Por qué el backend de negocio no debe emitir ese token?
6. ¿Qué token se envía a la API?
7. ¿Qué significa `Authorization: Bearer`?
8. ¿Por qué decodificar un JWT no equivale a validarlo?
9. ¿Qué diferencia existe entre 401 y 403?
10. ¿Qué responsabilidades mantienen API Gateway y Spring Security?

## Siguiente etapa

El siguiente starter será un **toolkit JWT conceptual** para observar claims, construcción, firma, decodificación, validación y alteración de un token de forma aislada antes de integrar tokens reales emitidos por el IDaaS.