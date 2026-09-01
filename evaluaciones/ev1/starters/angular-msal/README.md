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
- `auth.config.ts`: crea la instancia `PublicClientApplication`.
- `app.config.example.ts`: ejemplo de providers para una aplicación Angular standalone.
- `auth.service.ts`: login, logout, cuenta, manejo del redirect y adquisición explícita de Access Token.
- `auth.guard.ts`: guard pedagógico mínimo para rutas autenticadas.
- `auth.interceptor.ts`: interceptor pedagógico que obtiene un token y agrega Bearer para la API.

> MSAL Angular también incluye `MsalGuard` y `MsalInterceptor`. Los archivos de este starter mantienen el flujo visible para facilitar el aprendizaje. Usar las implementaciones oficiales directamente también es válido si el proyecto conserva el mismo comportamiento.

## Configuración

1. Copiar `environment.example.ts` a la ubicación de configuración del proyecto con el nombre/import que corresponda, por ejemplo `environment.ts`.
2. Completar:

```text
clientId
tenantId
apiScope
apiBaseUrl
```

3. Incorporar los providers mostrados en `app.config.example.ts` al `app.config.ts` real del proyecto.
4. Registrar en Entra ID la URI con la que se ejecutará la SPA, por ejemplo `http://localhost:4200`.

Una SPA es un cliente público. No debe contener `client_secret`.

## Uso del guard

Ejemplo:

```ts
{
  path: 'privado',
  loadComponent: () => import('./privado/privado.component')
    .then(m => m.PrivadoComponent),
  canActivate: [authGuard]
}
```

El guard controla navegación del frontend. **No sustituye la autorización del backend.**

## Uso del servicio

```ts
constructor(public readonly auth: AuthService) {}
```

Operaciones disponibles:

```text
isAuthenticated()
getAccount()
login()
logout()
getAccessToken()
```

## Interceptor

`auth.interceptor.ts` solo agrega Bearer Token a URLs bajo:

```text
${apiBaseUrl}/api/
```

Por ello una llamada normal mediante `HttpClient`:

```ts
this.http.get(`${environment.apiBaseUrl}/api/demo`)
```

puede transformarse en:

```http
Authorization: Bearer <access_token>
```

sin que cada componente tenga que manipular tokens.

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
handleRedirectObservable()
  ↓
cuenta activa
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
- `handleRedirectObservable()` procesa el retorno de una interacción basada en redirect.
- `acquireTokenSilent()` intenta obtener un Access Token sin una nueva interacción visible.
- el Access Token es el token enviado a la API.
- el ID Token describe la autenticación del usuario y no sustituye al Access Token.
- el guard protege navegación del frontend; la seguridad real del recurso sigue siendo responsabilidad del backend.
