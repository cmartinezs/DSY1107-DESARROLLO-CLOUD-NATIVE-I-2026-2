# Etapa 13 · Asociar la SPA y ejecutar el primer auto-registro

## Objetivo

Asociar el user flow B2B a la aplicación correcta y demostrar el primer ciclo completo:

```text
usuario externo inexistente
→ llega a la aplicación
→ entra al self-service sign-up
→ valida su identidad
→ completa atributos
→ Entra aprovisiona Guest
→ vuelve a la aplicación
```

---

## 0 · Preparar una prueba limpia

Necesitas una identidad externa que **todavía no exista en el tenant** como Guest.

Antes de probar:

1. ir a `Entra ID → Users`;
2. buscar el correo de prueba;
3. confirmar que no existe;
4. no reutilizar el Guest manual de Etapa 4 para la primera evidencia self-service.

Si el usuario ya existe, estarás probando **sign-in**, no el alta.

```mermaid
flowchart TD
    U[Identidad de prueba] --> Q{¿Ya existe en tenant?}
    Q -- Sí --> OTHER[Usar otra identidad para probar sign-up]
    Q -- No --> READY[Prueba limpia]
```

---

## 1 · Abrir el user flow

Ruta:

`Entra ID → External Identities → User flows → <user flow>`

Comprueba que estás abriendo exactamente el flujo creado en Etapa 12.

---

## 2 · Asociar la aplicación

Dentro del user flow:

`Use → Applications → Add application`

Seleccionar la aplicación que representa la SPA de DSY1107.

Compara el nombre y, cuando sea necesario, el `Application (client) ID` con el guardado en Etapa 2.

No asocies accidentalmente:

- la App Registration de la API;
- Microsoft Graph;
- otra app de prueba;
- una Enterprise Application distinta solo porque tiene nombre parecido.

```mermaid
flowchart TD
    UF[User flow] --> APPS[Add application]
    APPS --> Q{¿Es la SPA client?}
    Q -- No --> STOP[No asociar]
    Q -- Sí --> ADD[Seleccionar]
```

---

## 3 · Verificar asociación

Después de agregarla, vuelve a `Applications` dentro del user flow.

La SPA debe aparecer en la lista.

La asociación significa que **esta aplicación puede utilizar este user flow de self-service**. No significa que cualquier aplicación del tenant use este flujo.

---

## 4 · Volver a comprobar la App Registration

Ruta:

`Entra ID → App registrations → <SPA>`

Confirmar:

- `Application (client) ID` esperado;
- `Directory (tenant) ID` esperado;
- plataforma `Single-page application`;
- redirect URI exacto del entorno de prueba;
- sin client secret en el frontend.

No cambies `Supported account types` como intento genérico de hacer aparecer el sign-up.

---

## 5 · Entender cómo llega el usuario al flujo

```mermaid
sequenceDiagram
    actor U as Usuario externo nuevo
    participant SPA as SPA DSY1107
    participant E as Microsoft Entra External ID
    participant UF as User flow B2B
    participant IDP as Identity Provider
    participant T as Workforce tenant DSY1107

    U->>SPA: Abrir / iniciar acceso
    SPA->>E: Authorization request
    E->>UF: Aplicar user flow asociado
    UF->>U: Mostrar sign-up
    U->>IDP: Demostrar identidad
    IDP-->>UF: Identidad validada
    UF->>U: Solicitar atributos de alta
    U->>UF: Completar formulario
    UF->>T: Aprovisionar Guest
    E-->>SPA: Resultado + redirección
```

La SPA no crea el Guest mediante una llamada JavaScript propia.

---

## 6 · Ejecutar en navegador limpio

Para reducir sesiones heredadas:

1. cerrar sesión Microsoft previa cuando corresponda;
2. usar ventana privada/incógnito para la primera prueba;
3. abrir la SPA;
4. iniciar el flujo de autenticación;
5. seleccionar la opción de registro cuando aparezca;
6. usar la identidad externa elegida;
7. completar atributos;
8. finalizar el flujo.

