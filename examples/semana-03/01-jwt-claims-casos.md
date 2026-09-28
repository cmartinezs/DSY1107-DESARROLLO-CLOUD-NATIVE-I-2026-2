# Ejemplo · JWT y claims bajo lupa

Payload conceptual:

```json
{
  "iss": "https://identity.example/",
  "aud": "products-api",
  "sub": "user-123",
  "exp": 1780000000,
  "scp": "products.read"
}
```

## Casos

### Audience incorrecta

Si `aud=billing-api`, el token no fue emitido para `products-api`.

### Expiración

Si `exp` ya pasó, el token no está vigente.

### Scope insuficiente

`products.read` no autoriza necesariamente `POST /products`.

## Regla

Leer el payload no valida firma, issuer, audience ni tiempo.

```text
decode ≠ verify ≠ authorize
```
