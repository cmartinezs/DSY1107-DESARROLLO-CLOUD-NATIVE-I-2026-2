# 2 · Seguridad de API y gateway

## Flujo mental

```text
cliente
→ obtiene token desde identidad
→ llama gateway con Authorization: Bearer <token>
→ gateway aplica controles técnicos
→ backend recibe solicitud válida
→ backend aplica autorización que le corresponde
```

## ¿Qué puede validar el gateway?

Según producto y arquitectura: presencia/formato del token, firma, issuer, audience, expiración, scopes/claims y políticas de ruta. Centralizar controles repetitivos puede reducir duplicación, pero no convierte al gateway en dueño de todas las reglas de negocio.

## ¿Qué conserva el backend?

Las reglas que dependen del dominio. Ejemplo: tener `reservations.write` puede habilitar la operación técnica, pero decidir si un usuario puede modificar **esa reserva específica** puede requerir ownership, estado de reserva u otra regla que pertenece a ReservApp.

## Casos de prueba

Construye una matriz y ejecútala cuando el laboratorio esté disponible:

| Caso | Token | Permiso | Resultado esperado |
|---|---|---|---|
| A | ausente | — | rechazo de autenticación |
| B | inválido/expirado | — | rechazo de autenticación |
| C | válido | scope insuficiente | rechazo de autorización |
| D | válido | scope correcto | acceso técnico permitido |
| E | válido | scope correcto, regla negocio falla | backend rechaza |

No memorices el status sin observar qué componente emitió la respuesta.

## Usuarios externos / CIAM

En una aplicación orientada a clientes externos, identidad debe considerar registro/inicio de sesión, ciclo de vida de usuario, aplicaciones cliente, redirect URIs, consentimiento/permisos y experiencia de acceso. El diseño se hace primero en términos de capacidades; después se mapea al proveedor real disponible.

## Checkpoint

Dibuja ReservApp con usuario, cliente, IdP, gateway y API. Sobre cada flecha escribe qué artefacto circula y sobre cada componente qué valida o decide. Si dos componentes parecen decidir exactamente lo mismo, justifica por qué o corrige el diseño.