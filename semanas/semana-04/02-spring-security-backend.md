# 2 · Spring Security como Resource Server

## Objetivo

Proteger una API Spring Boot para que acepte únicamente access tokens válidos y autorice operaciones según claims/scopes.

## Rol del backend

Cuando el frontend obtiene un access token, el backend no debe confiar en él por haber llegado desde un cliente autenticado. Debe validarlo como **Resource Server**.

```text
request + Bearer token
→ validar firma
→ validar issuer
→ validar audience
→ validar expiración
→ mapear claims/scopes
→ autorizar endpoint
```

## Dependencia conceptual

En Spring Boot moderno, el patrón habitual utiliza Spring Security OAuth2 Resource Server con JWT.

Configuración representativa:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://login.microsoftonline.com/<tenant-id>/v2.0
```

La configuración real debe corresponder al proveedor de identidad y tenant utilizados en laboratorio.

## SecurityFilterChain

Ejemplo conceptual:

```java
@Bean
SecurityFilterChain security(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/public/**").permitAll()
            .requestMatchers("/api/read/**").hasAuthority("SCOPE_read")
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth -> oauth.jwt())
        .build();
}
```

## 401 vs 403

- **401 Unauthorized:** no existe autenticación válida para procesar la operación.
- **403 Forbidden:** existe una identidad autenticada, pero no posee permiso suficiente.

Ejemplos:

| Situación | Resultado esperado |
|---|---:|
| sin token | 401 |
| token expirado | 401 |
| firma inválida | 401 |
| issuer/audience inválidos | 401 |
| token válido sin scope requerido | 403 |
| token válido con scope requerido | 2xx |

## Claims y authorities

Spring puede convertir scopes del token en authorities como `SCOPE_read`. Para roles o claims personalizados puede ser necesario un `JwtAuthenticationConverter`.

No se debe autorizar una operación simplemente porque el JWT tenga un campo con un nombre esperado: el token debe haber pasado primero las validaciones criptográficas y contextuales.

## Audience

El token debe haber sido emitido **para esta API**. Validar solo la firma no basta: un token válido para otro recurso no debería ser aceptado.

## Gateway y backend

El gateway puede ejecutar controles tempranos, pero el backend conserva responsabilidad sobre la autorización de negocio y sobre las reglas que no deben depender de un único perímetro externo.

## Errores frecuentes

- desactivar seguridad para “hacer funcionar” la demo;
- aceptar cualquier token firmado por el proveedor sin validar audiencia;
- autorizar por datos controlados por el cliente;
- registrar tokens completos en logs;
- mezclar CORS con autenticación;
- devolver 403 cuando el request ni siquiera está autenticado.

## Evidencia sugerida

Probar al menos cuatro requests reproducibles: sin token, token inválido/expirado, token válido sin permiso y token válido con permiso.
