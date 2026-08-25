# 08A · Hosting frontend, HTTPS y mixed content

Este anexo usa **S3 + CloudFront** como opción de referencia para publicar una SPA Angular con una URL HTTPS estable. Si el laboratorio define otro hosting cloud, puede utilizarse siempre que produzca el mismo estado funcional.

## Decisión de referencia

```text
Angular build
→ S3 (archivos estáticos)
→ CloudFront (HTTPS/CDN)
```

## 1. Build limpio

```bash
cd frontend
npm ci
ng build
```

Localizar el directorio real dentro de `dist/`. No subir source code como si fuese el build.

## 2. Probar build antes de cloud

Servir `dist` con un servidor HTTP estático local y comprobar que `index.html` abre.

**Checkpoint 08A-1**

- [ ] build termina sin error.
- [ ] bundle no contiene secrets.
- [ ] API de producción no apunta a `localhost`.

## 3. Publicar

Crear/configurar hosting estático autorizado. Obtener:

```text
FRONTEND_CLOUD_URL=https://...
```

Preferir HTTPS para la SPA.

## 4. Mixed content

Una página cargada mediante HTTPS no debe depender de requests HTTP inseguras desde el navegador.

Arquitectura final esperada:

```text
HTTPS frontend
→ HTTPS API Gateway
→ backend EC2
```

El navegador llama al endpoint HTTPS de API Gateway, no al `http://EC2:8080` directo.

Si DevTools muestra `Mixed Content`, revisar primero la URL configurada en Angular.

## 5. Redirect URI

Agregar exactamente `FRONTEND_CLOUD_URL` a la plataforma SPA en Entra.

Comparar carácter a carácter:

```text
scheme
host
port (si existe)
path
slash final
```

## 6. CORS

Agregar como allowed origin:

```text
FRONTEND_CLOUD_URL
```

No agregar `API_GATEWAY_URL` como origin: el origin es la SPA.

## 7. SPA fallback

Si Angular usa rutas de cliente, una recarga directa puede devolver 403/404 desde hosting/CDN. Configurar el fallback correspondiente hacia `index.html` según el servicio utilizado.

## 8. Cache

Después de un redeploy, si el navegador sigue llamando a una URL antigua:

1. revisar Network y nombre/hash de bundles;
2. recargar ignorando cache;
3. invalidar/actualizar CDN si corresponde;
4. verificar que el build desplegado contiene la config correcta.

No modificar Entra/CORS si el problema real es un bundle antiguo.

## Checkpoint 08A-2

- [ ] `FRONTEND_CLOUD_URL` abre por HTTPS.
- [ ] login inicia y retorna a esa URL.
- [ ] Network muestra llamadas a `API_GATEWAY_URL`, nunca EC2 directo.
- [ ] no existe mixed content.
- [ ] CORS permite origin cloud exacto.
- [ ] refresh de la SPA no rompe navegación relevante.

## Decisión tecnológica

S3 + CloudFront es una referencia apropiada para esta práctica, no una dependencia conceptual de Angular, OAuth2/OIDC, JWT o CORS. Si se utiliza otra alternativa autorizada, debe conservarse:

```text
SPA desplegada
+ URL estable
+ HTTPS
+ redirect URI coherente
+ CORS coherente
+ consumo del API Gateway
```
