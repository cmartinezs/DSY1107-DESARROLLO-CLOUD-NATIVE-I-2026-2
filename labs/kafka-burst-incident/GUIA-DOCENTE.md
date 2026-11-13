# Guía docente privada · Operación Avalancha

**Uso exclusivo del profesor.** La guía de estudiantes omite deliberadamente la causa y las soluciones. No entregarla antes de la defensa.

## Diferencia didáctica

El Game Day padre enseña `retry`, fallos técnicos y DLT con causas explícitas. Esta variante **no activa fallos**: `APP_CHAOS_ENABLED=false` y no habilita `compras-dlt`. El pipeline queda limitado intencionalmente por la latencia de una dependencia simulada, que obliga a observar **capacidad de consumo**.

El archivo `KafkaPipelineListener.java` de esta rama agrega una espera configurable mediante la variable `APP_INCIDENT_DEPENDENCY_LATENCY_MS` (inicialmente 450 ms) en el camino de procesamiento exitoso. Es la simulación de una API externa de validación lenta, **no un problema de Kafka**. Fuera de este Compose, el valor por defecto es 0 ms.

El `procesador-api` comienza con `SPRING_KAFKA_LISTENER_CONCURRENCY=1` y tres particiones en `compras`. Por eso, en régimen ideal y sin sobrecostos, el único worker se acerca a ~2,2 eventos/segundo. El pico intenta generar **12 requests/s durante 40 segundos**, por lo que debería acumularse lag. Con la tasa decreciente es posible que la cola tarde en bajar incluso tras terminar JMeter. No prometer cifras exactas sin medición, ni declarar caída real del broker.

## Preflight de docente (requerido antes de clase)

1. Desde `labs\kafka-burst-incident` ejecutar `scripts\iniciar.ps1`, confirmando build Maven y arranque de Kafka, Kafka UI, compras, resumen, procesador, web y topics.
2. En Kafka UI confirmar `compras`, `compras-validas`, `compras-rechazadas` (3 particiones), y consumidor grupo `procesador-s13`.
3. Realizar un POST aislado a `http://localhost:8081/api/compras` con JSON `{"cliente":"LAB-OK-Docente","monto":200}`; confirmar HTTP 201, topic de entrada y transformación de salida.
4. Ejecutar JMeter desde Docker con `scripts\ejecutar-oleada.ps1 -RunId validacion` en paralelo al monitor de lag y verificar que el perfil temporal dure aproximadamente 300 segundos.
5. Comprobar **que el lag suba durante el pico** y **que la tasa procesada sea inferior a la publicada**. Kafka UI y los snapshots mandan; si no se reproduce, **detener la evaluación** y calibrar la latencia/tasa del escenario.
6. Repetir el plan con una propuesta razonada, desde un entorno reiniciado, y demostrar una diferencia observada.
7. No fusionar ni declarar PASS sin evidencias de ejecución. Versionar resultados solo si no contienen datos personales.

## Hipótesis válidas para la discusión

- Paralelizar el consumidor según las particiones disponibles. Aumentar `SPRING_KAFKA_LISTENER_CONCURRENCY` puede mejorar throughput mientras existan particiones suficientes; **no crea particiones por sí solo**, ni es igual a aumentar consumer groups.
- Desacoplar llamadas lentas de la ruta del listener, si es seguro y se preserva control sobre el ACK, idempotencia y orden por clave.
- Separar colas y cargas, controlar ritmo de ingreso (*backpressure*), escalar procesamiento y planificar capacidad. No recomendar simplemente «más threads» sin medición.
- La carga de 12 eventos/s supera la capacidad de un worker con 450 ms por evento. Tres workers, uno por partición, mejorarían capacidad teórica, pero no necesariamente alcanzarán a absorber todo el pico. Cuidado con particiones, claves y orden.
- Cambiar artificialmente `DEPENDENCY_LATENCY_MS=0` solo **elimina la simulación del problema**: puede servir como experimento de control, pero no se considera solución arquitectónica defendible.
- Retry y DLT del Game Day padre son soluciones a errores de procesamiento, **no** a falta de capacidad. No confundir lag persistente con mensajes perdidos.
- JMeter informa códigos, latencia y throughput HTTP; Kafka UI muestra lag del consumidor. Deben correlacionarse; una respuesta HTTP 201 puede coexistir con lag alto.
- En `resumen-api` hay otro consumer group, por lo que el tablero React no garantiza que `procesador-api` esté al día.
- La sesión no prueba alta disponibilidad ni fallos de broker: Kafka usa un solo broker sin persistencia.

## Validación y pautas de aceptación

| Gate | Qué revisar |
| --- | --- |
| G0 | Arranque de todos los servicios, topic existente, HTTP 201 |
| G1 | JMeter plan con ocho intervalos no solapados, horizonte total 300 segundos |
| G2 | Ola subida suave → pico ~115–155s → descenso gradual |
| G3 | Evidencia correlacionada: tasas HTTP vs lag del grupo procesador |
| G4 | Hipótesis, evidencia de descarte de una causa incorrecta |
| G5 | Propuesta no trivial, riesgo/costo, prueba comparable antes/después |
| G6 | DevLog reproducible, no se confunden resultados observados con simulaciones |

## Contingencias de aula

- **El procesador no aparece:** inspeccionar `docker compose --project-name dsy1107-burst --env-file ..\kafka-burst-incident\.env.incident -f compose.yaml -f ..\kafka-burst-incident\compose.incident.yaml --profile pipeline logs --tail=150 procesador-api` desde `labs\kafka-compras-webapp`.
- **La avalancha no aparece:** observar reportes JMeter por tramos, comprobar `TestPlan.serialize_threadgroups=false`, las ocho fases y que el equipo no esté bloqueado con alto uso de recursos.
- **No crece el lag:** verificar grupo, que el consumidor usa una sola concurrencia, `APP_INCIDENT_DEPENDENCY_LATENCY_MS=450` y que la carga envía `LAB-OK-*`. Ajustar latencia únicamente para calibración docente, sin confundirlo con el arreglo estudiantil.
- **HTTP empieza a fallar:** analizar productores y recursos antes de culpar a Kafka; puede ser un segundo cuello de botella.
- **JMeter GUI no instalado:** el runner Docker ejecuta el mismo `.jmx` y deja CSV/JTL.
- **No hay tiempo para dos corridas:** exigir diagnóstico, hipótesis y plan de experimentación; no inventar evidencia DESPUÉS.

**Estado:** publicado en rama derivada. Sin Docker Desktop accesible desde la autoría, todos los gates de runtime quedan **NOT RUN** hasta ejecución real.
