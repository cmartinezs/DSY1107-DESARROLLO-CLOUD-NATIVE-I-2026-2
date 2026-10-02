# Ejemplos de capacidades · Actividad RabbitMQ

Esta carpeta complementa la [actividad evaluada de Semana 08](../actividad-evaluada-rabbitmq.md).

El objetivo es mostrar **posibilidades de diseño**, no entregar soluciones que deban copiarse literalmente.

Cada archivo representa una capacidad o contexto de negocio distinto y propone ejemplos de:

- comunicación síncrona;
- eventos o mensajes asíncronos;
- producer;
- consumers;
- exchange, routing keys y queues posibles;
- payload mínimo;
- preguntas de diseño.

Los equipos pueden inspirarse en estos ejemplos, combinarlos o proponer un dominio completamente diferente.

## Regla de lectura

Antes de implementar, pregunta:

> ¿Necesito esta respuesta para continuar el flujo ahora mismo?

Si la respuesta es **sí**, probablemente corresponde a comunicación síncrona.

Si la respuesta es **no**, y basta con informar que algo ocurrió para que otro componente actúe después, existe una buena oportunidad para mensajería asíncrona.

## Capacidades disponibles

- [Registro de usuario e identidad](./01-registro-usuario-identidad.md)
- [Comercio electrónico y compra](./02-ecommerce-compra.md)
- [Reservas y disponibilidad](./03-reservas-disponibilidad.md)
- [Logística y despacho](./04-logistica-despacho.md)
- [Banca y transferencias](./05-banca-transferencias.md)
- [Educación y matrícula](./06-educacion-matricula.md)
- [Salud y agendamiento](./07-salud-agendamiento.md)
- [Soporte y mesa de ayuda](./08-soporte-ticket.md)
- [Contenido digital y procesamiento](./09-contenido-procesamiento.md)
- [IoT y monitoreo](./10-iot-monitoreo.md)
