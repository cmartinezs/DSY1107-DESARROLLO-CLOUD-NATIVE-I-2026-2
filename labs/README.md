# Laboratorios · DSY1107

Esta carpeta contiene los **laboratorios canónicos** de la asignatura. Las carpetas semanales solo enlazan hacia aquí.

La estrategia pedagógica es:

```text
concepto → laboratorio local/neutral → laboratorio cloud real → comparación
```

## Disponibles

- [`api-gateway-local/`](api-gateway-local/) — routing, integración, versionado, políticas y CORS mediante Spring Cloud Gateway + backend público.
- [`identidad-local/`](identidad-local/) — laboratorio histórico de OAuth2/OIDC, PKCE, tokens, scopes, roles, 401/403, tenant y app registration.
- [`jwt-forense/`](jwt-forense/) — Semana 3: JWT, claims, audience/issuer/expiración, scopes, 401/403 y frontera gateway/backend con dominio neutral.

## Regla de evolución

Cuando aparezca el laboratorio cloud real, se agrega como experiencia separada y enlazada al laboratorio conceptual. El laboratorio local/neutral no se elimina: permite comparar qué cambia al usar un servicio administrado y qué conceptos permanecen iguales.

Los laboratorios nuevos deben mantenerse independientes de RegistrApp; la transferencia al proyecto ocurre después y vive en `proyecto-formativo/`.

→ [Estrategia completa: concepto a cloud](../docs/ESTRATEGIA-LABORATORIOS-CONCEPTO-A-CLOUD.md)
