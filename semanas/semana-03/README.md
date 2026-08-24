# Semana 3 · Seguridad de APIs, JWT y usuarios externos

**Periodo:** 24 al 29 de agosto de 2026  
**Asignatura:** DSY1107 Desarrollo Cloud Native I

← [Volver al índice](../README.md)

## Regla de trabajo de Semana 3

`semanas/semana-03/` contiene **solo el contenido curricular, práctica y planificación de esta semana**.

RegistrApp no vive aquí. Es una vertical independiente en [`proyecto-formativo/`](../../proyecto-formativo/).

```text
SEMANAS
concepto
→ explicación
→ ejemplo mínimo
→ práctica/lab independiente
→ evidencia de comprensión

PROYECTO FORMATIVO
contenido ya comprendido
→ transferencia a RegistrApp
→ decisión
→ incremento
→ checkpoint
```

> **RegistrApp no es la ejemplificación del contenido ni un archivo de Semana 3.** La semana únicamente puede enlazar al checkpoint transversal correspondiente.

## Propósito

Evolucionar desde los fundamentos OAuth2/OIDC e identidad hacia la **protección verificable de una API**, interpretación de JWT/claims y posterior mapeo a capacidades de API Manager e Identity as a Service.

La progresión conceptual es:

```text
OAuth2/OIDC
→ access token
→ JWT y claims
→ verificación
→ 401/403
→ política de gateway/API
→ mapeo a cloud
```

## Contenidos de la semana

- JWT: estructura, claims y propósito;
- access token y su uso sobre APIs;
- decodificación vs validación/verificación;
- `issuer`, `audience`, expiración y scopes;
- decisiones 401 vs 403;
- responsabilidades de identidad, gateway y backend;
- seguridad aplicada al API Manager/gateway;
- aplicación orientada a usuarios externos / CIAM;
- mapeo de los conceptos al proveedor cloud disponible.

## Material de contenido

### 1. JWT y claims

- **Base obligatoria:** [JWT y claims](./01-jwt-claims.md)
- **Profundización opcional:** [Estructura, firma, claims, `kid` y JWKS](./01-jwt-claims/README.md)

Profundización:

1. [Estructura, codificación y firma](./01-jwt-claims/01-estructura-codificacion-y-firma.md)
2. [Validación contextual de claims](./01-jwt-claims/02-validacion-contextual-de-claims.md)
3. [Claves públicas, `kid` y JWKS](./01-jwt-claims/03-claves-kid-y-jwks.md)
4. [Errores frecuentes al trabajar con JWT](./01-jwt-claims/04-errores-frecuentes.md)

### 2. Seguridad de API y gateway

- **Base obligatoria:** [Seguridad de API y gateway](./02-seguridad-api.md)
- **Profundización opcional:** [Gateway/backend, 401/403, permisos y CIAM](./02-seguridad-api/README.md)

Profundización:

1. [Gateway vs backend: frontera de responsabilidad](./02-seguridad-api/01-gateway-vs-backend.md)
2. [Pipeline de acceso y 401 vs 403](./02-seguridad-api/02-pipeline-401-403.md)
3. [Scopes, roles y claims en autorización](./02-seguridad-api/03-scopes-roles-y-claims.md)
4. [Usuarios externos, CIAM y API protegida](./02-seguridad-api/04-ciam-y-api-protegida.md)

### 3. Aplicación práctica

- [Laboratorio JWT forense](./03-laboratorio-jwt-forense.md)

El laboratorio no tiene carpeta de profundización propia porque es una instancia de aplicación, no un contenido conceptual base.

## Transferencia al proyecto formativo

Cuando exista comprensión suficiente, el estudiante puede continuar en la vertical independiente:

→ [RegistrApp · Checkpoint Semana 3](../../proyecto-formativo/semana-03/README.md)

Ese checkpoint puede reforzarse después de cada clase. **No pertenece físicamente a esta carpeta semanal.**

## Planificación por sección

- [DSY1107-002D · plan y checkpoint](./DSY1107-002D.md)
- [DSY1107-003D · plan y checkpoint](./DSY1107-003D.md)

002D y 003D no se sincronizan artificialmente. Cada documento declara desde qué evidencia real comienza y hasta dónde llegó.

## Resultado técnico esperado del contenido

El estudiante debe poder:

- distinguir header, payload y signature;
- interpretar claims comunes;
- explicar por qué decodificar no equivale a confiar;
- relacionar `iss`, `aud`, `exp` y scopes con decisiones de acceso;
- provocar y explicar 401/403;
- ubicar responsabilidades entre proveedor de identidad, gateway y API;
- mapear esos conceptos a una solución cloud.

## Evidencia mínima del contenido

- análisis de JWT sintéticos;
- tabla de casos de aceptación/rechazo;
- requests/responses reproducibles cuando exista starter local;
- explicación de 401 vs 403;
- diagrama técnico de un caso mínimo independiente;
- defensa breve de las decisiones.

La evidencia de RegistrApp se registra en `proyecto-formativo/`, no aquí.

## Seguridad

Nunca versionar client secrets, contraseñas, tokens reales reutilizables ni credenciales cloud. Los JWT usados como evidencia deben ser locales, sintéticos, expirados o sanitizados.

## Cierre obligatorio

Por sección registrar:

- contenido efectivamente alcanzado;
- evidencia;
- bloqueos;
- deuda curricular;
- avance real de RegistrApp, si se trabajó;
- punto exacto de arranque de la siguiente clase.
