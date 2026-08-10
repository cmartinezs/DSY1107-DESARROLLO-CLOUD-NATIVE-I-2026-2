# Semana 1 · Explorando la gestión de APIs

**Periodo:** 10 al 15 de agosto de 2026  
**Actividad:** 1.1 Explorando la gestión de APIs

## Objetivo de la semana

Comprender el rol de un API Manager dentro de una arquitectura cloud native y realizar una primera aproximación práctica a la publicación y administración de una API mediante un API Gateway.

## Avance esperado

### 1.1.1 Conociendo un API Manager

Al finalizar esta parte deberías poder distinguir, al menos, los conceptos de:

- API y endpoint.
- API Gateway.
- API Manager.
- consumidor y proveedor de una API.
- políticas aplicadas sobre una API.
- ciclo de vida básico de una API.

### 1.1.2 Creando nuestro primer API Manager

La actividad práctica permitirá reconocer el flujo general:

```text
Cliente → API Gateway → API / Backend
```

El objetivo inicial no es memorizar una interfaz de nube específica, sino comprender qué problema resuelve cada componente y qué ocurre con una petición desde que llega al gateway hasta que alcanza el servicio publicado.

### 1.1.3 Versionando APIs

Revisaremos por qué una API cambia con el tiempo y cómo evitar que una modificación rompa inmediatamente a sus consumidores.

Ejemplo conceptual:

```text
/api/v1/products
/api/v2/products
```

La versión forma parte del contrato que existe entre quienes ofrecen y quienes consumen una API.

### 1.1.4 Configurando CORS en nuestro API Gateway

Revisaremos qué ocurre cuando una aplicación web intenta consumir una API desde un origen diferente y por qué el navegador puede bloquear la solicitud.

Conceptos principales:

- origen (`scheme + host + port`),
- Same-Origin Policy,
- CORS,
- encabezado `Origin`,
- `Access-Control-Allow-Origin`,
- solicitudes preflight con `OPTIONS`.

## Antes de comenzar

Comprueba que tienes disponibles las herramientas indicadas durante la clase. Dependiendo de la actividad podremos utilizar navegador, terminal, Git, Postman y la plataforma cloud definida para el laboratorio.

## Evidencia mínima de aprendizaje

Al finalizar la semana deberías ser capaz de explicar con tus propias palabras:

1. qué diferencia existe entre una API, un API Gateway y un API Manager;
2. qué recorrido realiza una petición HTTP cuando existe un gateway;
3. por qué puede ser necesario mantener más de una versión de una API;
4. qué problema intenta resolver CORS;
5. qué configuración realizaste durante el laboratorio y qué resultado obtuviste.

> Conserva capturas, comandos relevantes, URLs de prueba y observaciones del laboratorio. Estas evidencias permiten reconstruir lo realizado y facilitan el diagnóstico cuando algo no funciona.

## Recursos

Los recursos institucionales de la actividad se encuentran disponibles en AVA. El material complementario y los ejemplos desarrollados durante las clases se incorporarán progresivamente a este repositorio.
