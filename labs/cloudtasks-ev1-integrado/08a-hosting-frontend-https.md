# 08A · Hosting frontend, HTTPS y mixed content

Este anexo usa **S3 + CloudFront** como opción de referencia para publicar una SPA Angular con una URL HTTPS estable. Si el laboratorio define otro hosting cloud, puede utilizarse siempre que produzca el mismo estado funcional.

## Estado de entrada

Antes de publicar debe existir:

```text
API_GATEWAY_URL validado
frontend local PASS
login local PASS
CORS local→Gateway PASS
```

La configuración del frontend debe apuntar al Gateway antes de compilar:

```ts
export const apiConfig = {
  baseUrl: '<API_GATEWAY_URL>'
};
```

No construir el bundle cloud mientras todavía exista:

```text
http://localhost:8080
```

como base URL de producción.

## Decisión de referencia

```text
Angular build
→ S3 (archivos estáticos)
→ CloudFront (HTTPS/CDN)
```

## 1. Build limpio

Desde `frontend/`:

```bash
npm ci
ng build
```

Localizar el directorio real generado dentro de `dist/`. No asumir una ruta fija si la versión/configuración de Angular produce otra estructura.

No subir source code como si fuese el build.

## 2. Verificar el bundle antes de publicar

Comprobar:

- [ ] build termina sin error;
- [ ] bundle no contiene secrets;
- [ ] bundle no contiene Access Tokens;
- [ ] API de producción apunta a `API_GATEWAY_URL`;
- [ ] no existen referencias funcionales a `localhost:8080`.

Si se sirve `dist/` localmente para inspección, recordar que eso **no reemplaza** la prueba HTTPS final.

## 3. Publicar

Crear/configurar hosting estático autorizado y obtener:

```text
FRONTEND_CLOUD_URL=https://...
```

Registrar el valor en 00C antes de propagarlo.

## 4. Comprobar HTTPS antes de tocar Entra/CORS

Abrir `FRONTEND_CLOUD_URL` en navegador.

Debe cumplirse:

```text
HTTPS válido
SPA carga
assets cargan
Console sin errores críticos
```

Si esto falla, corregir hosting antes de modificar identidad o CORS.

## 5. Mixed content

Arquitectura esperada:

```text
HTTPS frontend
→ HTTPS API Gateway
→ backend EC2
```

El navegador llama al endpoint HTTPS de API Gateway, no a `http://EC2:8080`.

Si DevTools muestra `Mixed Content`, revisar primero `apiConfig.baseUrl` y el bundle realmente desplegado.

## 6. Propagar `FRONTEND_CLOUD_URL`

Solo después de validar la URL cloud:

### Microsoft Entra

Agregar exactamente `FRONTEND_CLOUD_URL` como redirect URI de la plataforma SPA.

Comparar:

```text
scheme
host
port, si existe
path
slash final
```

### API Gateway CORS

Agregar exactamente:

```text
FRONTEND_CLOUD_URL
```

como allowed origin, conservando temporalmente `http://localhost:4200` si todavía se necesita desarrollo local.

No agregar:

```text
API_GATEWAY_URL
BACKEND_CLOUD_URL
```

como origins.

## 7. Login cloud

Desde `FRONTEND_CLOUD_URL`:

```text
abrir SPA
→ login
→ Entra External ID
→ redirect vuelve a FRONTEND_CLOUD_URL
→ active account disponible
```

El redirect local y el cloud son entradas distintas; ambos deben estar registrados si ambos se siguen utilizando.

## 8. SPA fallback

Si Angular usa rutas de cliente, una recarga directa puede devolver 403/404 desde hosting/CDN. Configurar el fallback correspondiente hacia `index.html` según el servicio utilizado.

No confundir un 403 del hosting por ruta SPA con un 403 de autorización del API.

## 9. Cache/CDN

Después de un redeploy, si el navegador sigue llamando a una URL antigua:

1. revisar Network y hash/nombre de bundles;
2. recargar ignorando cache;
3. invalidar/actualizar CDN si corresponde;
4. verificar que el build desplegado es el nuevo;
5. revisar `apiConfig.baseUrl` del source solo después de confirmar qué bundle está sirviendo el CDN.

No modificar Entra/CORS si el problema real es cache.

## Checkpoint 08A

- [ ] `ng build` PASS.
- [ ] bundle no contiene secrets/tokens.
- [ ] bundle usa `API_GATEWAY_URL`.
- [ ] `FRONTEND_CLOUD_URL` abre por HTTPS.
- [ ] SPA carga sin errores críticos.
- [ ] redirect URI cloud está registrada.
- [ ] CORS incluye origin cloud exacto.
- [ ] login vuelve al frontend cloud.
- [ ] Network muestra API Gateway, nunca EC2 directo.
- [ ] no existe mixed content.
- [ ] refresh de rutas SPA relevantes no rompe navegación.

## Decisión tecnológica

S3 + CloudFront es una referencia apropiada para esta práctica, no una dependencia conceptual de Angular, OAuth2/OIDC, JWT o CORS. Otra alternativa autorizada debe conservar:

```text
SPA desplegada
+ URL estable
+ HTTPS
+ redirect URI coherente
+ CORS coherente
+ consumo del API Gateway
```
