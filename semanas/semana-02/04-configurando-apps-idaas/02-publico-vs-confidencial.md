# Cliente público vs cliente confidencial

← [Volver a la profundización](./README.md)

La clasificación responde a una pregunta concreta:

> ¿Este cliente puede proteger de forma confiable una credencial propia?

## Cliente público

Ejemplos típicos:

- SPA ejecutada en el navegador;
- aplicación móvil;
- aplicación de escritorio distribuida a usuarios.

El código y los artefactos están bajo control del usuario final, por lo que un secreto embebido puede extraerse.

Por eso no debemos diseñar la seguridad suponiendo que un `client_secret` dentro de una SPA seguirá siendo secreto.

## Cliente confidencial

Ejemplos típicos:

- backend server-side;
- servicio que ejecuta en infraestructura controlada;
- aplicación capaz de custodiar credenciales fuera del alcance del usuario final.

Puede autenticarse ante el Authorization Server mediante mecanismos apropiados para clientes confidenciales.

## Relación con PKCE

En clientes públicos, Authorization Code + PKCE permite vincular el intercambio del authorization code con quien inició el flujo sin depender de un secreto estático embebido.

```text
SPA / móvil
→ cliente público
→ no confiar en client_secret embebido
→ Authorization Code + PKCE
```

## Error frecuente

> "Si ofusco el secreto dentro del JavaScript, entonces la SPA se vuelve confidencial."

No. La ofuscación dificulta la lectura, pero no transforma un entorno controlado por el usuario en un almacén seguro de secretos.