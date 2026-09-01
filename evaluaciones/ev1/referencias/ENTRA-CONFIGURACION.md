# Configuración de Microsoft Entra ID para validar EV1 end-to-end

Este documento prepara el entorno real necesario para validar las aplicaciones de referencia React y Angular contra el mismo backend Spring Boot Resource Server.

## Objetivo

Obtener una configuración de Microsoft Entra ID que permita demostrar el flujo completo:

```text
SPA React / Angular
  ↓ Authorization Code + PKCE
Microsoft Entra ID
  ↓ Access Token
Spring Boot Resource Server
  ↓
200 / 401 / 403
```

## Importante

La configuración del tenant y de los App Registrations requiere permisos administrativos sobre Microsoft Entra ID. El repositorio no contiene secretos ni credenciales del tenant.

## Diseño de registros

Se recomienda utilizar tres App Registrations separados:

```text
DSY1107-EV1-API
DSY1107-EV1-REACT
DSY1107-EV1-ANGULAR
```

Esto mantiene explícita la separación entre recurso protegido y clientes SPA.

---

# 1. Registrar la API

Crear App Registration:

```text
Nombre: DSY1107-EV1-API
Tipo de cuentas: solo cuentas del directorio organizacional usado para la prueba
```

Registrar los siguientes valores:

```text
API_CLIENT_ID=<Application (client) ID>
TENANT_ID=<Directory (tenant) ID>
```

## Exponer la API

En `Expose an API`:

1. Definir Application ID URI usando el valor recomendado:

```text
api://<API_CLIENT_ID>
```

2. Crear scope delegado:

```text
Nombre: recurso.read
URI completo: api://<API_CLIENT_ID>/recurso.read
```

3. Crear scope delegado:

```text
Nombre: recurso.write
URI completo: api://<API_CLIENT_ID>/recurso.write
```

Los nombres pueden adaptarse más adelante al dominio real del proyecto. Para la referencia técnica se mantienen estos nombres porque coinciden con las reglas del backend.

## App role para demostración 403/200 por rol

Crear un App Role:

```text
Display name: Admin
Value: ADMIN
Allowed member types: Users/Groups
Enabled: true
```

Este valor terminará apareciendo como `roles: ["ADMIN"]` en el Access Token cuando el usuario tenga el rol asignado.

---

# 2. Registrar SPA React

Crear App Registration:

```text
Nombre: DSY1107-EV1-REACT
Tipo de cuentas: mismo tenant que la API
```

Registrar:

```text
REACT_CLIENT_ID=<Application (client) ID>
```

En `Authentication` → `Add a platform` → `Single-page application` registrar la URI exacta usada localmente por Vite, por ejemplo:

```text
http://localhost:5173
```

No crear `client_secret`. Una SPA es un cliente público.

## API Permissions

Agregar permisos de `My APIs` → `DSY1107-EV1-API`:

```text
recurso.read
recurso.write
```

Aplicar consentimiento administrativo cuando la política del tenant lo requiera.

---

# 3. Registrar SPA Angular

Crear App Registration:

```text
Nombre: DSY1107-EV1-ANGULAR
Tipo de cuentas: mismo tenant que la API
```

Registrar:

```text
ANGULAR_CLIENT_ID=<Application (client) ID>
```

En `Authentication` → `Add a platform` → `Single-page application` registrar:

```text
http://localhost:4200
```

No crear `client_secret`.

## API Permissions

Agregar los mismos permisos delegados de la API:

```text
recurso.read
recurso.write
```

Aplicar consentimiento administrativo cuando corresponda.

---

# 4. Asignar usuario de prueba

Para validar tanto 403 como 200 por rol se requieren, idealmente, dos estados observables:

## Usuario normal

Usuario autenticado sin App Role `ADMIN`.

Debe permitir:

```text
/api/profile → 200
/api/admin/example → 403
```

## Usuario admin

Asignar el App Role `ADMIN` de la API al usuario/grupo correspondiente desde Enterprise Applications.

Debe permitir:

```text
/api/admin/example → 200
```

La asignación de scopes delegados y App Roles son mecanismos distintos. El scope controla permisos delegados solicitados por la SPA; el rol permite demostrar autorización basada en `roles`.

