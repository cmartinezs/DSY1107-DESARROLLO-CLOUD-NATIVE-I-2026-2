# Convención transversal de contenido didáctico

Esta asignatura organiza el material con **lectura por capas**. El objetivo es que un estudiante pueda comprender un tema leyendo su `README.md` y, cuando necesite mayor profundidad, acceder a documentos especializados sin que el flujo principal dependa de ellos.

## Regla principal

> Un tema relevante se modela como una unidad autocontenida. Su `README.md` debe ser suficiente para comprender el tema; los documentos secundarios existen para profundizar.

No todo concepto debe convertirse en carpeta. La estructura crece de forma orgánica según complejidad pedagógica.

## Niveles de complejidad

### Tema pequeño

Usar un único archivo Markdown cuando el concepto puede explicarse de forma completa y legible sin fragmentarlo.

```text
tema.md
```

### Tema mediano

Usar una carpeta con `README.md` y documentos de profundización cuando el concepto ya contiene varias partes independientes.

```text
tema/
├── README.md
├── 01-subtema.md
├── 02-subtema.md
└── 03-errores-frecuentes.md
```

### Tema complejo

Usar carpeta, README conciliador y subcarpetas cuando uno de los subtemas también requiera su propio recorrido pedagógico.

```text
tema/
├── README.md
├── 01-concepto-base.md
└── 02-subtema-complejo/
    ├── README.md
    ├── 01-parte.md
    └── 02-parte.md
```

## Responsabilidad del README

El `README.md` de cada tema debe poder leerse por sí solo. Debe incluir, cuando corresponda:

1. objetivo de aprendizaje;
2. problema o contexto que motiva el tema;
3. conceptos fundamentales;
4. actores o componentes relevantes;
5. flujo o proceso general;
6. ejemplo contextualizado en la asignatura;
7. conceptos que el estudiante debe recordar;
8. errores o confusiones frecuentes;
9. preguntas de comprobación;
10. enlaces de profundización;
11. siguiente paso: ejemplo, laboratorio, desafío o proyecto formativo.

## Regla de navegación

Nunca se debe obligar al estudiante a abrir varios archivos para entender el flujo principal.

Los enlaces secundarios deben responder a:

> "Entendí la idea general y quiero profundizar esta parte."

No a:

> "No puedo entender el tema si no abro otros cuatro documentos."

## Etiqueta de profundización

Cuando un concepto tenga material extendido, utilizar una llamada clara y consistente:

```markdown
> **Si quieres profundizar:** revisa [Nombre del subtema](ruta/al/documento.md).
```

También puede utilizarse:

```markdown
> **Para profundizar:** este documento explica con mayor detalle ...
```

## Numeración

Usar prefijos `01-`, `02-`, `03-`, etc. cuando exista un orden pedagógico recomendado. La numeración guía la lectura, pero no impide que un estudiante acceda directamente a un subtema para resolver una duda.

## Relación con ejemplos y laboratorios

La documentación conceptual, los ejemplos y los laboratorios cumplen responsabilidades distintas:

```text
COMPRENDER  → README del tema
PROFUNDIZAR → documentos secundarios
OBSERVAR    → ejemplos
APLICAR     → laboratorios / desafíos / proyecto formativo
```

Evitar convertir el README en un laboratorio y evitar que el laboratorio sea el único lugar donde se explique un concepto importante.

## Criterio para dividir un documento existente

Considerar migrar un `.md` a carpeta cuando ocurra una o más de estas situaciones:

- mezcla varios conceptos que podrían consultarse de forma independiente;
- contiene varias secciones extensas con diagramas propios;
- supera una longitud donde navegar deja de ser cómodo;
- aparecen preguntas recurrentes sobre una sección particular;
- requiere explicación conceptual, técnica y ejemplos claramente separables;
- un subtema necesita profundización adicional sin sobrecargar al lector principal.

El tamaño por sí solo no obliga a dividir: prima la coherencia pedagógica.

## Principio editorial

El repositorio debe funcionar como **documentación técnica educativa**, no como una colección de apuntes aislados.

Cada tema debe ofrecer un recorrido progresivo:

```text
orientación → comprensión → profundización → aplicación
```

Esta convención aplica al material nuevo y se adoptará progresivamente en contenido existente cuando sea modificado o cuando su complejidad lo justifique.
