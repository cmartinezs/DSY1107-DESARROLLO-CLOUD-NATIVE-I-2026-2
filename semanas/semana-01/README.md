# Semana 1 · Explorando la gestión de APIs

**Periodo:** 10 al 15 de agosto de 2026  
**Actividad institucional:** 1.1 Explorando la gestión de APIs

← [Volver al índice de semanas](../README.md)

## Objetivo de la semana

Comprender el rol de la gestión de APIs dentro de una arquitectura cloud native y aplicar los conceptos iniciales mediante Amazon API Gateway: publicación de una API, versionamiento y configuración de CORS.

## Contenidos de la semana

El material del repositorio consolida los contenidos institucionales y agrega las aclaraciones técnicas necesarias para estudiarlos de forma autónoma.

1. [**1.1.1 · Conociendo un API Manager**](01-api-manager.md)  
   API, API Gateway, API Management, responsabilidades y flujo de una solicitud.

2. [**1.1.2 · Creando nuestro primer API Manager**](02-primer-api-manager.md)  
   Primera aproximación práctica a Amazon API Gateway, rutas, integraciones, publicación y prueba.

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
- **Amazon API Gateway:** servicio concreto de AWS utilizado en los laboratorios.

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
3. cómo se publica y prueba una ruta mediante API Gateway;
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
