# 03 · Pruebas 401 / 403 / 2xx y diagnóstico

## Matriz mínima

| Caso | Esperado | Diagnóstico inicial |
|---|---:|---|
| sin token | 401 | credencial ausente |
| token expirado/incorrecto | 401 | validación técnica |
| token válido sin permiso | 403 | autorización insuficiente |
| token válido + permiso | 2xx | flujo continúa |

## Orden de diagnóstico

1. ¿login/IDaaS funciona?
2. ¿existe Access Token?
3. ¿`iss`/`aud` corresponden?
4. ¿Gateway acepta?
5. ¿backend acepta?
6. ¿la regla de aplicación permite?

## Regla

No corregir cinco capas a la vez. Volver al último checkpoint verificable.


## Por qué 401 y 403 no son “lo mismo”

401 indica que no existe una autenticación aceptable para continuar. 403 indica que la identidad/credencial fue aceptada, pero no tiene autoridad suficiente para la operación.

Esta diferencia es una herramienta de diagnóstico.

## Casos que conviene probar

Además de la matriz mínima:

- token con audience de otro recurso;
- token válido sin el scope requerido;
- token correcto enviado sin prefijo Bearer;
- endpoint público;
- endpoint protegido;
- request que llega al Gateway pero falla en backend.

La idea es observar cómo cambia la frontera de rechazo.

## Método del último checkpoint verificable

Cuando el flujo falla, volver al último punto conocido como correcto y avanzar una sola frontera.

Ejemplo:

1. Login funciona.
2. Access Token existe.
3. Claims son correctos.
4. Gateway devuelve 401.

En ese escenario no conviene tocar Spring Security todavía. Primero se debe resolver por qué el Gateway rechaza.

## Evidencia de diagnóstico

Una evidencia útil registra:

- request;
- status;
- frontera que respondió;
- mensaje relevante;
- hipótesis;
- cambio aplicado;
- resultado.

Eso permite reconstruir el razonamiento, no solo mostrar una captura final.

## Árbol mental simplificado

- ¿Hay token? Si no, 401.
- ¿Es válido para este recurso? Si no, 401.
- ¿Tiene permiso suficiente? Si no, 403.
- Si cumple autenticación y autorización, el flujo puede continuar a 2xx o a reglas de negocio.

## Anti-patrones

- cambiar frontend, Gateway y backend a la vez;
- desactivar seguridad como primer diagnóstico;
- probar solo el happy path;
- asumir que cualquier 401 viene del backend;
- usar tokens distintos sin controlar sus claims.

## Preguntas de salida

1. ¿Por qué audience incorrecta es un problema de autenticación contextual?
2. ¿Por qué scope insuficiente termina normalmente en 403?
3. ¿Qué revisarías primero si login funciona pero la API responde 401?
4. ¿Cómo distinguirías un rechazo del Gateway de uno del backend?

## Profundización

→ [Contenido extendido · Pruebas y diagnóstico](./03-pruebas-y-diagnostico/)
