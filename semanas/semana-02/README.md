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

## Contenidos oficiales y material por tema

Cada contenido mantiene un **Markdown base suficiente para los aprendizajes esperados de la asignatura**. Cuando un tema dispone de material extendido, se ofrece además una carpeta de profundización opcional.

### Finalizar 1.1

Los contenidos pendientes de Semana 01 se cierran utilizando el material existente:

- 1.1.1 Conociendo un API Manager.
- 1.1.2 Tutorial Creando Nuestro Primer API Manager.
- 1.1.3 Versionando APIs.
- 1.1.4 Configurando CORS en nuestro API Gateway.

### 1.2 Implementando autenticación con Identity as a Service

1. **[1.2.1 · OAuth2 y OIDC](./01-oauth2-oidc.md)**  
   Autenticación vs autorización, actores, Authorization Code + PKCE, access token vs ID token, scopes, claims, 401/403 y responsabilidades gateway/backend.  
   → **[Si quieres profundizar: OAuth2/OIDC y Authorization Code + PKCE](./01-oauth2-oidc/README.md)**

2. **[1.2.2 · Identity as a Service y CIAM](./02-idaas-ciam.md)**  
   IdP, IDaaS, IAM vs CIAM, usuarios, aplicaciones, issuer, scopes, claims y separación de responsabilidades.

3. **[1.2.3 · Configurando un Tenant](./03-configurando-tenant.md)**  
   Diseño del espacio de identidad de ReservApp: usuarios, aplicaciones, permisos, claims y relación de confianza. Esta semana se trabaja a nivel conceptual y de diseño.

4. **[1.2.4 · Configurando aplicaciones en un IDaaS](./04-configurando-apps-idaas.md)**  
   Client ID, redirect URI, cliente público/confidencial, API/resource server, audience, scopes y diseño de app registration.

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
- IDaaS/CIAM;
- diseño de tenant;
- diseño de las aplicaciones/clientes;
- responsabilidades gateway/backend.

### Salida / checkpoint

Debe quedar un diagrama de arquitectura de ReservApp donde pueda explicarse:

```text
usuario → identidad → cliente con token → gateway → reservapp-api
```

junto con scopes, casos 401/403, tenant conceptual, aplicaciones y decisiones de autorización.

Este checkpoint se reutilizará en las experiencias siguientes cuando se incorporen proveedor real, JWT, seguridad del gateway y solución full stack.

## Patrón de trabajo

1. explicación conceptual breve;
2. demostración sobre ReservApp;
3. práctica/laboratorio incremental;
4. evidencia reproducible;
5. defensa técnica;
6. checkpoint para la próxima clase.

## Práctica / laboratorio

- [Laboratorio local · ReservApp identidad](../../labs/identidad-local/)

El laboratorio toma contenidos de los cuatro temas y obliga a aplicarlos sobre el mismo dominio formativo antes de trasladarlos al proveedor cloud real.

## Dinámica viva de la semana

Esta semana queda preparada **Concepto sin marca**.

➡️ [Abrir dinámica](./dinamica-viva-concepto-sin-marca.md)

Durante la primera parte está prohibido responder con nombres de servicios comerciales: primero se diseñan capacidades y responsabilidades; luego se realiza el mapeo al proveedor cloud.

## Planificación por sección

- [DSY1107-002D](./DSY1107-002D.md)
- [DSY1107-003D](./DSY1107-003D.md)

## Qué deben aprender haciendo

Cada grupo debe terminar siendo capaz de:

- cerrar/demostrar v1/v2 + CORS del gateway;
- dibujar actores OAuth2/OIDC correctamente;
- distinguir autenticación de autorización;
- diferenciar ID token y access token;
- explicar IDaaS y CIAM;
- diseñar un tenant coherente;
- identificar cliente, API, Client ID y redirect URI;
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
