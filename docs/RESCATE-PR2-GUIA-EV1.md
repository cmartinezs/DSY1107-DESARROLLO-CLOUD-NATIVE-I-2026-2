# Rescate de conocimiento · PR #2 · Guía integrada EV1

## Propósito

Este documento registra el rescate del conocimiento contenido en el PR histórico #2
(`feat/guias-integradas-ev1`) hacia una rama nueva basada en el `master` actual.

La operación de rescate **no decide todavía la arquitectura documental definitiva** de
`guias-integradas/`. Esa conciliación se realiza en una rama separada.

## Qué se rescató

- ruta integrada CloudTasks;
- preparación de entorno y Git/GitHub;
- matriz de valores y checkpoints;
- Spring Boot + Angular local;
- Microsoft Entra External ID;
- MSAL + Authorization Code + PKCE;
- JWT, claims, issuer, audience y scopes;
- Spring Security Resource Server;
- roles opcionales;
- EC2;
- API Gateway + JWT Authorizer;
- CORS;
- frontend cloud + HTTPS;
- troubleshooting y runbook;
- verificación integrada;
- cleanup;
- ruta Advanced Developer;
- materializador y validadores locales.

## Ajustes de tiempo presente

Las referencias mutables que describían Semana 3 como la “semana actual” o el
“checkpoint vigente” se normalizaron a **checkpoint curricular asociado a Semana 3**.

Esto preserva el contexto histórico de aprendizaje sin afirmar que Semana 3 sigue siendo
la semana vigente del curso.

## Ajustes operacionales mínimos

- el validador local distingue su PASS como **validación local de guía EV1**;
- tanto remotes GitHub por SSH como por HTTPS se consideran válidos;
- `master` conserva la arquitectura canónica actual de dos verticales;
- el README raíz únicamente enlaza el material rescatado y declara explícitamente que
  la conciliación arquitectónica queda pendiente.

## Gate antes de retirar el PR histórico

- [x] rama de rescate creada desde `master` actual;
- [x] archivos de contenido del PR #2 recuperados;
- [x] tooling de validación recuperado;
- [x] referencias temporales principales normalizadas;
- [x] material enlazado desde README sin reescribir todavía la arquitectura canónica;
- [x] conciliación arquitectónica ejecutada en rama separada;
- [x] rescate integrado a `master`.

El PR #2 puede cerrarse como superseded una vez abierto el PR de rescate. La rama histórica
se debe eliminar después de confirmar que el rescate quedó disponible; la eliminación de
rama se realiza mediante GitHub cuando el conector disponible permita borrar refs.


## Resultado de la conciliación

El contenido rescatado quedó reclasificado como:

```text
labs/cloudtasks-ev1-integrado/
```

y el workspace técnico pasó a `.work/cloudtasks-ev1/`, ignorado por Git. Las rutas `guia/` y `guias-integradas/` dejan de formar parte de la estructura activa del repositorio.
