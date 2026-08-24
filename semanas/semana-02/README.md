# Semana 2 · Gestión de APIs + fundamentos de identidad

**Periodo:** 17 al 22 de agosto de 2026  
**Actividad institucional:** cierre 1.1 + inicio 1.2

← [Volver al índice de semanas](../README.md)

## Regla pedagógica de la asignatura

DSY1107 trabaja con **dos verticales separadas**.

### 1. Contenido semanal

El contenido se enseña y practica por sí mismo:

```text
concepto
→ explicación
→ ejemplo pequeño y autocontenido
→ mini ejercicio/laboratorio independiente
→ evidencia de comprensión
```

Los ejemplos no necesitan usar RegistrApp. Se elige el dominio mínimo que permita comprender mejor cada concepto.

### 2. Desafío transversal · RegistrApp

RegistrApp evoluciona durante el semestre, pero **no es el ejemplo conductor del contenido**.

Después de aprender y practicar una competencia, el estudiante la transfiere a RegistrApp y deja un checkpoint reutilizable para la semana siguiente.

→ [Ver definición canónica del desafío transversal](../../docs/DESAFIO-TRANSVERSAL-REGISTRAPP.md)

> **Primero se aprende fuera de RegistrApp. Después se transfiere a RegistrApp.**

## Objetivo semanal

Cerrar los pendientes de gestión de APIs y avanzar hacia autenticación/autorización moderna mediante OAuth2, OpenID Connect e Identity as a Service, **sin depender todavía de Azure ni de otro proveedor específico**.

## Contenidos oficiales y material por tema

Cada contenido mantiene un **Markdown base suficiente para los aprendizajes esperados de la asignatura**. Cuando un tema dispone de material extendido, se ofrece además una carpeta de profundización opcional.

> **Regla de lectura:** estudia primero el `.md` base. Los enlaces “Si quieres profundizar” amplían detalles, ejemplos y decisiones técnicas; no contienen prerrequisitos ocultos para comprender la materia base.

### Finalizar 1.1

- 1.1.1 Conociendo un API Manager.
- 1.1.2 Tutorial Creando Nuestro Primer API Manager.
- 1.1.3 Versionando APIs.
- 1.1.4 Configurando CORS en nuestro API Gateway.

### 1.2 Implementando autenticación con Identity as a Service

1. **[1.2.1 · OAuth2 y OIDC](./01-oauth2-oidc.md)**  
   Autenticación vs autorización, actores, Authorization Code + PKCE, access token vs ID token, scopes, claims, 401/403 y responsabilidades gateway/backend.  
   → **[Si quieres profundizar](./01-oauth2-oidc/README.md)**

2. **[1.2.2 · Identity as a Service y CIAM](./02-idaas-ciam.md)**  
   IdP, IDaaS, IAM vs CIAM, usuarios, aplicaciones, issuer, scopes, claims y separación de responsabilidades.  
   → **[Si quieres profundizar](./02-idaas-ciam/README.md)**

3. **[1.2.3 · Configurando un Tenant](./03-configurando-tenant.md)**  
   Tenant como frontera de confianza: usuarios, aplicaciones, permisos, claims y relaciones de confianza.  
   → **[Si quieres profundizar](./03-configurando-tenant/README.md)**

4. **[1.2.4 · Configurando aplicaciones en un IDaaS](./04-configurando-apps-idaas.md)**  
   Client ID, redirect URI, cliente público/confidencial, API/resource server, audience, scopes y diseño de app registration.  
   → **[Si quieres profundizar](./04-configurando-apps-idaas/README.md)**

> Los puntos 1.2.3 y 1.2.4 se trabajan esta semana **a nivel conceptual y de diseño**. La configuración real en Azure queda para cuando exista el entorno correspondiente.

## Ejemplificación del contenido

Los ejemplos de clase deben permanecer pequeños y autocontenidos. Algunas posibilidades:

- una app de fotos que pide acceso a almacenamiento para explicar OAuth2;
- una app que usa “Continuar con Google” para explicar OIDC;
- una API mínima de productos para explicar scopes y 401/403;
- un cliente SPA ficticio para explicar Authorization Code + PKCE;
- un tenant ficticio sin relación con el desafío para discutir usuarios, clientes y recursos.

No se exige que estos ejemplos compartan dominio entre sí.

## Mini práctica / laboratorio de contenido

La práctica debe permitir comprobar la competencia **antes** de transferirla a RegistrApp.

Por ejemplo:

1. identificar actores de un flujo OAuth2/OIDC independiente;
2. distinguir access token e ID token;
3. resolver casos 401/403;
4. diseñar scopes para una API mínima;
5. justificar cliente público/confidencial y redirect URI.

## Desafío transversal · checkpoint Semana 2

Una vez trabajados los contenidos, cada estudiante/equipo debe transferirlos a RegistrApp.

El checkpoint puede incluir:

- actores OAuth2/OIDC aplicados al desafío;
- decisión sobre cliente, IdP y resource server;
- propuesta de scopes;
- ubicación de controles 401/403;
- diseño conceptual de tenant/aplicaciones cuando corresponda;
- diagrama actualizado;
- breve registro de decisiones y dudas pendientes.

Esto **no reemplaza** los ejemplos ni el laboratorio de contenido: es la aplicación transversal posterior.

## Dinámica viva de la semana

Esta semana queda preparada **Concepto sin marca**.

➡️ [Abrir dinámica](./dinamica-viva-concepto-sin-marca.md)

Durante la primera parte está prohibido responder con nombres de servicios comerciales: primero se diseñan capacidades y responsabilidades; luego se realiza el mapeo al proveedor cloud.

## Planificación por sección

- [DSY1107-002D](./DSY1107-002D.md)
- [DSY1107-003D](./DSY1107-003D.md)

## Criterio técnico

No se enseña “hacer clic en una consola cloud” antes de comprender lo que representa cada elemento. Tenant, aplicación, issuer, client ID, redirect URI, scopes y claims deben tener significado antes de configurarlos en un proveedor.

## Material

- [Biblioteca pública](https://drive.google.com/drive/folders/1UOZMZcEbtfKFq4ygWKj3Yi8VEHkW7hx1?usp=sharing)
- [Semana 02 en Drive](https://drive.google.com/drive/folders/1Bi-w4_ZCWIffbYsgekkcvWE6oDV2rwDQ)
