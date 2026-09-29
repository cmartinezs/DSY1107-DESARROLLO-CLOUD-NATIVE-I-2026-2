# Defensa técnica · cierre Parcial 1

Semana 07 continúa las defensas pendientes sin introducir una nueva experiencia conceptual.

## Foco

- comprensión individual;
- trazabilidad entre código y explicación;
- diagnóstico de fallas;
- justificación de decisiones;
- diferenciación entre configuración cloud y reglas de negocio.

## Criterio

Una respuesta técnicamente defendible debe conectar evidencia observable con una decisión concreta. Nombrar servicios sin explicar responsabilidades no demuestra comprensión.


## Qué se espera de una respuesta técnica

Una buena respuesta conecta:

- una afirmación;
- una evidencia;
- una responsabilidad;
- una decisión.

Ejemplo débil:

“Spring Security protege la API.”

Ejemplo defendible:

“Spring Security actúa como Resource Server, valida el token destinado a la API y exige el scope configurado para el endpoint. Lo verificamos con un caso 401, uno 403 y uno 2xx.”

## Diagnóstico como parte de la defensa

Comprender una solución también implica saber qué revisar cuando falla.

El estudiante debería poder explicar cómo distinguir:

- error de login;
- token incorrecto;
- rechazo del Gateway;
- rechazo del backend;
- autorización insuficiente;
- regla de negocio.

## Preguntas hipotéticas

La defensa puede incorporar variaciones como:

- ¿qué ocurre si cambia la audience?;
- ¿qué pasa si agregamos un endpoint con otro scope?;
- ¿qué ocurre si el Gateway deja de validar JWT?;
- ¿qué componente debería seguir protegido?;
- ¿qué cambiaría al reemplazar el proveedor de identidad?

Estas preguntas permiten observar comprensión transferible.

## Error frecuente: memorizar nombres

Nombrar MSAL, JWT, Gateway y Spring Security no basta.

La defensa debe explicar relaciones y responsabilidades.

## Criterio de cierre individual

Cada estudiante debería poder narrar el flujo completo sin depender de que otro integrante complete las partes críticas.
