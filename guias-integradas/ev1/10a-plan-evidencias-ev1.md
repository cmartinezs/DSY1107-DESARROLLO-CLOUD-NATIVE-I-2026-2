# 10A · Plan de evidencias EV1-01 a EV1-08

**REQUERIDO EV1** · Cada evidencia existe porque cubre un indicador institucional. No entregar capturas decorativas.

| ID | Indicador | Peso | Evidencia principal | Prueba viva |
|---|---|---:|---|---|
| EV1-01 | rutas API Manager | 13% | tabla/rutas + integración | Angular llama ruta Gateway y recibe JSON |
| EV1-02 | CORS API Manager | 7% | origins/methods/headers | preflight válido + origin inválido bloqueado |
| EV1-03 | tenant IDaaS | 10% | External tenant | usuario/flujo existente |
| EV1-04 | aplicación tenant | 10% | SPA/API registrations | IDs y redirect URI coherentes |
| EV1-05 | user flow/tokens | 10% | user flow + sesión | sign-up/sign-in y Access Token |
| EV1-06 | Authorization Code + PKCE | 15% | SPA config/MSAL | explicar redirect/code/PKCE sin secret |
| EV1-07 | JWT por rutas | 20% | JWT Authorizer | sin token/alterado rechazado; válido aceptado |
| EV1-08 | backend + JSON | 15% | EC2 + backend observable | endpoint vía Gateway retorna JSON esperado |

## Estructura recomendada

```text
docs/evidencias/
├── README.md
├── EV1-01-api-manager.md
├── EV1-02-cors.md
├── EV1-03-tenant.md
├── EV1-04-apps.md
├── EV1-05-user-flow.md
├── EV1-06-pkce.md
├── EV1-07-jwt.md
└── EV1-08-backend-json.md
```

## Plantilla por evidencia

```markdown
# EV1-XX · <indicador>

## Qué demuestra
...

## Configuración relevante
...

## Prueba
Request:
Resultado esperado:
Resultado obtenido:

## Evidencia visual
<captura sanitizada>

## Explicación técnica
...

## Prueba negativa relacionada
...
```

## Evidencia visual segura

Ocultar/evitar:

```text
Access Token completo
refresh token
cookies
passwords
AWS keys
client secrets
private keys
```

Puede mostrarse sanitizado:

```text
client ID
tenant ID
issuer
audience
scopes
URLs cloud
HTTP status
```

## Checkpoint de cobertura

Antes de la defensa:

```text
EV1-01 PASS
EV1-02 PASS
EV1-03 PASS
EV1-04 PASS
EV1-05 PASS
EV1-06 PASS
EV1-07 PASS
EV1-08 PASS
```

Un “PASS” significa que existe configuración + prueba observable + explicación, no solo una captura.

## ★ Advanced

Docker/WSL puede documentarse como evidencia adicional de decisión técnica, pero **no sustituye EV1-01…08** ni cambia los porcentajes institucionales.
