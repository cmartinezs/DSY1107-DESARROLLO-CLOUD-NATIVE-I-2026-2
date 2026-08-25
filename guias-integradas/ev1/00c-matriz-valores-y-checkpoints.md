# 00C · Matriz de valores, origen y validación

**REQUERIDO EV1** · Ningún ID, URL, issuer, audience o scope se copia “porque se parece”. Cada valor debe tener origen, destino y prueba.

## Archivo de trabajo local

Crear `ev1-local-values.txt` y mantenerlo fuera de Git.

```text
TENANT_ID=
TENANT_DOMAIN=
TENANT_SUBDOMAIN=
SPA_CLIENT_ID=
API_CLIENT_ID=
API_AUDIENCE=
SCOPE_READ=
SCOPE_WRITE=
MSAL_AUTHORITY=
OIDC_ISSUER=
OIDC_JWKS_URI=
BACKEND_LOCAL_URL=http://localhost:8080
FRONTEND_LOCAL_URL=http://localhost:4200
BACKEND_CLOUD_URL=
API_GATEWAY_URL=
FRONTEND_CLOUD_URL=
```

## Matriz canónica

| Valor | Se obtiene en | Se utiliza en | Cómo validarlo |
|---|---|---|---|
| `TENANT_ID` | Entra External tenant | referencia/documentación | coincide con directorio seleccionado |
| `TENANT_SUBDOMAIN` | dominio del External tenant | `MSAL_AUTHORITY` | login abre dominio CIAM esperado |
| `SPA_CLIENT_ID` | app registration SPA | Angular/MSAL | login corresponde a `cloudtasks-spa` |
| `API_CLIENT_ID` | app registration API | identificación API | corresponde a `cloudtasks-api` |
| `API_AUDIENCE` | configuración/token real API | Spring + API Gateway | claim `aud` del Access Token coincide |
| `SCOPE_READ` | Expose an API | MSAL + ruta GET | claim `scp` contiene permiso esperado |
| `SCOPE_WRITE` | Expose an API | MSAL + POST/DELETE | claim `scp` contiene permiso esperado |
| `MSAL_AUTHORITY` | External tenant | MSAL | redirect va al tenant CIAM correcto |
| `OIDC_ISSUER` | metadata/token real | Spring + JWT Authorizer | claim `iss` coincide exactamente |
| `OIDC_JWKS_URI` | OIDC discovery | diagnóstico JWT | endpoint JWKS es alcanzable |
| `BACKEND_LOCAL_URL` | convención local | Angular etapa local | health = 200 |
| `FRONTEND_LOCAL_URL` | Angular | Entra redirect + CORS | navegador abre 4200 |
| `BACKEND_CLOUD_URL` | EC2 validado | integración API Gateway | health directo remoto = 200 |
| `API_GATEWAY_URL` | API Gateway Invoke URL | Angular | health vía Gateway = 200 |
| `FRONTEND_CLOUD_URL` | hosting frontend | Entra redirect + CORS | SPA HTTPS abre y login vuelve correctamente |

## Regla de propagación

Cuando un valor cambia, no “arreglar donde falle”. Buscar todos sus consumidores.

Ejemplo:

```text
FRONTEND_CLOUD_URL cambia
        ↓
redirect URI Entra
CORS API Gateway
config del propio frontend si aplica
README/evidencia
```

## Checkpoint por valor

Cada valor tiene tres estados:

```text
OBTENIDO → COPIADO → VALIDADO
```

No continuar con un valor que solo esté “obtenido”.

## Plantilla de registro

```text
Valor: API_AUDIENCE
Origen: Entra / token real
Destino: API Gateway + backend
Prueba: aud del Access Token = <valor>
Estado: VALIDADO
```

## Checkpoint 00C

- [ ] todos los valores conocidos tienen origen documentado.
- [ ] ningún placeholder `<...>` se confunde con valor real.
- [ ] `API_AUDIENCE` fue contrastado con Access Token.
- [ ] `OIDC_ISSUER` fue contrastado con metadata/token.
- [ ] `ev1-local-values.txt` no está versionado.
- [ ] el grupo sabe qué configuraciones dependen de cada URL.
