# Estándar de repositorio del estudiante · DSY1107

Este documento define cómo organiza y entrega su trabajo el estudiante. **No copia la estructura del repositorio docente.**

Aplica a las secciones `002D` y `003D`.

## 1. Repositorio único y público

Formato:

```text
DSY1107-002D-nombre-apellido
DSY1107-003D-nombre-apellido
```

Cada estudiante usa únicamente su sección.

Reglas:

- un solo repositorio personal para todo el semestre;
- repositorio **público**, salvo instrucción excepcional del docente;
- nombre y apellido en minúsculas, separados por `-`;
- sin espacios, tildes ni caracteres especiales.

## 2. Estructura oficial

```text
DSY1107-00XD-nombre-apellido/
├── README.md
├── .gitignore
├── docs/
│   ├── README.md
│   └── devlog/
│       ├── README.md
│       ├── semana-02.md
│       ├── semana-03.md
│       └── ...
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

`docs/` contiene documentación transversal del estudiante y `docs/devlog/` conserva la evolución semanal personal.

Las semanas no organizan técnicamente el código o los labs; **solo el DevLog utiliza semanas porque registra el proceso en el tiempo**.

## 3. DevLog transversal

El DevLog real vive en:

```text
docs/devlog/semana-XX.md
```

Formato mínimo:

```markdown
# DevLog · Semana XX

## Objetivo
...

## Avance
...

## Bloqueo
...

## Aprendizaje
...

## Siguiente
...
```

En trabajo grupal, el repo de equipo documenta la solución compartida; el **DevLog personal** registra qué hiciste, qué comprendiste, dónde te bloqueaste y cuál es tu siguiente paso.

Manual:

➡️ [`docs/DEVLOG-ESTUDIANTE.md`](./DEVLOG-ESTUDIANTE.md)

## 4. Package raíz personal

Para Java/Kotlin:

```text
c.martinez → cmartinez → cl.duoc.cmartinez
```

## 5. Práctica corta

Cuando corresponda, la práctica pequeña puede vivir en un proyecto común organizado por bloques conceptuales:

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

## 6. Laboratorios Cloud Native

Cada laboratorio de mayor alcance tiene su propia carpeta/proyecto:

```text
labs/
└── api-gateway/
    ├── README.md
    ├── pom.xml
    ├── src/
    ├── docs/
    └── .gitignore
```

Según el caso también pueden existir `Dockerfile`, `compose.yaml`, `application.yml`, `openapi.yaml` o scripts.

### Concepto → local/neutral → cloud real

Cuando exista un par de laboratorios, el estudiante debe poder explicar la transferencia conceptual:

```markdown
## Del laboratorio conceptual al laboratorio cloud

| Concepto | En local/neutral | En cloud |
|---|---|---|
| ... | ... | ... |

### Qué cambió
...

### Qué se mantuvo
...
```

No basta con documentar clics en una consola.

## 7. Trabajo colaborativo

El repositorio personal sigue existiendo durante todo el semestre.

Si una actividad exige un repo de equipo, el docente indicará su formato. Allí se espera evidencia de colaboración real: commits identificables, responsabilidades documentadas y branches/PR cuando corresponda.

El DevLog individual no desaparece por trabajar en grupo.

## 8. Proyecto formativo

Cuando exista un proyecto transversal:

```text
proyecto-formativo/
└── <nombre-proyecto>/
```

Debe evolucionar en la misma carpeta y conservar todo lo necesario para reproducir su ejecución.

## 9. Evaluaciones

```text
evaluaciones/
├── ep1/
├── ep2/
├── ep3/
└── eft/
```

Cada evaluación contiene exactamente lo necesario para construir, configurar y revisar la entrega.

## 10. Markdown obligatorio

README mínimo obligatorio en:

```text
/README.md
/docs/README.md
/docs/devlog/README.md
/practica/README.md
/labs/README.md
/proyecto-formativo/README.md
/desafios/README.md
/evaluaciones/README.md
```

Además, cada lab/proyecto/desafío/evaluación con identidad propia debe incluir README.

El README raíz debe enlazar el DevLog:

```markdown
## Seguimiento
- [DevLog](docs/devlog/)
```

Para labs Cloud Native, el README debe explicar objetivo, arquitectura/componentes, requisitos, ejecución, endpoints/uso, configuración sin secretos, evidencia y decisiones.

Los README sobresalientes por claridad, reproducibilidad y decisiones propias pueden recibir bonificación definida por el docente; cumplir el mínimo no genera bonificación automática.

## 11. Qué versionar

Sí:

- código fuente;
- Maven/Gradle necesarios;
- Docker/Compose cuando correspondan;
- configuración sin secretos;
- contratos API;
- scripts necesarios;
- README y documentación;
- DevLog.

No:

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

Nunca subir contraseñas, tokens, access keys, secret keys o credenciales cloud.

## 12. Flujo Git mínimo

Al comenzar:

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

Debe existir un solo `.git` en la raíz del repositorio personal, salvo repo de equipo solicitado explícitamente.

## 13. Relación entre evidencias

```text
Código/config → qué construiste
Commits       → cómo fue cambiando
README        → cómo se reproduce
DevLog        → qué ocurrió y qué aprendiste
Issues        → qué trabajo quieres abordar, cuando los incorporemos
```

## 14. Checklist

- [ ] Repo público con sección correcta.
- [ ] `docs/devlog/` existe y contiene la entrada semanal.
- [ ] DevLog tiene objetivo, avance, bloqueo, aprendizaje y siguiente.
- [ ] DevLog no expone secretos ni datos sensibles.
- [ ] Labs/proyectos contienen lo necesario para reproducirse.
- [ ] Puedo explicar el concepto detrás del servicio cloud.
- [ ] Si existe local → cloud, documenté qué cambió y qué se mantuvo.
- [ ] README raíz y carpetas relevantes documentados.
- [ ] Configuración sensible fuera del repo.
- [ ] Código/servicios ejecutan correctamente según corresponda.
- [ ] Commit y push realizados y verificados en GitHub.

> El repositorio del curso enseña y publica. El repositorio personal conserva tu trabajo, evolución, DevLog y evidencia técnica del semestre.
