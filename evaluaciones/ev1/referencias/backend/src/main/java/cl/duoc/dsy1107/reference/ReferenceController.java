package cl.duoc.dsy1107.reference;

import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReferenceController {

    @GetMapping("/public/health")
    public Map<String, String> health() {
        return Map.of("status", "ok", "access", "public");
    }

    @GetMapping("/api/profile")
    public Map<String, Object> profile(Authentication authentication) {
        return Map.of(
                "status", "ok",
                "user", authentication.getName(),
                "authorities", authentication.getAuthorities().stream().map(Object::toString).toList());
    }

    @PostMapping("/api/write/example")
    public Map<String, String> write() {
        return Map.of("status", "ok", "required", "SCOPE_recurso.write");
    }

    @GetMapping("/api/admin/example")
    public Map<String, String> admin() {
        return Map.of("status", "ok", "required", "ROLE_ADMIN");
    }
}
