# Etapa 3 · Matriz HTTP 401 / 403 / 2xx

## Objetivo

Relacionar el punto de falla con una respuesta HTTP coherente.

Completa primero sin mirar una solución implementada:

| Caso | Esperado | Explicación |
|---|---:|---|
| sin `Authorization` | 401 | no hay credencial |
| scheme distinto de Bearer | 401 | credencial no interpretable según contrato |
| firma/issuer/audience/exp inválido | 401 | token no aceptable para el recurso |
| token válido + `products.read` en GET | 2xx | autenticación y permiso suficientes |
| token válido sin `products.write` en POST | 403 | identidad válida, permiso insuficiente |
| token válido + write + regla permite | 2xx | operación autorizada |
| token válido + write + regla deniega | 403 | autorización de negocio insuficiente |

## Discusión

401 y 403 no son una prueba matemática del punto exacto de falla: un framework puede personalizar respuestas. En el laboratorio se usa esta semántica para construir un diagnóstico consistente.

## Ejercicio de frontera

Para cada fila marca quién podría rechazar:

```text
Gateway | Backend security | Regla de negocio
```

Una misma condición técnica puede validarse en más de una capa por defensa en profundidad; eso no significa duplicar reglas de negocio.

## Checkpoint 3

Explicar por qué un 403 presupone que la credencial ya resultó suficientemente válida como para evaluar permisos o reglas.
