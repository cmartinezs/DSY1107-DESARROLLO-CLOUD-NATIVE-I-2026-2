# Cierre de la Experiencia de Aprendizaje 1

## Resultado acumulado

La primera experiencia deja como base:

```text
SPA
→ Identity as a Service
→ Access Token
→ API Manager / Gateway
→ Backend protegido
```

## Transición a Semana 08

La siguiente experiencia cambia el problema:

```text
request/response inmediato
→ trabajo desacoplado
→ broker
→ mensajes asíncronos
```

La seguridad aprendida no desaparece. RabbitMQ se incorpora como una nueva capacidad arquitectónica, no como reemplazo de las fronteras existentes.


## Qué se conserva como baseline

La primera experiencia no deja solo tecnologías configuradas. Deja un modelo mental:

- el frontend es un cliente;
- el Identity Provider autentica y emite tokens;
- el Access Token está destinado a un recurso;
- el Gateway protege una frontera;
- el backend conserva seguridad de aplicación;
- 401 y 403 representan fallos distintos;
- la evidencia debe ser reproducible.

## Qué cambia en Semana 08

Hasta aquí el recorrido dominante es inmediato:

Request → validación → respuesta.

Con mensajería aparece otra posibilidad:

Acción → publicación → broker → procesamiento posterior.

Eso introduce nuevas preguntas:

- ¿debe el solicitante esperar?;
- ¿qué ocurre si el receptor no está disponible?;
- ¿cómo se desacopla una capacidad?;
- ¿qué parte del sistema procesa después?

## Qué NO cambia

La mensajería no invalida:

- separación de responsabilidades;
- seguridad;
- trazabilidad;
- contratos claros;
- diagnóstico por fronteras.

Al contrario, esos principios se vuelven más importantes cuando aparecen más componentes.

## Checklist de cierre de EA1

- [ ] arquitectura actual documentada;
- [ ] flujo seguro demostrado;
- [ ] casos 401/403/2xx conocidos;
- [ ] deuda explícita;
- [ ] secretos fuera del repositorio;
- [ ] commits y DevLog suficientes;
- [ ] cada integrante puede explicar el flujo;
- [ ] baseline acordado antes de iniciar RA2.

## Pregunta de transición

Antes de comenzar RabbitMQ:

> ¿Qué operación de nuestro sistema podría ejecutarse después sin bloquear la respuesta principal?

Esa pregunta conecta naturalmente la arquitectura segura de EA1 con la mensajería asíncrona de EA2.
