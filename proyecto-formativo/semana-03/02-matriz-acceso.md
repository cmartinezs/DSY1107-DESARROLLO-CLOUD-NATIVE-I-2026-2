# 02 · Matriz de acceso

Construir casos antes de programar reglas.

| Caso | Credencial | Permiso | Regla negocio | Resultado |
|---|---|---|---|---|
| sin token | ausente | — | — | 401 |
| token inválido | inválida | — | — | 401 |
| token válido sin scope | válida | insuficiente | — | 403 |
| token válido + scope | válida | suficiente | permite | 2xx |
| token válido + scope | válida | suficiente | deniega | 403 |

## Checkpoint

Cada fila debe poder relacionarse con la frontera que toma la decisión.
