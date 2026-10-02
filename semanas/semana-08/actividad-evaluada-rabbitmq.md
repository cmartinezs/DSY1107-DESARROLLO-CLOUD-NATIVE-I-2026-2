# Actividad evaluada · Diseño e implementación de comunicación con RabbitMQ

> **Ponderación:** 10% de la Evaluación Parcial 2 (EV2)  
> **Modalidad:** trabajo con informe, sin presentación  
> **Parte 1 · Diseño:** entrega viernes 2 de octubre de 2026  
> **Parte 2 · Implementación:** entrega lunes 5 de octubre de 2026

## Propósito

Diseñar e implementar una solución simple basada en microservicios que permita distinguir correctamente cuándo una comunicación debe resolverse de forma **síncrona** y cuándo conviene desacoplarla mediante **mensajería asíncrona con RabbitMQ**.

La actividad no busca agregar RabbitMQ por obligación. El objetivo principal es justificar las decisiones de comunicación de una arquitectura y demostrar posteriormente que el flujo asíncrono diseñado puede implementarse y observarse en funcionamiento.

## Idea central

En una solución distribuida no todas las operaciones necesitan comportarse de la misma forma.

Una comunicación **síncrona** es apropiada cuando el flujo necesita una respuesta inmediata para poder continuar.

Una comunicación **asíncrona** es apropiada cuando un componente necesita informar que algo ocurrió y otro componente puede procesarlo después, sin bloquear la respuesta principal.

Ejemplo:

```text
Cliente
   |
   | POST /usuarios
   v
ms-usuarios
   |
   |---- comunicación síncrona ----> ms-validacion
   |
   |---- UsuarioRegistrado --------> RabbitMQ
                                      |
                                      +--> ms-auditoria
                                      |
                                      +--> ms-notificaciones
```

En este ejemplo, una validación necesaria para decidir si el registro puede continuar requiere una respuesta inmediata.

En cambio, una vez registrado el usuario, acciones como registrar auditoría, enviar una notificación o iniciar un análisis posterior no necesitan bloquear la respuesta al cliente.

## Requerimientos mínimos de arquitectura

La solución propuesta debe incorporar como mínimo:

- **1 comunicación síncrona** entre responsabilidades o microservicios;
- **2 comunicaciones asíncronas** mediante RabbitMQ;
- al menos un **producer**;
- al menos un **exchange**;
- las **queues** necesarias para representar los flujos diseñados;
- al menos **2 consumers** o dos responsabilidades consumidoras claramente diferenciadas;
- routing keys y bindings coherentes con el caso planteado.

Las dos comunicaciones asíncronas pueden originarse desde un mismo evento cuando tenga sentido.

Por ejemplo:

```text
UsuarioRegistrado
    |
    v
 RabbitMQ
    |
    +--> auditoría
    |
    +--> notificaciones
```

## Parte 1 · Diseño

**Entrega: viernes 2 de octubre de 2026.**

En esta etapa no es necesario implementar código.

Cada equipo debe diseñar una solución y entregar un informe breve que permita comprender la arquitectura propuesta.

El informe debe incluir:

1. problema o contexto que resolverá el sistema;
2. microservicios o responsabilidades principales;
3. descripción del flujo principal;
4. identificación de las comunicaciones síncronas;
5. identificación de las comunicaciones asíncronas;
6. justificación de por qué cada comunicación fue clasificada de esa forma;
7. eventos o mensajes que circularán mediante RabbitMQ;
8. producer de cada mensaje;
9. consumer o consumers asociados;
10. exchange, queue, routing key y binding propuestos;
11. payload mínimo esperado para los mensajes;
12. diagrama de arquitectura o flujo.

La pregunta que debe poder responder el diseño es:

> **¿Qué necesita ocurrir inmediatamente para continuar el flujo y qué puede ocurrir después sin bloquear al usuario?**

### Ejemplo de definición de mensaje

```text
Evento:
UsuarioRegistrado

Producer:
ms-usuarios

Exchange:
usuarios.exchange

Routing key:
usuario.registrado

Consumers:
- ms-auditoria
- ms-notificaciones

Datos mínimos:
- usuarioId
- email
- fechaRegistro
```

## Parte 2 · Implementación

**Entrega: lunes 5 de octubre de 2026.**

A partir del diseño entregado en la Parte 1, el equipo debe implementar el flujo definido.

La implementación debe demostrar como mínimo:

- la comunicación síncrona diseñada;
- publicación de mensajes mediante RabbitMQ;
- configuración del exchange;
- configuración de queues;
- bindings y routing keys;
- producer operativo;
- consumers operativos;
- procesamiento observable del mensaje;
- evidencia en RabbitMQ Management;
- separación razonable entre infraestructura de mensajería y lógica de aplicación.

No es necesario implementar todavía mecanismos avanzados como retries complejos, DLQ, clustering o idempotencia avanzada.

## Informe final

La actividad se entrega **con informe y sin presentación**.

El informe final debe incorporar el diseño de la Parte 1 y complementarlo con evidencia de la implementación.

Debe permitir seguir el recorrido completo de una operación:

```text
solicitud
→ procesamiento principal
→ comunicación síncrona cuando corresponde
→ publicación de evento
→ exchange
→ routing
→ queue
→ consumer
→ procesamiento asíncrono
```

Se deben incluir evidencias suficientes, por ejemplo:

- fragmentos relevantes del código;
- capturas de RabbitMQ Management;
- queues y exchanges creados;
- mensajes publicados y consumidos;
- logs de producer y consumers;
- breve explicación de los resultados obtenidos.

## Criterios generales de revisión

Se observará especialmente:

- correcta diferencia entre comunicación síncrona y asíncrona;
- justificación técnica de las decisiones;
- coherencia de la arquitectura;
- pertinencia de los mensajes definidos;
- funcionamiento del producer y consumers;
- uso coherente de exchange, queue, binding y routing key;
- claridad del informe;
- evidencia que permita verificar el recorrido del mensaje.

> **Importante:** no se evalúa positivamente utilizar RabbitMQ donde no aporta valor. La decisión arquitectónica debe poder justificarse.

## Relación con EV2

Esta actividad corresponde al **10% de la Evaluación Parcial 2 (EV2)** y permite evidenciar tempranamente la capacidad de diseñar e implementar comunicación distribuida utilizando los conceptos trabajados durante la Semana 8.


## Ejemplos de capacidades y dominios

Para ampliar las posibilidades de diseño y evitar que todos los equipos resuelvan el mismo caso, existe una colección de ejemplos separados por dominio de negocio.

Cada ejemplo muestra posibles decisiones síncronas y asíncronas, eventos, consumers y preguntas que ayudan a razonar la arquitectura.

> Los ejemplos son referencias de comprensión. No constituyen una solución obligatoria ni una plantilla que deba copiarse.

→ [Explorar ejemplos de capacidades](./actividad-evaluada-rabbitmq-ejemplos/)
