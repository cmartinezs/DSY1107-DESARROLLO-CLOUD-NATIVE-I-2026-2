# Validación local real · 2026-08-27

## Contexto

Ejecución realizada sobre un clone local en WSL2 de la rama:

```text
feat/guias-integradas-ev1
```

HEAD observado durante la ejecución más reciente:

```text
79f47c5de8b1
```

Repositorio local:

```text
/home/carlos/projects/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2
```

## Entorno observado

```text
Python 3.12.3
Git 2.43.0
OpenJDK 21.0.12
Node v24.15.0
npm 12.0.2
Linux 6.6.87.2-microsoft-standard-WSL2 x86_64
```

## Ejecución

Comando:

```bash
python3 scripts/validate_ev1.py
```

Resultado resumido:

```text
PASS  tool:Python
PASS  tool:Git
PASS  tool:Java
PASS  tool:Node
PASS  tool:npm
PASS  git:worktree
PASS  git:origin
PASS  git:clean
PASS  docs:static
SKIP  backend
SKIP  frontend

PASS=9  WARN=0  SKIP=2  FAIL=0
PASS  VALIDACIÓN EV1: PASS
```

La validación estática interna ejecutó además:

```text
Guías revisadas: 35
PASS: enlaces relativos, fences, Mermaid básico y reglas semánticas.
```

## Interpretación

Esta ejecución confirma en un entorno local real:

- tooling base disponible;
- Java 21 correcto;
- repositorio Git válido;
- rama correcta;
- origin configurado mediante SSH;
- working tree limpio;
- validación estática de las guías en PASS;
- enlaces relativos, fences, Mermaid básico y reglas semánticas en PASS;
- el nuevo checkpoint curricular 03B está incorporado y no rompe la validación documental.

Los estados `SKIP` de backend y frontend son esperados en el repositorio docente actual, porque `guia/ev1/backend` y `guia/ev1/frontend` todavía no han sido materializados como proyectos ejecutables.

Por tanto, este resultado **no constituye todavía validación funcional de Maven/Angular ni E2E cloud**.

## Próximo nivel de validación

El siguiente gate es materializar CloudTasks hasta el checkpoint curricular vigente y luego ejecutar:

```bash
python3 scripts/validate_ev1.py --require-projects --strict
```

Ese gate debe pasar solo cuando existan y compilen realmente:

```text
guia/ev1/backend
guia/ev1/frontend
```

Después vendrá el smoke test funcional de Semana 3:

```text
CloudTasks local
→ External tenant + user flow
→ cloudtasks-spa + cloudtasks-api + scopes
→ login real con MSAL
→ Access Token real
→ JWT decodificado
→ iss/aud/sub/exp/scp comprendidos
→ JWT Authorizer configurado en API Gateway
```

No declarar Maven/Angular/Entra/AWS como PASS hasta ejecutarlos realmente.
