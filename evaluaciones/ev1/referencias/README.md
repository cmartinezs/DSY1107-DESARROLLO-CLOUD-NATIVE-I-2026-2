# EV1 · Aplicaciones mínimas de referencia

Este directorio contiene aplicaciones de referencia cuyo único propósito es validar, de extremo a extremo, los starters de seguridad utilizados en EV1.

No constituyen un proyecto funcional que los estudiantes deban copiar como dominio. La funcionalidad de negocio es deliberadamente mínima para concentrar la observación en autenticación, autorización y comunicación segura frontend → API.

## Arquitectura de validación

```text
React ───┐
         ├──► mismo IDaaS
Angular ─┘
              │
              ▼
       mismo Access Token
              │
              ▼
      Spring Boot API
      Resource Server
```

Ambos clientes deben demostrar exactamente los mismos escenarios:

1. recurso público sin autenticación → `200`;
2. recurso protegido sin token → `401`;
3. login mediante Authorization Code + PKCE administrado por MSAL;
4. recurso autenticado con Access Token válido → `200`;
5. operación sin permiso suficiente → `403`;
6. operación con scope/rol requerido → `2xx`;
7. logout y pérdida de acceso a recursos protegidos.

## Directorios

- `backend/`: aplicación Spring Boot mínima compartida.
- `react/`: cliente React mínimo.
- `angular/`: cliente Angular mínimo.
- `VALIDACION-E2E.md`: procedimiento y registro de validación.

## Regla

Los tres proyectos usan configuración externa. No se versionan secretos, tokens, credenciales ni identificadores sensibles que no deban ser públicos.

## Estado de validación

La estructura y código de referencia pueden validarse estáticamente en el repositorio. La validación E2E real contra Microsoft Entra ID requiere valores reales de tenant, aplicaciones registradas, scopes y usuarios de prueba. Esa validación debe registrarse en `VALIDACION-E2E.md` cuando el entorno de identidad esté disponible.