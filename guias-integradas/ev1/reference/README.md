# Referencia técnica de CloudTasks

## Propósito

Esta carpeta define **cómo validar la guía**, no una segunda implementación de CloudTasks.

Se evita mantener una copia paralela completa del código porque produciría dos fuentes de verdad que podrían divergir:

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
| Spring Security/JWT | `04a-starter-spring-security.md` |
| JAR en EC2 | `05-aws-backend.md` + `05a-ec2-paso-a-paso.md` |
| ★ Docker local/EC2 | `advanced-developer/` |

## Qué significa “starter conocido”

Un starter es un conjunto de archivos completos que puede aplicarse sobre el scaffolding generado en etapas anteriores.

```text
Spring Initializr genera backend
→ estudiante mantiene package personal
→ aplica archivos completos de 04A

Angular CLI genera frontend
→ estudiante conserva providers generados válidos
→ aplica archivos completos de 03A
```

No significa copiar un repositorio terminado sin entenderlo.

## Matriz de validación técnica

### Backend

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

### Frontend

Desde `guia/ev1/frontend/`:

```bash
npm ci
npm start
```

Y antes de publicar:

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

### Navegador

La prueba integrada requiere DevTools:

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

## Validación en un computador limpio

La prueba más fuerte de la guía es ejecutarla desde un entorno donde no exista configuración implícita previa.

Orden:

```text
00A herramientas
00B repo/guia/ev1
00C valores
00D responsabilidades
01A backend
01B frontend
01C CORS local
02 Entra
03/03A MSAL Angular
04/04A Spring Security
05 EC2
06 Gateway
07 CORS cloud
08 frontend cloud
09 troubleshooting
10 verificación integrada
11 cleanup
```

En cada etapa registrar el primer checkpoint que no puede reproducirse. No compensar una instrucción incompleta con conocimiento implícito del docente; corregir la guía.

## Restricción de validación automatizada

La validación de estos snippets depende de dependencias externas (Maven Central, npm, Microsoft Entra y AWS). El repositorio no debe fingir un `PASS` cuando esos servicios no se ejecutaron realmente.

La documentación puede someterse a validaciones estáticas en cualquier momento; la validación funcional exige ejecutar los comandos anteriores en un entorno con red y credenciales/sandbox apropiados.

## Regla de mantenimiento

Cuando cambie una API relevante de Angular, MSAL, Spring Security, Entra o AWS:

1. actualizar primero el starter canónico;
2. volver a ejecutar sus checkpoints;
3. actualizar las explicaciones que dependan del cambio;
4. no crear un segundo snippet alternativo sin una razón pedagógica explícita.
