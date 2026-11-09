# Evaluación Parcial 3 · Integración de Streaming y Analítica con Kafka

**Asignatura:** DSY1107 · Desarrollo Cloud Native I  
**RA:** RA3 · **Indicadores:** IL3.1, IL3.2 e IL3.3  
**Modalidad indicada en el PA:** presentación y entrega de encargo.  
**Ventana informada por coordinación:** semana 16 (23–28 de noviembre de 2026).

> **Alcance de este documento:** orientaciones de preparación y organización del trabajo. No reemplaza el encargo, la rúbrica, la ponderación ni las instrucciones oficiales publicadas en AVA/Drive. Si existe alguna diferencia, prevalece el material oficial de la asignatura.

## 1. Propósito

Integrar Kafka a la arquitectura distribuida del proyecto semestral para resolver una necesidad real de streaming y analítica. La solución debe demostrar publicación y consumo de eventos, uso correcto de topics y justificación técnica de su diseño.

Kafka debe **complementar** la arquitectura existente. No se espera reemplazar indiscriminadamente las colas RabbitMQ ni romper los flujos que ya funcionan.

## 2. Qué debe resolver el proyecto

El equipo debe identificar un caso de uso donde un flujo de eventos retenidos y consumidos por una o más responsabilidades aporte valor. Por ejemplo:

- generar indicadores a partir de eventos de negocio;
- permitir que analítica y auditoría lean los mismos eventos de forma independiente;
- procesar eventos continuamente;
- reconstruir una vista o resultado a partir de eventos retenidos.

No basta con agregar Kafka porque el encargo lo menciona. Debes explicar qué problema resuelve y por qué una llamada HTTP o una cola de trabajo no representa igual de bien esa necesidad.

## 3. Alcance técnico que debe poder demostrarse

### 3.1 Arquitectura e integración

Documenta mediante un diagrama:

- productor o productores;
- topics utilizados;
- claves de los eventos;
- particiones;
- consumidores y consumer groups;
- resultado analítico;
- relación con los componentes RabbitMQ existentes.

Explica qué flujo sigue usando RabbitMQ y qué flujo utiliza Kafka. No migres una integración previa sin justificarlo y comprobar que sigue funcionando.

### 3.2 Topics y configuración

El proyecto debe presentar al menos dos topics con configuración real y documentada.

Para cada topic registra:

| Campo | Qué debes indicar |
|---|---|
| Nombre | Nombre real del topic. |
| Propósito | Qué tipo de evento contiene y quién lo produce/consume. |
| Particiones | Número configurado y justificación. |
| Replication factor | Valor real y limitación del entorno, si corresponde. |
| Retención temporal | Valor de retention.ms y por qué se eligió. |
| Retención por tamaño | Valor de retention.bytes y alcance por partición. |
| Cleanup policy | delete o compact, con justificación. |

El proyecto debe documentar la configuración de ambos topics en su Compose o en el microservicio administrador. Un script/comando puede servir como operación administrativa reproducible, pero no reemplaza esa documentación declarativa. Una tabla escrita a mano sin correspondencia con la configuración efectiva no demuestra que el topic esté configurado.

**Replicación:** si el proyecto utiliza un broker local, RF=1 debe documentarse como una limitación, no como alta disponibilidad. Si declara tolerancia a fallos, debe mostrar réplicas reales en brokers distintos y evidencia acorde.

### 3.3 Productores y eventos

Demuestra al menos un productor que publique eventos reales del proyecto.

Documenta:

- estructura JSON o contrato del evento;
- key utilizada y motivo;
- topic de destino;
- evidencia de publicación;
- partición y offset observados.

No confundas una solicitud HTTP exitosa con evidencia suficiente de que el evento llegó y fue consumido.

### 3.4 Consumidores y consumer groups

Identifica al menos dos consumidores y sus responsabilidades. Documenta los consumer groups asociados.

Explica si:

- varios consumidores del mismo grupo distribuyen particiones para escalar una responsabilidad; o
- grupos independientes leen el mismo topic para cumplir responsabilidades distintas.

Incluye una evidencia de los grupos y de sus offsets.

### 3.5 Analítica y observabilidad

Demuestra el resultado del procesamiento o analítica: indicador, agregado, reporte, proyección o vista que tenga sentido para el caso de uso.

El consumer lag debe poder observarse en Kafbat UI o mediante CLI. Explica qué grupo se está observando, qué particiones tienen lag y qué significa el valor registrado.

