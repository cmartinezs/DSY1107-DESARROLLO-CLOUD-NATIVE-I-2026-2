# 02 · Pruebas, evidencias y cierre · Laboratorio B (Semana 12)

**Punto de entrada:** [guía paso a paso con checkpoints 0–8](./LAB-WEBAPP-PASO-A-PASO.md). La aplicación es código base entregado; el estudiante **configura e integra**, no programa React/Spring desde cero.

## Gate por checkpoint

| CP | Resultado observable |
|---|---|
| 0 | `docker --version` y `docker compose version` funcionan |
| 1 | Kafka saludable y topic `compras` con tres particiones |
| 2 | `docker compose --profile app config` sin `PENDIENTE` |
| 3 | Nginx sin placeholders `__SERVICIO_` |
| 4 | Cinco servicios disponibles; APIs responden salud y navegador abre web |
| 5 | Compra publicada y visible en tablero + Kafka UI con key/partition/offset |
| 6 | Otro grupo independiente observa eventos nuevos |
| 7 | Replay del grupo **inactivo** reconstruye resumen (sin borrar broker) |
| 8 | Topología con ventanas diseñada conceptualmente; **no** se declara Kafka Streams implementado |

## Prueba positiva

Publicar cuatro compras según CP5. Evidenciar los eventos, offsets y agregación; valores esperados únicamente al comenzar con topic vacío. Explicar que el tablero consulta REST y que la web no es consumidor Kafka.

## Pruebas negativas/diagnóstico

- **Solicitud HTTP inválida:** `Invoke-RestMethod` con cliente vacío o monto negativo; comprobar que la API rechaza entrada. Esto **no prueba** la recuperación del consumidor ante JSON inválido.
- **Servicio caído/502:** `docker compose --profile app ps` y `docker compose --profile app logs --tail=80 web compras-api resumen-api`; verificar configuración de nombres de servicio, puertos y broker.
- **Resumen vacío:** revisar offsets del grupo, topic y momento de publicación; no resetear ni borrar datos sin identificar la causa.
- **Puertos ocupados:** comprobar que laboratorio A está detenido. Los dos ambientes no comparten estado.
- **Descarga/compilación:** registrar error Docker/Maven/npm. No atribuir un build correcto sin ejecutar las imágenes.

## Entrega

Capturas de topic, cinco contenedores, web con compra, Kafka UI y grupo, replay cuando corresponda. DevLog: fecha, archivos configurados, comandos exactos, checkpoint alcanzado, incidencias, diagnóstico y decisión. No subir `.env` real.

## Apagado

Desde `labs\kafka-compras-webapp`:

```powershell
docker compose --profile app ps
docker compose --profile app down
```

**Pérdida de datos:** no hay volumen Kafka persistente; `down` elimina eventos del broker local. Guardar evidencia antes. No hacer limpieza global de imágenes/volúmenes del laboratorio institucional.

## Continuidad

La extensión de transformación pertenece al [laboratorio de semana 13](../kafka-pipeline-s13/) y no se atribuye a este laboratorio hasta que el software se implemente y pruebe.
