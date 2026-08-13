# START HERE · Laboratorio API Gateway

El foco del laboratorio está en la **configuración y los conceptos**, no en programar Spring.

## Ejecutar la configuración verificada

```bash
cd gateway
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.config.name=application-lab"
```

Gateway:

```text
http://localhost:8080
```

Pruebas iniciales:

```bash
curl -i http://localhost:8080/api/v1/posts/1
curl -i http://localhost:8080/api/v2/posts/1
```

Deben observar el header `X-Gateway-Lab: DSY1107` y el header `X-API-Version` correspondiente.

Luego continúen con la guía principal del laboratorio y documenten la evidencia en el repositorio de su grupo.