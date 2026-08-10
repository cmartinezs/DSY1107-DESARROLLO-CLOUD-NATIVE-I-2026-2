# 2. Desafío funcional — Mini Help Desk

## Escenario

El equipo desarrollará una aplicación web para registrar y gestionar incidencias técnicas internas de una organización.

Cada incidencia deberá representar un problema o solicitud realista, por ejemplo:

- usuario sin acceso a una aplicación;
- computador que no inicia;
- error en una plataforma interna;
- solicitud de instalación de software;
- problema de conectividad;
- solicitud de revisión de un servicio.

---

## Entidad principal sugerida: Incidencia

Como mínimo, cada incidencia debe manejar:

- identificador;
- título;
- descripción;
- categoría;
- prioridad;
- estado;
- fecha de creación.

Campos adicionales son permitidos si el equipo puede justificar su utilidad.

### Valores sugeridos

**Prioridad:**

- BAJA
- MEDIA
- ALTA

**Estado:**

- ABIERTA
- EN_PROGRESO
- RESUELTA

---

## Funcionalidades mínimas obligatorias

### CRUD

La aplicación debe permitir:

1. Crear una incidencia.
2. Listar todas las incidencias.
3. Ver el detalle de una incidencia.
4. Editar una incidencia existente.
5. Eliminar una incidencia.

### Plus obligatorio

Además del CRUD, el equipo debe implementar **al menos dos** de las siguientes capacidades:

- búsqueda por texto;
- filtro por estado;
- filtro por prioridad;
- cambio rápido de estado;
- contador de incidencias por estado;
- dashboard sencillo;
- orden por fecha o prioridad;
- historial simple de cambios de estado.

Al menos una de las capacidades seleccionadas debe requerir interacción entre frontend y backend; no puede ser solamente una transformación visual estática en el navegador.

---

## API mínima esperada

La solución debe exponer una API HTTP coherente. Como referencia:

```text
GET    /api/incidencias
GET    /api/incidencias/:id
POST   /api/incidencias
PUT    /api/incidencias/:id
DELETE /api/incidencias/:id
```

Los nombres exactos pueden variar, pero el equipo debe mantener consistencia semántica y documentar las decisiones tomadas.

Para filtros puede utilizarse, por ejemplo:

```text
GET /api/incidencias?estado=ABIERTA
GET /api/incidencias?prioridad=ALTA
GET /api/incidencias?search=impresora
```

---

## Reglas mínimas

La solución debe implementar al menos las siguientes reglas:

- título obligatorio;
- descripción obligatoria;
- prioridad válida;
- estado válido;
- una incidencia nueva comienza en un estado definido y documentado;
- no se deben aceptar identificadores inexistentes como si fueran recursos válidos;
- los errores de validación deben producir respuestas comprensibles para el frontend.

El equipo puede agregar otras reglas si las considera necesarias.

---

## Interfaz mínima

La aplicación web debe incluir al menos:

- vista principal con listado;
- formulario de creación;
- acción de edición;
- acción de eliminación;
- mecanismo para utilizar las capacidades adicionales elegidas;
- mensajes básicos de éxito o error.

No se evaluará diseño gráfico avanzado. Se evaluará que la interfaz sea utilizable y que permita demostrar correctamente la integración fullstack.
