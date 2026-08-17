# 1.2.2 · Identity as a Service (IDaaS) y CIAM

## Objetivo

Comprender qué problema resuelve un proveedor de identidad, qué responsabilidades puede delegar una aplicación en un servicio de identidad y cómo se relacionan **IAM, IDaaS, IdP y CIAM** dentro de la arquitectura de **ReservApp**.

> El foco es reconocer capacidades y responsabilidades. Todavía no importa cómo se llama cada menú en un proveedor específico.

---

## 1. Del protocolo al servicio

En el tema anterior vimos OAuth2 y OIDC como protocolos/modelos de interacción.

Ahora aparece una pregunta distinta:

> **¿Quién implementa y opera toda esa infraestructura de identidad?**

ReservApp podría construir por sí misma:

- almacenamiento de credenciales;
- recuperación de contraseña;
- MFA;
- sesiones;
- emisión de tokens;
- administración de clientes;
- federación con otras identidades;
- auditoría;
- políticas de acceso.

Pero hacerlo implica responsabilidad operativa y de seguridad.

Un proveedor especializado puede ofrecer esas capacidades como servicio.

```mermaid
flowchart LR
    APP[ReservApp] -->|delega identidad| IDAAS[IDaaS]
    IDAAS --> AUTH[Autenticación]
    IDAAS --> TOKENS[Emisión de tokens]
    IDAAS --> MFA[MFA]
    IDAAS --> USERS[Gestión de usuarios]
    IDAAS --> POLICIES[Políticas]
```

---

## 2. Ejemplo cotidiano: una misma identidad para muchas aplicaciones

Piensa en cuántas veces has visto botones como:

```text
Continuar con Google
Continuar con Microsoft
Continuar con Apple
```

La aplicación que utilizas puede decidir **no administrar directamente tus credenciales** y confiar en un proveedor de identidad externo.

Por ejemplo, una aplicación de música podría permitir que el usuario utilice su identidad de Google para crear o reconocer una cuenta dentro de la propia aplicación.

```mermaid
flowchart LR
    U[Usuario] --> APP[Aplicación de música]
    APP -->|Redirección para login| G[Proveedor de identidad]
    G -->|Identidad verificada| APP
    APP --> PROFILE[(Perfil propio de la app)]
```

Hay dos datos que no debemos confundir:

```text
Identidad externa
→ “Google conoce a este sujeto como X”

Cuenta de negocio de la aplicación
→ “En mi sistema este sujeto corresponde al cliente 827”
```

Por eso una app puede usar Google para login y mantener igualmente su propia base de datos con preferencias, plan contratado, historial, reservas o cualquier otra información del negocio.

---

## 3. ¿Qué es IAM?

**IAM — Identity and Access Management** es el conjunto de procesos, políticas y tecnologías que permiten gestionar:

- identidades;
- autenticación;
- autorización;
- acceso a recursos;
- ciclo de vida de usuarios;
- auditoría y gobierno.

IAM es un concepto amplio.

No significa necesariamente “producto cloud”.

---

## 4. ¿Qué es un Identity Provider (IdP)?

Un **Identity Provider** es un sistema que autentica identidades y entrega afirmaciones o artefactos que otros sistemas pueden confiar.

En nuestro escenario puede actuar además como Authorization Server.

ReservApp confía en el IdP para no manejar directamente la contraseña del usuario.

```mermaid
flowchart LR
    U[Usuario] -->|credenciales| IDP[Identity Provider]
    IDP -->|resultado autenticación / tokens| C[ReservApp Web]
    C -->|access token| API[ReservApp API]
```

La contraseña queda entre usuario e IdP, no entre usuario y backend de ReservApp.

---

## 5. ¿Qué es IDaaS?

**Identity as a Service (IDaaS)** significa consumir capacidades de identidad como un servicio administrado.

El proveedor normalmente se ocupa de buena parte de la infraestructura necesaria para:

- autenticación;
- directorio de usuarios;
- MFA;
- recuperación de cuentas;
- registro de aplicaciones;
- emisión y validación de tokens;
- integración con estándares como OAuth2/OIDC;
- federación;
- políticas de seguridad.

### Qué ganamos

- menor cantidad de seguridad crítica implementada desde cero;
- protocolos estandarizados;
- operaciones centralizadas;
- reutilización entre aplicaciones;
- mejor capacidad de evolución.

### Qué NO significa

IDaaS no significa:

> “la seguridad ahora es problema del proveedor”.

ReservApp todavía debe decidir:

- qué usuarios pueden hacer qué;
- qué permisos necesita cada operación;
- qué datos son sensibles;
- qué reglas de negocio protegen recursos;
- qué scopes/roles utilizar;
- cómo actuar ante errores y accesos indebidos.

---

## 6. Ejemplo cotidiano: identidad y acceso a Drive son responsabilidades distintas

Supongamos una herramienta de diseño que permite:

