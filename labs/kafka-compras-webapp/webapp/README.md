# Código base · Compras en tiempo real (Semana 12)

**No desarrollar desde cero.** Estos tres proyectos ya tienen su lógica implementada. La tarea del estudiante está descrita en la [guía paso a paso](../LAB-WEBAPP-PASO-A-PASO.md), que pide completar solo dos configuraciones.

## Componentes

| Carpeta | Rol | Puerto interno |
|---|---|---|
| `web/` | React compilado con Vite y servido por Nginx; proxy de las dos APIs | 80 |
| `compras-api/` | Microservicio Spring Boot: recibe HTTP y **publica un evento** en Kafka | 8080 |
| `resumen-api/` | Microservicio Spring Boot: **consume eventos**, agrega totales por cliente en memoria y entrega JSON para el dashboard | 8080 |

**Kafka** se declara en `../compose.yaml` (un broker único, topic `compras` creado en la guía).

### Contrato del evento de compra

```json
{
  "id": "uuid-generado-por-la-api",
  "cliente": "Ana",
  "monto": 100,
  "fechaEvento": "2026-10-29T15:00:00Z"
}
```

La clave Kafka (`key`) es `cliente`. El consumidor también tolera eventos de consola sin `fechaEvento` **si se publican en el Kafka de esta misma aplicación**; utiliza el timestamp del registro como respaldo. Los dos laboratorios tienen brokers independientes y no comparten eventos.

### APIs disponibles (para probar desde PowerShell de Windows)

- `GET http://localhost:8081/api/compras/salud`
- `POST http://localhost:8081/api/compras` con JSON `{"cliente":"Ana","monto":100}`
- `GET http://localhost:8082/api/resumen/salud`
- `GET http://localhost:8082/api/resumen`

La web usa `http://localhost:5173` al completar la configuración sugerida. El navegador se comunica con el proxy web, que envía peticiones HTTP por la red Docker.

### ¿Qué está pendiente intencionalmente?

1. Completar los valores de `../.env` a partir de `../.env.example`: bootstrap interno de Kafka, nombre del topic, consumer group y puerto web.
2. Sustituir los nombres ficticios `__SERVICIO_COMPRAS__` / `__SERVICIO_RESUMEN__` de `web/nginx/default.conf` por los nombres de servicio de Docker Compose.
3. Crear el topic Kafka y levantar la aplicación siguiendo la guía con checkpoints.

**No modificar** controladores Java, componentes React ni Dockerfiles en este laboratorio.

### Qué es y qué no es

Es un ejemplo funcional de producción de eventos mediante Spring Kafka y procesamiento asíncrono entre microservicios, con actualización del dashboard cada dos segundos mediante polling.

**No** utiliza WebSocket, base de datos, Kafka Streams ejecutable, autenticación ni Kafka replicado. El estado del dashboard y su deduplicación son **volátiles**; usar replay para reconstruirlos es parte intencional del aprendizaje.
