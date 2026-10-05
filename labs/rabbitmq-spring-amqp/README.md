# Laboratorio · RabbitMQ + Spring AMQP

**Semanas:** 08–09  
**RA/IL:** RA2 · IL2.1–IL2.3  
**Modalidad:** local, guiada y reproducible  
**Foco:** broker, producer/consumer, exchanges, bindings, routing, Publish/Subscribe, ACK, durabilidad y DLX/DLQ.

## Propósito

Construir desde cero un flujo de mensajería asíncrona suficientemente pequeño para comprender cada pieza, pero suficientemente completo para observar routing, desacoplamiento y diagnóstico desde RabbitMQ Management UI; luego evolucionarlo para manejar procesamiento exitoso y fallos controlados.

## Resultado esperado

Al terminar Semana 08 el estudiante debe poder demostrar:

```text
Producer
→ DirectExchange
→ Binding
→ Queue
→ Consumer
```

Al terminar Semana 09 debe además poder demostrar:

```text
Consumer
├─ OK → ACK
└─ FAIL definitivo → DLX → DLQ
```

y distinguir ACK de durabilidad.

## Ruta del laboratorio

### Semana 08 · Flujo básico

1. [00 · Prerrequisitos y línea base](./00-prerrequisitos.md)
2. [01 · RabbitMQ con Docker Compose](./01-broker-docker.md)
3. [02 · Hello World Producer → Queue → Consumer](./02-hello-world.md)
4. [03 · DirectExchange, bindings y routing keys](./03-direct-exchange.md)
5. [04 · Management UI y diagnóstico](./04-management-ui.md)
6. [05 · Separación de responsabilidades](./05-separacion-responsabilidades.md)
7. [06 · Pruebas, evidencia y cierre](./06-pruebas-evidencia.md)

### Semana 09 · Confiabilidad

8. [07 · Work Queue vs Publish/Subscribe](./07-work-queue-vs-pubsub.md)
9. [08 · ACK manual](./08-manual-ack.md)
10. [09 · Durabilidad y persistencia](./09-durabilidad.md)
11. [10 · DLX/DLQ](./10-dlx-dlq.md)
12. [11 · TTL y dead-lettering](./11-ttl-y-dead-lettering.md)
13. [12 · Fallo controlado y diagnóstico](./12-fallo-controlado-y-diagnostico.md)

## Prerrequisitos conceptuales

Para Semana 08:

- [mensajería asíncrona](../../semanas/semana-08/01-asincronia-y-colas.md);
- [Hello World](../../semanas/semana-08/02-hello-world-rabbitmq.md);
- [Exchange/Binding/Routing Key](../../semanas/semana-08/03-exchange-binding-routing-key.md);
- [ejercicios breves](../../ejercicios/semana-08/).

Para Semana 09:

- [Work Queue vs Publish/Subscribe](../../semanas/semana-09/01-work-queue-vs-publish-subscribe.md);
- [ACK y fallos](../../semanas/semana-09/02-acknowledgements-y-fallos.md);
- [durabilidad](../../semanas/semana-09/03-durabilidad-y-persistencia.md);
- [DLX/DLQ](../../semanas/semana-09/04-dlx-dlq-y-retencion.md).

## Regla de avance

Cada etapa termina con un checkpoint observable. Si una etapa falla, se vuelve al último checkpoint verde en vez de cambiar producer, broker, consumer y configuración al mismo tiempo.

## Alcance progresivo

Semana 08 no introduce todavía ACK manual avanzado ni DLX/DLQ; esos mecanismos se incorporan en Semana 09.

Siguen fuera de alcance por ahora:

- retries complejos;
- publisher confirms;
- cluster RabbitMQ;
- alta disponibilidad;
- idempotencia distribuida avanzada.

## Transferencia

Solo después de entender el patrón se aplica al proyecto:

→ [RegistrApp · Semana 08](../../proyecto-formativo/semana-08/)  
→ [RegistrApp · Semana 09](../../proyecto-formativo/semana-09/)