1. entrar usando una identidad de Google;
2. importar una imagen desde Google Drive;
3. eventualmente guardar o exportar un archivo hacia Drive.

Aunque el usuario lo vea como una sola experiencia, técnicamente hay responsabilidades distintas.

```mermaid
flowchart TD
    U[Usuario]
    APP[Herramienta de diseño]
    IDP[Proveedor de identidad]
    DRIVE[API de almacenamiento / Drive]

    U -->|Iniciar sesión| APP
    APP -->|OIDC / identidad| IDP
    IDP -->|Quién es el usuario| APP
    APP -->|OAuth2 / permiso delegado| IDP
    IDP -->|Access Token limitado| APP
    APP -->|Leer o guardar recurso autorizado| DRIVE
```

La aplicación puede delegar al proveedor cuestiones de identidad y autorización, pero sigue siendo responsable de:

- decidir cuándo solicitar acceso a Drive;
- pedir solo los permisos necesarios;
- manejar correctamente el token recibido;
- no mostrar recursos a personas que no correspondan;
- aplicar sus propias reglas de negocio.

Este ejemplo es útil porque muestra que **IDaaS no es solo una pantalla de login**. Es una pieza de infraestructura que puede centralizar múltiples capacidades de identidad y acceso.

---

## 7. Responsabilidad compartida

Una forma útil de comprender IDaaS es separar responsabilidades.

```mermaid
flowchart TB
    subgraph IDAAS[Proveedor de identidad / IDaaS]
        A1[Autenticar usuario]
        A2[Emitir tokens]
        A3[MFA / recuperación]
        A4[Administrar clientes]
    end

    subgraph APP[ReservApp]
        B1[Enviar usuario al IdP]
        B2[Usar access token]
        B3[Validar reglas de negocio]
        B4[Proteger datos de reservas]
    end

    subgraph GW[API Gateway]
        C1[Política transversal]
        C2[Validación técnica]
    end
```

No todas las arquitecturas ubicarán exactamente las mismas comprobaciones en el mismo componente, pero la separación ayuda a razonar.

---

## 8. ¿Qué es CIAM?

**CIAM — Customer Identity and Access Management** es una especialización de IAM orientada a clientes, ciudadanos, consumidores o usuarios externos de una organización.

Sus preocupaciones suelen incluir:

- registro/autoregistro;
- inicio de sesión para grandes cantidades de usuarios externos;
- recuperación de cuenta;
- experiencia de usuario;
- login social o federado;
- consentimiento;
- perfiles;
- escalabilidad;
- seguridad sin requerir intervención administrativa por cada usuario.

### Ejemplo cotidiano

Cuando una aplicación orientada al público permite:

```text
Crear cuenta
Continuar con Google
Continuar con Apple
Recuperar contraseña
Activar MFA
Administrar sesiones
```

estamos viendo capacidades típicas de un escenario CIAM.

### ReservApp como ejemplo

Si ReservApp permite que cualquier cliente cree una cuenta y administre sus propias reservas, estamos frente a un escenario cercano a CIAM.

Si además existieran trabajadores internos, podríamos tener dos poblaciones distintas:

```mermaid
flowchart TB
    ID[Plataforma de identidad]
    EXT[Clientes externos\nCIAM]
    INT[Operadores internos\nWorkforce IAM]
    ID --> EXT
    ID --> INT
```

La diferencia no es solo semántica: usuarios externos e internos pueden requerir políticas, experiencias y ciclos de vida distintos.

---

## 9. Usuario de identidad vs entidad de negocio

Este punto es importante.

Un usuario autenticado en el IdP **no tiene por qué ser exactamente la misma cosa** que una entidad de negocio en ReservApp.

Por ejemplo:

```text
IdP
sub = user-1024
email = ana@example.com
```

Mientras que la base de datos de ReservApp podría tener:

```text
Cliente
id = 827
identitySubject = user-1024
nombre = Ana
preferencias = ...
```

```mermaid
flowchart LR
    IDP[IdP\nsub=user-1024] -->|identidad estable| API[ReservApp API]
    API --> DB[(Cliente id=827\nidentitySubject=user-1024)]
```

### Volviendo al ejemplo de una app de música

El proveedor de identidad podría informar:

```text
sub = google-user-xyz
email = usuario@example.com
```

Mientras la aplicación mantiene:

```text
UsuarioApp
id = 9381
plan = premium
playlists = ...
preferencias = ...
```

La identidad permite reconocer al usuario; **el dominio de negocio sigue perteneciendo a la aplicación**.

Separar ambas ideas evita acoplar excesivamente el dominio a un proveedor específico.

---

## 10. Tenant, aplicaciones y políticas

Un servicio de identidad necesita organizar sus recursos.

Por eso aparecen conceptos como:

- tenant / realm / organization;
- usuarios;
- grupos;
- aplicaciones/clientes;
- APIs/resources;
- métodos de autenticación;
- políticas;
- scopes;
- roles;
- claims.

