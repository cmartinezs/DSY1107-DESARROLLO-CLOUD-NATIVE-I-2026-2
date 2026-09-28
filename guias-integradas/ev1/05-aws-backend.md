# 05 · Desplegar backend en AWS EC2

## Objetivo

Pasar el backend ya validado localmente a AWS **sin introducir todavía API Gateway**. Primero se demuestra que Spring Boot funciona en EC2; después se agrega la frontera de API Management.

La práctica usa **EC2 + API Gateway** como arquitectura de referencia. ECS queda como evolución posterior para no introducir orquestación antes de dominar este flujo base.

## Antes de comenzar

Debe estar en `PASS`:

```text
04A build
health local 200
protegida local sin token 401
Access Token válido
issuer/audience
scopes
ownership
```

Y deben existir valores validados:

```text
OIDC_ISSUER
API_AUDIENCE
```

## Elegir ruta de despliegue

```text
RUTA BASE
JAR + Java 21 + EC2
        ↓
BACKEND_CLOUD_URL
        ↓
API Gateway

★ ADVANCED
Docker image + Docker Engine + EC2
        ↓
BACKEND_CLOUD_URL
        ↓
API Gateway
```

Ruta base: continuar aquí.

★ Advanced:

→ [★ Desplegar CloudTasks containerizado en EC2](./advanced-developer/02-docker-ec2.md)

Ambas rutas deben terminar con el **mismo contrato HTTP** y el mismo `BACKEND_CLOUD_URL` funcional.

---

# Ruta base · JAR sobre EC2

## 1. Empaquetar con Maven Wrapper

PowerShell:

```powershell
.\mvnw.cmd clean package
```

Git Bash/Linux/macOS/WSL:

```bash
./mvnw clean package
```

No se requiere Maven global.

Antes de copiar el artefacto, validar localmente con las mismas variables que usará Spring:

PowerShell:

```powershell
$env:OIDC_ISSUER="<OIDC_ISSUER>"
$env:API_AUDIENCE="<API_AUDIENCE>"
java -jar target/*.jar
```

Git Bash/Linux/macOS:

```bash
export OIDC_ISSUER='<OIDC_ISSUER>'
export API_AUDIENCE='<API_AUDIENCE>'
java -jar target/*.jar
```

Comprobar:

```text
/api/public/health → 200
/api/tasks sin token → 401
```

No desplegar un JAR que todavía falla localmente.

---

# 2. Crear EC2

Seguir el runbook detallado:

→ [05A · EC2 paso a paso](./05a-ec2-paso-a-paso.md)

Usar una instancia Linux permitida por el laboratorio y configurar únicamente lo necesario.

No abrir SSH a `0.0.0.0/0` indiscriminadamente.

---

# 3. Verificar Java 21 en EC2

```bash
java -version
```

Debe existir Java 21 compatible con el JAR. Instalar runtime 21 según la distribución concreta si falta.

---

# 4. Copiar el JAR

Usar el mecanismo permitido por el laboratorio, por ejemplo:

```text
SCP
SSM / Session Manager
mecanismo provisto por sandbox
```

Confirmar en EC2:

```bash
ls -lh cloudtasks-api.jar
```

---

# 5. Configuración runtime: nombres canónicos

`application.properties` usa:

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=${OIDC_ISSUER}
cloudtasks.security.audience=${API_AUDIENCE}
```

Por lo tanto, en EC2 definir exactamente:

```bash
export OIDC_ISSUER='<OIDC_ISSUER>'
export API_AUDIENCE='<API_AUDIENCE>'
```

No mezclar esta guía con un nombre alternativo como:

```text
SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI
```

porque el starter canónico ya resolvió la configuración mediante `${OIDC_ISSUER}`.

Comprobar antes de ejecutar:

```bash
printenv OIDC_ISSUER
printenv API_AUDIENCE
```

No mostrar tokens ni secretos en capturas compartidas.

---

# 6. Ejecutar en foreground primero

```bash
java -jar cloudtasks-api.jar
```

Observar logs directamente.

Desde otra sesión/terminal en la misma EC2:

```bash
curl -i http://localhost:8080/api/public/health
curl -i http://localhost:8080/api/tasks
```

Esperado:

```text
health = 200
/tasks sin token = 401
```

**CHECKPOINT 05-1 · aplicación en EC2**

- [ ] proceso Spring activo.
- [ ] issuer cargado.
- [ ] audience cargada.
- [ ] health localhost EC2 = 200.
- [ ] protegida localhost EC2 sin token = 401.

---

# 7. Probar networking externo

Solo después del checkpoint anterior:

```bash
curl -i http://<HOST_EC2>:8080/api/public/health
```

Luego:

```bash
curl -i http://<HOST_EC2>:8080/api/tasks
```

Esperado:

```text
health → 200
protegida sin token → 401
```

Con Access Token válido:

```bash
curl -i \
  -H "Authorization: Bearer <ACCESS_TOKEN>" \
  http://<HOST_EC2>:8080/api/tasks
```

Debe conservar las mismas políticas locales.

Registrar:

```text
BACKEND_CLOUD_URL=http://<HOST_EC2>:8080
```

---

# 8. Persistencia con systemd

Solo después de validar foreground, configurar `systemd` o el mecanismo autorizado por el laboratorio.

El servicio debe definir también:

```text
OIDC_ISSUER
API_AUDIENCE
```

No asumir que variables exportadas en una shell interactiva existirán dentro de `systemd`.

Después:

```bash
sudo systemctl daemon-reload
sudo systemctl restart cloudtasks-api
sudo systemctl status cloudtasks-api
```

Cerrar SSH y repetir health remoto.

**CHECKPOINT 05-2 · persistencia**

- [ ] servicio inicia sin terminal interactiva.
- [ ] variables runtime llegan al proceso.
- [ ] health remoto sigue en 200.

---

# 9. IP/DNS cambiante

Si la instancia utiliza IP pública dinámica:

```text
Stop
→ Start
→ IP pública puede cambiar
```

Después de cada reinicio de ese tipo:

1. comprobar IP/DNS actual;
2. actualizar `BACKEND_CLOUD_URL` si cambió;
3. repetir health remoto;
4. más adelante, si Gateway ya existe, actualizar/revalidar la integración.

No asumir que una URL EC2 antigua seguirá siendo válida.

---

# Puerta de validación 05

Antes de crear API Gateway:

```text
build local PASS
EC2 runtime PASS
OIDC_ISSUER cargado PASS
API_AUDIENCE cargado PASS
health localhost EC2 200 PASS
protegida localhost EC2 401 PASS
health remoto 200 PASS
Access Token remoto PASS
proceso persistente PASS
BACKEND_CLOUD_URL validado PASS
```

## Diagnóstico rápido

### Backend no arranca

```text
Java 21
→ OIDC_ISSUER
→ API_AUDIENCE
→ issuer discovery/JWKS alcanzable
→ logs Spring
```

### Timeout externo

```text
¿health localhost EC2 funciona?
NO → proceso/runtime/config
SÍ → puerto/listening/Security Group/networking
```

### Connection refused

Normalmente host alcanzable pero ningún proceso escucha en el puerto esperado.

```bash
ps aux | grep java
```

★ Docker:

```bash
docker ps
docker logs cloudtasks-api
```

### 401 con token en cloud pero no local

Comparar:

```text
OIDC_ISSUER
API_AUDIENCE
token real
hora del sistema
```

No modificar CORS: `curl` no aplica CORS.

## Siguiente etapa

→ [06 · AWS API Gateway + JWT Authorizer](./06-api-gateway-jwt.md)
