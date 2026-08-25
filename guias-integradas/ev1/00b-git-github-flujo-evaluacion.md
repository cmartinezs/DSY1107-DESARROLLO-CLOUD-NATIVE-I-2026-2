# 00B · Git y GitHub aplicados a EV1

**REQUERIDO EV1** · El repositorio debe permitir reconstruir la entrega sin depender del computador original.

## Objetivo

Crear el espacio de trabajo de EV1 dentro del repositorio personal, trabajar con Git sin repositorios anidados y verificar que cada checkpoint relevante quede persistido en GitHub.

## Estructura esperada

```text
DSY1107-00XD-nombre-apellido/
└── evaluaciones/
    └── ep1/
        ├── README.md
        ├── frontend/
        ├── backend/
        └── docs/
```

No ejecutar `git init` dentro de `frontend/` ni `backend/`. Angular se crea con `--skip-git`.

## Flujo inicial

### GitHub Desktop

1. `File → Clone repository`.
2. Seleccionar el repositorio personal DSY1107.
3. Confirmar una ruta local conocida.
4. `Fetch origin` y luego `Pull` si corresponde.
5. Crear `evaluaciones/ep1/`.

### Terminal equivalente

```bash
git clone <URL_REPO>
cd DSY1107-00XD-nombre-apellido
git pull
mkdir -p evaluaciones/ep1
```

## Comprobar que existe un solo repositorio

Desde `evaluaciones/ep1/`:

```bash
git rev-parse --show-toplevel
```

Debe devolver la raíz del repositorio personal, no `frontend/` ni `backend/`.

## Branch de trabajo

Si el docente solicita trabajo individual directo puede usarse el branch definido para la asignatura. En trabajo grupal o cuando se quiera practicar colaboración:

```bash
git switch -c ev1/cloudtasks
```

No crear branches por cada archivo. La branch representa una unidad de trabajo coherente.

## Checkpoint Git mínimo

Al cerrar una puerta de validación:

```bash
git status
git add evaluaciones/ep1
git commit -m "ev1: completar checkpoint <XX>"
git push
```

**EVIDENCIA** · Abrir GitHub en navegador y comprobar que el commit y los archivos realmente existen remotamente.

## `.gitignore`

Debe excluir al menos:

```gitignore
.idea/
.vscode/
*.iml
backend/target/
frontend/node_modules/
frontend/dist/
.env
.env.*
ev1-local-values.txt
```

No ignorar `mvnw`, `mvnw.cmd`, `.mvn/`, `pom.xml`, `package.json`, `package-lock.json`, `angular.json` ni source code.

## Archivos sensibles

Antes de cada push:

```bash
git diff --cached
```

Buscar accidentalmente:

```text
Bearer ey...
client_secret
AWS_ACCESS_KEY_ID
AWS_SECRET_ACCESS_KEY
password=
```

Si un secreto ya fue committeado, borrarlo del archivo **no lo convierte nuevamente en secreto**. Debe revocarse/rotarse.

## Checkpoint 00B

- [ ] `evaluaciones/ep1/` existe.
- [ ] `git rev-parse --show-toplevel` apunta al repo personal.
- [ ] no hay `.git` anidados.
- [ ] `.gitignore` protege artefactos y secretos.
- [ ] existe al menos un commit de checkpoint.
- [ ] el commit es visible en GitHub.
- [ ] otro integrante podría clonar el repo y localizar EV1.

**SI FALLA** · No crear frontend/backend hasta corregir repositorio, branch, permisos o sincronización.
