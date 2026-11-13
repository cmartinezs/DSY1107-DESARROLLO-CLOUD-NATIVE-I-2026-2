# Render — Staging del laboratorio de avalancha (solo plan)

Este directorio propone un despliegue para el workspace **DSY**. No cambia el Docker Compose local ni los dos laboratorios en `master`. El Blueprint no debe aprobarse sin conocer el costo. Se publica en una rama de infraestructura separada.

## Infraestructura propuesta
- `dsy1107-kafka-lab-staging`: Apache Kafka 4.1.2, KRaft single-node, privado, 1 CPU / 2 GB y disco persistente de 1 GB.
- `dsy1107-compras-lab-staging`: Spring Boot API HTTP pública, 512 MB; recibe las solicitudes de JMeter, publica eventos en Kafka.
- `dsy1107-procesador-lab-staging`: worker Spring Boot 512 MB, grupo `procesador-s13`, procesa y publica `compras-validas`.

Todos van en Virginia, con nombres nuevos y despliegue automático deshabilitado. **No hay UI Kafka pública**: exponer Kafka UI sin autenticación en internet sería inseguro. Consultar logs y métricas de Render o usar la prueba local para observar consumer lag directamente.

## Cómo probar cuando esté desplegado
- Primero comprobar que Kafka y el worker aparecen como `live` en Render; luego la API debe responder `GET /api/compras/salud`.
- JMeter debe apuntar **solo** al host de staging `dsy1107-compras-lab-staging.onrender.com`, protocolo `https`, puerto `443`; utilizar la variante de plan [oleada-render-300s.jmx](./oleada-render-300s.jmx) que ya trae protocolo parametrizable.
- Ejecutar primero 1 solicitud, luego una corrida controlada con `-JloadScale=0.1` (aprox. una décima parte de la tasa objetivo). **No ejecutar la avalancha completa sobre un servicio público sin comprobar recursos, límites y autorización docente**.
- `201` indica recepción/publicación del evento, no que el consumidor terminó. Observar logs del worker para correlacionar avance de offsets; sin un monitor de lag remoto, la observabilidad del backlog queda incompleta.
- El modo `APP_CHAOS_ENABLED` inicia `false`, por lo tanto la prueba estudia capacidad, no fallas DLT. Puede activarse para contrastar comportamientos solo con una decisión explícita, registrando el cambio.

## Diferencias conocidas y riesgos
- El broker privado usa PLAINTEXT **solo en la red privada** del workspace; no expone su puerto en internet. Esta arquitectura es didáctica, no apta para tráfico sensible ni producción.
- Solo un broker, replicación 1. Disco persistente para no perder datos en un redeploy, pero sin alta disponibilidad.
- El plan gratuito de Render no permite alojar este broker privado; los tres servicios y el disco implican costo.
- Las instancias con 512 MB pueden necesitar más memoria para Spring Boot; la prueba real puede fallar por recursos.
- Las métricas y resultados son específicos de Render (latencias externas, región, recursos diferentes al PC). No comparar números absolutos con la ejecución Docker local como si fueran iguales.
- Si se aprueba, crear recursos con el Blueprint, comprobar despliegues y logs; al terminar desmontar el entorno para dejar de consumir recursos facturables.

**Estado:** archivos de infraestructura preparados. El primer `create_blueprint` únicamente planifica; `approve_blueprint` crea los servicios facturables y requiere autorización de costo.
