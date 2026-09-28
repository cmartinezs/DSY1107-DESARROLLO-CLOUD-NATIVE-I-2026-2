# Etapa 4 · Decodificar no es verificar

## Prueba conceptual

Modifica manualmente el payload de un JWT sintético sin regenerar correctamente la firma.

Observa:

```text
payload legible
≠
firma válida
```

## Diferencias

- **decode:** leer estructura;
- **verify:** firma + issuer + audience + tiempo;
- **authorize:** decidir si la operación está permitida.

## Checkpoint

Debes poder explicar por qué una herramienta que muestra claims no convierte automáticamente el token en confiable.
