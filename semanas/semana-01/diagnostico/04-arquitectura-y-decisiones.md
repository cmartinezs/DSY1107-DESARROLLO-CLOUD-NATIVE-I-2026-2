# 4. Arquitectura y decisiones técnicas

El equipo debe demostrar que no solamente puede programar la solución, sino también explicar por qué la construyó de esa forma.

No se espera una arquitectura compleja. Para este diagnóstico, una solución simple y bien justificada es preferible a una arquitectura sobredimensionada.

El stack tecnológico base ya está definido por la actividad:

- **Backend:** Java 21 + Spring Boot 4.x.
- **Frontend:** React con Vite o Next.js.
- **Persistencia:** MySQL o PostgreSQL.
- **Infraestructura local:** Docker + Docker Compose.

Las decisiones arquitectónicas no consisten en cambiar ese stack, sino en justificar **cómo se utilizará**, qué alternativa se escogerá cuando exista más de una opción y cómo se organizarán las responsabilidades del sistema.

---

## 4.1 Diagrama de arquitectura

Incluyan un diagrama sencillo que muestre, como mínimo:

```text
Usuario
  ↓
Frontend React
  ↓ HTTP/JSON
Backend Java 21 / Spring Boot 4.x
  ↓
Base de datos relacional
```

Pueden utilizar Mermaid, PlantUML, draw.io u otra herramienta conocida por el equipo.

El diagrama debe reflejar la implementación real.

---

## 4.2 Responsabilidades

Expliquen brevemente qué responsabilidad tiene cada componente:

### Frontend — React

- presentación;
- interacción con el usuario;
- consumo de la API REST;
- administración del estado de interfaz;
- validaciones de experiencia de usuario que correspondan.

### Backend — Spring Boot

- exposición de endpoints REST;
- validación de datos;
- reglas de negocio;
- coordinación con persistencia;
- respuestas HTTP y manejo de errores.

### Base de datos

- almacenamiento persistente;
- integridad básica de los datos.

---

## 4.3 Decisiones arquitectónicas obligatorias

El equipo deberá documentar como mínimo **5 decisiones**.

Cada decisión debe contener:

1. **Contexto:** qué problema o necesidad existía.
2. **Decisión:** qué alternativa eligió el equipo.
3. **Justificación:** por qué fue elegida.
4. **Consecuencias:** qué ventajas, costos o restricciones introduce.

No se espera un ADR formal completo, pero pueden utilizar ese formato si ya lo conocen.

### Decisiones mínimas a documentar

#### DA-01 — Vite o Next.js

El frontend debe utilizar React. El equipo debe decidir si desarrollará la aplicación con **React + Vite** o con **Next.js**.

Expliquen por qué esa alternativa es adecuada para una aplicación pequeña de este tipo. Consideren, por ejemplo, complejidad, routing, necesidades de renderizado, estructura del proyecto y experiencia previa del equipo.

#### DA-02 — JavaScript o TypeScript

El frontend puede desarrollarse con JavaScript o TypeScript.

Justifiquen la elección considerando simplicidad, seguridad de tipos, mantenibilidad y experiencia del equipo.

#### DA-03 — MySQL o PostgreSQL

¿Por qué escogieron la base de datos relacional utilizada?

Expliquen además cómo se conecta Spring Boot con la persistencia y qué mecanismo utilizaron para mapear o acceder a los datos.

#### DA-04 — Organización del backend Spring Boot

Expliquen cómo separaron responsabilidades en el backend.

Una estructura posible puede incluir:

```text
controller
service
domain/model
repository
dto
exception
```

No es obligatorio utilizar exactamente esos paquetes. Lo importante es que el equipo pueda explicar dónde se ubican:

- endpoints;
- reglas de negocio;
- acceso a datos;
- validaciones;
- transformación de requests/responses;
- manejo de errores.

#### DA-05 — Estrategia de contenerización

¿Qué componentes se ejecutan en contenedores y por qué?

Expliquen como mínimo:

- cómo se construye el backend Java 21 / Spring Boot;
- cómo se construye o ejecuta el frontend React;
- cómo se ejecuta la base de datos;
- cómo se comunican los servicios dentro de Docker Compose;
- cómo se preservan los datos.

---

## 4.4 Decisiones adicionales sugeridas

Si corresponde, pueden justificar también:

- estructura de carpetas;
- uso de DTOs;
- formato de respuestas de la API;
- códigos HTTP utilizados;
- estrategia de validación con Spring;
- manejo de excepciones;
- configuración mediante `application.properties` o `application.yml`;
- variables de entorno;
- CORS entre frontend y backend;
- estrategia Git del equipo;
- inicialización de la base de datos;
- migraciones o creación de esquema;
- health checks;
- puertos utilizados;
- separación entre configuración de desarrollo y ejecución en Docker.

---

## 4.5 Restricción importante

No es válido justificar una decisión únicamente con frases como:

> "porque es mejor"

> "porque es más moderno"

> "porque lo pidió la actividad"

La justificación debe relacionar la decisión con el contexto del problema, las competencias del equipo, la simplicidad, la mantenibilidad, el tiempo disponible o alguna otra razón técnica defendible.

Tampoco es necesario justificar por qué se utiliza Java/Spring Boot o React como stack general, ya que esas tecnologías forman parte de las restricciones del desafío. Sí deben justificar las decisiones realizadas **dentro de ese stack**.

---

## Entregable

La documentación puede quedar, por ejemplo, en:

```text
docs/
├── arquitectura.md
└── decisiones.md
```

También puede integrarse en un único documento si mantiene una estructura clara.
