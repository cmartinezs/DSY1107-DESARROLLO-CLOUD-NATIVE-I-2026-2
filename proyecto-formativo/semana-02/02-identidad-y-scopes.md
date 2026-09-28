# 02 · Diseño conceptual de identidad

## Actores

Identificar en RegistrApp:

- Resource Owner;
- Client;
- Authorization Server / IdP;
- Resource Server.

## Diseño

Proponer scopes por capacidad, no por pantalla.

Ejemplo:

```text
reservas.read
reservas.write
```

Distinguir:

- ID Token: información de autenticación para el cliente;
- Access Token: credencial destinada a una API.

## Checkpoint

El grupo puede dibujar Authorization Code + PKCE y explicar qué componente recibe cada token.
