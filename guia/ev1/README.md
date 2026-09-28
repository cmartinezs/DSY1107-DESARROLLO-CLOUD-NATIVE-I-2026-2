# CloudTasks · workspace local de validación

Esta carpeta es un **workspace técnico local** para validar que la guía integrada puede materializarse y compilarse. No es una segunda fuente canónica de contenido docente ni reemplaza `guias-integradas/ev1/`.

Los proyectos se generan localmente mediante:

```bash
python3 scripts/materialize_cloudtasks_week03.py
```

Resultado esperado:

```text
guia/ev1/
├── README.md
├── backend/
└── frontend/
```

`backend/` y `frontend/` están ignorados por Git porque son artefactos reproducibles de validación. La fuente canónica sigue siendo la guía y sus starters.

## Alcance materializado

El materializador reproduce el checkpoint curricular vigente hasta Semana 3:

```text
backend Spring Boot mínimo + /api/public/health
frontend Angular
MSAL Angular
Authorization Code + PKCE gestionado por MSAL
login/logout
adquisición de Access Token
lectura didáctica de claims JWT
iss / aud / sub / exp / scp
```

No crea automáticamente tenant, aplicaciones Entra ni API Gateway. Esos recursos requieren un sandbox real y deben configurarse siguiendo `guias-integradas/ev1/02-entra-external-id.md` y `03b-checkpoint-semana-03-idaas-jwt-api-manager.md`.

## Validación

Después de materializar:

```bash
python3 scripts/validate_ev1.py --require-projects --strict
```

Ese gate exige que Maven y Angular compilen realmente. Un PASS de build no equivale todavía a PASS de autenticación/cloud; el smoke test real de Entra y API Gateway se registra aparte.
