package cl.duoc.dsy1107.gateway;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class DidacticTokenFilter extends OncePerRequestFilter {

    private static final String EXPECTED_ISSUER = "https://identity.reservapp.local";
    private static final String EXPECTED_AUDIENCE = "reservapp-api";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/api/") || "OPTIONS".equals(request.getMethod());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            reject(response, 401, "Falta access token");
            return;
        }

        try {
            String raw = new String(Base64.getUrlDecoder().decode(authorization.substring(7)), StandardCharsets.UTF_8);
            String[] p = raw.split("\\|", -1);

            if (p.length != 7 || !"access".equals(p[0])) {
                reject(response, 401, "Se esperaba un access token didáctico, no un ID token");
                return;
            }
            if (!EXPECTED_ISSUER.equals(p[2])) {
                reject(response, 401, "Issuer no confiable");
                return;
            }
            if (!EXPECTED_AUDIENCE.equals(p[3])) {
                reject(response, 401, "Audience incorrecta");
                return;
            }
            if (Long.parseLong(p[6]) <= Instant.now().getEpochSecond()) {
                reject(response, 401, "Token expirado");
                return;
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            reject(response, 401, "Token inválido");
        }
    }

    private void reject(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.getWriter().write("{\"status\":" + status + ",\"message\":\"" + message + "\"}");
    }
}
