# EV1 · Protocolo de validación end-to-end

Este documento registra la validación real de las aplicaciones de referencia React y Angular contra el mismo proveedor de identidad y el mismo backend Spring Boot.

## Estado actual

- ✅ backend mínimo de referencia construido;
- ✅ cliente React mínimo construido;
- ✅ cliente Angular mínimo construido;
- ⬜ aplicaciones registradas/configuradas en Microsoft Entra ID;
- ⬜ scopes/roles reales asignados;
- ⬜ validación E2E React ejecutada;
- ⬜ validación E2E Angular ejecutada.

La validación real no debe marcarse como completada usando valores ficticios. Requiere un tenant y registros de aplicaciones reales.

## Configuración requerida

### API / Resource Server

Definir:

```text
JWT_ISSUER=<issuer real del tenant>
JWT_AUDIENCE=<audience real de la API>
ALLOWED_ORIGINS=http://localhost:5173,http://localhost:4200
```

La API debe exponer al menos un scope equivalente a `recurso.write` y, para validar roles, un app role equivalente a `ADMIN`.

### React

Copiar `.env.example` a `.env.local` y configurar:

```text
VITE_ENTRA_CLIENT_ID
VITE_ENTRA_TENANT_ID
VITE_API_SCOPE
VITE_API_BASE_URL
```

Registrar `http://localhost:5173` como redirect URI SPA.

### Angular

Configurar `src/environments/environment.ts` con valores reales y registrar `http://localhost:4200` como redirect URI SPA.

## Secuencia E2E obligatoria

### 1. Backend

```bash
cd backend
mvn spring-boot:run
```

Comprobar:

```text
GET http://localhost:8080/public/health → 200
GET http://localhost:8080/api/profile → 401
```

### 2. React

```bash
cd react
npm install
npm run dev
```

Ejecutar en UI:

1. GET público → 200;
2. login → redirección Entra ID → retorno a SPA;
3. GET autenticado → 200;
4. POST scope write sin permiso → 403;
5. asignar/consentir permiso requerido y repetir → 2xx;
6. GET admin sin rol → 403;
7. asignar rol ADMIN y repetir → 2xx;
8. logout;
9. nuevo acceso protegido sin sesión → requiere autenticación.

### 3. Angular

```bash
cd angular
npm install
npm start
```

Repetir exactamente la misma matriz utilizada para React.

## Resultado esperado común

| Escenario | React | Angular |
|---|---:|---:|
| público sin token | 200 | 200 |
| protegido sin token | 401 | 401 |
| login Authorization Code + PKCE | OK | OK |
| protegido con token válido | 200 | 200 |
| scope insuficiente | 403 | 403 |
| scope suficiente | 2xx | 2xx |
| rol insuficiente | 403 | 403 |
| rol ADMIN | 2xx | 2xx |
| logout | OK | OK |

## Evidencia a registrar

Registrar solamente:

- fecha de prueba;
- frontend probado;
- endpoint;
- HTTP status;
- `iss`, `aud`, `scp`/`roles` relevantes;
- resultado esperado/observado;
- diagnóstico si difiere.

No registrar Access Tokens completos, refresh tokens, secretos ni contraseñas.

## Criterio de cierre de Etapa 6

La Etapa 6 solo puede declararse completamente cerrada cuando React y Angular hayan ejecutado satisfactoriamente la matriz anterior contra el mismo IDaaS y backend. Hasta entonces, el código de referencia está construido pero la validación E2E permanece pendiente.