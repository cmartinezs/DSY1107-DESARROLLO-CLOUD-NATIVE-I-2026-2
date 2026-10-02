# Capacidad · Banca y transferencias

## Contexto

Un cliente solicita una transferencia entre cuentas.

## Comunicación síncrona posible

```text
ms-transferencias -> ms-cuentas
```

Antes de confirmar la operación se necesita validar:

- existencia de la cuenta;
- saldo suficiente;
- estado de la cuenta.

## Comunicaciones asíncronas posibles

Evento:

```text
TransferenciaRealizada
```

Consumers posibles:

- `ms-notificaciones`: informa al cliente;
- `ms-auditoria`: registra el movimiento;
- `ms-antifraude`: procesa señales adicionales;
- `ms-reportes`: actualiza consolidados.

## Advertencia conceptual

No todo análisis antifraude debe ser asíncrono.

Si una regla antifraude determina si la transferencia puede ejecutarse, esa validación debe formar parte del flujo que responde antes de confirmar la operación.

En cambio, análisis posteriores o enriquecimiento de señales pueden procesarse de manera asíncrona.

## Pregunta de diseño

¿Qué controles son condición para autorizar la transferencia y cuáles son actividades posteriores?
