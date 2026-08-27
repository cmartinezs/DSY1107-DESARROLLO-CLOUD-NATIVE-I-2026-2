# 3 · Arquitectura Full Stack segura en la nube

## Objetivo

Relacionar las piezas estudiadas —frontend, proveedor de identidad, API Gateway y backend— en una arquitectura segura y explicable.

## Arquitectura de referencia

```text
[Usuario]
   |
   v
[SPA / Frontend]
   |  Authorization Code + PKCE
   v
[Identity Provider]
   |
   | access token
   v
[SPA]
   |
   | HTTPS + Bearer token
   v
[API Gateway]
   |
   v
[Spring Boot Resource Server]
   |
   v
[Datos / servicios internos]
```

## Responsabilidades

### Frontend

- iniciar autenticación;
- solicitar scopes apropiados;
- no contener secretos;
- minimizar exposición de tokens;
- gestionar errores de sesión sin saltarse controles.

### Identity Provider

- autenticar al usuario;
- emitir tokens;
- publicar metadata y claves públicas;
- representar tenant, aplicaciones y permisos.

### API Gateway

- exponer una entrada controlada;
- aplicar políticas técnicas comunes;
- rate limiting, observabilidad y controles tempranos cuando corresponda;
- no reemplazar la autorización de negocio del backend.

### Backend / Resource Server

- validar token y contexto;
- aplicar autorización;
- proteger reglas de negocio y datos;
- no confiar en parámetros del cliente para identidad/permisos.

## Controles fundamentales

1. HTTPS en tránsito.
2. Authorization Code + PKCE para SPA.
3. No usar client secret en frontend.
4. Validación de firma, `iss`, `aud`, `exp` y permisos.
5. Mínimo privilegio para scopes y roles.
6. CORS limitado a orígenes requeridos.
7. Secretos en mecanismos de configuración seguros, no en Git.
8. Logs sin tokens ni credenciales completas.
9. Separación clara entre autenticación y autorización.
10. Observabilidad de fallos 401/403 sin filtrar información sensible.

## CORS no es autenticación

CORS controla qué navegadores pueden leer respuestas desde determinados orígenes. No protege una API contra clientes no navegador y no reemplaza OAuth2/OIDC.

```text
CORS → política del navegador
OAuth2/OIDC → identidad/delegación
Spring Security → protección del recurso
```

## Threat sketch

| Riesgo | Control principal |
|---|---|
| robo/intercepción de authorization code | PKCE + HTTPS |
| token emitido para otra API | validación de audience |
| token de otro tenant/emisor | validación de issuer |
| permisos excesivos | scopes/roles mínimos |
| secret expuesto en JavaScript | arquitectura public client sin secret |
| XSS roba token accesible | reducir exposición + CSP/buenas prácticas frontend |
| credenciales en repositorio | secret management + `.gitignore` + rotación |
| bypass del gateway | backend validando autenticación/autorización |

## Principio de defensa en profundidad

La arquitectura no debe depender de una única barrera. Gateway, backend, IdP y cliente tienen responsabilidades complementarias.

## Ejercicio

Dibuje un flujo completo de una operación `GET /api/orders` e indique:

- dónde ocurre autenticación;
- dónde se obtiene el token;
- qué audience se espera;
- qué scope se exige;
- qué componente puede responder 401;
- qué componente puede responder 403;
- qué datos nunca deben aparecer en logs.
