package cl.duoc.dsy1107.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Valida que el Access Token haya sido emitido para esta API.
 *
 * <p>Se implementa de forma explícita para que el concepto de audience sea visible
 * y para mantener compatibilidad con distintas versiones de Spring Security 6.x.</p>
 */
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

    private final String expectedAudience;

    public AudienceValidator(String expectedAudience) {
        this.expectedAudience = expectedAudience;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (jwt.getAudience() != null && jwt.getAudience().contains(expectedAudience)) {
            return OAuth2TokenValidatorResult.success();
        }

        OAuth2Error error = new OAuth2Error(
                "invalid_token",
                "El token no contiene la audience esperada para esta API",
                null
        );

        return OAuth2TokenValidatorResult.failure(error);
    }
}
