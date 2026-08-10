# 5. Docker y ejecución del sistema

El diagnóstico debe permitir observar si el equipo comprende los conceptos básicos necesarios para ejecutar una aplicación fullstack mediante contenedores.

No se busca optimización avanzada de imágenes ni orquestación productiva. Se busca una solución reproducible y comprensible.

El stack de la actividad es:

- backend con **Java 21 + Spring Boot 4.x**;
- frontend con **React + Vite** o **Next.js**;
- persistencia con **MySQL o PostgreSQL**;
- ejecución mediante **Docker y Docker Compose**.

---

## 5.1 Componentes esperados

La solución debe considerar, como mínimo:

- frontend React;
- backend/API Spring Boot;
- base de datos relacional.

Como referencia, una composición típica puede ser:

```text
Docker Compose
├── frontend     # React + Vite o Next.js
├── backend      # Java 21 + Spring Boot 4.x
└── database     # MySQL o PostgreSQL
```

El equipo debe ser capaz de explicar cómo se comunican estos servicios y qué puertos necesita exponer al host.

---

## 5.2 Dockerfile

El equipo deberá incluir los Dockerfile necesarios para construir los componentes de aplicación.

Como mínimo deben ser capaces de explicar:

- qué imagen base utilizan;
- qué archivos copian;
- cuándo se descargan o instalan dependencias;
- cómo se ejecuta el build;
- qué puerto expone el servicio;
- qué comando inicia la aplicación.

### Backend

El contenedor del backend debe ejecutar la aplicación con **Java 21**. El equipo puede utilizar una estrategia simple o un build multi-stage, pero debe entenderla y poder explicarla.

Deben poder identificar claramente:

```text
código fuente
    ↓
build Maven o Gradle
    ↓
artefacto ejecutable
    ↓
runtime Java 21
    ↓
Spring Boot 4.x
```

### Frontend

Si utilizan **React + Vite**, deben explicar la diferencia entre el servidor de desarrollo y el artefacto generado para producción.

Si utilizan **Next.js**, deben explicar qué proceso necesita permanecer ejecutándose y por qué su contenerización puede diferir de una SPA estática generada con Vite.

No copien Dockerfiles desde Internet sin poder explicar sus instrucciones.

---

## 5.3 Docker Compose

La raíz del proyecto debe incluir un archivo `compose.yaml` o `docker-compose.yml` que permita iniciar la solución.

La ejecución esperada debe ser equivalente a:

```bash
docker compose up --build
```

El equipo debe documentar:

- servicios;
- puertos;
- variables de entorno necesarias;
- redes, si fueron declaradas explícitamente;
- volúmenes utilizados;
- dependencias entre servicios.

El frontend debe poder consumir la API Spring Boot y el backend debe conectarse a la base de datos utilizando nombres de servicio o configuración apropiada para el entorno Docker.

---

## 5.4 Persistencia

Los datos no deberían desaparecer simplemente por detener y volver a iniciar los contenedores.

La base de datos debe ejecutarse en Docker utilizando un volumen persistente, y el equipo debe explicar su propósito.

El docente puede comprobarlo mediante una secuencia similar a:

```bash
docker compose up -d
# crear datos desde la aplicación
docker compose down
docker compose up -d
# verificar que los datos continúan disponibles
```

El uso de `docker compose down -v` sí puede eliminar los volúmenes y no forma parte de esta comprobación de persistencia.

---

## 5.5 Configuración

No deben versionarse secretos ni contraseñas personales.

Si utilizan variables de entorno, agreguen un archivo de ejemplo, por ejemplo:

```text
.env.example
```

La aplicación debe poder configurarse sin modificar código fuente para cambiar valores como:

- host de base de datos;
- puerto;
- nombre de base de datos;
- credenciales de desarrollo;
- URL del backend consumida por el frontend;
- perfiles o propiedades relevantes de Spring Boot.

Se espera que comprendan cómo variables de entorno utilizadas por Docker Compose terminan siendo consumidas por Spring Boot o por la aplicación frontend.

---

## 5.6 Verificaciones mínimas

Antes de considerar terminado el diagnóstico, el equipo deberá comprobar:

1. Las imágenes se construyen sin error.
2. El backend se ejecuta con Java 21 y Spring Boot 4.x.
3. El frontend React inicia correctamente en la alternativa escogida.
4. Los contenedores inician correctamente.
5. El frontend puede comunicarse con el backend.
6. El backend puede comunicarse con la base de datos.
7. El CRUD funciona utilizando la solución levantada en Docker.
8. Las funcionalidades adicionales también funcionan.
9. Los datos persisten después de detener y volver a levantar los contenedores.
10. Un integrante puede clonar el repositorio en un entorno limpio y seguir el README para ejecutar la aplicación.

---

## 5.7 Conceptos que cada integrante debe poder explicar

Al finalizar el trabajo, cualquier integrante debería poder responder, al menos en términos básicos:

- diferencia entre imagen y contenedor;
- propósito de un Dockerfile;
- propósito de Docker Compose;
- diferencia entre `build` y `up`;
- por qué un volumen puede ser necesario;
- cómo se comunican los servicios;
- qué problema resuelven las variables de entorno;
- qué ocurre cuando un contenedor se elimina;
- cómo se genera y ejecuta el artefacto Spring Boot;
- qué diferencia existe entre servir una SPA construida con Vite y ejecutar una aplicación Next.js.

Estas ideas también serán retomadas en el cuestionario individual.
