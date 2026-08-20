# Errores frecuentes en Authorization Code + PKCE

## 1. "El Authorization Code es el Access Token"

Incorrecto.

El Authorization Code es temporal y debe intercambiarse en el Token Endpoint. El Access Token es la credencial que posteriormente se presenta al Resource Server.

---

## 2. "El code_verifier lo genera el servidor"

Incorrecto.

Lo genera el **Client** antes de iniciar el Authorization Request.

---

## 3. "El code_challenge es un secreto"

No debe tratarse como la prueba secreta original. Es un valor derivado del verifier y se envía durante la petición de autorización.

---

## 4. "PKCE autentica al usuario"

Incorrecto.

PKCE no sustituye el mecanismo mediante el cual el Identity Provider autentica al usuario. Su función es proteger el intercambio del Authorization Code.

---

## 5. "PKCE reemplaza HTTPS"

Incorrecto.

PKCE es una defensa adicional. El flujo sigue requiriendo comunicaciones seguras y el resto de las validaciones del protocolo.

---

## 6. "ID Token y Access Token sirven para lo mismo"

Incorrecto.

El ID Token pertenece a OIDC y está orientado al Client para comunicar información verificable sobre la autenticación. El Access Token está destinado al Resource Server.

---

## 7. "state, nonce y PKCE son lo mismo"

Incorrecto.

Protegen aspectos diferentes del flujo. Revisa [State y nonce](03-state-y-nonce.md) para compararlos.

---

## Prueba rápida

Si puedes explicar correctamente esta frase, entendiste la idea esencial:

> ReservApp crea un verifier, envía primero una huella derivada de él y, cuando recibe el Authorization Code, presenta el verifier original para demostrar que el intercambio pertenece al mismo flujo que inició.
