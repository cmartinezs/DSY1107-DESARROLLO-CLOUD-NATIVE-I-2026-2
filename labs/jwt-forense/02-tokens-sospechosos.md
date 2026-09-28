# Etapa 2 · Tokens sospechosos

## Objetivo

Diagnosticar una credencial usando condiciones concretas en lugar de decir solamente “token malo”.

## Caso A · Audience incorrecta

```json
{ "iss": "https://identity.example/", "aud": "billing-api", "scp": "products.read" }
```

La firma podría ser válida y el issuer confiable, pero el token no está destinado a `products-api`. Resultado esperado: rechazo de autenticación para este recurso, normalmente 401.

## Caso B · Expirado

El token contiene un `exp` anterior al instante actual. Aunque el resto coincida, ya no es vigente.

## Caso C · Scope insuficiente

Token técnicamente válido:

```text
aud = products-api
scp = products.read
```

Request: `POST /products`. El recurso exige `products.write`. Resultado esperado: 403.

## Caso D · Regla de negocio

Token válido + `products.write`, pero el usuario intenta eliminar un producto bloqueado por política de negocio. El token no resuelve la autorización contextual del dominio.

## Matriz

| Caso | Falla | Tipo | Frontera posible |
|---|---|---|---|
| A | audience | validación técnica | Gateway/backend |
| B | expiración | validación técnica | Gateway/backend |
| C | scope | autorización técnica | Gateway/backend |
| D | regla de negocio | autorización de dominio | backend |

## Checkpoint 2

Para cada rechazo, nombrar exactamente la condición y la frontera que puede observarla.
