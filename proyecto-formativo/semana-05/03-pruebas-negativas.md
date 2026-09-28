# 03 · Pruebas negativas y cierre

Probar al menos:

| Prueba | Esperado |
|---|---|
| sin Bearer | 401 |
| token alterado/incorrecto | 401 |
| identidad válida sin permiso | 403 cuando aplique |
| token válido + permiso | 2xx |

## Evidencia

- request/response;
- configuración relevante sin secretos;
- explicación de la frontera que rechazó;
- DevLog;
- deuda explícita.

El estado resultante se congela como baseline de la Evaluación Parcial 1.
