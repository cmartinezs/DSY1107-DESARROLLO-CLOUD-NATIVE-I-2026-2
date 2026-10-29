# Laboratorio local · Kafka, particiones y consumer groups

Laboratorio de la **Semana 12**, independiente de RegistrApp. Se utiliza **un solo contenedor de Apache Kafka en Docker Desktop**, sin instalaciones adicionales de Kafka o Java.

## Requisitos del laboratorio (Windows 10)

- Computador con **Windows 10**, **Docker Desktop instalado y abierto**, en modo **Linux containers**.
- **PowerShell o Símbolo del sistema (CMD)** para copiar y pegar los comandos.
- Acceso a Internet para descargar la imagen `apache/kafka:4.1.2` la primera vez.

**No se necesita instalar Make, Git Bash, una distribución Linux, Java ni abrir una terminal WSL.** Docker Desktop puede emplear WSL2 o Hyper-V internamente según la configuración del equipo; eso debe estar **habilitado previamente en el laboratorio**, no es una tarea para los estudiantes.

## Inicio rápido

1. Abrir Docker Desktop y comprobar que esté en funcionamiento.
2. Abrir **PowerShell** en la carpeta del repositorio.
3. Entrar al laboratorio y levantar Kafka:

```powershell
cd labs\kafka-streaming
docker compose up -d
docker compose ps
```

La primera ejecución descargará la imagen. Si aún no aparece Kafka listo, esperar a que inicie y volver a intentar el comando que haya fallado.

4. Comprobar que Kafka responde:

```powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-broker-api-versions.sh --bootstrap-server localhost:9092
```

5. Crear el topic de práctica con tres particiones:

```powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --if-not-exists --topic compras --partitions 3 --replication-factor 1
```

> Los nombres terminados en `.sh` corresponden a **herramientas que Kafka trae dentro del contenedor**. Se ejecutan desde PowerShell mediante `docker exec`; no requieren Bash ni Linux en el computador del estudiante.

## Itinerario

1. [Laboratorio 01: producir eventos y observar particiones y offsets](./01-primeros-eventos.md).
2. [Laboratorio 02: consumer groups y relectura de eventos](./02-consumer-groups.md).
3. [Laboratorio 03: diseñar ventanas de Kafka Streams](./03-streams-diseno.md) (solo diseño, sin aplicación Java ejecutable).

## Apagar y limpiar

Desde `labs\kafka-streaming`:

```powershell
docker compose down
```

Para **eliminar también los datos** del entorno de práctica, opcionalmente:

```powershell
docker compose down -v
```

## Si algo falla

- **`docker` no se reconoce:** comprobar instalación y apertura de Docker Desktop; pedir apoyo al docente o encargado de laboratorio.
- **No se conecta al motor:** abrir Docker Desktop y esperar a que termine de iniciar.
- **Kafka todavía no responde:** ejecutar `docker compose ps`, esperar unos instantes y repetir la comprobación.
- **Puerto 9092 ocupado:** cerrar otro Kafka o solicitar ayuda al docente; no cambiar puertos individualmente sin indicación.

## Alcance y seguridad

Broker único de demostración, `replication-factor=1`, sin alta disponibilidad. Puerto 9092 accesible **solo desde el equipo local**, sin autenticación. No usar esta configuración en producción ni publicar el puerto en Internet.
