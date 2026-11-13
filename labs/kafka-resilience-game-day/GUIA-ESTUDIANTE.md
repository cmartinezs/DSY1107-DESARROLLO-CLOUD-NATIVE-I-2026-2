# Laboratorio · Operación Black Friday: Kafka bajo presión

**DSY1107 · Desarrollo Cloud Native I** — Semana 13 / extensión formativa.  
**Modalidad:** parejas, Windows + PowerShell + Docker Desktop, JMeter GUI o contenedor.  
**Duración objetivo:** 100–120 minutos.  
**Objetivo:** demostrar qué ocurre con los mensajes cuando el consumidor falla y justificar una política de recuperación. **No se premia generar la mayor cantidad de peticiones.**

## 1. Contexto del incidente

La tienda recibe compras mediante HTTP. `compras-api` publica eventos en Kafka, `procesador-api` valida y transforma, y `resumen-api` construye un resumen independiente. Llegan solicitudes normales, algunas que fallan temporalmente y otras que nunca podrán procesarse.

Tu equipo es responsable de mantener la trazabilidad del evento. Debe justificar: ¿se reintenta?, ¿cuántas veces?, ¿a qué topic va?, ¿quién y cómo lo recupera?, ¿cuándo avanza el offset?

## 2. Arquitectura, antes de tocar nada

```text
JMeter --HTTP POST--> compras-api --Kafka--> compras (3 particiones)
                                           |
                                           +--> resumen-api (grupo distinto, tablero React)
                                           |
                                           +--> procesador-api (grupo procesador-s13)
                                                  |
                                                  +--> compras-validas (OK)
                                                  +--> compras-rechazadas (contrato invalido)
                                                  +--> [retry 2 x 1s] --> compras-dlt (falla tecnica agotada)
```

**Atención:** un HTTP `201 Created` significa **publicación en Kafka aceptada**, no que el procesador haya terminado. Una compra fallida en el procesador puede seguir apareciendo en el resumen porque son grupos independientes.

## 3. Checkpoint 0 · Preparar el entorno (15 min)

1. Abrir Docker Desktop y comprobar que está operativo.
2. Clonar el repositorio oficial desde `master` (si aún no tienes una copia) y situarse en `labs\kafka-resilience-game-day`:
   ```powershell
   git clone https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2.git
   cd DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2\labs\kafka-resilience-game-day
   ```
   Si ya tienes el repositorio, actualizar `master` con `git switch master` y `git pull --ff-only origin master`.
3. Comprobar que NO está encendido otro broker del laboratorio A en los mismos puertos.
4. Ejecutar en PowerShell:

```powershell
.\scripts\iniciar.ps1
```

5. Verificar en navegador:
   - `http://localhost:8081/api/compras/salud` responde con `estado: ok`.
   - `http://localhost:8083` abre Kafka UI.
   - En Kafka UI aparecen `compras`, `compras-validas`, `compras-rechazadas` y `compras-dlt`.
6. Verificar los seis servicios de aplicación/broker/UI y el inicializador de topics (puede estar en estado `Exited (0)` exitoso).

**Evidencia E0:** captura del estado Docker y la lista de topics. **No seguir si el procesador está caído.**

## 4. Checkpoint 1 · Preparar JMeter (15 min)

**GUI recomendada:** JMeter 5.6.3 requiere Java instalado en Windows. Descargar JMeter del sitio Apache, descomprimir y ejecutar `bin\jmeter.bat`. En *File → Open*, abrir `load\carga-compras.jmx`.

Si el equipo no tiene Java o permisos de instalación, usar la ejecución Docker documentada en [README](./README.md): el mismo `.jmx` corre sin Java local.

**Qué contiene el plan:** un grupo de usuarios, peticiones POST con JSON, cabecera `Content-Type: application/json`, una pausa configurable, una aserción de HTTP 201 y un reporte de resumen.

Parámetros del plan: en la GUI, seleccionar **Test Plan → Variables** y modificar los valores `USUARIOS`, `VUELTAS`, `RAMPA`, `PAUSA_MS`, `PREFIJO`, `PCT_RETRY` y `PCT_DLT`. Cada variable viene inicializada con una expresión `__P(...)` que acepta las opciones CLI `-J` (si usas la GUI, sustituye el valor por un literal como `SIM-RETRY-`, `2` o `10`).

| Parámetro | Valor inicial | Significado |
| --- | --- | --- |
| `threads` | 5 | Clientes concurrentes |
| `loops` | 5 | Compras por cliente |
| `rampSec` | 10 | Segundos para iniciar clientes |
| `delayMs` | 250 | Pausa por usuario y petición |
| `clientePrefix` | `LAB-OK-` | Selecciona escenario o `MIXTO` |
| `pctRetry` / `pctDlt` | 10 / 5 | Porcentajes aproximados en modo `MIXTO` |

**Predicción P1:** con la configuración inicial, ¿cuántas peticiones se intentarán? ¿Esperas el mismo número de eventos exitosos instantáneamente?

## 5. Checkpoint 2 · Carga normal (15 min)

Ejecutar el plan con `clientePrefix=LAB-OK-`. Desde Kafka UI observar `compras` y `compras-validas`; anotar al menos una combinación **key, partición y offset**. Revisar el *Summary Report* de JMeter: muestras, promedio, percentil si lo habilitas, throughput y porcentaje de error HTTP.

**Evidencia E1:** reporte de JMeter y un evento exitoso en Kafka UI.

