# Diagnóstico de problemas CORS

Un mensaje CORS en consola no demuestra automáticamente que la causa raíz sea CORS.

## Secuencia recomendada

1. abrir DevTools → Network;
2. identificar la petición real;
3. comprobar si hubo `OPTIONS`;
4. revisar `Origin` enviado;
5. revisar `Access-Control-Allow-Origin`;
6. comprobar métodos y headers permitidos;
7. revisar el status del preflight;
8. revisar el status de la petición real si llegó a ejecutarse.

## Casos típicos

### Postman funciona, navegador no

Fuerte señal de que debemos revisar Same-Origin Policy/CORS.

### `OPTIONS` falla

La petición real puede no ejecutarse.

### API devuelve 500

Primero existe un error del backend. Configurar CORS indiscriminadamente no arregla la causa raíz.

### `Authorization` no permitido

Si el frontend envía ese header, la política CORS debe permitirlo cuando corresponda.

## Anti-patrón

```text
“Hay CORS error → poner * en todo”
```

Eso oculta el diagnóstico y puede ampliar innecesariamente la política. Configura únicamente orígenes, métodos y headers que la aplicación necesita.