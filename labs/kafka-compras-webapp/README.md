# Laboratorio B · Compras en tiempo real con React + Spring Boot + Kafka

> **ATENCIÓN — RAMA `clase-kafka-starter`:** este código es el **estado INICIAL** de la clase de integración. `compras-api` recibe compras por HTTP pero **NO publica en Kafka**; `resumen-api` expone su resumen y registro manual pero **NO tiene `@KafkaListener`**. Sigue la [guía específica para construir Kafka en vivo](./CLASE-EN-VIVO-INTEGRAR-KAFKA.md). La [guía del laboratorio B de configuración](./LAB-WEBAPP-PASO-A-PASO.md) está escrita para `master`, no para esta rama.

**Semana 12 · DSY1107.** Proyecto de integración **independiente** del laboratorio de consola. Aquí hay una web React, dos microservicios Spring Boot, un Kafka propio y **Kafka UI** para observar mensajes, particiones y offsets. **El alumno no programa los componentes desde cero:** recibe el código y configura la conexión entre servicios.

**RA/IL:** RA3 · 3.1.1–3.1.2, con puente a 3.2.1. **Modalidad:** local, guiada, configuración de código base. **Evidencia final:** [pruebas y cierre](./02-pruebas-evidencia-cierre.md).

## Modalidades disponibles

- **Laboratorio B de configuración:** el código ya tiene productor/consumidor Kafka; el alumno completa configuración y checkpoints ([guía original](./LAB-WEBAPP-PASO-A-PASO.md)).
- **Nueva clase de integración en vivo:** código base HTTP funcionando y el profesor incorpora **`KafkaTemplate` y `@KafkaListener`** ([guía de construcción en vivo](./CLASE-EN-VIVO-INTEGRAR-KAFKA.md); rama `clase-kafka-starter`). No son el mismo estado inicial; seleccionar el recorrido antes de descargar código.

## Comienza aquí

**[Abrir guía completa, con pasos y checkpoints 0–8](./LAB-WEBAPP-PASO-A-PASO.md)**

**Requisitos para estudiantes:** Windows 10, Docker Desktop abierto, PowerShell, navegador y editor de texto. No se instala Java, Maven, Node, Linux, Make ni se utiliza WSL como terminal; Docker compila el código en sus contenedores.

| Elemento | Qué hace | El estudiante |
|---|---|---|
| `webapp/web` | React: publicar una compra y mostrar el tablero | No lo programa; completa el proxy Nginx |
| `webapp/compras-api` | Spring Boot: produce eventos en Kafka | Configura broker y topic en `.env` |
| `webapp/resumen-api` | Spring Boot: consume eventos y agrega montos | Configura topic y consumer group |
| `kafka` | Broker único con tres particiones en `compras` | Crea el topic y verifica resultados |

## Configuración a completar

1. Copiar `.env.example` a `.env` y completar 4 variables usando la guía.
2. Completar los nombres faltantes de microservicios en `webapp/web/nginx/default.conf`.
3. Crear el topic `compras` y ejecutar los checkpoints.

**Para levantarlo (solo tras completar la guía):**

```powershell
cd labs\kafka-compras-webapp
docker compose --profile app up -d --build
```

Abrir la **web React en http://localhost:5173** y **Kafka UI en http://localhost:8083** después de verificar los contenedores.

## Relación con las guías AVA 3.2.1 y 3.2.2

- **AVA 3.2.1:** el topic, particiones, keys, offsets y consumer groups se visualizan con Kafka UI, además de la tabla del propio React.
- **AVA 3.2.2:** la web representa una entrada de eventos; `compras-api` publica, Kafka los retiene y `resumen-api` mantiene agregaciones. **No implementamos el Kafka Streams de la guía bancaria**, ni detección de fraude ni conversión USD; esos pasos se comparan como extensión **conceptual**, no se hacen pasar por funcionalidades existentes.
- **[Actividad guiada en Kafka UI y conciliación AVA](./01-observar-eventos-kafka-ui.md)**.

## Pruebas y cierre

Antes de detener los contenedores, realizar [02 · Gates por checkpoint, evidencias, pruebas negativas y troubleshooting](./02-pruebas-evidencia-cierre.md). Registrar por separado lo diseñado y lo ejecutado.

## Alcance y límites

La web consulta el resumen cada dos segundos; el servicio de resumen es un consumidor Spring Kafka con agregación **en memoria**, **no Kafka Streams**. Se estudia replay para reconstruir el resultado. No hay base de datos ni alta disponibilidad.

Este Kafka es **distinto** del [Laboratorio A · Fundamentos](../kafka-fundamentos/). No comparten mensajes ni consumer groups. Ambos exponen los puertos `9092` (Kafka) y `8083` (Kafka UI); **apaga un laboratorio antes de iniciar el otro**, desde su propia carpeta, con `docker compose down`.

**Importante:** este broker no tiene volumen persistente: al ejecutar `docker compose --profile app down` desaparecen los datos del contenedor Kafka. No lo detengas antes de terminar la demostración de replay.
