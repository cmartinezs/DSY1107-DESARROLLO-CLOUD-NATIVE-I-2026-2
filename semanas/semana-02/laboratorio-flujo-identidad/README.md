# Laboratorio · ReservApp · Diseñando un flujo OAuth2/OIDC

**Duración sugerida:** 25–35 minutos.  
**Modalidad:** grupos de máximo 3 integrantes.  
**Prerequisito:** haber cerrado o comprendido el laboratorio de API Gateway de Semana 01.

Este laboratorio **no utiliza Azure**. El objetivo es construir y defender el flujo antes de tocar un proveedor concreto.

> **Dominio formativo transversal:** desde esta semana, los ejemplos, ejercicios y laboratorios de clase de DSY1107 utilizarán **ReservApp** siempre que sea técnicamente pertinente. El sistema irá evolucionando durante el semestre. Los casos de evaluación sumativa se mantienen separados.

## Caso

**ReservApp** es una aplicación para gestionar reservas de servicios. En esta etapa posee:

- una aplicación web utilizada por clientes;
- una API de reservas;
- un API Gateway;
- usuarios normales y operadores;
- operaciones de consulta, creación y cancelación de reservas.

Se incorporará un proveedor de identidad en una etapa posterior. Por ahora deben diseñar correctamente la integración.

## Parte 1 · Identificar actores

Dibujen el flujo e identifiquen explícitamente:

- Resource Owner;
- Client;
- Authorization Server / Identity Provider;
- Resource Server;
- API Gateway.

No basta con poner cajas: indiquen **qué mensaje o artefacto viaja entre ellas**.

## Parte 2 · Autenticación vs autorización

Clasifiquen cada situación:

1. un usuario inicia sesión;
2. la API comprueba que el token no expiró;
3. un operador puede cancelar cualquier reserva;
4. un cliente puede consultar solamente sus propias reservas;
5. una petición llega sin token.

Para cada una indiquen si corresponde principalmente a autenticación, autorización o validación técnica del token.

## Parte 3 · Diseñar scopes

Propongan como mínimo:

```text
reservations.read
reservations.write
```

Decidan qué actor debería recibir cada scope y justifiquen.

Luego discutan: ¿`reservations.write` basta para asegurar que un cliente solo modifique o cancele sus propias reservas? Expliquen dónde debe vivir esa regla.

## Parte 4 · Access token vs ID token

Completen una tabla propia con:

- quién consume cada token;
- qué propósito tiene;
- qué ocurriría si se enviara el ID token a la API por error.

## Parte 5 · 401 vs 403

Para cada escenario indiquen el status esperado y por qué:

- no hay token;
- token expirado;
- token válido sin `reservations.write`;
- token válido con scope suficiente pero intentando cancelar una reserva de otro usuario.

## Parte 6 · Inspección de un JWT de ejemplo

Usen el siguiente **payload didáctico** (no es un token real):

```json
{
  "iss": "https://identity.example.edu",
  "sub": "user-1024",
  "aud": "reservapp-api",
  "exp": 1787000000,
  "scope": "reservations.read reservations.write",
  "role": "customer"
}
```

Respondan:

1. ¿quién emitió el token?;
2. ¿para qué API fue emitido?;
3. ¿qué permisos declara?;
4. ¿qué claim identifica al sujeto?;
5. ¿qué información adicional necesitaría el backend para validar la regla “solo mis reservas”?

## Parte 7 · Integración con el gateway

Actualicen el diagrama trabajado con ReservApp:

```text
cliente → identidad → cliente con token → gateway → reservapp-api
```

Marquen en el diagrama dónde se realizan:

- autenticación;
- validación técnica del token;
- autorización por scope;
- autorización de negocio.

## Entrega mínima

Repositorio grupal con:

- `README.md`;
- diagrama Mermaid;
- tabla 401/403;
- scopes propuestos;
- respuestas de análisis;
- una sección **“Qué cambiará cuando configuremos un proveedor real”**.

## Defensa técnica

Cada integrante debe poder explicar en aproximadamente un minuto:

1. por qué OAuth2 y OIDC no son lo mismo;
2. para qué sirve el access token;
3. qué responsabilidad queda en gateway y cuál en backend.

No se requiere PPT.

## Continuidad

No desechen este trabajo. El diagrama, las decisiones de autorización y el modelo de ReservApp se reutilizarán y evolucionarán en las siguientes experiencias de aprendizaje.