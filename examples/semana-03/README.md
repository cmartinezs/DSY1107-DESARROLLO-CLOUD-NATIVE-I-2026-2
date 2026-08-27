# Ejemplos · Semana 3

Ejemplos breves, autocontenidos e independientes de RegistrApp para observar JWT, claims y autorización.

## Caso · Products API

```text
cliente → IdP → access token → API Gateway → products-api
```

Token conceptual:

```json
{
  "iss": "https://identity.example/",
  "aud": "products-api",
  "sub": "user-123",
  "exp": 1780000000,
  "scope": "products.read"
}
```

Casos mínimos:

1. `aud=billing-api` → rechazo del token para `products-api`;
2. `exp` vencido → rechazo por token no vigente;
3. token válido con `products.read` sobre `POST /products` que exige `products.write` → rechazo de autorización;
4. token válido con scope correcto → continúa a reglas de negocio.

## Objetivo

El ejemplo permite distinguir:

- decodificar vs verificar;
- autenticación vs autorización;
- issuer/audience/expiración;
- scopes;
- gateway vs backend;
- 401 vs 403 como resultado del punto donde falla el pipeline.

→ [Contenido Semana 3](../../semanas/semana-03/)  
→ [Laboratorio canónico JWT forense](../../labs/jwt-forense/)
