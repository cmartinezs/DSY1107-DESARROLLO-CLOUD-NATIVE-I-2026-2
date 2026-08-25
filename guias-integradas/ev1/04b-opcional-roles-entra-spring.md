# ★ 04B · Roles de Entra en Spring Security

## Objetivo

Extender CloudTasks **solo después de que 04/04A esté completamente en PASS** para observar la diferencia entre:

```text
scope
→ permiso delegado para una operación/API

role
→ pertenencia/función asignada al sujeto
```

Esta etapa es opcional porque algunos sandboxes académicos pueden restringir la creación/asignación de app roles.

## Prerrequisito

```text
04A build PASS
JWT PASS
scopes PASS
ownership PASS
```

No usar roles para reparar un problema de scopes.

---

# 1. Crear app role en `cloudtasks-api`

En Microsoft Entra, dentro de la app registration de la API, crear un app role con valor:

```text
Admin
```

Usar una descripción clara, por ejemplo:

```text
Administrador de CloudTasks
```

Si el entorno solicita tipos de miembros permitidos, seleccionar la opción coherente con los usuarios/grupos que utilizará el laboratorio.

## 2. Asignar el rol

Asignar `Admin` a un usuario de prueba mediante la experiencia disponible en el tenant/Enterprise Application.

Después de cambiar asignaciones, cerrar sesión y adquirir **un token nuevo**. Un token emitido antes de la asignación no cambia retroactivamente.

## CHECKPOINT 04B-1 · token

Inspeccionar temporalmente el Access Token nuevo.

Esperado:

```json
"roles": ["Admin"]
```

Si `roles` no aparece, no modificar Spring todavía. Revisar primero la creación/asignación del app role y volver a emitir token.

---

# 3. Por qué `roles` no funciona automáticamente con `hasRole`

El mapping estándar de Resource Server se concentra en scopes:

```text
scp=tasks.read
→ SCOPE_tasks.read
```

Un claim:

```json
"roles": ["Admin"]
```

no debe suponerse convertido automáticamente en:

```text
ROLE_Admin
```

Por eso se agrega un converter explícito.

---

# 4. Agregar converter de scopes + roles

En `SecurityConfig.java` agregar imports:

```java
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
```

Agregar bean:

```java
@Bean
JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter scopeConverter = new JwtGrantedAuthoritiesConverter();

    JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
    authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
        Collection<GrantedAuthority> authorities =
                new ArrayList<>(scopeConverter.convert(jwt));

        List<String> roles = jwt.getClaimAsStringList("roles");
        if (roles != null) {
            roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .forEach(authorities::add);
        }

        return authorities;
    });

    return authenticationConverter;
}
```

El converter conserva scopes y **agrega** roles. No reemplazar uno por otro.

---

# 5. Conectar converter al Resource Server

Modificar el método `securityFilterChain` para recibir el converter:

```java
@Bean
SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        JwtAuthenticationConverter jwtAuthenticationConverter
) throws Exception {
```

Y reemplazar la configuración JWT simple por:

```java
.oauth2ResourceServer(oauth -> oauth
        .jwt(jwt -> jwt
                .jwtAuthenticationConverter(jwtAuthenticationConverter)
        )
);
```

Agregar a las reglas:

```java
.requestMatchers("/api/admin/**").hasRole("Admin")
```

Colocarla antes de `.anyRequest().authenticated()`.

---

# 6. Crear `AdminController.java`

```java
package cl.duoc.cmartinez.cloudtasks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        return Map.of(
                "service", "cloudtasks-api",
                "admin", true
        );
    }
}
```

Adaptar `cmartinez` al package personal.

---

# 7. Compilar antes de probar

PowerShell:

```powershell
.\mvnw.cmd clean test
```

Git Bash/Linux/macOS:

```bash
./mvnw clean test
```

**CHECKPOINT 04B-2**

- [ ] compile PASS.
- [ ] scopes siguen funcionando.
- [ ] `ROLE_Admin` se agrega sin eliminar `SCOPE_...`.

---

# 8. Probar

Usuario autenticado **sin** rol Admin:

```text
GET /api/admin/stats
→ 403
```

Usuario con token nuevo que contiene:

```json
"roles": ["Admin"]
```

Esperado:

```text
GET /api/admin/stats
→ 200
```

## CHECKPOINT 04B-3

```text
sin Admin → 403 PASS
con Admin → 200 PASS
scopes read/write siguen PASS
```

---

# 9. Qué debe explicar

```text
scope ≠ role
scp → SCOPE_tasks.read
roles[Admin] → ROLE_Admin mediante converter explícito
asignar rol no modifica tokens ya emitidos
backend sigue validando issuer/audience/firma antes de autorizar rol
```

## SI FALLA

| Síntoma | Revisar primero |
|---|---|
| token no contiene `roles` | asignación Entra + token nuevo |
| token contiene Admin pero 403 | converter + `ROLE_Admin` |
| scopes dejaron de funcionar | converter reemplazó en vez de combinar authorities |
| 401 | validación JWT, no role |
| sandbox no permite asignar rol | omitir 04B y continuar ruta base |

## Convergencia

Después de 04B, volver a la ruta común:

→ [05 · Backend en AWS EC2](./05-aws-backend.md)
