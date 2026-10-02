# Capacidad · Registro de usuario e identidad

## Contexto

Un sistema registra nuevos usuarios y debe ejecutar validaciones inmediatas y tareas posteriores.

## Comunicación síncrona posible

```text
ms-usuarios -> ms-identidad
```

El registro necesita validar, por ejemplo:

- que el correo no exista;
- que el identificador sea válido;
- que la cuenta pueda ser creada.

Sin esa respuesta, el flujo no puede continuar.

## Comunicaciones asíncronas posibles

Evento:

```text
UsuarioRegistrado
```

Consumers posibles:

- `ms-auditoria`: registra trazabilidad;
- `ms-notificaciones`: envía correo de bienvenida;
- `ms-riesgo`: analiza señales de comportamiento potencialmente riesgoso;
- `ms-analytics`: actualiza métricas de altas.

## Topología posible

```text
Exchange: usuarios.events
Routing key: usuario.registrado
Queues:
- auditoria.usuario-registrado
- notificaciones.usuario-registrado
```

## Payload mínimo

```json
{
  "usuarioId": 123,
  "email": "usuario@ejemplo.cl",
  "fechaRegistro": "2026-10-02T12:30:00"
}
```

## Pregunta de diseño

¿El usuario necesita esperar el envío del correo o el registro de auditoría para saber que su cuenta fue creada?
