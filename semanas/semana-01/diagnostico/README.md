# Semana 1 — Diagnóstico Fullstack y Docker

## Propósito

Esta actividad corresponde al diagnóstico inicial de la asignatura **DSY1107 - Desarrollo Cloud Native I**.

La asignatura **no enseñará desarrollo fullstack desde cero**. Se asume que las y los estudiantes ya poseen las competencias necesarias para construir una aplicación web sencilla con frontend, backend y persistencia. El objetivo del diagnóstico es observar el nivel real del curso antes de avanzar hacia arquitectura cloud native, gestión de APIs, contenerización, automatización, observabilidad, CI/CD y despliegue.

La actividad tiene dos componentes:

1. **Desafío grupal:** desarrollo de una aplicación fullstack pequeña, ejecutable mediante Docker.
2. **Cuestionario individual y privado:** reflexión técnica obligatoria sobre el trabajo realizado.

> La actividad es **formativa y sin calificación**, pero su realización es **obligatoria**. El resultado será utilizado para orientar el nivel de profundidad y apoyo requerido durante la asignatura.

---

## Desafío

Cada equipo deberá construir una aplicación denominada **Mini Help Desk**, un sistema sencillo para registrar y gestionar incidencias técnicas internas.

La aplicación debe permitir como mínimo:

- crear una incidencia;
- listar incidencias;
- consultar una incidencia;
- editar una incidencia;
- eliminar una incidencia;
- cambiar su estado;
- filtrar o buscar incidencias;
- mostrar al menos un resumen o indicador simple del estado de las incidencias.

El objetivo no es construir un producto complejo. Se busca comprobar que el equipo puede diseñar y conectar correctamente las distintas capas de una aplicación fullstack.

---

## Organización de la actividad

La documentación está dividida en los siguientes capítulos:

1. [Contexto y objetivos](01-contexto-y-objetivos.md)
2. [Desafío funcional](02-desafio-funcional.md)
3. [Levantamiento de requerimientos](03-levantamiento-requerimientos.md)
4. [Arquitectura y decisiones técnicas](04-arquitectura-y-decisiones.md)
5. [Docker y ejecución del sistema](05-docker-y-ejecucion.md)
6. [Entregables y evidencias](06-entregables-y-evidencias.md)
7. [Cuestionario individual privado](07-cuestionario-individual-privado.md)

---

## Stack tecnológico obligatorio

El diagnóstico debe realizarse con el stack fullstack trabajado previamente en la carrera. Para esta actividad se estandariza la tecnología para que el foco esté en la integración, la arquitectura y Docker, y no en comparar frameworks distintos.

### Backend

- **Java 21**.
- **Spring Boot 4.x**.
- API REST sobre HTTP/JSON.
- Persistencia mediante Spring Data JPA o mecanismo equivalente dentro del ecosistema Spring.

### Frontend

- **React**, utilizando la versión estable vigente al momento de desarrollar la actividad.
- El equipo debe utilizar una de estas dos alternativas:
  - **React + Vite** — recomendado para una SPA simple.
  - **Next.js** — permitido si el equipo ya lo domina y puede justificar su elección.
- Se permite JavaScript o TypeScript. La elección debe documentarse.

### Persistencia e infraestructura

- **Base de datos relacional:** MySQL o PostgreSQL.
- **Control de versiones:** Git + GitHub.
- **Contenedores:** Docker y Docker Compose.

No se debe reemplazar Java/Spring Boot por Node.js, Python, .NET u otro backend, ni React por otro framework frontend, salvo autorización explícita del docente.

La elección entre Vite y Next.js, entre JavaScript y TypeScript, y entre MySQL y PostgreSQL debe quedar justificada en la documentación arquitectónica.

---

## Principio de evaluación diagnóstica

No se espera una solución perfecta. Se observará principalmente si el equipo puede:

- comprender un problema sencillo;
- levantar requerimientos mínimos;
- transformar requerimientos en una solución funcional;
- separar frontend, backend y persistencia;
- diseñar una API coherente;
- justificar decisiones técnicas básicas;
- utilizar Git de manera colaborativa;
- construir y ejecutar el sistema con Docker;
- explicar cómo funciona la solución que desarrolló.

No se premiará agregar funcionalidades innecesarias si las funcionalidades esenciales no están terminadas o correctamente integradas.
