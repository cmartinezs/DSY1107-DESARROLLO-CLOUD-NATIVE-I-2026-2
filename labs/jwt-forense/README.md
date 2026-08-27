# Laboratorio canónico · JWT forense

**Semana:** 3  
**Dominio:** API ficticia `products-api`  
**Objetivo:** interpretar y defender decisiones de acceso antes de configurar un proveedor cloud real.

Este laboratorio pertenece a la vertical de contenido y es independiente de RegistrApp.

## Contexto

```text
cliente → identidad → access token
cliente → gateway → products-api
```

Scopes:

```text
products.read
products.write
```

## Parte 1 · Anatomía

Usa JWT locales/sintéticos. Identifica `header`, `payload` y `signature`; decodifica header/payload y registra los claims encontrados.

## Parte 2 · Tokens sospechosos

Analiza:

1. audience incorrecta;
2. token expirado;
3. token válido sin `products.write`.

Para cada caso explica qué condición falla, si corresponde a autenticación o autorización, qué componente podría detectarlo y qué evidencia falta antes de confiar.

## Parte 3 · Matriz HTTP

Ejecuta cuando exista starter mínimo:

| Caso | Resultado conceptual |
|---|---|
| sin `Authorization` | 401 |
| bearer mal formado | 401 |
| firma/token inválido | 401 |
| token válido + `products.read` en lectura | 2xx |
| token válido sin `products.write` en escritura | 403 |
| token válido + scope correcto | continúa a dominio |

Registrar request mínimo, status, body, componente que produjo la respuesta y explicación.

## Parte 4 · Decodificar no es confiar

Modifica manualmente el payload de un token local sin regenerar correctamente la firma. Demuestra que el contenido sigue siendo legible pero deja de ser confiable.

## Parte 5 · Arquitectura mínima

Dibuja usuario, cliente, identidad, gateway y `products-api`, agregando:

- `iss`;
- `aud`;
- scopes;
- validación técnica;
- autorización;
- una regla de negocio que permanezca en backend.

## Evidencia

- tabla de casos;
- respuestas HTTP reproducibles cuando corresponda;
- diagrama;
- explicación decode vs verify;
- defensa 401 vs 403;
- DevLog técnico.

## Después del laboratorio

Solo después de comprender y defender el laboratorio puede transferirse la competencia al proyecto transversal:

→ [RegistrApp · Checkpoint Semana 3](../../proyecto-formativo/semana-03/README.md)

Nunca versionar secretos ni tokens reales reutilizables.
