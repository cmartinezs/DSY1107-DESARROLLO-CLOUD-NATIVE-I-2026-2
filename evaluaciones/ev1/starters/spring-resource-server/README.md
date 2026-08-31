# Spring Boot · Resource Server Starter

Este starter entrega una base mínima para convertir una API Spring Boot en un **OAuth 2.0 Resource Server** capaz de recibir y validar Access Tokens JWT emitidos por un Identity Provider.

> La API de negocio **no autentica usuario/contraseña ni emite Access Tokens**. Su responsabilidad es validar el Bearer Token recibido y autorizar o rechazar la operación.

## Qué resuelve este starter

- Spring Security como Resource Server;
- validación criptográfica del JWT usando las claves públicas publicadas por el Identity Provider;
- validación de `iss` (issuer);
- validación de `aud` (audience);
- validación temporal (`exp` y, cuando corresponda, `nbf`);
- conversión de `scope`/`scp` a authorities `SCOPE_*`;
- conversión de `roles` a authorities `ROLE_*`;
- API stateless;
- CORS explícito;
- respuesta JSON para `401 Unauthorized`;
- respuesta JSON para `403 Forbidden`;
- endpoints mínimos para probar acceso público, autenticado, por scope y por rol.

## Archivos

```text
spring-resource-server/
├── README.md
├── pom-dependencies.xml
├── application.example.yml
└── src/main/java/cl/duoc/dsy1107/security/
    ├── SecurityConfig.java
    ├── AudienceValidator.java
    ├── AuthoritiesConverter.java
    ├── RestAuthenticationEntryPoint.java
    ├── RestAccessDeniedHandler.java
    └── SecurityExampleController.java
```

Los paquetes son de ejemplo. Al incorporar estos archivos a tu proyecto puedes adaptar el package a la estructura de tu aplicación.

## 1. Dependencias

Agrega a tu `pom.xml` las dependencias indicadas en [`pom-dependencies.xml`](./pom-dependencies.xml).

Las dos piezas principales son:

- `spring-boot-starter-security`;
- `spring-boot-starter-oauth2-resource-server`.

No agregues una librería para generar Access Tokens: en la arquitectura EV1 esos tokens son emitidos por el IDaaS.

## 2. Configuración

Toma [`application.example.yml`](./application.example.yml) como referencia.

Debes configurar al menos:

```text
JWT_ISSUER
JWT_AUDIENCE
ALLOWED_ORIGINS
```

### `JWT_ISSUER`

Identifica al emisor que Spring debe aceptar. Debe coincidir exactamente con el claim `iss` del Access Token.

### `JWT_AUDIENCE`

Identifica la API para la cual fue emitido el Access Token. Debe aparecer dentro del claim `aud`.

### `ALLOWED_ORIGINS`

Lista separada por comas con los orígenes frontend autorizados para invocar la API desde un navegador.

Ejemplo local:

```text
http://localhost:5173,http://localhost:4200
```

No uses `*` como solución por defecto para evitar comprender CORS.

## 3. Copiar las clases

Copia las clases de `src/main/java/.../security/` a tu proyecto y adapta el package.

La relación es:

```text
SecurityConfig
├── configura rutas y Resource Server
├── crea JwtDecoder
├── aplica issuer + audience
├── registra AuthoritiesConverter
├── configura CORS
└── registra respuestas 401/403
```

## 4. Política incluida

El ejemplo aplica deliberadamente cuatro niveles distintos:

```text
/public/**
→ permitAll

/api/**
→ authenticated

/api/write/**
→ SCOPE_recurso.write

/api/admin/**
→ ROLE_ADMIN
```

El nombre `recurso.write` es demostrativo. Debes sustituirlo por un scope coherente con tu dominio y con la configuración real del Identity Provider.

## 5. Scopes y roles

Spring Security convierte scopes en authorities con prefijo `SCOPE_`.

Por ejemplo:

```text
scp = "recurso.read recurso.write"
```

produce conceptualmente:

```text
SCOPE_recurso.read
SCOPE_recurso.write
```

El `AuthoritiesConverter` incluido conserva ese comportamiento y además transforma:

```json
"roles": ["ADMIN"]
```

en:

```text
ROLE_ADMIN
```

Esto permite practicar ambas estrategias sin confundirlas:

- scopes: permisos delegados sobre la API;
- roles: roles de aplicación cuando el proveedor los emite.

## 6. Qué valida realmente el backend

Un JWT no se acepta porque pueda decodificarse.

El backend debe validar:

```text
firma válida
AND issuer esperado
AND audience esperada
AND token vigente
```

Después de autenticar el token, Spring aplica las reglas de autorización de cada endpoint.

## 7. Resultados esperados

| Caso | Resultado |
|---|---:|
| `/public/health` sin token | `200` |
| `/api/profile` sin token | `401` |
| `/api/profile` con JWT inválido | `401` |
| `/api/profile` con JWT válido | `200` |
| `/api/write/example` sin scope requerido | `403` |
| `/api/write/example` con scope requerido | `200` |
| `/api/admin/example` sin rol ADMIN | `403` |
| `/api/admin/example` con rol ADMIN | `200` |

## 8. Diferencia entre 401 y 403

### 401 Unauthorized

La API **no pudo autenticar** la solicitud.

Ejemplos:

- falta Bearer Token en una ruta protegida;
- token mal formado;
- firma inválida;
- issuer incorrecto;
- audience incorrecta;
- token expirado.

### 403 Forbidden

La solicitud ya fue autenticada, pero el principal **no posee el permiso requerido**.

Ejemplos:

- token válido sin `recurso.write`;
- token válido sin rol `ADMIN`.

## 9. Qué debes adaptar

Antes de usar este starter en tu proyecto debes revisar al menos:

- package Java;
- nombres de rutas;
- scope(s) requeridos;
- roles, si los utilizas;
- issuer;
- audience;
- orígenes CORS.

No copies estos valores sin comprenderlos.

## 10. Lo que este starter NO hace

No incluye:

- formulario de login;
- almacenamiento de passwords;
- endpoint `/login` propio;
- `generateToken()`;
- firma propia de Access Tokens;
- endpoint `/oauth2/token`;
- client secret en frontend o backend de negocio.

Esas responsabilidades no corresponden al Resource Server de la aplicación EV1.

## 11. Checkpoint

Antes de continuar al frontend con MSAL debes poder explicar:

1. ¿Quién emite el Access Token?
2. ¿Por qué Spring necesita validar `iss`?
3. ¿Por qué Spring necesita validar `aud`?
4. ¿Qué diferencia existe entre decodificar y validar el JWT?
5. ¿Por qué un token válido puede producir `403`?
6. ¿Qué diferencia hay entre `SCOPE_recurso.write` y `ROLE_ADMIN`?
7. ¿Por qué la API debe ser stateless?

Si puedes responderlas y los endpoints muestran los estados esperados, el backend está preparado para integrarse con el frontend autenticado.
