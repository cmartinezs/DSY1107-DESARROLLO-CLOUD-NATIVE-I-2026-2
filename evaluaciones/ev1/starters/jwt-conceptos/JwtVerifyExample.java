package cl.duoc.dsy1107.ev1.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

public class JwtVerifyExample {

    private static final String DEMO_SECRET =
            "clave-demo-local-ev1-32-bytes-minimo-no-usar-en-produccion";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java JwtVerifyExample <jwt>");
            return;
        }

        SecretKey key = Keys.hmacShaKeyFor(DEMO_SECRET.getBytes(StandardCharsets.UTF_8));

        try {
            var claims = Jwts.parser()
                    .verifyWith(key)
                    .requireIssuer("https://issuer.demo.local")
                    .requireAudience("api://dsy1107-demo")
                    .build()
                    .parseSignedClaims(args[0])
                    .getPayload();

            System.out.println("Token válido.");
            System.out.println("subject = " + claims.getSubject());
            System.out.println("issuer = " + claims.getIssuer());
            System.out.println("audience = " + claims.getAudience());
            System.out.println("expiration = " + claims.getExpiration());
            System.out.println("scope = " + claims.get("scope", String.class));
        } catch (JwtException | IllegalArgumentException ex) {
            System.out.println("Token rechazado: " + ex.getClass().getSimpleName());
            System.out.println(ex.getMessage());
        }
    }
}
