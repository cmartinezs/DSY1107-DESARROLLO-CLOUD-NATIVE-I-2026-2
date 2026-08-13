# Semana 1 · Explorando la gestión de APIs

**Periodo:** 10 al 15 de agosto de 2026  
**Actividad institucional:** 1.1 Explorando la gestión de APIs

← [Volver al índice de semanas](../README.md)

## Objetivo de la semana

Comprender el rol de la gestión de APIs dentro de una arquitectura cloud native y aplicar los conceptos iniciales mediante un gateway: publicación/enrutamiento de una API, versionamiento y configuración de CORS.

> Durante la primera semana utilizaremos **Spring Cloud Gateway como laboratorio local puente** para practicar los conceptos sin depender todavía de un laboratorio AWS operativo. Posteriormente los mismos conceptos se trasladarán a Amazon API Gateway.

## Distribución diaria · sección DSY1107-002D

La sección 002D dispone de **8 bloques pedagógicos**, distribuidos en **4 + 2 + 2**. El contenido se reparte según el tiempo real de cada jornada y el avance efectivo del curso.

### Martes 11 de agosto · 4 bloques · realizado

1. **Inicio de la asignatura:** propósito general, tecnologías, herramientas y dinámica del semestre.
2. **Evaluaciones y reglas:** fechas, modalidad de las evaluaciones y reglas generales de trabajo.
3. **Diagnóstico y trabajo transversal:** revisión general del trabajo del semestre, organización y diagnóstico técnico inicial.
4. **1.1.1 · Conociendo un API Manager:** primera PPT revisada; API, API Gateway, API Management, responsabilidades y flujo general de una solicitud.

**Estado:** realizado. El diagnóstico puede quedar como actividad de continuidad fuera del horario de clase.

### Jueves 13 de agosto · 2 bloques · clase actual

**Bloque 1 — 1.1.2 · Creando nuestro primer API Manager**

- recuperar brevemente la diferencia API / Gateway / API Management;
- representar el flujo `cliente → gateway → backend → gateway → cliente`;
- reconocer ruta, predicate, integración/destino y filtros;
- contrastar un servicio administrado como Amazon API Gateway con un gateway local;
- presentar el backend de prueba y el gateway del laboratorio sin introducir programación reactiva como contenido de la asignatura.

**Bloque 2 — Inicio del laboratorio Spring Cloud Gateway**

- levantar backend y gateway;
- revisar dónde se configura cada elemento del gateway;
- crear/probar la primera ruta `/api/v1/...`;
- verificar request, status, headers y body con Postman/curl;
- dejar evidencia reproducible de la prueba.

**Meta de salida:** gateway v1 funcionando y estudiantes capaces de explicar qué hizo el gateway en la petición.

### Viernes 14 de agosto · 2 bloques

**Bloque 1 — 1.1.3 · Versionando APIs**

- contrato y compatibilidad;
- `/v1` y `/v2` como estrategia visible de versionamiento;
- diferencia entre versionamiento de contrato y versionamiento semántico del software;
- configuración y prueba de una segunda ruta/version;
- header de versión como evidencia cuando corresponda.

**Bloque 2 — 1.1.4 · CORS + cierre del laboratorio**

- Same-Origin Policy y CORS;
- preflight `OPTIONS`;
- `allowedOrigins`, métodos y headers;
- ubicación concreta de la configuración global en el proyecto;
- comprobación práctica de CORS;
- documentación final: arquitectura, tabla de pruebas y conclusiones.

**Meta de salida:** gateway local con v1/v2, CORS verificable y evidencia técnica suficiente para explicar el recorrido de una petición.

> Los resúmenes de avance efectivo se publican **por sección**. Esta planificación no debe interpretarse como evidencia de que otra sección avanzó exactamente al mismo ritmo.

## Actividad diagnóstica complementaria

Antes de avanzar con los contenidos cloud native, la semana incorpora un **diagnóstico fullstack y Docker**, de carácter **formativo, obligatorio y sin calificación**, para verificar las competencias previas que la asignatura da por adquiridas.

La actividad considera:

- desarrollo grupal de una aplicación fullstack pequeña;
- levantamiento de requerimientos;
- decisiones arquitectónicas justificadas;
- backend con Java + Spring Boot;
- frontend con React + Vite o Next.js;
- persistencia con MySQL o PostgreSQL;
- ejecución con Docker y Docker Compose;
- cuestionario individual y privado de 10 preguntas.

Consulta el desafío completo en [`diagnostico/`](diagnostico/README.md).

## Contenidos de la semana

