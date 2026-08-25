# 10A · Mapa de cobertura de conocimientos

## Objetivo

Comprobar que la práctica integrada recorre todos los bloques conceptuales relevantes y que cada uno puede ejecutarse y explicarse de forma independiente dentro del flujo completo.

| ID | Conocimiento | Práctica dentro de CloudTasks |
|---|---|---|
| COV-01 | rutas API Manager | Angular llama una ruta del Gateway y recibe JSON |
| COV-02 | CORS API Manager | preflight válido + origin inválido bloqueado |
| COV-03 | tenant IDaaS | External tenant y user flow operativos |
| COV-04 | aplicaciones en tenant | SPA/API registrations coherentes |
| COV-05 | user flow y tokens | sign-up/sign-in + ID/Access Token diferenciados |
| COV-06 | Authorization Code + PKCE | MSAL Angular ejecuta el flujo sin client secret |
| COV-07 | JWT por rutas | token ausente/alterado/audience incorrecta rechazado; válido aceptado |
| COV-08 | backend + JSON | endpoint vía Gateway alcanza Spring Boot y retorna JSON |
| COV-X1 | scopes vs ownership | `tasks.write` no basta para borrar recurso ajeno |
| COV-X2 | roles vs scopes | ★ `Admin` se mapea explícitamente a `ROLE_Admin` si el sandbox lo permite |
| COV-X3 | responsabilidades Gateway/backend | ambos validan/aplican controles complementarios |

Los bloques `COV-X*` profundizan conceptos transversales sin reemplazar los ocho checkpoints principales.

## Registro de aprendizaje sugerido

```text
docs/cobertura/
├── README.md
├── COV-01-api-manager.md
├── COV-02-cors.md
├── COV-03-tenant.md
├── COV-04-apps.md
├── COV-05-user-flow.md
├── COV-06-pkce.md
├── COV-07-jwt.md
├── COV-08-backend-json.md
└── COV-X-autorization.md   # opcional
```

Estos archivos son opcionales como bitácora de aprendizaje.

## Plantilla de autoverificación

```markdown
# COV-XX · <conocimiento>

## Qué concepto estoy comprobando
...

## Configuración que interviene
...

## Prueba
Request:
Resultado esperado:
Resultado obtenido:

## Qué aprendí del resultado
...

## Error o prueba negativa relacionada
...
```

## Checkpoint principal

```text
COV-01 PASS
COV-02 PASS
COV-03 PASS
COV-04 PASS
COV-05 PASS
COV-06 PASS
COV-07 PASS
COV-08 PASS
```

Un `PASS` significa que el estudiante puede ejecutar la prueba y explicar por qué el resultado ocurre.

## Checkpoints de profundización

```text
COV-X1 ownership PASS
COV-X2 roles PASS | OMITIDO POR SANDBOX
COV-X3 Gateway vs backend PASS
```

## Trazabilidad hacia episodios

| Cobertura | Episodios principales |
|---|---|
| COV-01 | 06, 08 |
| COV-02 | 01C, 07, 08 |
| COV-03 | 02 |
| COV-04 | 02 |
| COV-05 | 02, 03, 03A |
| COV-06 | 03, 03A |
| COV-07 | 04, 04A, 06 |
| COV-08 | 05, 06, 08 |
| COV-X1 | 04, 04A |
| COV-X2 | ★ 04B |
| COV-X3 | 04, 06, 09 |

## ★ Advanced Developer

WSL/Docker agrega profundidad de entorno y empaquetado, pero no sustituye los bloques de identidad, autorización, API Gateway o CORS.
