# 08 · Desplegar frontend e integrar extremo a extremo

## Objetivo

Publicar Angular, obtener una URL cloud real y cerrar el flujo:

```text
navegador
→ Entra
→ Angular
→ API Gateway
→ EC2
→ Spring Boot
```

## Antes de comenzar

Debe existir:

```text
API_GATEWAY_URL validado
Gateway health = 200
Gateway protegida sin token = 401
Access Token válido funciona vía Gateway
```

Si Gateway todavía falla con `curl`, no publicar frontend para intentar ocultar el problema.

## Hosting

Seguir:

→ [08A · Hosting frontend, HTTPS y mixed content](./08a-hosting-frontend-https.md)

S3 + CloudFront es la opción de referencia, no una dependencia conceptual.

---

# 1. Cambiar una sola configuración: API base URL

En 03A `auth-config.ts` quedó:

```ts
export const apiConfig = {
  baseUrl: 'http://localhost:8080',
  readScope: '<SCOPE_READ>',
  writeScope: '<SCOPE_WRITE>'
};
```

Para el build cloud cambiar **solo** `baseUrl`:

```ts
export const apiConfig = {
  baseUrl: '<API_GATEWAY_URL>',
  readScope: '<SCOPE_READ>',
  writeScope: '<SCOPE_WRITE>'
};
```

Ejemplo conceptual:

```text
https://abc123.execute-api.<region>.amazonaws.com
```

No agregar `/api/tasks` al base URL porque `ApiService` ya agrega las rutas.

No usar:

```text
http://localhost:8080
http://<EC2>:8080
```

en el build cloud.

### Redirect URI no necesita hardcode

El starter utiliza:

```ts
redirectUri: window.location.origin
```

Por eso, una vez servido desde cloud, MSAL usa automáticamente el origin cloud. **Ese origin sí debe estar previamente registrado en Entra** antes de probar login.

---

# 2. Build limpio

Desde `frontend/`:

```bash
npm ci
ng build
```

Si `npm ci` falla porque `package-lock.json` no corresponde a `package.json`, resolver la discrepancia conscientemente; no cambiar a `npm install` solo para ocultarla.

**CHECKPOINT 08-1 · build**

- [ ] `npm ci` PASS.
- [ ] `ng build` PASS.
- [ ] `apiConfig.baseUrl` contiene `API_GATEWAY_URL`.
- [ ] bundle no contiene client secrets/tokens/keys.

## Comprobación rápida de configuración

Buscar en el source antes de desplegar:

```text
localhost:8080
```

El único uso aceptable puede estar en documentación/comentarios; no debe ser la API activa del build cloud.

---

# 3. Publicar primero, configurar integración después

Publicar los archivos de `dist/` mediante el hosting elegido.

Obtener y validar:

```text
FRONTEND_CLOUD_URL=https://...
```

Antes de tocar Entra/CORS:

- [ ] URL abre.
- [ ] HTTPS válido.
- [ ] bundles cargan.
- [ ] Console no muestra error fatal.

Registrar `FRONTEND_CLOUD_URL` en 00C.

---

# 4. Registrar redirect URI cloud

En `cloudtasks-spa`, plataforma SPA, agregar exactamente:

```text
FRONTEND_CLOUD_URL
```

Como el código usa `window.location.origin`, si el hosting abre en:

```text
https://app.ejemplo.cl
```

el redirect registrado debe ser coherente con ese origin.

No eliminar todavía `http://localhost:4200` si se desea conservar pruebas locales.

---

# 5. Configurar CORS cloud

En API Gateway agregar como allowed origin:

```text
FRONTEND_CLOUD_URL
```

No confundir:

```text
Origin = frontend
Destino = API Gateway
```

Por lo tanto esto es incorrecto:

```text
allowed origin = API_GATEWAY_URL
```

Seguir también [07 · CORS](./07-cors.md).

---

# 6. Login cloud

Abrir una ventana nueva y navegar a:

```text
FRONTEND_CLOUD_URL
```

Presionar login.

Esperado:

```text
frontend HTTPS
→ ciamlogin.com
→ user flow
→ retorno a FRONTEND_CLOUD_URL
→ active account
```

**CHECKPOINT 08-2 · identidad cloud**

- [ ] frontend HTTPS abre.
- [ ] authority correcta.
- [ ] redirect vuelve al frontend cloud.
- [ ] Access Token conserva `iss`/`aud` correctos.

---

# 7. Verificar requests en Network

Ejecutar:

```text
Mi identidad
Recargar tareas
Crear tarea
Eliminar tarea propia
```

En DevTools cada request protegida debe tener como host:

```text
API_GATEWAY_URL
```

Nunca:

```text
localhost
EC2 público directo
```

Además observar:

```text
Authorization: Bearer ...
Origin: FRONTEND_CLOUD_URL
status esperado
```

**CHECKPOINT 08-3 · E2E**

```text
/api/me        PASS
GET tasks      PASS
POST task      PASS
DELETE propia  PASS
```

---

# 8. Mixed content

La cadena navegador debe ser:

```text
HTTPS frontend
→ HTTPS API Gateway
```

Aunque Gateway integre internamente con un backend HTTP de laboratorio, el navegador no debe llamar esa URL EC2 directamente.

Si aparece `Mixed Content`, revisar `apiConfig.baseUrl` y el bundle desplegado antes de modificar CORS.

---

# 9. Cache/build antiguo

Si se cambió `apiConfig` pero Network sigue mostrando localhost:

```text
1. comprobar source actual
2. ejecutar ng build nuevamente
3. confirmar qué carpeta dist se publicó
4. revisar hash/nombre de bundles
5. recarga sin cache
6. invalidación CDN si corresponde
```

No modificar Entra para reparar un JavaScript antiguo servido por cache.

---

# 10. SPA fallback

Si `/` funciona pero una recarga directa en una ruta Angular produce 403/404, configurar el hosting/CDN para devolver `index.html` donde corresponda.

CloudTasks evita routing innecesario, por lo que este problema debería ser mínimo; no agregar rutas solo para probarlo.

---

# 11. Volver a desarrollo local

Si luego se desea continuar localmente, restaurar:

```ts
baseUrl: 'http://localhost:8080'
```

La alternativa profesional sería introducir environments/runtime config, pero para esta práctica una única línea explícita mantiene visible qué endpoint se consume y evita scaffolding adicional.

---

# Puerta de validación 08

```text
build limpio PASS
frontend HTTPS PASS
redirect cloud PASS
Access Token PASS
Network → API Gateway PASS
CORS cloud PASS
sin mixed content PASS
/api/me PASS
GET tasks PASS
POST task PASS
DELETE propia PASS
```

**SI FALLA** · usar [09A · estado conocido](./09a-runbook-checkpoints-estado-conocido.md). No cambiar simultáneamente hosting, Entra y Gateway.
