# RegistrApp · Arquitectura y continuidad incremental

## Principio

RegistrApp es una única solución que evoluciona durante el semestre. Cada checkpoint semanal debe partir del estado anterior y dejar un nuevo estado reproducible.

No se crean ejercicios independientes llamados RegistrApp semana a semana.

```text
estado N-1
→ competencia nueva aprendida
→ decisión de transferencia
→ incremento
→ evidencia
→ estado N
```

## Contrato obligatorio de cada semana

Todo `proyecto-formativo/semana-XX/README.md` debe declarar:

1. **Estado de entrada:** qué existe antes de comenzar.
2. **Contenido transferible:** qué aprendizaje de clases puede aplicarse.
3. **Incremento:** qué se agrega/modifica, sin rehacer lo anterior.
4. **Evidencia:** cómo demostrar que el incremento existe y funciona o está correctamente diseñado.
5. **Estado de salida:** qué queda disponible para la siguiente semana.
6. **Deuda/siguiente paso:** qué no quedó cerrado.
7. **Trazabilidad:** referencia al DevLog y, cuando exista código, commits/archivos relevantes.

## Regla de acumulación

Un checkpoint no sustituye al anterior: lo **extiende**.

Ejemplo conceptual:

```text
Semana 1: arquitectura/API inicial
    ↓
Semana 2: + gateway + identidad conceptual
    ↓
Semana 3: + JWT + política de acceso
    ↓
Semana 4: recibe TODO lo anterior y agrega la siguiente capacidad
```

Si una capacidad anterior cambia, el estudiante debe registrar la razón y el impacto. La evolución puede refactorizar, pero no borrar silenciosamente decisiones previas.

## Evidencia incremental mínima

Cada semana debe permitir responder:

- ¿qué tenía RegistrApp antes?;
- ¿qué aprendí que habilita este cambio?;
- ¿qué cambió exactamente?;
- ¿cómo lo demuestro?;
- ¿qué quedó disponible para continuar?;
- ¿qué deuda arrastro?

## Progreso clase a clase

El checkpoint semanal es el contenedor histórico. Dentro de él pueden registrarse varios incrementos de clase:

```text
Clase 1 → incremento A
Clase 2 → incremento B
Clase 3 → incremento C
```

Todos convergen en el mismo estado de salida de la semana.

## Semanas sin incremento

Si una semana no permite avanzar por evaluación, feriado, deuda de prerrequisitos u otra causa, **la carpeta igualmente existe** y registra:

- estado de entrada;
- motivo de no incremento;
- evidencia de continuidad;
- siguiente punto de reanudación.

Así el histórico nunca tiene huecos y el progreso del semestre es auditable.
