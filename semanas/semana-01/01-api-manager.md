# 1.1.1 · Conociendo un API Manager

## Propósito

Comprender por qué, en una arquitectura cloud native, normalmente no se expone cada servicio backend directamente a todos sus consumidores y qué responsabilidades puede asumir una capa de gestión de APIs.

## API, API Gateway y API Management

Estos conceptos están relacionados, pero no significan exactamente lo mismo.

### API

Una **API** define una forma de interacción entre sistemas. En una API HTTP normalmente encontramos:

- una URL o endpoint;
- un método HTTP (`GET`, `POST`, `PUT`, `DELETE`, etc.);
- headers;
- parámetros;
- un body opcional;
- una respuesta con status code y datos.

Ejemplo:

```http
GET /productos/42
```

### API Gateway

Un **API Gateway** se ubica delante de uno o más servicios y actúa como punto de entrada para los consumidores.

```text
Cliente
   │
   ▼
API Gateway
   │
   ├──► Servicio de productos
   ├──► Servicio de pedidos
   └──► Servicio de clientes
```

Dependiendo de la plataforma, puede encargarse de funciones como:

- enrutar solicitudes;
- aplicar autenticación y autorización;
- limitar tráfico;
- transformar requests o responses;
- recopilar métricas y logs;
- aplicar políticas;
- exponer distintas versiones de una API.

### API Management

**API Management** es un concepto más amplio que el gateway. Incluye el ciclo de vida de una API: diseño, publicación, seguridad, versionamiento, documentación, observabilidad, control de consumo y retiro.

Algunas plataformas agrupan estas capacidades bajo productos llamados **API Manager**.

> En las actividades institucionales de esta asignatura se utiliza frecuentemente el término *API Manager*. Para los laboratorios en AWS trabajaremos concretamente con **Amazon API Gateway**. Por eso conviene distinguir entre la capacidad general de gestión de APIs y el servicio específico utilizado para implementarla.

## ¿Qué problema resuelve un gateway?

Supongamos que una aplicación tiene tres servicios backend:

```text
Frontend ──► Products Service
Frontend ──► Orders Service
Frontend ──► Customers Service
```

Si el frontend debe conocer la dirección de cada servicio, aumenta el acoplamiento y se vuelve más difícil centralizar políticas comunes.

Con un gateway:

```text
Frontend ──► API Gateway ──► Products Service
                        ├──► Orders Service
                        └──► Customers Service
```

El cliente conoce un punto de entrada y el gateway decide cómo enrutar cada solicitud.

## Flujo simplificado de una solicitud

```text
1. Cliente envía request
2. Gateway recibe request
3. Se aplican políticas/configuraciones
4. Gateway determina el destino
5. Backend procesa la solicitud
6. Backend responde al gateway
7. Gateway devuelve la respuesta al cliente
```

Ejemplo:

```http
GET https://api.ejemplo.cl/v1/productos
```

El consumidor no necesita conocer dónde está físicamente desplegado el servicio de productos.

## Casos de uso frecuentes

Un API Gateway puede ser útil para:

- publicar servicios internos de forma controlada;
- entregar una entrada común para varios microservicios;
- implementar autenticación antes de llegar al backend;
- aplicar rate limiting o cuotas;
- separar APIs públicas y privadas;
- enrutar distintas versiones;
- centralizar observabilidad del tráfico;
- facilitar migraciones de servicios sin cambiar inmediatamente al consumidor.

## Un gateway no reemplaza al backend

El gateway no debería concentrar la lógica principal del negocio.

```text
Gateway  → políticas, seguridad, routing, exposición
Backend  → reglas y lógica de negocio
```

Un gateway demasiado cargado de reglas de negocio puede convertirse en un nuevo punto de acoplamiento difícil de mantener.

## Conceptos que debes poder explicar

Al terminar esta sección deberías poder responder:

1. ¿Qué diferencia existe entre una API y un API Gateway?
2. ¿Qué diferencia existe entre API Gateway y API Management?
3. ¿Por qué un consumidor podría preferir comunicarse con un gateway y no directamente con cada microservicio?
4. ¿Qué responsabilidades son apropiadas para el gateway?
5. ¿Qué responsabilidades deberían continuar en el backend?

## Evidencia sugerida

Dibuja una arquitectura sencilla que incluya:

- un cliente;
- un API Gateway;
- al menos dos servicios backend;
- una ruta distinta hacia cada servicio.

Luego explica, en pocas líneas, qué ocurre cuando el cliente realiza una solicitud.

## Material institucional

El archivo original de esta actividad se encuentra disponible en la carpeta pública de la Semana 1 en Google Drive:

- **1.1.1 Conociendo un API Manager.pptx**

Consulta el índice de la semana para acceder a la carpeta.