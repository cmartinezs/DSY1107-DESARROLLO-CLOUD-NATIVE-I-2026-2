# 11 · Costos, detención y cleanup

## Objetivo

Cerrar la práctica sin dejar cobros accidentales, recursos huérfanos ni confusión entre “detener temporalmente” y “eliminar definitivamente”.

## Inventario antes de limpiar

Registrar qué se creó realmente:

```text
AWS
- EC2 instance
- Security Group
- key pair / mecanismo acceso
- API Gateway HTTP API
- integrations/routes/authorizer
- hosting frontend (si aplica)
- CloudFront/S3 u alternativa autorizada

Microsoft
- External tenant si fue creado solo para la práctica
- cloudtasks-spa
- cloudtasks-api
- user flow
- usuarios demo
- scopes/roles
```

No borrar recursos compartidos con otras actividades.

## Mientras se desarrolla la práctica

Cuando no se necesite EC2 durante un periodo prolongado y el laboratorio lo permita, detener la instancia puede reducir consumo.

```text
Stop != Terminate
```

Al volver a iniciarla, comprobar nuevamente IP/DNS y `BACKEND_CLOUD_URL`.

No depender de una IP que se asumió estable.

## Al terminar la práctica

Orden sugerido:

1. comprobar commits/push de la guía;
2. registrar IDs/nombres de recursos creados;
3. eliminar frontend/CDN/hosting creado exclusivamente para esta práctica si ya no se usará;
4. eliminar API Gateway de práctica si ya no se usará;
5. terminar EC2 si ya no se necesita;
6. revisar Security Groups huérfanos;
7. eliminar imágenes/registries opcionales de ★ Advanced si corresponde;
8. revisar recursos Entra antes de borrar tenant/apps;
9. verificar en las consolas que no queden recursos inesperados.

## ★ Docker

Local:

```bash
docker ps -a
docker image ls
```

Eliminar contenedores/imágenes de práctica si ya no son útiles. Esto no sustituye cleanup cloud.

## No hacer

- no borrar un tenant Microsoft si se reutilizará en otras actividades;
- no eliminar una VPC/subnet compartida;
- no borrar un Security Group sin saber qué lo usa;
- no asumir que cerrar la pestaña AWS detiene recursos;
- no eliminar configuraciones compartidas solo porque CloudTasks ya terminó.

## Checklist cleanup

- [ ] trabajo de la guía está en GitHub.
- [ ] EC2 innecesaria detenida/terminada según decisión.
- [ ] API Gateway innecesario eliminado.
- [ ] hosting/CDN innecesario eliminado.
- [ ] recursos Docker avanzados revisados.
- [ ] recursos Microsoft revisados conscientemente.
- [ ] no quedan access tokens/keys en archivos locales compartidos.
- [ ] el grupo sabe qué recursos quedaron y por qué.

## Nota de costos

Los precios, free tiers y créditos pueden cambiar. No asumir que un servicio es gratuito porque lo fue en otro semestre. Antes de crear recursos fuera de un sandbox académico, revisar el pricing y límites vigentes de la cuenta utilizada.
