# 01 · Transferencia de gestión de APIs

## Gate de entrada

Sólo transferir gateway, versionado o CORS si el equipo puede explicar esos conceptos fuera de RegistrApp.

## Incremento

Evolucionar:

```text
Cliente → API
```

hacia:

```text
Cliente → Gateway → API
```

cuando corresponda.

Registrar:

- ruta pública del gateway;
- integración/destino;
- estrategia de versión;
- política CORS necesaria para el cliente real.

## Checkpoint

Una request puede seguirse desde cliente a gateway y API, indicando qué responsabilidad corresponde a cada frontera.
