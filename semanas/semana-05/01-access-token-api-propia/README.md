# Contenido extendido · Access Token para API propia

Complementa [Access Token para la API propia](../01-access-token-api-propia.md).

## 1. Un token siempre tiene contexto

Un JWT válido no es una credencial universal. Para decidir si una API debe aceptarlo hay que revisar quién lo emitió, para qué recurso fue emitido, si sigue vigente y qué permisos representa.

## 2. Claims mínimos

- iss: emisor esperado.
- aud: recurso destinatario.
- exp: vigencia.
- scp o roles: permisos.

La firma demuestra integridad y procedencia criptográfica; los claims contextualizan el uso permitido.

## 3. Cliente y recurso

La SPA representa al cliente público. La API representa al recurso protegido. El cliente solicita scopes que el recurso expone.

Esto explica por qué una autenticación exitosa puede coexistir con una API que rechaza el token.

## 4. Token para Graph vs token para API propia

Dos tokens emitidos por el mismo proveedor pueden tener audiences diferentes.

Si uno está destinado a Microsoft Graph y otro a BookShelf API, solamente el segundo debería enviarse al backend propio.

## 5. Inspección

En laboratorio conviene revisar el payload decodificado para aprender, pero decodificar no equivale a verificar.

Nunca publicar tokens completos como evidencia.

## 6. Ejercicio

Compara dos tokens y responde:

1. ¿qué issuer tienen?;
2. ¿qué audience?;
3. ¿qué permisos?;
4. ¿cuál debería aceptar la API?;
5. ¿qué status esperarías con el otro?

## 7. Idea clave

La pregunta profesional es: “¿esta credencial es válida para este recurso y esta operación?”, no simplemente “¿este JWT parece válido?”.
