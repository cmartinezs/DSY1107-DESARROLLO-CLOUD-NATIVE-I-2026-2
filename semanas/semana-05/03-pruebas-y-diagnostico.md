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
