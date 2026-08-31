# 01 · Arquitectura y alcance mínimo

## Objetivo

La aplicación debe ser una solución **Full Stack real** y suficientemente completa para habilitar los escenarios técnicos evaluables en EV1.

## Libertad de dominio

El equipo decide libremente:

- nombre del proyecto;
- temática y problemática;
- diseño visual;
- reglas de negocio;
- funcionalidades adicionales;
- Angular o React para el frontend.

Ejemplos de dominio: reservas, biblioteca, inventario, eventos, música, videojuegos, mascotas, turismo, comercio, deporte o cualquier otro dominio coherente.

## Arquitectura mínima obligatoria

```text
Frontend SPA
    │
    │ HTTP / REST
    ▼
Backend Spring Boot
    │
    ▼
Persistencia
```

Frontend y backend deben ser componentes separados y comunicarse mediante HTTP.

## Complejidad funcional mínima

La solución debe manejar al menos **dos conceptos o entidades relacionadas**.

Ejemplos:

```text
Evento ─ Inscripción
Producto ─ Categoría
Libro ─ Préstamo
Reserva ─ Recurso
Pedido ─ Detalle
Curso ─ Actividad
```

No se exige una relación JPA compleja; se exige un dominio suficiente para evitar una aplicación trivial.

## La solución no será suficiente si consiste únicamente en

- una landing page;
- frontend estático;
- un formulario sin backend;
- datos mantenidos exclusivamente en variables, JSON o `localStorage` del frontend;
- un único endpoint tipo `/hola`;
- frontend y backend sin comunicación real;
- una aplicación sin operaciones diferenciables por permisos;
- una arquitectura que no permita incorporar autenticación, autorización o API Gateway.

## Criterio rector

La funcionalidad debe ser **suficiente para probar la arquitectura**. No se premia agregar decenas de funcionalidades si estas no aportan a demostrar los conceptos técnicos de la asignatura.