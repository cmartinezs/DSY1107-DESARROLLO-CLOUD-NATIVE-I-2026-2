# Etapa 1 · Anatomía de un JWT

## Trabajo

Identifica:

```text
header.payload.signature
```

En el payload ubica al menos:

- `iss`;
- `aud`;
- `sub`;
- `exp`;
- `scp` o `scope`.

## Regla

Decodificar Base64URL permite leer. No demuestra que el token sea confiable.

## Checkpoint

Explica con tus palabras qué dato responde a:

- quién emitió;
- para qué recurso;
- quién es el sujeto;
- hasta cuándo es válido;
- qué permisos delegados contiene.
