# 1 · MSAL y autenticación de frontend

## Objetivo

Comprender qué resuelve **MSAL (Microsoft Authentication Library)** y cómo participa en una aplicación frontend que autentica usuarios mediante Microsoft Entra ID / Microsoft Identity Platform.

Esta guía explica el concepto. La implementación paso a paso en la miniapp de la semana vive en:

→ [Firebase miniapp · Parte 9: Microsoft Entra ID + MSAL](../../labs/firebase-auth-miniapp/09-microsoft-entra-msal.md)

Para el caso grupal en que el dueño del tenant entra pero sus compañeros no:

→ [Entra ID · usuarios externos en SPA + API Gateway](../../docs/identity/entra-usuarios-externos-spa-api-gateway.md)

---

## Qué problema resuelve

Una SPA no debe implementar OAuth2/OIDC manualmente. MSAL encapsula gran parte de la interacción con el proveedor de identidad:

```text
SPA
→ inicia login
→ redirige/abre interacción con Microsoft Entra ID
→ usuario se autentica
→ vuelve con authorization code
→ PKCE protege el intercambio
→ MSAL obtiene tokens
→ SPA mantiene contexto de cuenta
→ SPA puede solicitar access token para una API
```

## Authorization Code + PKCE

Para aplicaciones públicas como SPA se utiliza Authorization Code Flow con PKCE.

PKCE agrega un `code_verifier` y un `code_challenge`. Aunque un atacante intercepte el authorization code, no puede intercambiarlo sin el verifier original.

La SPA sigue siendo un **public client**: no puede guardar de forma segura un `client_secret`.

---

## ID token vs access token

| Token | Propósito principal | Destinatario |
|---|---|---|
| ID token | informar al cliente sobre la autenticación del usuario | aplicación cliente |
| Access token | autorizar acceso a una API | API / Resource Server |

**No se debe enviar un ID token a una API como sustituto del access token.**

---

## Configuración de MSAL

Una SPA normalmente necesita:

- `clientId` público de la aplicación registrada;
- `authority` o tenant esperado;
- `redirectUri`;
- scopes requeridos por la API cuando corresponda.

No necesita un `client_secret` embebido en JavaScript.

Para DSY1107 usamos inicialmente un tenant específico:

```javascript
const config = {
  auth: {
    clientId: "<spa-client-id>",
    authority: "https://login.microsoftonline.com/<tenant-id>",
    redirectUri: "http://localhost:5173/redirect.html"
  }
};
```

### Nota importante para el stack actual

En la miniapp Vite utilizamos una página de redirect dedicada:

```text
http://localhost:5173/redirect.html
```

La guía práctica crea esa página con el **MSAL redirect bridge**. No reemplazarla por una página con router o lógica principal de la aplicación.

---

## Inicialización

En MSAL Browser actual, la instancia debe inicializarse antes de utilizar APIs interactivas:

```javascript
const msalInstance = new PublicClientApplication(config);
await msalInstance.initialize();
```

Después se puede recuperar/seleccionar una cuenta activa y ejecutar Login/Logout.

---

## Usuarios del tenant

Una App Registration configurada como:

```text
Accounts in this organizational directory only
```

es **single-tenant**.

Eso no significa "solo el alumno que creó el tenant". Pueden autenticarse:

- Members del tenant;
- usuarios externos invitados como Guest/B2B.

Por eso, cuando al dueño le funciona y al compañero no, primero se revisan:

```text
tenant
→ Guest
→ invitación aceptada
→ authority
→ redirect URI
→ Assignment required?
```

No se cambia automáticamente a multitenant para ocultar el problema.

---

## Login de identidad vs access token para API propia

Primera etapa:

```text
usuario
→ Entra ID
→ MSAL
→ cuenta autenticada en SPA
```

Segunda etapa, cuando existe API protegida:

```text
SPA
→ solicita scope de API propia
→ obtiene access token para esa API
→ Authorization: Bearer <token>
→ API Gateway / Resource Server
```

Una confusión frecuente es solicitar un token para Microsoft Graph y enviarlo al backend propio. Ese token tiene otro recurso/audience.

---

## Solicitud de scopes de API

Para una API propia que expone:

```text
api://<api-client-id>/api.read
```

la SPA solicita:

```javascript
const tokenRequest = {
  scopes: ["api://<api-client-id>/api.read"]
};
```

El backend/gateway debe validar que el access token corresponde a la API esperada y contiene el permiso requerido.

---

## Ciclo mínimo

1. registrar SPA en Entra ID;
2. configurar plataforma SPA y redirect URI;
3. invitar Guest si el grupo usa single-tenant;
4. instalar `@azure/msal-browser`;
5. inicializar `PublicClientApplication`;
6. autenticar usuario;
7. seleccionar cuenta activa;
8. si existe API, solicitar access token para la API propia;
9. enviar `Authorization: Bearer <token>`;
10. interpretar correctamente errores de autenticación y autorización.

---

## Errores frecuentes

- guardar secretos en el frontend;
- usar MSAL antes de `await initialize()`;
- redirect URI diferente del registrado;
- usar `common` cuando el ejercicio exige tenant concreto;
- olvidar invitar/activar al compañero Guest;
- pedir scopes que la API no reconoce;
- confundir ID token con access token;
- obtener un token para Graph y enviarlo a la API propia;
- asumir que decodificar un token significa que es válido;
- almacenar tokens manualmente sin necesidad;
- ignorar expiración y renovación silenciosa;
- mezclar autenticación con autorización.

---

## Preguntas de comprobación

1. ¿Por qué una SPA es un public client?
2. ¿Qué amenaza mitiga PKCE?
3. ¿Qué diferencia hay entre la cuenta MSAL y el access token para una API?
4. ¿Por qué el frontend no debe tener un client secret?
5. ¿Qué ocurre si la API recibe un token con audience incorrecta?
6. ¿Por qué un compañero puede necesitar ser Guest aun usando su propio correo?
7. ¿Por qué no usamos `common` en el ejercicio single-tenant?
8. ¿Por qué Microsoft Entra/MSAL no es simplemente "otro botón Firebase" en esta práctica?

---

## Ruta práctica

→ [Implementar Microsoft Entra ID + MSAL en la miniapp](../../labs/firebase-auth-miniapp/09-microsoft-entra-msal.md)
