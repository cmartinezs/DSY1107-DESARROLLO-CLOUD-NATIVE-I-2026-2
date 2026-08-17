# Dominio formativo transversal · ReservApp

## Propósito

**ReservApp** es el dominio formativo longitudinal de DSY1107 durante el semestre 2026-2.

Siempre que el contenido lo permita, los ejemplos, ejercicios, demostraciones y laboratorios realizados en clases deben reutilizar y evolucionar ReservApp en lugar de comenzar un caso nuevo desde cero.

El objetivo es que cada experiencia de aprendizaje agregue una capacidad real sobre lo construido anteriormente y que el estudiante pueda observar cómo una arquitectura evoluciona durante el semestre.

## Regla de separación con evaluaciones

ReservApp es exclusivamente **formativo**.

- No debe reutilizar nombres, entidades ni reglas de negocio propias de una evaluación sumativa.
- No debe convertirse en una plantilla que resuelva indirectamente el caso evaluado.
- En semanas de evaluación el proyecto puede pausarse.
- Las competencias técnicas sí se transfieren; la solución de negocio no.

## Contexto mínimo

ReservApp gestiona reservas de servicios.

Conceptos iniciales:

- `Usuario`
- `Reserva`
- `Servicio`
- cliente web
- API de reservas
- API Gateway

El dominio debe mantenerse pequeño. Solo se agregan elementos cuando un contenido de la asignatura los necesita.

## Evolución por experiencias de aprendizaje

### Semanas 1–5 · APIs, Gateway e identidad

ReservApp evoluciona desde una API simple hacia una solución protegida.

1. API REST de reservas y recursos.
2. API Gateway.
3. versionado `/v1` y `/v2`.
4. CORS.
5. autenticación vs autorización.
6. OAuth2 y OIDC.
7. scopes `reservations.read` / `reservations.write`.
8. JWT y claims.
9. integración progresiva con proveedor de identidad cuando corresponda.
10. frontend → gateway → API protegida.

### Semanas 8–10 · Mensajería asíncrona

La misma aplicación incorpora eventos sin cambiar de dominio.

Eventos sugeridos:

- `reservation.created`
- `reservation.confirmed`
- `reservation.cancelled`

Evolución:

- productor y consumidor;
- cola;
- exchanges y routing keys;
- acknowledgements;
- durabilidad;
- DLX/DLQ;
- monitoreo.

Ejemplo de consumidor formativo: un componente de notificaciones recibe `reservation.confirmed` sin acoplarse directamente a la API de reservas.

### Semanas 12–15 · Streaming con Kafka

ReservApp produce un flujo de eventos que permita estudiar comportamiento agregado y tiempo real.

Eventos posibles:

- solicitudes de reserva;
- reservas confirmadas;
- cancelaciones;
- cambios de estado.

Se reutilizan para estudiar:

- topics;
- particiones;
- consumer groups;
- offsets;
- procesamiento en tiempo real;
- replicación;
- retención;
- consumer lag;
- manejo de errores.

Un caso formativo posible es calcular reservas confirmadas por servicio o ventana temporal a partir del stream, sin convertirlo en un sistema de analítica complejo.

## Regla de continuidad

Cada laboratorio debe indicar explícitamente:

1. **Qué parte de ReservApp recibe como entrada** desde clases anteriores.
2. **Qué capacidad nueva incorpora**.
3. **Qué artefactos deben conservarse** para la siguiente experiencia.
4. **Qué conceptos son nuevos** y cuáles se reutilizan.

No se parte de cero salvo que exista una razón pedagógica documentada.

## Estructura pedagógica estándar

Para una unidad de tamaño suficiente:

**concepto → demostración sobre ReservApp → ejemplo guiado → práctica/laboratorio sobre ReservApp → evidencia → defensa técnica → checkpoint**

El ejemplo guiado puede mostrar una porción reducida de la solución. El laboratorio no debe consistir en copiar el ejemplo: debe exigir aplicar la misma competencia a una situación adicional del dominio.

## Checkpoint

Al cierre de cada semana debe existir un estado reproducible de ReservApp o, como mínimo, artefactos de arquitectura/documentación que puedan ser retomados posteriormente.

La continuidad importa más que completar artificialmente funcionalidades fuera del objetivo de aprendizaje.