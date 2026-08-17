# Semana 2 · Gestión de APIs + fundamentos de identidad

**Periodo:** 17 al 22 de agosto de 2026  
**Actividad institucional:** cierre 1.1 + inicio 1.2

← [Volver al índice de semanas](../README.md)

## Objetivo semanal

Cerrar los pendientes de gestión de APIs y avanzar hacia autenticación/autorización moderna mediante OAuth2, OpenID Connect e Identity as a Service, **sin depender todavía de Azure ni de otro proveedor específico**.

## Contenidos oficiales

### Finalizar 1.1

- 1.1.1 Conociendo un API Manager.
- 1.1.2 Tutorial Creando Nuestro Primer API Manager.
- 1.1.3 Versionando APIs.
- 1.1.4 Configurando CORS en nuestro API Gateway.

### Iniciar 1.2

- 1.2.1 OAuth2 y OIDC.
- 1.2.2 Identity as a Service y CIAM.
- 1.2.3 Configurando un Tenant.
- 1.2.4 Configurando apps en un IDaaS.

> Los puntos 1.2.3 y 1.2.4 se trabajan esta semana **a nivel conceptual y de diseño**. La configuración real en Azure queda para cuando exista el entorno correspondiente.

## Patrón de trabajo

1. explicación conceptual breve;
2. ejemplo/diagrama acompañado;
3. práctica o laboratorio;
4. evidencia reproducible;
5. defensa técnica.

## Material creado

- [Guía · OAuth2 y OIDC sin depender de Azure](./01-oauth2-oidc-sin-proveedor.md)
- [Laboratorio · Diseñando un flujo OAuth2/OIDC](./laboratorio-flujo-identidad/README.md)
- [Plan específico DSY1107-002D](./DSY1107-002D.md)
- [Plan específico DSY1107-003D](./DSY1107-003D.md)

## Qué deben aprender haciendo

Cada grupo debe terminar siendo capaz de:

- cerrar/demostrar v1/v2 + CORS del gateway;
- dibujar actores OAuth2/OIDC correctamente;
- distinguir autenticación de autorización;
- diferenciar ID token y access token;
- proponer scopes;
- interpretar claims;
- resolver casos 401/403;
- separar autorización técnica de autorización de negocio;
- integrar conceptualmente identidad → gateway → backend;
- defender su solución sin PPT.

## Criterio técnico

No se enseña “hacer clic en una consola cloud” antes de comprender lo que representa cada elemento. Tenant, aplicación, issuer, client ID, redirect URI, scopes y claims deben tener significado antes de configurarlos en un proveedor.

## Material

- [Biblioteca pública](https://drive.google.com/drive/folders/1UOZMZcEbtfKFq4ygWKj3Yi8VEHkW7hx1?usp=sharing)
- [Semana 02 en Drive](https://drive.google.com/drive/folders/1Bi-w4_ZCWIffbYsgekkcvWE6oDV2rwDQ)
