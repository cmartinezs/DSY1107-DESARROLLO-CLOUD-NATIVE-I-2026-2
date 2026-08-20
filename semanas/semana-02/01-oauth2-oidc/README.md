# Profundización · OAuth2 y OpenID Connect (OIDC)

← [Volver al contenido base](../01-oauth2-oidc.md)

Esta carpeta **expande** el contenido oficial de `01-oauth2-oidc.md`. No lo reemplaza.

> Si estás estudiando la base de la asignatura, el archivo `01-oauth2-oidc.md` contiene lo necesario. Entra aquí cuando quieras comprender con mayor detalle una parte del flujo.

## ¿Qué puedes profundizar aquí?

El primer subtema desarrollado en profundidad es **Authorization Code + PKCE**, porque detrás del diagrama general aparecen varios conceptos que conviene separar:

- authorization code;
- `code_verifier`;
- `code_challenge`;
- transformación `S256`;
- Authorization Endpoint;
- Token Endpoint;
- intercambio del código;
- validación PKCE;
- `state` y `nonce`;
- errores y confusiones frecuentes.

## Ruta recomendada

### Authorization Code + PKCE

→ [Abrir profundización completa](./07-authorization-code-pkce/README.md)

Dentro encontrarás:

1. [Code Verifier y Code Challenge](./07-authorization-code-pkce/01-code-verifier-y-code-challenge.md)
2. [Cómo valida PKCE el Authorization Server](./07-authorization-code-pkce/02-validacion-pkce.md)
3. [State y Nonce](./07-authorization-code-pkce/03-state-y-nonce.md)
4. [Errores frecuentes](./07-authorization-code-pkce/04-errores-frecuentes.md)

## Cómo usar este material

```text
01-oauth2-oidc.md
        ↓
contenido base suficiente
        │
        └── quiero entender más
                ↓
01-oauth2-oidc/README.md
                ↓
07-authorization-code-pkce/
                ↓
detalles especializados
```

La profundización puede crecer con nuevos documentos cuando una duda recurrente o un concepto justifique mayor detalle, pero nunca debe quitar contenido indispensable del Markdown base.