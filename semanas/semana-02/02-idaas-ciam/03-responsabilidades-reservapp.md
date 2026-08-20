# Responsabilidades: qué delega ReservApp y qué conserva

← [Volver a la profundización](./README.md)

Usar un IDaaS no significa delegar toda la seguridad ni toda la lógica de autorización.

## Responsabilidades que pueden delegarse

Una plataforma de identidad puede encargarse de:

- autenticación;
- almacenamiento y protección de credenciales;
- MFA;
- recuperación de cuenta;
- federación;
- emisión de tokens;
- registro de clientes;
- políticas generales de acceso;
- auditoría de eventos de identidad.

## Responsabilidades que ReservApp conserva

ReservApp sigue siendo responsable de:

- decidir qué recursos existen;
- definir reglas de negocio;
- validar que la identidad tenga permiso suficiente;
- comprobar propiedad de recursos;
- proteger datos;
- manejar errores y trazabilidad del dominio.

Ejemplo:

```text
IDaaS confirma:
"user-123 posee reservations.write"

ReservApp decide:
"¿user-123 puede cancelar ESTA reserva?"
```

Si la reserva pertenece a `user-456`, el backend puede responder `403 Forbidden` aunque el token sea válido.

## Frontera útil

```text
IDaaS
→ identidad y credenciales
→ tokens y capacidades delegadas

Gateway
→ controles transversales
→ validaciones técnicas

Backend
→ reglas de autorización del dominio
→ propiedad y consistencia del recurso
```

La arquitectura segura no elimina responsabilidades: las distribuye donde corresponde.