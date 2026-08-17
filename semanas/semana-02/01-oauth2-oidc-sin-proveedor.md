# Guía · OAuth2 y OIDC sin depender de Azure

Esta semana el foco es comprender el modelo, no configurar Azure.

## Qué problema estamos resolviendo

Una aplicación necesita saber **quién es el usuario** y **qué puede hacer** sin administrar contraseñas en cada sistema.

- **Autenticación:** demostrar quién eres.
- **Autorización:** decidir qué puedes hacer.
- **OAuth2:** framework de autorización.
- **OpenID Connect (OIDC):** capa de identidad construida sobre OAuth2.

## Actores mínimos

1. **Usuario / Resource Owner**.
2. **Cliente**: aplicación que necesita actuar.
3. **Authorization Server / IdP**: autentica y emite tokens.
4. **Resource Server / API**: protege recursos.

## Tokens

### Access token

Se presenta ante una API. Representa permisos delegados y normalmente contiene audiencia, scopes y expiración.

### ID token

Le informa al cliente sobre la identidad autenticada. No debe utilizarse como sustituto del access token para llamar una API.

## Claims, scopes y roles

- **Claim:** dato contenido en un token (`sub`, `iss`, `aud`, `exp`, etc.).
- **Scope:** permiso solicitado/concedido (`orders.read`).
- **Role:** agrupación de capacidades asociada a un usuario o contexto.

## Flujo recomendado para aplicaciones modernas

Para una app pública se trabaja conceptualmente con **Authorization Code + PKCE**:

1. la aplicación redirige al usuario al proveedor de identidad;
2. el usuario se autentica allí;
3. el cliente recibe un código;
4. el código se intercambia por tokens;
5. el access token se usa contra la API;
6. la API valida firma, issuer, audience y expiración;
7. la API decide si el scope/rol permite la operación.

## Relación con API Gateway

El gateway puede aplicar políticas transversales y rechazar peticiones antes de llegar al backend. Aun así, la autorización de negocio pertenece a la aplicación/servicio cuando depende del dominio.

Ejemplo:

```text
Usuario
  ↓ login
Identity Provider
  ↓ access token
Cliente
  ↓ Authorization: Bearer <token>
API Gateway
  ↓ petición validada
Backend
```

## Preguntas que un estudiante debe poder responder

- ¿OAuth2 autentica al usuario por sí solo?
- ¿Para qué sirve OIDC?
- ¿Por qué ID token y access token no son intercambiables?
- ¿Qué diferencia hay entre `401 Unauthorized` y `403 Forbidden`?
- ¿Qué debería validar una API antes de confiar en un token?
- ¿Qué responsabilidad corresponde al gateway y cuál al backend?

## Importante esta semana

**No se configura Azure todavía.** Los conceptos se estudian de forma portable. La configuración de tenant/app en un proveedor se realizará cuando el entorno de la asignatura esté disponible y corresponda al avance real.
