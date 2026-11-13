# Guía de estudiante · Incidente: la avalancha de las 02:00

**DSY1107 — Desarrollo Cloud Native I**  
**Modalidad:** equipos de dos, laboratorio local, investigación y defensa técnica.  
**Tiempo sugerido:** 100–120 minutos si se realizan dos corridas de cinco minutos.

## Misión

La tienda comienza el día con tráfico tranquilo. Kafka recibe compras desde una API HTTP y varios servicios consumen eventos. Pasado un minuto y medio llega una avalancha, luego disminuye progresivamente. El negocio informa que algunas compras **no aparecen a tiempo en el procesamiento posterior**, aunque el servicio HTTP responda correctamente.

**Tu trabajo NO es copiar una receta de reintentos.** Debes descubrir qué componente se atrasa, explicar por qué, proponer un cambio proporcionado y comparar evidencias de antes y después.

### Regla 0: no adivinar

Antes de modificar algo, escribir una hipótesis verificable. No asumir que un aumento de consumo, un HTTP `201` o una cifra del tablero React representan el mismo fenómeno. Tampoco asumir que Kafka se cayó sin probarlo.

## 1. Checkpoint A — ejecutar y observar la normalidad (15 min)

Desde `labs\kafka-burst-incident` seguir el [README](./README.md) y arrancar:

```powershell
.\scripts\iniciar.ps1
```

Verificar `http://localhost:8081/api/compras/salud` y Kafka UI `http://localhost:8083`.

Observar los topics `compras`, `compras-validas` y `compras-rechazadas`; localizar el grupo `procesador-s13`. Identificar los roles de `compras-api`, Kafka y `procesador-api`. Anotar topic, particiones y el primer offset observado; si ya hay eventos por otra corrida, **detenerse y avisar**.

**Evidencia A:** arquitectura dibujada por el equipo y captura del sistema saludable.

## 2. Checkpoint B — predecir el incidente (10 min)

Abrir `load\oleada-300s.jmx` en JMeter GUI o inspeccionarlo como XML. Tiene fases de tráfico creciente, un pico intenso de **40 segundos entre 1:55 y 2:35**, y caída gradual hasta completar cinco minutos.

**Antes de ejecutar, responder:** ¿qué servicio crees que sufrirá primero y qué métrica lo demostraría? Escribe dos indicadores que usarás para distinguir que Kafka recibe un mensaje de que un consumidor ya terminó de procesarlo.

## 3. Checkpoint C — registrar el incidente (15 min)

En una PowerShell ejecutar el monitor **antes** de iniciar JMeter:

```powershell
.\scripts\monitorear-lag.ps1 -RunId antes -Snapshots 35 -EverySeconds 10
```

En otra PowerShell ejecutar el plan completo:

```powershell
.\scripts\ejecutar-oleada.ps1 -RunId antes
```

En Kafka UI observar durante la corrida el grupo `procesador-s13` y el avance de `compras` y `compras-validas`. También puedes consultar logs:

```powershell
docker logs --tail 100 dsy1107-kafka-s12
```

El comando anterior muestra **el broker**, no el procesador. Para consultar los logs del procesador, usa el servicio `procesador-api` con el Compose de esta variante o inspecciona el grupo `procesador-s13` en Kafka UI.

Cuando termine JMeter (aproximadamente cinco minutos), obtener el reporte:

```powershell
.\scripts\resumir-jmeter.ps1 -RunId antes
```

**Evidencia B:** `oleada-antes.jtl`, `lag-antes.txt`, screenshot del pico y curva real de solicitudes por intervalos. Distinguir valores **objetivo** del generador de valores **observados**.

## 4. Checkpoint D — diagnóstico forense (20 min)

En equipo, responder con evidencia y sin modificar todavía:

1. ¿Qué pasó primero: errores HTTP, crecimiento del lag o caída del broker?
2. ¿Cuándo empezó a crecer la diferencia entre eventos publicados y procesados?
3. ¿Sigue respondiendo el endpoint HTTP cuando la cola de eventos pendientes crece? ¿Por qué?
4. ¿Se perdieron mensajes, quedaron pendientes o se rechazaron? **No afirmar pérdida de datos sin comprobarla.**
5. ¿Qué componente y qué configuración o método puede explicar ese cuello de botella?
6. ¿Bastaría aumentar la memoria o agregar `retry`? ¿Qué experimentarías antes de recomendarlo?

Entregar una **hipótesis principal** y al menos **una alternativa descartada** con explicación.

## 5. Checkpoint E — proponer y probar una mejora (25 min)

Tu equipo decide una mejora técnica (código, paralelismo, recursos, arquitectura o política de procesamiento) y responde:

- ¿Qué cambio exacto ejecutarías y qué indicador debería mejorar?
- ¿Qué nueva limitación, riesgo de orden, duplicación o consistencia introduce?
- ¿Cómo probarías que no se trata solo de aumentar el poder del computador?

**Importante:** guardar todas las evidencias del primer intento. Detener y volver a levantar el stack local para que **DESPUÉS** comience sin backlog anterior:

```powershell
.\scripts\detener.ps1
# Aplicar el cambio razonado al código o configuración de tu copia.
.\scripts\iniciar.ps1
```

Luego repetir:

```powershell
.\scripts\monitorear-lag.ps1 -RunId despues -Snapshots 35 -EverySeconds 10
```

En otra PowerShell:

```powershell
.\scripts\ejecutar-oleada.ps1 -RunId despues
.\scripts\resumir-jmeter.ps1 -RunId despues
```

Guardar nombre, instante y valores usados de cada experimento. Si cambias la carga, **ya no tendrás una comparación equivalente**.

## 6. Entrega y defensa (10–15 min)

Crear `labs/kafka-burst-incident/DEVLOG.md` en tu repositorio personal. Incluir:

| Pregunta / indicador | ANTES | DESPUÉS |
| --- | --- | --- |
| Peticiones HTTP enviadas / exitosas | | |
| Errores HTTP y latencia | | |
| Lag máximo observado del grupo `procesador-s13` | | |
| Lag al terminar JMeter | | |
| ¿Se recupera el procesamiento luego del pico? | | |
| Cambio técnico aplicado | Original | |
| Evidencia o limitación | | |

Añadir diagrama **antes/después**, hipótesis, decisión, resultado, contrapartidas y evidencia de comandos reales. No manipular resultados para que coincidan con la hipótesis.

**Pregunta final:** si la aplicación recibe una avalancha todavía mayor, ¿qué parte de tu solución fallaría primero y qué necesitarías medir para anticiparlo?

## Seguridad

Se permite probar únicamente el stack de Docker del propio equipo (`localhost` o red interna). No usar servicios institucionales, de compañeros ni aplicaciones públicas como destino de JMeter. No detener ni borrar topics durante el incidente. No cambiar la carga antes de tener una línea base comparable.
