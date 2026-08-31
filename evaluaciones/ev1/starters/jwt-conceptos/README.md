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

## Orden recomendado

1. `JwtCreateAndSignExample.java` — crear claims y firmar un JWT.
2. `JwtDecodeExample.java` — leer header y payload sin validar la firma.
3. `JwtVerifyExample.java` — verificar firma y expiración.
4. `JwtTamperExample.java` — modificar el payload y comprobar que la validación falla.

## Dependencia usada

Los ejemplos utilizan JJWT para mantener el código breve y concentrarnos en los conceptos:

```xml
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-api</artifactId>
  <version>0.12.6</version>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-impl</artifactId>
  <version>0.12.6</version>
  <scope>runtime</scope>
</dependency>
<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-jackson</artifactId>
  <version>0.12.6</version>
  <scope>runtime</scope>
</dependency>
```

La versión podrá actualizarse si el proyecto base utiliza una versión posterior compatible.

## Clave de demostración

Para los ejemplos locales se usa una clave generada en memoria. No se versionan secretos reales ni claves productivas.

## Qué debes observar

### Al crear y firmar

El token debe contener claims como:

```text
iss
sub
aud
iat
exp
scope
```

### Al decodificar

Podrás leer el payload aunque no tengas la clave de firma. Esto demuestra que JWT no implica confidencialidad.

### Al validar

La verificación correcta debe comprobar al menos:

- integridad de la firma;
- expiración;
- emisor esperado cuando corresponda;
- audiencia esperada cuando corresponda.

### Al alterar el token

Si modificas el payload sin volver a firmar correctamente el token, la firma deja de ser válida.

## Relación con EV1

Más adelante Spring Security realizará estas validaciones sobre Access Tokens reales emitidos por el IDaaS. Este toolkit existe para que puedas comprender qué está ocurriendo cuando Spring acepta o rechaza un Bearer Token.
