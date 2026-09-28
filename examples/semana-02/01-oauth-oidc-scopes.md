# Ejemplo · OAuth2/OIDC, scopes y tokens

## Escenario

```text
Usuario → SPA → Identity Provider → API
```

La SPA necesita leer pedidos.

Scope:

```text
orders.read
```

## Diferencia clave

- ID Token: información de autenticación para el cliente.
- Access Token: credencial presentada al recurso protegido.

## Casos

1. login correcto + sin Access Token → API no autorizada;
2. Access Token para otra audience → rechazo;
3. Access Token correcto sin `orders.read` → acceso denegado;
4. token correcto + scope correcto → request continúa.

## Pregunta

¿Por qué “el usuario pudo iniciar sesión” no implica que cualquier API deba aceptar la request?
