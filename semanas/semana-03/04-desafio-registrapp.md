# 4 · Desafío transversal · RegistrApp · checkpoint Semana 3

## Propósito

Este documento **no enseña JWT ni seguridad de APIs**. Su función es transferir a RegistrApp lo aprendido previamente en los materiales y laboratorios de la Semana 3.

Antes de comenzar, el estudiante debe haber trabajado al menos:

- anatomía de JWT;
- `iss`, `aud`, `exp` y scopes;
- decodificación vs verificación;
- 401 vs 403;
- separación de responsabilidades entre identidad, gateway y backend.

## Pregunta de transferencia

> ¿Cómo debería evolucionar RegistrApp para aplicar correctamente las decisiones de identidad y seguridad aprendidas esta semana?

No existe una única respuesta válida. Las decisiones deben justificarse.

## Incremento esperado

### 1. Arquitectura

Actualiza el diagrama de RegistrApp e identifica explícitamente:

- usuario;
- aplicación cliente;
- Identity Provider / Authorization Server;
- API Gateway;
- API o APIs protegidas.

### 2. Token

Define conceptualmente para el access token:

- issuer esperado;
- audience esperada;
- scopes necesarios;
- expiración;
- claims adicionales solo si realmente aportan una decisión.

No uses secretos ni tokens reales.

### 3. Responsabilidades

Para cada control indica quién debería ejecutarlo y por qué:

| Control | ¿Identidad, gateway o backend? | Justificación |
|---|---|---|
| emisión del token | | |
| firma / integridad | | |
| issuer | | |
| audience | | |
| expiración | | |
| scope | | |
| regla de negocio | | |

### 4. Matriz de acceso

Define al menos cuatro escenarios propios de RegistrApp:

1. solicitud sin token;
2. token inválido o expirado;
3. token válido sin permiso suficiente;
4. token válido con permiso suficiente.

Agrega al menos un quinto caso donde el token sea técnicamente válido, pero una regla de negocio impida la operación.

### 5. Decisión de diseño

Registra al menos una decisión técnica de esta semana en formato breve:

```text
Decisión:
Motivo:
Alternativa descartada:
Consecuencia:
```

## Evidencia del checkpoint

El checkpoint de Semana 3 debe dejar:

- diagrama actualizado;
- propuesta de `iss`, `aud` y scopes;
- matriz de responsabilidades;
- matriz de casos de acceso;
- una decisión técnica justificada;
- DevLog con qué se logró, qué falta y qué se retomará después.

## Regla de autonomía

El profesor puede revisar, preguntar y tensionar las decisiones, pero el desafío no debe transformarse en una copia de una implementación mostrada previamente.

La evidencia debe demostrar **transferencia**, no repetición.

## Si la sección todavía no llega a JWT

No se fuerza este checkpoint.

RegistrApp avanza únicamente hasta el contenido realmente comprendido por la sección. El documento se retoma cuando los prerrequisitos estén demostrados.
