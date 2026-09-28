# Etapa 0 · Contexto y contrato de análisis

## Objetivo

Trabajar con una API ficticia `products-api` para aislar JWT, claims y decisiones de acceso.

## Contrato

Scopes:

```text
products.read
products.write
```

Pipeline conceptual:

```text
cliente → identidad → access token → gateway → products-api
```

## Checkpoint

Antes de analizar tokens, el estudiante debe distinguir:

- autenticación;
- validación técnica;
- autorización;
- regla de negocio.
