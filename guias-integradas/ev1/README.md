# EV1 · Guía integrada de implementación real

Esta guía conecta, en una sola ruta reproducible, los contenidos que la EV1 exige demostrar: frontend + backend, API Manager/Gateway, CORS, Identity as a Service, OAuth2, OpenID Connect, Authorization Code + PKCE, JWT, scopes/roles y despliegue cloud.

> Esta vertical no es RegistrApp. RegistrApp permanece como proyecto formativo transversal. Aquí se construye una **aplicación técnica de referencia** para demostrar el encargo institucional sin dependencias ocultas.

## Resultado final

Al completar la guía debe existir este flujo real:

```mermaid
flowchart LR
    U[Usuario] --> F[Angular SPA]
    F -->|Authorization Code + PKCE| E[Microsoft Entra External ID]
    E -->|ID Token + Access Token| F
    F -->|HTTPS + Bearer Access Token| G[AWS API Gateway]
    G -->|JWT válido + scope| B[Spring Boot en AWS]
    B --> D[(Datos demo)]
```

El estudiante debe poder demostrar y explicar cada salto.

## Aplicación de referencia: CloudTasks

CloudTasks es intencionalmente pequeña. Solo permite iniciar sesión, consultar tareas, crear una tarea, eliminar una tarea propia y consultar información básica de identidad. El dominio es irrelevante; la arquitectura y la seguridad son lo evaluado.

### Endpoints mínimos

| Método | Ruta | Requisito | Propósito didáctico |
|---|---|---|---|
| GET | `/api/public/health` | público | comprobar backend |
| GET | `/api/me` | token válido | inspeccionar identidad |
| GET | `/api/tasks` | `tasks.read` | autorización por scope |
| POST | `/api/tasks` | `tasks.write` | autorización por scope |
| DELETE | `/api/tasks/{id}` | `tasks.write` + ownership | autorización de negocio |
| GET | `/api/admin/stats` | rol `Admin` | rol vs scope |

## Orden obligatorio

La guía está diseñada para que ningún paso use un artefacto inexistente.

1. [00 · Mapa EV1 y prerequisitos](./00-mapa-y-prerequisitos.md)
2. [01 · Crear y ejecutar CloudTasks local](./01-cloudtasks-local.md)
3. [02 · Crear Microsoft Entra External ID](./02-entra-external-id.md)
4. [03 · Integrar Angular con MSAL y PKCE](./03-angular-msal.md)
5. [04 · JWT, scopes, roles y Spring Security](./04-jwt-y-backend.md)
6. [05 · Desplegar backend en AWS](./05-aws-backend.md)
7. [06 · Crear AWS API Gateway + JWT Authorizer](./06-api-gateway-jwt.md)
8. [07 · Configurar CORS con URLs que ya existen](./07-cors.md)
9. [08 · Desplegar frontend e integrar extremo a extremo](./08-frontend-cloud-e2e.md)
10. [09 · Pruebas negativas y troubleshooting](./09-pruebas-y-troubleshooting.md)
11. [10 · Evidencias y defensa EV1](./10-evidencias-y-defensa.md)

## Regla de avance

No se continúa porque “parece estar bien”. Cada etapa termina con una **puerta de validación**. Si la validación falla, se corrige antes de seguir.

## Convención de valores

En toda la guía:

```text
<VALOR_ASI>
```

significa que el estudiante debe reemplazarlo por un valor real obtenido en un paso anterior.

Nunca versionar:

- contraseñas;
- client secrets;
- access tokens reutilizables;
- claves AWS;
- credenciales del tenant.

Los IDs públicos (`client_id`, tenant id, audience pública) sí pueden documentarse cuando corresponda, pero la guía evita exigir que se suban si no son necesarios.

## Correspondencia con el contenido existente

Antes o durante cada implementación se enlazan los contenidos canónicos existentes de las Semanas 1–3. Esta guía no vuelve a escribir teoría de OAuth2, CORS o JWT: muestra exactamente **dónde aparece en una solución real**.
