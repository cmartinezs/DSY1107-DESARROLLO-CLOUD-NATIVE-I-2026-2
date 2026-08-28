# Laboratorio · Flujo Full Stack protegido

**Semana:** 4  
**Foco:** MSAL + OAuth2/OIDC + API Manager/Gateway + Spring Security Resource Server

← [Volver al índice de laboratorios](../README.md)

## Propósito

Construir y observar un flujo mínimo donde un frontend autentica al usuario mediante un Identity Provider, obtiene un **access token** para una API y un backend Spring Boot acepta o rechaza requests según autenticación y permisos.

El laboratorio debe poder explicarse sin depender de una consola cloud específica. Cuando se utilice Microsoft Entra ID/MSAL en clase, se mapearán estos conceptos al servicio real.

## Resultado esperado

Al terminar debes poder explicar:

- por qué una SPA usa Authorization Code + PKCE;
- por qué el frontend no contiene `client_secret`;
- la diferencia entre ID token y access token;
- qué valida el gateway/API Manager y qué debe seguir validando el backend;
- cómo Spring Security actúa como Resource Server;
- por qué ciertos casos producen 401 y otros 403;
- cómo `issuer`, `audience`, expiración y scopes participan en la decisión.

## Prerrequisitos

Antes de comenzar debes haber trabajado:

- OAuth2 y OpenID Connect;
- Authorization Code + PKCE;
- usuarios externos / CIAM;
- JWT y claims;
- decode vs verify;
- `iss`, `aud`, `exp` y scopes;
- diferencia entre 401 y 403.

Si alguno de estos conceptos no puede explicarse todavía, vuelve a Semana 3 antes de continuar.

## Escenario

Usaremos un dominio neutral:

```text
BookShelf UI
    |
    | Authorization Code + PKCE
    v
Identity Provider
    |
    | access token · scope books.read
    v
BookShelf UI
    |
    | Authorization: Bearer <token>
    v
API Manager / Gateway
    |
    v
BookShelf API · Spring Security Resource Server
    |
    v
GET /api/books
```

## Paso 1 · Diseña el flujo antes de configurar

Dibuja el flujo completo y anota:

- quién inicia la autenticación;
- dónde se autentica el usuario;
- quién recibe el authorization code;
- qué protege PKCE;
- quién solicita el access token;
- para qué audience se emite;
- qué scope necesita la operación;
- qué componentes pueden responder 401 o 403.

### Checkpoint 1

Puedes señalar cada componente del diagrama y explicar su responsabilidad sin mencionar botones de una consola cloud.

## Paso 2 · Registra/configura el cliente frontend

La SPA debe configurarse como **public client**.

Necesitas conceptualmente:

```text
clientId
identity authority / tenant
redirectUri
scopes de la API
```

No agregues un `client_secret` al frontend.

Configuración representativa:

```javascript
const msalConfig = {
  auth: {
    clientId: "<spa-client-id>",
    authority: "https://login.microsoftonline.com/<tenant-id>",
    redirectUri: "http://localhost:5173"
  }
};
```

### Checkpoint 2

Puedes justificar por qué `clientId` puede estar en el frontend y por qué un `client_secret` no.

## Paso 3 · Solicita identidad y permisos

Configura la solicitud de login/token con los scopes necesarios.

Ejemplo conceptual:

```javascript
const request = {
  scopes: ["openid", "profile", "api://<api-client-id>/books.read"]
};
```

Después del login:

1. identifica la cuenta activa;
2. solicita un access token para la API;
3. evita persistir o imprimir el token completo;
4. envía el token mediante `Authorization: Bearer`.

### Checkpoint 3

Puedes mostrar cuál token sirve para identidad del cliente y cuál se envía a la API.

## Paso 4 · Configura Spring Security como Resource Server

El backend debe validar tokens por sí mismo.

Dependencia conceptual:

```text
Spring Security OAuth2 Resource Server + JWT
```

Configuración representativa:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://login.microsoftonline.com/<tenant-id>/v2.0
```

Protege al menos:

```text
/public/health  -> público
/api/books      -> autenticado + scope books.read
```

Una configuración conceptual puede usar:

```java
.requestMatchers("/public/**").permitAll()
.requestMatchers("/api/books").hasAuthority("SCOPE_books.read")
.anyRequest().authenticated()
```

### Checkpoint 4

El backend rechaza requests sin autenticación válida aunque el frontend “crea” que el usuario inició sesión correctamente.

## Paso 5 · Verifica issuer y audience

No basta con verificar que la firma sea correcta.

Debes poder justificar:

- `iss`: quién emitió el token;
- `aud`: para qué recurso/API fue emitido;
- `exp`: si sigue vigente;
- scopes/authorities: qué operación puede ejecutar.

### Checkpoint 5

Puedes explicar por qué un token criptográficamente válido para otra API debe ser rechazado.

## Paso 6 · Ejecuta la matriz de pruebas

Registra request sanitizado, status code y explicación.

| Caso | Token | Permiso | Resultado esperado |
|---|---|---|---:|
| A | ninguno | — | 401 |
| B | inválido / expirado / audience incorrecta | — | 401 |
| C | válido | sin `books.read` | 403 |
| D | válido | `books.read` | 2xx |

### Checkpoint 6

Puedes explicar cada resultado por la condición concreta que falló o fue satisfecha.

## Paso 7 · Ubica el API Manager/Gateway

Agrega el gateway al diagrama y define qué controles aplicaría:

- validación temprana de autenticación cuando corresponda;
- rate limiting;
- routing;
- observabilidad;
- políticas técnicas comunes.

Luego responde:

> Si el gateway valida el token, ¿por qué el backend sigue teniendo responsabilidades de seguridad?

La respuesta debe considerar defensa en profundidad, bypass de rutas y autorización de negocio.

### Checkpoint 7

Puedes separar controles perimetrales del gateway de controles de recurso y negocio del backend.

## Paso 8 · Threat sketch

Completa al menos esta matriz:

| Riesgo | Control |
|---|---|
| interception del authorization code | PKCE + HTTPS |
| token para otra API | validación de audience |
| token de otro emisor/tenant | validación de issuer |
| permisos excesivos | scopes mínimos |
| secret en JavaScript | public client sin secret |
| exposición de token en logs | logging sanitizado |
| bypass del gateway | validación/autorización en backend |

### Checkpoint 8

Para cada control puedes explicar qué amenaza mitiga y qué amenaza **no** resuelve.

## Evidencia mínima

Entrega o conserva en tu repositorio de trabajo:

- diagrama de arquitectura y flujo;
- configuración sanitizada;
- matriz de pruebas 401/403/2xx;
- evidencia de audience/issuer/scopes sin exponer tokens completos;
- threat sketch;
- explicación breve de responsabilidades;
- DevLog.

## Seguridad

Nunca versionar:

- client secrets;
- contraseñas;
- access/refresh tokens reutilizables;
- certificados privados;
- credenciales cloud.

## Criterio de logro

El laboratorio está logrado cuando puedes explicar **por qué** cada request fue aceptado o rechazado y qué componente fue responsable de la decisión, no simplemente cuando la demo devuelve HTTP 200.

## Transferencia posterior

Este laboratorio es independiente de RegistrApp. Una vez comprendido el patrón, la competencia puede transferirse al checkpoint correspondiente del proyecto formativo.

→ [RegistrApp · Semana 4](../../proyecto-formativo/semana-04/README.md)
