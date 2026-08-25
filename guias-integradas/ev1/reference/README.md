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
| Angular + MSAL Angular | `03a-starter-angular-msal.md` |
| Spring Security/JWT/scopes/ownership | `04a-starter-spring-security.md` |
| ★ roles Entra → Spring | `04b-opcional-roles-entra-spring.md` |
| JAR en EC2 | `05-aws-backend.md` + `05a-ec2-paso-a-paso.md` |
| API Gateway/JWT scopes | `06-api-gateway-jwt.md` |
| CORS cloud | `07-cors.md` |
| frontend cloud | `08-frontend-cloud-e2e.md` + `08a-hosting-frontend-https.md` |
| ★ Docker local/EC2 | `advanced-developer/` |

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

# Validación estática local

Desde la raíz del repositorio docente ejecutar:

```bash
python scripts/validate_integrated_guides.py
```

El script no usa Internet ni GitHub Actions. Comprueba:

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

Resultado esperado:

```text
PASS: enlaces relativos, fences, Mermaid básico y reglas semánticas.
```

Este control debe ejecutarse después de renombrar/mover episodios.

---

# Matriz de validación funcional

## Backend

Desde `guia/ev1/backend/`:

PowerShell:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

Git Bash/Linux/macOS:

```bash
./mvnw clean test
./mvnw spring-boot:run
```

Pruebas mínimas:

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

Desde `guia/ev1/frontend/`:

```bash
npm ci
npm start
```

Antes de publicar:

```bash
ng build
```

Pruebas mínimas:

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

Orden:

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
04/04A Spring Security
★04B roles si se elige
05 EC2
06 Gateway
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

La validación estática puede ejecutarse offline **si el repositorio ya está disponible localmente**. Un entorno sin workspace local y sin conectividad para clonar GitHub no puede fingir la ejecución del script; debe registrar esa limitación y ejecutar la validación cuando exista una copia local.

---

# Regla de mantenimiento

Cuando cambie una API relevante de Angular, MSAL, Spring Security, Entra o AWS:

1. actualizar primero el starter canónico;
2. ejecutar el validador estático;
3. volver a ejecutar checkpoints funcionales afectados;
4. actualizar explicaciones dependientes;
5. no crear un segundo snippet alternativo sin razón pedagógica explícita.
