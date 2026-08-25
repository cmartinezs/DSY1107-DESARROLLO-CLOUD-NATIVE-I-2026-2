# 10 · Evidencias y defensa EV1

## Objetivo

Cerrar la implementación con evidencia trazable a los indicadores institucionales. La defensa debe demostrar funcionamiento y comprensión, no una secuencia de capturas sin contexto.

## Matriz de cobertura institucional

| Indicador evaluado | Peso | Evidencia mínima recomendada |
|---|---:|---|
| rutas del API Manager entre frontend y backend | 13% | rutas + request real desde Angular + integración visible |
| CORS en API Manager | 7% | configuración de origins/methods/headers + preflight + prueba negativa |
| tenant IDaaS | 10% | External tenant + contexto correcto + usuarios/flujo |
| aplicación dentro del tenant | 10% | SPA y API registrations + IDs/redirect URI sanitizados |
| flujo de usuario y tokens | 10% | sign-up/sign-in real + sesión frontend + Access Token |
| Authorization Code + PKCE | 15% | configuración SPA + explicación del flujo + evidencia MSAL/OIDC |
| rutas protegidas mediante JWT | 20% | JWT Authorizer + issuer/audience + scopes + 401/403 |
| rutas llaman backend y devuelven JSON | 15% | Network/curl + respuesta JSON + backend observable |

## Evidencia técnica sugerida

### Arquitectura

Un único diagrama actualizado que muestre:

```text
Usuario
Angular AWS
Microsoft Entra External ID
OAuth2/OIDC + PKCE
ID Token
Access Token
AWS API Gateway
JWT Authorizer
CORS
Spring Boot AWS
reglas de negocio
```

### Identidad

Mostrar sin revelar secretos:

```text
tenant
user flow
SPA registration
API registration
redirect URI
scopes
roles (si aplica)
```

### Token

Mostrar claims sanitizados:

```text
iss
aud
sub
exp
scp/roles
```

Explicar por qué leerlos no reemplaza la validación criptográfica.

### API Gateway

Mostrar:

```text
Invoke URL
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
request sin permiso → rechazo/403
CORS válido desde frontend
respuesta JSON en UI
```

## Guion técnico de 5–10 minutos

No memorizar texto. Mantener este hilo:

```text
1. qué problema resuelve la arquitectura
2. componentes y responsabilidades
3. login OIDC / Authorization Code + PKCE
4. Access Token JWT
5. API Gateway valida entrada
6. Spring aplica seguridad/negocio
7. CORS permite al frontend autorizado consumir
8. prueba positiva
9. prueba negativa
10. conclusión
```

## Preguntas que cada integrante debe poder responder

1. ¿OAuth2 y OIDC resuelven exactamente lo mismo?
2. ¿Por qué Angular no guarda un client secret?
3. ¿Qué protege PKCE?
4. ¿Para qué sirve el ID Token?
5. ¿Para qué sirve el Access Token?
6. ¿Por qué `aud` debe representar el recurso esperado?
7. ¿Qué ocurre cuando `exp` está en el pasado?
8. ¿Cómo obtiene el Gateway la clave pública para validar un JWT?
9. ¿Qué diferencia hay entre scope y role?
10. ¿Qué diferencia hay entre 401 y 403?
11. ¿Qué seguridad pertenece al Gateway y cuál al backend?
12. ¿Por qué Postman funcionando no demuestra que CORS esté correcto?
13. ¿Qué es un preflight?
14. ¿Por qué no se debe configurar CORS con `*` por defecto?
15. ¿Por qué el frontend consume API Gateway y no debería depender directamente del backend?

## Entrega en GitHub

El repositorio de evaluación debe permitir reconstruir el sistema sin depender del computador original. Incluir como mínimo:

```text
README.md
frontend/
backend/
docs/
  arquitectura.md
  evidencias.md
.gitignore
```

El README debe indicar:

```text
prerequisitos
cómo ejecutar localmente
variables públicas/configuración requerida
qué valores NO se versionan
URLs cloud sanitizadas cuando corresponda
arquitectura
flujo de autenticación
cómo reproducir pruebas
```

## Seguridad de evidencias

Antes de subir capturas o archivos comprobar que no aparezcan:

- contraseñas;
- client secrets;
- access/refresh tokens completos vigentes;
- AWS access keys;
- cookies de sesión;
- claves privadas.

## Checklist final

- [ ] frontend cloud abre por HTTPS;
- [ ] backend cloud está operativo;
- [ ] frontend inicia sesión con Entra External ID;
- [ ] se usa Authorization Code + PKCE;
- [ ] se obtiene Access Token para la API correcta;
- [ ] API Gateway protege rutas con JWT;
- [ ] issuer y audience son correctos;
- [ ] scopes afectan autorización;
- [ ] backend mantiene validación y reglas de negocio;
- [ ] CORS permite origen local/cloud explícito;
- [ ] preflight fue observado;
- [ ] 401 fue provocado y explicado;
- [ ] 403/rechazo por permiso fue provocado y explicado;
- [ ] frontend consume exclusivamente la URL del Gateway;
- [ ] las respuestas JSON correctas llegan a la UI;
- [ ] documentación permite reproducir el trabajo;
- [ ] no hay secretos versionados.

## Cierre

La EV1 no queda terminada cuando “la aplicación abre”. Queda terminada cuando el grupo puede **demostrar, reproducir y explicar** el flujo completo desde el navegador hasta el backend y justificar por qué cada control existe.