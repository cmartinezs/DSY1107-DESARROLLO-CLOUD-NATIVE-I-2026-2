package cl.duoc.dsy1107.ev1.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtCreateAndSignExample {

    private static final String DEMO_SECRET =
            "clave-demo-local-ev1-32-bytes-minimo-no-usar-en-produccion";

    public static void main(String[] args) {
        SecretKey key = Keys.hmacShaKeyFor(DEMO_SECRET.getBytes(StandardCharsets.UTF_8));

        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(300);

        String token = Jwts.builder()
                .issuer("https://issuer.demo.local")
                .subject("alumno-123")
                .audience().add("api://dsy1107-demo").and()
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .claim("scope", "recurso.read recurso.write")
                .claim("nombre", "Alumno Demo")
                .signWith(key)
                .compact();

        System.out.println("JWT firmado:\n");
        System.out.println(token);
        System.out.println("\nObserva que el token contiene tres segmentos separados por puntos.");
    }
}
