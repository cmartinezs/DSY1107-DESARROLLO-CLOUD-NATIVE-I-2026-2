# Contenido extendido · Spring Security como Resource Server

Complementa [Spring Security como Resource Server](../02-spring-security-backend.md).

## 1. Qué significa Resource Server

Una API protegida no autentica al usuario con formulario propio. Recibe un Bearer token y decide si esa credencial es aceptable para el recurso.

```text
Request
→ Bearer token
→ validación criptográfica
→ validación contextual
→ autorización
→ recurso
```

## 2. Validación criptográfica

El backend debe comprobar que el token fue firmado por una autoridad confiable.

Con OpenID Connect normalmente obtiene metadata y claves públicas mediante el issuer.

## 3. Validación contextual

Firma válida no es suficiente.

También importan:

- `iss`: issuer esperado;
- `aud`: recurso correcto;
- `exp`: vigencia;
- `nbf` cuando aplica;
- scopes/roles requeridos.

Un JWT puede ser auténtico y aun así no estar destinado a nuestra API.

## 4. 401 y 403

### 401 Unauthorized

La request no tiene una autenticación aceptable.

Ejemplos:

- sin token;
- token expirado;
- firma inválida;
- issuer incorrecto;
- audience incorrecta.

### 403 Forbidden

La autenticación fue aceptada, pero no tiene autoridad suficiente.

Ejemplo:

```text
token válido
+ audience correcta
- scope books.read
= 403
```

## 5. Scopes como authorities

Spring suele mapear:

```text
scp = books.read books.write
```

a:

```text
SCOPE_books.read
SCOPE_books.write
```

Esto permite reglas declarativas en `SecurityFilterChain`.

## 6. Seguridad técnica vs negocio

Spring Security puede proteger endpoints, pero una regla de negocio puede necesitar contexto adicional.

Ejemplo:

> Un usuario autenticado puede leer reservas, pero solo modificar las propias.

Eso no debería delegarse completamente al API Gateway.

## 7. Defensa en profundidad

```text
Gateway
→ filtra entrada y políticas comunes

Backend
→ valida recurso y autorización de aplicación
```

Duplicar algunas validaciones críticas no es necesariamente redundancia inútil; puede ser una frontera deliberada.

## 8. Errores frecuentes

- aceptar cualquier JWT firmado por el proveedor;
- omitir audience;
- mapear mal scopes;
- convertir 401 en 403 por configuración incorrecta;
- desactivar CSRF/CORS/seguridad sin comprender impacto;
- confiar en headers de identidad enviados por el cliente.

## 9. Prueba mínima

Construye una matriz:

| Token | Audience | Scope | Resultado |
|---|---|---|---|
| ausente | — | — | 401 |
| válido | incorrecta | sí | 401 |
| válido | correcta | no | 403 |
| válido | correcta | sí | 2xx |

## 10. Idea clave

El backend protege el recurso según el contexto del token y las reglas de la aplicación; no “confía” en que otro componente ya hizo todo.
