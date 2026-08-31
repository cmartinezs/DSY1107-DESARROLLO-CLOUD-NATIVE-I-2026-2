# 03 · Requerimientos mínimos de backend y datos

## Tecnología

El backend debe implementarse con **Java + Spring Boot** y exponer una API REST consumible desde el frontend.

## Recursos de dominio

La API debe representar recursos coherentes con el dominio elegido por el equipo.

Ejemplos:

```text
/api/libros
/api/prestamos
```

```text
/api/eventos
/api/inscripciones
```

Los nombres son libres; lo obligatorio es que exista un dominio suficiente para demostrar operaciones y permisos diferentes.

## Endpoints mínimos

La API debe incluir, como mínimo:

1. **Una operación pública**, que pueda ejecutarse sin autenticación.
2. **Una operación de lectura protegible**, destinada a usuarios autenticados.
3. **Una operación de modificación protegible**, que cree, actualice, elimine o ejecute una acción y pueda requerir permisos adicionales.

Ejemplo conceptual:

```text
GET    /api/public/...
GET    /api/...
POST   /api/...
```

No es obligatorio utilizar exactamente esas rutas o métodos.

## HTTP

Los métodos y códigos HTTP deben utilizarse de forma coherente con la operación.

Ejemplos habituales:

- `GET` para consultar;
- `POST` para crear o ejecutar una operación;
- `PUT` o `PATCH` para actualizar;
- `DELETE` para eliminar.

La API debe poder producir respuestas apropiadas como `200`, `201`, `204`, `400`, `401`, `403` y `404` cuando corresponda.

## Persistencia

La información debe persistirse fuera del frontend y sobrevivir al ciclo normal de una petición.

La tecnología de persistencia concreta se ajustará a las instrucciones del curso.

Se espera una separación razonable de responsabilidades:

```text
Controller
    ↓
Service
    ↓
Repository / acceso a datos
```

No se exige complejidad arquitectónica innecesaria; sí una estructura que permita evolucionar y proteger adecuadamente la API.

## CORS

Cuando frontend y backend se ejecuten en orígenes distintos, CORS debe configurarse explícitamente y de manera controlada. No se debe resolver el problema desactivando indiscriminadamente controles de seguridad.