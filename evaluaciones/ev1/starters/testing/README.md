# EV1 · Kit de integración y pruebas

Este directorio convierte la configuración de seguridad en **evidencia reproducible**.

El objetivo no es solo comprobar que “funciona”, sino demostrar de forma controlada qué ocurre cuando cambia el estado de autenticación o autorización.

## Material

1. [Matriz 200 / 401 / 403](./matriz-200-401-403.md)
2. [`requests.http`](./requests.http) — requests reutilizables desde IntelliJ IDEA, VS Code REST Client u otra herramienta compatible.
3. [Inspección segura de claims](./inspeccion-claims.md)
4. [Checklist de diagnóstico](./checklist-diagnostico.md)

## Precondiciones

Antes de ejecutar este kit:

- el backend Spring Boot debe incorporar el starter `spring-resource-server/`;
- `JWT_ISSUER` debe apuntar al issuer correcto;
- `JWT_AUDIENCE` debe coincidir con la audiencia de la API;
- el frontend o herramienta utilizada debe poder obtener un **Access Token** válido para la API;
- deben existir usuarios/permisos suficientes para demostrar al menos un acceso permitido y uno denegado por autorización.

## Endpoints de referencia

El starter Spring incluye endpoints temporales para comprobar la seguridad antes de adaptarla al dominio real:

```text
GET  /public/health
GET  /api/profile
POST /api/write/example
GET  /api/admin/example
```

Cuando el proyecto ya posea endpoints equivalentes, estos ejemplos pueden eliminarse y la matriz debe trasladarse al dominio real.

## Regla de evidencia

Una captura de código no demuestra por sí sola que la seguridad funciona.

La evidencia debe relacionar:

```text
precondición
→ request
→ credenciales/permisos presentes
→ status HTTP observado
→ explicación del resultado
```

## Token y privacidad

Nunca incorporar un Access Token completo en:

- README;
- screenshots entregadas;
- commits;
- issues;
- documentos compartidos;
- grabaciones donde pueda copiarse el valor.

Los tokens son credenciales temporales. Para evidencia basta mostrar claims relevantes y ocultar cualquier valor que permita reutilizar el token.

## Resultado esperado

Al completar este kit debes poder explicar con ejemplos reales la diferencia entre:

- recurso público;
- usuario no autenticado;
- token inválido;
- usuario autenticado;
- usuario autenticado sin permiso;
- usuario autenticado con el permiso requerido.
