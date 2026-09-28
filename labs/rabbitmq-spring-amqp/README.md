# Laboratorio · RabbitMQ + Spring AMQP

**Semana:** 08  
**RA/IL:** RA2 · IL2.1  
**Modalidad:** local, guiada y reproducible  
**Foco:** broker, producer/consumer, DirectExchange, bindings, routing keys y observación

## Propósito

Construir desde cero un flujo de mensajería asíncrona suficientemente pequeño para comprender cada pieza, pero suficientemente completo para observar routing, desacoplamiento y diagnóstico desde RabbitMQ Management UI.

## Resultado esperado

Al terminar el estudiante debe poder demostrar:

```text
Producer
→ DirectExchange
→ Binding
→ Queue
→ Consumer
```

y explicar por qué la lógica de negocio no debe quedar acoplada al listener ni a la configuración del broker.

## Ruta del laboratorio

1. [00 · Prerrequisitos y línea base](./00-prerrequisitos.md)
2. [01 · RabbitMQ con Docker Compose](./01-broker-docker.md)
3. [02 · Hello World Producer → Queue → Consumer](./02-hello-world.md)
4. [03 · DirectExchange, bindings y routing keys](./03-direct-exchange.md)
5. [04 · Management UI y diagnóstico](./04-management-ui.md)
6. [05 · Separación de responsabilidades](./05-separacion-responsabilidades.md)
7. [06 · Pruebas, evidencia y cierre](./06-pruebas-evidencia.md)

## Prerrequisitos conceptuales

Antes de entrar al lab:

- [mensajería asíncrona](../../semanas/semana-08/01-asincronia-y-colas.md);
- [Hello World](../../semanas/semana-08/02-hello-world-rabbitmq.md);
- [Exchange/Binding/Routing Key](../../semanas/semana-08/03-exchange-binding-routing-key.md);
- [ejercicios breves](../../ejercicios/semana-08/).

## Regla de avance

Cada etapa termina con un checkpoint observable. Si una etapa falla, se vuelve al último checkpoint verde en vez de cambiar producer, broker, consumer y configuración al mismo tiempo.

## Fuera de alcance

Semana 08 no introduce aún:

- acknowledgements manuales avanzados;
- retries;
- DLX/DLQ;
- publisher confirms;
- cluster RabbitMQ;
- alta disponibilidad.

Esos temas pertenecen a semanas posteriores según cronograma.

## Transferencia

Solo después de entender el patrón se aplica una capacidad similar en RegistrApp:

→ [RegistrApp · Semana 08](../../proyecto-formativo/semana-08/)
