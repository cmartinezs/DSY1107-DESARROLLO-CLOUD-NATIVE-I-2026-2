# Estándar de repositorio del estudiante · DSY1107

Este documento define **cómo entrega y organiza su trabajo el estudiante**. No copia la estructura interna del repositorio docente.

Aplica a las secciones:

```text
DSY1107-002D
DSY1107-003D
```

## 1. Nombre obligatorio

Formato:

```text
<SIGLA>-<SECCION>-nombre-apellido
```

Ejemplos:

```text
DSY1107-002D-carlos-martinez
DSY1107-003D-carlos-martinez
```

Cada estudiante usa únicamente la sección que le corresponde. Nombre y apellido en minúsculas, separados por guion y sin espacios, tildes ni caracteres especiales.

## 2. Un repositorio para todo el semestre

```text
DSY1107-00XD-nombre-apellido/
├── README.md
├── .gitignore
├── practica/
├── labs/
├── proyecto-formativo/
├── desafios/
└── evaluaciones/
    ├── ep1/
    ├── ep2/
    ├── ep3/
    └── eft/
```

Las semanas pertenecen a la planificación/publicación docente; **no es obligatorio replicarlas en la estructura técnica del estudiante**.

## 3. Package raíz personal

Para código Java/Kotlin usa tu usuario Duoc normalizado:

```text
c.martinez → cmartinez → cl.duoc.cmartinez
```

Patrón:

```text
cl.duoc.<usuario-duoc-sin-puntos>
```

## 4. Práctica corta

Cuando una práctica consiste en ejercicios pequeños o ejemplos técnicos, puede mantenerse en un proyecto común y organizarse por bloque conceptual:

```text
practica/
└── src/cl/duoc/cmartinez/
    ├── api/
    ├── versionado/
    ├── cors/
    ├── oauth/
    └── oidc/
```

La semana no forma parte del package.

## 5. Laboratorios Cloud Native

Cada laboratorio de mayor alcance tiene su propia subcarpeta/proyecto:

```text
labs/
└── api-gateway/
    ├── README.md
    ├── pom.xml
    ├── src/
    ├── docs/
    └── .gitignore
```

Según el laboratorio, también pueden ser necesarios:

```text
Dockerfile
docker-compose.yml
compose.yaml
application.yml
application.properties
openapi.yaml
scripts/
```

Se versionan cuando son parte real de la solución y **no contienen secretos**.

## 6. Entregas colaborativas

Algunas actividades pueden ser grupales. Cuando el docente indique explícitamente que una entrega se realiza en un repositorio de equipo, se informará el formato de nombre de ese repositorio y sus integrantes deberán colaborar mediante Git/GitHub.

El repositorio personal del estudiante sigue existiendo para su trabajo individual y evidencias personales. No se debe inventar un repo grupal si la actividad no lo solicita.

En una entrega grupal se espera evidencia de colaboración real: commits identificables, ramas/PR cuando corresponda y README con integrantes y responsabilidades.

## 7. Proyecto formativo

Cuando exista un proyecto transversal de la asignatura, se mantiene en:

```text
proyecto-formativo/
└── <nombre-proyecto>/
```

Debe evolucionar en la misma carpeta y conservar todo lo necesario para reproducir su ejecución.

## 8. Evaluaciones

```text
evaluaciones/
├── ep1/
├── ep2/
├── ep3/
└── eft/
```

Cada evaluación contiene exactamente lo necesario para construir, configurar y revisar la entrega.

## 9. Qué subir / qué no subir

### Sí subir cuando corresponda

- `src/` y código fuente;
- `pom.xml` / `build.gradle` / `build.gradle.kts`;
- `Dockerfile` y archivos Compose necesarios;
- configuración YAML/properties sin secretos;
- contratos OpenAPI u otra documentación técnica requerida;
- scripts necesarios;
- README y carpeta `docs/` cuando la entrega lo requiera.

### No subir

```text
.idea/
.vscode/
*.iml
target/
build/
out/
.gradle/
*.class
*.log
.env
.env.*
*.key
*.pem
```

Nunca subir contraseñas, tokens, access keys, secret keys, credenciales cloud ni archivos de credenciales.

Si una aplicación necesita variables sensibles, documenta los **nombres** requeridos en el README usando valores de ejemplo, no valores reales.

## 10. Markdown obligatorio

Aprenderemos y utilizaremos Markdown como documentación técnica.

README mínimo obligatorio en:

```text
/README.md
/practica/README.md
/labs/README.md
/proyecto-formativo/README.md
/desafios/README.md
/evaluaciones/README.md
```

Además, cada laboratorio, proyecto, desafío o evaluación con identidad propia debe incluir su propio `README.md`.

### README mínimo de proyecto/laboratorio

```markdown
# Nombre de la actividad

## Objetivo
Qué problema se resuelve o qué concepto se aplica.

## Arquitectura / componentes
Qué servicios, aplicaciones o piezas participan.

## Requisitos
Java, Maven/Gradle, Docker u otros requisitos.

## Ejecución
Pasos concretos para ejecutar la solución.

## Endpoints / uso
Cómo probarla, si corresponde.

## Configuración
Variables o archivos requeridos, sin secretos reales.

## Evidencia / decisiones
Qué se implementó y decisiones técnicas relevantes.
```

### README mínimo raíz

```markdown
# DSY1107-002D · Desarrollo Cloud Native I

**Estudiante:** Nombre Apellido  
**Usuario Duoc:** c.martinez  
**Package raíz:** cl.duoc.cmartinez  
**Semestre:** 2026-2

## Contenido
- [Práctica](practica/)
- [Laboratorios](labs/)
- [Proyecto formativo](proyecto-formativo/)
- [Desafíos](desafios/)
- [Evaluaciones](evaluaciones/)
```

Para 003D se reemplaza `002D` por `003D`.

Los README que superen claramente el mínimo por **claridad, estructura, utilidad, diagramas cuando aporten valor, reproducibilidad y explicación de decisiones propias** podrán recibir una compensación o bonificación definida por el docente. No es automática.

## 11. Git

Al comenzar en cualquier computador:

```bash
git pull
```

Al terminar:

```bash
git status
git add .
git commit -m "mensaje claro"
git push
```

Debe existir un solo `.git` en la raíz del repositorio personal, salvo que el docente indique expresamente una entrega en repositorio independiente de equipo.

## 12. Checklist

- [ ] Nombre del repo con sección correcta: `DSY1107-002D-...` o `DSY1107-003D-...`.
- [ ] Package raíz `cl.duoc.<usuario-sin-puntos>` cuando hay código Java/Kotlin.
- [ ] Labs/proyectos contienen todos los archivos necesarios para reproducirlos.
- [ ] Configuración sensible está fuera del repositorio.
- [ ] README raíz y README de cada carpeta/entrega relevante.
- [ ] README explica ejecución y configuración sin asumir el computador del autor.
- [ ] Código/servicios ejecutan correctamente.
- [ ] Commit y push realizados y verificados en GitHub.

> La organización docente y la estructura de entrega del estudiante son conceptos distintos.