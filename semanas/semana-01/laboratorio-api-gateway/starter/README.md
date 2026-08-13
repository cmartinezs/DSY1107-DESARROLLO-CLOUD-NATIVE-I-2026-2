# Starter · Spring Cloud Gateway

Este proyecto es el punto de partida del Laboratorio 1 de DSY1107.

No es necesario programar lógica Java. La actividad se concentra en comprender y configurar routes, filtros, versionado y CORS.

## Ejecutar

Requiere JDK 21+ y Maven.

```bash
cd gateway
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.config.name=application-lab"
```

Gateway:

```text
http://localhost:8080
```

Prueba inicial:

```bash
curl -i http://localhost:8080/api/v1/posts/1
curl -i http://localhost:8080/api/v2/posts/1
```

Consulta [`START-HERE.md`](START-HERE.md) y la guía ubicada un nivel arriba.