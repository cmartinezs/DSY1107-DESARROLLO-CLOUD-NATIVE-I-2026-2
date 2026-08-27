# Referencia técnica de CloudTasks

## Propósito

Esta carpeta define **cómo validar la guía**, no una segunda implementación de CloudTasks.

Se evita mantener una copia paralela completa del código porque produciría dos fuentes de verdad:

```text
guía dice A
reference project dice B
→ ambigüedad
```

Los snippets completos de las etapas son la fuente canónica:

| Componente | Fuente canónica |
|---|---|
| health Spring Boot | `01a-crear-backend-intellij.md` |
| HttpClient + CORS local | `01-cloudtasks-local.md` |
| External ID / apps / scopes | `02-entra-external-id.md` |
| Angular + MSAL Angular | `03a-starter-angular-msal.md` |
| **gate curricular Semana 3: IDaaS + JWT + API Manager** | `03b-checkpoint-semana-03-idaas-jwt-api-manager.md` |
| Spring Security/JWT/scopes/ownership | `04a-starter-spring-security.md` |
| ★ roles Entra → Spring | `04b-opcional-roles-entra-spring.md` |
| JAR en EC2 | `05-aws-backend.md` + `05a-ec2-paso-a-paso.md` |
| API Gateway/JWT scopes | `06-api-gateway-jwt.md` |
| CORS cloud | `07-cors.md` |
| frontend cloud | `08-frontend-cloud-e2e.md` + `08a-hosting-frontend-https.md` |
| ★ Docker local/EC2 | `advanced-developer/` |

## Gate curricular vigente · Semana 3

El avance pedagógico actual de CloudTasks termina en `03B`, no en la arquitectura E2E final.

Debe quedar demostrable:

```text
External tenant + user flow
cloudtasks-spa + cloudtasks-api
scopes delegados
login real mediante MSAL
Access Token real
JWT decodificado
iss / aud / sub / exp / scp comprendidos
API_AUDIENCE derivado de aud real
JWT Authorizer creado en API Gateway
```

No se exige todavía:

```text
EC2
BACKEND_CLOUD_URL
rutas Gateway → backend
CORS cloud
frontend cloud
```

El Gateway/authorizer creados en Semana 3 son **recursos acumulativos**: 06 los reutiliza y agrega integración/rutas; no se crean duplicados.

## Qué significa “starter conocido”

Un starter es un conjunto de archivos completos que se aplica sobre el scaffolding generado previamente.

```text
Spring Initializr genera backend
→ estudiante mantiene package personal
→ aplica archivos completos de 04A

Angular CLI genera frontend
→ estudiante conserva providers generados válidos
→ aplica archivos completos de 03A
```

No significa copiar un repositorio terminado sin comprenderlo.

## Configuración runtime canónica

Para evitar divergencia entre local, JAR y Docker, la referencia utiliza siempre:

```text
OIDC_ISSUER
API_AUDIENCE
```

correspondientes a:

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=${OIDC_ISSUER}
cloudtasks.security.audience=${API_AUDIENCE}
```

El mismo par debe llegar al proceso Spring ejecutado mediante IntelliJ/Maven Wrapper, `java -jar`, `systemd` o Docker.

---

# Validación local profesional · un solo comando

Desde la raíz del repositorio ejecutar:

```bash
python3 scripts/validate_ev1.py
```

Este es el **entry point canónico** de validación local. Ejecuta en orden:

```text
preflight de herramientas
→ estado Git
→ validación estática de guías
→ Maven clean verify, si existe guia/ev1/backend
→ npm ci + npm run build, si existe guia/ev1/frontend
→ resumen único PASS/WARN/SKIP/FAIL
```

En el repositorio docente, `backend/` y `frontend/` pueden no existir todavía porque la guía enseña a crearlos. En ese caso aparecen como `SKIP`, no como un falso `PASS`.

Para una comprobación funcional estricta de un workspace donde ambos proyectos ya fueron materializados:

```bash
python3 scripts/validate_ev1.py --require-projects
```

Para convertir también warnings operacionales —por ejemplo working tree sucio— en fallo:

```bash
python3 scripts/validate_ev1.py --require-projects --strict
```

Para producir además un reporte machine-readable:

```bash
python3 scripts/validate_ev1.py \
  --require-projects \
  --json-report /tmp/dsy1107-ev1-validation.json
