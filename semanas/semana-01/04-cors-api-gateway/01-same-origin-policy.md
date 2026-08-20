# Same-Origin Policy y origen

El navegador considera un origen como la combinación:

```text
scheme + host + port
```

Por eso estos orígenes son distintos:

```text
http://localhost:3000
http://localhost:5173
```

aunque ambos usen `localhost`.

## Same-Origin Policy

La Same-Origin Policy limita determinadas interacciones entre documentos y recursos de orígenes distintos. Es una protección implementada principalmente por navegadores.

CORS no elimina esta política: crea un mecanismo controlado para que el servidor declare qué accesos cross-origin permite.

## Consecuencia importante

Una API puede responder perfectamente a `curl` o Postman y, al mismo tiempo, ser bloqueada por el navegador.

```text
Postman → API → 200 OK
Browser → API → respuesta recibida, pero JS no puede usarla por política CORS
```

Esto explica por qué “funciona en Postman” no demuestra que una integración frontend esté correctamente configurada.