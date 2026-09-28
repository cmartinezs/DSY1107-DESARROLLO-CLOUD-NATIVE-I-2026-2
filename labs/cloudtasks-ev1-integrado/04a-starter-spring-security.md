# 04A · Starter reproducible Spring Security Resource Server

## Objetivo

Configurar un Resource Server mínimo y completamente trazable. El estudiante adapta el package y dos valores (`OIDC_ISSUER`, `API_AUDIENCE`); Spring Security realiza la validación criptográfica JWT.

No se implementa un parser JWT, RSA, JWKS ni login propio.

## 0. Dependencias

Agregar a `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

Validar inmediatamente:

PowerShell:

```powershell
.\mvnw.cmd test
```

Git Bash/Linux/macOS:

```bash
./mvnw test
```

No continuar si Maven no puede resolver dependencias.

---

# 1. Configuración externa

En `src/main/resources/application.properties` agregar:

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=${OIDC_ISSUER}
cloudtasks.security.audience=${API_AUDIENCE}
```

Los valores se entregan en runtime; no se duplican dentro de clases Java.

PowerShell, sesión actual:

```powershell
$env:OIDC_ISSUER="<OIDC_ISSUER>"
$env:API_AUDIENCE="<API_AUDIENCE>"
```

Git Bash/Linux/macOS:

```bash
export OIDC_ISSUER='<OIDC_ISSUER>'
export API_AUDIENCE='<API_AUDIENCE>'
```

`API_AUDIENCE` debe coincidir con el claim `aud` observado en un **Access Token real** para CloudTasks API.

---

# 2. Package de referencia

Los ejemplos usan:

```text
cl.duoc.cmartinez.cloudtasks
```

El estudiante debe reemplazar solo `cmartinez` por su usuario Duoc sin puntos.

Estructura:

```text
src/main/java/cl/duoc/<usuario>/cloudtasks/
├── config/
│   ├── AudienceValidator.java
│   └── SecurityConfig.java
├── controller/
│   ├── MeController.java
│   └── TaskController.java
├── model/
│   ├── CreateTaskRequest.java
│   ├── MeResponse.java
│   └── Task.java
└── service/
    └── TaskService.java
```

Conservar el `PublicController` creado en 01A.

---

# 3. `AudienceValidator.java`

```java
package cl.duoc.cmartinez.cloudtasks.config;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final String requiredAudience;

    public AudienceValidator(String requiredAudience) {
        this.requiredAudience = requiredAudience;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (jwt.getAudience().contains(requiredAudience)) {
            return OAuth2TokenValidatorResult.success();
        }

        OAuth2Error error = new OAuth2Error(
                "invalid_token",
                "The required audience is missing",
                null
        );

        return OAuth2TokenValidatorResult.failure(error);
    }
}
```

Este validator comprueba una sola decisión de CloudTasks:

```text
¿este token fue emitido para MI API?
```

---

# 4. `SecurityConfig.java`

```java
package cl.duoc.cmartinez.cloudtasks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${cloudtasks.security.audience}")
    private String audience;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/tasks").hasAuthority("SCOPE_tasks.read")
                        .requestMatchers(HttpMethod.POST, "/api/tasks").hasAuthority("SCOPE_tasks.write")
                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/**").hasAuthority("SCOPE_tasks.write")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder decoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);

        OAuth2TokenValidator<Jwt> issuerValidator =
                JwtValidators.createDefaultWithIssuer(issuerUri);

        OAuth2TokenValidator<Jwt> audienceValidator =
                new AudienceValidator(audience);

        decoder.setJwtValidator(
                new DelegatingOAuth2TokenValidator<>(
                        issuerValidator,
                        audienceValidator
                )
        );

        return decoder;
    }
}
```

### Qué valida Spring aquí

```text
firma / JWKS
exp / nbf y validaciones estándar
issuer
+
audience requerida por CloudTasks
```

