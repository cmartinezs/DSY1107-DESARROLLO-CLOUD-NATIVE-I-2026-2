# 2 · Seguridad de API y gateway

## Objetivo

Comprender qué controles puede aplicar un gateway, cuáles deben permanecer en el backend y cómo distinguir autorización técnica de reglas de negocio.

Este contenido se trabaja con un caso mínimo independiente de RegistrApp.

## Caso de ejemplo

Supongamos una API ficticia de productos:

```text
cliente
→ obtiene access token
→ llama gateway con Authorization: Bearer <token>
→ gateway aplica controles técnicos
→ products-api recibe solicitud válida
→ backend aplica reglas de negocio
```

## ¿Qué puede validar el gateway?

Según producto y arquitectura, el gateway puede validar:

- presencia y formato del token;
- firma;
- issuer;
- audience;
- expiración;
- scopes/claims;
- políticas asociadas a una ruta.

Centralizar controles repetitivos puede reducir duplicación, pero no convierte al gateway en dueño de todas las reglas de negocio.

## ¿Qué conserva el backend?

Las reglas que dependen del dominio.

Ejemplo independiente:

Tener el scope `products.write` puede habilitar técnicamente una operación `PUT /products/{id}`. Sin embargo, decidir si ese producto se puede modificar por su estado, propietario o regla comercial puede seguir perteneciendo al backend.

## Casos de prueba

Construye una matriz y ejecútala cuando el starter local esté disponible:

| Caso | Token | Permiso | Resultado esperado |
|---|---|---|---|
| A | ausente | — | rechazo de autenticación |
| B | inválido/expirado | — | rechazo de autenticación |
| C | válido | scope insuficiente | rechazo de autorización |
| D | válido | scope correcto | acceso técnico permitido |
| E | válido | scope correcto, regla de negocio falla | backend rechaza |

No memorices el status sin observar qué componente emitió la respuesta.

## Usuarios externos / CIAM

En una aplicación orientada a clientes externos, identidad debe considerar capacidades como:

- registro e inicio de sesión;
- recuperación de cuenta;
- ciclo de vida del usuario;
- aplicaciones cliente;
- redirect URIs;
- consentimiento/permisos;
- experiencia de acceso.

El diseño se hace primero en términos de capacidades y responsabilidades; después se mapea al proveedor real disponible.

## Mini ejercicio independiente

Dibuja un caso pequeño con:

- usuario;
- cliente;
- IdP;
- gateway;
- `products-api`.

Sobre cada flecha escribe qué artefacto circula. Sobre cada componente indica qué valida o decide.

Después responde:

1. ¿qué rechazo produciría el gateway si falta token?;
2. ¿qué rechazo debería ocurrir si el token es válido pero no tiene `products.write`?;
3. ¿qué regla dejarías necesariamente en el backend?;
4. ¿por qué mover toda la autorización al gateway sería un error?

## Cierre

Antes de transferir este contenido a RegistrApp, debes poder defender la separación:

```text
gateway → controles técnicos y políticas transversales
backend → reglas de negocio y decisiones de dominio
```

> **Si quieres profundizar:** revisa [Profundización · Seguridad de API y gateway](./02-seguridad-api/README.md) para estudiar frontera gateway/backend, pipeline 401/403, scopes/roles/claims y CIAM con APIs protegidas.