**Pregunta Q1:** ¿Qué diferencia hay entre tasa de solicitudes HTTP y tasa de procesamiento de eventos Kafka?

## 6. Checkpoint 3 · Falla transitoria con retry (20 min)

Cambiar **PREFIJO** a `SIM-RETRY-`, manteniendo cinco usuarios o menos y bajando inicialmente a dos loops. El procesador simula dos fallos por evento, separados por reintentos de 1 segundo, y procesa el tercer intento. **El código de simulación está desactivado fuera de este Game Day.**

Para ver logs (desde `labs\kafka-compras-webapp`):

```powershell
docker compose --env-file ..\kafka-resilience-game-day\.env.game-day -f compose.yaml -f ..\kafka-resilience-game-day\compose.game-day.yaml --profile pipeline logs --tail=150 procesador-api
```

Buscar `SIMULACION FALLO TRANSITORIO` y `SIMULACION RECUPERADA`. Revisar lag del grupo `procesador-s13` en Kafka UI.

**Evidencia E2:** logs de los tres intentos de un evento y su salida en `compras-validas`.

**Pregunta Q2:** ¿cuándo es adecuado reintentar y qué costo tiene sobre el throughput? Si el fallo continuara, ¿cuándo dejarías de intentar?

## 7. Checkpoint 4 · Falla permanente y DLT (20 min)

Repetir una carga pequeña cambiando **PREFIJO** a `SIM-DLT-`, máximo **2 threads x 2 loops** para el primer ensayo. Cada evento fallará en sus tres intentos. El recuperador copiará el evento ORIGINAL a `compras-dlt`, con headers que indican de dónde vino.

En Kafka UI abrir `compras-dlt`, inspeccionar el payload y los headers `x-original-topic`, `x-original-partition`, `x-original-offset` y `x-error-type`. Observar si el grupo termina poniéndose al día. **No es reingesta ni reintento automático de la DLT.**

**Evidencia E3:** mensaje en DLT y sus headers, logs de reintentos y lag del grupo.

**Pregunta Q3:** ¿por qué no conviene reintentar para siempre? ¿Qué condiciones exigirías para volver a procesar un evento de la DLT?

## 8. Checkpoint 5 · Contrato inválido: NO es el mismo fallo (10 min)

Desde `labs\kafka-resilience-game-day` ejecutar:

```powershell
.\scripts\inyectar-invalidos.ps1
```

En Kafka UI localizar dos rechazos en `compras-rechazadas`: monto negativo y JSON malformado. También verificar una compra válida posterior en `compras-validas`.

**Evidencia E4:** dos rechazos distintos y continuidad del procesamiento. **Q4:** ¿por qué la prueba se hace directo en Kafka y no a través del formulario o HTTP?

## 8B. Desafío opcional · Mezcla de fallos y tasa de error (10–15 min)

Cambiar **PREFIJO = MIXTO**, **PCT_RETRY = 10** y **PCT_DLT = 5** en las variables de JMeter. Usar 10 usuarios x 20 loops = 200 solicitudes como máximo inicial del escenario mixto. Cada request selecciona su tipo aleatoriamente: probabilidad aproximada de 85 % normales, 10 % transitorios y 5 % permanentes. **Los porcentajes observados no tienen por qué coincidir exactamente en muestras pequeñas.**

En CLI, agregar `-JclientePrefix=MIXTO -JpctRetry=10 -JpctDlt=5`. Anotar solicitudes HTTP 201, mensajes de salida, DLT y lag **después de esperar a que el consumidor termine**. Proponer una métrica `fallos_permanentes / eventos_recibidos * 100` y compararla con `errores_HTTP / requests_HTTP * 100`. No mezclar ejecuciones previas sin separar offsets de inicio y fin.

**Pregunta Q5:** ¿por qué puede haber un 0 % de error HTTP y al mismo tiempo mensajes en DLT? ¿Qué señales operativas dispararían una alerta?

## 9. Cierre: política y decisión operativa (15 min)

Completar esta tabla con lo observado, sin inventar datos:

| Caso | Reintentos observados | Topic final | ¿Puede continuar el consumidor? | ¿Qué harías como operador? |
| --- | --- | --- | --- | --- |
| Compra válida | | | | |
| Falla transitoria | | | | |
| Falla permanente | | | | |
| JSON o monto inválido | | | | |

Responder en el DevLog:

1. Diferencia entre *reject*, *retry* y *dead-letter topic*.
2. Cómo calcularías **tasa de error HTTP** y **tasa de fallos de procesamiento**, y por qué no tienen el mismo denominador.
3. Qué sucede si tampoco está disponible `compras-dlt`. ¿Por qué nunca declararías el registro recuperado?
4. Una mejora razonada: backoff exponencial, alertas por lag, retención DLT, idempotencia o reingesta supervisada.

**Entrega:** `labs/kafka-resilience-game-day/DEVLOG.md` en el repo personal, con E0–E4, tabla, decisiones y limitaciones reales. Se evalúa **diagnóstico y evidencia**, no volumen de tráfico.

## Seguridad y recuperación del laboratorio

No enviar carga a sistemas compartidos o externos; mantener `localhost` y la red Docker interna. Si hay errores 503, elevar threads NO soluciona el problema: detener la carga y examinar logs/recursos primero. Si el entorno falla, capturar la situación y resolver desde el último checkpoint verde.

No ejecutar `docker compose down` ni el script `detener.ps1` antes de tomar evidencias: este broker de laboratorio **no tiene persistencia de datos**.
