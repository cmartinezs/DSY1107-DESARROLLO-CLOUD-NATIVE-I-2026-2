# Identidad y acceso · DSY1107

Este dominio concentra la documentación canónica relacionada con **Identity as a Service**, Microsoft Entra ID, usuarios internos/externos, OAuth2/OIDC, MSAL, emisión de tokens y protección de APIs.

> La documentación de este dominio es canónica para el detalle técnico. La web del curso es una vista derivada y navegable; si difiere de estos documentos, debe considerarse desactualizada y corregirse.

## Comienza aquí

### Microsoft Entra ID · ruta completa

→ [Guía completa por etapas](./entra-guia-completa/README.md)

La ruta parte desde cuenta Duoc + Azure for Students y avanza por checkpoints verificables:

```mermaid
flowchart TD
    A[Cuenta + Azure for Students] --> B[Tenant / directorio / permisos]
    B --> C[SPA App Registration]
    C --> D[API + scopes]
    D --> E[Guest/B2B manual]
    E --> F[Self-service sign-up]
    F --> G[MSAL + Authorization Code + PKCE]
    G --> H[Access token API propia]
    H --> I[AWS API Gateway]
    I --> J[Pruebas 401/403 + evidencia]
```

### Caso específico · usuarios externos + SPA + API Gateway

→ [Referencia extendida](./entra-usuarios-externos-spa-api-gateway.md)

Úsala cuando el síntoma sea, por ejemplo, **“al dueño del tenant le funciona pero a un compañero no”** o cuando sea necesario diagnosticar issuer, audience, scopes y JWT Authorizer.

## Navegación por contexto

- [Semana 4 · guía operativa](../../semanas/semana-04/00a-guia-operativa-autenticacion-entra.md)
- [RegistrApp · transferencia del patrón](../../proyecto-formativo/semana-04/guia-operativa-identidad-entra.md)
- [Firebase Auth · comparación IDaaS](../../labs/firebase-auth-miniapp/README.md)
- [Portal web · Identidad y acceso](../../page/identidad.html)

## Autoridad y publicación

```mermaid
flowchart TD
    STD[ADÜMÜN Platform Standards] --> DOC[docs/identity · fuente canónica del dominio]
    DOC --> WEEK[Semana / proyecto / labs · consumo contextual]
    DOC --> WEB[page/identidad.html · read model derivado]
```

Este repositorio consume `STD-ENG-DOC-001` para documentación/publicación y `STD-ENG-DIAG-001` para diagramación. No redefine esos estándares localmente.

## Regla de actualización

Cuando cambie materialmente una etapa, flujo, prerequisito, permiso o frontera de seguridad de este dominio:

1. actualizar primero el documento canónico correspondiente;
2. actualizar enlaces/contextos que consumen esa información;
3. actualizar la superficie web derivada en el mismo cambio cuando el conocimiento sea relevante para estudiantes;
4. mantener diagramas técnicos en Mermaid cuando sea viable;
5. nunca publicar secretos, tokens reutilizables, passwords, client secrets ni credenciales cloud.
