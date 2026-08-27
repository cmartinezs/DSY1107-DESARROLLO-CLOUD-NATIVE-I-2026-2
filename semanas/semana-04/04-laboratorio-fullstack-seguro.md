# 4 · Laboratorio guiado: flujo Full Stack protegido

## Objetivo

Construir y observar un flujo mínimo donde un frontend obtiene identidad/tokens mediante un IdP y una API Spring Boot rechaza o acepta requests según autenticación y permisos.

El laboratorio puede ejecutarse con el tenant/proveedor disponible en clase. La competencia evaluada es el **flujo y sus responsabilidades**, no memorizar una consola específica.

## Antes de comenzar

Debe existir:

- una SPA registrada como public client;
- una API registrada/expuesta con al menos un scope, por ejemplo `read`;
- redirect URI de laboratorio;
- backend Spring Boot con OAuth2 Resource Server;
- issuer y audience conocidos.

Nunca copiar secretos al repositorio.

## Parte A · Diagrama previo

Antes de configurar, dibuje:

```text
Browser → IdP → Browser → Gateway/API → Spring Security → endpoint
```

Anote en cada flecha qué dato viaja y quién confía en quién.

## Parte B · Frontend

1. configurar MSAL con `clientId`, `authority` y `redirectUri`;
2. iniciar login;
3. solicitar access token para el scope de la API;
4. mostrar solo claims sanitizados para inspección;
5. realizar request con `Authorization: Bearer ...`.

No imprimir el token completo en consola como evidencia persistente.

## Parte C · Backend

1. agregar soporte OAuth2 Resource Server;
2. configurar issuer;
3. validar audience cuando la configuración/proveedor lo requiera explícitamente;
4. proteger `/api/read` con scope `read`;
5. mantener `/public/health` público para contraste.

## Parte D · Matriz de pruebas

Ejecutar y registrar:

| Caso | Token | Permiso | Esperado |
|---|---|---|---:|
| A | ninguno | — | 401 |
| B | inválido/expirado | — | 401 |
| C | válido | sin `read` | 403 |
| D | válido | `read` | 2xx |

Para cada caso registrar request sanitizado, status code y explicación.

## Parte E · Arquitectura segura

Responder:

1. ¿Qué pasaría si se embebe un client secret en JavaScript?
2. ¿Por qué CORS no reemplaza autenticación?
3. ¿Qué control impide aceptar un token válido para otra API?
4. ¿Qué debería ocurrir si el gateway valida el token pero el backend recibe tráfico por otra ruta?
5. ¿Qué campos de observabilidad son útiles sin almacenar credenciales?

## Evidencia de entrega

- diagrama;
- configuración sanitizada;
- matriz de cuatro pruebas;
- captura o transcript de status codes;
- explicación de 401/403;
- breve threat sketch;
- DevLog del trabajo.

## Criterio de éxito

El laboratorio se considera logrado cuando el estudiante puede explicar **por qué** cada request fue aceptado o rechazado y ubicar la responsabilidad técnica correspondiente.
