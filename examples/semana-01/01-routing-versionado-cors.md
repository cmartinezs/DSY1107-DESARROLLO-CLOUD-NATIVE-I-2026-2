# Ejemplo · Routing, versionado y CORS

## Objetivo

Observar tres responsabilidades típicas de una capa API Manager/Gateway sin depender de RegistrApp.

## Escenario

Un cliente consume dos versiones de catálogo:

```text
GET /api/v1/products
GET /api/v2/products
```

El gateway decide a qué backend enviar cada request.

## Routing

```text
/api/v1/** → backend-v1
/api/v2/** → backend-v2
```

## Versionado

La versión de URL expresa un contrato público, no la versión de despliegue del artefacto.

## CORS

Si una SPA en `http://localhost:4200` consume el gateway en otro origen, el navegador aplica política CORS.

Preguntas:

1. ¿Quién decide el destino del request?
2. ¿Qué cambia si se agrega `/v3`?
3. ¿Por qué Postman puede funcionar cuando el navegador falla por CORS?
