# Etapa 6 · Pruebas, evidencia y cierre

## Matriz mínima

| Caso | Esperado |
|---|---|
| broker detenido al iniciar app | error diagnosticable |
| producer publica evento válido | mensaje llega a queue correcta |
| routing key desconocida | no llega a queues sin binding |
| consumer detenido | mensaje queda pendiente |
| consumer vuelve | mensaje se procesa |

## Evidencia

- `docker compose ps`;
- topología visible;
- logs producer/consumer;
- capturas solo si aportan información no reproducible por texto;
- diagrama final;
- explicación de responsabilidades.

## Cierre

El lab termina cuando el estudiante puede explicar el recorrido sin depender de memorizar nombres de clases.
