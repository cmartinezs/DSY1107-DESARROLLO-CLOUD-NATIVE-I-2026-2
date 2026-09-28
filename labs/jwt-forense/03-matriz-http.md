# Etapa 3 · Matriz HTTP 401 / 403 / 2xx

Completa:

| Caso | Esperado | Razón |
|---|---:|---|
| sin Authorization | 401 | |
| bearer mal formado | 401 | |
| token inválido | 401 | |
| token válido + read en GET | 2xx | |
| token válido sin write en POST | 403 | |
| token válido + write | continúa | |

## Checkpoint

Explica por qué un 403 presupone que existe suficiente contexto de identidad/autorización para negar una operación.
