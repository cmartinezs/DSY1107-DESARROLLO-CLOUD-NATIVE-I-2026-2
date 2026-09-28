# 00B · Git y GitHub aplicados a la guía

## Objetivo

Crear un espacio de trabajo propio para desarrollar CloudTasks como práctica guiada, trabajar con Git sin repositorios anidados y dejar checkpoints persistidos en GitHub.

## Estructura esperada

```text
DSY1107-00XD-nombre-apellido/
└── guia/
    └── ev1/
        ├── README.md
        ├── frontend/
        ├── backend/
        └── docs/
```

Esta estructura corresponde exclusivamente al desarrollo de esta guía integrada.

No ejecutar `git init` dentro de `frontend/` ni `backend/`. Angular se crea con `--skip-git`.

## Flujo inicial

### GitHub Desktop

1. `File → Clone repository`.
2. Seleccionar el repositorio personal DSY1107.
3. Confirmar una ruta local conocida.
4. `Fetch origin` y luego `Pull` si corresponde.
5. Crear `guia/ev1/`.

### Terminal equivalente

```bash
git clone <URL_REPO>
cd DSY1107-00XD-nombre-apellido
git pull
mkdir -p guia/ev1
```

## Comprobar que existe un solo repositorio

Desde `guia/ev1/`:

```bash
git rev-parse --show-toplevel
```

Debe devolver la raíz del repositorio personal, no `frontend/` ni `backend/`.

## Branch de trabajo

Para practicar un flujo ordenado puede utilizarse una branch dedicada a la guía:

```bash
git switch -c guia/ev1-cloudtasks
```

No crear branches por cada archivo. La branch representa una unidad de trabajo coherente.

## Checkpoint Git mínimo

Al cerrar una puerta de validación:

```bash
git status
git add guia/ev1
git commit -m "guia-ev1: completar checkpoint <XX>"
git push
```

Abrir GitHub en navegador y comprobar que el commit y los archivos realmente existen remotamente.

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

- [ ] `guia/ev1/` existe.
- [ ] `git rev-parse --show-toplevel` apunta al repo personal.
- [ ] no hay `.git` anidados.
- [ ] `.gitignore` protege artefactos y secretos.
- [ ] existe al menos un commit de checkpoint.
- [ ] el commit es visible en GitHub.
- [ ] otra persona podría clonar el repo y localizar el trabajo de la guía.

**SI FALLA** · No crear frontend/backend hasta corregir repositorio, branch, permisos o sincronización.
