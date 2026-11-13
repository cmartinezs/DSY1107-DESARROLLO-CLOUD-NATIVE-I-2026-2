# Guía docente · Operación Black Friday

**Propósito:** realizar un Game Day técnico en 2 bloques de DSY1107, con enseñanza basada en evidencia. Esta guía acompaña la [guía de estudiantes](./GUIA-ESTUDIANTE.md), no duplica los pasos.

## Preparación docente (preflight obligatorio)

1. En un equipo con Docker Desktop, ejecutar `scripts\iniciar.ps1` y **verificar** que se construyen las imágenes Java/Maven y suben los servicios. El repositorio no afirma validación de ejecución en sala hasta pasar este paso.
2. Abrir `http://localhost:8083` y observar cuatro topics, 3 particiones cada uno.
3. Realizar carga pequeña `LAB-OK-` (cinco usuarios, cinco loops). Comprobar HTTP 201 y `compras-validas`.
4. Realizar `SIM-RETRY-` (un usuario, un loop). Exigir dos logs de fallo, uno de recuperación y un resultado en `compras-validas`.
5. Realizar `SIM-DLT-` (un usuario, un loop). Exigir los tres intentos, payload original y cuatro headers en `compras-dlt`.
6. Ejecutar `scripts\inyectar-invalidos.ps1`: dos rechazos y un evento válido posterior.
7. Si un gate falla, no subir carga ni anunciar el lab como validado. Registrar logs y utilizar la discusión basada en diseño y capturas de muestra como contingencia.

## Claves conceptuales

- **Distinguir capas.** JMeter prueba HTTP (`201`, latencia, requests/s), no mide directamente procesamiento Kafka ni porcentaje de eventos en DLT.
- **No todo error necesita retry.** Eventos de negocio inválidos y JSON corrupto se envían a `compras-rechazadas` sin reintentos. Un fallo técnico transitorio puede recuperarse con retry.
- **Política existente.** `DefaultErrorHandler(FixedBackOff(1000,2))`: primer intento más dos reintentos, pausados un segundo entre intentos. El modo `SIM-RETRY-` triunfa en el tercero.
- **DLT didáctica.** La DLT preserva el payload y headers de origen, con retención local 24 h; no cuenta con automatización de reprocesamiento, auditoría operativa ni sistema de alertas. No declarar arquitectura de producción.
- **Un solo broker.** Kafka en Docker local no representa resiliencia del clúster ante caída de broker. La práctica prueba la **resiliencia del consumidor**.
- **Consumer groups separados.** El grupo de `resumen-api` no necesariamente comparte resultados del grupo `procesador-s13`. La web puede incluir compras que el procesador envió a DLT.
- **At-least-once y duplicados.** Una publicación exitosa de salida seguida de un fallo antes del commit del offset puede producir duplicados. No afirmar exactly-once; la semántica exacta exige diseño/controles adicionales.
- **Orden.** Kafka conserva orden dentro de una partición; los reintentos bloqueantes retrasan esa partición. Un retry topic puede cambiar el orden lógico, no es un cambio gratis.
- **Si DLT falla.** El recuperador lanza excepción, no declara recuperado el original, y el grupo puede mostrar lag sostenido hasta restablecer el destino. No hace falta eliminar el topic en una clase breve para entenderlo.
- **No sobreinterpretar los conteos.** Esperar a que baje el lag antes de comparar entrada/salida. JMeter reporta respuestas HTTP, no compensaciones ni transacciones externas.

## Escalado sugerido, sin romper computadores del aula

| Etapa | Usuarios | Loops | Escenario | Peticiones objetivo |
| --- | ---: | ---: | --- | ---: |
| Verificación | 1 | 1 | LAB-OK | 1 |
| Baseline | 5 | 5 | LAB-OK | 25 |
| Escenario transitorio | 2 | 2 | SIM-RETRY | 4 |
| Escenario DLT | 2 | 2 | SIM-DLT | 4 |
| Carga mixta (opcional) | 10 | 20 | MIXTO: 85 % OK, 10 % retry, 5 % DLT | 200 |

En MIXTO los porcentajes son probabilidades por solicitud (selección aleatoria en JMeter), no proporciones exactas garantizadas. Verificar `PCT_RETRY + PCT_DLT <= 100`.

Los números son **intentos configurados**, no resultados garantizados. Para 200 requests usa el reporte JMeter; no saturar deliberadamente una máquina del aula más allá de la capacidad local. Si el procesador recibe continuamente eventos DLT, dos segundos de retry por evento pueden aumentar lag: esa es la observación, no un fallo del laboratorio.

## Rúbrica formativa sugerida (20 puntos)

| Criterio | Puntaje |
| --- | ---: |
| Arranque, topics, evidencia de preflight E0 | 3 |
| Línea base: HTTP y eventos Kafka E1 | 3 |
| Reintentos observados, trazabilidad y explicación E2 | 4 |
| DLT, headers, offsets y lag E3 | 4 |
| Rechazos de contrato / continuidad E4 | 3 |
| Política razonada, cálculos de tasas, limitaciones, DevLog | 3 |

No penalizar por infraestructura institucional indisponible si el equipo documentó evidencia y diagnóstico; sustituir ejecución por razonamiento supervisado y dejar explícito el alcance.

## Contingencias típicas

| Síntoma | Acción |
| --- | --- |
| `PENDIENTE` en bootstrap/topic | Confirmar `--env-file ..\kafka-resilience-game-day\.env.game-day` |
| `topics-init` falla | Revisar salud de Kafka y sus logs, no comenzar prueba |
| HTTP 201 pero no hay salida en topic | Revisar `procesador-api`, su grupo, offsets y `APP_CHAOS_ENABLED` |
| No hay errores en JMeter durante escenario DLT | **Normal:** la API acepta el evento; el error ocurre después |
| No hay JMeter GUI | Ejecutar `jmeter-runner` con el mismo `.jmx` desde Docker |
| El broker o Compose no arrancan | Volver al gate anterior, usar análisis de política y logs disponibles |
| Datos desaparecen tras `down` | Es consecuencia de no tener volumen persistente: registrar limitación |

**Estado:** se incluyen código, pruebas unitarias, plan y scripts. La prueba real con Docker/JMeter **debe registrarse antes de usar la palabra PASS**; no hay evidencia de ejecución end-to-end desde el entorno de autoría.