Spring Resource Server obtiene las claves públicas a partir del issuer/discovery; no se copian claves al proyecto.

### Por qué `csrf` se deshabilita aquí

CloudTasks API usa autenticación Bearer stateless y no una sesión web basada en cookie. No reutilizar esta configuración sin entender el contexto en una aplicación tradicional con formularios/cookies.

---

# 5. Scopes

Con el mapping estándar de Spring Resource Server, scopes de un Access Token suelen transformarse en authorities con prefijo:

```text
tasks.read  → SCOPE_tasks.read
tasks.write → SCOPE_tasks.write
```

Primero probar el comportamiento real del token emitido. No introducir un converter custom mientras el mapping estándar funcione.

---

# 6. `Task.java`

```java
package cl.duoc.cmartinez.cloudtasks.model;

public record Task(
        Long id,
        String title,
        String ownerId
) {
}
```

# 7. `CreateTaskRequest.java`

```java
package cl.duoc.cmartinez.cloudtasks.model;

public record CreateTaskRequest(String title) {
}
```

# 8. `MeResponse.java`

```java
package cl.duoc.cmartinez.cloudtasks.model;

import java.util.List;

public record MeResponse(
        String subject,
        String issuer,
        List<String> audience,
        List<String> scopes,
        List<String> roles
) {
}
```

---

# 9. `TaskService.java`

```java
package cl.duoc.cmartinez.cloudtasks.service;

import cl.duoc.cmartinez.cloudtasks.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final ConcurrentHashMap<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public List<Task> findByOwner(String ownerId) {
        return tasks.values().stream()
                .filter(task -> task.ownerId().equals(ownerId))
                .sorted(Comparator.comparing(Task::id))
                .toList();
    }

    public Task create(String title, String ownerId) {
        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title is required");
        }

        long id = sequence.incrementAndGet();
        Task task = new Task(id, title.trim(), ownerId);
        tasks.put(id, task);
        return task;
    }

    public void delete(Long id, String requesterId) {
        Task task = tasks.get(id);

        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found");
        }

        if (!task.ownerId().equals(requesterId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "task belongs to another user");
        }

        tasks.remove(id);
    }
}
```

La memoria se pierde al reiniciar. Eso es intencional: persistencia no es el aprendizaje de esta práctica.

---

# 10. `TaskController.java`

```java
package cl.duoc.cmartinez.cloudtasks.controller;

import cl.duoc.cmartinez.cloudtasks.model.CreateTaskRequest;
import cl.duoc.cmartinez.cloudtasks.model.Task;
import cl.duoc.cmartinez.cloudtasks.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> list(@AuthenticationPrincipal Jwt jwt) {
        return taskService.findByOwner(jwt.getSubject());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody CreateTaskRequest request
    ) {
        return taskService.create(request.title(), jwt.getSubject());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id
    ) {
        taskService.delete(id, jwt.getSubject());
    }
}
```

El `ownerId` **no llega desde Angular**. Se obtiene de `jwt.getSubject()`. Así un cliente no puede declarar arbitrariamente que una tarea pertenece a otro sujeto.

---

# 11. `MeController.java`

```java
package cl.duoc.cmartinez.cloudtasks.controller;

import cl.duoc.cmartinez.cloudtasks.model.MeResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/me")
public class MeController {

    @GetMapping
    public MeResponse me(@AuthenticationPrincipal Jwt jwt) {
        String scopeClaim = jwt.getClaimAsString("scp");

        List<String> scopes = scopeClaim == null || scopeClaim.isBlank()
                ? List.of()
                : Arrays.asList(scopeClaim.split("\\s+"));

        List<String> roles = jwt.getClaimAsStringList("roles");
        if (roles == null) {
            roles = List.of();
        }

        return new MeResponse(
                jwt.getSubject(),
                jwt.getIssuer().toString(),
                jwt.getAudience(),
                scopes,
                roles
        );
    }
}
```

No devolver el JWT completo.

---

