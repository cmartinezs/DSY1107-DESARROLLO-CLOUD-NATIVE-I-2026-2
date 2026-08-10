# 3. Levantamiento de requerimientos

Antes de implementar, el equipo debe realizar un levantamiento breve pero explícito de requerimientos.

El objetivo es diagnosticar si el equipo puede pasar desde una necesidad de negocio a una solución técnica sin comenzar directamente escribiendo código.

---

## 3.1 Problema

Redacten entre **1 y 3 párrafos** explicando:

- qué problema resuelve la aplicación;
- quién tiene ese problema;
- qué ocurre actualmente si no existe la solución;
- qué resultado debería producir la aplicación.

No describan tecnologías en esta sección.

---

## 3.2 Actores

Identifiquen los actores principales.

Para este diagnóstico puede existir un solo actor principal, por ejemplo:

```text
Operador de soporte
```

Para cada actor indiquen brevemente:

- qué necesita hacer;
- qué información utiliza;
- qué resultado espera.

---

## 3.3 Requerimientos funcionales

Definan al menos **8 requerimientos funcionales** numerados.

Formato sugerido:

```text
RF-01 — Registrar incidencia
El sistema debe permitir registrar una incidencia indicando título,
descripción, categoría y prioridad.
```

Los requerimientos deben cubrir como mínimo:

- creación;
- consulta/listado;
- detalle;
- actualización;
- eliminación;
- cambio de estado;
- búsqueda o filtrado;
- resumen o indicador.

---

## 3.4 Requerimientos no funcionales

Definan al menos **5 requerimientos no funcionales**.

Deben considerar al menos:

- usabilidad;
- mantenibilidad;
- manejo de errores;
- ejecución/reproducibilidad;
- persistencia de datos.

Ejemplo:

```text
RNF-01 — Ejecución reproducible
La aplicación completa debe poder iniciarse mediante Docker Compose
siguiendo las instrucciones del README del equipo.
```

Eviten declaraciones imposibles de verificar como "el sistema será rápido". Cuando sea posible, formulen criterios observables.

---

## 3.5 Reglas de negocio

Documenten al menos **4 reglas de negocio**.

Ejemplo:

```text
RN-01
Toda incidencia nueva debe comenzar en estado ABIERTA.
```

Las reglas deben quedar implementadas en el sistema y no solamente escritas en el documento.

---

## 3.6 Criterios de aceptación

Seleccionen **3 requerimientos funcionales** y escriban criterios de aceptación verificables.

Pueden utilizar Given/When/Then o lenguaje equivalente.

Ejemplo:

```gherkin
Dado que el usuario completó todos los campos obligatorios
Cuando registra una nueva incidencia
Entonces la incidencia queda almacenada
Y aparece en el listado principal
Y su estado inicial es ABIERTA
```

---

## 3.7 Alcance y fuera de alcance

Definan explícitamente qué sí realizará el equipo y qué no realizará durante el diagnóstico.

Ejemplo de fuera de alcance:

- autenticación real;
- recuperación de contraseña;
- envío de correo;
- notificaciones push;
- microservicios;
- despliegue productivo en nube.

La capacidad de limitar el alcance es parte del diagnóstico.

---

## Entregable

El levantamiento debe quedar versionado en el repositorio del equipo, por ejemplo:

```text
docs/
└── requerimientos.md
```

Debe representar lo que realmente implementó el equipo. Si durante el desarrollo cambia una decisión importante, actualicen la documentación.
