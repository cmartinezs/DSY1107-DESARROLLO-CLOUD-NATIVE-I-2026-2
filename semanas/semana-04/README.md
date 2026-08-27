# Semana 4 · MSAL, Spring Security y arquitectura Full Stack segura

**Periodo:** 31 de agosto al 5 de septiembre de 2026  
**Asignatura:** DSY1107 Desarrollo Cloud Native I

← [Volver al índice](../README.md)

## Regla de trabajo

Esta carpeta contiene únicamente contenido curricular, ejemplos y práctica de Semana 4. RegistrApp sigue siendo una vertical independiente en [`proyecto-formativo/`](../../proyecto-formativo/).

> Primero se comprende el patrón con un caso independiente. Después se transfiere la competencia a RegistrApp si existe evidencia suficiente.

## Propósito

Integrar autenticación/autorización de extremo a extremo en una solución Full Stack segura, conectando frontend, proveedor de identidad, API Gateway y backend protegido.

```text
usuario
→ frontend
→ IdP / Authorization Code + PKCE
→ access token
→ API Gateway
→ Resource Server Spring Security
→ autorización por scopes/claims
```

## Contenidos oficiales

- 1.3.1 Conociendo MSAL.
- 1.3.2 Configurar MSAL en el frontend.
- 1.3.3 Configurar Spring Security en el Backend.
- 1.3.4 Arquitecturas seguras en la nube.

Durante esta semana el docente entrega además las instrucciones de la Evaluación Parcial 1.

## Material

1. [MSAL y autenticación de frontend](./01-msal-frontend.md)
2. [Spring Security como Resource Server](./02-spring-security-backend.md)
3. [Arquitectura Full Stack segura](./03-arquitectura-segura-cloud.md)
4. [Laboratorio guiado: flujo Full Stack protegido](./04-laboratorio-fullstack-seguro.md)

## Resultado técnico esperado

Al finalizar el contenido, el estudiante debe poder:

- explicar Authorization Code + PKCE y por qué es apropiado para SPA;
- distinguir ID token y access token;
- configurar conceptualmente MSAL sin secretos embebidos en frontend;
- proteger endpoints con Spring Security Resource Server;
- interpretar 401 y 403 en el flujo completo;
- ubicar responsabilidades entre frontend, IdP, gateway y backend;
- justificar scopes, audience, issuer y mínimo privilegio;
- reconocer riesgos de CORS, almacenamiento de tokens, exposición de secretos y logging inseguro.

## Evidencia mínima

- diagrama del flujo de autenticación y acceso a API;
- configuración reproducible o pseudoconfiguración equivalente;
- request autorizado y al menos un caso 401/403;
- explicación de qué componente valida cada condición;
- breve análisis de amenazas y controles.

## Transferencia a RegistrApp

Solo después de comprender el contenido:

→ [Checkpoint transversal Semana 4](../../proyecto-formativo/semana-04/README.md)

## Planificación por sección

- [DSY1107-002D](./DSY1107-002D.md)
- [DSY1107-003D](./DSY1107-003D.md)

Los documentos de sección declaran planificación y punto de arranque. El avance real se actualiza únicamente con evidencia de clase.

## Seguridad

Nunca versionar client secrets, contraseñas, access tokens reutilizables, certificados privados ni credenciales cloud. Para demos, usar datos sintéticos, cuentas de laboratorio y secretos fuera del repositorio.
