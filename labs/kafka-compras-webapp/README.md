# Laboratorio B · Compras en tiempo real con React + Spring Boot + Kafka

**Semana 12 · DSY1107.** Proyecto de integración **independiente** del laboratorio de consola. Aquí hay una web React, dos microservicios Spring Boot y un Kafka propio. **El alumno no programa los componentes desde cero:** recibe el código y configura la conexión entre servicios.

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

Abrir `http://localhost:5173` después de verificar los contenedores.

## Alcance y límites

La web consulta el resumen cada dos segundos; el servicio de resumen es un consumidor Spring Kafka con agregación **en memoria**, **no Kafka Streams**. Se estudia replay para reconstruir el resultado. No hay base de datos ni alta disponibilidad.

Este Kafka es **distinto** del [Laboratorio A · Fundamentos](../kafka-fundamentos/). No comparten mensajes ni consumer groups. Ambos exponen el puerto `9092`; **apaga un laboratorio antes de iniciar el otro**, desde su propia carpeta, con `docker compose down`.

**Importante:** este broker no tiene volumen persistente: al ejecutar `docker compose --profile app down` desaparecen los datos del contenedor Kafka. No lo detengas antes de terminar la demostración de replay.
