package cl.duoc.dsy1107.identity;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class TokenController {

    @GetMapping("/token")
    public Map<String, Object> token(
            @RequestParam(defaultValue = "ana") String user,
            @RequestParam(defaultValue = "reservations.read") String scope,
            @RequestParam(defaultValue = "reservapp-api") String audience) {

        String sub = switch (user) {
            case "operador" -> "user-9000";
            case "bruno" -> "user-2000";
            default -> "user-1000";
        };
        String role = user.equals("operador") ? "operator" : "customer";
        long exp = Instant.now().plusSeconds(3600).getEpochSecond();

        String accessPayload = String.join("|", "access", sub, audience, scope, role, String.valueOf(exp));
        String idPayload = String.join("|", "id", sub, user, user + "@example.edu", String.valueOf(exp));

        return Map.of(
                "tokenType", "Bearer",
                "expiresIn", 3600,
                "accessToken", encode(accessPayload),
                "idToken", encode(idPayload),
                "didacticWarning", "Tokens simulados: NO son JWT reales ni deben usarse fuera del laboratorio"
        );
    }

    private String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
}
