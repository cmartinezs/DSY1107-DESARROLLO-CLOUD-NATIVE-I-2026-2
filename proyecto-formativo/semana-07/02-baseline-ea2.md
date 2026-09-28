# 02 · Baseline de entrada a EA2

Antes de iniciar mensajería, dejar explícito qué arquitectura existe.

## Inventario

- frontend;
- IdP/IDaaS;
- Gateway;
- backend(s);
- persistencia relevante;
- puntos donde hoy existen llamadas síncronas.

## Salida

Identificar **candidatos** a asincronía sin implementar todavía RabbitMQ.

Ejemplos: notificación, auditoría, procesamiento posterior.

La selección final se realiza en Semana 8 después de aprender el patrón fuera de RegistrApp.
