# Deprecación y retiro de versiones

Versionar bien incluye planificar cómo desaparecen versiones antiguas.

## Ciclo de vida típico

```text
publicar v2
  ↓
operar v1 + v2
  ↓
marcar v1 como deprecada
  ↓
comunicar fecha de retiro
  ↓
migrar consumidores
  ↓
retirar v1
```

## Qué debe comunicarse

- qué versión queda deprecada;
- desde cuándo;
- alternativa recomendada;
- diferencias relevantes;
- fecha objetivo de retiro;
- evidencia o guía de migración.

## Por qué importa

Una API puede técnicamente soportar dos versiones, pero si nadie sabe cuándo desaparece la antigua, el costo operacional crece indefinidamente.

## Idea clave

El versionamiento no termina al crear `/v2`. También incluye administrar la coexistencia y el retiro responsable de `/v1`.