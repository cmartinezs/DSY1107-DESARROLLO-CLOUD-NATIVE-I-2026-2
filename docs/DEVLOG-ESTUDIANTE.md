# DevLog del estudiante · manual de uso

El **DevLog** es una bitácora técnica breve que vive en el repositorio personal del estudiante y registra cómo avanza su trabajo durante el semestre.

No es un diario personal ni una hoja de horas. Su objetivo es dejar una traza útil del proceso: qué querías lograr, qué hiciste, dónde te bloqueaste, qué aprendiste y qué harás después.

## Dónde vive

```text
docs/
└── devlog/
    ├── README.md
    ├── semana-02.md
    ├── semana-03.md
    └── ...
```

El repositorio del curso contiene **este manual y ejemplos**. Tu DevLog real se mantiene en tu repositorio personal.

## Formato mínimo

```markdown
# DevLog · Semana XX

## Objetivo
¿Qué quería avanzar esta semana?

## Avance
¿Qué hice realmente?

## Bloqueo
¿Qué me costó, falló o quedó sin resolver?

## Aprendizaje
¿Qué entendí, corregí o descubrí?

## Siguiente
¿Cuál es el próximo paso concreto?
```

## Ejemplo útil

```markdown
# DevLog · Semana 03

## Objetivo
Cerrar el flujo local de identidad y entender por qué una llamada termina en 401 o 403.

## Avance
- ejecuté el cliente, mock de identidad, gateway y API;
- probé llamada sin token y con scope insuficiente;
- documenté evidencia en el README del lab.

## Bloqueo
Confundí ID Token con Access Token al enviarlo como Bearer.

## Aprendizaje
Entendí que el ID Token describe la autenticación del usuario y que la API debe validar un Access Token destinado a ella.

## Siguiente
Mapear el mismo flujo al proveedor cloud real y registrar qué cambió y qué se mantuvo.
```

## No registrar horas

```text
mal: trabajamos dos horas
bien: aislamos el error de audience, corregimos configuración y dejamos una prueba reproducible
```

## Si no hubo avance

También es válido. No inventes actividad.

```markdown
## Avance
No logré levantar el laboratorio completo.

## Bloqueo
El gateway responde 502 y todavía no sé si el problema es routing o backend.

## Siguiente
Probar primero el backend directo y después repetir a través del gateway.
```

## Relación con Git y GitHub

```text
Issue      → qué quiero hacer
Commits    → qué cambié
DevLog     → qué ocurrió y qué aprendí
README     → cómo ejecutar y reproducir el resultado
```

En trabajo grupal, el repositorio de equipo documenta la solución compartida; el **DevLog personal** conserva tu propia evidencia de aprendizaje, decisiones, bloqueos y siguiente paso.

## Cómo se utilizará

El docente puede revisar DevLogs públicos para detectar bloqueos repetidos, problemas de infraestructura, errores conceptuales, decisiones técnicas, calidad de la colaboración y temas que conviene recuperar en la clase siguiente.

El objetivo es **feedback y evidencia formativa**, no vigilancia de horas.

## Reglas

- breve y específico;
- escrito con tus propias palabras;
- coherente con código, commits y evidencia;
- puede declarar que algo no se logró;
- no incluir access keys, tokens, secretos, URLs privadas o datos sensibles;
- no copiar logs completos: resume el síntoma y conserva solo evidencia útil;
- no convertirlo en una transcripción de la clase.

## Checklist semanal

- [ ] existe `docs/devlog/semana-XX.md`;
- [ ] tiene objetivo, avance, bloqueo, aprendizaje y siguiente;
- [ ] describe resultados reales;
- [ ] deja una pregunta pendiente si existe;
- [ ] es coherente con commits/código/README/evidencia;
- [ ] no expone secretos;
- [ ] está enviado a GitHub.
