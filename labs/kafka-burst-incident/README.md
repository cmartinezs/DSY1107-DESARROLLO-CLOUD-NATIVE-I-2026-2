# Operación Avalancha · Kafka bajo presión (300 segundos)

**Variante alternativa, deliberadamente sin solución aplicada.** Reutiliza los componentes y el generador HTTP/JMeter del [Game Day original](../kafka-resilience-game-day/), pero estudia un **cuello de botella bajo carga**, no excepciones inyectadas para practicar retries. La rama padre del Game Day y su PR #11 permanecen intactos.

## Comienza aquí

- [Guía del estudiante](./GUIA-ESTUDIANTE.md): investigación en equipos, hipótesis y comparación antes/después.
- [Guía docente](./GUIA-DOCENTE.md): explicación del cuello de botella y gates para el profesor. **No entregar antes del desafío**.
- [Plan JMeter: oleada de 300 segundos](./load/oleada-300s.jmx).
- [Compose adicional](./compose.incident.yaml): no reemplaza el Compose base.
- Scripts PowerShell en `scripts/`: iniciar, ejecutar JMeter, monitorear lag y detener.

## Primer paso: obtener esta variante

Está en **otra rama** (aún no fusionada), derivada de `lab/kafka-resilience-jmeter-game-day`.

```powershell
git clone --branch lab/kafka-burst-diagnosis-300s --single-branch https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2.git
cd DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2\labs\kafka-burst-incident
```

Si ya tienes el repositorio, desde su raíz:

```powershell
git fetch origin
git switch --track origin/lab/kafka-burst-diagnosis-300s
cd labs\kafka-burst-incident
```

Si la rama ya está creada localmente, usar `git switch lab/kafka-burst-diagnosis-300s`.

## Qué cambia respecto del Game Day anterior

| Aspecto | Game Day: fallos y DLT | Operación Avalancha: investigación |
| --- | --- | --- |
| Generador | JMeter, POST /api/compras | Mismo POST/payload, perfil progresivo de 5 minutos |
| Error inducido | Prefijos SIM-RETRY / SIM-DLT | **Ninguno**: `APP_CHAOS_ENABLED=false` |
| Causa del problema | Errores inyectados determinísticamente | Consumidor limitado por una dependencia externa lenta, inicialmente un solo worker |
| Manifestación | Reintentos y DLT visibles | Crecimiento de backlog / consumer lag durante el pico |
| ¿Se entrega la solución? | Sí, recuperación ya programada | **No**. Se requiere diagnóstico, propuesta, cambio y nueva medición |
| Observación | Kafka UI / logs | Kafka UI / consumer group lag + resultados de JMeter por intervalos |

No esperar un cierre del broker. El síntoma reproducible buscado es **degradación del procesamiento y recuperación lenta**. Una respuesta HTTP `201` significa que la API **publicó**, no que el consumidor terminó. El `resumen-api` es un **grupo independiente** y puede mantenerse al día aunque el procesador esté atrasado.

## Perfil de carga objetivo

| Intervalo | Duración | Usuarios concurrentes (máximo de fase) | Tasa objetivo |
| --- | ---: | ---: | ---: |
| 00:00–00:35 | 35 s | 1 | 0,5 req/s |
| 00:35–01:15 | 40 s | 2 | 0,75 req/s |
| 01:15–01:55 | 40 s | 3 | 1 req/s |
| **01:55–02:35** | **40 s** | **20** | **12 req/s** |
| 02:35–03:10 | 35 s | 10 | 5 req/s |
| 03:10–03:45 | 35 s | 6 | 3 req/s |
| 03:45–04:20 | 35 s | 3 | 1,5 req/s |
| 04:20–05:00 | 40 s | 2 | 0,5 req/s |

