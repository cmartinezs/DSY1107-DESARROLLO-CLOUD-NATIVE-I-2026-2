# 1.2.2 · Identity as a Service (IDaaS) y CIAM

## Objetivo

Comprender qué problema resuelve un proveedor de identidad y cómo se integra, conceptualmente, con una aplicación moderna y una API sin depender todavía de Azure ni de otro proveedor concreto.

## 1. Antes de IDaaS: ¿qué problema tenemos?

ReservApp necesita saber:

- quién intenta usar la aplicación;
- si esa persona ya inició sesión;
- qué información de identidad puede confiar;
- qué permisos puede solicitar la aplicación;
- cómo evitar implementar contraseñas, recuperación de cuenta, MFA y gestión de sesiones desde cero.

Una alternativa sería que ReservApp implementara todo su sistema de identidad. Eso aumenta responsabilidad, superficie de ataque y trabajo operativo.

Un **Identity Provider (IdP)** centraliza la autenticación. Cuando esa capacidad se consume como servicio hablamos habitualmente de **Identity as a Service (IDaaS)**.

## 2. Separación de responsabilidades

```text
Usuario
  ↓
ReservApp Web
  ↓ redirección
Proveedor de identidad
  ↓ autenticación
ReservApp Web recibe identidad/tokens
  ↓ access token
API Gateway
  ↓
ReservApp API
```

La aplicación no necesita recibir la contraseña del usuario para validar identidad.

## 3. Conceptos esenciales

### Identity Provider / Authorization Server

Sistema responsable de autenticar al usuario y emitir los artefactos necesarios para los protocolos de identidad/autorización.

### Usuario

Identidad que puede autenticarse. No es necesariamente equivalente a una fila de la base de datos de negocio de ReservApp.

### Tenant / Realm / Organization

Espacio lógico donde se administran usuarios, aplicaciones, políticas y configuración de identidad. El nombre cambia entre productos; el concepto permanece.

### Aplicación o Client

Representa a ReservApp ante el sistema de identidad. Su configuración puede incluir:

- Client ID;
- redirect URI;
- scopes;
- tipo de cliente;
- URLs permitidas.

### Issuer

Identifica a la autoridad que emitió un token.

### Scope

Permiso solicitado/delegado. Para ReservApp podemos razonar con:

```text
reservations.read
reservations.write
```

### Claim

Dato declarado dentro de un token, por ejemplo:

```text
sub
iss
aud
exp
scope
role
```

## 4. IAM vs CIAM

**IAM** es un término amplio para gestión de identidad y acceso.

**CIAM (Customer Identity and Access Management)** está orientado principalmente a clientes o usuarios externos de una organización.

ReservApp es un buen ejemplo de CIAM si la aplicación será utilizada por personas externas que crean su propia cuenta y administran sus reservas.

## 5. IDaaS no decide todo

Supongamos que un access token contiene:

```text
scope = reservations.write
sub = user-123
```

Eso puede demostrar que el cliente recibió permiso para escribir reservas. Sin embargo, no implica automáticamente que `user-123` pueda cancelar **cualquier** reserva.

La regla:

> Un cliente solo puede cancelar sus propias reservas.

es una regla de negocio que probablemente debe validar el backend usando la identidad del sujeto y la propiedad de la reserva.

## 6. Actividad guiada · ReservApp

Clasifiquen cada responsabilidad indicando si pertenece principalmente a:

- proveedor de identidad;
- cliente web;
- API Gateway;
- backend ReservApp.

Casos:

1. verificar contraseña;
2. emitir un access token;
3. enviar el token en una petición HTTP;
4. comprobar firma, issuer y expiración;
5. validar que el usuario sea dueño de la reserva;
6. mostrar el nombre del usuario autenticado;
7. impedir una operación si falta `reservations.write`.

La discusión importa más que memorizar una tabla fija: algunas responsabilidades pueden distribuirse según la arquitectura.

## 7. Checkpoint

Al terminar este tema debes poder explicar sin mencionar Azure:

- qué es un IdP;
- qué aporta IDaaS;
- qué significa tenant;
- qué representa una aplicación/client;
- qué diferencia hay entre scope y claim;
- qué distingue IAM de CIAM;
- qué decisiones siguen perteneciendo a ReservApp.

## Continuidad

El siguiente tema toma estos conceptos y diseña el **tenant de ReservApp** antes de configurarlo en un proveedor real.
