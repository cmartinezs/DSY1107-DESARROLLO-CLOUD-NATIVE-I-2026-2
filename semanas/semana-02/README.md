# Semana 2 · Gestión de APIs + fundamentos de identidad

**Periodo:** 17 al 22 de agosto de 2026  
**Actividad institucional:** cierre 1.1 + inicio 1.2

← [Volver al índice de semanas](../README.md)

## Dominio formativo transversal

Desde esta semana se formaliza **ReservApp** como dominio longitudinal de DSY1107.

Los ejemplos, ejercicios y laboratorios realizados en clase reutilizarán este mismo sistema siempre que el contenido lo permita. Cada experiencia de aprendizaje debe recibir un checkpoint anterior, agregar una capacidad y dejar una nueva versión reutilizable.

→ [Ver estrategia transversal ReservApp](../../docs/DOMINIO-FORMATIVO-TRANSVERSAL.md)

ReservApp es exclusivamente formativo y se mantiene separado de los dominios y soluciones de evaluaciones sumativas.

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

## Evolución de ReservApp esta semana

### Entrada

ReservApp recibe lo trabajado con API Gateway:

- API de reservas;
- rutas a través del gateway;
- versionado `/v1` y `/v2`;
- CORS;
- comprensión de cliente → gateway → backend.

### Incremento

Se incorpora el modelo de identidad y autorización:

- usuario;
- autenticación vs autorización;
- OAuth2/OIDC;
- access token vs ID token;
- scopes `reservations.read` / `reservations.write`;
- claims;
- 401 vs 403;
- responsabilidades gateway/backend.

### Salida / checkpoint

Debe quedar un diagrama de arquitectura de ReservApp donde pueda explicarse:

```text
usuario → identidad → cliente con token → gateway → reservapp-api
```

junto con scopes, casos 401/403 y decisiones de autorización.

Este checkpoint se reutilizará en las experiencias siguientes cuando se incorporen proveedor real, JWT, seguridad del gateway y solución full stack.

## Patrón de trabajo

1. explicación conceptual breve;
2. demostración sobre ReservApp;
3. práctica/laboratorio incremental;
4. evidencia reproducible;
5. defensa técnica;
6. checkpoint para la próxima clase.

## Material creado

- [Guía · OAuth2 y OIDC sin depender de Azure](./01-oauth2-oidc-sin-proveedor.md)
- [Laboratorio · ReservApp: diseñando un flujo OAuth2/OIDC](./laboratorio-flujo-identidad/README.md)
- [Plan específico DSY1107-002D](./DSY1107-002D.md)
- [Plan específico DSY1107-003D](./DSY1107-003D.md)

## Qué deben aprender haciendo

Cada grupo debe terminar siendo capaz de:

- cerrar/demostrar v1/v2 + CORS del gateway;
- dibujar actores OAuth2/OIDC correctamente;
- distinguir autenticación de autorización;
- diferenciar ID token y access token;
- proponer scopes para ReservApp;
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
