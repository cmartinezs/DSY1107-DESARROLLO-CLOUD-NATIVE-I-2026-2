package cl.duoc.dsy1107.security;

import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints mínimos para comprobar autenticación y autorización antes de
 * adaptar las reglas al dominio real del proyecto.
 */
@RestController
public class SecurityExampleController {

    @GetMapping("/public/health")
    public Map<String, Object> publicHealth() {
        return Map.of(
                "status", "ok",
                "access", "public"
        );
    }

    @GetMapping("/api/profile")
    public Map<String, Object> authenticated(Authentication authentication) {
        Map<String, Object> response = new java.util.LinkedHashMap<>();
        response.put("authenticated", authentication.isAuthenticated());
        response.put("name", authentication.getName());
        response.put("authorities", authentication.getAuthorities());

        if (authentication instanceof JwtAuthenticationToken jwtAuthentication) {
            response.put("subject", jwtAuthentication.getToken().getSubject());
            response.put("issuer", jwtAuthentication.getToken().getIssuer());
            response.put("audience", jwtAuthentication.getToken().getAudience());
        }

        return response;
    }

    @PostMapping("/api/write/example")
    public Map<String, Object> writeWithScope() {
        return Map.of(
                "status", "ok",
                "requiredAuthority", "SCOPE_recurso.write"
        );
    }

    @GetMapping("/api/admin/example")
    public Map<String, Object> adminWithRole() {
        return Map.of(
                "status", "ok",
                "requiredAuthority", "ROLE_ADMIN"
        );
    }
}
