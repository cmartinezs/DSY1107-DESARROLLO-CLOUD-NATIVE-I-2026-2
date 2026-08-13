# START HERE · Laboratorio API Gateway

El foco está en **configuración y conceptos**, no en programar Spring.

El starter utiliza **Spring Cloud Gateway Server Web MVC**. No se requiere programación reactiva ni conocimientos de WebFlux, Reactor, `Mono` o `Flux`.

## Ejecutar

```bash
cd gateway
mvn spring-boot:run
```

Gateway:

```text
http://localhost:8080
```

Prueba inicial:

```bash
curl -i http://localhost:8080/api/v1/posts/1
```

El starter solo trae `v1`. El grupo deberá construir `v2`, headers y habilitar CORS siguiendo la guía principal.

La configuración principal está en:

```text
gateway/src/main/resources/application.yml
```

No modifiquen `LabCorsConfiguration.java`; esa clase está preparada para que CORS se habilite desde `application.yml`.