# 09A · Runbook de checkpoints y estado conocido

**REQUERIDO EV1** · Esta guía permite retomar el trabajo después de horas/días sin asumir que “todo sigue funcionando”.

## Regla general

Antes de iniciar una nueva sesión de trabajo:

```text
1. Git actualizado
2. frontend local responde
3. backend local responde
4. identidad/token se valida
5. backend cloud responde
6. Gateway responde
7. frontend cloud responde
```

No saltar al último componente si falla uno anterior.

## CP-00 · Entorno

```bash
git --version
java -version
node --version
npm --version
ng version
```

Resultado: tooling disponible.

## CP-01 · Git

```bash
git status
git pull
```

Resultado: working tree entendido y repo sincronizado.

## CP-02 · Backend local

```text
GET localhost:8080/api/public/health → 200
GET localhost:8080/api/tasks sin token → 401 (después de seguridad)
```

## CP-03 · Frontend local

```text
http://localhost:4200 abre
Console sin errores críticos
```

## CP-04 · Identidad

```text
login External ID funciona
active account existe
Access Token existe
aud correcto
scope correcto
```

## CP-05 · Backend EC2

```text
health directo remoto → 200
protegida sin token → 401
```

Ruta base: comprobar proceso/systemd.

★ Docker: comprobar `docker ps` + logs.

## CP-06 · API Gateway

```text
health Gateway → 200
sin token → 401
token válido + scope → éxito
```

## CP-07 · CORS

Desde navegador:

```text
origin local/cloud permitido
OPTIONS/preflight correcto
Authorization permitido
```

## CP-08 · Frontend cloud

```text
HTTPS abre
login vuelve correctamente
Network llama Gateway
JSON llega a UI
```

## CP-09 · GitHub remoto

```text
últimos commits visibles
README actualizado
sin secretos
```

## Plantilla de checkpoint

```markdown
### CP-XX · <nombre>
Fecha:
Commit:
Resultado esperado:
Resultado observado:
Estado: PASS | FAIL
Evidencia:
Cambio desde último PASS:
```

## Regla FAIL

Si un checkpoint falla:

```text
NO continuar
NO cambiar tres capas simultáneamente
NO borrar configuración que antes funcionaba
```

1. identificar último checkpoint PASS;
2. comparar qué cambió desde ese punto;
3. corregir una capa;
4. repetir prueba positiva;
5. recién después avanzar.

## Checkpoint 09A final

La solución está lista para defensa solo cuando CP-00 a CP-09 pueden repetirse sin intervención improvisada del docente.
