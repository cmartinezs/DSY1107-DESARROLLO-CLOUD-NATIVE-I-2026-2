# Estructura canónica del repositorio docente · DSY1107

Este documento especializa el canon transversal para **DSY1107 Desarrollo Cloud Native I**.

## Principio rector

La semana organiza **cuándo** se aprende. Las raíces transversales organizan **qué tipo de recurso** es.

```text
docs/               base académica y conocimiento transversal
semanas/            contenido curricular y ejecución temporal
examples/           ejemplos demostrativos mínimos
ejercicios/         práctica breve y focalizada
labs/               práctica guiada reproducible
evaluaciones/       mapa y orientaciones de evaluaciones
proyecto-formativo/ transferencia incremental a RegistrApp
data/weekly/        estado agregado plan vs avance real
page/               vista web derivada
```

## 1. `docs/` — base estable y conocimiento transversal

Debe contener, como mínimo:

- `PDA-RESUMEN.md`;
- `RESULTADOS-DE-APRENDIZAJE.md`;
- `RUTA-DE-APRENDIZAJE.md`;
- `CRONOGRAMA.md`;
- documentación transversal por dominio, por ejemplo `identity/`.

La información estable no debe mezclarse con “semana actual”.

## 2. `semanas/` — aprender

Cada `semana-XX/` tiene un `README.md` que funciona como índice y síntesis. El README no reemplaza el contenido: cada materia relevante vive en uno o más `.md` separados.

Una semana debe permitir responder:

- qué corresponde aprender;
- qué RA/IL se trabaja;
- qué conceptos se explican;
- qué ejemplos corresponden;
- qué ejercicios corresponden;
- qué lab integra el aprendizaje;
- qué transferencia a RegistrApp es válida;
- qué evidencia demuestra comprensión.

## 3. `examples/` — demostrar

DSY1107 conserva el alias histórico `examples/`.

Los ejemplos:

- son breves;
- tienen un objetivo principal;
- no dependen de RegistrApp;
- pueden ser código o una demostración técnica reproducible;
- no sustituyen un laboratorio.

Un README que solo “apunta a otro lugar” no constituye por sí mismo un ejemplo completo.

## 4. `ejercicios/` — practicar sin guía completa

Se usa para:

- ejercicios de 5–20 minutos;
- decisiones de arquitectura acotadas;
- lectura de diagramas;
- diagnóstico de requests/responses;
- pequeñas modificaciones de código;
- práctica previa a un lab.

No contiene evaluaciones oficiales ni soluciones completas.

## 5. `labs/` — practicar guiado

Cada lab con identidad propia debe declarar:

- propósito;
- resultados esperados;
- prerrequisitos;
- arquitectura;
- pasos secuenciales en archivos separados;
- checkpoint por etapa;
- evidencia;
- troubleshooting;
- criterio de cierre.

Cuando un lab supera unas pocas etapas, un README único es insuficiente.

## 6. `evaluaciones/` — orientar y mapear

Consolida el mapa institucional de evaluaciones, su relación con RA/IL y las ventanas del cronograma.

No reemplaza los encargos oficiales de AVA/Drive ni publica soluciones.

## 7. `proyecto-formativo/` — integrar

RegistrApp es longitudinal.

Cada `semana-XX/` debe incluir:

- estado de entrada;
- capacidad transferible;
- decisión de diseño;
- implementación incremental;
- pruebas/evidencia;
- estado de salida;
- deuda.

Cuando el incremento tiene más de una decisión relevante, se divide en varios `.md` y el README actúa como índice.

## 8. `guias-integradas/`

El material rescatado de EV1 se conserva durante la conciliación. Su rol definitivo se evalúa por intención:

- conocimiento transversal → `docs/`;
- práctica guiada sustancial → `labs/`;
- ejemplo acotado → `examples/`;
- práctica breve → `ejercicios/`.

No se reconoce automáticamente como una quinta vertical permanente.

## 9. Criterio de completitud

Una carpeta está completa cuando sus archivos hacen realmente el trabajo que declara su nombre.

```text
README = índice + contexto
*.md / código = contenido efectivo
checkpoints = evidencia observable
índices raíz = navegación
```

La existencia de un README básico no basta para considerar una semana, lab, ejemplo o checkpoint terminado.