Se usa JMeter estándar 5.6.3 con **ocho Thread Groups temporizados**, no plugins. Un **Constant Throughput Timer por grupo** intenta regular solicitudes/minuto; la tasa efectivamente lograda puede ser menor si la API, el generador o Docker se saturan. <https://jmeter.apache.org/usermanual/component_reference>

El plan acepta `-JloadScale=0.5` para reducir **las tasas objetivo** a la mitad en equipos débiles; la duración sigue siendo 300 segundos, y no cambia el número máximo de usuarios configurados. La intensidad por defecto está limitada a 20 usuarios simultáneos en el pico. **Solo localhost o red Docker privada.**

## Preparación técnica: Windows 10 / Docker Desktop

No instalar Java, Maven ni Node localmente; las aplicaciones se compilan dentro de Docker. JMeter puede ejecutarse de dos maneras:

1. **Sin instalar nada más**: ejecutar `scripts\ejecutar-oleada.ps1`, que reutiliza el runner Docker Apache JMeter del laboratorio original.
2. **JMeter GUI instalado** (Java 17+): abrir `load\oleada-300s.jmx` y ejecutar allí; verificar `host=localhost` y `port=8081` por defecto.

No levantar el broker de `kafka-fundamentos` o el Game Day anterior simultáneamente: usan los mismos puertos y nombre de contenedor.

Desde **esta carpeta**:

```powershell
.\scripts\iniciar.ps1
```

Confirmar `http://localhost:8081/api/compras/salud` y Kafka UI `http://localhost:8083`. En Kafka UI deben existir `compras`, `compras-validas` y `compras-rechazadas`. El procesador es el grupo `procesador-s13`.

En **otra PowerShell** (mantenerla abierta durante el test):

```powershell
.\scripts\monitorear-lag.ps1 -RunId antes -Snapshots 35 -EverySeconds 10
```

En la PowerShell inicial:

```powershell
.\scripts\ejecutar-oleada.ps1 -RunId antes
```

Se generarán `results\oleada-antes.jtl` y `results\lag-antes.txt` (este último contiene snapshots del grupo). Para ver tasas HTTP por tramos:

```powershell
.\scripts\resumir-jmeter.ps1 -RunId antes
```

**Importante:** el monitor tarda un poco más que los cinco minutos del generador para capturar la recuperación posterior al pico. No ejecutar dos perfiles de JMeter a la vez.

## Dos corridas comparables

1. **ANTES**: no modificar parámetros ni código. Guardar JMeter, logs, snapshots y observación.
2. Diseñar una hipótesis. Explicar por qué ese cambio atacaría la causa y cuál es su costo/limitación.
3. **Reiniciar el entorno local** antes de medir **DESPUÉS** (ver guía). El broker de este laboratorio no tiene volumen persistente: al bajar el stack desaparecen los mensajes y offsets. Guardar primero evidencias.
4. Aplicar la modificación decidida por el equipo **al código o configuración en su copia**.
5. Levantar nuevamente, repetir los mismos 300 segundos con `-RunId despues` y comparar **tasa HTTP lograda, evolución del lag, tiempo de recuperación y eventuales errores**. No cambiar simultáneamente la forma de carga entre corridas.

## Límites y advertencias

- El entorno simula un consumidor acoplado a una dependencia de negocio lenta. No demuestra que Kafka se caiga por recibir muchos eventos.
- El broker es único, factor de replicación 1 y sin volumen persistente; no demuestra alta disponibilidad ni tolerancia a caída del broker.
- Una reducción de lag no garantiza consistencia ni exactly-once ante errores.
- No inyectar SIM-RETRY/SIM-DLT: esta versión estudia **capacidad**, no la DLT, que sigue disponible en el primer laboratorio.
- No ejecutar `docker compose down` antes de recoger evidencias. No superar el perfil predefinido sin autorización docente.

**Estado de QA:** JMX y código publicados; hace falta comprobar ejecución real con Docker/JMeter en equipo Windows antes de distribuir como laboratorio validado.
