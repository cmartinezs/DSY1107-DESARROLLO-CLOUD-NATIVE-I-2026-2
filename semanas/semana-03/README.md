# Semana 3 · Seguridad de APIs, JWT y usuarios externos

**Periodo:** 24 al 29 de agosto de 2026  
**Asignatura:** DSY1107 Desarrollo Cloud Native I

← [Volver al índice](../README.md)

## Regla de trabajo

`semanas/semana-03/` contiene contenido curricular y planificación. Los ejemplos reutilizables viven en `examples/`, los laboratorios canónicos en `labs/` y RegistrApp en `proyecto-formativo/`.

```text
CONTENIDO
concepto → explicación → ejemplo independiente → laboratorio independiente → evidencia

PROYECTO FORMATIVO
contenido comprendido → transferencia a RegistrApp → incremento → checkpoint
```

> RegistrApp no se utiliza para enseñar por primera vez el contenido de Semana 3.

## Cobertura curricular oficial

→ [Mapeo 1.2.5–1.2.8](./00-mapeo-curricular.md)

La semana cubre:

- **1.2.5** Creando una aplicación para usuarios externos;
- **1.2.6** Integrando Seguridad en nuestro API Manager;
- **1.2.7** Introducción a JWT y Claims;
- **1.2.8** Decodificando tokens JWT.

## Propósito

Evolucionar desde OAuth2/OIDC e identidad hacia la protección verificable de una API, interpretación de JWT/claims y mapeo de responsabilidades entre proveedor de identidad, API Manager/gateway y backend.

```text
OAuth2/OIDC
→ access token
→ JWT y claims
→ decode vs verify
→ issuer / audience / exp
→ scopes
→ 401 / 403
→ gateway / backend
→ CIAM / usuarios externos
→ mapeo cloud
```

## Material de contenido

### 1. JWT y claims

- [Base obligatoria](./01-jwt-claims.md)
- [Profundización opcional](./01-jwt-claims/README.md)
  - [Estructura, codificación y firma](./01-jwt-claims/01-estructura-codificacion-y-firma.md)
  - [Validación contextual de claims](./01-jwt-claims/02-validacion-contextual-de-claims.md)
  - [Claves públicas, `kid` y JWKS](./01-jwt-claims/03-claves-kid-y-jwks.md)
  - [Errores frecuentes](./01-jwt-claims/04-errores-frecuentes.md)

### 2. Seguridad de API, API Manager y usuarios externos

- [Base obligatoria](./02-seguridad-api.md)
- [Profundización opcional](./02-seguridad-api/README.md)
  - [Gateway vs backend](./02-seguridad-api/01-gateway-vs-backend.md)
  - [Pipeline 401/403](./02-seguridad-api/02-pipeline-401-403.md)
  - [Scopes, roles y claims](./02-seguridad-api/03-scopes-roles-y-claims.md)
  - [Usuarios externos, CIAM y API protegida](./02-seguridad-api/04-ciam-y-api-protegida.md)

### 3. Ejemplo independiente

→ [Products API · ejemplo Semana 3](../../examples/semana-03/README.md)

### 4. Laboratorio canónico

→ [JWT forense](../../labs/jwt-forense/README.md)

El archivo [`03-laboratorio-jwt-forense.md`](./03-laboratorio-jwt-forense.md) se conserva solo como punto de entrada desde la semana.

## Transferencia al proyecto formativo

Cuando exista comprensión suficiente:

→ [RegistrApp · Checkpoint Semana 3](../../proyecto-formativo/semana-03/README.md)

## Planificación por sección

- [DSY1107-002D](./DSY1107-002D.md)
- [DSY1107-003D](./DSY1107-003D.md)

002D y 003D mantienen avance independiente y parten desde el último checkpoint demostrable.

## Resultado técnico esperado

El estudiante debe poder:

- distinguir header, payload y signature;
- interpretar claims comunes;
- explicar por qué decodificar no equivale a verificar;
- relacionar `iss`, `aud`, `exp` y scopes con decisiones de acceso;
- explicar 401 vs 403 según el punto de falla;
- ubicar responsabilidades entre identidad, gateway/API Manager y backend;
- explicar el rol de CIAM y usuarios externos;
- mapear los conceptos a una solución cloud sin depender de un proveedor para comprenderlos.

## Evidencia mínima

- análisis de JWT sintéticos;
- tabla de aceptación/rechazo;
- requests/responses reproducibles cuando exista starter;
- explicación 401/403;
- diagrama técnico independiente;
- defensa de decisiones;
- DevLog.

## Seguridad

Nunca versionar client secrets, contraseñas, tokens reales reutilizables ni credenciales cloud.

## Cierre obligatorio

Por sección registrar contenido alcanzado, evidencia, bloqueos, deuda curricular, avance real de RegistrApp si ocurrió y punto exacto de arranque de la siguiente clase.
