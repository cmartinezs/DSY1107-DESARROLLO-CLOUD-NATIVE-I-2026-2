# 10 · Verificación integrada de la guía

## Objetivo

Cerrar CloudTasks comprobando que todos los componentes funcionan juntos y que el estudiante puede explicar el flujo completo sin depender de una secuencia memorizada.

## Cadena técnica completa

```text
Angular SPA
→ MSAL Angular
→ Entra External ID
→ Authorization Code + PKCE
→ Access Token
→ MsalInterceptor
→ API Gateway
→ JWT Authorizer
→ integración EC2
→ Spring Security Resource Server
→ scope
→ ownership
→ JSON
→ navegador + CORS
```

★ Si se realizó 04B:

```text
roles claim
→ converter explícito
→ ROLE_Admin
```

## 1. Arquitectura

El estudiante debe poder recorrer el diagrama y responder:

```text
qué componente es
qué recibe
qué valida/hace
qué entrega
qué NO es responsabilidad de ese componente
```

## 2. Identidad

Comprobar:

```text
External tenant correcto
user flow
cloudtasks-spa
cloudtasks-api
redirect URI
SCOPE_READ/SCOPE_WRITE completos
permisos/consent
```

Explicar por qué SPA y API son registrations diferentes.

## 3. OAuth2/OIDC + PKCE

Poder explicar:

```text
OIDC → identidad/sesión
OAuth2 → autorización hacia recurso
Authorization Code → resultado intermedio
PKCE → vincula code con cliente que inició flujo
SPA → no guarda client secret
MSAL → implementa el protocolo
```

No se exige reconstruir manualmente `code_verifier` o `code_challenge`.

## 4. Token

Inspeccionar únicamente claims sanitizados:

```text
iss
aud
sub
exp
scp
roles si existen
```

Comprobar las tres representaciones de permiso:

```text
scope solicitado por MSAL
api://.../tasks.read

claim scp
 tasks.read

Spring authority
SCOPE_tasks.read
```

Explicar por qué **decodificar** un JWT no valida firma ni confianza.

## 5. Spring Security

Comprobar:

```text
health público 200
sin token 401
issuer incorrecto 401
audience incorrecta 401
scope faltante 403/rechazo
scope correcto éxito
ownership ajeno 403
```

El `ownerId` de una tarea debe provenir de `jwt.sub`, no del cliente.

## 6. API Gateway

Comprobar:

```text
rutas
integración a BACKEND_CLOUD_URL
JWT Authorizer
issuer
audience
scopes por ruta
CORS
```

Explicar por qué Gateway y Spring Security son controles complementarios.

## 7. CORS

Desde navegador:

```text
Origin frontend real
OPTIONS/preflight cuando corresponda
allowed methods
allowed headers
Authorization
```

Ejecutar una prueba de origin inválido y restaurar el estado válido.

No utilizar Postman como sustituto de esta comprobación.

## 8. Frontend cloud

Comprobar en DevTools:

```text
HTTPS
sin mixed content
API base = API_GATEWAY_URL
no localhost
no EC2 directo
redirect cloud correcto
JSON visible en UI
```

## 9. Flujo positivo mínimo

```text
abrir frontend
→ login
→ /api/me
→ listar tareas
→ crear tarea
→ eliminar tarea propia
```

## 10. Flujo negativo mínimo

Ejecutar al menos:

```text
sin token → 401
```

y una autorización aceptada pero insuficiente:

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

## Preguntas de autoverificación

1. ¿Qué diferencia existe entre OAuth2 y OIDC?
2. ¿Por qué Angular no guarda un client secret?
3. ¿Qué protege PKCE?
4. ¿Para qué sirve el ID Token?
5. ¿Para qué sirve el Access Token?
6. ¿Qué diferencia existe entre scope completo solicitado y claim `scp`?
7. ¿Por qué `aud` debe corresponder al recurso esperado?
8. ¿Qué ocurre cuando `exp` está en el pasado?
9. ¿Cómo obtiene Gateway/Spring las claves públicas para validar JWT?
10. ¿Qué diferencia hay entre scope, role y ownership?
11. ¿Qué diferencia hay entre 401 y 403?
12. ¿Qué seguridad pertenece al Gateway y cuál al backend?
13. ¿Por qué Postman funcionando no demuestra CORS?
14. ¿Qué es un preflight?
15. ¿Por qué no esconder CORS con `*`?
16. ¿Por qué frontend consume API Gateway y no EC2 directo?
17. ¿Qué cambia si Spring corre en Docker y qué permanece igual?

## Reproducibilidad

El trabajo debe poder entenderse desde:

```text
guia/ev1/
├── README.md
├── frontend/
├── backend/
└── docs/
```

README sugerido:

```text
prerrequisitos
cómo ejecutar localmente
configuración pública requerida
qué valores no se versionan
URLs utilizadas
arquitectura
flujo de autenticación
tests/checkpoints reproducibles
```

## Seguridad

No registrar/subir:

```text
passwords
client secrets
Access/Refresh Tokens completos
AWS keys
cookies de sesión
private keys
```

## Checkpoint 10

- [ ] CP-00…CP-10 del runbook están PASS.
- [ ] COV-01…COV-08 están PASS.
- [ ] COV-X1 ownership comprendido.
- [ ] COV-X3 Gateway vs backend comprendido.
- [ ] ★ COV-X2 roles PASS u omitido conscientemente por sandbox.
- [ ] login External ID funciona.
- [ ] PKCE puede explicarse sin implementación manual.
- [ ] Access Token corresponde a CloudTasks API.
- [ ] issuer/audience están validados.
- [ ] scopes producen authorities esperadas.
- [ ] ownership se demuestra con 403.
- [ ] Gateway protege rutas.
- [ ] CORS fue comprobado desde navegador.
- [ ] frontend cloud usa Gateway por HTTPS.
- [ ] otra persona puede reconstruir la práctica desde GitHub.
- [ ] no hay secretos versionados.

## Checkpoint documental docente

Desde la raíz del repositorio docente:

```bash
python scripts/validate_integrated_guides.py
```

Debe terminar en `PASS` antes de publicar cambios de la guía.
