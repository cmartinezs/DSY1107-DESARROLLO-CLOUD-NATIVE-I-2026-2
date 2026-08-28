# Mapeo curricular oficial · Semana 4

**Periodo:** 31 de agosto al 5 de septiembre de 2026  
**Asignatura:** DSY1107 Desarrollo Cloud Native I

← [Volver a Semana 4](./README.md)

## Propósito

Este documento concilia el comunicado oficial de contenidos de Semana 4 con la estructura pedagógica del repositorio. La semana **no comienza exclusivamente en 1.3**: primero debe finalizar formalmente el bloque 1.2 pendiente y luego continuar con la integración Full Stack.

## Cobertura oficial

### Finalizar · Implementando autenticación con Identity as a Service

| Código | Contenido | Hogar de estudio |
|---|---|---|
| **1.2.5** | Creando una aplicación para usuarios externos | [Semana 3 · Seguridad de API y CIAM](../semana-03/02-seguridad-api.md) |
| **1.2.6** | Integrando Seguridad en nuestro API Manager | [Semana 3 · Seguridad de API](../semana-03/02-seguridad-api.md) |
| **1.2.7** | Introducción a JWT y Claims | [Semana 3 · JWT y Claims](../semana-03/01-jwt-claims.md) |
| **1.2.8** | Decodificando tokens JWT | [Semana 3 · JWT y Claims](../semana-03/01-jwt-claims.md) |

> Estos recursos continúan siendo su fuente canónica. Semana 4 los declara como **cierre curricular pendiente** sin duplicar el contenido.

### Continuar · API Manager + Identity as a Service en una solución Full Stack

| Código | Contenido | Hogar de estudio |
|---|---|---|
| **1.3.1** | Conociendo MSAL | [MSAL y autenticación de frontend](./01-msal-frontend.md) |
| **1.3.2** | Configurar MSAL en el frontend | [MSAL y autenticación de frontend](./01-msal-frontend.md) |
| **1.3.3** | Configurar Spring Security en el Backend | [Spring Security como Resource Server](./02-spring-security-backend.md) |
| **1.3.4** | Arquitecturas seguras en la nube | [Arquitectura Full Stack segura](./03-arquitectura-segura-cloud.md) |

## Secuencia pedagógica recomendada

```text
cerrar usuarios externos + seguridad API Manager
→ cerrar JWT + claims + decode vs verify
→ Authorization Code + PKCE
→ MSAL
→ access token para la API
→ API Manager / Gateway
→ Spring Security Resource Server
→ scopes / claims / 401 / 403
→ arquitectura segura en la nube
```

## Regla de continuidad

El cierre de 1.2.5–1.2.8 debe realizarse desde el **último checkpoint real de cada sección**. Que el material exista en el repositorio no significa que ya haya sido impartido.

## Evaluación Parcial 1

Durante Semana 4 se deben entregar y explicar las indicaciones de la Evaluación Parcial 1.

→ [Orientaciones de Evaluación Parcial 1](./05-evaluacion-parcial-1.md)
