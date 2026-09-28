# 09A · Runbook de checkpoints y estado conocido

Esta guía permite retomar el trabajo después de horas o días sin asumir que “todo sigue funcionando”.

## Regla general

No saltar al último componente. Recuperar la cadena en orden:

```text
CP-00 entorno
→ CP-01 Git/workspace
→ CP-02 backend local
→ CP-03 frontend local
→ CP-04 identidad/MSAL/token
→ CP-05 seguridad backend
→ CP-06 EC2
→ CP-07 API Gateway
→ CP-08 CORS navegador
→ CP-09 frontend cloud/E2E
→ CP-10 GitHub/reproducibilidad
```

## CP-00 · Entorno

```bash
git --version
java -version
node --version
npm --version
ng version
```

Si se usa `gh`:

```bash
gh auth status
```

Resultado:

```text
Git PASS
Java 21 PASS
Node/npm PASS
Angular CLI PASS
```

★ Advanced agrega:

```text
WSL2 PASS
Docker PASS
```

## CP-01 · Git y workspace

```bash
git status
git pull
git rev-parse --show-toplevel
```

Comprobar:

```text
guia/ev1/ existe
no hay .git anidado
working tree entendido
```

## CP-02 · Backend local

Antes de seguridad:

```text
GET localhost:8080/api/public/health → 200
```

Después de 04A:

```text
GET health sin token → 200
GET tasks sin token  → 401
```

Además:

```text
Maven Wrapper build/test PASS
```

## CP-03 · Frontend local

```text
http://localhost:4200 abre
Console sin errores críticos
Angular major conocido
```

Después de 01C:

```text
frontend → backend health = 200
CORS local PASS
```

## CP-04 · Identidad + MSAL

```text
External tenant correcto
user flow PASS
SPA/API registrations PASS
login PASS
active account PASS
```

Desde Network/token real:

```text
Authorization Bearer presente
aud = API_AUDIENCE validado
iss = OIDC_ISSUER validado
scp contiene permiso esperado
```

## CP-05 · Spring Security

```text
health sin token 200
protegida sin token 401
token válido 200 según ruta
aud incorrecta 401
scope faltante 403/rechazo
ownership ajeno 403
```

★ Si se hizo 04B:

```text
Admin ausente 403
Admin presente 200
```

## CP-06 · Backend EC2

```text
health localhost EC2 → 200
health remoto         → 200
protegida sin token   → 401
proceso persiste sin sesión SSH
BACKEND_CLOUD_URL validado
```

Ruta base:

```text
Java/JAR/systemd
```

★ Docker:

```text
docker ps
logs correctos
restart policy
```

## CP-07 · API Gateway

```text
health Gateway → 200
protegida sin token → 401
audience inválida → 401
token válido + scope correcto → llega al backend
API_GATEWAY_URL validado
```

## CP-08 · CORS navegador

Desde DevTools:

```text
Origin correcto
OPTIONS/preflight correcto
Authorization permitido
origin inválido bloqueado
```

No sustituir por Postman.

## CP-09 · Frontend cloud / E2E

```text
HTTPS abre
login retorna a frontend cloud
Network llama API Gateway
no mixed content
GET/POST/DELETE esperados PASS
JSON llega a UI
```

## CP-10 · GitHub y reproducibilidad

```text
últimos commits visibles
README de práctica actualizado
sin secretos
otra persona puede reconstruir el estado desde repo + guía
```

## CP-DOC · Integridad de documentación docente

Desde la raíz del repositorio docente:

```bash
python scripts/validate_integrated_guides.py
```

Esperado:

```text
PASS: enlaces relativos, fences, Mermaid básico y reglas semánticas.
```

Este checkpoint no sustituye los builds funcionales.

## Plantilla de checkpoint

```markdown
### CP-XX · <nombre>
Fecha:
Commit:
Resultado esperado:
Resultado observado:
Estado: PASS | FAIL
Registro:
Cambio desde último PASS:
```

## Regla FAIL

Si un checkpoint falla:

```text
NO continuar
NO cambiar varias capas simultáneamente
NO borrar configuración que antes funcionaba
```

1. identificar último `PASS`;
2. comparar qué cambió desde ese punto;
3. corregir una sola capa;
4. repetir prueba positiva;
5. actualizar el checkpoint;
6. recién después avanzar.

## Checkpoint final

La práctica queda técnicamente estable cuando CP-00 a CP-10 pueden repetirse y CP-DOC no reporta regresiones documentales.
