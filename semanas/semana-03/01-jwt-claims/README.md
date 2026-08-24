# Profundización · JWT y claims

← [Volver al contenido base](../01-jwt-claims.md)

Esta carpeta **expande** el contenido oficial de `01-jwt-claims.md`. No lo reemplaza.

> Si estás estudiando la base de la asignatura, el archivo `01-jwt-claims.md` contiene lo necesario. Entra aquí cuando quieras entender con mayor precisión por qué un token puede ser legible y aun así no ser confiable.

## ¿Qué puedes profundizar aquí?

1. [Estructura, codificación y firma](./01-estructura-codificacion-y-firma.md)
2. [Validación contextual de claims](./02-validacion-contextual-de-claims.md)
3. [Claves públicas, `kid` y JWKS](./03-claves-kid-y-jwks.md)
4. [Errores frecuentes al trabajar con JWT](./04-errores-frecuentes.md)

## Idea que debe permanecer

```text
leer un JWT
≠
validar un JWT
≠
autorizar una operación
```

Son tres acciones distintas.

La primera permite observar datos. La segunda establece si el token puede considerarse confiable dentro de un contexto técnico. La tercera decide si esa identidad/capacidad puede ejecutar una operación concreta.

## Ruta sugerida

```text
01-jwt-claims.md
        ↓
contenido base suficiente
        │
        └── quiero comprender más
                ↓
01-jwt-claims/README.md
        ↓
estructura → claims → claves → errores
```

No necesitas memorizar algoritmos criptográficos ni implementar validación JWT desde cero. El objetivo es comprender qué valida una biblioteca/framework y por qué esas comprobaciones importan.