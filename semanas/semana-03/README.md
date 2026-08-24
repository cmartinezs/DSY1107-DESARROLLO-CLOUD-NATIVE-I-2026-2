# Semana 3 · Seguridad de APIs, JWT y usuarios externos

**Periodo:** 24 al 29 de agosto de 2026  
**Asignatura:** DSY1107 Desarrollo Cloud Native I

← [Volver al índice](../README.md)

## Regla de trabajo de Semana 3

Esta semana se mantiene explícitamente la separación entre **contenido** y **desafío transversal**.

### Vertical A · contenido de la semana

El contenido se aprende fuera de RegistrApp:

```text
concepto
→ explicación
→ ejemplo mínimo y autocontenido
→ mini práctica/laboratorio independiente
→ evidencia de comprensión
```

Los ejemplos pueden usar APIs, clientes y tokens ficticios distintos entre sí. No tienen que compartir dominio ni construir una aplicación longitudinal.

### Vertical B · desafío transversal RegistrApp

Solo después de comprender y practicar el contenido se transfiere lo aprendido a RegistrApp:

```text
contenido comprendido
→ decisión de diseño en RegistrApp
→ incremento
→ evidencia
→ checkpoint
```

→ [Definición canónica del desafío transversal](../../docs/DESAFIO-TRANSVERSAL-REGISTRAPP.md)

> **RegistrApp no es la ejemplificación del contenido. Es el desafío donde el estudiante aplica el contenido.**

## Propósito

Evolucionar desde los fundamentos OAuth2/OIDC e identidad hacia la **protección verificable de una API**, interpretación de JWT/claims y posterior mapeo a capacidades de API Manager e Identity as a Service.

La progresión conceptual es:

```text
OAuth2/OIDC
→ access token
→ JWT y claims
→ verificación
→ 401/403
→ política de gateway/API
→ mapeo a cloud
```

No se enseña configuración cloud como una secuencia de clics sin modelo mental previo.

## Contenidos de la semana

- JWT: estructura, claims y propósito;
- access token y su uso sobre APIs;
- decodificación vs validación/verificación;
- `issuer`, `audience`, expiración y scopes;
- decisiones 401 vs 403;
- responsabilidades de identidad, gateway y backend;
- seguridad aplicada al API Manager/gateway;
- aplicación orientada a usuarios externos / CIAM;
- mapeo de los conceptos al proveedor cloud disponible.

## Material de contenido

1. [JWT y claims](./01-jwt-claims.md)
2. [Seguridad de API y gateway](./02-seguridad-api.md)
3. [Laboratorio JWT forense](./03-laboratorio-jwt-forense.md)

Estos tres recursos deben poder trabajarse **sin RegistrApp**.

## Desafío transversal

4. [RegistrApp · checkpoint Semana 3](./04-desafio-registrapp.md)

Este recurso toma las competencias aprendidas en los tres materiales anteriores y pide transferirlas al desafío transversal.

## Planificación por sección

5. [DSY1107-002D · plan y checkpoint](./DSY1107-002D.md)
6. [DSY1107-003D · plan y checkpoint](./DSY1107-003D.md)

002D y 003D **no se sincronizan artificialmente**. Cada documento de sección declara desde qué checkpoint demostrable comienza y hasta dónde alcanzó realmente.

## Resultado técnico esperado del contenido

El estudiante debe poder:

- mirar un token y distinguir header, payload y signature;
- interpretar claims comunes;
- explicar por qué decodificar no equivale a confiar;
- relacionar `iss`, `aud`, `exp` y scopes con decisiones de acceso;
- provocar y explicar 401/403;
- ubicar responsabilidades entre proveedor de identidad, gateway y API;
- mapear después esos conceptos a una solución cloud.

## Evidencia mínima del contenido

- análisis de JWT sintéticos;
- tabla de casos de aceptación/rechazo;
- requests/responses reproducibles cuando exista starter local;
- explicación de 401 vs 403;
- diagrama técnico de un caso mínimo independiente;
- defensa breve de las decisiones.

## Evidencia separada de RegistrApp

El checkpoint del desafío puede incorporar arquitectura actualizada, scopes/claims propuestos, decisiones de validación y un incremento implementado cuando corresponda, pero **no reemplaza la evidencia del contenido semanal**.

## Seguridad

Nunca versionar client secrets, contraseñas, tokens reales reutilizables ni credenciales cloud. Los JWT usados como evidencia deben ser locales, sintéticos, expirados o sanitizados.

## Cierre obligatorio

Por sección registrar:

- último checkpoint demostrado;
- contenido efectivamente alcanzado;
- evidencia;
- bloqueos;
- deuda curricular;
- avance real de RegistrApp, si se trabajó;
- punto exacto de arranque de la siguiente clase.
