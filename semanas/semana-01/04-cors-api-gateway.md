# 1.1.4 · Configurando CORS en nuestro API Gateway

## Propósito

Comprender por qué un navegador puede bloquear una solicitud hacia una API alojada en otro origen y cómo se configura CORS para permitir únicamente los orígenes, métodos y headers necesarios.

## ¿Qué es un origen?

Para el navegador, un origen se determina por:

```text
scheme + host + port
```

Por ejemplo:

```text
https://app.ejemplo.cl
https://api.ejemplo.cl
```

son orígenes distintos porque cambia el host.

También:

```text
http://localhost:3000
http://localhost:5173
```

son orígenes distintos porque cambia el puerto.

## Same-Origin Policy

Los navegadores aplican una política llamada **Same-Origin Policy**. Su objetivo es limitar cómo un documento cargado desde un origen puede interactuar con recursos pertenecientes a otro origen.

Una aplicación frontend puede necesitar legítimamente llamar a una API distinta:

```text
Frontend
https://app.ejemplo.cl
      │
      │ fetch()
      ▼
API
https://api.ejemplo.cl
```

Para permitir determinados accesos entre orígenes se utiliza **CORS (Cross-Origin Resource Sharing)**.

## Qué hace CORS

CORS funciona mediante headers HTTP. El navegador envía información sobre el origen de la solicitud y el servidor o gateway responde indicando qué está permitido.

Header enviado por el navegador:

```http
Origin: https://app.ejemplo.cl
```

Respuesta posible:

```http
Access-Control-Allow-Origin: https://app.ejemplo.cl
```

El navegador evalúa esa respuesta antes de permitir que el código JavaScript acceda al resultado.

## CORS no es autenticación

Esta distinción es muy importante.

CORS **no impide que cualquier cliente HTTP del mundo llame a una API**. Es principalmente un mecanismo aplicado por navegadores.

Herramientas como:

- `curl`;
- Postman;
- servicios backend;
- scripts;

no dependen de la Same-Origin Policy de un navegador de la misma forma.

Por ello:

```text
CORS ≠ autenticación
CORS ≠ autorización
CORS ≠ firewall
```

La API sigue necesitando sus propios controles de seguridad cuando corresponda.

## Solicitudes simples y preflight

Algunas solicitudes cross-origin requieren que el navegador consulte primero qué está permitido.

Esta solicitud previa se denomina **preflight** y normalmente utiliza:

```http
OPTIONS /productos
```

Ejemplo conceptual:

```http
OPTIONS /productos
Origin: https://app.ejemplo.cl
Access-Control-Request-Method: POST
Access-Control-Request-Headers: Content-Type, Authorization
```

El servidor o gateway puede responder:

```http
Access-Control-Allow-Origin: https://app.ejemplo.cl
Access-Control-Allow-Methods: GET,POST,OPTIONS
Access-Control-Allow-Headers: Content-Type,Authorization
```

Si la política lo permite, el navegador realiza posteriormente la solicitud real.

## Headers frecuentes

### Access-Control-Allow-Origin

Indica qué origen puede acceder al recurso desde el navegador.

```http
Access-Control-Allow-Origin: https://app.ejemplo.cl
```

### Access-Control-Allow-Methods

```http
Access-Control-Allow-Methods: GET,POST,PUT,DELETE,OPTIONS
```

No habilites métodos que la aplicación no necesita.

### Access-Control-Allow-Headers

```http
Access-Control-Allow-Headers: Content-Type,Authorization
```

### Access-Control-Allow-Credentials

Puede ser necesario cuando existen credenciales que deban enviarse cross-origin.

Su uso debe evaluarse junto con una política explícita de orígenes.

## Evitar `*` por comodidad

Durante una demostración puede parecer sencillo utilizar:

```http
Access-Control-Allow-Origin: *
```

pero en un sistema real conviene permitir únicamente los orígenes necesarios.

La configuración debe representar la relación real entre consumidores y API.

## CORS en Amazon API Gateway

La experiencia de configuración depende del tipo de API utilizado en AWS.

### HTTP API

AWS dispone de configuración CORS integrada para HTTP APIs.

### REST API

En REST APIs pueden intervenir configuraciones de métodos, respuestas y la gestión de `OPTIONS`, dependiendo de cómo esté construida la API y su integración.

> No memorices únicamente una secuencia de botones. Primero identifica si estás trabajando con una **HTTP API** o una **REST API**, porque la consola y el comportamiento no son idénticos.

## Ejemplo de escenario

Frontend local:

```text
http://localhost:5173
```

API publicada:

```text
https://abc123.execute-api.us-east-1.amazonaws.com
```

Configuración esperada para desarrollo:

```text
Allowed origin:  http://localhost:5173
Allowed methods: GET, POST, OPTIONS
Allowed headers: Content-Type, Authorization
```

La configuración exacta debe ajustarse a lo que realmente consume la aplicación.

## Cómo diagnosticar un problema CORS

Cuando una aplicación web informa un error CORS:

1. abre las herramientas de desarrollo del navegador;
2. revisa la pestaña **Network**;
3. identifica la solicitud afectada;
4. busca si existió una solicitud `OPTIONS`;
5. revisa `Origin`;
6. revisa los headers `Access-Control-Allow-*` de la respuesta;
7. confirma que el backend realmente respondió correctamente;
8. evita asumir que todos los errores de red son problemas de CORS.

Un `500`, un endpoint incorrecto o una autorización fallida pueden coexistir con mensajes relacionados con CORS y confundir el diagnóstico.

## Evidencia sugerida

Registra una prueba indicando:

- origen del frontend;
- endpoint de la API;
- método solicitado;
- si ocurrió preflight;
- configuración CORS aplicada;
- status code;
- headers relevantes;
- resultado antes y después de la configuración.

## Preguntas de comprobación

1. ¿Qué elementos forman un origen?
2. ¿Quién aplica principalmente la Same-Origin Policy?
3. ¿Por qué Postman puede funcionar mientras el navegador presenta un error CORS?
4. ¿Para qué sirve una solicitud `OPTIONS` de preflight?
5. ¿Por qué CORS no reemplaza autenticación ni autorización?
6. ¿Por qué debemos identificar si usamos HTTP API o REST API en AWS?

## Material institucional

Disponible en la carpeta pública de la Semana 1:

- **1.1.4 Configurando CORS en nuestro API GATEWAY.docx**

Consulta el índice de la semana para acceder a la carpeta.