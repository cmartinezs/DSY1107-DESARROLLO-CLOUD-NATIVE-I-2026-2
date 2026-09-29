# Checklist técnico · Parcial 1

Antes de la defensa verificar:

- [ ] frontend ejecuta;
- [ ] login funciona;
- [ ] Access Token puede observarse de forma segura;
- [ ] audience corresponde a la API propia;
- [ ] gateway enruta;
- [ ] backend protegido responde;
- [ ] al menos un caso 401 está demostrado;
- [ ] al menos un caso 403 está demostrado;
- [ ] existe un caso 2xx válido;
- [ ] secretos/tokens no están versionados;
- [ ] diagrama coincide con implementación;
- [ ] commits/evidencia permiten reconstruir el trabajo.

## Regla

No ocultar fallas con capturas antiguas. Si un componente no funciona, documentar el último estado reproducible y la causa conocida.


## Checklist por capas

### Frontend

- [ ] la aplicación inicia de forma reproducible;
- [ ] no contiene client secrets;
- [ ] obtiene el Access Token correcto;
- [ ] envía Authorization: Bearer;
- [ ] no registra tokens completos.

### Identity Provider

- [ ] tenant/proyecto correcto;
- [ ] cliente registrado;
- [ ] API/resource configurado;
- [ ] scope o permiso definido;
- [ ] redirect URI coherente.

### Gateway

- [ ] integración/routing operativo;
- [ ] política/authorizer activo cuando corresponde;
- [ ] errores distinguibles de los del backend;
- [ ] configuración sanitizada.

### Backend

- [ ] Resource Server activo;
- [ ] issuer correcto;
- [ ] audience validada;
- [ ] scopes/authorities coherentes;
- [ ] endpoint público y protegido distinguibles.

## Checklist de pruebas

- [ ] sin token;
- [ ] token incorrecto;
- [ ] token válido sin permiso cuando aplique;
- [ ] token válido con permiso;
- [ ] endpoint esperado produce 2xx;
- [ ] logs permiten identificar la frontera.

## Checklist de explicación

Cada integrante debería poder responder:

- ¿quién autentica?;
- ¿qué token usa la API?;
- ¿qué significa audience?;
- ¿qué diferencia 401 de 403?;
- ¿qué hace Gateway?;
- ¿qué conserva Spring Security?;
- ¿dónde están las reglas de negocio?

## Regla de preparación

Si un ítem no está verde, no esconderlo: documentar estado, causa conocida y plan de corrección.
