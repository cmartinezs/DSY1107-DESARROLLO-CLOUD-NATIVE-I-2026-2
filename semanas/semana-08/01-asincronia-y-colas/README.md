# Contenido extendido · Asincronía y colas

Este material profundiza los conceptos de [2.1.1 · Mensajería asíncrona y RabbitMQ](../01-asincronia-y-colas.md).

## 1. Acoplamiento temporal

Dos componentes están temporalmente acoplados cuando ambos deben estar disponibles al mismo tiempo para completar una interacción.

En una llamada HTTP tradicional:

```text
Servicio A
→ llama a Servicio B
→ espera
→ B procesa
→ B responde
→ A continúa
```

Si B tarda o no está disponible, A queda afectado.

Esto no es necesariamente un problema. Muchas operaciones requieren precisamente ese comportamiento. El error sería asumir que **toda colaboración entre componentes debe ser síncrona**.

## 2. El cambio de modelo

Con mensajería:

```text
A
→ publica mensaje
→ broker acepta el mensaje
→ A continúa

más tarde:

broker
→ entrega mensaje
→ B procesa
```

La interacción queda dividida en dos momentos.

Eso introduce una propiedad importante: **el producer no controla exactamente cuándo ocurre el procesamiento final**.

## 3. Consistencia inmediata vs consistencia eventual

En un flujo síncrono es habitual esperar que todas las consecuencias de una operación hayan ocurrido antes de responder.

En un flujo asíncrono puede existir un periodo donde:

```text
reserva = creada
notificación = pendiente
auditoría = pendiente
```

Después:

```text
reserva = creada
notificación = enviada
auditoría = registrada
```

Ese intervalo corresponde a una forma sencilla de **consistencia eventual**.

No significa “datos incorrectos para siempre”. Significa que distintas partes del sistema pueden alcanzar el estado final en momentos diferentes.

## 4. Ejemplo de carga

Supongamos que llegan 1.000 solicitudes en pocos segundos.

Un consumer puede procesar solamente 50 mensajes por segundo.

Sin queue, un componente podría quedar sometido directamente al peak.

Con queue:

```text
Producer rápido
→ Queue acumula temporalmente
→ Consumer procesa a su ritmo
```

La queue funciona como buffer.

Esto no crea capacidad infinita. Si durante mucho tiempo entran más mensajes de los que salen, la cola seguirá creciendo. Por eso en sistemas reales se monitorean:

- profundidad de queue;
- tasa de publicación;
- tasa de consumo;
- tiempo de espera;
- consumers disponibles.

## 5. Fallo parcial

Consideremos:

```text
ReservaService
→ RabbitMQ
→ EmailConsumer
```

Si `EmailConsumer` deja de ejecutarse temporalmente, el producer puede seguir publicando mientras RabbitMQ permanezca disponible y la configuración permita conservar los mensajes.

Eso desacopla la disponibilidad del consumer respecto del producer.

Pero aparece un nuevo problema operacional:

> ¿Qué ocurre si el consumer nunca vuelve?

La mensajería no elimina la necesidad de recuperación. Solo permite administrarla de otra manera.

## 6. ¿Comando o evento?

Durante esta etapa no necesitamos formalizar completamente event-driven architecture, pero sí conviene distinguir dos intenciones.

### Comando

Expresa algo que se quiere ejecutar.

```text
enviar.notificacion
generar.documento
```

### Evento

Expresa algo que ya ocurrió.

```text
reserva.creada
pago.confirmado
```

Una diferencia conceptual útil:

```text
comando → "haz esto"
evento  → "esto ocurrió"
```

En Semana 08 usamos principalmente mensajes sencillos y eventos de aplicación para comprender el flujo.

## 7. Escenarios apropiados

Mensajería suele ser razonable para:

### Notificaciones

```text
usuario registrado
→ responder al usuario
→ email se procesa después
```

### Auditoría

```text
acción completada
→ publicar evento
→ auditoría registra posteriormente
```

### Procesamiento pesado

```text
solicitud de reporte
→ aceptar solicitud
→ worker genera reporte
```

### Integración desacoplada

```text
sistema A
→ evento
→ sistema B reacciona
```

## 8. Escenarios donde probablemente no conviene

### Validación necesaria para continuar

```text
¿usuario tiene permiso?
```

Si la respuesta determina inmediatamente si la operación puede seguir, una llamada síncrona suele ser natural.

### Consulta simple

```text
obtener detalle de una reserva
```

Agregar broker puede introducir complejidad sin beneficio claro.

### Flujo pequeño sin presión operacional

Si solo existe una operación rápida, estable y local, la mensajería puede convertirse en sobrearquitectura.

## 9. Preguntas de diseño

Antes de agregar RabbitMQ, pregunta:

1. ¿el emisor necesita la respuesta del receptor para continuar?;
2. ¿el trabajo puede ocurrir segundos después?;
3. ¿el receptor puede estar temporalmente fuera de servicio?;
4. ¿existen peaks de carga que conviene amortiguar?;
5. ¿más de una capacidad puede reaccionar al mismo hecho?;
6. ¿aceptamos consistencia eventual?;
7. ¿tenemos una razón concreta que justifique operar un broker?

Si varias respuestas favorecen procesamiento diferido, mensajería empieza a tener sentido.

## 10. Ejercicio mental

Caso:

> Una aplicación confirma una reserva y luego debe enviar un correo, registrar auditoría y recalcular un indicador.

Clasifica:

| Acción | ¿Síncrona o asíncrona? | Justificación |
|---|---|---|
| validar disponibilidad | | |
| crear reserva | | |
| enviar correo | | |
| registrar auditoría | | |
| recalcular indicador | | |

No existe una única respuesta universal. Lo importante es poder justificar cada decisión.

## 11. Idea clave

La asincronía no es una optimización automática.

Es una decisión arquitectónica que intercambia:

```text
menos acoplamiento temporal
por
más complejidad operacional y consistencia eventual
```

La pregunta profesional no es “¿podemos usar una queue?”, sino:

> “¿Qué problema concreto resolvemos al introducirla?”
