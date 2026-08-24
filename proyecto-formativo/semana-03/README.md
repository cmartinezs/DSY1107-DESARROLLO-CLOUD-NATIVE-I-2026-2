# RegistrApp · Checkpoint Semana 3

## Propósito

Aplicar en RegistrApp, de forma autónoma, las competencias de seguridad e identidad que hayan sido realmente comprendidas durante las clases.

Este checkpoint **no enseña JWT ni seguridad de APIs** y no pertenece al contenido de Semana 3. Es parte de la vertical independiente de RegistrApp.

## Prerrequisitos posibles

Antes de aplicar cada elemento, el estudiante debe haber comprendido y practicado fuera de RegistrApp:

- anatomía de JWT;
- `iss`, `aud`, `exp` y scopes;
- decodificación vs verificación;
- 401 vs 403;
- separación entre identidad, gateway y backend.

No es obligatorio incorporar una capacidad cuyo contenido todavía no haya sido alcanzado por la sección.

## Incremento sugerido

1. Actualizar la arquitectura de RegistrApp identificando usuario, cliente, IdP/Authorization Server, gateway y API protegida.
2. Proponer issuer, audience y scopes con justificación.
3. Distribuir responsabilidades entre identidad, gateway y backend.
4. Definir al menos cuatro casos de acceso y un caso donde el token sea técnicamente válido pero una regla de negocio impida la operación.
5. Registrar una decisión técnica con motivo, alternativa descartada y consecuencia.

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

## Evidencia del checkpoint

- diagrama actualizado;
- propuesta de `iss`, `aud` y scopes;
- matriz de responsabilidades;
- matriz de casos de acceso;
- decisión técnica justificada;
- DevLog con avance real y siguiente paso.

## Refuerzo clase a clase

Este README representa el checkpoint del periodo, pero puede actualizarse **después de cada clase** cuando exista una nueva competencia aplicable. No es necesario esperar al final de la semana ni completar todo de una sola vez.

La continuidad de RegistrApp depende del aprendizaje real, no del número de semana.
