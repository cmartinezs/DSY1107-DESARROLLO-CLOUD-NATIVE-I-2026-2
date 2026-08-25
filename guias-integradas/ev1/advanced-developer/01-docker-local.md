# ★ 01 · Containerizar Spring Boot y probar Docker local

## Objetivo

Empaquetar el mismo backend CloudTasks que ya funciona como JAR dentro de una imagen Docker y comprobar que mantiene el mismo contrato HTTP y las mismas reglas de seguridad.

> No containerizar una aplicación que todavía falla fuera de Docker. Primero debe pasar las puertas normales de backend/JWT.

## Cuándo entrar a esta ruta

Completar antes:

- `01A` backend operativo;
- `04` Resource Server/JWT operativo localmente.

La bifurcación ocurre **después de validar el backend**, no antes.

## 1. Crear `.dockerignore`

En `backend/`:

```dockerignore
target/
.git/
.idea/
*.iml
.env
.env.*
```

No excluir:

```text
.mvn/
mvnw
pom.xml
src/
```

porque el build utilizará Maven Wrapper.

## 2. Crear Dockerfile multi-stage

En `backend/Dockerfile`:

```dockerfile
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -DskipTests clean package

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
```

### Qué aporta el multi-stage build

```text
stage build
  JDK + Maven Wrapper + código fuente
       ↓ genera JAR
stage runtime
  JRE + JAR
```

La imagen final no necesita conservar el source tree ni las herramientas de compilación.

## 3. Construir imagen

Desde `backend/`:

```bash
docker build -t cloudtasks-api:ev1 .
```

Verificar:

```bash
docker image ls cloudtasks-api
```

## 4. Ejecutar health sin seguridad externa

Las variables reales se pasan en runtime, no se escriben en Dockerfile.

Ejemplo:

```bash
docker run --rm \
  --name cloudtasks-api \
  -p 8080:8080 \
  -e SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI='<OIDC_ISSUER>' \
  cloudtasks-api:ev1
```

En otra terminal:

```bash
curl -i http://localhost:8080/api/public/health
```

Esperado:

```text
HTTP 200
```

Sin token:

```bash
curl -i http://localhost:8080/api/tasks
```

Esperado:

```text
401
```

## 5. Probar con Access Token real

```bash
curl -i \
  -H "Authorization: Bearer <ACCESS_TOKEN>" \
  http://localhost:8080/api/tasks
```

Debe comportarse igual que el backend ejecutado directamente con `./mvnw spring-boot:run` o `java -jar`.

## 6. Comparación obligatoria

El estudiante avanzado debe poder explicar:

| Aspecto | JAR directo | Docker |
|---|---|---|
| código Spring | igual | igual |
| puerto app | 8080 | 8080 interno |
| Java host requerido | sí | no para runtime del contenedor |
| artefacto | JAR | imagen |
| config sensible | env/config | env/config |
| JWT | igual | igual |
| endpoints | iguales | iguales |
| API Gateway posterior | igual | igual |

## 7. Comandos de diagnóstico

Contenedores:

```bash
docker ps
docker ps -a
```

Logs:

```bash
docker logs cloudtasks-api
```

Procesos/puertos:

```bash
docker port cloudtasks-api
```

Detener:

```bash
docker stop cloudtasks-api
```

No usar `docker exec` para modificar manualmente el contenedor como forma normal de deployment. Si se requiere un cambio, modificar configuración/código y reconstruir la imagen.

## 8. Errores frecuentes

### `permission denied: ./mvnw`

El Dockerfile debe ejecutar:

```dockerfile
RUN chmod +x mvnw
```

Además revisar que `mvnw` no tenga finales CRLF.

### `port is already allocated`

Existe otro proceso/contenedor usando 8080. Detenerlo en vez de cambiar puertos al azar.

### funciona como JAR pero falla en Docker

Comparar:

1. variable `issuer-uri`;
2. conectividad del contenedor hacia el issuer/JWKS;
3. puerto publicado;
4. logs Docker;
5. Java runtime de la imagen.

### secreto dentro del Dockerfile

Incorrecto:

```dockerfile
ENV CLIENT_SECRET=...
```

EV1 no necesita client secret en Angular y cualquier configuración sensible backend debe entrar en runtime/secrets, no quedar horneada en la imagen.

## Puerta de validación ★01

- [ ] el JAR funciona antes de Docker.
- [ ] existe `Dockerfile` reproducible.
- [ ] existe `.dockerignore`.
- [ ] `docker build` finaliza correctamente.
- [ ] health containerizado = 200.
- [ ] protegida sin token = 401.
- [ ] Access Token válido conserva el comportamiento esperado.
- [ ] no hay secretos en imagen/Dockerfile/repositorio.
- [ ] el alumno puede explicar imagen vs contenedor.

## Siguiente bifurcación

Al llegar a [05 · Desplegar backend en AWS](../05-aws-backend.md):

```text
ruta base → JAR + Java 21 + systemd en EC2
★ avanzada → Docker Engine + container en EC2
```

Seguir:

→ [★ 02 · Desplegar contenedor en EC2](./02-docker-ec2.md)
