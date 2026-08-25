# 01 · Crear y ejecutar CloudTasks local

## Objetivo

Construir **antes del cloud** el frontend y backend que las guías posteriores van a usar. Así CORS, OAuth2 y API Gateway nunca dependen de una aplicación inexistente.

## 1. Crear workspace

```bash
mkdir cloudtasks
cd cloudtasks
```

Estructura esperada:

```text
cloudtasks/
├── frontend/
└── backend/
```

## 2. Crear frontend Angular

```bash
ng new frontend --routing --style=css --skip-git
cd frontend
npm start
```

Abrir:

```text
http://localhost:4200
```

Debe verse la aplicación Angular antes de continuar.

### Interfaz mínima

La app final solo necesita:

- título `CloudTasks`;
- botón `Iniciar sesión` / `Cerrar sesión`;
- bloque `Mi identidad`;
- listado de tareas;
- formulario de nueva tarea;
- botón eliminar.

En esta etapa todavía no hay login real. Crear temporalmente una pantalla simple que muestre:

```text
CloudTasks
Frontend operativo
Backend: pendiente
Identidad: pendiente
```

## 3. Crear backend Spring Boot

Crear un proyecto Maven Spring Boot con Java 21 y dependencias iniciales:

- Spring Web;
- Spring Boot Actuator.

Puede generarse con Spring Initializr o el mecanismo provisto por el docente. El artifact sugerido es `cloudtasks-api`.

Puerto esperado:

```properties
server.port=8080
```

Crear un endpoint público:

```java
@RestController
@RequestMapping("/api/public")
class PublicController {

    @GetMapping("/health")
    Map<String, Object> health() {
        return Map.of(
            "status", "UP",
            "service", "cloudtasks-api"
        );
    }
}
```

Ejecutar:

```bash
mvn spring-boot:run
```

Validar:

```bash
curl http://localhost:8080/api/public/health
```

Respuesta esperada:

```json
{"status":"UP","service":"cloudtasks-api"}
```

## 4. Hacer la primera llamada frontend → backend

Crear en Angular una llamada temporal a:

```text
http://localhost:8080/api/public/health
```

En este punto puede aparecer CORS. **No ocultar el error**: es la primera evidencia real del problema que Semana 1 explica.

Si el navegador bloquea la request por CORS, abrir DevTools → Network y Console, registrar:

- origin del frontend: `http://localhost:4200`;
- URL del backend: `http://localhost:8080`;
- método: `GET`;
- mensaje exacto del navegador.

Luego agregar temporalmente CORS en Spring para permitir solo el frontend local:

```java
@Configuration
class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:4200")
            .allowedMethods("GET", "POST", "DELETE", "OPTIONS")
            .allowedHeaders("Authorization", "Content-Type");
    }
}
```

Reiniciar backend y comprobar que la llamada funciona.

> Esta configuración es **solo el puente local**. Más adelante CORS se moverá/ajustará en la frontera real expuesta por AWS API Gateway.

## 5. Gitignore mínimo

No versionar:

```text
frontend/node_modules/
backend/target/
ev1-local-values.txt
.env
.env.*
```

## Puerta de validación 01

No continuar hasta poder demostrar simultáneamente:

```text
http://localhost:4200            → frontend operativo
http://localhost:8080/api/public/health → backend operativo
frontend → backend               → request exitosa desde navegador
```

Si Postman funciona pero el navegador no, **no afirmar que la integración está resuelta**: probablemente sigue existiendo un problema CORS.

## Contenido relacionado

- [Semana 1 · CORS](../../semanas/semana-01/04-cors-api-gateway.md)
- [Diagnóstico CORS](../../semanas/semana-01/04-cors-api-gateway/03-diagnostico-cors.md)
