# Checklist de diagnóstico · EV1

Usa este orden cuando una request protegida no produzca el resultado esperado.

La regla es diagnosticar por capas y cambiar **una variable a la vez**.

## 1. Frontend / cliente

- [ ] ¿El usuario inició sesión correctamente?
- [ ] ¿Se está solicitando un **Access Token** para la API correcta?
- [ ] ¿Se está enviando `Authorization: Bearer <token>`?
- [ ] ¿El frontend está usando la URL correcta de la API/Gateway?
- [ ] ¿El token enviado corresponde al usuario/permisos que quieres probar?
- [ ] ¿CORS permite el origen real del frontend?

## 2. Token

- [ ] ¿El token puede decodificarse como JWT?
- [ ] ¿`iss` coincide con `JWT_ISSUER`?
- [ ] ¿`aud` contiene el valor esperado por `JWT_AUDIENCE`?
- [ ] ¿`exp` sigue vigente?
- [ ] ¿El token contiene `scp`/`scope` o `roles` según la regla que estás probando?
- [ ] ¿Estás usando Access Token y no ID Token?

## 3. Spring Resource Server

- [ ] ¿Está incluida la dependencia `spring-boot-starter-oauth2-resource-server`?
- [ ] ¿`SecurityFilterChain` está cargando?
- [ ] ¿El backend puede resolver metadata/JWKs del issuer?
- [ ] ¿`AudienceValidator` recibe la audience correcta?
- [ ] ¿`AuthoritiesConverter` transforma los claims esperados?
- [ ] ¿La ruta está asociada a la regla correcta?

Ejemplos:

```text
/api/write/** → SCOPE_recurso.write
/api/admin/** → ROLE_ADMIN
```

## 4. API Manager / Gateway

Cuando exista una capa Gateway entre frontend y backend:

- [ ] ¿La ruta externa apunta al backend correcto?
- [ ] ¿El header `Authorization` llega al backend?
- [ ] ¿El Gateway no está eliminando o reemplazando accidentalmente el Bearer Token?
- [ ] ¿Las políticas aplicadas corresponden a la API correcta?
- [ ] ¿CORS está configurado en la capa que realmente responde al navegador?

Para aislar un problema, compara cuando sea posible:

```text
cliente → backend directo
```

versus

```text
cliente → gateway → backend
```

La comparación permite determinar si el fallo aparece antes o después del Gateway.

## 5. Interpretar el status antes de cambiar código

### Recibes 401

Investiga primero autenticación/validación:

- token ausente;
- token corrupto;
- token expirado;
- issuer incorrecto;
- audience incorrecta;
- firma no verificable.

No empieces modificando roles o scopes si el token ni siquiera fue autenticado.

### Recibes 403

La autenticación ya fue aceptada. Investiga autorización:

- scope ausente;
- rol ausente;
- claim con nombre distinto;
- conversión de authorities incorrecta;
- regla de ruta distinta de la esperada.

### Recibes 404

Antes de revisar seguridad confirma:

- ruta correcta;
- método HTTP correcto;
- contexto/base URL correcto;
- routing del Gateway correcto.

### Recibes error CORS en navegador

Comprueba el mismo endpoint con `requests.http` o una herramienta HTTP no sujeta a CORS.

Si funciona fuera del navegador, probablemente el problema está en política CORS/origin y no en JWT.

## 6. Evidencia mínima de diagnóstico

Cuando informes un problema, registra:

```text
endpoint:
método:
status observado:
status esperado:
con/sin token:
issuer observado:
audience observada:
scopes/roles relevantes:
llamada directa o vía gateway:
```

No pegues el token completo.

## Cierre

No consideres la integración terminada hasta poder reproducir deliberadamente:

- [ ] 200 público;
- [ ] 401 sin token;
- [ ] 401 con token inválido;
- [ ] 200 con token válido en recurso autenticado;
- [ ] 403 con token válido sin permiso;
- [ ] 2xx con token válido y permiso suficiente.
