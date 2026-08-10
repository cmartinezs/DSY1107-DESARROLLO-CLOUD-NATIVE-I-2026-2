# 1.1.2 · Creando nuestro primer API Manager

## Propósito

Realizar una primera publicación de una API utilizando **Amazon API Gateway**, comprendiendo qué se configura en cada paso y evitando que el laboratorio se reduzca a seguir clics de memoria.

## Antes de comenzar

Verifica que puedes acceder al entorno AWS indicado para la asignatura. Si utilizas AWS Academy, ingresa primero a la sesión de laboratorio correspondiente.

También es recomendable disponer de:

- navegador actualizado;
- Postman o herramienta equivalente;
- terminal;
- una URL/backend que pueda utilizarse como integración cuando la actividad lo requiera.

## Aclaración de terminología

El material institucional denomina esta actividad **“Creando Nuestro Primer API Manager”** y en algunos pasos habla de “crear una API Manager”.

En AWS, el servicio que utilizaremos se llama **Amazon API Gateway**. Por tanto, técnicamente crearemos/configuraremos una **API dentro de API Gateway**.

Asimismo, un gateway no “securitiza” por el solo hecho de existir. La seguridad depende de las políticas y mecanismos que se configuren: autenticación, autorización, restricciones de acceso, TLS, validaciones, throttling, etc.

## Arquitectura mínima

```text
Cliente
  │
  │ HTTP request
  ▼
Amazon API Gateway
  │
  │ integración
  ▼
Backend
```

El objetivo de esta actividad es reconocer el recorrido completo de la solicitud.

## Paso 1 · Acceder a API Gateway

Desde la consola AWS:

1. inicia la sesión de laboratorio;
2. abre la consola de AWS;
3. busca **API Gateway**;
4. ingresa al servicio.

Antes de crear recursos, observa los tipos de API disponibles. AWS puede ofrecer alternativas como HTTP API, REST API o WebSocket API.

> No todos los tipos de API poseen exactamente las mismas opciones ni la misma forma de configurar características como CORS. Es importante identificar qué tipo se utiliza en el laboratorio.

## Paso 2 · Crear la API

Selecciona **Crear API** y utiliza el tipo indicado por la actividad.

Registra al menos:

- nombre de la API;
- tipo de API;
- región;
- fecha de creación.

Estos datos forman parte de una evidencia técnica reproducible.

## Paso 3 · Definir una ruta o recurso

Una API necesita saber qué solicitudes debe aceptar.

Ejemplo:

```http
GET /productos
```

Una ruta combina al menos:

```text
Método HTTP + path
```

Otros ejemplos:

```http
GET    /productos
GET    /productos/{id}
POST   /productos
DELETE /productos/{id}
```

## Paso 4 · Configurar la integración

El gateway necesita saber qué componente procesará la solicitud.

Según la actividad, una integración podría apuntar a:

- una función Lambda;
- un servicio HTTP;
- un backend desplegado en infraestructura cloud;
- otro recurso soportado por AWS.

Conceptualmente:

```text
Ruta del Gateway → Integración → Backend
```

## Paso 5 · Publicar o desplegar

Dependiendo del tipo de API utilizado, puede ser necesario crear o asociar un **stage**.

Ejemplos conceptuales:

```text
dev
qa
prod
```

Un stage representa un contexto de publicación y no debe confundirse automáticamente con la versión funcional de una API.

## Paso 6 · Probar el endpoint

Una vez disponible la URL pública o invoke URL, realiza una prueba.

Ejemplo:

```bash
curl https://<id>.execute-api.<region>.amazonaws.com/productos
```

O en Postman:

```text
Method: GET
URL: <invoke-url>/productos
```

Registra:

- URL utilizada;
- método HTTP;
- status code;
- response body;
- headers relevantes.

## ¿Qué ocurrió realmente?

Cuando realizaste la prueba, el flujo fue aproximadamente:

```text
Postman/Navegador
      │
      ▼
API Gateway
      │
      ▼
Integración configurada
      │
      ▼
Backend
      │
      ▼
API Gateway
      │
      ▼
Cliente
```

El valor pedagógico de la actividad está en poder explicar este recorrido, no únicamente en obtener una respuesta `200`.

## Errores frecuentes

### 403 / acceso denegado

Revisa autorización, URL, stage y políticas configuradas.

### 404 / ruta inexistente

Comprueba el path y la ruta definida en API Gateway.

### 5xx

Puede existir un problema en la integración o en el backend. Un error del gateway no significa necesariamente que el gateway sea la causa original.

### La consola se ve distinta al tutorial

Las consolas cloud evolucionan. Prioriza comprender los recursos y conceptos en lugar de depender de la posición exacta de un botón.

## Evidencia mínima sugerida

Conserva:

1. nombre y tipo de API creada;
2. ruta configurada;
3. integración utilizada;
4. URL de invocación;
5. request de prueba;
6. status code y response;
7. breve explicación del flujo.

Una captura aislada es menos útil que una evidencia que permita repetir el procedimiento.

## Limpieza de recursos

Al finalizar un laboratorio cloud, identifica qué recursos fueron creados y cuáles deben mantenerse. Cuando un recurso ya no sea necesario, elimínalo según las indicaciones de la asignatura.

Esto ayuda a evitar configuraciones olvidadas y consumo innecesario.

## Material institucional

Disponible en la carpeta pública de la Semana 1:

- **1.1.2 Tutorial Creando Nuestro Primer API Manager.docx**
- **Manual AWS - Estudiante.pdf**

Consulta el índice de la semana para acceder a la carpeta.