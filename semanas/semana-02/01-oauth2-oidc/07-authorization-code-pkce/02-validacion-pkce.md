# Validación PKCE

## Qué intenta comprobar el Authorization Server

Cuando el cliente intenta intercambiar un Authorization Code por tokens, el servidor necesita verificar que ese cliente conoce el `code_verifier` asociado al inicio del flujo.

El servidor ya había recibido y guardado el `code_challenge` durante `/authorize`.

En `/token` recibe el verifier.

---

## Proceso

```text
code_verifier recibido
        ↓
      SHA-256
        ↓
     Base64URL
        ↓
challenge calculado
```

Luego compara:

```text
challenge calculado == challenge guardado
```

Si coinciden, la prueba PKCE es válida.

Si no coinciden, el intercambio debe rechazarse.

---

## Qué ocurre con un código interceptado

Un atacante podría obtener el Authorization Code, pero eso no implica que también conozca el verifier.

```text
Atacante:
Authorization Code ✅
code_verifier      ❌
```

Si inventa otro verifier, la transformación genera otro challenge y la comparación falla.

```text
challenge inventado != challenge esperado
```

Por eso PKCE reduce el valor de un Authorization Code interceptado.

---

## Qué PKCE no reemplaza

PKCE no reemplaza:

- TLS/HTTPS;
- validación de `redirect_uri`;
- validación del Authorization Code;
- controles de expiración;
- `state` cuando corresponde;
- `nonce` en los usos OIDC donde corresponde;
- validación posterior de tokens.

Es una protección específica dentro de un flujo que necesita varias defensas complementarias.

---

## Idea que debes poder explicar

> El Authorization Server no confía únicamente en el Authorization Code. También verifica que el cliente que intenta intercambiarlo posee el verifier que corresponde al challenge registrado al inicio de esa misma transacción.
