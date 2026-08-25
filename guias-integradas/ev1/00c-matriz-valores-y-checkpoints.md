# 00C · Matriz de valores, origen y validación

Ningún ID, URL, issuer, audience o scope se copia “porque se parece”. Cada valor debe tener **origen, destino y prueba**.

## Archivo de trabajo local

Crear `ev1-local-values.txt` dentro del contexto de la guía y mantenerlo fuera de Git.

```text
TENANT_ID=
TENANT_DOMAIN=
TENANT_SUBDOMAIN=
SPA_CLIENT_ID=
API_CLIENT_ID=
API_AUDIENCE=
SCOPE_READ=
SCOPE_WRITE=
SCOPE_READ_CLAIM=tasks.read
SCOPE_WRITE_CLAIM=tasks.write
MSAL_AUTHORITY=
OIDC_ISSUER=
OIDC_JWKS_URI=
BACKEND_LOCAL_URL=http://localhost:8080
FRONTEND_LOCAL_URL=http://localhost:4200
BACKEND_CLOUD_URL=
API_GATEWAY_URL=
FRONTEND_CLOUD_URL=
```

## Distinción crítica: scope solicitado vs claim emitido

No confundir:

```text
scope que Angular/MSAL solicita
        ≠ necesariamente
texto que aparece dentro de scp
```

Ejemplo típico:

```text
SCOPE_READ=api://<API_CLIENT_ID>/tasks.read
SCOPE_READ_CLAIM=tasks.read
```

Entonces:

```text
MSAL protectedResourceMap
→ usa SCOPE_READ completo

Access Token scp
→ contiene tasks.read

Spring Security authority
→ SCOPE_tasks.read
```

Lo mismo aplica a escritura.

## Matriz canónica

| Valor | Se obtiene en | Se utiliza en | Cómo validarlo |
|---|---|---|---|
| `TENANT_ID` | Entra External tenant | referencia técnica | coincide con directorio seleccionado |
| `TENANT_DOMAIN` | External tenant | referencia/discovery | corresponde al tenant usado |
| `TENANT_SUBDOMAIN` | dominio External tenant | `MSAL_AUTHORITY` | login abre dominio CIAM esperado |
| `SPA_CLIENT_ID` | app registration SPA | MSAL Angular | login corresponde a `cloudtasks-spa` |
| `API_CLIENT_ID` | app registration API | exposición/identificación API | corresponde a `cloudtasks-api` |
| `API_AUDIENCE` | configuración + Access Token real | Spring + API Gateway | claim `aud` coincide exactamente |
| `SCOPE_READ` | Expose an API | MSAL `protectedResourceMap` GET | token adquirido contiene permiso de lectura |
| `SCOPE_WRITE` | Expose an API | MSAL `protectedResourceMap` POST/DELETE | token adquirido contiene permiso de escritura |
| `SCOPE_READ_CLAIM` | claim `scp` observado | Spring/Gateway policy | `scp` contiene `tasks.read` |
| `SCOPE_WRITE_CLAIM` | claim `scp` observado | Spring/Gateway policy | `scp` contiene `tasks.write` |
| `MSAL_AUTHORITY` | External tenant | MSAL Angular | redirect va al tenant CIAM correcto |
| `OIDC_ISSUER` | metadata + token real | Spring + JWT Authorizer | claim `iss` coincide exactamente |
| `OIDC_JWKS_URI` | OIDC discovery | diagnóstico JWT | endpoint JWKS es alcanzable |
| `BACKEND_LOCAL_URL` | convención local | Angular local | health = 200 |
| `FRONTEND_LOCAL_URL` | Angular | redirect local + CORS | navegador abre 4200 |
| `BACKEND_CLOUD_URL` | EC2 validado | integración API Gateway | health directo remoto = 200 |
| `API_GATEWAY_URL` | Invoke URL | Angular cloud/local | health vía Gateway = 200 |
| `FRONTEND_CLOUD_URL` | hosting frontend | redirect + CORS | SPA HTTPS abre y login retorna |

## Orden temporal de obtención

No todos los valores existen al comenzar.

```text
00/01
BACKEND_LOCAL_URL
FRONTEND_LOCAL_URL

02 Entra
TENANT_*
SPA_CLIENT_ID
API_CLIENT_ID
SCOPE_READ
SCOPE_WRITE
MSAL_AUTHORITY
OIDC_ISSUER
OIDC_JWKS_URI

03 token real
API_AUDIENCE confirmado
SCOPE_READ_CLAIM confirmado
SCOPE_WRITE_CLAIM confirmado

05 AWS backend
BACKEND_CLOUD_URL

06 Gateway
API_GATEWAY_URL

08 frontend hosting
FRONTEND_CLOUD_URL
```

Si un valor aún no existe según esta secuencia, **no debe inventarse** para avanzar.

## Regla de propagación

Cuando un valor cambia, buscar todos sus consumidores.

Ejemplo:

```text
FRONTEND_CLOUD_URL cambia
        ↓
redirect URI Entra
CORS API Gateway
config frontend si aplica
README de práctica
```

Otro ejemplo:

```text
API_GATEWAY_URL cambia
        ↓
apiConfig.baseUrl Angular
CORS/preflight que observa el navegador
checkpoints E2E
```

## Estado de cada valor

```text
OBTENIDO → COPIADO → VALIDADO
```

No continuar con un valor que solo esté obtenido.

## Plantilla de registro

```text
Valor: API_AUDIENCE
Origen: Access Token real para cloudtasks-api
Destino: Spring + API Gateway
Prueba: claim aud = <valor>
Estado: VALIDADO
```

## Checkpoint 00C

- [ ] todos los valores existentes tienen origen identificado.
- [ ] ningún placeholder `<...>` se confundió con valor real.
- [ ] scopes completos y claims cortos se distinguen.
- [ ] `API_AUDIENCE` se confirmó con Access Token real antes de seguridad cloud.
- [ ] `OIDC_ISSUER` se confirmó con metadata/token real.
- [ ] `ev1-local-values.txt` no está versionado.
- [ ] una URL nueva se propaga a todos sus consumidores antes de continuar.
