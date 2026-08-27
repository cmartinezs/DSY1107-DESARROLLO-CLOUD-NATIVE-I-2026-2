# 11 · Costos, detención y cleanup

## Objetivo

Cerrar la práctica sin dejar cobros accidentales, recursos huérfanos ni confusión entre **detener**, **deshabilitar** y **eliminar**.

## 1. Inventario antes de limpiar

Registrar qué se creó realmente.

### AWS

```text
EC2 instance
EBS volume(s)
Security Group
key pair / mecanismo de acceso
public IPv4 / Elastic IP si se utilizó
API Gateway HTTP API
integrations
routes
JWT authorizer
hosting frontend
S3 bucket/objetos si aplica
CloudFront distribution si aplica
registry/image opcional de ★ Advanced
```

### Microsoft

```text
External tenant, si fue creado solo para la práctica
cloudtasks-spa
cloudtasks-api
user flow
usuarios demo
scopes
app role Admin, si se implementó ★04B
```

No borrar recursos compartidos con otras actividades.

## 2. Diferenciar estados

```text
EC2 Stop      → instancia deja de ejecutar CPU, pero no equivale a eliminar todos sus recursos/costos
EC2 Terminate → instancia se elimina; revisar volúmenes/IP asociados
CloudFront disable → no equivale todavía a eliminar distribución
S3 empty      → no equivale a borrar bucket
```

Nunca asumir que cerrar una pestaña o cerrar SSH detiene recursos.

## 3. Mientras se desarrolla

Si el laboratorio lo permite, detener EC2 cuando no se utilizará durante un periodo prolongado puede reducir consumo.

Al iniciar nuevamente:

```text
1. comprobar estado EC2
2. comprobar IP/DNS
3. comprobar BACKEND_CLOUD_URL
4. comprobar proceso/systemd o contenedor
5. repetir health remoto
6. repetir prueba protegida
7. comprobar integración Gateway
```

Si la IP pública cambió, actualizar consumidores y volver a validar en vez de diagnosticar el host antiguo.

## 4. Antes del cleanup definitivo

No eliminar infraestructura hasta comprobar:

```text
Git status entendido
commits realizados
push confirmado
README/bitácora actualizados
no hay secretos versionados
IDs/nombres de recursos registrados
```

Los recursos cloud no deben convertirse en la única evidencia de cómo estaba construida la práctica.

## 5. Orden de cleanup sugerido

1. confirmar GitHub remoto;
2. registrar inventario final;
3. eliminar/retirar frontend cloud creado solo para la práctica;
4. revisar CloudFront y S3 según la opción utilizada;
5. eliminar API Gateway de práctica si ya no se usará;
6. terminar EC2 si ya no se necesita;
7. revisar EBS y direcciones IP que hayan quedado independientes;
8. revisar Security Groups sin uso;
9. eliminar imágenes/registries opcionales de ★ Advanced si corresponde;
10. revisar apps, user flow, usuarios demo y roles de Entra;
11. borrar un External tenant **solo** si se confirma que no es compartido;
12. volver a las consolas y comprobar que el inventario esperado quedó vacío o justificado.

## 6. ★ Docker

Local:

```bash
docker ps -a
docker image ls
```

Eliminar contenedores/imágenes de práctica solo si ya no son útiles.

En EC2, si la instancia se conservará para otras actividades, revisar explícitamente:

```bash
docker ps -a
docker image ls
```

Eliminar un contenedor no elimina automáticamente imágenes, volúmenes u otros recursos del host.

## 7. Secretos y datos temporales

Revisar también el computador local:

```text
ev1-local-values.txt
capturas
exports
terminal history
archivos con Access Token
copias de keys
.env temporales
```

`ev1-local-values.txt` puede conservar valores públicos de referencia mientras siga siendo útil, pero nunca debe contener secretos ni tokens vigentes.

Si una credencial real fue publicada accidentalmente, borrar el archivo no basta: debe revocarse/rotarse.

## 8. No hacer

- no borrar un tenant Microsoft compartido;
- no eliminar una VPC/subnet compartida;
- no borrar un Security Group sin revisar asociaciones;
- no asumir que `Stop` = `Terminate`;
- no asumir que borrar EC2 elimina todos los volúmenes/IP;
- no asumir que borrar archivos S3 elimina automáticamente CloudFront;
- no dejar una distribución/CDN activa por olvido;
- no eliminar configuraciones compartidas solo porque CloudTasks terminó.

## Checklist cleanup

- [ ] trabajo de la guía está en GitHub.
- [ ] no hay secretos versionados.
- [ ] EC2 innecesaria detenida o terminada según decisión.
- [ ] EBS/IP asociados revisados.
- [ ] API Gateway innecesario eliminado.
- [ ] hosting/CDN/S3 innecesarios revisados.
- [ ] recursos Docker avanzados revisados.
- [ ] recursos Microsoft revisados conscientemente.
- [ ] apps/user flow/usuarios demo innecesarios eliminados si son exclusivos.
- [ ] tenant compartido no fue eliminado accidentalmente.
- [ ] archivos locales con credenciales/tokens fueron revisados.
- [ ] el grupo sabe qué recursos quedaron activos y por qué.

## Nota de costos

Precios, free tiers, créditos y cobros asociados a IPv4, almacenamiento, CDN u otros servicios pueden cambiar. Antes de crear o conservar recursos fuera de un sandbox académico, revisar el pricing y los límites vigentes de la cuenta utilizada.
