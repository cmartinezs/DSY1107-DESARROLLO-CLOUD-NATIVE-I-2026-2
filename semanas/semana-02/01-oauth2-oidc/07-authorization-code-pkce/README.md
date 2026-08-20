# Authorization Code + PKCE

Este documento profundiza el flujo **Authorization Code + PKCE** utilizado por clientes modernos. El objetivo no es memorizar parámetros, sino comprender qué problema resuelve cada pieza y cómo se conectan entre sí.

## El problema que resuelve

El Authorization Server puede entregar un **Authorization Code** al cliente después de que el usuario se autentica y autoriza la operación.

Ese código todavía **no es un Access Token**. Debe ser intercambiado posteriormente por tokens.

El problema es que, si bastara con poseer el código, un tercero que lograra interceptarlo podría intentar intercambiarlo antes que el cliente legítimo.

PKCE agrega una prueba adicional:

> Quien intercambia el Authorization Code debe demostrar que conserva un valor secreto creado al inicio del flujo.

---

## 1. El cliente genera `code_verifier`

ReservApp genera localmente un valor aleatorio difícil de adivinar:

```text
code_verifier = mR9xQ2pL7vN4kT8sW1yF6dH3...
```

Este valor se conserva temporalmente en el cliente.

```text
¿Quién lo crea?     → el Client
¿Cuándo?            → antes de iniciar /authorize
¿Se envía al inicio?→ no
¿Para qué sirve?    → demostrar posteriormente que el cliente inició el flujo
```

> **Si quieres profundizar:** revisa [Code Verifier y Code Challenge](01-code-verifier-y-code-challenge.md).

---

## 2. El cliente calcula `code_challenge`

A partir del verifier se calcula un valor derivado:

```text
code_verifier
      ↓
    SHA-256
      ↓
 Base64URL
      ↓
code_challenge
```

Conceptualmente:

```text
code_challenge = BASE64URL(SHA256(code_verifier))
```

El mecanismo se identifica como `S256`.

La idea es sencilla:

```text
code_verifier  → prueba original
code_challenge → huella derivada
```

---

## 3. Authorization Request

Cuando el usuario presiona **Iniciar sesión**, ReservApp redirige el navegador hacia el Authorization Endpoint.

Una petición conceptual puede contener:

```http
GET /authorize?
    response_type=code
    &client_id=reservapp-web
    &redirect_uri=https://reservapp.cl/callback
    &scope=openid reservations.read
    &code_challenge=XYZ...
    &code_challenge_method=S256
```

Observa que aquí viaja:

```text
code_challenge
```

pero todavía **no** viaja:

```text
code_verifier
```

El Authorization Server conserva la asociación entre la transacción y el challenge recibido.

---

## 4. El usuario se autentica

El usuario se autentica ante el Identity Provider / Authorization Server.

```text
Usuario
   ↓
Identity Provider
   ├── contraseña
   ├── MFA
   ├── passkey
   └── otros mecanismos
```

La contraseña no necesita viajar hacia ReservApp API.

Dependiendo del escenario, el usuario puede además aprobar los scopes solicitados.

---

## 5. Authorization Code

Si el proceso es correcto, el Authorization Server genera un código temporal y redirige el navegador hacia el `redirect_uri` registrado:

```text
https://reservapp.cl/callback?code=abc123xyz
```

Debes recordar:

```text
Authorization Code ≠ Access Token
```

El código representa una autorización pendiente de intercambio y normalmente es de corta duración y un solo uso.

---

## 6. Token Exchange

ReservApp ahora posee:

```text
Authorization Code
        +
code_verifier original
```

Entonces llama al Token Endpoint:

```http
POST /token
```

con información equivalente a:

```text
grant_type=authorization_code
code=abc123xyz
code_verifier=mR9xQ2pL7vN4kT8sW1yF6dH3...
```

La relación importante es:

```text
/authorize → code_challenge
/token     → code_verifier
```

---

## 7. Validación PKCE

El Authorization Server toma el `code_verifier` recibido, repite la transformación y obtiene un challenge calculado.

```text
code_verifier recibido
        ↓
      SHA-256
        ↓
     Base64URL
        ↓
challenge calculado
```

