# Dinámica viva · Concepto sin marca

**Duración sugerida:** 10–15 minutos  
**Objetivo:** comprobar que el estudiante entiende responsabilidades antes de memorizar nombres de servicios cloud.

## Escenario

Una aplicación web permite a una persona iniciar sesión y consultar sus reservas.

La solución necesita:

- autenticar al usuario;
- obtener un token;
- exponer una única entrada HTTP;
- enrutar solicitudes a una API de reservas;
- permitir lectura solo si existe el permiso correspondiente;
- rechazar solicitudes sin credenciales;
- distinguir falta de autenticación de falta de permiso.

## Regla

Durante la primera parte **no se puede escribir AWS, Azure, GCP ni el nombre de ningún servicio comercial**.

## Parte 1 · Diseña por capacidades

Dibuja solamente componentes genéricos:

```text
usuario
cliente
proveedor de identidad / authorization server
gateway
API / resource server
```

Agrega las flechas necesarias y explica qué responsabilidad tiene cada componente.

## Parte 2 · Responde

1. ¿quién autentica al usuario?;
2. ¿quién emite el token?;
3. ¿quién recibe primero la petición hacia la API?;
4. ¿quién debe validar que el token sirve para la API?;
5. ¿qué situación debería producir 401?;
6. ¿qué situación debería producir 403?;
7. ¿qué información conceptual representa un scope como `reservations.read`?

## Parte 3 · Recién ahora mapea a cloud

Elige el proveedor correspondiente al laboratorio real y completa:

| Capacidad conceptual | Servicio/configuración cloud |
|---|---|
| identidad / authorization server | ... |
| gateway | ... |
| API/backend | ... |
| permisos/scopes | ... |
| logs/evidencia | ... |

## Cierre

La defensa correcta debe poder seguir este orden:

```text
problema
→ capacidad necesaria
→ responsabilidad
→ flujo
→ implementación concreta del proveedor
```

Si la explicación comienza por el nombre del servicio y no puede explicar qué problema resuelve, todavía falta comprensión conceptual.
