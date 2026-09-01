# Angular + MSAL · Starter EV1

Este starter muestra la integración mínima de una SPA Angular con Microsoft Entra ID mediante `@azure/msal-angular` y `@azure/msal-browser`.

## Objetivo

La aplicación Angular debe poder:

1. iniciar sesión mediante redirección al proveedor de identidad;
2. identificar la cuenta autenticada;
3. obtener Access Tokens para la API;
4. adjuntar `Authorization: Bearer <token>` a llamadas protegidas;
5. cerrar sesión;
6. proteger rutas que requieren autenticación.

MSAL gestiona Authorization Code + PKCE. La aplicación no implementa manualmente el protocolo ni crea sus propios tokens.

## Dependencias

```bash
npm install @azure/msal-browser @azure/msal-angular
```

## Archivos

- `environment.example.ts`: configuración que debe adaptar cada proyecto.
- `auth.config.ts`: instancia MSAL, guard e interceptor.
- `auth.service.ts`: login, logout, cuenta y adquisición explícita de token.
- `auth.guard.ts`: guard simple pedagógico para rutas autenticadas.
- `auth.interceptor.ts`: interceptor pedagógico que obtiene un token y agrega Bearer para la API.

> MSAL Angular también incluye `MsalGuard` y `MsalInterceptor`. Los archivos de este starter mantienen el flujo visible para facilitar el aprendizaje. Si el proyecto utiliza las implementaciones oficiales directamente, debe conservar el mismo comportamiento y configuración.

## Configuración

Adaptar los valores de `environment.example.ts`:

```text
clientId
 tenantId
 apiScope
 apiBaseUrl
```

Una SPA es un cliente público. No debe contener `client_secret`.

## Flujo

```text
Usuario
  ↓
loginRedirect()
  ↓
Microsoft Entra ID
  ↓
Authorization Code + PKCE gestionado por MSAL
  ↓
Angular mantiene la cuenta autenticada
  ↓
acquireTokenSilent()
  ↓
Access Token
  ↓
Authorization: Bearer <token>
  ↓
Spring Resource Server
```

## Qué debe entender el estudiante

- `clientId`: identifica a la SPA.
- `authority`: tenant/proveedor contra el que se autentica.
- `redirectUri`: URI registrada para recibir el retorno del login.
- `scope`: permiso solicitado para la API.
- el Access Token es el token enviado a la API.
- el ID Token describe la autenticación del usuario y no sustituye al Access Token.
- el guard protege navegación del frontend; la seguridad real del recurso sigue siendo responsabilidad del backend.
