# Capacidad · IoT y monitoreo

## Contexto

Sensores o dispositivos envían mediciones de temperatura, humedad, ubicación u otras variables.

## Comunicación síncrona posible

```text
ms-dispositivos -> ms-autorizacion
```

Un dispositivo puede requerir validación de credenciales antes de aceptar sus datos.

## Comunicaciones asíncronas posibles

Evento:

```text
MedicionRecibida
```

Consumers posibles:

- `ms-historico`: almacena series de tiempo;
- `ms-alertas`: evalúa umbrales;
- `ms-analytics`: procesa agregaciones;
- `ms-dashboard`: actualiza proyecciones o vistas.

## Variación

También puede existir un evento distinto:

```text
TemperaturaCriticaDetectada
```

que se enrute a una queue específica para alertas.

## Pregunta de diseño

¿Qué parte del flujo necesita respuesta inmediata y qué procesamiento puede desacoplarse para absorber grandes volúmenes de mediciones?
