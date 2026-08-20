# API vs API Gateway vs API Management

El archivo base presenta la diferencia esencial. Aquí la ampliamos desde una perspectiva arquitectónica.

## API

Una API es un **contrato de interacción**. Puede existir sin gateway. Por ejemplo, un servicio Spring Boot puede exponer directamente `/reservas`.

## API Gateway

El gateway es un **componente de ejecución en el camino de las peticiones**. Puede enrutar, aplicar políticas, validar credenciales, limitar tráfico o transformar mensajes.

```text
cliente → gateway → backend
```

El gateway no define por sí solo toda la estrategia de gestión de APIs.

## API Management

API Management abarca el ciclo de vida completo:

```text
diseñar → publicar → documentar → proteger → observar → versionar → deprecar → retirar
```

Puede incluir gateway, portal de desarrolladores, analítica, catálogo, políticas, suscripciones y gobierno.

## Una distinción útil

```text
API            → qué contrato ofrezco
API Gateway    → cómo proceso/exponemos tráfico en runtime
API Management → cómo gobierno el ciclo de vida completo
```

## Pregunta de arquitectura

Si mañana reemplazamos Spring Cloud Gateway por Amazon API Gateway, ¿cambia necesariamente el contrato de ReservApp? No. El componente de gateway puede cambiar mientras el contrato de la API permanezca estable.