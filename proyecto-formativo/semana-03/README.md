# RegistrApp · Semana 3

## Estado de entrada

RegistrApp recibe **todo el estado de salida de Semana 2**. No se reinicia el proyecto.

Como mínimo debe existir evidencia de lo realmente alcanzado hasta ese momento: API inicial y, según la sección, gateway, versionado, CORS y/o arquitectura conceptual de identidad.

Cualquier deuda de Semana 2 se arrastra explícitamente; Semana 3 no la da por resuelta por defecto.

## Contenido transferible

Solo después de haberlo aprendido y practicado fuera de RegistrApp pueden incorporarse:

- anatomía de JWT;
- `iss`, `aud`, `exp` y scopes;
- decodificación vs verificación;
- 401 vs 403;
- separación entre identidad, gateway y backend;
- seguridad aplicada al gateway/API.

## Incremento

Sobre la arquitectura existente:

1. identificar usuario, cliente, IdP/Authorization Server, gateway y API protegida;
2. proponer issuer, audience y scopes con justificación;
3. distribuir responsabilidades entre identidad, gateway y backend;
4. definir al menos cuatro casos de acceso;
5. agregar un caso donde el token sea técnicamente válido pero una regla de negocio impida la operación;
6. registrar al menos una decisión técnica y su impacto sobre lo construido en Semanas 1–2.

## Matriz de responsabilidades

| Control | Responsable propuesto | Justificación |
|---|---|---|
| emisión del token | | |
| firma / integridad | | |
| issuer | | |
| audience | | |
| expiración | | |
| scope | | |
| regla de negocio | | |

## Evidencia incremental

La evidencia debe permitir comparar el **antes y después**:

- diagrama anterior y diagrama actualizado, o diff claramente explicable;
- propuesta de `iss`, `aud` y scopes;
- matriz de responsabilidades;
- matriz de casos de acceso;
- decisión técnica justificada;
- referencia a commits/archivos modificados cuando corresponda;
- DevLog con qué cambió respecto del checkpoint anterior.

## Estado de salida

RegistrApp mantiene todo lo construido previamente y añade únicamente las capacidades de seguridad efectivamente comprendidas e implementadas/diseñadas durante Semana 3.

Este estado completo será la **entrada obligatoria de Semana 4**.

## Deuda / siguiente paso

Registrar explícitamente qué capacidad de Semanas 1–3 sigue pendiente y desde qué punto exacto debe continuar el siguiente incremento.

## Refuerzo clase a clase

Dentro de esta misma semana pueden existir varios incrementos pequeños. Cada uno debe indicar qué competencia recién aprendida habilitó el cambio y qué parte del estado anterior fue modificada.
