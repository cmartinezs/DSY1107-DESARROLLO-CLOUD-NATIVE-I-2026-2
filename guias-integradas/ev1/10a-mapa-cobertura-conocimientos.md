# 10A · Mapa de cobertura de conocimientos

## Objetivo

Comprobar que la práctica integrada recorre todos los bloques conceptuales relevantes y que cada uno puede ejecutarse y explicarse de forma independiente dentro del flujo completo.

| ID de cobertura | Conocimiento | Práctica dentro de CloudTasks |
|---|---|---|
| COV-01 | rutas API Manager | Angular llama una ruta del Gateway y recibe JSON |
| COV-02 | CORS API Manager | preflight válido + origin inválido bloqueado |
| COV-03 | tenant IDaaS | External tenant y user flow operativos |
| COV-04 | aplicaciones en tenant | SPA/API registrations coherentes |
| COV-05 | user flow y tokens | sign-up/sign-in + Access Token |
| COV-06 | Authorization Code + PKCE | MSAL ejecuta el flujo sin client secret |
| COV-07 | JWT por rutas | token ausente/alterado rechazado; válido aceptado |
| COV-08 | backend + JSON | endpoint vía Gateway alcanza Spring Boot y retorna JSON |

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
└── COV-08-backend-json.md
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

## Checkpoint de cobertura

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

## ★ Advanced Developer

WSL/Docker agrega profundidad técnica, pero no reemplaza ninguno de estos ocho bloques de conocimiento.
