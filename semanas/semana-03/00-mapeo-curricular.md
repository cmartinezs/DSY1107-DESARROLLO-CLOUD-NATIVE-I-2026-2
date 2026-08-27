# Semana 3 · Mapeo curricular oficial

**Periodo:** 24 al 29 de agosto de 2026

Este documento verifica que el material publicado cubra explícitamente las actividades institucionales de Semana 3.

| Actividad oficial | Cobertura en el repo | Evidencia |
|---|---|---|
| 1.2.5 Creando una aplicación para usuarios externos | CIAM, ciclo de vida de identidad, cliente registrado, redirect URI y relación identidad ↔ usuario de negocio | [`02-seguridad-api/04-ciam-y-api-protegida.md`](./02-seguridad-api/04-ciam-y-api-protegida.md) |
| 1.2.6 Integrando Seguridad en nuestro API Manager | frontera gateway/backend, validación de token, issuer, audience, expiración, scopes y políticas por ruta | [`02-seguridad-api.md`](./02-seguridad-api.md) y profundización [`02-seguridad-api/`](./02-seguridad-api/) |
| 1.2.7 Introducción a JWT y Claims | estructura JWT, claims, firma, validación contextual, `kid` y JWKS | [`01-jwt-claims.md`](./01-jwt-claims.md) y profundización [`01-jwt-claims/`](./01-jwt-claims/) |
| 1.2.8 Decodificando tokens JWT | lectura de header/payload, diferencia decode vs verify y laboratorio forense | [`01-jwt-claims.md`](./01-jwt-claims.md) y [`../../labs/jwt-forense/`](../../labs/jwt-forense/) |

## Criterio de validación

Semana 3 se considera curricularmente completa cuando:

1. las cuatro actividades oficiales tienen material identificable;
2. existe ejemplo independiente del proyecto transversal;
3. existe laboratorio canónico en `labs/`;
4. RegistrApp se mantiene en `proyecto-formativo/` y solo recibe transferencia;
5. 002D y 003D conservan avance independiente;
6. existe estado machine-readable en `data/weekly/semana-03.yml`;
7. no existen enlaces internos rotos conocidos en la ruta principal de Semana 3.
