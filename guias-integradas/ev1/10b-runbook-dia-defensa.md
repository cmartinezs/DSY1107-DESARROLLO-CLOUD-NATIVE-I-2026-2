# 10B · Runbook del día de la defensa

**REQUERIDO OPERACIONAL** · La demo se prepara; no se descubre el estado del sistema frente al docente.

## 15–30 minutos antes

### 1. GitHub

```bash
git status
git log -5 --oneline
git push
```

Comprobar en navegador que el commit final existe.

### 2. Backend EC2

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

### 3. API Gateway

```text
GET health → 200
GET tasks sin token → 401
```

### 4. Frontend cloud

Abrir en ventana nueva/private según necesidad:

```text
FRONTEND_CLOUD_URL
```

Confirmar HTTPS y que Network no llama `localhost` ni EC2 directo.

### 5. Entra

Realizar login real. Confirmar que puede volver al frontend y adquirir Access Token.

### 6. Camino feliz

```text
login
→ /api/me
→ listar tareas
→ crear
→ eliminar propia
```

### 7. Una prueba negativa preparada

Preferir una segura y reproducible:

```text
request protegida sin token → 401
```

y otra de autorización si está preparada:

```text
scope/ownership inválido → 403/rechazo
```

No romper configuración productiva durante la presentación si no es necesario.

## Orden sugerido de 5–10 minutos

```text
0:00 arquitectura
0:45 Entra + SPA/API/user flow
2:00 login + PKCE
3:00 Access Token/claims
4:00 API Gateway/JWT Authorizer
5:15 CORS/preflight
6:00 llamada frontend → Gateway → EC2
7:00 prueba negativa
8:00 decisiones y cierre
```

Ajustar al tiempo asignado, pero conservar el hilo causal.

## Ventanas/pestañas preparadas

```text
1. frontend cloud
2. DevTools Network
3. Entra tenant/user flow/apps
4. API Gateway routes/authorizer/CORS
5. EC2
6. GitHub repo
```

No tener tokens, passwords o secretos visibles en terminal/history.

## Si algo falla en vivo

No editar cinco configuraciones.

Usar el último checkpoint:

```text
frontend abre?
↓
login funciona?
↓
token correcto?
↓
Gateway health?
↓
backend health?
```

Explicar el diagnóstico también demuestra comprensión técnica.

## Preguntas rápidas que todos deben dominar

```text
OAuth2 vs OIDC
ID Token vs Access Token
por qué PKCE
por qué SPA sin secret
iss vs aud
scope vs role
401 vs 403
Gateway vs backend security
qué hace CORS
qué hace preflight
por qué Postman no prueba CORS
por qué frontend usa Gateway
qué corre en EC2
```

★ Advanced additionally:

```text
WSL2 vs container
image vs container
host port vs container port
qué cambió y qué no cambió al usar Docker
```

## Checkpoint DEFENSA-READY

- [ ] CP-00…CP-09 PASS.
- [ ] EV1-01…EV1-08 PASS.
- [ ] repo remoto actualizado.
- [ ] recursos cloud encendidos.
- [ ] URLs verificadas.
- [ ] login probado ese día.
- [ ] prueba negativa preparada.
- [ ] secretos fuera de pantalla.
- [ ] todos los integrantes pueden explicar arquitectura.
