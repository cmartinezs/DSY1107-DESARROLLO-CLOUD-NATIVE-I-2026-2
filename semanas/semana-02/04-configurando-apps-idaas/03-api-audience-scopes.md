# API, audience y scopes

← [Volver a la profundización](./README.md)

Cuando registramos una API o recurso protegido, necesitamos distinguir **para quién se emite un token** y **qué capacidades representa**.

## Audience

La `audience` identifica al destinatario lógico del token.

Ejemplo conceptual:

```text
aud = reservapp-api
```

ReservApp API debería rechazar un token válido que fue emitido para otra API.

## Scopes

Los scopes representan capacidades solicitadas y concedidas.

Ejemplo:

```text
reservations.read
reservations.write
```

Un scope debería expresar una capacidad estable del recurso, no copiar botones de una pantalla.

## Relación

```text
Access Token
├── aud   = reservapp-api
├── scope = reservations.read
└── exp   = ...
```

La API valida que:

1. el token proviene de un emisor confiable;
2. está destinado a ella;
3. sigue vigente;
4. contiene permisos suficientes.

## Lo que los scopes no resuelven solos

Un token con `reservations.write` puede permitir modificar reservas en general, pero la API todavía debe comprobar reglas del dominio.

Ejemplo:

```text
scope permite escribir
+
sub coincide con ownerId
=
operación autorizada
```

Por eso audience y scopes forman parte de la autorización técnica, mientras que la autorización de negocio sigue perteneciendo al backend.