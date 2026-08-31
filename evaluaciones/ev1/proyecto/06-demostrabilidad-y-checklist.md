# 06 · Demostrabilidad y checklist de preparación

## Regla de demostrabilidad

Una característica técnica no se considera correctamente implementada solo porque aparezca en el código.

El equipo debe poder **provocar, observar y explicar** su comportamiento.

## Matriz mínima de seguridad

La arquitectura debe permitir demostrar escenarios equivalentes a:

| Escenario | Resultado esperado |
|---|---:|
| Request público | 2xx |
| Request protegido sin token | 401 |
| Token inválido/no aceptable | 401 |
| Token válido sin permiso suficiente | 403 |
| Token válido con permiso correcto | 2xx |

Los endpoints concretos dependen del dominio del proyecto.

## Checklist antes de aplicar la configuración cloud de EV1

### Arquitectura

- [ ] Existe un frontend SPA en Angular o React.
- [ ] Existe un backend Java + Spring Boot separado.
- [ ] Frontend y backend se comunican mediante HTTP/REST.
- [ ] Existe persistencia real de información.
- [ ] El dominio contiene al menos dos conceptos o entidades relacionadas.

### Funcionalidad

- [ ] Se puede consultar una colección de recursos.
- [ ] Se puede visualizar información relevante de un recurso.
- [ ] Existe al menos una operación que modifica información.
- [ ] Hay suficiente diferencia funcional para definir distintos niveles de acceso.

### API

- [ ] Existe al menos una operación pública.
- [ ] Existe al menos una operación de lectura protegible.
- [ ] Existe al menos una operación de modificación protegible.
- [ ] Métodos y códigos HTTP son coherentes.
- [ ] CORS está configurado de forma controlada cuando corresponde.

### Seguridad y evolución

- [ ] La arquitectura permite incorporar OAuth 2.0 / OIDC.
- [ ] La SPA puede evolucionar a Authorization Code + PKCE.
- [ ] Es posible definir al menos dos scopes/roles/permisos diferentes.
- [ ] El backend puede proteger recursos mediante Spring Security.
- [ ] La URL de API y otros valores de ambiente son configurables.
- [ ] La solución puede interponer un API Manager/Gateway sin rehacer frontend y backend.
- [ ] No hay secretos, tokens reutilizables ni credenciales versionadas.

## Resultado esperado

Si todos los puntos anteriores pueden demostrarse, el proyecto posee la base técnica mínima necesaria para aplicar de forma efectiva los contenidos cloud y de seguridad asociados a EV1.

El objetivo no es maximizar funcionalidades de negocio, sino asegurar que la aplicación permita **implementar, observar, probar y explicar** los conceptos evaluados.