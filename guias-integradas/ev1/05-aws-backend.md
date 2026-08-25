# 05 · Desplegar backend en AWS EC2

## Objetivo

Pasar el backend ya validado localmente a AWS sin introducir todavía API Gateway. Primero se demuestra que Spring Boot funciona en EC2; después se agrega la frontera de API Management.

Esta práctica usa **EC2 + API Gateway** como arquitectura de referencia. La variante con ECS se deja fuera para no introducir orquestación de contenedores antes de dominar este flujo base.

## Elegir ruta de despliegue

En este punto existen dos caminos que terminan en el mismo checkpoint:

```text
RUTA BASE
JAR + Java 21 + EC2
        ↓
BACKEND_CLOUD_URL
        ↓
API Gateway

★ ADVANCED DEVELOPER
Docker image + Docker Engine + EC2
        ↓
BACKEND_CLOUD_URL
        ↓
API Gateway
```

### Ruta base

Continuar en esta misma guía.

### ★ Advanced Developer

Si ya se completó la containerización local:

→ [★ Desplegar CloudTasks containerizado en EC2](./advanced-developer/02-docker-ec2.md)

Al terminar esa alternativa, volver directamente a:

→ [06 · AWS API Gateway + JWT Authorizer](./06-api-gateway-jwt.md)

> ECS puede estudiarse posteriormente como evolución de la solución Docker, pero no forma parte de esta ruta de práctica.

---

# Ruta base · JAR sobre EC2

## 1. Empaquetar con Maven Wrapper

Desde `backend/`:

Linux/macOS/WSL/Git Bash:

```bash
./mvnw clean package
```

Windows PowerShell:

```powershell
.\mvnw.cmd clean package
```

No se requiere Maven global.

Validar el JAR **localmente** antes de copiarlo:

```bash
java -jar target/*.jar
```

Probar:

```text
/api/public/health
```

No desplegar a AWS un artefacto que todavía falla localmente.

## 2. Crear EC2

Usar una instancia Linux permitida por el entorno académico. Configurar únicamente los puertos necesarios.

Para la primera experiencia didáctica puede exponerse temporalmente el puerto de la aplicación solo desde orígenes controlados, si el laboratorio lo permite. La arquitectura objetivo es que el consumidor use API Gateway, no el backend directo.

> No abrir `0.0.0.0/0` indiscriminadamente para SSH. Restringir administración según las reglas del laboratorio.

## 3. Instalar Java

En la instancia comprobar:

```bash
java -version
```

Debe existir una versión compatible con el JAR. Si no existe, instalar JDK/JRE 21 desde los repositorios aprobados para la imagen utilizada.

## 4. Copiar y ejecutar

Copiar el JAR mediante el mecanismo permitido por el laboratorio, por ejemplo SCP o SSM.

Ejecutar primero en foreground para observar errores:

```bash
java -jar cloudtasks-api.jar
```

No crear un servicio persistente hasta que el proceso arranque correctamente.

## 5. Variables/configuración

El issuer no debe quedar hardcodeado en múltiples archivos.

Preferir configuración externa:

```bash
export SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI='<OIDC_ISSUER>'
```

Luego ejecutar el JAR.

No guardar Access Tokens ni credenciales cloud en archivos versionados.

## 6. Probar desde EC2 primero

Dentro de la propia instancia:

```bash
curl -i http://localhost:8080/api/public/health
```

Esperado:

```text
200
```

Luego:

```bash
curl -i http://localhost:8080/api/tasks
```

Esperado sin token:

```text
401
```

Esta prueba separa el problema de aplicación del problema de networking.

## 7. Probar desde fuera de EC2

Desde un origen autorizado:

```bash
curl -i http://<HOST_EC2>:8080/api/public/health
```

Luego una ruta protegida sin token:

```bash
curl -i http://<HOST_EC2>:8080/api/tasks
```

Resultados esperados:

```text
health → 200
/tasks sin token → 401
```

Con Access Token real, `/api/tasks` debe conservar el mismo comportamiento observado localmente.

Registrar:

```text
BACKEND_CLOUD_URL=http://<HOST_EC2>:8080
```

## 8. Persistencia del proceso

Una vez validado, configurar el mecanismo aprobado para mantener la app ejecutándose, por ejemplo `systemd`.

El objetivo es que cerrar la sesión SSH no apague el backend.

La ruta ★ Docker resuelve esta misma necesidad mediante Docker daemon + restart policy.

## Puerta de validación 05

Antes de crear API Gateway debe existir, por una de las dos rutas:

- backend validado localmente;
- EC2 operativo;
- health remoto = 200;
- endpoint protegido remoto sin token = 401;
- endpoint protegido con token válido conserva su política;
- `BACKEND_CLOUD_URL` conocido;
- proceso/contenedor continúa activo sin depender de una terminal interactiva.

### Registro según ruta

**Base:**

```text
EC2
+ java -version
+ proceso Spring/JAR
+ health
```

**★ Advanced:**

```text
EC2
+ docker version
+ docker ps
+ container CloudTasks
+ health
```

La variante Docker agrega profundidad operativa, pero ambas rutas deben terminar en el mismo estado funcional.

## Diagnóstico rápido

### Timeout

Revisar, en este orden:

1. proceso Spring o contenedor activo;
2. puerto donde escucha la app;
3. binding (`0.0.0.0` vs loopback);
4. publicación de puerto Docker, si aplica;
5. Security Group;
6. rutas/networking del entorno.

### Connection refused

Normalmente el host es alcanzable pero no existe un proceso escuchando en ese puerto.

Ruta base:

```bash
ps aux | grep java
```

Ruta ★:

```bash
docker ps
docker logs cloudtasks-api
```

### 401 en cloud pero 200 local

Comparar issuer/configuración y token.

No modificar CORS: `curl` no depende de CORS.

## Siguiente etapa

Cuando exista un `BACKEND_CLOUD_URL` reproducible, ambas rutas convergen:

→ [06 · AWS API Gateway + JWT Authorizer](./06-api-gateway-jwt.md)
