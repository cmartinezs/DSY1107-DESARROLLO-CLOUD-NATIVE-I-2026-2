# Semana 3 · Seguridad de APIs, JWT y usuarios externos

**Periodo:** 24 al 29 de agosto de 2026  
**Asignatura:** DSY1107 Desarrollo Cloud Native I

← [Volver al índice](../README.md)

## Propósito

Evolucionar desde los fundamentos OAuth2/OIDC e identidad local hacia la **protección verificable de una API**, interpretación de JWT/claims y posterior mapeo a capacidades de API Manager e Identity as a Service.

La estrategia se mantiene:

```text
concepto
→ implementación local/neutral
→ evidencia HTTP
→ comprensión del token
→ mapeo a cloud
```

No se enseña configuración cloud como una secuencia de clics sin modelo mental previo.

## Contenidos de la semana

- aplicación orientada a usuarios externos / CIAM;
- seguridad aplicada al API Manager/gateway;
- JWT: estructura, claims y propósito;
- access token y su uso sobre APIs;
- decodificación vs validación/verificación;
- `issuer`, `audience`, expiración y scopes;
- decisiones 401 vs 403;
- responsabilidades de identidad, gateway y backend.

## Material

1. [JWT y claims](./01-jwt-claims.md)
2. [Seguridad de API y gateway](./02-seguridad-api.md)
3. [Laboratorio JWT forense](./03-laboratorio-jwt-forense.md)
4. [DSY1107-002D · plan y checkpoint](./DSY1107-002D.md)
5. [DSY1107-003D · plan y checkpoint](./DSY1107-003D.md)

## ReservApp

ReservApp continúa como dominio formativo transversal. El checkpoint esperado, **si los prerrequisitos reales de cada sección lo permiten**, evoluciona hacia:

```text
usuario externo
→ IdP/CIAM
→ Authorization Code + PKCE
→ access token JWT
→ gateway
→ validación técnica
→ autorización por scope/claim
→ reservapp-api
```

## Resultado técnico esperado

El estudiante debe poder mirar un token y distinguir header/payload/signature; interpretar claims comunes; explicar por qué decodificar no equivale a confiar; relacionar `iss`, `aud`, `exp` y scopes con decisiones de acceso; provocar y explicar 401/403; y ubicar responsabilidades entre proveedor de identidad, gateway y API.

## Regla de sección

002D y 003D **no se sincronizan artificialmente**. Cada documento de sección declara desde qué checkpoint demostrable comienza. El contenido común representa el horizonte de Semana 3; el cierre real se registra por sección.

## Evidencia mínima

Diagrama actualizado de ReservApp, requests/responses reproducibles, ejemplos de 401 y 403 cuando se alcance autorización, token de laboratorio sin secretos reales, interpretación de claims y defensa técnica breve.

## Seguridad

Nunca versionar client secrets, contraseñas, tokens reales reutilizables ni credenciales cloud. Los JWT usados como evidencia deben ser locales, sintéticos, expirados o sanitizados.

## Cierre obligatorio

Por sección registrar: último checkpoint demostrado, contenido efectivamente alcanzado, evidencia, bloqueos, deuda curricular y punto exacto de arranque siguiente.