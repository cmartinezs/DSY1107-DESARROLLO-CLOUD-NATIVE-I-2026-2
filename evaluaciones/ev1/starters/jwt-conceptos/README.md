# JWT · Toolkit conceptual

Este material permite comprender de forma aislada cómo se estructura, firma, decodifica y valida un JSON Web Token (JWT).

> **Importante:** estos ejemplos son pedagógicos. En la solución EV1 con IDaaS, los Access Tokens son emitidos y firmados por el proveedor de identidad. El backend Spring Boot de la aplicación no debe implementar un emisor propio de Access Tokens.

## Objetivos de aprendizaje

Al finalizar este toolkit debes poder explicar:

- qué es un JWT y cuáles son sus tres partes;
- qué información puede contener el payload mediante claims;
- qué significa firmar un token;
- por qué decodificar no equivale a validar;
- qué ocurre si se modifica el payload después de firmarlo;
- cómo se valida expiración e integridad;
- qué claims serán especialmente relevantes al trabajar posteriormente con IDaaS: `iss`, `aud`, `sub`, `exp`, `scp` y/o `roles`.

## Estructura de un JWT

```text
header.payload.signature
```

- `header`: describe, entre otros datos, el algoritmo utilizado.
- `payload`: contiene los claims.
- `signature`: permite detectar alteraciones y verificar que el token fue firmado con la clave esperada.

El header y el payload están codificados en Base64URL. **No están cifrados.** Cualquier persona que posea el token puede decodificarlos.

## Archivos

1. `JwtCreateAndSignExample.java` — crea claims y firma un JWT.
2. `JwtDecodeExample.java` — lee header y payload sin validar la firma.
3. `JwtVerifyExample.java` — verifica firma, expiración, issuer y audience.
4. `JwtTamperExample.java` — altera el payload y demuestra que la firma deja de ser válida.
5. `pom.xml` — proyecto Maven mínimo para ejecutar los ejemplos.

## Requisitos

- JDK 17 o superior.
- Maven disponible en terminal.

Los ejemplos utilizan **JJWT 0.12.6**. La API utilizada corresponde a la línea 0.12.x (`Jwts.builder()`, `Jwts.parser()`, `verifyWith`, `requireIssuer` y `requireAudience`).

## Ejecución guiada

Ubícate en este directorio:

```bash
cd evaluaciones/ev1/starters/jwt-conceptos
```

### 1. Compilar

```bash
mvn clean compile
```

### 2. Crear y firmar un token

```bash
mvn exec:java \
  -Dexec.mainClass="cl.duoc.dsy1107.ev1.jwt.JwtCreateAndSignExample"
```

Copia solamente el JWT generado para utilizarlo en los pasos siguientes.

### 3. Decodificar sin validar

```bash
mvn exec:java \
  -Dexec.mainClass="cl.duoc.dsy1107.ev1.jwt.JwtDecodeExample" \
  -Dexec.args="<JWT>"
```

Observa que puedes leer header y payload sin disponer de una clave de firma.

### 4. Validar

```bash
mvn exec:java \
  -Dexec.mainClass="cl.duoc.dsy1107.ev1.jwt.JwtVerifyExample" \
  -Dexec.args="<JWT>"
```

El programa valida la firma y los claims esperados. JJWT también rechaza automáticamente un token expirado durante el parsing.

### 5. Alterar el payload

```bash
mvn exec:java \
  -Dexec.mainClass="cl.duoc.dsy1107.ev1.jwt.JwtTamperExample" \
  -Dexec.args="<JWT>"
```

El ejemplo cambia el scope del payload sin recalcular correctamente la firma. El token debe ser rechazado.

## Dependencia usada

El `pom.xml` incluye:

- `jjwt-api`;
- `jjwt-impl`;
- `jjwt-jackson`.

Se usa una biblioteca para no distraer el ejercicio implementando manualmente el formato JWS. El objetivo es comprender claims, firma y validación.

## Clave de demostración

Para mantener el ejercicio autocontenido, los ejemplos utilizan una **clave didáctica fija y explícitamente no productiva** compartida solo entre los ejemplos de este directorio.

Esta decisión permite crear un token en un programa y validarlo en otro sin agregar almacenamiento de claves al ejercicio.

> Nunca reutilices esta clave ni este patrón en una aplicación real. Las claves reales no deben quedar versionadas en el repositorio.

## Qué debes observar

### Al crear y firmar

El token contiene claims como:

```text
iss
sub
aud
iat
exp
scope
```

### Al decodificar

Puedes leer el payload aunque no poseas la clave. Esto demuestra que **decodificar no es validar** y que JWT no implica confidencialidad.

### Al validar

El ejemplo comprueba:

- integridad de la firma;
- expiración;
- issuer esperado;
- audience esperada.

Posteriormente Spring Security realizará este tipo de validaciones sobre los Access Tokens emitidos por el IDaaS.

### Al alterar el token

Modificar el payload cambia el contenido firmado. Mantener la firma antigua provoca que la verificación falle.

```text
token original
      ↓
modificar payload
      ↓
conservar firma original
      ↓
verificación criptográfica falla
```

## Preguntas de comprobación

Antes de continuar debes poder responder:

1. ¿Por qué es posible leer un JWT sin conocer la clave de firma?
2. ¿Qué diferencia existe entre decodificar y validar?
3. ¿Qué protege la firma?
4. ¿Qué ocurre cuando `exp` ya quedó en el pasado?
5. ¿Para qué sirven `iss` y `aud`?
6. ¿Por qué modificar manualmente un claim invalida el token?
7. ¿Quién emitirá y firmará el Access Token real de EV1?

## Relación con EV1

Este toolkit termina en la **comprensión del token**.

La arquitectura real continúa así:

```text
IDaaS
  │
  │ emite y firma Access Token
  ▼
SPA
  │
  │ Authorization: Bearer <token>
  ▼
Spring Boot Resource Server
  │
  └── valida token y aplica autorización
```

La siguiente etapa construye precisamente ese Resource Server con Spring Security.
