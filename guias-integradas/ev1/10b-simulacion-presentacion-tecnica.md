# 10B · Simulación de presentación técnica

## Objetivo

Practicar una explicación breve y ordenada de la arquitectura construida en la guía, demostrando comprensión técnica sin memorizar definiciones aisladas.

## Preparación 15–30 minutos antes

### 1. Validación documental docente

Si se está trabajando desde el repositorio docente:

```bash
python scripts/validate_integrated_guides.py
```

Debe quedar `PASS` antes de usar la guía como referencia.

### 2. GitHub del estudiante

```bash
git status
git log -5 --oneline
git push
```

Comprobar que `guia/ev1/` está disponible remotamente.

### 3. Backend EC2

Ruta base:

```text
systemd/proceso activo
health directo = 200
```

★ Docker:

```bash
docker ps
docker logs --tail 30 cloudtasks-api
```

### 4. API Gateway

```text
GET health → 200
GET tasks sin token → 401
```

### 5. Frontend cloud

Abrir:

```text
FRONTEND_CLOUD_URL
```

Confirmar HTTPS y revisar en Network que la API sea `API_GATEWAY_URL`, no `localhost` ni EC2 directo.

### 6. Entra/MSAL

Realizar login y comprobar:

```text
active account
Access Token
iss correcto
aud correcto
scp esperado
```

### 7. Camino feliz

```text
login
→ /api/me
→ listar tareas
→ crear tarea
→ eliminar tarea propia
```

### 8. Pruebas negativas seguras

Mínima:

```text
request protegida sin token → 401
```

Luego una de autorización:

```text
scope faltante → 403/rechazo
```

o:

```text
write scope + recurso ajeno → 403
```

★ Roles:

```text
sin Admin → 403
con Admin → 200
```

No romper deliberadamente configuración cloud si la misma idea puede demostrarse con una request controlada.

## Orden sugerido de 5–10 minutos

```text
0:00 arquitectura y responsabilidades
0:45 External tenant + SPA/API/user flow
2:00 login + Authorization Code + PKCE
3:00 Access Token: iss/aud/scp
4:00 API Gateway + JWT Authorizer
5:15 CORS/preflight
6:00 frontend → Gateway → EC2 → JSON
7:00 401/403/ownership
8:00 decisiones y cierre
```

## Pestañas útiles

```text
1. frontend cloud
2. DevTools Network
3. Entra tenant/user flow/apps
4. API Gateway routes/authorizer/CORS
5. EC2
6. GitHub
```

No dejar tokens, passwords, cookies o secretos visibles.

## Si algo falla durante la simulación

No improvisar cambios. Volver a [09A](./09a-runbook-checkpoints-estado-conocido.md):

```text
CP-00 entorno
↓
CP-01 repo
↓
CP-02/03 local
↓
CP-04 identidad
↓
CP-05 Spring Security
↓
CP-06 EC2
↓
CP-07 Gateway
↓
CP-08 CORS
↓
CP-09 frontend cloud
```

## Preguntas rápidas de comprobación

```text
OAuth2 vs OIDC
ID Token vs Access Token
por qué PKCE
por qué SPA sin secret
scope completo solicitado vs scp
iss vs aud
scope vs role
scope vs ownership
401 vs 403
Gateway vs backend security
qué hace CORS
qué hace preflight
por qué Postman no prueba CORS
por qué frontend usa Gateway
qué corre en EC2
```

★ Advanced Developer:

```text
Git Bash vs WSL2
WSL2 vs container
image vs container
host port vs container port
qué cambia y qué no al usar Docker
por qué se mantiene EC2
```

## Checkpoint PRESENTACION-TECNICA

- [ ] CP-00…CP-10 PASS.
- [ ] COV-01…COV-08 PASS.
- [ ] COV-X1 y COV-X3 comprendidos.
- [ ] COV-X2 PASS u omitido conscientemente por sandbox.
- [ ] trabajo remoto actualizado.
- [ ] recursos cloud disponibles.
- [ ] URLs verificadas.
- [ ] login probado.
- [ ] prueba 401 preparada.
- [ ] prueba 403 preparada.
- [ ] secretos fuera de pantalla.
- [ ] cada integrante puede explicar la arquitectura de extremo a extremo.
