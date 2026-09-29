# 04 · Formativa 1 y transferencia a RegistrApp

## Formativa 1

La evaluación formativa se realiza como comprobación de comprensión y se revisa colectivamente. El objetivo no es sumar alcance funcional, sino consolidar la arquitectura segura.

## Transferencia

Solo después de demostrar el patrón en un lab independiente se transfiere a RegistrApp:

```text
contenido comprendido
→ lab reproducible
→ transferencia a RegistrApp
→ evidencia del incremento
```

## Evidencia

- endpoint protegido;
- request con Bearer;
- caso 401;
- caso 403 cuando aplique;
- caso 2xx;
- diagrama;
- decisión técnica;
- DevLog.

→ [RegistrApp · Semana 05](../../proyecto-formativo/semana-05/)


## Secuencia pedagógica

La transferencia correcta mantiene este orden:

1. contenido comprendido;
2. laboratorio reproducible;
3. diagnóstico de casos positivos y negativos;
4. evaluación formativa;
5. transferencia a RegistrApp;
6. evidencia del incremento.

RegistrApp no debería ser el lugar donde se descubre por primera vez cada concepto.

## Qué debería comprobar la Formativa 1

Más que recordar nombres, debería revelar si el estudiante distingue:

- ID Token y Access Token;
- cliente y recurso;
- issuer y audience;
- 401 y 403;
- Gateway y backend;
- autenticación y autorización.

La revisión colectiva debe explicar por qué una alternativa es correcta y qué supuesto erróneo existe detrás de los distractores.

## Transferencia con sentido

Antes de modificar RegistrApp, responder:

- ¿qué capacidad del proyecto necesita protección?;
- ¿quién actúa como cliente?;
- ¿cuál es el recurso?;
- ¿qué token necesita?;
- ¿qué permiso representa la operación?;
- ¿qué valida el Gateway?;
- ¿qué conserva el backend?

## Alcance razonable

No es necesario agregar CRUD, persistencia adicional ni nuevos microservicios solo para “mostrar más”.

El incremento es suficiente si integra correctamente identidad, token, Gateway, backend protegido y evidencia.

## Definition of Done

La transferencia puede considerarse cerrada cuando:

- el frontend obtiene el Access Token correcto;
- envía Bearer al Gateway;
- el Gateway enruta/valida;
- el backend protegido valida y autoriza;
- existen casos 401/403/2xx verificables;
- el estudiante puede explicar el recorrido.

## Deuda explícita

Si algo queda pendiente, se documenta con:

- estado actual;
- evidencia;
- causa conocida;
- siguiente acción.

Eso es mejor evidencia técnica que esconder la falla detrás de una captura antigua.

## Preparación para defensa

El estudiante debe poder explicar qué configuró, por qué, cómo lo probó y qué cambiaría si cambia issuer, audience, scope o proveedor.

## Profundización

→ [Contenido extendido · Formativa y transferencia](./04-formativa-y-transferencia/)