Incógnito es una técnica de laboratorio, no un requisito de producción.

---

## 7 · Observar el formulario

Comprueba que aparecen:

- el Identity Provider esperado;
- los atributos seleccionados;
- el orden configurado en Page layouts.

Si no aparecen los atributos:

1. verifica que el usuario no exista previamente;
2. confirma que editaste el user flow correcto;
3. revisa User attributes/Page layouts;
4. no asumas que MSAL es el problema.

---

## 8 · Completar el sign-up

El usuario debe completar el proceso sin intervención manual del administrador.

```mermaid
flowchart LR
    MAN[Manual] --> INV[Admin invita]
    SELF[Self-service] --> UF[Usuario completa user flow]
    INV --> G[Guest]
    UF --> G
```

---

## 9 · Verificar el Guest en el directorio

Ir a:

`Entra ID → Users`

Verificar:

- existe un objeto de usuario;
- `User type = Guest`;
- la identidad corresponde al usuario de prueba;
- los atributos seleccionados están presentes cuando aplique.

No uses capturas que expongan datos de otros estudiantes.

---

## 10 · Registrar estado antes/después

```text
ANTES
usuario de prueba no existe en Entra Users

DESPUÉS
usuario de prueba existe como Guest
```

No necesitas publicar identificadores internos completos.

---

## 11 · Probar el segundo acceso

Cerrar sesión y repetir con **el mismo usuario**.

Resultado esperado:

```text
primer acceso = sign-up + aprovisionamiento
segundo acceso = sign-in
```

```mermaid
stateDiagram-v2
    [*] --> NoExiste
    NoExiste --> SignUp: primer acceso
    SignUp --> Guest
    Guest --> SignIn: accesos posteriores
    SignIn --> Guest
```

---

## 12 · Separar éxito de sign-up de éxito de API

En este punto puedes tener:

```text
Guest creado correctamente
+
login correcto
+
API todavía rechaza
```

Eso no invalida el self-service. La siguiente frontera es token/autorización.

```mermaid
flowchart LR
    SSR[Self-service OK] --> AUTH[Usuario autenticado]
    AUTH --> TOKEN{¿Access token correcto?}
    TOKEN -- No --> API401[API rechaza]
    TOKEN -- Sí --> GW[API Gateway valida]
```

---

## 13 · Fallos frecuentes

### “No aparece opción de registro”

Revisar self-service habilitado, user flow creado, SPA asociada, aplicación correcta, Identity Provider disponible e identidad realmente nueva.

### “Veo login pero no formulario de atributos”

Probable causa: usuario ya aprovisionado. Prueba con una identidad nueva.

### “Se crea el Guest, pero vuelve con error a localhost”

Revisar redirect URI, plataforma SPA y configuración MSAL. El aprovisionamiento pudo funcionar aunque la redirección falle después.

### “El Guest aparece pero API da 401”

No borres/recrees el usuario repetidamente. La Etapa 14 reutiliza las pruebas de token/Gateway del flujo base para aislar la nueva frontera.

---

## 14 · Evidencia mínima

- aplicación visible en `User flow → Applications`;
- usuario de prueba inexistente antes del flujo;
- formulario self-service visible;
- Guest resultante;
- segundo acceso como sign-in;
- ningún secreto/token publicado.

---

## Checkpoint E13

- [ ] asocié la **SPA**, no la API;
- [ ] utilicé un usuario que no existía previamente;
- [ ] el user flow apareció;
- [ ] se ejecutó el Identity Provider esperado;
- [ ] se recopilaron atributos de primer alta;
- [ ] se creó un Guest sin invitación manual;
- [ ] segundo acceso funciona como sign-in;
- [ ] sé separar fallo de provisioning, frontend y API.

→ Continúa con [Etapa 14 · Segunda pasada integral de pruebas, troubleshooting y evidencia](./14-self-service-pruebas-troubleshooting.md).