El material del repositorio consolida los contenidos institucionales y agrega las aclaraciones técnicas necesarias para estudiarlos de forma autónoma.

1. [**1.1.1 · Conociendo un API Manager**](01-api-manager.md)  
   API, API Gateway, API Management, responsabilidades y flujo de una solicitud.

2. [**1.1.2 · Creando nuestro primer API Manager**](02-primer-api-manager.md)  
   Primera aproximación práctica a un gateway, rutas, integraciones, publicación y prueba. El laboratorio de esta semana usa Spring Cloud Gateway como equivalente local para practicar estos conceptos.

3. [**1.1.3 · Versionando APIs**](03-versionamiento-api.md)  
   Contratos, compatibilidad, estrategias de versionamiento, deprecación y rol del gateway.

4. [**1.1.4 · Configurando CORS en nuestro API Gateway**](04-cors-api-gateway.md)  
   Same-Origin Policy, CORS, preflight, headers y diagnóstico en navegador.

## Material original de la asignatura

Los archivos originales utilizados durante esta semana se mantienen disponibles en la biblioteca pública del curso:

📚 [**Material público del curso**](https://drive.google.com/drive/folders/1UOZMZcEbtfKFq4ygWKj3Yi8VEHkW7hx1?usp=sharing)  
📁 [**Semana 01 · 10 al 15 de agosto**](https://drive.google.com/drive/folders/1G_Fi_4wk4BO2JTBw49Jcfi11hX1ApJO6?usp=sharing)

La carpeta contiene:

- `1.1.1 Conociendo un API Manager.pptx`
- `1.1.2 Tutorial Creando Nuestro Primer API Manager.docx`
- `1.1.3 Versionando APIs.pptx`
- `1.1.4 Configurando CORS en nuestro API GATEWAY.docx`
- `Manual AWS - Estudiante.pdf`

> Los archivos institucionales se conservan como referencia. Para estudiar la materia se recomienda revisar también el contenido consolidado del repositorio, ya que incorpora aclaraciones, contexto adicional y ajustes cuando la terminología o las instrucciones requieren precisión.

## Aclaraciones incorporadas esta semana

### API Manager y API Gateway

En algunos materiales institucionales los términos **API Manager** y **API Gateway** aparecen utilizados de manera muy cercana. En este curso distinguiremos:

- **API Gateway:** componente o servicio que recibe y enruta solicitudes, y puede aplicar políticas sobre ellas.
- **API Management:** disciplina/capacidad más amplia que comprende publicación, seguridad, versionamiento, documentación, observabilidad y ciclo de vida de las APIs.
- **Amazon API Gateway:** servicio concreto de AWS que utilizaremos cuando el entorno AWS esté disponible.

### Spring Cloud Gateway durante el laboratorio

Spring Cloud Gateway se utiliza esta semana como **gateway local**, no como una unidad de programación reactiva. El foco pedagógico está en rutas, integración, filtros/políticas, versionamiento, CORS y recorrido HTTP. Los estudiantes no necesitan estudiar WebFlux ni programación reactiva para completar el laboratorio.

### Seguridad

Crear un API Gateway no vuelve automáticamente segura una API. La seguridad depende de las configuraciones aplicadas: autenticación, autorización, políticas, TLS, validación, límites de tráfico y otras medidas según el caso.

### Versionamiento

El versionamiento semántico (`MAJOR.MINOR.PATCH`) es útil como convención, pero no es equivalente a la estrategia mediante la cual un consumidor selecciona una API (`/v1`, `/v2`, headers, etc.).

### CORS

CORS es un mecanismo relacionado principalmente con navegadores y la Same-Origin Policy. **No reemplaza autenticación, autorización ni controles de acceso del backend.**

## Evidencia mínima de aprendizaje

Al finalizar la semana deberías poder explicar con tus propias palabras:

1. qué diferencia existe entre una API, un API Gateway y API Management;
2. qué recorrido realiza una petición HTTP cuando existe un gateway;
3. cómo se configura y prueba una ruta mediante un gateway;
4. por qué puede ser necesario mantener más de una versión de una API;
5. qué problema resuelve CORS y qué no resuelve;
6. qué configuración realizaste durante el laboratorio y qué resultado obtuviste.

Conserva evidencia reproducible del laboratorio:

- URL o endpoint probado;
- método HTTP;
- request relevante;
- status code;
- response;
- headers importantes;
- configuración realizada;
- breve explicación del resultado.

## Trabajar con el repositorio

Para obtener todo el material y ejemplos de forma local:

```bash
git clone https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2.git
cd DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2
```

Si ya tienes el repositorio:

```bash
git pull
```
