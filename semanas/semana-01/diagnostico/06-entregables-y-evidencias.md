# 6. Entregables y evidencias

## Entrega grupal

Cada equipo deberá entregar un repositorio Git con la solución completa.

La entrega debe permitir que el docente pueda comprender, ejecutar y revisar el proyecto sin depender de explicaciones externas.

---

## Estructura mínima sugerida

```text
proyecto/
├── frontend/
├── backend/
├── docs/
│   ├── requerimientos.md
│   ├── arquitectura.md
│   └── decisiones.md
├── compose.yaml
├── .env.example
└── README.md
```

La estructura puede variar según la alternativa frontend escogida, pero debe mantener una separación clara de responsabilidades.

---

## README obligatorio

El README del equipo debe indicar como mínimo:

1. Nombre del proyecto.
2. Integrantes.
3. Problema que resuelve.
4. Tecnologías utilizadas.
5. Arquitectura resumida.
6. Requisitos para ejecutar.
7. Variables de entorno necesarias.
8. Comando para levantar la solución.
9. URLs y puertos esperados.
10. Cómo verificar rápidamente el CRUD.
11. Funcionalidades adicionales implementadas.
12. Limitaciones conocidas.

---

## Evidencias técnicas mínimas

La revisión considerará evidencia observable de:

- frontend funcionando;
- API Spring Boot funcionando;
- persistencia real;
- CRUD completo;
- dos funcionalidades adicionales;
- ejecución mediante Docker;
- documentación de requerimientos;
- decisiones arquitectónicas;
- historial Git del equipo.

No es obligatorio preparar una presentación formal, salvo que el docente lo solicite durante la clase.

---

## Uso de Git

El repositorio debe mostrar trabajo colaborativo real.

Se espera observar:

- commits identificables;
- mensajes de commit comprensibles;
- participación de los integrantes;
- ausencia de secretos versionados;
- código fuente y documentación alineados.

No se exige una estrategia Git específica para este diagnóstico, pero el equipo debe poder explicar cómo coordinó el trabajo.

---

## Revisión formativa

La actividad **no tiene calificación**, pero será revisada obligatoriamente.

El diagnóstico observará principalmente cuatro dimensiones:

### 1. Comprensión funcional

¿El equipo entendió el problema y definió correctamente qué debía construir?

### 2. Desarrollo fullstack

¿Frontend, backend y persistencia están realmente integrados?

### 3. Razonamiento técnico

¿El equipo puede justificar decisiones y explicar su arquitectura?

### 4. Base Cloud Native / DevOps

¿El equipo puede construir y ejecutar la solución de manera reproducible mediante Docker y explicar la relación entre sus componentes?

---

## Criterio de finalización

La actividad se considera técnicamente completa cuando una persona que no participó en el desarrollo puede:

1. clonar el repositorio;
2. seguir el README;
3. ejecutar la solución;
4. crear, consultar, modificar y eliminar incidencias;
5. utilizar las funcionalidades adicionales;
6. comprobar que los datos persisten;
7. comprender la arquitectura y las principales decisiones leyendo la documentación.

Si esto no es posible, el equipo debe registrar la limitación encontrada y explicar qué faltó para resolverla.
