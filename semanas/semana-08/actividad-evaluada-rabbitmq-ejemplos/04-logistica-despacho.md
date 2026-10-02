# Capacidad · Logística y despacho

## Contexto

Una empresa recibe solicitudes de despacho de paquetes.

## Comunicación síncrona posible

```text
ms-envios -> ms-cobertura
```

Antes de aceptar un despacho se necesita validar si la dirección está dentro de una zona atendida.

## Comunicaciones asíncronas posibles

Evento:

```text
EnvioCreado
```

Consumers posibles:

- `ms-etiquetas`: genera etiqueta;
- `ms-tracking`: crea seguimiento;
- `ms-notificaciones`: informa al cliente;
- `ms-planificacion`: incorpora el envío a una ruta futura.

## Topología posible

```text
Exchange: envios.events
Routing key: envio.creado
Queues:
- tracking.envio-creado
- etiquetas.envio-creado
```

## Pregunta de diseño

¿La generación de una etiqueta o del código de tracking necesita bloquear la creación inicial del envío?
