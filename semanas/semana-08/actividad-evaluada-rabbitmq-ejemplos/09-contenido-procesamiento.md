# Capacidad · Contenido digital y procesamiento

## Contexto

Un usuario sube una imagen, video, audio o documento.

## Comunicación síncrona posible

```text
ms-contenidos -> ms-autorizacion
```

Antes de aceptar la carga se puede necesitar validar permisos, cuota disponible o formato permitido.

## Comunicaciones asíncronas posibles

Evento:

```text
ContenidoSubido
```

Consumers posibles:

- `ms-miniaturas`: genera previews;
- `ms-transcodificacion`: transforma formatos;
- `ms-indexacion`: prepara búsquedas;
- `ms-moderacion`: ejecuta análisis posterior.

## Topología posible

```text
Exchange: contenidos.events
Routing key: contenido.subido
Queues:
- miniaturas.contenido-subido
- indexacion.contenido-subido
```

## Pregunta de diseño

¿El usuario necesita esperar la generación de todas las versiones del archivo para saber que su carga fue aceptada?
