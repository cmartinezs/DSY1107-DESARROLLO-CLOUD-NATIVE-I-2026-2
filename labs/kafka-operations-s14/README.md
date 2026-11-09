# Laboratorio · Semana 14 · Kafka Operations

**RA/IL:** RA3 · IL3.3.  
**Actividades:** 3.3.1 escalamiento, replicación, retención y consumer lag; 3.3.2 tuning y pruebas de carga.  
**Tipo:** laboratorio local, guiado y reproducible.  
**Caso:** operación de la aplicación de compras construida en semanas 12 y 13.

## Propósito

Administrar topics y consumer groups, observar lag, generar una carga controlada y comparar parámetros del productor. El laboratorio reutiliza el código y el broker existentes: **no debes crear otra webapp ni reimplementar los microservicios**.

## Punto de partida

- [Laboratorio B · Compras en tiempo real](../kafka-compras-webapp/)
- [Extensión pipeline Semana 13](../kafka-pipeline-s13/)

El broker principal es **dsy1107-kafka-s12**, Apache Kafka 4.1.2 en KRaft, con Kafbat UI en http://localhost:8083. Se requiere que Docker Desktop esté iniciado.

## Archivos

- [LAB-PASO-A-PASO.md](./LAB-PASO-A-PASO.md): procedimiento completo con checkpoints.
- [scripts/crear-topics.ps1](./scripts/crear-topics.ps1): crea topics faltantes y aplica políticas de retención.
- [scripts/generar-compras.ps1](./scripts/generar-compras.ps1): publica un lote de compras válidas para observar lag.
- [compose.replication.yaml](./compose.replication.yaml): entorno opcional de tres brokers.
- [replicacion/README.md](./replicacion/README.md): demostración de RF=3, ISR y cambio de leader.

## Requisitos

- Windows 10.
- Docker Desktop en ejecución, modo Linux containers.
- PowerShell y navegador.
- Repositorio del curso actualizado.
- Puertos 9092, 8081, 8082, 8083 y 5173 disponibles para el laboratorio de compras.
- Conectividad a Docker Hub, Maven Central y npm para la primera compilación.

No instalar Java, Maven, Node, Kafka, Make ni Bash localmente. No abrir WSL para seguir este procedimiento.

## Secuencia

1. Iniciar el laboratorio de compras con profiles **app** y **pipeline**.
2. Crear/verificar los topics y sus configuraciones.
3. Inspeccionar consumer groups.
4. Distribuir consumidores en un mismo grupo.
5. Detener temporalmente **resumen-api**, generar eventos y observar lag.
6. Reiniciar el consumidor y comprobar recuperación.
7. Comparar una prueba de carga base y otra ajustada.

La extensión de tres brokers es opcional y solo se ejecuta si el equipo dispone de recursos suficientes.

## Evidencia de cierre

- Descripción de al menos dos topics con particiones y replication factor.
- Configuración efectiva de retención y cleanup policy de ambos topics.
- Dos consumer groups identificados y sus responsabilidades.
- Captura o salida CLI de consumer lag.
- Resultado de prueba base y ajustada, con condiciones de prueba y métricas.
- Operación administrativa reproducible (script o comandos).
- DevLog personal en **docs/devlog/semana-14.md**.

## Límites importantes

El broker obligatorio tiene una sola instancia: RF=1 es la única configuración replicable en ese entorno. No presentes ese escenario como tolerante a fallos. Para observar RF=3 usa la extensión opcional de tres brokers; al ejecutarla en un solo computador, solo simulas caída de un proceso/broker.

El laboratorio no garantiza que los valores ajustados rindan mejor en todos los equipos. Debes registrar resultados reales y explicar los compromisos. El script de este lab demuestra una operación administrativa reproducible; en tu proyecto semestral, documenta la configuración de topics en Compose o en el microservicio administrador, como solicita la actividad.

## Cierre

Guarda capturas y resultados antes de detener el entorno. El broker de compras no tiene volumen persistente; al ejecutar **docker compose down** sus eventos locales se pierden.
