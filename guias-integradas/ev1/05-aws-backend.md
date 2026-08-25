# 05 · Desplegar backend en AWS

## Objetivo

Pasar el backend ya validado localmente a AWS sin introducir todavía API Gateway. Primero se demuestra que el proceso Spring Boot funciona en cloud; después se agrega la frontera de API Management.

## 1. Empaquetar

Desde `backend/`:

```bash
mvn clean package
```

Validar el JAR **localmente** antes de copiarlo:

```bash
java -jar target/*.jar
```

Probar `/api/public/health`.

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

Copiar el JAR mediante el mecanismo permitido (SCP, SSM u otro del laboratorio).

Ejecutar primero en foreground para observar errores:

```bash
java -jar cloudtasks-api.jar
```

No crear un servicio persistente hasta que el proceso arranque correctamente.

## 5. Variables/configuración

El issuer no debe quedar hardcodeado en múltiples archivos. Preferir variable de entorno/configuración externa, por ejemplo:

```bash
export SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI='<OIDC_ISSUER>'
```

Luego ejecutar el JAR.

## 6. Probar desde fuera de EC2

Primero health:

```bash
curl http://<HOST_EC2>:8080/api/public/health
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
BACKEND_CLOUD_URL=http://<host>:8080
```

## 7. Persistencia del proceso

Una vez validado, configurar el mecanismo aprobado para mantener la app ejecutándose (por ejemplo systemd). El objetivo es que un reinicio de sesión SSH no apague el backend.

## Puerta de validación 05

Antes de crear API Gateway:

- JAR validado localmente;
- EC2 ejecuta Java compatible;
- health remoto = 200;
- endpoint protegido remoto sin token = 401;
- endpoint protegido con token válido conserva su política;
- se conoce `BACKEND_CLOUD_URL`;
- el proceso continúa activo sin depender de una terminal interactiva.

## Diagnóstico rápido

### Timeout

Revisar, en este orden:

1. proceso Spring activo;
2. puerto donde escucha la app;
3. binding (`0.0.0.0` vs loopback);
4. Security Group;
5. rutas/networking del entorno.

### Connection refused

Normalmente el host es alcanzable pero no hay proceso escuchando en ese puerto.

### 401 en cloud pero 200 local

Comparar issuer/configuración y token. No modificar CORS: `curl` no depende de CORS.
