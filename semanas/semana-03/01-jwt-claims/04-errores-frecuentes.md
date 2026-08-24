# Errores frecuentes al trabajar con JWT

← [Volver a la profundización](./README.md)

JWT suele parecer sencillo porque puede decodificarse con facilidad. Precisamente por eso aparecen varios errores conceptuales.

## 1. “Si puedo leerlo, es válido”

Falso.

Decodificar solo revela lo que el token **declara**.

Todavía falta verificar firma, issuer, audience, vigencia y permisos.

## 2. “JWT significa cifrado”

Falso.

El payload normalmente puede inspeccionarse. No coloques secretos solo porque el token tenga apariencia ilegible.

## 3. “Cualquier JWT sirve como access token”

Falso.

JWT describe un formato. El propósito depende del tipo de token, issuer, audience y protocolo.

Un ID token no debe utilizarse automáticamente para invocar una API.

## 4. “Firma válida significa acceso permitido”

Falso.

La firma es solo una condición.

También deben verificarse contexto y autorización.

```text
firma válida
+ issuer correcto
+ audience correcta
+ vigente
+ permiso requerido
≠
regla de negocio automáticamente satisfecha
```

## 5. “El email es el identificador técnico perfecto”

No necesariamente.

El email puede cambiar y su semántica depende del proveedor.

Para identidad suele ser más robusto pensar primero en un identificador estable como `sub`, dentro del contexto del issuer.

## 6. “El frontend debe validar el token y con eso basta”

Incorrecto.

El frontend puede usar información para experiencia de usuario, pero la decisión de proteger una API debe existir en componentes del lado servidor/gateway que no dependan de la buena voluntad del cliente.

## 7. “Un token expirado sigue sirviendo si conozco al usuario”

No.

La expiración existe precisamente para limitar temporalmente la validez del artefacto.

## 8. “Scopes y roles son exactamente lo mismo”

No.

Pueden participar juntos en autorización, pero modelan ideas distintas según la arquitectura.

- scope: capacidad concedida sobre un recurso;
- role: función o pertenencia dentro de un contexto;
- claim: afirmación/dato transportado por el token.

## Autoevaluación

Explica qué está mal en estas frases:

1. “Lo pegué en un decoder y se ve bien, así que es válido.”
2. “Tiene firma correcta; entonces puede llamar cualquier endpoint.”
3. “Es JWT, por eso el contenido está cifrado.”
4. “Si el frontend oculta el botón, la operación ya está protegida.”
5. “ID token y access token son intercambiables porque ambos pueden ser JWT.”