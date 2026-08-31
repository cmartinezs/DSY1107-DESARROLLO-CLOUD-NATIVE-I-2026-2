# 02 · Requerimientos mínimos de frontend

## Tecnología

El frontend debe desarrollarse con una de estas alternativas:

- **Angular**, o
- **React**.

Debe comportarse como una aplicación web SPA y consumir el backend mediante HTTP.

## Navegación mínima

Debe existir navegación entre vistas, rutas o secciones equivalentes. Como mínimo, la aplicación debe ofrecer:

- una vista pública o de inicio;
- una vista que muestre información obtenida desde el backend;
- una vista o flujo que permita modificar información;
- una zona que pueda protegerse posteriormente mediante autenticación/autorización.

## Consumo real de API

La versión evaluable debe obtener y modificar información mediante llamadas reales al backend.

Los mocks son válidos durante el desarrollo, pero no como implementación definitiva.

```text
Frontend → HTTP → Backend
```

## Operaciones mínimas

El frontend debe permitir al menos:

- consultar una colección de recursos;
- visualizar información de un recurso;
- crear, actualizar o ejecutar alguna operación que modifique datos.

Un CRUD completo es recomendable cuando tenga sentido, pero no es obligatorio por sí mismo.

## Manejo de estados y respuestas

La interfaz debe poder distinguir y comunicar al usuario situaciones como:

- operación exitosa;
- error de solicitud o validación;
- recurso inexistente cuando corresponda;
- usuario no autenticado;
- usuario autenticado sin permisos suficientes.

Esto debe permitir observar posteriormente respuestas `2xx`, `4xx`, `401` y `403`.

## Configuración

La URL del backend y los valores dependientes del ambiente no deben quedar dispersos ni fuertemente acoplados al código.

La aplicación debe poder cambiar posteriormente desde un backend local hacia un API Manager/Gateway mediante configuración.