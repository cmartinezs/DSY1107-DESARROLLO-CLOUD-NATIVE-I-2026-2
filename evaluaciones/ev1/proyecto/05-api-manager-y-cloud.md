# 05 · Preparación para API Manager y cloud

## Objetivo

El proyecto debe quedar preparado para incorporar la configuración cloud evaluable sin rehacer la arquitectura base.

## API Manager / Gateway

El frontend no debe depender rígidamente de una URL fija del backend.

Se recomienda manejar una configuración equivalente a:

```text
API_BASE_URL
```

para permitir una evolución como:

```text
Frontend
   │
   ▼
API Manager / Gateway
   │
   ▼
Backend Spring Boot
```

El gateway podrá asumir responsabilidades técnicas trabajadas en la asignatura, por ejemplo:

- routing;
- exposición de la API;
- políticas;
- validaciones;
- control de acceso;
- observabilidad;
- CORS;
- otras capacidades definidas en EV1.

El backend conserva sus propias responsabilidades de seguridad; pasar por un gateway no reemplaza la autorización del recurso.

## Configuración por ambiente

Los valores dependientes del entorno deben poder configurarse sin modificar lógica de negocio.

Ejemplos:

```text
API URL
Client ID
Tenant / Authority
Scopes
Issuer
Audience
```

No todos estos valores deben existir desde el primer día; la arquitectura debe permitir incorporarlos de manera limpia cuando corresponda.

## Frontera con AVA

Los ejercicios y laboratorios cloud oficiales pertenecen al **AVA** y siguen las instrucciones institucionales del curso.

Este repositorio no redefine esos ejercicios ni crea una segunda versión de ellos.

La webapp del equipo funciona como la base técnica propia sobre la cual se aplican, configuran y demuestran posteriormente los aprendizajes cloud exigidos por EV1.