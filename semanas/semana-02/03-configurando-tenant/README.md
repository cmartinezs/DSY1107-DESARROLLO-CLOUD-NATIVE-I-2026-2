# Profundización · Configurando un Tenant

← [Volver al contenido base](../03-configurando-tenant.md)

Esta carpeta **expande** el contenido oficial de `03-configurando-tenant.md`. El archivo base sigue siendo suficiente para los aprendizajes esperados de la asignatura.

## Ruta sugerida

1. [Tenant como frontera de confianza](./01-frontera-de-confianza.md)
2. [Poblaciones, aplicaciones y recursos](./02-poblaciones-aplicaciones-recursos.md)
3. [Diseño conceptual del tenant de ReservApp](./03-diseno-reservapp.md)

## Idea que debe permanecer

Un tenant no es simplemente una cuenta ni una aplicación. Es un **espacio lógico de administración y confianza** donde se relacionan identidades, clientes, APIs, políticas y configuración de seguridad.

```text
Tenant
├── identidades
├── clientes / aplicaciones
├── APIs / recursos
├── scopes / roles
├── políticas
└── configuración de emisión de tokens
```

El objetivo de esta profundización es comprender qué decisiones hay detrás de esa estructura antes de reproducirlas en una consola cloud.