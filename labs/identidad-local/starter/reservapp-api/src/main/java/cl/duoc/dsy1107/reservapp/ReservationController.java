package cl.duoc.dsy1107.reservapp;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ReservationController {

    private static final List<Map<String, String>> RESERVATIONS = List.of(
            Map.of("id", "R-101", "ownerId", "user-1000", "service", "Cancha 1", "status", "CONFIRMED"),
            Map.of("id", "R-202", "ownerId", "user-2000", "service", "Cancha 2", "status", "CONFIRMED"),
            Map.of("id", "R-303", "ownerId", "user-1000", "service", "Sala de reuniones", "status", "PENDING")
    );

    @GetMapping("/reservations")
    public List<Map<String, String>> reservations(@RequestHeader("Authorization") String authorization) {
        Token token = decode(authorization);
        requireScope(token, "reservations.read");
        if (token.role().equals("operator")) return RESERVATIONS;
        return RESERVATIONS.stream().filter(r -> r.get("ownerId").equals(token.sub())).toList();
    }

    @DeleteMapping("/reservations/{id}")
    public Map<String, String> cancel(@PathVariable String id, @RequestHeader("Authorization") String authorization) {
        Token token = decode(authorization);
        requireScope(token, "reservations.write");
        Map<String, String> reservation = RESERVATIONS.stream()
                .filter(r -> r.get("id").equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));

        if (!token.role().equals("operator") && !reservation.get("ownerId").equals(token.sub())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Regla de negocio: un cliente solo puede cancelar sus propias reservas");
        }
        return Map.of("id", id, "result", "CANCELLED", "authorizedAs", token.role());
    }

    private void requireScope(Token token, String expected) {
        if (!List.of(token.scope().split(" ")).contains(expected)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Falta scope " + expected);
        }
    }

    private Token decode(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Falta Bearer token");
        }
        try {
            String raw = new String(Base64.getUrlDecoder().decode(authorization.substring(7)), StandardCharsets.UTF_8);
            String[] p = raw.split("\\|", -1);
            if (p.length != 7 || !p[0].equals("access")) throw new IllegalArgumentException();
            return new Token(p[1], p[2], p[3], p[4], p[5], Long.parseLong(p[6]));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token didáctico inválido");
        }
    }

    private record Token(String sub, String iss, String aud, String scope, String role, long exp) {}
}
