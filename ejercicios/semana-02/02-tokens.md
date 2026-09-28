# 02 · ID Token vs Access Token

Decide qué token corresponde a cada necesidad:

1. la SPA necesita conocer información de la sesión autenticada;
2. la SPA llama una API protegida;
3. la API debe comprobar que la credencial fue emitida para ella.

Para cada caso explica por qué.

## Error a detectar
> “Mando el ID Token al backend porque también es JWT.”

Explica qué tiene de incorrecto esa decisión.
