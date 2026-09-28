# Etapa 0 · Prerrequisitos y línea base

## Objetivo

Comprobar que el entorno puede ejecutar RabbitMQ y una aplicación Spring Boot **antes** de agregar mensajería. Esta etapa evita diagnosticar simultáneamente Docker, Java y código AMQP.

## Requisitos

- Docker Desktop o Docker Engine operativo;
- Java 21 disponible;
- proyecto Spring Boot capaz de ejecutar;
- Maven Wrapper (`mvnw` / `mvnw.cmd`) recomendado;
- puertos `5672` y `15672` disponibles;
- acceso a `http://localhost:15672`.

## Verificaciones reproducibles

```bash
docker version
docker compose version
java -version
```

En Windows PowerShell puede comprobar puertos con:

```powershell
Get-NetTCPConnection -LocalPort 5672,15672 -ErrorAction SilentlyContinue
```

## Dependencia Spring

El proyecto utiliza el starter:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

## Configuración local mínima

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

> `guest/guest` se usa únicamente para el laboratorio local. No es una recomendación de credenciales para ambientes compartidos o productivos.

## Evidencia

Registrar versiones, sistema operativo y cualquier cambio de puerto necesario. No subir passwords reales, tokens ni secretos.

## Checkpoint 0

- [ ] Docker responde.
- [ ] Java 21 responde.
- [ ] el proyecto Spring inicia antes de integrar AMQP.
- [ ] 5672 y 15672 no presentan conflicto no explicado.

No continuar si la línea base ya está rota.
