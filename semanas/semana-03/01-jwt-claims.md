# 1 · JWT y claims

## Objetivo

Comprender qué contiene un JWT, qué significan sus claims principales y por qué **decodificar un token no equivale a verificarlo**.

Este contenido se trabaja con ejemplos sintéticos e independientes de RegistrApp.

## Qué es un JWT

JWT es un formato compacto para transportar claims. Visualmente suele tener tres segmentos separados por puntos:

```text
header.payload.signature
```

Header y payload pueden **decodificarse** fácilmente; no están cifrados por el hecho de ser JWT. La firma permite verificar integridad/autenticidad cuando se valida correctamente con la clave y reglas esperadas.

## Claims relevantes

Ejemplo conceptual de payload para una API ficticia de productos:

```json
{
  "iss": "https://identity.example/",
  "sub": "user-123",
  "aud": "products-api",
  "exp": 1780000000,
  "scope": "products.read"
}
```

- `iss`: quién emitió el token.
- `sub`: sujeto identificado por el emisor.
- `aud`: recurso/audiencia para el que fue emitido.
- `exp`: expiración.
- `scope`: permisos delegados representados por el token, según el sistema.

## Decodificar ≠ verificar

Leer el payload responde:

> ¿Qué declara este texto?

Verificar responde:

> ¿Puedo confiar criptográfica y contextualmente en estas declaraciones?

Una API no debe autorizar solo porque pudo hacer Base64 decode.

## Access token vs ID token

El **access token** se presenta al recurso/API para autorizar acceso.

El **ID token** comunica información de autenticación al cliente OIDC.

Usar un ID token como si fuera access token confunde destinatario y propósito.

## Validación conceptual

Antes de aceptar un access token, el recurso debe considerar al menos:

1. firma/clave válida;
2. issuer esperado;
3. audience esperada;
4. vigencia temporal;
5. permisos requeridos.

La política exacta depende de la arquitectura y proveedor.

## 401 y 403

Como regla de aprendizaje:

- **401**: no existe autenticación/token utilizable para acceder al recurso protegido;
- **403**: la identidad/token es reconocible, pero carece del permiso exigido.

Los detalles concretos dependen del framework y política, por lo que siempre se valida observando request, configuración y respuesta real.

## Mini ejercicio independiente

Recibe tres payloads sintéticos para `products-api`.

Para cada uno determina:

1. ¿issuer correcto?;
2. ¿audience correcta?;
3. ¿vigente?;
4. ¿contiene `products.read`?;
5. ¿qué condición impediría el acceso?

No uses tokens reales.

## Cierre

Antes de pasar al desafío transversal, debes poder explicar con tus propias palabras por qué un JWT que “se ve bien” al decodificarlo todavía puede ser inválido o no estar destinado a esa API.

> **Si quieres profundizar:** revisa [Profundización · JWT y claims](./01-jwt-claims/README.md) para estudiar estructura y firma, validación contextual, `kid`/JWKS y errores frecuentes.