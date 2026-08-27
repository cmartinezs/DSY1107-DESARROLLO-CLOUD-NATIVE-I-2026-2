# EV1 · Guía integrada de preparación

> **Importante:** esta guía **no es la evaluación E1**, no reemplaza sus instrucciones oficiales y no define una estructura de entrega. Es una práctica integrada para comprender, relacionar y ejercitar los conocimientos que se medirán en E1 mediante una aplicación técnica mínima llamada **CloudTasks**.

CloudTasks permite recorrer de extremo a extremo frontend + backend, API Gateway, CORS, IDaaS, OAuth2/OIDC, Authorization Code + PKCE, JWT, scopes/roles y despliegue cloud sin mezclar esos aprendizajes con RegistrApp.

## Convenciones visuales

- **CHECKPOINT**: estado observable que debe quedar funcionando antes de avanzar.
- **SI FALLA**: volver al último checkpoint `PASS` y aislar una capa.
- **★ OPCIONAL / Advanced Developer**: profundización técnica adicional.

## Checkpoint curricular vigente · Semana 3

La guía completa llega hasta despliegue cloud, pero **el avance esperado para la semana actual** termina en:

→ [03B · Checkpoint Semana 3 · IDaaS + JWT + seguridad en API Manager](./03b-checkpoint-semana-03-idaas-jwt-api-manager.md)

Este checkpoint integra específicamente:

```text
1.2 Implementando autenticación con Identity as a Service
1.2.5 Creando una aplicación para usuarios externos
1.2.6 Integrando Seguridad en nuestro API Manager
1.2.7 Introducción a JWT y Claims
1.2.8 Decodificando tokens JWT
```

Estado esperado al cerrar la semana:

```text
CloudTasks local
→ External tenant + user flow
→ SPA/API registrations + scopes
→ login real mediante MSAL
→ Access Token real
→ JWT decodificado
→ iss/aud/sub/exp/scp comprendidos
→ API_AUDIENCE validado desde aud real
→ JWT Authorizer configurado en API Gateway
```

No se exige todavía EC2, integración Gateway→backend, CORS cloud ni frontend cloud.

## Arquitectura final de la práctica

```mermaid
flowchart LR
    U[Usuario] --> F[Angular SPA]
    F -->|Authorization Code + PKCE| E[Microsoft Entra External ID]
    E -->|ID Token + Access Token| F
    F -->|HTTPS + Bearer| G[AWS API Gateway]
    G -->|JWT + scope| B[Spring Boot en EC2]
```

## CloudTasks mínimo

| Método | Ruta | Propósito de aprendizaje |
|---|---|---|
| GET | `/api/public/health` | comprobar backend/Gateway |
| GET | `/api/me` | observar identidad y claims |
| GET | `/api/tasks` | autorización por `tasks.read` |
| POST | `/api/tasks` | autorización por `tasks.write` |
| DELETE | `/api/tasks/{id}` | combinar scope + ownership |
| GET | `/api/admin/stats` | ★ diferenciar role y scope, si el sandbox lo permite |

## Principio: mínimo código, máxima comprensión

```text
scaffolding
→ configuración explícita
→ código mínimo indispensable
→ checkpoint
→ explicación técnica
```

Si un fragmento de código no aporta directamente al aprendizaje de esta vertical, se evita o se proporciona como starter.

## Ruta completa

### Preparación

0. [00A · Instalar herramientas](./00a-preparar-entorno.md)
1. [00B · Git/GitHub aplicado a la guía](./00b-git-github-flujo-guia.md)
2. [00C · Matriz de valores y checkpoints](./00c-matriz-valores-y-checkpoints.md)
3. [00D · Scaffolding vs código del estudiante](./00d-scaffolding-vs-codigo-estudiante.md)
4. [00 · Mapa y prerequisitos](./00-mapa-y-prerequisitos.md)

### Aplicaciones locales

5. [01A · Backend Spring Boot con IntelliJ](./01a-crear-backend-intellij.md)
6. [01B · Frontend Angular](./01b-crear-frontend-angular.md)
7. [01C · Integración local + CORS](./01-cloudtasks-local.md)

### Identidad y seguridad

