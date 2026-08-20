# Compatibilidad y breaking changes

Una nueva versión se justifica por el impacto sobre consumidores, no por el simple hecho de haber cambiado código.

## Cambios generalmente compatibles

- agregar un campo opcional;
- agregar un endpoint nuevo;
- ampliar valores aceptados sin alterar los existentes.

## Cambios potencialmente incompatibles

- eliminar o renombrar campos;
- cambiar tipos de datos;
- volver obligatorio un dato antes opcional;
- cambiar semántica de status codes;
- alterar estructura de request/response.

## Pregunta clave

> ¿Un consumidor existente que no ha cambiado seguirá funcionando correctamente?

Si la respuesta es no, estamos frente a un posible breaking change.

## Compatibilidad semántica

Incluso sin cambiar JSON puede romperse el contrato. Por ejemplo, si `estado="ACTIVA"` antes significaba una cosa y ahora otra, el cambio semántico también afecta consumidores.

Por eso el contrato no es solo sintaxis: incluye comportamiento observable.