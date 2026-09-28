# Auditoría estructural · DSY1107 · 28-09-2026

## Referencia

Se utilizó **DSY1102 Desarrollo Orientado a Objetos 2026-2** como referencia de nivel de completitud, no como plantilla de contenido.

El patrón útil observado allí es:

```text
README de carpeta = índice y propósito
*.md separados     = contenido real
labs               = etapas + checkpoints
ejercicios         = práctica breve
proyecto formativo = pasos incrementales
docs               = base académica estable
```

## Hallazgos en DSY1107 antes de esta conciliación

### 1. Faltaba la capa académica estable

No existían:

- `docs/PDA-RESUMEN.md`;
- `docs/RESULTADOS-DE-APRENDIZAJE.md`;
- `docs/RUTA-DE-APRENDIZAJE.md`;
- `docs/CRONOGRAMA.md`;
- `evaluaciones/README.md`.

Esto hacía que el repositorio describiera bien varias semanas, pero no pudiera responder de forma directa **qué es la asignatura completa**.

### 2. Faltaba `ejercicios/`

La ruta pedagógica decía “concepto → ejemplo → lab”, pero no existía una superficie para práctica breve no guiada.

Resultado: ejercicios pequeños terminaban mezclados dentro de semanas, labs o conversaciones docentes.

### 3. `examples/` tenía índices sin ejemplos reales

Semana 01 y 02 eran esencialmente READMEs que apuntaban a labs. Semana 03 y 04 tenían casos escritos dentro del README, pero sin archivos independientes.

Eso cumple navegación, no el nivel esperado de una vertical de ejemplos.

### 4. Semana 06 no existía

El cronograma institucional contiene Semana 06 como Parcial 1. La ausencia rompía la continuidad temporal del repo.

### 5. Semana 07 tenía cierre evaluativo, pero poca descomposición

Existía README, pero faltaban archivos para defensa, evidencia y cierre de la experiencia.

### 6. Semana 08 tenía buen índice, pero contenido comprimido

El README describía 2.1.1–2.1.3, pero faltaban archivos que enseñaran cada materia al nivel de `semanas/`.

### 7. Lab RabbitMQ era un checklist, no un lab completo

Tenía checkpoints listados, pero no etapas separadas, instrucciones y evidencia por paso.

### 8. RegistrApp semanas 06–08 estaba incompleto

Semanas 06 y 07 eran placeholders. Semana 08 describía el incremento, pero no lo dividía en selección de capacidad, diseño, integración y evidencia.

### 9. `guias-integradas/` concentraba material de gran profundidad

El rescate de EV1 mostró que parte del conocimiento más completo estaba fuera de las raíces canónicas. Se reclasificó como `labs/cloudtasks-ev1-integrado/`, preservando la secuencia completa sin mantener una raíz pedagógica adicional.

## Por qué ocurrió

La estructura evidencia crecimiento incremental del repositorio antes de estabilizarse el canon transversal:

1. primero se resolvieron necesidades semanales;
2. luego aparecieron labs y documentación transversal;
3. algunos READMEs se usaron como placeholders de futuras capas;
4. el canon común se formalizó después;
5. la guía integrada EV1 creció como solución a huecos end-to-end.

No es un problema de contenido inexistente: gran parte estaba **disperso o en el nivel estructural equivocado**.

## Criterio adoptado

DSY1107 queda alineado al mismo nivel de completitud de DSY1102, manteniendo sus particularidades Cloud Native:

```text
base académica
→ semana con materias separadas
→ ejemplo demostrativo
→ ejercicio breve
→ lab guiado por etapas
→ transferencia a RegistrApp
→ evidencia
```

## Estado después de la conciliación

- base académica estable creada;
- `ejercicios/` creado y poblado hasta la semana vigente donde aporta valor;
- examples 01–05 y 08 materializados con artefactos independientes;
- Semana 06 incorporada y Semanas 07–08 descompuestas;
- datos semanales reconciliados hasta Semana 08;
- labs JWT/RabbitMQ descompuestos; RabbitMQ profundizado como experiencia reproducible;
- CloudTasks reclasificado a `labs/cloudtasks-ev1-integrado/`;
- `guia/` y `guias-integradas/` retirados como raíces activas;
- RegistrApp descompuesto incrementalmente hasta Semana 08;
- governance declarativa incorporada.

## Deuda deliberada

- reconciliar el portal web con toda la nueva base académica;
- seguir descomponiendo labs históricos monolíticos solo cuando aporte mantenibilidad;
- mantener semanas futuras sin inventar contenido antes de su preparación;
- ejecutar validación/replay de publicación antes de declarar conformidad FULL.
