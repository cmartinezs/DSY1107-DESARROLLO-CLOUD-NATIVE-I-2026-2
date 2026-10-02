# Capacidad · Salud y agendamiento

## Contexto

Un paciente agenda una hora médica.

## Comunicación síncrona posible

```text
ms-agenda -> ms-disponibilidad
```

Antes de confirmar se necesita verificar que el profesional tenga el bloque disponible.

## Comunicaciones asíncronas posibles

Evento:

```text
HoraAgendada
```

Consumers posibles:

- `ms-recordatorios`: programa avisos;
- `ms-notificaciones`: envía confirmación;
- `ms-integraciones`: sincroniza con otra agenda;
- `ms-auditoria`: registra trazabilidad.

## Pregunta de diseño

¿Cuáles de estas tareas deben completarse para confirmar la hora y cuáles pueden ocurrir después?

## Nota

El ejemplo busca practicar arquitectura. No reemplaza requisitos reales de seguridad, privacidad o cumplimiento asociados a sistemas clínicos.
