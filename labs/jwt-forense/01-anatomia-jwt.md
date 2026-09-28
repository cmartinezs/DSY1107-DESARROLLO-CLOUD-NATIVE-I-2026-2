# Etapa 1 · Anatomía de un JWT

## Objetivo

Leer la estructura de un JWT sin confundir lectura con confianza.

Un JWT compacto suele representarse como:

```text
header.payload.signature
```

## Header conceptual

```json
{
  "alg": "RS256",
  "typ": "JWT",
  "kid": "key-01"
}
```

`kid` ayuda al validador a seleccionar una clave pública compatible dentro de un JWKS; por sí solo no valida nada.

## Payload de trabajo

```json
{
  "iss": "https://identity.example/",
  "aud": "products-api",
  "sub": "user-123",
  "exp": 1790000000,
  "scp": "products.read"
}
```

Completa:

| Claim | Pregunta que responde | Comprobación |
|---|---|---|
| `iss` | ¿quién lo emitió? | coincide con issuer confiable |
| `aud` | ¿para qué recurso? | incluye `products-api` |
| `sub` | ¿quién es el sujeto? | identificador no vacío |
| `exp` | ¿hasta cuándo? | instante futuro al validar |
| `scp` | ¿qué permisos delegados? | contiene scope requerido |

## Regla

Base64URL es codificación, no cifrado. Que el payload pueda leerse es normal.

## Checkpoint 1

Explica qué información entrega cada claim y cuáles participan en validación técnica versus autorización.