# 12. Roles: extensión opcional, no bloquear la ruta base

El mapping estándar de scopes **no implica** que un claim `roles` vaya a convertirse automáticamente en `ROLE_Admin`.

Por eso el starter base no protege una ruta Admin con `hasRole("Admin")` hasta que se haya observado el token y configurado conscientemente un converter de roles.

La ruta principal queda centrada en:

```text
JWT válido
+ scope
+ ownership
```

Luego puede estudiarse `roles` como profundización separada sin romper el camino mínimo.

---

# 13. Compilar antes de ejecutar

PowerShell:

```powershell
.\mvnw.cmd clean test
```

Git Bash/Linux/macOS:

```bash
./mvnw clean test
```

**CHECKPOINT 04A-0**

- [ ] compilación Java = PASS.
- [ ] tests generados por proyecto = PASS.
- [ ] ningún package sigue usando `cmartinez` si ese no es el usuario real.
- [ ] `OIDC_ISSUER` y `API_AUDIENCE` están definidos en la sesión.

---

# 14. Ejecutar

PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Git Bash/Linux/macOS:

```bash
./mvnw spring-boot:run
```

Si el issuer no es alcanzable o es incorrecto, Spring puede fallar al inicializar/validar JWT. Corregir el issuer; no agregar una librería JWT diferente.

---

# 15. Pruebas por capas

## CHECKPOINT 04A-1 · seguridad básica

```bash
curl -i http://localhost:8080/api/public/health
```

Esperado:

```text
200
```

```bash
curl -i http://localhost:8080/api/tasks
```

Esperado:

```text
401
```

## CHECKPOINT 04A-2 · JWT real

Con Access Token real:

```bash
curl -i \
  -H "Authorization: Bearer <ACCESS_TOKEN>" \
  http://localhost:8080/api/me
```

Esperado con token correcto:

```text
200
+ claims sanitizados
```

Casos negativos:

| Caso | Esperado |
|---|---|
| token alterado | 401 |
| token expirado | 401 |
| issuer incorrecto | 401 |
| audience incorrecta | 401 |
| Access Token correcto | autenticación válida |

## CHECKPOINT 04A-3 · scopes

```text
GET /api/tasks sin tasks.read       → 403/rechazo
GET /api/tasks con tasks.read       → 200
POST /api/tasks sin tasks.write     → 403/rechazo
POST /api/tasks con tasks.write     → 201
```

## CHECKPOINT 04A-4 · ownership

1. usuario A crea una tarea;
2. usuario B intenta borrar el ID de A;
3. aunque B tenga `tasks.write`, el backend compara `sub` con `ownerId`.

Esperado:

```text
403
```

Esto diferencia:

```text
scope → ¿puede ejecutar esta clase de operación?
ownership → ¿puede ejecutarla sobre ESTE recurso?
```

---

# 16. SI FALLA

| Síntoma | Revisar primero |
|---|---|
| backend no arranca | variables + issuer discovery + logs |
| health devuelve 401 | orden/ruta `permitAll` |
| todo token devuelve 401 | `iss`, `aud`, firma/JWKS, `exp` |
| `/api/me` funciona, GET tasks 403 | authority `SCOPE_tasks.read` |
| GET funciona, POST 403 | `SCOPE_tasks.write` |
| DELETE 403 con write scope | ownership |
| navegador CORS | CORS/Origin; no AudienceValidator |
| curl falla CORS | no es CORS: curl no aplica Same-Origin Policy |

## Puerta de validación 04A

```text
build PASS
health público 200 PASS
protegida sin token 401 PASS
Access Token correcto PASS
audience incorrecta 401 PASS
scope read PASS
scope write PASS
ownership PASS
```

## Qué debe explicar

```text
Entra firma/emite
Spring Resource Server valida
JWKS entrega claves públicas
iss identifica emisor
aud identifica destinatario
scope habilita clase de operación
sub identifica sujeto
ownership protege recurso concreto
401 vs 403
```
