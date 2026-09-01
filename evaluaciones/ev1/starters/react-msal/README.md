# React + MSAL · Starter EV1

Este starter muestra la integración mínima de una SPA React con Microsoft Entra ID mediante MSAL.

## Objetivo

La aplicación React debe poder:

1. iniciar sesión mediante redirección al proveedor de identidad;
2. recuperar la cuenta autenticada;
3. obtener un Access Token para la API;
4. enviar `Authorization: Bearer <token>` al backend;
5. cerrar sesión;
6. proteger componentes/rutas que requieren autenticación.

MSAL Browser implementa Authorization Code + PKCE para aplicaciones SPA. El código del proyecto no genera manualmente `code_verifier`, `code_challenge`, authorization codes ni Access Tokens.

## Dependencias

```bash
npm install @azure/msal-browser @azure/msal-react
```

## Archivos

- `msalConfig.ts`: configuración del cliente y scopes.
- `AuthProvider.tsx`: inicializa MSAL y expone `MsalProvider`.
- `useAuth.ts`: login, logout, cuenta y adquisición explícita de Access Token.
- `ProtectedRoute.tsx`: ejemplo simple para contenido autenticado.
- `apiClient.ts`: llamada a la API usando Bearer Token.
- `.env.example`: variables que debe configurar cada proyecto.

## Variables

Copiar `.env.example` a `.env.local` o al mecanismo equivalente del proyecto y completar:

```text
VITE_ENTRA_CLIENT_ID
VITE_ENTRA_TENANT_ID
VITE_API_SCOPE
VITE_API_BASE_URL
```

No versionar secretos. Una SPA es un cliente público y no debe contener `client_secret`.

## Flujo mínimo

```text
Usuario
  ↓
loginRedirect()
  ↓
Microsoft Entra ID
  ↓
Authorization Code + PKCE gestionado por MSAL
  ↓
React recibe sesión
  ↓
acquireTokenSilent()
  ↓
Access Token
  ↓
Authorization: Bearer <token>
  ↓
Spring Resource Server
```

## Integración

En el punto de entrada de React:

```tsx
import { AuthProvider } from './auth/AuthProvider';

root.render(
  <AuthProvider>
    <App />
  </AuthProvider>
);
```

Luego, desde un componente:

```tsx
const { login, logout, account, getAccessToken } = useAuth();
```

Para una llamada protegida:

```ts
const token = await getAccessToken();
const data = await apiGet('/api/demo', token);
```

## Qué debe entender el estudiante

- `clientId` identifica a la SPA registrada en Entra ID.
- `authority` identifica el tenant/proveedor contra el que se autentica.
- `redirectUri` debe coincidir con una URI registrada para la aplicación.
- `scope` expresa el permiso solicitado para llamar la API.
- `acquireTokenSilent` intenta obtener un token válido sin obligar al usuario a autenticarse nuevamente.
- el Access Token se envía a la API, no el ID Token.

## Importante

El starter no resuelve el dominio de la aplicación. Cada equipo debe integrar estas piezas en su propia estructura y reemplazar los scopes/endpoints de ejemplo por los definidos para su proyecto.