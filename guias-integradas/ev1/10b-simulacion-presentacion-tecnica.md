# 10B · Simulación de presentación técnica

## Objetivo

Practicar una explicación breve y ordenada de la arquitectura construida en la guía, de modo que el estudiante pueda demostrar comprensión técnica sin improvisar ni memorizar definiciones aisladas.

## Preparación 15–30 minutos antes de la simulación

### 1. GitHub

```bash
git status
git log -5 --oneline
git push
```

Comprobar que el trabajo de `guia/ev1/` está disponible remotamente.

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

Abrir:

```text
FRONTEND_CLOUD_URL
```

Confirmar HTTPS y revisar en Network que no se esté llamando `localhost` ni EC2 directo.

### 5. Entra

Realizar login real y comprobar que el frontend obtiene un Access Token válido.

### 6. Camino feliz

```text
login
→ /api/me
→ listar tareas
→ crear
→ eliminar propia
```

### 7. Prueba negativa segura

```text
request protegida sin token → 401
```

Si está preparado:

```text
scope/ownership inválido → rechazo
```

## Orden sugerido de 5–10 minutos

```text
0:00 arquitectura
0:45 Entra + SPA/API/user flow
2:00 login + PKCE
3:00 Access Token/claims
4:00 API Gateway/JWT Authorizer
5:15 CORS/preflight
6:00 frontend → Gateway → EC2
7:00 prueba negativa
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

No dejar tokens, passwords o secretos visibles.

## Si algo falla durante la simulación

Usar el último checkpoint conocido:

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

## Preguntas rápidas de autoevaluación

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

★ Advanced Developer:

```text
WSL2 vs container
image vs container
host port vs container port
qué cambia y qué no al usar Docker
```

## Checkpoint PRESENTACION-TECNICA

- [ ] CP-00…CP-09 PASS.
- [ ] COV-01…COV-08 PASS.
- [ ] trabajo remoto actualizado.
- [ ] recursos cloud disponibles.
- [ ] URLs verificadas.
- [ ] login probado.
- [ ] prueba negativa preparada.
- [ ] secretos fuera de pantalla.
- [ ] cada integrante puede explicar la arquitectura.
