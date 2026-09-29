# Defensa y evidencia · Parcial 1

## Evidencia mínima

- arquitectura actual;
- repositorio con historial;
- ejecución reproducible;
- pruebas positivas y negativas;
- explicación de decisiones.

## Durante la defensa

El estudiante debe poder responder sin memorizar una receta:

1. ¿Por qué la SPA no usa client secret?
2. ¿Qué diferencia existe entre ID Token y Access Token?
3. ¿Qué significan issuer y audience?
4. ¿Qué valida API Gateway y qué sigue validando Spring?
5. ¿Dónde se produce un 401 y dónde un 403?
6. ¿Qué cambiaría si se reemplaza el proveedor de identidad?

## Cierre

Las deudas detectadas se registran como entrada de Semana 07. La evaluación no borra el historial técnico del proyecto.


## Cómo estructurar una defensa técnica

Una defensa clara puede seguir esta secuencia:

1. problema;
2. arquitectura;
3. recorrido de una request;
4. evidencia;
5. fallo controlado;
6. decisión técnica;
7. deuda o mejora.

Esto evita una presentación basada únicamente en “mostrar pantallas”.

## Evidencia fuerte

Una evidencia fuerte permite relacionar afirmación y comportamiento.

Ejemplos:

- diagrama que coincide con la implementación;
- request sin token que produce 401;
- token con audience correcta;
- endpoint protegido que exige scope;
- commit donde se incorporó la validación;
- log sanitizado que muestra la frontera de rechazo.

## Evidencia débil

- capturas sin contexto;
- código que no se ejecuta;
- pantallas de proveedor sin explicar su función;
- tokens completos pegados en documentos;
- afirmaciones sin relación con el repositorio.

## Comprensión individual

Aunque la implementación sea grupal, cada integrante debería poder:

- explicar una frontera;
- diagnosticar un caso;
- justificar una decisión;
- responder un escenario hipotético.

## Preguntas de profundización

Además de las preguntas base:

1. ¿por qué un JWT firmado puede ser rechazado por audience?;
2. ¿qué riesgo existe si Spring confía solo en el Gateway?;
3. ¿qué diferencia hay entre scope y regla de negocio?;
4. ¿qué cambiaría si la SPA fuese reemplazada por un cliente servidor?;
5. ¿qué evidencia demuestra que una respuesta 403 es esperada?

## Cierre de defensa

La defensa debería terminar con un estado explícito:

- validado;
- parcialmente validado;
- deuda conocida.

La evaluación técnica gana valor cuando deja trazabilidad del estado real.
