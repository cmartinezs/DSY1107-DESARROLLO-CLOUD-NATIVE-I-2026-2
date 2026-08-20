# State y nonce: protecciones relacionadas pero diferentes

Cuando se estudia Authorization Code + PKCE aparecen varios valores aparentemente aleatorios. No todos protegen lo mismo.

## PKCE

PKCE vincula el inicio del flujo con el posterior intercambio del Authorization Code.

```text
code_verifier ↔ code_challenge
```

Pregunta que ayuda a responder:

> ¿Quien está intercambiando este código demuestra conocer la prueba creada al comenzar el flujo?

---

## State

`state` permite al cliente relacionar la respuesta recibida con una solicitud que él mismo inició y es una protección importante frente a ataques relacionados con la transacción, incluyendo CSRF según el diseño del flujo.

Conceptualmente:

```text
Client genera state
      ↓
lo envía en /authorize
      ↓
Authorization Server lo devuelve
      ↓
Client comprueba que sea el esperado
```

Pregunta pedagógica:

> ¿Esta respuesta pertenece a una transacción que yo inicié?

---

## Nonce

`nonce` pertenece a OpenID Connect y se utiliza para vincular la autenticación y el ID Token con la solicitud iniciada por el cliente, ayudando a prevenir reutilizaciones indebidas de respuestas.

Pregunta pedagógica:

> ¿Este resultado de autenticación corresponde a la solicitud OIDC que esperaba?

---

## Comparación rápida

| Valor | Principal propósito didáctico |
|---|---|
| `code_verifier` / `code_challenge` | Proteger el intercambio del Authorization Code. |
| `state` | Vincular solicitud y respuesta de autorización / proteger la transacción. |
| `nonce` | Vincular la respuesta de autenticación OIDC y el ID Token con la solicitud esperada. |

No deben enseñarse como "tres tokens de seguridad". Son mecanismos distintos que protegen partes diferentes del flujo.
