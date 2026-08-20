# Rutas, integraciones y stages

Estos conceptos suelen aparecer juntos en la consola, pero representan decisiones distintas.

## Ruta

Determina **qué solicitud reconoce el gateway**.

```text
GET /productos
POST /reservas
```

## Integración

Determina **quién procesa la solicitud**.

```text
GET /productos → Products Service
POST /reservas → Reservations Service
```

## Stage

Representa un contexto de publicación como `dev`, `qa` o `prod`. No equivale automáticamente a una versión funcional de la API.

## Error frecuente

Confundir:

```text
/v1 → versión del contrato
prod → contexto de despliegue
```

Son dimensiones distintas. Podemos tener `/v1` y `/v2` expuestas en un mismo stage `prod`, o la misma `/v1` en `dev` y `prod`.

## Modelo mental

```text
request
  ↓
ruta
  ↓
integración
  ↓
backend

stage = contexto donde esta configuración está publicada
```