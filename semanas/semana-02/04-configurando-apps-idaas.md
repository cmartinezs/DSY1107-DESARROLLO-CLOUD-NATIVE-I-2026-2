# 1.2.4 · Configurando aplicaciones en un IDaaS

## Objetivo

Comprender qué significa registrar una aplicación en un servicio de identidad y diseñar la configuración de ReservApp sin depender todavía de una consola específica.

## 1. ¿Por qué registrar una aplicación?

El proveedor de identidad necesita saber **qué aplicación está participando en el flujo**, qué URLs son válidas, qué permisos puede solicitar y para qué recurso se emitirán tokens.

En ReservApp distinguiremos dos piezas:

```text
reservapp-web  → cliente
reservapp-api  → recurso protegido
```

Aunque algunos proveedores modelan estos elementos de manera diferente, la separación conceptual sigue siendo útil.

## 2. Client ID

El **Client ID** identifica una aplicación ante el proveedor de identidad.

No es una contraseña.

Ejemplo conceptual:

```text
client_id = reservapp-web-123
```

El cliente lo utiliza para indicar quién está solicitando autenticación/autorización.

## 3. Redirect URI

Después de autenticar al usuario, el proveedor debe saber a qué dirección permitida puede devolver el control.

Ejemplo local:

```text
http://localhost:3000/callback
```

Una redirect URI debe registrarse explícitamente. No debería aceptarse cualquier destino porque permitiría desviar respuestas de autenticación a sitios no autorizados.

## 4. Cliente público vs cliente confidencial

### Cliente público

No puede mantener un secreto de forma confiable.

Ejemplos típicos:

- SPA;
- aplicación móvil;
- aplicación instalada en dispositivo del usuario.

Por eso se utiliza **Authorization Code + PKCE** en escenarios modernos apropiados.

### Cliente confidencial

Puede mantener credenciales en un entorno controlado de servidor.

Ejemplo:

- backend server-side.

No debemos copiar un `client_secret` a una SPA ni subirlo al repositorio.

## 5. API / Resource Server

ReservApp API representa el recurso protegido.

Conceptualmente puede esperar tokens cuya audiencia sea:

```text
reservapp-api
```

Y cuyos scopes permitan acciones como:

```text
reservations.read
reservations.write
```

## 6. ¿Quién usa qué?

```text
Usuario
  ↓
ReservApp Web
  ↓ solicita autorización
Proveedor de identidad
  ↓ devuelve resultado al redirect URI
ReservApp Web
  ↓ Authorization: Bearer <access_token>
API Gateway
  ↓
ReservApp API
```

El cliente usa el **access token** para llamar a la API.

El backend no debería usar el ID token como reemplazo del access token.

## 7. Scopes y reglas de negocio

Supongamos:

```text
reservations.read
reservations.write
```

Estos scopes pueden permitir diferenciar operaciones generales de lectura/escritura.

Pero tener:

```text
reservations.write
```

no significa automáticamente:

> “puedo cancelar cualquier reserva del sistema”.

La API todavía debe validar reglas de negocio, por ejemplo que la reserva pertenezca al `sub` autenticado, salvo que exista una política distinta para operadores.

## 8. Actividad práctica · App registration de ReservApp en papel/código

En el repositorio grupal creen `app-registration-design.md`.

Completen como mínimo:

### Aplicación cliente

```text
Nombre: reservapp-web
Tipo: __________________
Client ID: lo asignará el proveedor
Redirect URI local: __________________
Scopes solicitados: __________________
¿Usa client secret?: sí/no y por qué
```

### API

```text
Nombre: reservapp-api
Audiencia esperada: __________________
Scopes expuestos/esperados: __________________
Issuer esperado: __________________
```

### Casos

Decidan qué debería ocurrir si:

1. la redirect URI enviada no está registrada;
2. el token corresponde a otra audiencia;
3. el token expiró;
4. falta `reservations.write`;
5. el scope es correcto pero el usuario intenta modificar una reserva ajena.

Indiquen qué componente debería detectar cada situación.

## 9. Mini checklist de configuración futura

Cuando llegue el momento de utilizar un IDaaS real, deberíamos poder trasladar este diseño a la consola y encontrar equivalentes para:

- tenant/realm;
- usuario;
- aplicación/client;
- Client ID;
- redirect URI;
- API/resource;
- scopes;
- issuer;
- audience;
- claims.

Si un proveedor usa otros nombres, debemos poder reconocer la equivalencia.

## 10. Checkpoint de Semana 02

ReservApp debería terminar la semana con:

```text
API Gateway de Semana 01
+
modelo OAuth2/OIDC
+
diseño IDaaS/CIAM
+
diseño del tenant
+
diseño de registración de aplicaciones
```

No necesitamos todavía un tenant cloud operativo para que este checkpoint tenga valor.

## Continuidad

En la siguiente etapa se podrá llevar este modelo a un proveedor real, obtener tokens, estudiar JWT/claims e integrar la seguridad efectivamente con el Gateway y la API sin rediseñar el dominio desde cero.
