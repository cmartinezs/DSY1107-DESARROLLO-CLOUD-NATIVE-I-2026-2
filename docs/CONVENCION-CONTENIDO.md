# Convención transversal de contenido didáctico

Esta asignatura organiza el material con **lectura por capas**. El objetivo es que cada tema tenga un **archivo Markdown base suficiente para la asignatura** y, cuando el estudiante quiera profundizar, pueda acceder a una carpeta asociada con material extendido.

## Regla principal

> **La carpeta no reemplaza al `.md` base: lo expande.**

El archivo individual del tema debe contener todo lo necesario para comprender los conceptos, vocabulario, relaciones y flujo exigidos por la asignatura. Un estudiante que estudie solamente ese `.md` debe disponer de la base académica requerida.

La carpeta asociada es una segunda capa opcional: explica con más detalle partes del tema, responde dudas frecuentes, desarrolla procesos paso a paso, incorpora ejemplos adicionales o profundiza aspectos técnicos.

## Modelo canónico

### Tema pequeño

Si el contenido base es suficiente y no necesita extensión:

```text
01-tema.md
```

### Tema con profundización

Cuando existe material que vale la pena desarrollar con más detalle, **se conserva el `.md` y se agrega una carpeta homónima**:

```text
01-tema.md                 ← BASE: suficiente para la asignatura
01-tema/                   ← PROFUNDIZACIÓN: opcional
├── README.md              ← mapa de la profundización
├── 01-subtema.md
├── 02-subtema.md
└── 03-errores-frecuentes.md
```

### Subtema complejo dentro de la profundización

La carpeta puede crecer orgánicamente cuando una parte amerite todavía más detalle:

```text
01-tema.md
01-tema/
├── README.md
├── 01-concepto.md
└── 02-subtema-complejo/
    ├── README.md
    ├── 01-parte.md
    └── 02-parte.md
```

Esto **no modifica la responsabilidad del `.md` base**.

## Responsabilidad del `.md` base

El archivo individual es el material principal y debe poder leerse por sí solo. Debe incluir, cuando corresponda:

1. objetivo de aprendizaje;
2. problema o contexto que motiva el tema;
3. conceptos y vocabulario fundamentales;
4. actores o componentes relevantes;
5. flujo o proceso general;
6. ejemplo contextualizado en la asignatura;
7. relaciones importantes entre conceptos;
8. conceptos que el estudiante debe recordar;
9. errores o confusiones fundamentales;
10. vínculo hacia la profundización, si existe;
11. siguiente paso: ejemplo, laboratorio, desafío o proyecto formativo.

El `.md` base puede ser resumido respecto de la carpeta, pero **no puede ser incompleto respecto de los resultados de aprendizaje de la asignatura**.

## Responsabilidad de la carpeta de profundización

La carpeta existe para responder preguntas como:

- ¿cómo funciona exactamente esta parte?;
- ¿por qué existe este mecanismo?;
- ¿qué ocurre paso a paso?;
- ¿qué diferencia hay entre estos conceptos parecidos?;
- ¿qué errores son frecuentes?;
- ¿qué detalles técnicos se omitieron deliberadamente en la explicación base?;
- ¿qué ejemplo adicional puede ayudarme a entenderlo mejor?

Su `README.md` actúa como **mapa de profundización**, no como reemplazo del material principal.

## Regla de navegación

Nunca se debe obligar al estudiante a entrar a la carpeta para comprender lo exigido por la asignatura.

La navegación esperada es:

```text
.md base
   │
   ├── suficiente para aprender lo requerido
   │
   └── "Si quieres profundizar..."
             ↓
        carpeta asociada
             ↓
        detalles específicos
```

Los enlaces secundarios deben responder a:

> "Ya entendí la base y quiero comprender esta parte con más detalle."

No a:

> "El archivo principal no explica esto; debo entrar a la carpeta para poder entenderlo."

## Etiqueta de profundización

Cuando exista una carpeta asociada, utilizar una llamada clara y consistente en el `.md` base:

```markdown
> **Si quieres profundizar:** este tema cuenta con material extendido en [Profundización: Nombre del tema](./01-tema/README.md).
```

Dentro del texto también pueden existir enlaces específicos:

```markdown
> **Para profundizar en este concepto:** revisa [Code Verifier y Code Challenge](./01-tema/02-code-verifier-y-code-challenge.md).
```

## Correspondencia de nombres

Siempre que sea razonable, el archivo y la carpeta deben compartir el mismo nombre base:

```text
01-oauth2-oidc.md
01-oauth2-oidc/

01-guia-kotlin-fundamentos.md
01-guia-kotlin-fundamentos/
```

Esto hace evidente que la carpeta **extiende** al documento y evita que el alumno los interprete como contenidos independientes.

## Numeración

Usar prefijos `01-`, `02-`, `03-`, etc. cuando exista un orden pedagógico recomendado. La numeración guía la lectura, pero no impide acceder directamente a una profundización para resolver una duda.

## Relación con ejemplos y laboratorios

```text
APRENDER LA BASE → archivo .md del tema
PROFUNDIZAR      → carpeta homónima
OBSERVAR         → ejemplos
APLICAR          → laboratorios / desafíos / proyecto formativo
```

La profundización tampoco reemplaza al laboratorio: explicar en detalle y aplicar son actividades pedagógicas distintas.

## Cuándo crear una carpeta de profundización

Agregarla cuando una o más de estas condiciones lo justifiquen:

- un concepto del `.md` requiere explicación paso a paso;
- existen varias preguntas frecuentes sobre partes específicas;
- hay detalles técnicos útiles pero no indispensables para el nivel base;
- se quieren agregar comparaciones, diagramas o ejemplos adicionales sin sobrecargar el `.md` principal;
- una explicación completa haría innecesariamente pesado el recorrido obligatorio;
- un estudiante interesado podría beneficiarse de una segunda capa de lectura.

El tamaño del `.md` por sí solo no obliga a crear una carpeta.

## Aplicación retroactiva

Esta convención es **retroactiva**. Todo material conceptual ya publicado debe revisarse bajo el mismo criterio:

- el `.md` existente se conserva como base suficiente;
- no se mueve contenido esencial desde el `.md` hacia la carpeta;
- cuando exista profundidad útil, se agrega una carpeta homónima;
- los índices semanales deben distinguir claramente contenido base y profundización;
- el material histórico se adapta sin romper enlaces existentes siempre que sea posible.

Laboratorios, diagnósticos, resúmenes por sección, planificación y documentos operativos no requieren una carpeta homónima por el solo hecho de ser Markdown: esta convención se aplica a **temas didácticos/conceptuales**, no mecánicamente a cualquier archivo del repositorio.

## Principio editorial

El repositorio debe funcionar como **documentación técnica educativa de profundidad progresiva**:

```text
base obligatoria → profundización voluntaria → observación → aplicación
```

La profundidad puede crecer tanto como resulte útil, pero nunca a costa de vaciar el archivo principal.

La convención rige tanto para material nuevo como para todo contenido didáctico existente.