# 11 · Costos, detención y cleanup

**REQUERIDO OPERACIONAL** · La evaluación no termina dejando recursos cloud olvidados.

## Objetivo

Evitar cobros accidentales, recursos huérfanos y confusión entre “detener temporalmente” y “eliminar definitivamente”.

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
- External tenant (si fue creado solo para curso)
- cloudtasks-spa
- cloudtasks-api
- user flow
- usuarios demo
- scopes/roles
```

No borrar recursos compartidos con otras actividades.

## Durante las dos semanas

Cuando no se necesite EC2 durante un periodo prolongado y el laboratorio lo permita, detener la instancia puede reducir consumo. Tener presente:

```text
Stop != Terminate
```

Al volver a iniciarla, comprobar nuevamente IP/DNS y `BACKEND_CLOUD_URL`.

**EVIDENCIA** · Antes de la defensa, no depender de una IP que se asumió estable.

## Después de evaluación

Orden sugerido:

1. guardar evidencias sanitizadas;
2. comprobar commits/push final;
3. registrar IDs/nombres de recursos a eliminar;
4. eliminar frontend/CDN/hosting creado exclusivamente para EV1;
5. eliminar API Gateway de EV1;
6. terminar EC2 de EV1 si ya no se usará;
7. revisar Security Groups huérfanos;
8. eliminar imágenes/registries opcionales creados por ★ Advanced si corresponde;
9. revisar recursos Entra antes de borrar tenant/apps;
10. verificar en consolas que no queden recursos inesperados.

## ★ Docker

Local:

```bash
docker ps -a
docker image ls
```

Eliminar contenedores/imágenes de práctica si ya no son útiles. Esto no sustituye cleanup cloud.

## No hacer

- no borrar el tenant Microsoft si se reutilizará en otras evaluaciones;
- no eliminar una VPC/subnet compartida;
- no borrar un Security Group sin saber qué lo usa;
- no eliminar evidencia antes de que la evaluación esté cerrada;
- no asumir que cerrar la pestaña AWS detiene recursos.

## Checklist cleanup

- [ ] repositorio final está en GitHub.
- [ ] evidencia final está preservada sin secretos.
- [ ] EC2 innecesaria detenida/terminada según decisión.
- [ ] API Gateway innecesario eliminado.
- [ ] hosting/CDN innecesario eliminado.
- [ ] recursos Docker avanzados revisados.
- [ ] recursos Microsoft revisados conscientemente.
- [ ] no quedan access tokens/keys en archivos locales compartidos.
- [ ] el grupo sabe qué recursos quedaron y por qué.

## Nota de costos

Los precios, free tiers y créditos pueden cambiar. No asumir que un servicio es gratuito porque lo fue en otro semestre. Antes de crear recursos fuera de un sandbox académico, revisar el pricing y límites vigentes de la cuenta utilizada.
