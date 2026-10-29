# Laboratorio local · Kafka, particiones, consumer groups y aplicación real

Semana 12 de DSY1107. **Windows 10 + Docker Desktop + PowerShell** (CMD para comandos Docker simples). No se exige Linux, WSL como terminal, Make, Java, Node ni Maven instalados en Windows.

## ¿Cuál laboratorio realizo?

### Opción recomendada: webapp React + Spring Boot + Kafka

**[Ir al laboratorio guiado, con checkpoints y configuraciones pendientes](./LAB-WEBAPP-PASO-A-PASO.md)**

El código de la web React y **dos microservicios Spring Boot** ya está implementado en [`webapp/`](./webapp/). El estudiante **no programa desde cero**: completa `.env` y dos nombres de servicio en el proxy web, levanta los contenedores y comprueba el flujo HTTP → Kafka → consumidor → web. El broker y el topic son compartidos con las prácticas de abajo.

### Alternativa: prácticas de consola (solo Kafka)

1. [Laboratorio 01 · Topics, claves, particiones y offsets](./01-primeros-eventos.md).
2. [Laboratorio 02 · Consumer groups, distribución y replay](./02-consumer-groups.md).
3. [Laboratorio 03 · Diseño de Kafka Streams](./03-streams-diseno.md) (solo diseño).

Para iniciar **solo Kafka** en PowerShell, desde `labs\kafka-streaming`:

```powershell
docker compose up -d
docker compose ps
```

Para comprobarlo:

```powershell
docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-broker-api-versions.sh --bootstrap-server localhost:9092
```

Para iniciar **toda la webapp**, primero sigue el tutorial y completa la configuración; después:

```powershell
docker compose --profile app up -d --build
```

Para terminar, `docker compose --profile app down`. El contenedor Kafka de este laboratorio **no tiene volumen**: detenerlo con `docker compose down` elimina los eventos guardados en ese contenedor. El puerto Kafka 9092 solo se expone localmente, sin autenticación, y la configuración **no es adecuada para producción**.

**Nota sobre Docker Desktop:** requiere un motor de contenedores Linux operativo. Docker Desktop puede utilizar WSL2/Hyper-V internamente, pero configurar esa infraestructura corresponde a la administración del laboratorio, no al alumno.