### 3.6 Operación administrativa reproducible

Incluye al menos una operación que otra persona pueda repetir, por ejemplo:

- crear/configurar topics;
- consultar configuración;
- describir consumer groups y lag;
- ejecutar un procedimiento de administración desde script;
- utilizar un endpoint administrativo, si forma parte de la solución.

Documenta requisitos, comandos, parámetros y resultado esperado. No publiques secretos ni credenciales.

## 4. Entregables de preparación

Organiza el material de la evaluación en **evaluaciones/ep3/** del repositorio personal. No dupliques toda la aplicación: referencia la ubicación del código del proyecto y concentra aquí las instrucciones y evidencias.

Estructura sugerida:

~~~text
evaluaciones/
└── ep3/
    ├── README.md
    ├── docs/
    │   ├── arquitectura.md
    │   ├── topics-y-eventos.md
    │   └── decisiones-tecnicas.md
    └── evidencias/
        ├── topics/
        ├── consumer-groups/
        ├── lag/
        └── pruebas/
~~~

El README de EP3 debe indicar cómo levantar la solución, qué variables completar, cómo generar eventos, cómo verificar los topics/consumidores y cómo observar el resultado analítico.

## 5. Presentación y demostración

Prepara una demostración breve y ordenada:

1. Presenta el problema y por qué Kafka aporta valor.
2. Muestra el diagrama y explica la coexistencia con RabbitMQ.
3. Levanta la solución siguiendo el README.
4. Publica un evento de ejemplo.
5. Verifica el evento en el topic, identificando key, partición y offset.
6. Muestra los dos consumidores y sus consumer groups.
7. Explica el resultado analítico.
8. Muestra consumer lag y la operación administrativa reproducible.
9. Demuestra que el flujo RabbitMQ que ya existía continúa funcionando.

Cada integrante debe comprender la arquitectura, los contratos y las decisiones que presenta. La defensa individual de la Evaluación Final Transversal posterior no se reemplaza por una demostración grupal.

## 6. Evidencia de avance esperada al finalizar Semana 14

El correo de coordinación solicita que, como evidencia del aprendizaje de esta semana y en relación con el proyecto semestral, el equipo pueda presentar:

- dos topics configurados, con particiones, retención y replicación documentadas;
- consumer groups definidos para al menos dos consumidores;
- lag observable en Kafka UI o CLI;
- una operación administrativa reproducible mediante script, comando o endpoint.

Estos puntos son el checkpoint de avance de Semana 14. No constituyen por sí solos una rúbrica ponderada de la Evaluación Parcial 3.

## 7. Qué se completará en Semana 15

La actividad 3.3.3 aborda manejo de errores en arquitecturas de streaming. Por tanto, durante Semana 14 debes dejar claro el flujo y su operación; no declares implementadas políticas de errores, reintentos o DLQ que todavía no hayas construido y probado.

## 8. Checklist del equipo

- [ ] El caso de uso de Kafka está justificado.
- [ ] El diagrama muestra Kafka y RabbitMQ sin confundir sus responsabilidades.
- [ ] Existen al menos dos topics configurados.
- [ ] Particiones, RF, retención y cleanup policy están documentados y comprobados.
- [ ] Existe un productor funcional y un evento identificable.
- [ ] Existen al menos dos consumidores y sus grupos están identificados.
- [ ] Se observa consumer lag y se explica su interpretación.
- [ ] Existe un resultado de analítica verificable.
- [ ] Hay una operación administrativa reproducible.
- [ ] RabbitMQ mantiene su funcionamiento previo.
- [ ] README reproducible, evidencias y DevLog están actualizados.
- [ ] No hay secretos, tokens ni credenciales en el repositorio.

## 9. Criterios de calidad para auto-revisión

Antes de entregar, revisa:

- **Corrección técnica:** topics, claves, offsets, grupos y configuraciones corresponden a lo que realmente ejecuta el sistema.
- **Integración:** Kafka aporta una capacidad concreta y no rompe los flujos existentes.
- **Justificación:** particiones, retención, replicación y grupos responden a necesidades explicables.
- **Reproducibilidad:** otra persona puede levantar y probar la solución siguiendo instrucciones.
- **Dominio:** el equipo puede explicar el flujo completo y las limitaciones de su implementación.

Estos criterios orientan la auto-revisión y no asignan ponderaciones. La rúbrica oficial publicada en AVA es la referencia de evaluación.
