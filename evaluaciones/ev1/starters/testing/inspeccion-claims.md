# Inspección segura de claims

El objetivo de inspeccionar un JWT es comprender por qué la API acepta o rechaza una solicitud, sin convertir el token en evidencia pública.

## Claims relevantes

Según la configuración del proveedor de identidad, observa principalmente:

| Claim | Pregunta que responde |
|---|---|
| `iss` | ¿Quién emitió el token? |
| `aud` | ¿Para qué API o recurso fue emitido? |
| `sub` | ¿Qué sujeto representa? |
| `exp` | ¿Hasta cuándo es válido? |
| `iat` | ¿Cuándo fue emitido? |
| `scp` / `scope` | ¿Qué scopes delegados posee? |
| `roles` | ¿Qué roles de aplicación posee? |
| `oid` u otro identificador | ¿Qué identidad concreta representa en el proveedor? |

## Decodificar no es validar

Puedes decodificar `header` y `payload` para observar claims. Eso no prueba que el token sea auténtico.

La validación real corresponde al Resource Server y debe incluir, según la configuración:

```text
firma
+
issuer
+
audience
+
expiración
+
reglas de autorización
```

## Evidencia segura

Para una demostración o entrega se puede registrar una tabla como:

| Dato | Valor observado |
|---|---|
| `iss` | dominio/issuer configurado |
| `aud` | identificador o URI de la API |
| `exp` | fecha/hora de expiración |
| `scp` | `recurso.read` |
| `roles` | `ADMIN` o sin rol |
| resultado API | `403` |

No es necesario mostrar:

```text
header.payload.signature completo
```

## Sanitización

Si una captura contiene un token, oculta completamente el valor después de `Bearer`.

Ejemplo correcto:

```text
Authorization: Bearer [REDACTED]
```

No basta ocultar solo algunos caracteres del centro del token.

## Relación con diagnóstico

### `iss` incorrecto

El token puede ser perfectamente válido para otro issuer, pero no para esta API.

Resultado esperado:

```text
401
```

### `aud` incorrecto

El token fue emitido para otro recurso/API.

Resultado esperado:

```text
401
```

### scope o rol ausente

El token puede ser válido para esta API, pero no autorizar la operación solicitada.

Resultado esperado:

```text
403
```

### `exp` vencido

El token ya no debe considerarse válido.

Resultado esperado:

```text
401
```

## Preguntas de comprobación

Antes de cerrar la evidencia debes poder responder:

1. ¿Cuál es el issuer esperado por tu backend?
2. ¿Cuál es la audience esperada?
3. ¿Qué claim contiene los permisos que estás usando?
4. ¿Qué authority de Spring se genera desde ese claim?
5. ¿Por qué el mismo usuario puede obtener 200 en una operación y 403 en otra?
