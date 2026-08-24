# RegistrApp · Semana 2

## Estado de entrada

RegistrApp recibe el estado de salida de Semana 1: propósito mínimo, recurso principal, API inicial y arquitectura cliente → API.

## Contenido transferible

Según el avance real de la sección, pueden transferirse:

- API Gateway;
- versionado `/v1` y `/v2`;
- CORS;
- actores OAuth2/OIDC;
- Authorization Code + PKCE;
- access token vs ID token;
- identidad/IDaaS de forma conceptual.

## Incremento

Extender la arquitectura existente, sin reiniciarla:

1. incorporar gateway como punto de entrada cuando el concepto esté comprendido;
2. justificar versionado y CORS si corresponden al estado real;
3. identificar Resource Owner, Client, Authorization Server/IdP y Resource Server;
4. documentar conceptualmente el flujo de autenticación/autorización;
5. proponer primeras capacidades/scopes sin entrar todavía en validación JWT de Semana 3.

## Evidencia

- diagrama actualizado sobre la versión de Semana 1;
- rutas/versiones y política CORS si fueron aplicadas;
- mapa de actores de identidad;
- explicación access token vs ID token;
- decisiones técnicas registradas;
- DevLog con referencias a cambios relevantes.

## Estado de salida

RegistrApp conserva la API inicial y suma las capacidades efectivamente comprendidas de gateway e identidad. Este estado constituye la entrada obligatoria de Semana 3.

## Deuda / siguiente paso

Todo elemento no alcanzado por la sección permanece explícitamente como deuda. Semana 3 no debe asumirlo terminado sin evidencia.
