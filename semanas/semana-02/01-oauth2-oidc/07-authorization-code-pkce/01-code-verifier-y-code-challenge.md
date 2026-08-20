# Code Verifier y Code Challenge

## Idea central

PKCE utiliza dos valores relacionados:

```text
code_verifier  → prueba secreta original
code_challenge → valor derivado de esa prueba
```

El cliente crea ambos al comenzar el flujo, pero no los envía al mismo tiempo.

---

## Code Verifier

El `code_verifier` es un valor aleatorio de alta entropía generado por el cliente.

Ejemplo conceptual:

```text
mR9xQ2pL7vN4kT8sW1yF6dH3jC0...
```

No representa al usuario, no es una contraseña del usuario y no es un token.

Su propósito es funcionar como una prueba temporal asociada a una única transacción de autorización.

```text
¿Quién lo crea? → Client
¿Quién debe conservarlo? → Client
¿Cuándo se revela? → durante el Token Exchange
```

---

## Code Challenge

El cliente deriva el `code_challenge` desde el verifier.

Para `S256`:

```text
code_challenge = BASE64URL(SHA256(code_verifier))
```

La idea pedagógica es pensar en el challenge como una **huella** del verifier.

```text
verifier
   ↓
transformación
   ↓
challenge
```

El Authorization Server recibe inicialmente el challenge, pero no el verifier.

---

## ¿Por qué enviar primero el challenge?

Porque el servidor necesita guardar una referencia verificable de la prueba sin recibir todavía la prueba original.

Más adelante, cuando el cliente presenta el verifier, el servidor puede repetir la transformación y comprobar si produce el challenge esperado.

```text
Inicio del flujo
Client → Authorization Server: code_challenge

Intercambio
Client → Authorization Server: code_verifier
```

---

## Analogía

Imagina que ReservApp escribe una palabra secreta y conserva el papel.

Antes de iniciar el flujo entrega al servidor solamente una huella digital de ese papel.

Cuando llega el momento del intercambio, muestra el papel original. El servidor genera nuevamente la huella y comprueba que sea la misma.

La analogía sirve para entender el propósito, aunque técnicamente estamos hablando de valores digitales y una transformación criptográfica.

---

## No confundas

`code_verifier` no es:

- contraseña del usuario;
- Client Secret;
- Authorization Code;
- Access Token;
- ID Token.

`code_challenge` tampoco es un secreto que permita consumir la API.

Ambos existen para proteger una parte específica del flujo: el intercambio del Authorization Code.

---

## Comprueba que entendiste

1. ¿Cuál de los dos valores se conserva inicialmente en el cliente?
2. ¿Cuál se envía al Authorization Endpoint?
3. ¿Cuál se envía al Token Endpoint?
4. ¿Qué relación matemática existe entre ambos cuando se usa `S256`?
5. ¿Por qué un Authorization Server no compara directamente dos verifiers almacenados?
