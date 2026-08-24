# 1 · JWT y claims

## Qué es un JWT

JWT es un formato compacto para transportar claims. Visualmente suele tener tres segmentos separados por puntos:

```text
header.payload.signature
```

Header y payload pueden **decodificarse** fácilmente; no están cifrados por el hecho de ser JWT. La firma permite verificar integridad/autenticidad cuando se valida correctamente con la clave y reglas esperadas.

## Claims relevantes

Ejemplo conceptual de payload:

```json
{
  "iss": "https://identity.local/",
  "sub": "user-123",
  "aud": "reservapp-api",
  "exp": 1780000000,
  "scope": "reservations.read"
}
```

- `iss`: quién emitió el token.
- `sub`: sujeto identificado por el emisor.
- `aud`: recurso/audiencia para el que fue emitido.
- `exp`: expiración.
- `scope`: permisos delegados representados por el token, según el sistema.

## Decodificar ≠ verificar

Leer el payload responde «¿qué declara este texto?». Verificar responde «¿puedo confiar criptográfica y contextualmente en estas declaraciones?». Una API no debe autorizar solo porque pudo hacer Base64 decode.

## Access token vs ID token

El access token se presenta al recurso/API para autorizar acceso. El ID token comunica información de autenticación al cliente OIDC. Usar un ID token como si fuera access token confunde destinatario y propósito.

## Validación conceptual

Antes de aceptar un access token, el recurso debe considerar al menos firma/clave válida, issuer esperado, audience esperada, vigencia temporal y permisos requeridos. La política exacta depende de la arquitectura y proveedor.

## 401 y 403

Como regla de aprendizaje: **401** cuando no existe autenticación/token utilizable para acceder al recurso protegido; **403** cuando la identidad/token es reconocible pero carece del permiso exigido. Los detalles concretos dependen del framework y política, por lo que siempre se valida observando request, configuración y respuesta real.

## Ejercicio

Recibe tres payloads sintéticos. Para cada uno determina: ¿issuer correcto?, ¿audience correcta?, ¿vigente?, ¿contiene `reservations.read`? Luego decide qué condición impediría el acceso. No uses tokens reales.