# RegistrApp · Requerimientos transversales de aprendizaje

Este documento define los **requerimientos técnicos acumulativos** del proyecto formativo. No fija requisitos funcionales de negocio que no estén respaldados por el caso real del grupo.

## Principio

RegistrApp es un proyecto vivo que incorpora capacidades a medida que son aprendidas.

## Experiencia 1 · API Management e identidad

El sistema debe evolucionar hasta poder demostrar, según el alcance efectivo del grupo:

- API con contrato claro;
- gateway / API Manager;
- versionado y políticas transversales;
- Identity as a Service;
- SPA como cliente público;
- Access Token para API propia;
- backend protegido;
- 401/403/2xx reproducibles.

## Experiencia 2 · Mensajería asíncrona

Debe incorporarse al menos una capacidad que justifique desacoplamiento mediante:

- producer;
- exchange;
- routing key;
- queue;
- consumer;
- evidencia de operación;
- separación entre infraestructura y negocio.

Las capacidades avanzadas se incorporan cuando sean enseñadas: acknowledgements, durabilidad, DLX/DLQ, monitoreo y cluster.

## Experiencia 3 · Streaming

Cuando corresponda curricularmente, el proyecto debe incorporar Kafka sin reemplazar artificialmente RabbitMQ cuando ambas capacidades resuelven problemas distintos.

La evolución considerará:

- topic;
- producer/consumer;
- particiones;
- consumer groups;
- offsets;
- escalamiento/retención;
- monitoreo;
- manejo de errores.

## Reglas de continuidad

1. no reiniciar el proyecto cada semana;
2. no adelantar tecnologías no trabajadas;
3. registrar deuda;
4. conservar evidencia;
5. mantener secretos fuera del repositorio;
6. justificar por qué cada componente existe;
7. preferir un incremento pequeño y demostrable a una arquitectura grande no reproducible.

## Autoridad

Los encargos oficiales de evaluaciones prevalecen cuando establecen requisitos específicos. Este documento describe la evolución formativa del proyecto, no sustituye una pauta sumativa.
