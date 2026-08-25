# Guías integradas de implementación real

Esta raíz contiene **rutas de implementación end-to-end** que conectan los contenidos de DSY1107 con una solución real, reproducible y verificable.

## Qué problema resuelve

Las guías curriculares explican correctamente cada concepto por separado. Sin embargo, una implementación cloud real introduce dependencias que pueden quedar implícitas: una guía de CORS puede requerir la URL de un frontend que todavía no existe; una guía de OAuth2 puede asumir que ya existe un client registrado; un API Gateway puede necesitar un issuer y audience que aún no fueron creados.

Esta vertical elimina esas dependencias ocultas.

## Relación con las otras verticales

```text
semanas/
  qué se aprende y cuándo

examples/
  ejemplos pequeños y autocontenidos

labs/
  práctica acotada de una competencia

proyecto-formativo/
  RegistrApp: transferencia longitudinal y comprensión del concepto

guias-integradas/
  implementación real completa del encargo institucional
```

`guias-integradas/` **no sustituye RegistrApp**. Tampoco reemplaza las guías de `semanas/`. Las usa como referencia conceptual y agrega continuidad operacional.

## Contrato de una guía integrada

Cada guía integrada debe cumplir estas reglas:

1. declarar el estado inicial esperado;
2. enumerar herramientas, cuentas y permisos necesarios;
3. proporcionar o construir todos los componentes que la guía utiliza;
4. no pedir una URL, ID, secret, tenant, scope, recurso o aplicación que no haya sido creada previamente;
5. indicar exactamente dónde obtener cada valor dinámico;
6. diferenciar valores de ejemplo de valores reales del estudiante;
7. incluir comprobaciones de éxito antes de continuar;
8. incluir errores frecuentes y cómo diagnosticarlos;
9. indicar cómo volver a un estado conocido cuando un paso falla;
10. evitar secretos y credenciales versionadas;
11. incluir pruebas positivas y negativas;
12. terminar con una evidencia observable y reproducible.

## EV1

La primera guía integrada es:

→ [EV1 · FullStack + IDaaS + OAuth2/OIDC + JWT + API Gateway + CORS + AWS/Azure](./ev1/README.md)

La implementación de referencia utiliza:

- frontend Angular;
- Microsoft Authentication Library (MSAL);
- Microsoft Entra External ID para identidad;
- OAuth 2.0 + OpenID Connect;
- Authorization Code + PKCE;
- JWT;
- backend Spring Boot;
- AWS EC2 para backend;
- AWS API Gateway HTTP API;
- CORS configurado explícitamente;
- despliegue del frontend en AWS.

La aplicación de referencia es deliberadamente mínima: su objetivo es hacer visibles los conceptos evaluados, no enseñar un dominio de negocio nuevo.