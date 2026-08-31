package cl.duoc.dsy1107.ev1.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JwtDecodeExample {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java JwtDecodeExample <jwt>");
            return;
        }

        String[] parts = args[0].split("\\.");
        if (parts.length != 3) {
            System.out.println("El valor recibido no tiene la estructura header.payload.signature.");
            return;
        }

        String header = decode(parts[0]);
        String payload = decode(parts[1]);

        System.out.println("HEADER:\n" + header);
        System.out.println("\nPAYLOAD:\n" + payload);
        System.out.println("\nIMPORTANTE: solo hemos decodificado. No hemos validado la firma.");
    }

    private static String decode(String segment) {
        byte[] decoded = Base64.getUrlDecoder().decode(segment);
        return new String(decoded, StandardCharsets.UTF_8);
    }
}
