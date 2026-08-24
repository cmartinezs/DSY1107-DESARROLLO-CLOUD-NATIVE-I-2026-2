# Profundización · Seguridad de API y gateway

← [Volver al contenido base](../02-seguridad-api.md)

Esta carpeta **expande** el contenido oficial de `02-seguridad-api.md`. No lo reemplaza.

> El archivo base contiene lo exigido para la asignatura. Usa esta profundización para entender con más detalle cómo se distribuyen las decisiones de seguridad y por qué no todo debe resolverse en el mismo componente.

## ¿Qué puedes profundizar aquí?

1. [Gateway vs backend: frontera de responsabilidad](./01-gateway-vs-backend.md)
2. [Pipeline de acceso y 401 vs 403](./02-pipeline-401-403.md)
3. [Scopes, roles y claims en autorización](./03-scopes-roles-y-claims.md)
4. [Usuarios externos, CIAM y API protegida](./04-ciam-y-api-protegida.md)

## Idea que debe permanecer

```text
IdP / Authorization Server
→ identidad y emisión de tokens

Gateway
→ políticas técnicas transversales

Backend
→ reglas del dominio y autorización contextual
```

No existe una única distribución universal, pero sí una regla sana: **cada decisión debe vivir donde exista la información y responsabilidad adecuadas**.

## Ruta sugerida

```text
02-seguridad-api.md
        ↓
contenido base suficiente
        │
        └── quiero comprender más
                ↓
02-seguridad-api/README.md
        ↓
frontera → pipeline → permisos → CIAM
```

El objetivo no es memorizar un producto concreto de API Management, sino poder reconocer estas responsabilidades cuando cambie la tecnología.