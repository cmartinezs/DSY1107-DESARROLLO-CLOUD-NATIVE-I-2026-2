# Semana 08 · Mensajería asíncrona y RabbitMQ

**Periodo:** 28 de septiembre al 3 de octubre de 2026  
**Experiencia de aprendizaje:** Desarrollando colas de mensajes  
**RA/IL:** RA2 · IL2.1

## Contenidos institucionales

- **2.1.1** Introducción a mensajería asíncrona y RabbitMQ.
- **2.1.2** Crear cola, productor y consumidor básicos.
- **2.1.3** Exchanges, bindings y routing keys aplicados al caso.

## Ruta de aprendizaje

1. [Asincronía, colas y RabbitMQ](./01-asincronia-y-colas.md)
2. [Hello World · Queue, Producer y Consumer](./02-hello-world-rabbitmq.md)
3. [Exchange, Binding y Routing Key](./03-exchange-binding-routing-key.md)
4. [Separación de responsabilidades](./04-separacion-responsabilidades.md)
5. [Evidencia y criterio de salida](./05-evidencia-y-salida.md)

## Capas de práctica

### Ejemplos

→ [Examples · Semana 08](../../examples/semana-08/)

### Ejercicios breves

→ [Ejercicios · Semana 08](../../ejercicios/semana-08/)

### Laboratorio guiado

→ [RabbitMQ + Spring AMQP](../../labs/rabbitmq-spring-amqp/)

### Transferencia

→ [RegistrApp · Semana 08](../../proyecto-formativo/semana-08/)

## Dos clases · 4 bloques cada una

### Clase 1

```text
problema de acoplamiento
→ síncrono vs asíncrono
→ Producer / Broker / Queue / Consumer
→ RabbitMQ Docker
→ Hello World
```

### Clase 2

```text
Exchange / Binding / Routing Key
→ ejemplos
→ ejercicios
→ lab
→ transferencia formativa
```

## Trabajo autónomo AVA

- guía Hello World;
- instalación/uso de Docker Desktop;
- material Spring AMQP producer/consumer.

## Fuera de alcance

Aún no se profundiza en acknowledgements avanzados, durabilidad, DLX/DLQ, cluster o retry. Esos temas aparecen en semanas 09–10.

## Criterio de salida

El estudiante debe publicar y consumir un mensaje, observar la topología en Management UI y explicar por qué el flujo fue desacoplado.
