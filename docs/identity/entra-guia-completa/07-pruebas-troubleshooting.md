# Etapa 7 · Matriz de pruebas, troubleshooting y evidencia base

## Objetivo

Diagnosticar por capas y demostrar que el **flujo base completo** funciona para más de un usuario antes de incorporar self-service sign-up.

> Esta etapa cierra la Parte I de la guía. No se inicia la extensión de auto-registro mientras el circuito `Guest manual → MSAL → access token → Gateway → backend` no esté razonablemente estable y explicado.

## Matriz mínima

| ID | Caso | Resultado esperado |
|---|---|---|
| AUTH-E0 | Azure for Students visible y activo | continuar |
| AUTH-E1 | directorio correcto y App registrations accesible | continuar |
| AUTH-E2 | owner hace login en SPA | éxito |
| AUTH-E3 | usuario no invitado intenta entrar a single-tenant | rechazo |
| AUTH-E4 | Guest pendiente intenta entrar | completar invitación |
| AUTH-E5 | Guest aceptado hace login | éxito |
| AUTH-E6 | endpoint protegido sin token | rechazo |
| AUTH-E7 | access token para API propia | acceso permitido |
| AUTH-E8 | token para recurso equivocado | rechazo |
| AUTH-E9 | token válido con scope insuficiente | rechazo de autorización |
| AUTH-E10 | redirect URI incorrecto | fallo OAuth antes de API |

## Árbol de diagnóstico

```mermaid
flowchart TD
    S[Algo falla] --> A{¿Puedo entrar a Azure y ver la suscripción?}
    A -- No --> A1[Cuenta / Azure for Students]
    A -- Sí --> B{¿Estoy en el tenant correcto?}
    B -- No --> B1[Cambiar directorio]
    B -- Sí --> C{¿Puedo registrar/administrar la app?}
    C -- No --> C1[Permisos / política tenant]
    C -- Sí --> D{¿El usuario puede iniciar login?}
    D -- No --> D1[Guest / invitación / assignment / authority / redirect]
    D -- Sí --> E{¿Obtengo access token para mi API?}
    E -- No --> E1[Scopes / API permissions / consentimiento]
    E -- Sí --> F{¿API Gateway lo acepta?}
    F -- No --> F1[Issuer / audience / exp / scope]
    F -- Sí --> G[Revisar backend / regla de negocio]
```

## Errores frecuentes y qué revisar primero

### "No me aparece el tenant"

1. verificar cuenta activa;
2. abrir selector de directorios;
3. comprobar si el alumno confunde suscripción con directorio;
4. no asumir que Azure for Students creó un tenant administrable adicional.

### "No tengo permiso para crear App Registration"

1. verificar directorio;
2. verificar si la cuenta es Member o Guest;
3. revisar política de registro de aplicaciones;
4. no intentar solucionar desde el código;
5. si es tenant institucional administrado, documentar la restricción y usar el entorno autorizado para el laboratorio.

### "No puedo invitar usuarios"

Revisar permisos/políticas de colaboración externa del tenant. La ausencia de la opción es un problema administrativo, no de MSAL.

### "A mí me funciona pero a mi compañero no"

1. confirmar Guest creado;
2. confirmar invitación aceptada;
3. confirmar tenant/authority;
4. revisar `Assignment required?`;
5. probar primero login, después API.

### "Hace login pero API Gateway responde 401/403"

Inspeccionar:

- `iss`;
- `aud`;
- `exp`;
- `scp`;
- authorizer de la ruta;
- scope requerido.

No reemplazar access token por ID token.

### "El token se ve correcto al decodificar"

Eso no prueba que la API deba confiar en él. Decodificar solo permite leer claims; el gateway debe verificar firma y contexto.

## Evidencia requerida de la Parte I

El grupo debe conservar evidencia sanitizada de:

1. Azure for Students activo;
2. nombre del tenant/directorio utilizado;
3. App Registration SPA single-tenant;
4. App Registration API + scope;
5. Member y Guest existentes;
6. Guest aceptado;
7. login de al menos dos usuarios;
8. access token observado con `iss`, `aud` y `scp` sin publicar el token completo;
9. request sin token rechazado;
10. request con token correcto aceptado;
11. caso de token/scope incorrecto rechazado;
12. diagrama Mermaid del flujo;
13. DevLog con problema, causa, corrección y resultado.

## No publicar

- passwords;
- tokens completos reutilizables;
- refresh tokens;
- client secrets;
- AWS credentials;
- certificados/keys privadas.

## Gate de cierre de la Parte I

El flujo base no se considera terminado porque el dueño del tenant pueda iniciar sesión. Debe demostrarse el circuito completo:

```mermaid
flowchart LR
    M[Member] --> SPA[SPA]
    G[Guest manual] --> SPA
    SPA --> ENTRA[Entra ID]
    ENTRA --> TOKEN[Access token API propia]
    TOKEN --> GW[API Gateway]
    GW --> API[Backend autorizado]
```

Antes de abrir self-service, el estudiante debería poder responder sin adivinar:

- dónde vive el tenant;
- qué representa el Guest;
- qué hace MSAL;
- qué token se envía a la API;
- qué valida el Gateway;
- por qué una falla produce 401/403;
- en qué capa buscar primero cada síntoma.

## Qué ocurre después

Una vez cerrado este gate se abre una **extensión separada**. No se vuelve atrás a reordenar la Parte I.

```mermaid
flowchart LR
    BASE[Etapas 0–7 · flujo base cerrado] --> EXT[Etapas 8–13 · incorporar self-service]
    EXT --> RETEST[Etapa 14 · segunda pasada completa de pruebas]
```

La Etapa 14 volverá a comprobar token, Gateway y backend, pero ahora con un Guest aprovisionado por self-service, además de verificar que el Guest manual anterior siga funcionando.

→ Continúa con [Etapa 8 · Extensión: auto-registro de usuarios externos](./04a-self-service-introduccion.md).