8. [02 · Microsoft Entra External ID](./02-entra-external-id.md)
9. [03 · Angular + MSAL Angular + PKCE](./03-angular-msal.md)
   - [03A · Starter reproducible Angular/MSAL](./03a-starter-angular-msal.md)
   - [03B · Checkpoint Semana 3 · IDaaS + JWT + API Manager](./03b-checkpoint-semana-03-idaas-jwt-api-manager.md)
10. [04 · JWT, scopes, ownership y Spring Security](./04-jwt-y-backend.md)
   - [04A · Starter reproducible Spring Security](./04a-starter-spring-security.md)
   - [★ 04B · Roles Entra + Spring Security](./04b-opcional-roles-entra-spring.md)

### AWS

11. [05 · Backend en AWS EC2](./05-aws-backend.md)
    - [05A · EC2 paso a paso](./05a-ec2-paso-a-paso.md)
12. [06 · API Gateway + JWT Authorizer](./06-api-gateway-jwt.md)
13. [07 · CORS con URLs reales](./07-cors.md)
14. [08 · Frontend cloud + E2E](./08-frontend-cloud-e2e.md)
    - [08A · Hosting, HTTPS y mixed content](./08a-hosting-frontend-https.md)

### Verificación y cierre de la práctica

15. [09 · Pruebas negativas y troubleshooting](./09-pruebas-y-troubleshooting.md)
    - [09A · Runbook de checkpoints/estado conocido](./09a-runbook-checkpoints-estado-conocido.md)
16. [10 · Verificación integrada](./10-verificacion-integrada.md)
    - [10A · Mapa de cobertura de conocimientos](./10a-mapa-cobertura-conocimientos.md)
    - [10B · Simulación de presentación técnica](./10b-simulacion-presentacion-tecnica.md)
17. [11 · Costos y cleanup](./11-costos-y-cleanup.md)

### Referencia docente/técnica

- [Protocolo de validación y fuentes canónicas](./reference/README.md)
- validación local integral, desde la raíz del repositorio:

```bash
python3 scripts/validate_ev1.py
```

Para materializar automáticamente un **workspace técnico de referencia hasta Semana 3**:

```bash
python3 scripts/materialize_cloudtasks_week03.py
```

Ese materializador descarga scaffolding oficial de Spring Initializr, crea Angular 22, instala MSAL Angular 6 y deja una UI mínima capaz de iniciar sesión, adquirir un Access Token y mostrar solo los claims relevantes del JWT. Los artefactos generados viven localmente en `guia/ev1/backend` y `guia/ev1/frontend` y están ignorados por Git para no crear una segunda fuente de verdad.

Después se exige compilación real de ambos proyectos:

```bash
python3 scripts/validate_ev1.py --require-projects --strict
```

El materializador **no crea recursos Entra ni AWS**. El PASS de compilación no sustituye el smoke test real del checkpoint 03B.

## Estructura sugerida dentro del repositorio personal

```text
DSY1107-00XD-nombre-apellido/
└── guia/
    └── ev1/
        ├── README.md
        ├── frontend/
        ├── backend/
        └── docs/
```

## ★ Advanced Developer

Ruta opcional:

→ [WSL2 + Ubuntu + Docker](./advanced-developer/README.md)

Bifurcaciones:

```text
entorno base     → Windows/tooling habitual
★ advanced       → WSL2 + Ubuntu

backend base     → JAR
★ advanced       → Docker image

deployment base  → Java/JAR en EC2
★ advanced       → Docker Engine/container en EC2

ambas rutas       → mismo API Gateway → mismo frontend → mismos checkpoints
```

La práctica mantiene EC2 + API Gateway porque esos componentes aparecen en el material revisado; ECS queda como profundización posterior.

## Hosting frontend

S3 + CloudFront se mantiene como referencia técnica apropiada para una SPA. Si el laboratorio autoriza otra alternativa cloud que produzca la misma arquitectura observable, puede utilizarse.

## Regla de avance

Cada episodio termina en un estado observable. Solo se avanza con checkpoint `PASS`.

```text
FAIL
→ identificar último PASS
→ aislar una capa
→ corregir
→ repetir prueba positiva
→ continuar
```

## Seguridad

Nunca versionar contraseñas, client secrets, Access/Refresh Tokens, AWS keys, cookies de sesión o claves privadas.
