# Preflight paso a paso

Cuando una solicitud cross-origin no califica como simple, el navegador puede realizar primero una petición `OPTIONS`.

## Flujo

```text
1. frontend quiere hacer POST cross-origin
2. navegador detecta que debe consultar permisos
3. envía OPTIONS
4. servidor/gateway responde con política CORS
5. navegador evalúa la respuesta
6. si está permitido, envía el POST real
```

Ejemplo conceptual:

```http
OPTIONS /reservas
Origin: http://localhost:5173
Access-Control-Request-Method: POST
Access-Control-Request-Headers: Content-Type, Authorization
```

Respuesta:

```http
Access-Control-Allow-Origin: http://localhost:5173
Access-Control-Allow-Methods: GET,POST,OPTIONS
Access-Control-Allow-Headers: Content-Type,Authorization
```

## Idea clave

El preflight **no es la operación de negocio**. Es una consulta previa del navegador para determinar si la operación real puede ejecutarse desde ese origen.

Por eso un `OPTIONS` fallido puede impedir que el `POST` llegue siquiera al backend.