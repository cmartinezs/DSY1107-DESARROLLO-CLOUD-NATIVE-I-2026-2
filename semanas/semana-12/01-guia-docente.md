# Guía docente · Semana 12

## Intención didáctica

El foco es **configurar e integrar** una web React con dos microservicios Spring Boot y Kafka, no desarrollar los tres proyectos desde cero. Presentar el caso como tienda que emite compras y construye un tablero en tiempo real. Utilizar [el laboratorio B guiado con checkpoints](../../labs/kafka-compras-webapp/LAB-WEBAPP-PASO-A-PASO.md).

## Dos recorridos independientes

- **[Laboratorio A · Fundamentos Kafka](../../labs/kafka-fundamentos/):** terminal y broker, sin aplicación React. Apto para explorar comandos, topics, particiones y grupos.
- **[Laboratorio B · React + Spring Boot + Kafka](../../labs/kafka-compras-webapp/):** configuración de código base ya entregado, publicación REST y análisis de eventos reales.

Cada laboratorio usa **su propio broker y Compose**. No comparten mensajes ni offsets; sus puertos 9092 entrarían en conflicto si se ejecutan a la vez. Detener el primero antes de comenzar el segundo. La guía AVA queda por conciliar cuando se reciba.

## Preparación del laboratorio (antes de clase)

1. Confirmar que Docker Desktop esté operativo y en modo Linux containers en los computadores Windows 10 (WSL2/Hyper-V, de existir, corresponde a soporte TI).
2. Asegurar conectividad al registro Docker Hub, a Maven Central y a npm: la primera ejecución construye dos servicios Java y una web React **dentro de Docker**.
3. Si Internet o recursos del laboratorio no permiten compilar, **preparar con antelación las imágenes** en los equipos; no pedir instalaciones de herramientas a los estudiantes.
4. Verificar que estén libres los puertos 9092, 8081, 8082 y 5173, o ajustar la asignación de puertos con el grupo.

## Apertura · RabbitMQ vs Kafka

Ejemplo: una compra desencadena una tarea de correo, se refleja en indicadores y debe permitir recuperar el estado después de reiniciar. Comparar cola de trabajo y log de eventos retenidos sin afirmar que Kafka sustituye siempre a RabbitMQ.

## Clase guiada

- **Checkpoint 0–1:** comprobar Docker y crear el topic `compras` con 3 particiones.
- **Checkpoint 2:** completar `.env`. Preguntar por qué `localhost:9092` no sirve como bootstrap interno entre contenedores y por qué sí `kafka:9094`.
- **Checkpoint 3:** configurar Nginx para conectar React con las dos APIs a través del mismo origen. Distinguir HTTP de Kafka.
- **Checkpoint 4–5:** iniciar imágenes y probar REST; emitir 4 compras y pedir que identifiquen clave, partición, offset y los dos procesos separados.
- **Checkpoint 6:** abrir otro grupo independiente `auditoria-s12`, publicar una compra y ver que ambos grupos consumen el evento.
- **Checkpoint 7:** detener `resumen-api`, resetear offsets con grupo inactivo y reconstruir el tablero desde el log retenido.
- **Checkpoint 8:** distinguir el resumen Spring Kafka en memoria de un procesamiento Kafka Streams; diseñar topología y ventanas (no atribuir una implementación Java Streams al ejemplo).

## Alcance por sección

**002D:** priorizar hasta checkpoint 5; el docente puede demostrar grupos y replay si la duración no permite práctica completa.

**003D:** idealmente desarrollar 0–8. Ajustar según ritmo real, registrar checkpoint alcanzado, no simular ejecución por haber publicado la guía.

## Diagnóstico guiado

- Si web devuelve **502**: revisar nombres `compras-api` / `resumen-api` en el proxy.
- Si productor falla: comprobar `KAFKA_BROKER=kafka:9094`, topic `compras` y Kafka saludable.
- Si resumen no crece: revisar grupo y offsets mediante comandos Kafka; el consumidor puede haber registrado una compra anterior.
- Si falla por descarga Maven/npm: problema de entorno/caché, no del código que deba desarrollar el estudiante.

## Evaluación formativa

Evidencias: topic de 3 particiones, estado de 4 servicios, compra visible en web, partición/offset, dos grupos independientes, replay y topología conceptual con ventana.

**Advertencia docente:** el estado agregado es volátil y la deduplicación en memoria no reemplaza idempotencia duradera. Después de `docker compose down`, el broker de este ejemplo pierde los eventos porque no usa volumen persistente. No es un sistema de producción.
