# 03A · Starter mínimo Angular + MSAL

Este anexo reduce boilerplate. El alumno reemplaza valores, ejecuta el flujo y explica qué hace cada pieza; no implementa OAuth2/PKCE manualmente.

## Archivos mínimos

```text
src/app/
├── auth-config.ts
├── auth.service.ts
├── api.service.ts
├── app.component.ts
├── app.component.html
└── app.config.ts
```

## Dependencia

```bash
npm install @azure/msal-browser
```

## `auth-config.ts`

```ts
export const authConfig = {
  clientId: '<SPA_CLIENT_ID>',
  authority: '<MSAL_AUTHORITY>',
  redirectUri: window.location.origin,
  readScopes: ['<SCOPE_READ>'],
  writeScopes: ['<SCOPE_WRITE>']
};

export const apiConfig = {
  baseUrl: 'http://localhost:8080'
};
```

**VALIDAR** · Ningún valor debe contener un secret.

## Responsabilidades que sí debe entender

```text
clientId        → identifica SPA
authority       → tenant/autoridad correcta
redirectUri     → URL exacta registrada
scope           → permiso solicitado a la API
Access Token    → Bearer para API
ID Token        → identidad/sesión del cliente
```

## `AuthService`

Usar `PublicClientApplication`, inicializar una sola vez, procesar redirect, establecer active account y adquirir Access Token silenciosamente con fallback interactivo.

Regla del starter:

```text
login()         → scopes de lectura
getReadToken()  → tasks.read
getWriteToken() → tasks.write
logout()        → logoutRedirect
```

Separar lectura/escritura permite observar scopes distintos sin crear lógica artificial.

## `ApiService`

Debe contener solo:

```text
health()       GET público
me()           GET + read token
tasks()        GET + read token
createTask()   POST + write token
deleteTask()   DELETE + write token
```

Cada protected request agrega:

```http
Authorization: Bearer <ACCESS_TOKEN>
```

No crear interceptores complejos si agregar explícitamente el header hace más observable el aprendizaje.

## UI mínima

```text
CloudTasks
[Iniciar sesión] [Cerrar sesión]
Usuario: ...
[Ver mi identidad]
[Recargar tareas]
Título: [________] [Crear]
- tarea 1 [Eliminar]
Estado: ...
```

Sin routing adicional, CSS framework, state manager ni componentes que no aporten al objetivo de aprendizaje.

## Checkpoint 03A-1 · compilación

```bash
npm start
```

- [ ] Angular compila.
- [ ] Console sin errores de aplicación.
- [ ] no hay placeholders TypeScript inválidos.

## Checkpoint 03A-2 · autenticación

- [ ] botón login abre External ID.
- [ ] vuelve a `http://localhost:4200`.
- [ ] existe active account.
- [ ] logout termina la sesión local.

## Checkpoint 03A-3 · autorización

- [ ] read token tiene `aud` correcto.
- [ ] read token contiene scope esperado.
- [ ] write token permite POST/DELETE según política.
- [ ] ID Token no se utiliza como Bearer.

**SI FALLA** · diagnosticar en orden: redirect URI → authority → app registration → user flow → API permissions/scopes → token claims. No modificar CORS para corregir un error de adquisición de token.
