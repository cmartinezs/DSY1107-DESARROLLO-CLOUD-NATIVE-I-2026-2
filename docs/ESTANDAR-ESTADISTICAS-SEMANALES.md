# Estándar transversal de estadísticas semanales · 2026-2

Este documento define un contrato común para registrar el estado semanal de DSY1102, DSY1105 y DSY1107 de forma comparable y procesable automáticamente.

## Objetivo

Cada curso debe poder responder, semana a semana:

- qué estaba planificado;
- qué se alcanzó realmente;
- qué evidencia formativa quedó disponible;
- qué nivel de participación agregada existe en DevLog y actividades;
- qué avance tuvo el proyecto/dominio formativo transversal;
- qué bloqueos o deudas deben arrastrarse;
- qué requiere atención la semana siguiente.

La intención es alimentar dashboards, tendencias y revisiones docentes sin depender de leer manualmente documentos distintos por asignatura.

## Fuente canónica

Los datos agregados viven en:

```text
data/weekly/
├── README.md
├── semana-01.yml
├── semana-02.yml
└── ...
```

Cada archivo representa una semana curricular de una asignatura. En cursos con múltiples secciones, el mismo archivo contiene un bloque por sección.

## Principios

1. **Mismo esquema entre cursos.** Las claves comunes no cambian según la asignatura.
2. **Agregados, no datos personales.** No guardar nombres, notas individuales ni información sensible.
3. **Avance real separado del plan.** `planned` y `actual` nunca se mezclan.
4. **Valores desconocidos permanecen `null`.** No estimar datos para completar el archivo.
5. **Una actualización mínima por cierre semanal.** Puede actualizarse durante la semana, pero al cierre debe quedar reconciliado.
6. **Extensiones específicas aisladas.** Cada curso puede agregar `course_specific` sin romper el contrato común.

## Esquema mínimo

```yaml
schema_version: 1
course: DSY1107
term: 2026-2
week: 2
period:
  start: 2026-08-17
  end: 2026-08-22

sections:
  - id: 002D
    sessions:
      planned: null
      completed: null
    blocks:
      planned: null
      completed: null
    curricular_progress:
      planned_topics: []
      covered_topics: []
      pending_topics: []
    evidence:
      class_practice_available: null
      labs_available: null
      weekly_challenge_available: null
    devlog:
      expected: null
      submitted: null
      reviewed: null
    formative_project:
      name: ReservApp
      status: null
      checkpoint: null
    blockers: []
    next_week_focus: []

course_specific: {}
```

## Métricas derivables

A partir de este contrato pueden calcularse, sin cambiar el formato:

- porcentaje de sesiones y bloques realizados;
- cobertura curricular semanal;
- deuda curricular acumulada;
- tasa agregada de DevLog entregado/revisado;
- cantidad de prácticas/labs/desafíos disponibles;
- frecuencia de bloqueos recurrentes;
- avance del proyecto formativo;
- diferencias de avance entre secciones;
- tendencia de participación semana a semana.

## DevLog

Los DevLogs reales permanecen en los repositorios de los estudiantes. El archivo semanal solo conserva agregados:

```yaml
devlog:
  expected: 30
  submitted: 24
  reviewed: 18
```

No copiar contenido personal al dataset.

## Proyecto o dominio formativo

La clave común es `formative_project`, aunque cada asignatura la materialice de forma distinta:

- DSY1102 → PetCare;
- DSY1105 → PocketLog;
- DSY1107 → ReservApp / dominio formativo transversal.

Esto permite comparar continuidad formativa sin forzar que los tres artefactos tengan idéntica naturaleza técnica.

## Cierre semanal

Antes de considerar una semana cerrada:

- reconciliar planificación y avance real;
- actualizar agregados DevLog disponibles;
- registrar evidencia formativa liberada;
- registrar estado del proyecto/dominio transversal;
- consignar bloqueos/deuda;
- dejar `next_week_focus` con acciones concretas;
- mantener en `null` lo que todavía no pueda medirse con evidencia.

## Evolución del esquema

Si en el futuro se agrega una métrica común, se incrementa `schema_version` y se actualiza este documento en los tres repositorios. Las claves existentes no deben cambiar de significado silenciosamente.
