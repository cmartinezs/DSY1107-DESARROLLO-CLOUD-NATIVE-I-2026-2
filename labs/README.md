# Laboratorios · DSY1107

Esta carpeta contiene los **laboratorios canónicos del repositorio docente**. Las carpetas semanales solo enlazan hacia aquí.

## Regla canónica

Los laboratorios de este repositorio son experiencias **locales, autocontenidas e independientes** orientadas a practicar el contenido de la semana sin requerir infraestructura cloud real.

```text
contenido semanal
→ ejemplo / explicación
→ laboratorio local del repo
→ checkpoints de comprensión
```

Los ejercicios, guías o laboratorios que requieran proveedores cloud pertenecen al **contenido institucional publicado en AVA**. Pueden cubrir la misma competencia, pero no constituyen una segunda fase de los laboratorios de este repositorio y no se incorporan a `labs/`.

```text
repo docente                     AVA institucional
------------                     -----------------
labs/ locales                    ejercicios/labs cloud
independientes                   contenido oficial
sin infraestructura cloud       proveedor según programa
```

## Disponibles

- [`api-gateway-local/`](api-gateway-local/) — routing, integración, versionado, políticas y CORS mediante Spring Cloud Gateway + backend público.
- [`identidad-local/`](identidad-local/) — laboratorio histórico de OAuth2/OIDC, PKCE, tokens, scopes, roles, 401/403, tenant y app registration.
- [`jwt-forense/`](jwt-forense/) — Semana 3: JWT, claims, audience/issuer/expiración, scopes, 401/403 y frontera gateway/backend con dominio neutral.
- [`fullstack-seguro/`](fullstack-seguro/) — Semana 4: flujo SPA + Authorization Code/PKCE + MSAL + API Manager/Gateway + Spring Security Resource Server, con checkpoints y matriz 401/403/2xx, explicado sin depender de infraestructura cloud real.

## Relación con AVA

Cuando AVA incluya una actividad cloud sobre la misma competencia, la semana puede enlazar o mencionar esa correspondencia pedagógica. Esa referencia sirve para que el estudiante reconozca el mismo concepto en un proveedor real, pero:

- la actividad cloud sigue siendo material institucional de AVA;
- no se copia ni replica dentro de `labs/`;
- el lab del repo debe seguir pudiendo ejecutarse sin recursos cloud;
- completar un lab del repo no reemplaza una actividad institucional obligatoria del AVA;
- una actividad AVA no convierte el lab local en parte de una secuencia técnica obligatoria.

## Independencia del Proyecto Formativo

Los laboratorios deben mantenerse independientes de RegistrApp. Si una competencia aprendida en un lab se aplica posteriormente al proyecto formativo, esa transferencia ocurre después y se documenta en `proyecto-formativo/`.

→ [Estrategia completa de laboratorios y relación con AVA](../docs/ESTRATEGIA-LABORATORIOS-CONCEPTO-A-CLOUD.md)
