# Starter · Laboratorio API Gateway

Este directorio contiene el punto de partida del laboratorio.

## Qué viene preparado

- `gateway/`: Spring Cloud Gateway mínimo.
- `client/`: cliente web para comprobar CORS.
- `docs/evidencias.md`: plantilla de evidencias.

## Qué NO viene resuelto

El starter contiene solamente la ruta inicial `/api/v1/posts/**`.

Durante el laboratorio el grupo deberá configurar progresivamente:

1. pruebas HTTP mediante el gateway;
2. `/api/v2`;
3. headers de versión;
4. header transversal del gateway;
5. CORS;
6. documentación y evidencias.

No deben crear lógica Java ni un backend.

## Ejecutar gateway

Requiere JDK 21+ y Maven.

```bash
cd gateway
mvn spring-boot:run
```

El gateway queda en:

```text
http://localhost:8080
```

Prueba inicial:

```bash
curl -i http://localhost:8080/api/v1/posts/1
```

## Cliente web

El archivo `client/index.html` debe servirse mediante un servidor estático en:

```text
http://localhost:5500
```

Se utiliza únicamente para comprobar CORS desde navegador.

## Instrucciones completas

No trabajen solo desde este README. Sigan, en orden, la guía principal del laboratorio ubicada en:

```text
../README.md
```
