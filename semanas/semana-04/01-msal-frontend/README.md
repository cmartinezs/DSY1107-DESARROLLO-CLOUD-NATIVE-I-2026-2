# Contenido extendido · MSAL y autenticación de frontend

Complementa [MSAL y autenticación de frontend](../01-msal-frontend.md).

## 1. Rol de MSAL

MSAL evita que una SPA implemente manualmente detalles delicados de OAuth2/OIDC. Su responsabilidad es ayudar a:

- iniciar autenticación;
- completar Authorization Code + PKCE;
- mantener contexto de cuenta;
- solicitar access tokens para recursos concretos;
- renovar tokens cuando sea posible sin repetir login interactivo.

MSAL no reemplaza al proveedor de identidad ni decide reglas de autorización del backend.

## 2. SPA como public client

Una SPA distribuye su código al navegador. Por eso cualquier secreto incluido en JavaScript debe considerarse público.

```text
clientId = identificador público
client_secret = secreto que una SPA no puede custodiar
```

Esta es la razón arquitectónica de usar Authorization Code + PKCE.

## 3. Authorization Code + PKCE paso a paso

```text
1. SPA genera code_verifier
2. deriva code_challenge
3. redirige al Identity Provider
4. usuario se autentica
5. IdP devuelve authorization code
6. SPA intercambia code + verifier
7. IdP valida correspondencia
8. entrega tokens
```

Un authorization code interceptado no basta sin el verifier original.

## 4. ID token y access token

El ID token responde principalmente:

> ¿Quién inició sesión en el cliente?

El access token responde:

> ¿Qué recurso acepta esta credencial y qué permisos fueron delegados?

Enviar un ID token al backend como Bearer puede “parecer funcionar” en demos mal configuradas, pero es conceptualmente incorrecto.

## 5. Audience y scopes

Un token no es válido “para todo”.

Ejemplo:

```text
Token A → aud = Microsoft Graph
Token B → aud = BookShelf API
```

Aunque ambos hayan sido emitidos por el mismo proveedor, la API propia debe aceptar solo el token destinado a ella.

## 6. Adquisición silenciosa vs interactiva

En una SPA real conviene intentar primero obtener un token silenciosamente desde la sesión/cache administrada por MSAL.

Si se requiere interacción:

```text
silent request falla por condición de interacción
→ login/popup/redirect según estrategia
```

No transformar todo error en un nuevo login.

## 7. Riesgos comunes

- almacenar tokens manualmente en lugares innecesarios;
- registrar tokens completos en consola;
- usar scopes equivocados;
- mezclar configuración SPA y API;
- confundir autenticación con autorización;
- introducir client secret en frontend;
- asumir que login exitoso significa acceso autorizado al backend.

## 8. Diagnóstico

Cuando el frontend “inicia sesión pero la API responde 401/403”, revisar en orden:

1. ¿existe sesión MSAL?;
2. ¿se solicitó el scope de la API propia?;
3. ¿el access token tiene audience correcta?;
4. ¿el token está vigente?;
5. ¿el Bearer header se envía?;
6. ¿el backend acepta issuer/audience?;
7. ¿existe el permiso requerido?

## 9. Ejercicio

Compara dos tokens y responde:

- ¿quién los emitió?;
- ¿para qué audience?;
- ¿qué scopes contienen?;
- ¿cuál debería enviarse a la API propia?;
- ¿qué ocurriría si enviamos el otro?

## 10. Idea clave

MSAL resuelve el flujo cliente, pero la seguridad completa depende de que cada frontera valide el contexto correcto.