```

El script informa versión de Python, Git, Java, Node y npm; exige Java 21; informa branch/HEAD; comprueba si `origin` usa SSH; y delega la auditoría documental al validador estático especializado.

## Validador estático especializado

El comando anterior ejecuta internamente:

```bash
python3 scripts/validate_integrated_guides.py
```

Ese script comprueba:

```text
archivos canónicos presentes
enlaces Markdown relativos existentes
fences Markdown balanceados
bloques Mermaid con cabecera reconocible
no reintroducir rutas/nombres antiguos
mantener una única aclaración canónica guía ≠ evaluación
no reintroducir framing evaluativo dentro de episodios
no usar comandos Maven globales en code blocks
```

Resultado esperado del subcheck:

```text
PASS: enlaces relativos, fences, Mermaid básico y reglas semánticas.
```

---

# Matriz de validación funcional

## Backend

Desde `guia/ev1/backend/` el validador integral utiliza Maven Wrapper:

```bash
./mvnw -B clean verify
```

Pruebas mínimas posteriores con el servicio ejecutándose:

```text
GET /api/public/health sin token → 200
GET /api/tasks sin token         → 401
GET /api/me con Access Token     → 200
GET /api/tasks + read scope      → 200
POST /api/tasks + write scope    → 201
DELETE propia + write scope      → 204
DELETE ajena + write scope       → 403
```

★04B agrega, sin alterar la ruta base:

```text
GET /api/admin/stats sin ROLE_Admin → 403
GET /api/admin/stats con ROLE_Admin → 200
```

## Frontend

Desde `guia/ev1/frontend/` el validador exige instalación reproducible:

```bash
npm ci
npm run build
```

Por eso un frontend materializado debe conservar `package-lock.json`.

Pruebas mínimas posteriores en navegador:

```text
Angular compila
login redirige a External ID
after redirect existe active account
GET /api/me incluye Bearer
GET /api/tasks solicita read scope
POST/DELETE solicitan write scope
health público no necesita token
```

## API Gateway

Comprobar explícitamente la traducción conceptual:

```text
scope solicitado por MSAL: api://.../tasks.read
claim real:                 tasks.read
authorization scope AWS:    tasks.read
authority Spring:           SCOPE_tasks.read
```

No intercambiar esos cuatro valores.

## Navegador

DevTools:

```text
Console
Network
```

Comprobar:

```text
Origin correcto
preflight cuando corresponda
Authorization en rutas protegidas
status HTTP
Gateway como destino cloud
sin mixed content
```

---

# Validación en un computador limpio

La prueba más fuerte es ejecutar la guía donde no exista configuración implícita previa.

Orden hasta el gate vigente:

```text
00A herramientas
00B repo/guia/ev1
00C valores
00D responsabilidades
00 mapa
01A backend
01B frontend
01C CORS local
02 Entra
03/03A MSAL Angular
03B gate Semana 3
```

Después continúa la ruta completa:

```text
04/04A Spring Security
★04B roles si se elige
05 EC2
06 Gateway: reutiliza authorizer y agrega integración/rutas
07 CORS cloud
08 frontend cloud
09 troubleshooting
10 verificación integrada
11 cleanup
```

En cada etapa registrar el primer checkpoint que no puede reproducirse. No compensar una instrucción incompleta con conocimiento implícito del docente: **corregir la guía**.

---

# Restricción de validación automatizada

Los builds y pruebas integradas dependen de servicios externos:

```text
Maven Central
npm registry
Microsoft Entra External ID
AWS
```

No declarar un `PASS` funcional si esos comandos/servicios no se ejecutaron realmente.

`validate_ev1.py` cubre tooling, Git, documentación y builds locales materializados. No crea ni modifica recursos AWS/Entra y no reemplaza el smoke test real de autenticación/JWT/authorizer.

---

# Regla de mantenimiento

Cuando cambie una API relevante de Angular, MSAL, Spring Security, Entra o AWS:

1. actualizar primero el starter canónico;
2. ejecutar `python3 scripts/validate_ev1.py`;
3. volver a ejecutar checkpoints funcionales afectados;
4. actualizar explicaciones dependientes;
5. no crear un segundo snippet alternativo sin razón pedagógica explícita.
