# 04 · Seguridad, identidad y autorización

## Objetivo

La aplicación debe estar preparada para incorporar autenticación y autorización mediante los mecanismos trabajados en DSY1107, sin implementar un sistema casero de credenciales.

## Identidad

La arquitectura debe permitir integrar un **Identity Provider / Identity as a Service** mediante OAuth 2.0 y OpenID Connect.

El frontend debe poder evolucionar hacia un flujo SPA con **Authorization Code + PKCE**.

La solución debe permitir distinguir:

```text
ID Token ≠ Access Token
```

El Access Token será el utilizado para acceder a recursos protegidos de la API.

## Niveles de acceso

La aplicación debe poseer al menos **dos niveles de acceso diferenciables** mediante scopes, roles o permisos, según corresponda.

Ejemplos conceptuales:

```text
recurso.read
recurso.write
```

```text
USER
ADMIN
```

La diferencia debe tener sentido dentro del dominio elegido.

## Escenarios que la arquitectura debe permitir

```text
sin token
→ 401 Unauthorized

token válido sin permiso suficiente
→ 403 Forbidden

token válido con permiso correcto
→ operación permitida
```

## Seguridad en backend

Ocultar botones o rutas en el frontend no constituye autorización suficiente.

El backend debe poder actuar como **Resource Server** y proteger directamente sus recursos mediante Spring Security.

Conceptualmente debe poder validar:

- firma/token válido;
- issuer;
- audience;
- expiración;
- scopes, roles o authorities requeridas.

## Credenciales

No se deben versionar:

- contraseñas;
- client secrets;
- access tokens;
- refresh tokens;
- claves privadas;
- credenciales cloud.

Una SPA no debe contener `client_secret`.