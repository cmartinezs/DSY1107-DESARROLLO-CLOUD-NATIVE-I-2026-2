# Ejemplo · Flujo Full Stack seguro

## Recorrido

```text
Usuario
→ SPA
→ IDaaS
→ Access Token
→ API Gateway
→ Spring Security
→ endpoint
```

## Diagnóstico por frontera

1. ¿Login funciona?
2. ¿Existe Access Token?
3. ¿El token está destinado a la API correcta?
4. ¿Gateway acepta issuer/audience/scope?
5. ¿Spring acepta el token?
6. ¿La regla de negocio permite la operación?

## Regla

No corregir cinco capas a la vez. Identificar el último checkpoint válido.
