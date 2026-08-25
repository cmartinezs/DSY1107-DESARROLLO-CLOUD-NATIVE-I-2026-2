# 08A · Hosting frontend, HTTPS y mixed content

**REQUERIDO EV1** · La pauta exige frontend desplegado, activo e integrado. El material revisado no fija S3/CloudFront como tecnología obligatoria; por eso se usa como **opción de referencia**, no como requisito institucional.

## Decisión de referencia

```text
Angular build
→ S3 (archivos estáticos)
→ CloudFront (HTTPS/CDN)
```

Si el laboratorio define otro hosting AWS, puede utilizarse siempre que produzca una URL estable y permita demostrar la misma arquitectura.

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

Preferir HTTPS para la SPA real.

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

## Estado institucional

Confirmado por material EV1:

```text
frontend desplegado y activo = requerido
backend EC2 = requerido
API Gateway = requerido
```

No se encontró en el material revisado una obligación explícita de **S3 + CloudFront** para el frontend. Por eso la guía no penaliza una alternativa AWS autorizada que cumpla el mismo resultado.
