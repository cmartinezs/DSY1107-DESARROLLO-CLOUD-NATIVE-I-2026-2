package cl.duoc.dsy1107.identity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*")
public class TokenController {

    private static final String ISSUER = "https://identity.reservapp.local";
    private static final String CLIENT_ID = "reservapp-web";
    private static final String REDIRECT_URI = "http://localhost:5500/index.html";
    private final Map<String, AuthorizationRequest> codes = new ConcurrentHashMap<>();

    @GetMapping("/authorize")
    public Map<String, String> authorize(
            @RequestParam String clientId,
            @RequestParam String redirectUri,
            @RequestParam String user,
            @RequestParam(defaultValue = "reservations.read") String scope,
            @RequestParam(defaultValue = "reservapp-api") String audience,
            @RequestParam String codeChallenge) {

        if (!CLIENT_ID.equals(clientId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "client_id no registrado");
        }
        if (!REDIRECT_URI.equals(redirectUri)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "redirect_uri no registrada");
        }

        String code = UUID.randomUUID().toString();
        codes.put(code, new AuthorizationRequest(user, scope, audience, codeChallenge));
        return Map.of("authorizationCode", code, "redirectUri", REDIRECT_URI);
    }

    @GetMapping("/exchange")
    public Map<String, Object> exchange(@RequestParam String code, @RequestParam String codeVerifier) {
        AuthorizationRequest request = codes.remove(code);
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "authorization_code inválido o ya utilizado");
        }
        if (!request.codeChallenge().equals(challenge(codeVerifier))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PKCE verifier inválido");
        }
        return issueTokens(request.user(), request.scope(), request.audience());
    }

    private Map<String, Object> issueTokens(String user, String scope, String audience) {
        String sub = switch (user) {
            case "operador" -> "user-9000";
            case "bruno" -> "user-2000";
            default -> "user-1000";
        };
        String role = user.equals("operador") ? "operator" : "customer";
        long exp = Instant.now().plusSeconds(3600).getEpochSecond();

        String accessPayload = String.join("|", "access", sub, ISSUER, audience, scope, role, String.valueOf(exp));
        String idPayload = String.join("|", "id", sub, ISSUER, user, user + "@example.edu", String.valueOf(exp));

        return Map.of(
                "tokenType", "Bearer",
                "issuer", ISSUER,
                "expiresIn", 3600,
                "accessToken", encode(accessPayload),
                "idToken", encode(idPayload),
                "didacticWarning", "Tokens simulados: NO son JWT reales ni deben usarse fuera del laboratorio"
        );
    }

    private String challenge(String verifier) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(verifier.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private record AuthorizationRequest(String user, String scope, String audience, String codeChallenge) {}
}
