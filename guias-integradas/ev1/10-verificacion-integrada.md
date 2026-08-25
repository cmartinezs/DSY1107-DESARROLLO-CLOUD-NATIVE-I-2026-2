# 10 · Verificación integrada de la guía

## Objetivo

Cerrar CloudTasks comprobando que los componentes funcionan juntos y que el estudiante puede explicar el flujo completo sin depender de una secuencia memorizada.

## Cobertura técnica

La verificación integrada debe recorrer:

```text
Angular
→ Entra External ID
→ Authorization Code + PKCE
→ Access Token
→ API Gateway
→ JWT Authorizer
→ CORS
→ Spring Security
→ reglas de negocio
→ respuesta JSON
```

## Comprobaciones recomendadas

### Arquitectura

Explicar qué responsabilidad tiene cada componente y por qué existe.

### Identidad

Comprobar y explicar:

```text
tenant
user flow
SPA registration
API registration
redirect URI
scopes
roles si aplica
```

### Token

Inspeccionar de forma sanitizada:

```text
iss
aud
sub
exp
scp
roles si existen
```

Explicar por qué leer el payload no reemplaza la validación criptográfica.

### API Gateway

Comprobar:

```text
rutas
integración
JWT Authorizer
issuer
audience
scopes por ruta
CORS
```

### Funcionamiento

Ejecutar al menos:

```text
login
GET permitido
POST permitido
request sin token → 401
request sin permiso → rechazo
CORS válido desde navegador
respuesta JSON visible en la UI
```

## Preguntas de autoverificación

1. ¿OAuth2 y OIDC resuelven exactamente lo mismo?
2. ¿Por qué Angular no guarda un client secret?
3. ¿Qué protege PKCE?
4. ¿Para qué sirve el ID Token?
5. ¿Para qué sirve el Access Token?
6. ¿Por qué `aud` debe representar el recurso esperado?
7. ¿Qué ocurre cuando `exp` está en el pasado?
8. ¿Cómo obtiene el Gateway las claves públicas para validar un JWT?
9. ¿Qué diferencia hay entre scope y role?
10. ¿Qué diferencia hay entre 401 y 403?
11. ¿Qué seguridad pertenece al Gateway y cuál al backend?
12. ¿Por qué Postman funcionando no demuestra que CORS esté correcto?
13. ¿Qué es un preflight?
14. ¿Por qué no conviene esconder CORS con `*`?
15. ¿Por qué el frontend consume API Gateway y no el backend directamente?

## Reproducibilidad de la práctica

El trabajo de la guía debería quedar entendible desde:

```text
guia/ev1/
├── README.md
├── frontend/
├── backend/
└── docs/
```

El README de la práctica puede indicar:

```text
prerrequisitos
cómo ejecutar localmente
valores públicos/configuración requerida
qué valores no se versionan
URLs utilizadas
arquitectura
flujo de autenticación
cómo repetir las pruebas
```

## Seguridad

No registrar ni subir:

- contraseñas;
- client secrets;
- Access/Refresh Tokens completos vigentes;
- AWS access keys;
- cookies de sesión;
- claves privadas.

## Checkpoint 10

- [ ] frontend cloud abre por HTTPS;
- [ ] backend cloud está operativo;
- [ ] login External ID funciona;
- [ ] Authorization Code + PKCE puede explicarse;
- [ ] se obtiene Access Token para la API correcta;
- [ ] API Gateway protege rutas con JWT;
- [ ] issuer y audience son correctos;
- [ ] scopes afectan autorización;
- [ ] backend conserva validación y reglas de negocio;
- [ ] CORS permite orígenes explícitos;
- [ ] preflight fue observado;
- [ ] 401 fue provocado y explicado;
- [ ] un rechazo de autorización fue provocado y explicado;
- [ ] frontend consume la URL del Gateway;
- [ ] las respuestas JSON correctas llegan a la UI;
- [ ] otra persona puede reconstruir la práctica desde GitHub;
- [ ] no hay secretos versionados.
