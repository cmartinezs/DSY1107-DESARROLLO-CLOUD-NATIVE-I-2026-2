# 05A · EC2 paso a paso y estado conocido

**REQUERIDO EV1** · La pauta exige backend desplegado en EC2. Este anexo evita que una falla de instancia/red se confunda con Spring Security.

## Antes de AWS

- [ ] `./mvnw clean package` funciona.
- [ ] `java -jar target/*.jar` arranca localmente.
- [ ] health local = 200.
- [ ] protegida local sin token = 401.

Si cualquiera falla, no crear EC2 todavía.

## 1. Crear instancia

En EC2 → Launch instance:

```text
Name: cloudtasks-api-ev1
AMI: Linux autorizada por laboratorio
Architecture: compatible con tooling elegido
Instance type: el permitido por cuenta/lab
Storage: mínimo suficiente
```

No elegir una instancia más grande “por si acaso”.

## 2. Acceso administrativo

Usar el mecanismo autorizado: key pair/SSH o Session Manager si está disponible.

Security Group:

```text
SSH 22 → solo origen administrativo autorizado, si se usa SSH
TCP 8080 → solo durante integración didáctica y según reglas del laboratorio
```

No abrir SSH a todo Internet.

## 3. Registrar identidad de la instancia

Guardar localmente:

```text
EC2_INSTANCE_ID=
EC2_PUBLIC_IP=
EC2_PUBLIC_DNS=
```

**Advertencia** · una IP pública asignada automáticamente puede cambiar al detener/iniciar la instancia. Si cambia, cualquier integración que use ese host debe actualizarse y volver a validarse.

## 4. Probar conectividad antes de copiar la app

Entrar a EC2 y ejecutar:

```bash
uname -a
cat /etc/os-release
```

Confirmar distribución antes de copiar comandos de instalación de paquetes.

## 5. Ruta base: Java

```bash
java -version
```

Debe ser Java 21 compatible. Instalar runtime 21 según la distribución concreta si falta.

## 6. Transferir artefacto

Copiar el JAR mediante SCP/SSM/mecanismo autorizado. Confirmar:

```bash
ls -lh cloudtasks-api.jar
```

## 7. Ejecutar foreground primero

```bash
export OIDC_ISSUER='<OIDC_ISSUER>'
java -jar cloudtasks-api.jar
```

Desde la misma EC2:

```bash
curl -i http://localhost:8080/api/public/health
curl -i http://localhost:8080/api/tasks
```

Esperado:

```text
health = 200
/tasks sin token = 401
```

## 8. Recién ahora revisar networking externo

Desde equipo local:

```bash
curl -i http://<HOST_EC2>:8080/api/public/health
```

Si local EC2 funciona pero externo no:

```text
aplicación probablemente OK
→ revisar SG / puerto / routing / IP
```

No tocar Spring Security ni CORS.

## 9. Persistencia

Después de validar foreground, configurar `systemd` o mecanismo autorizado. Luego:

```bash
sudo systemctl status cloudtasks-api
```

Cerrar SSH y repetir health remoto.

## 10. Checkpoint EC2

- [ ] instancia identificada.
- [ ] administración restringida.
- [ ] distribución conocida.
- [ ] runtime correcto.
- [ ] health desde EC2 = 200.
- [ ] protegida desde EC2 sin token = 401.
- [ ] health remoto = 200.
- [ ] proceso persiste al cerrar sesión.
- [ ] `BACKEND_CLOUD_URL` registrado y validado.

## Árbol de falla

```text
timeout
├─ ¿health localhost EC2 funciona?
│  ├─ NO → proceso/runtime/config
│  └─ SÍ → networking/SG/host
connection refused
└─ revisar proceso + puerto/listening
401
└─ token/issuer/audience; no CORS
5xx
└─ logs aplicación antes de tocar Gateway
```

★ Si se eligió Docker, usar el anexo Advanced Developer y llegar al mismo checkpoint `BACKEND_CLOUD_URL validado`.
