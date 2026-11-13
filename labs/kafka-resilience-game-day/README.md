# Kafka Game Day · Resiliencia con JMeter — DSY1107

**Actividad integradora local:** Kafka, Spring Boot, Docker Compose y Apache JMeter. Los estudiantes investigan fallos transitorios, fallos permanentes, retry, DLT, offsets y consumo bajo carga.

- [Guía de estudiantes](./GUIA-ESTUDIANTE.md)
- [Guía docente, claves y contingencia](./GUIA-DOCENTE.md)
- [Plan JMeter listo para importar](./load/carga-compras.jmx)
- [Compose adicional](./compose.game-day.yaml)

## Contrato y origen

Este laboratorio **reutiliza** `../kafka-compras-webapp/compose.yaml`, su API de compras, Kafka, Kafka UI, React, resumen y el `procesador-api` de semana 13. **No se copia el proyecto ni se modifica su Compose canónico.** El único cambio de código sobre el procesador es una simulación opt-in de fallas, apagada por defecto.

| Entrada | Resultado esperado del procesador |
| --- | --- |
| `LAB-OK-*` | Produce en `compras-validas` |
| `SIM-RETRY-*` | Fallos en intentos 1 y 2; éxito en el tercero |
| `SIM-DLT-*` | Fallo en tres intentos; payload original a `compras-dlt`, con headers de origen |
| JSON malformado o regla de negocio inválida | Rechazo explícito a `compras-rechazadas` sin reintentar errores permanentes de contrato |

La API HTTP devuelve **201 al publicar el evento**, no al terminar su procesamiento. El `resumen-api` es **otro grupo de consumidores** y procesa eventos de `compras` independientemente del `procesador-api`; no usar el tablero React como prueba de éxito del procesamiento de resiliencia.

## Inicio rápido en Windows / PowerShell

**Prerequisitos:** Docker Desktop operativo, Docker Compose V2, conectividad a internet durante la primera compilación y terminal PowerShell. Dejar libres `8081`, `8082`, `8083`, `5173` y `9092`. Detener antes el laboratorio Kafka A: comparte los puertos.

Desde esta carpeta:

```powershell
.\scripts\iniciar.ps1
```

Alternativa sin script, desde `labs\kafka-compras-webapp`:

```powershell
docker compose --env-file ..\kafka-resilience-game-day\.env.game-day -f compose.yaml -f ..\kafka-resilience-game-day\compose.game-day.yaml --profile app --profile pipeline up -d --build
```

**Puntos de control:** `http://localhost:8081/api/compras/salud`, web `http://localhost:5173`, Kafka UI `http://localhost:8083`. En Kafka UI revisar `compras`, `compras-validas`, `compras-rechazadas` y `compras-dlt` (3 particiones cada uno). Si algo falla, NO ejecutar carga todavía: revisar `docker compose ... ps` y logs.

## JMeter: dos formas de ejecutar el mismo plan

**Opción A · GUI instalada:** descargar [Apache JMeter 5.6.3](https://jmeter.apache.org/download_jmeter.cgi), instalar Java 17 o superior y abrir `bin\jmeter.bat`. Importar `load\carga-compras.jmx`. El plan usa por defecto cinco usuarios, cinco repeticiones, ramp-up de 10 segundos y 250 ms de pausa por usuario. La carga objetivo es **la API local** en `localhost:8081`, no el broker. Para personalizar desde CLI usar `-Jthreads`, `-Jloops`, `-JrampSec`, `-JdelayMs` y `-JclientePrefix`.

**Opción B · solo Docker (sin instalar Java/JMeter en Windows):** en `labs\kafka-compras-webapp`, con el stack iniciado, ejecutar:

```powershell
docker compose --env-file ..\kafka-resilience-game-day\.env.game-day -f compose.yaml -f ..\kafka-resilience-game-day\compose.game-day.yaml --profile app --profile pipeline --profile load run --build --rm jmeter-runner -t /tests/carga-compras.jmx -Jhost=compras-api -Jport=8080 -Jthreads=5 -Jloops=5 -JclientePrefix=LAB-OK- -l /results/baseline.jtl
```

El reporte CSV queda en `results\baseline.jtl`. En cada corrida cambiar el nombre de salida (`normal.jtl`, `retry.jtl`, `dlt.jtl`) para no mezclar resultados. La compilación de este contenedor opcional descarga JMeter del archivo oficial Apache.

## Pruebas de error de contrato

Desde esta carpeta:

```powershell
.\scripts\inyectar-invalidos.ps1
```

Publica tres eventos directamente en Kafka: monto negativo, JSON corrupto y un evento válido posterior. **No usar el endpoint HTTP para esto:** `@Valid` rechazaría la entrada antes de Kafka.

## Observabilidad y límites

- `compras`: registro de entrada publicado. `compras-validas`: transformación exitosa.
- `compras-rechazadas`: contrato inválido. `compras-dlt`: error técnico agotado.
- La DLT conserva el **payload original** y los headers `x-original-topic`, `x-original-partition`, `x-original-offset` y `x-error-type`. Configuramos **retención de 24 horas**, `cleanup.policy=delete`. No es un mecanismo de reingesta automática: la recuperación/replay debe diseñarse y verificarse.
- `FixedBackOff(1000,2)` = tres intentos totales; cada mensaje permanentemente fallido puede retrasar la misma partición, generando **consumer lag**.
- Eventualmente pueden existir duplicados entre salida y commit de offset (no hay transacción end-to-end).
- El Kafka es **single broker**, sin volumen persistente. Nunca atribuirle alta disponibilidad. `docker compose down` elimina los datos locales del broker.
- La prueba queda estrictamente limitada a `localhost` / red Docker local. **No apuntar JMeter a servicios externos ni intentar tumbar infraestructura compartida**.
- No superar 20 usuarios concurrentes y 30 loops para el ejercicio inicial; aumentar solo bajo control docente, mirando el estado de Docker.

## Cierre

Capturar evidencias y DevLog **antes** de apagar. Desde esta carpeta:

```powershell
.\scripts\detener.ps1
```

**Estado de verificación:** fuentes, Compose, plan y tests están versionados; la ejecución end-to-end requiere validación real en una máquina con Docker Desktop y, para GUI, JMeter instalado. No confundir la disponibilidad del código con ejecución confirmada.