```mermaid
flowchart TD
    T[Tenant / Realm]
    T --> U[Usuarios]
    T --> C[Clients / Apps]
    T --> R[Resources / APIs]
    T --> P[Políticas]
    T --> M[Métodos de autenticación]
    R --> S[Scopes]
```

La interfaz concreta cambia entre proveedores; estas relaciones conceptuales permanecen.

---

## 11. Scopes, roles y reglas de negocio en un entorno IDaaS

Supongamos un token con:

```text
sub   = user-123
scope = reservations.write
role  = customer
```

Podemos interpretar tres niveles distintos:

1. **Identidad:** `sub=user-123`.
2. **Capacidad general:** `reservations.write`.
3. **Contexto/función:** `role=customer`.

Pero todavía falta consultar el negocio para responder:

> ¿La reserva que intenta cancelar pertenece a `user-123`?

```mermaid
flowchart LR
    TOKEN[Token\nsub + scope + role] --> GW[Gateway]
    GW --> API[ReservApp API]
    DB[(Reservas)] --> API
    API --> DECISION[Decisión final de negocio]
```

---

## 12. Qué puede validar cada componente

### Identity Provider

- credenciales;
- MFA;
- políticas de autenticación;
- emisión de tokens.

### ReservApp Web

- iniciar flujo de autenticación;
- recibir resultado del flujo;
- mantener estado de sesión de cliente de forma adecuada;
- enviar access token a la API.

### API Gateway

- existencia del token;
- issuer esperado;
- audience;
- expiración;
- políticas transversales;
- posiblemente scopes generales.

### ReservApp API

- autorización de negocio;
- propiedad del recurso;
- estados válidos de una reserva;
- permisos contextualizados;
- invariantes del dominio.

```mermaid
sequenceDiagram
    actor U as Usuario
    participant C as ReservApp Web
    participant I as IDaaS
    participant G as Gateway
    participant A as ReservApp API
    participant D as DB

    U->>C: Iniciar sesión
    C->>I: Flujo OIDC/OAuth2
    I-->>C: Tokens
    C->>G: Petición + access token
    G->>G: Validación técnica
    G->>A: Petición autenticada
    A->>D: Busca reserva y propietario
    D-->>A: Datos de negocio
    A->>A: Regla de autorización de negocio
    A-->>C: Resultado
```

---

## 13. Actividad guiada · mapa de responsabilidades

En grupos, clasifiquen los siguientes casos entre:

- usuario;
- ReservApp Web;
- IDaaS/IdP;
- API Gateway;
- ReservApp API.

Casos:

1. validar la contraseña;
2. solicitar MFA;
3. emitir access token;
4. enviar `Authorization: Bearer ...`;
5. comprobar firma/issuer/audience;
6. determinar si falta `reservations.write`;
7. comprobar que la reserva pertenezca al usuario;
8. decidir si una reserva ya cancelada puede cancelarse otra vez;
9. mostrar nombre/avatar del usuario autenticado;
10. revocar una sesión comprometida.

### Mini caso cotidiano adicional

Una aplicación permite iniciar sesión con Google y luego muestra el botón:

```text
Conectar Google Drive
```

Respondan:

1. ¿Por qué el login no debería entregar automáticamente acceso a Drive?
2. ¿Qué nueva autorización necesita la aplicación?
3. ¿Quién es el resource server en esta segunda operación?
4. ¿Qué debería ocurrir si el usuario niega el permiso?
5. ¿La aplicación debería dejar de funcionar completamente o solo deshabilitar la integración con Drive?

### Pregunta adicional

¿Hay responsabilidades que podrían ubicarse en más de un componente?

Justifiquen según arquitectura, no por memorización.

---

## 14. Errores conceptuales frecuentes

- confundir IDaaS con OAuth2;
- pensar que IdP, IAM, IDaaS y CIAM son sinónimos exactos;
- creer que “Continuar con Google” significa que la aplicación puede acceder automáticamente a Drive/Gmail/etc.;
- asumir que el proveedor conoce las reglas de negocio de ReservApp;
- usar email como identificador técnico eterno sin analizar estabilidad;
- mezclar usuario de identidad con entidad `Cliente` del dominio;
- guardar secretos en frontend;
- creer que integrar un proveedor elimina la necesidad de diseñar autorización.

---

## Checkpoint

Al terminar este tema debes poder explicar:

- diferencia entre protocolo y servicio;
- qué es IAM;
- qué es un IdP;
- qué aporta IDaaS;
- qué distingue CIAM de un escenario de workforce IAM;
- por qué login federado y permiso a otra API son operaciones distintas;
- qué responsabilidades se delegan y cuáles siguen en ReservApp;
- por qué usuario de identidad y entidad de negocio pueden ser distintos;
- dónde encajan tenant, apps, APIs, scopes y políticas.

## Continuidad

El siguiente tema usa estos conceptos para diseñar el **tenant de ReservApp**: qué identidades, aplicaciones, recursos y políticas necesita contener antes de tocar una consola real.