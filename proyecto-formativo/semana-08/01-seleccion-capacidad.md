# Paso 1 · Seleccionar una capacidad asíncrona

## Objetivo

Elegir **una** capacidad del proyecto que tenga sentido desacoplar.

Candidatas típicas:

- notificación;
- auditoría;
- generación posterior;
- actualización secundaria.

## No elegir todavía

Una operación que necesite responder inmediatamente al usuario o una capacidad cuya consistencia requiera una respuesta síncrona sin haber discutido compensaciones.

## Entregable

Documento breve:

```text
capacidad:
disparador:
por qué puede esperar:
qué sucede si el consumer está detenido:
qué datos necesita el mensaje:
```

## Checkpoint

La justificación debe hablar del problema, no de “usar RabbitMQ porque toca”.