---

# 5. Configurar Spring Boot

El issuer debe corresponder al tenant utilizado. Para tokens v2 del Microsoft identity platform, el patrón esperado es:

```text
https://login.microsoftonline.com/<TENANT_ID>/v2.0
```

Configurar el backend de referencia mediante variables de entorno:

```text
JWT_ISSUER=https://login.microsoftonline.com/<TENANT_ID>/v2.0
JWT_AUDIENCE=<API_CLIENT_ID>
ALLOWED_ORIGINS=http://localhost:5173,http://localhost:4200
```

La validación de audience debe compararse con el valor real del claim `aud` del Access Token emitido para la API. Si el tenant emite `api://<API_CLIENT_ID>` como audiencia en vez del GUID, ajustar `JWT_AUDIENCE` al valor observado y documentado.

No modificar esta configuración por intuición: primero inspeccionar el claim `aud` del token emitido.

---

# 6. Configurar React

Crear `.env.local` a partir del ejemplo del proyecto de referencia:

```text
VITE_ENTRA_CLIENT_ID=<REACT_CLIENT_ID>
VITE_ENTRA_TENANT_ID=<TENANT_ID>
VITE_API_SCOPE=api://<API_CLIENT_ID>/recurso.write
VITE_API_BASE_URL=http://localhost:8080
```

El scope `recurso.write` permite probar tanto endpoints autenticados generales como el endpoint de escritura.

Si se quiere demostrar consentimiento mínimo por separado, cambiar temporalmente a `recurso.read` y observar el comportamiento del endpoint que exige `recurso.write`.

---

# 7. Configurar Angular

Actualizar la configuración de environment de la referencia:

```text
clientId: <ANGULAR_CLIENT_ID>
tenantId: <TENANT_ID>
apiScope: api://<API_CLIENT_ID>/recurso.write
apiBaseUrl: http://localhost:8080
```

React y Angular deben apuntar al mismo tenant, misma API, mismos scopes y mismo backend.

---

# 8. Orden de ejecución

1. Iniciar Spring Boot en `localhost:8080`.
2. Validar `/public/health` sin token.
3. Iniciar React en `localhost:5173`.
4. Ejecutar login y comprobar `/api/profile`.
5. Comprobar `/api/write/example`.
6. Comprobar `/api/admin/example` con usuario sin rol → `403`.
7. Asignar `ADMIN` y volver a autenticar/adquirir token.
8. Comprobar `/api/admin/example` → `200`.
9. Repetir los mismos pasos con Angular en `localhost:4200`.
10. Registrar resultados en `VALIDACION-E2E.md`.

---

# 9. Valores que deben quedar registrados

No guardar secretos. Sí se pueden registrar identificadores/configuración pública:

```text
TENANT_ID=
API_CLIENT_ID=
API_APPLICATION_ID_URI=
REACT_CLIENT_ID=
ANGULAR_CLIENT_ID=
JWT_ISSUER=
JWT_AUDIENCE=
READ_SCOPE=
WRITE_SCOPE=
ADMIN_ROLE=ADMIN
REACT_REDIRECT_URI=http://localhost:5173
ANGULAR_REDIRECT_URI=http://localhost:4200
```

## Nunca registrar

- contraseñas;
- sesiones;
- Access Tokens completos;
- refresh tokens;
- client secrets;
- credenciales de administrador.

---

# 10. Gate de configuración

La configuración Entra se considera lista cuando:

- existen los tres App Registrations;
- API expone scopes `recurso.read` y `recurso.write`;
- API define App Role `ADMIN`;
- React y Angular están registrados como SPA con redirect URIs exactos;
- ambos clientes tienen permisos delegados sobre la API;
- existe al menos un usuario de prueba sin rol y uno con rol, o un usuario cuya asignación pueda alternarse;
- se conoce el `issuer` real;
- se conoce el `aud` real del Access Token;
- no existe ningún `client_secret` en los clientes SPA.

Solo entonces se ejecuta el protocolo de `VALIDACION-E2E.md` y se puede cerrar definitivamente la Etapa 6.
