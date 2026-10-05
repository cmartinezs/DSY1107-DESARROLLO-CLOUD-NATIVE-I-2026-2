# Parcial 2 · Mensajería Asíncrona con RabbitMQ

## Trazabilidad
- RA2;
- IL2.1, IL2.2, IL2.3;
- preparación semanas 08–10;
- evaluación prevista para semana 11.

## Propósito
Continuar el caso semestral incorporando una capa de mensajería que permita desacoplar capacidades y manejar explícitamente procesamiento exitoso y fallos.

## Alcance técnico mínimo
- configuración centralizada de queues, exchanges y bindings;
- consumers con ACK y manejo de errores;
- servicio de administración de colas utilizado;
- topología coherente con el caso;
- dead-lettering cuando corresponda;
- capacidad de explicar durabilidad, entrega y fallos.

## Defensa técnica
- presencial;
- cara a cara con el docente;
- aunque la solución sea grupal, la comprobación técnica puede ser individual;
- si Semana 11 no alcanza, las defensas pueden extenderse a la semana siguiente.

## Preparación
→ [Semana 08](../semanas/semana-08/)  
→ [Semana 09](../semanas/semana-09/)  
→ [Lab RabbitMQ + Spring AMQP](../labs/rabbitmq-spring-amqp/)  
→ [RegistrApp · Semana 09](../proyecto-formativo/semana-09/)

## Actividad evaluada · Semana 08
Se conserva como preparación progresiva de EV2:
- **Ponderación:** 10% de EV2.
- **Parte 1:** viernes 2 de octubre de 2026.
- **Parte 2:** lunes 5 de octubre de 2026.
- **Modalidad:** informe, sin presentación.
- **Mínimo:** 1 comunicación síncrona y 2 asíncronas justificadas.

→ [Actividad evaluada · Semana 08](../semanas/semana-08/actividad-evaluada-rabbitmq.md)

## Preguntas esperables
- ¿Por qué esta operación es asíncrona?
- ¿Work Queue o Publish/Subscribe?
- ¿Cuándo se envía ACK?
- ¿Qué ocurre si el consumer falla antes?
- ¿ACK y durabilidad son lo mismo?
- ¿Por qué un mensaje llega a DLQ?
- ¿Qué observarías para detectar acumulación o fallos?
