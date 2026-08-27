# 1 · MSAL y autenticación de frontend

## Objetivo

Comprender qué resuelve **MSAL (Microsoft Authentication Library)** y cómo participa en una aplicación frontend que autentica usuarios mediante Microsoft Entra ID / Microsoft Identity Platform.

## Qué problema resuelve

Una SPA no debe implementar OAuth2/OIDC manualmente. MSAL encapsula gran parte de la interacción con el proveedor de identidad:

```text
SPA
→ inicia login
→ redirige al proveedor de identidad
→ usuario se autentica
→ vuelve con authorization code
→ PKCE protege el intercambio
→ MSAL obtiene tokens
→ SPA solicita una API usando access token
```

## Authorization Code + PKCE

Para aplicaciones públicas como SPA se utiliza Authorization Code Flow con PKCE.

PKCE agrega un `code_verifier` y un `code_challenge`. Aunque un atacante intercepte el authorization code, no puede intercambiarlo sin el verifier original.

## ID token vs access token

| Token | Propósito principal | Destinatario |
|---|---|---|
| ID token | informar al cliente sobre la autenticación del usuario | aplicación cliente |
| Access token | autorizar acceso a una API | API / Resource Server |

**No se debe enviar un ID token a una API como sustituto del access token.**

## Configuración conceptual de MSAL

Una SPA normalmente necesita:

- `clientId` público de la aplicación registrada;
- `authority` o tenant esperado;
- `redirectUri`;
- scopes requeridos por la API.

No necesita un `client_secret` embebido en JavaScript.

## Ejemplo conceptual

```javascript
const config = {
  auth: {
    clientId: "<spa-client-id>",
    authority: "https://login.microsoftonline.com/<tenant-id>",
    redirectUri: "http://localhost:5173"
  }
};
```

La configuración pública identifica a la aplicación; no es un secreto.

## Solicitud de scopes

```javascript
const loginRequest = {
  scopes: ["openid", "profile", "api://<api-client-id>/read"]
};
```

Los scopes expresan capacidades solicitadas. La API debe validar que el token efectivamente contiene el permiso requerido.

## Ciclo mínimo

1. inicializar MSAL;
2. autenticar usuario;
3. seleccionar cuenta activa;
4. solicitar access token para la API;
5. enviar `Authorization: Bearer <token>`;
6. tratar errores de autenticación/autorización.

## Errores frecuentes

- guardar secretos en el frontend;
- pedir scopes que la API no reconoce;
- confundir ID token con access token;
- asumir que decodificar un token significa que es válido;
- almacenar tokens innecesariamente en mecanismos expuestos a XSS;
- ignorar expiración y renovación silenciosa;
- mezclar autenticación con autorización.

## Preguntas de comprobación

1. ¿Por qué una SPA es un public client?
2. ¿Qué amenaza mitiga PKCE?
3. ¿Quién consume el access token?
4. ¿Por qué el frontend no debe tener un client secret?
5. ¿Qué ocurre si la API recibe un token con audience incorrecta?
