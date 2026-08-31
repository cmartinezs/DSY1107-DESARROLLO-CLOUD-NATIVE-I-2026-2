package cl.duoc.dsy1107.ev1.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.SecretKey;

public class JwtTamperExample {

    private static final String DEMO_SECRET =
            "clave-demo-local-ev1-32-bytes-minimo-no-usar-en-produccion";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java JwtTamperExample <jwt>");
            return;
        }

        String[] parts = args[0].split("\\.");
        if (parts.length != 3) {
            System.out.println("JWT inválido: se esperaban tres segmentos.");
            return;
        }

        String payloadJson = new String(
                Base64.getUrlDecoder().decode(parts[1]),
                StandardCharsets.UTF_8);

        String alteredPayload = payloadJson.replace("recurso.read recurso.write", "admin.full");
        String alteredPayloadEncoded = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(alteredPayload.getBytes(StandardCharsets.UTF_8));

        String tamperedToken = parts[0] + "." + alteredPayloadEncoded + "." + parts[2];

        System.out.println("Payload original:\n" + payloadJson);
        System.out.println("\nPayload alterado:\n" + alteredPayload);
        System.out.println("\nJWT alterado:\n" + tamperedToken);

        SecretKey key = Keys.hmacShaKeyFor(DEMO_SECRET.getBytes(StandardCharsets.UTF_8));

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(tamperedToken);

            System.out.println("ERROR: el token alterado fue aceptado inesperadamente.");
        } catch (JwtException | IllegalArgumentException ex) {
            System.out.println("\nResultado esperado: firma inválida / token rechazado.");
            System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }
}
