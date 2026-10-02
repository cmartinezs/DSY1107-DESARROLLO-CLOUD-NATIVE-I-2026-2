# Capacidad · Educación y matrícula

## Contexto

Un estudiante intenta inscribir una asignatura.

## Comunicación síncrona posible

```text
ms-inscripciones -> ms-prerrequisitos
```

Antes de aceptar la inscripción se debe validar:

- prerrequisitos;
- cupos;
- restricciones académicas.

## Comunicaciones asíncronas posibles

Evento:

```text
AsignaturaInscrita
```

Consumers posibles:

- `ms-notificaciones`: confirma al estudiante;
- `ms-ava`: provisiona acceso al curso;
- `ms-analytics`: actualiza indicadores;
- `ms-calendario`: agrega sesiones a una agenda.

## Pregunta de diseño

¿El estudiante debería esperar la creación de su espacio en el AVA antes de recibir la confirmación de inscripción?