Después compara:

```text
challenge calculado == challenge guardado
```

Si coinciden, el servidor tiene evidencia de que quien está intentando intercambiar el código conoce el verifier relacionado con el inicio de esa transacción.

Si no coinciden, el intercambio se rechaza.

> **Si quieres profundizar:** revisa [Qué ocurre durante la validación PKCE](02-validacion-pkce.md).

---

## 8. ¿Qué ocurre si alguien roba el Authorization Code?

Supongamos que un atacante obtiene:

```text
code=abc123xyz
```

Pero no conoce el `code_verifier` original.

Si inventa otro verifier, el challenge calculado será diferente al que el Authorization Server guardó al inicio.

```text
challenge-atacante != challenge-reservapp
```

Resultado:

```text
intercambio rechazado
```

Por eso PKCE protege el **intercambio del Authorization Code**.

---

## 9. Emisión de tokens

Si las validaciones son correctas, el Authorization Server puede entregar:

```json
{
  "access_token": "...",
  "id_token": "...",
  "token_type": "Bearer",
  "expires_in": 3600
}
```

Recordatorio:

```text
ID Token     → información verificable sobre autenticación/identidad
Access Token → autorización para acceder al Resource Server
```

---

## 10. Uso del Access Token

Una vez obtenido el Access Token, ReservApp puede llamar a la API:

```http
GET /reservas
Authorization: Bearer <access_token>
```

A esta altura, `code_verifier`, `code_challenge` y Authorization Code ya cumplieron su propósito dentro del proceso de obtención de tokens.

---

## Flujo completo resumido

```text
1. Client genera code_verifier
                ↓
2. Calcula code_challenge
                ↓
3. /authorize + code_challenge
                ↓
4. Usuario se autentica
                ↓
5. Authorization Server entrega Code
                ↓
6. /token + Code + code_verifier
                ↓
7. Servidor recalcula challenge
                ↓
8. ¿Coincide con el guardado?
       ├── No → rechaza
       └── Sí
            ↓
9. Access Token + ID Token
            ↓
10. Access Token → API
```

---

## Vocabulario

| Concepto | Significado |
|---|---|
| Authorization Endpoint | Endpoint donde comienza la autorización y normalmente interviene el navegador. |
| Token Endpoint | Endpoint donde el cliente intercambia el Authorization Code por tokens. |
| Authorization Code | Código temporal emitido por el Authorization Server. |
| PKCE | Proof Key for Code Exchange. Protege el intercambio del Authorization Code. |
| Code Verifier | Prueba aleatoria original creada por el cliente. |
| Code Challenge | Valor derivado del verifier que se envía al comenzar la autorización. |
| S256 | Método basado en SHA-256 utilizado para derivar el challenge. |
| Redirect URI | Dirección registrada a la que vuelve el navegador. |
| Client ID | Identificador público de la aplicación cliente. |
| Scope | Capacidades solicitadas/concedidas. |
| Access Token | Credencial utilizada para acceder al recurso protegido. |
| ID Token | Token OIDC con información verificable sobre la autenticación. |

---

## No confundas estos valores

```text
code_verifier      → prueba secreta original
code_challenge     → huella derivada del verifier
Authorization Code → código temporal del servidor
Access Token       → credencial para consumir una API
```

---

## Comprueba que entendiste

1. ¿Quién genera el `code_verifier`?
2. ¿Por qué no se envía el verifier en la primera petición?
3. ¿Qué diferencia existe entre verifier y challenge?
4. ¿Quién genera el Authorization Code?
5. ¿Por qué el Authorization Code no es un Access Token?
6. ¿Qué envía ReservApp al Token Endpoint?
7. ¿Qué comparación realiza el Authorization Server?
8. ¿Qué le faltaría a un atacante que consiguiera solamente el Authorization Code?

---

## Profundizaciones

- [Code Verifier y Code Challenge](01-code-verifier-y-code-challenge.md)
- [Validación PKCE](02-validacion-pkce.md)
- [State y nonce: protecciones relacionadas pero diferentes](03-state-y-nonce.md)
- [Errores frecuentes](04-errores-frecuentes.md)
