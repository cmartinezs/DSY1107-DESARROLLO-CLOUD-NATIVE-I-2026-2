# Responsabilidades y límites del gateway

Un gateway agrega valor cuando concentra preocupaciones **transversales** y evita duplicarlas en cada servicio.

## Responsabilidades apropiadas

- routing;
- autenticación técnica inicial;
- rate limiting y cuotas;
- observabilidad del tráfico;
- headers y transformaciones técnicas;
- CORS;
- exposición de versiones;
- políticas comunes.

## Responsabilidades que normalmente deben permanecer en el backend

- reglas de negocio;
- decisiones dependientes del estado del dominio;
- validaciones que necesitan datos internos;
- invariantes de negocio.

Ejemplo en ReservApp:

```text
Gateway
→ verifica que exista un access token válido

Backend
→ verifica que el usuario sea dueño de la reserva que intenta cancelar
```

## Riesgo: gateway demasiado inteligente

Si cada regla de negocio termina en el gateway, éste deja de ser una capa transversal y se convierte en un punto central de acoplamiento.

Una pregunta útil es:

> ¿Esta regla seguiría teniendo sentido si la petición llegara por otro canal distinto del gateway?

Si la respuesta es sí, probablemente pertenece al dominio/backend.