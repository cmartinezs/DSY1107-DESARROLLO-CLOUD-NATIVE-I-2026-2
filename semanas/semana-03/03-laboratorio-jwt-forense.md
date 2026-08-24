# 3 · Laboratorio · JWT forense

**Objetivo:** interpretar y defender decisiones de acceso antes de configurar un proveedor cloud.

Este laboratorio pertenece a la **vertical de contenido**. Se realiza con una API mínima ficticia y no con RegistrApp.

## Contexto del laboratorio

Usaremos conceptualmente:

```text
cliente → identidad → access token
cliente → gateway → products-api
```

Scopes de ejemplo:

```text
products.read
products.write
```

Los nombres son deliberadamente simples para concentrarse en JWT, claims y autorización.

## Parte 1 · anatomía

Usa JWT locales/sintéticos preparados para el laboratorio.

Identifica:

- header;
- payload;
- signature.

Decodifica header/payload y registra los claims encontrados.

## Parte 2 · tres sospechosos

Analiza tres tokens:

1. audience incorrecta;
2. token expirado;
3. token válido pero sin `products.write`.

Para cada uno responde:

- qué claim o condición falla;
- si el problema corresponde a autenticación o autorización;
- qué componente podría detectarlo;
- qué evidencia necesitas antes de confiar en el token.

## Parte 3 · matriz HTTP

Ejecuta llamadas contra una API mínima/starter de laboratorio cuando esté disponible:

1. sin `Authorization`;
2. bearer mal formado;
3. token inválido;
4. token válido con `products.read` sobre una lectura;
5. token válido sin `products.write` sobre una escritura;
6. token válido con scope correcto.

Registra:

- request mínimo;
- status;
- body;
- componente que produjo la respuesta;
- explicación del resultado.

## Parte 4 · decodificar no es confiar

Modifica manualmente el payload de un token de laboratorio sin regenerar correctamente la firma.

Comprueba que poder leer el payload modificado no lo convierte en un token confiable.

Esta experiencia se realiza únicamente con claves/tokens locales del laboratorio.

## Parte 5 · arquitectura mínima

Dibuja la arquitectura del laboratorio:

```text
usuario
  ↓
cliente → identidad
  │         ↓
  │      token
  ↓
gateway → products-api
```

Agrega:

- `iss`;
- `aud`;
- scopes;
- responsabilidades de validación;
- autorización técnica;
- regla de negocio que permanecería en el backend.

## Dinámica viva

Antes de ejecutar, cada equipo recibe tres payloads y debe emitir un dictamen:

- **aceptable**;
- **no aceptable**;
- **información insuficiente**.

Deben justificarlo con claims. Luego se revela la evidencia de firma/configuración para mostrar por qué mirar el payload nunca basta.

## Evidencia

- tabla de casos;
- respuestas HTTP reproducibles;
- diagrama del caso mínimo;
- explicación decodificación vs verificación;
- DevLog técnico.

## Después del laboratorio

Una vez comprendido y defendido este laboratorio, el estudiante puede aplicar las mismas competencias al checkpoint semanal de RegistrApp.

→ [RegistrApp · checkpoint Semana 3](./04-desafio-registrapp.md)

Nunca subir secretos ni tokens reales reutilizables.
