# 1.2.2 · Identity as a Service (IDaaS) y CIAM

## Objetivo

Comprender qué problema resuelve un proveedor de identidad y distinguir **IAM, IdP, IDaaS y CIAM** sin depender todavía de un proveedor cloud específico.

Los ejemplos son independientes de RegistrApp. La transferencia al desafío transversal ocurre después.

---

## 1. Del protocolo al servicio

OAuth2 y OIDC describen protocolos/modelos de interacción. IDaaS responde otra pregunta:

> ¿Quién implementa y opera las capacidades de identidad?

Un servicio administrado puede encargarse de autenticación, usuarios, MFA, recuperación de cuenta, emisión de tokens, registro de aplicaciones y políticas.

```mermaid
flowchart LR
    APP["Aplicación"] -->|"delega capacidades de identidad"| IDAAS["IDaaS"]
    IDAAS --> AUTH["Autenticación"]
    IDAAS --> TOK["Emisión de tokens"]
    IDAAS --> MFA["MFA"]
    IDAAS --> USERS["Gestión de usuarios"]
    IDAAS --> POL["Políticas"]
```

---

## 2. IAM, IdP, IDaaS y CIAM

### IAM

**Identity and Access Management** es el conjunto de procesos, políticas y tecnologías para gestionar identidades y acceso.

### IdP

Un **Identity Provider** autentica identidades y entrega afirmaciones o artefactos verificables para otros sistemas.

### IDaaS

**Identity as a Service** significa consumir capacidades de identidad como un servicio administrado.

### CIAM

**Customer Identity and Access Management** es IAM orientado a clientes, ciudadanos, consumidores o usuarios externos.

No son sinónimos exactos.

---

## 3. Ejemplo de identidad federada

Una aplicación ficticia permite iniciar sesión mediante un proveedor externo.

```mermaid
flowchart LR
    U["Usuario"] --> APP["Aplicación"]
    APP -->|"redirección para login"| IDP["Identity Provider"]
    IDP -->|"resultado de autenticación"| APP
    APP --> PROFILE["Perfil de negocio propio"]
```

La aplicación puede delegar autenticación y aun mantener sus propios datos de negocio.

---

## 4. Identidad externa vs entidad de negocio

El usuario del proveedor de identidad no tiene por qué ser la misma entidad que usa el dominio de negocio.

Ejemplo:

```text
IdP
sub = user-1024
email = ana@example.com

Base de datos de negocio
customerId = 827
identitySubject = user-1024
plan = premium
```

```mermaid
flowchart LR
    IDP["IdP<br/>sub=user-1024"] -->|"identidad estable"| API["API de negocio"]
    API --> DB["Cliente 827<br/>identitySubject=user-1024"]
```

Separar ambas ideas evita acoplar el dominio a un proveedor concreto.

---

## 5. Responsabilidad compartida

Un proveedor administrado no elimina las responsabilidades de la aplicación.

```mermaid
flowchart TB
    subgraph ID["Identity Platform"]
        I1["Autenticar"]
        I2["Emitir tokens"]
        I3["MFA y recuperación"]
        I4["Administrar clientes"]
    end

    subgraph GW["API Gateway"]
        G1["Políticas transversales"]
        G2["Validación técnica"]
    end

    subgraph APP["Aplicación de negocio"]
        A1["Reglas de negocio"]
        A2["Propiedad de recursos"]
        A3["Protección de datos"]
    end
```

La ubicación exacta de cada control depende de la arquitectura, pero la responsabilidad debe estar explícita.

---

## 6. CIAM vs workforce IAM

Una solución orientada a público externo suele necesitar capacidades como:

- autoregistro;
- recuperación de cuenta;
- login social/federado;
- consentimiento;
- experiencia de usuario;
- escalabilidad para grandes poblaciones.

Un entorno de trabajadores internos suele tener políticas, ciclo de vida y gobierno distintos.

```mermaid
flowchart TB
    P["Plataforma de identidad"] --> EXT["Clientes externos<br/>CIAM"]
    P --> INT["Usuarios internos<br/>Workforce IAM"]
```

---

## 7. Tenant, aplicaciones y recursos

Los proveedores de identidad suelen organizar elementos conceptuales equivalentes a:

- tenant / realm / organization;
- usuarios;
- aplicaciones/clientes;
- APIs/resources;
- métodos de autenticación;
- scopes;
- roles;
- claims;
- políticas.

```mermaid
flowchart TD
    T["Tenant / Realm"] --> U["Usuarios"]
    T --> C["Clients / Apps"]
    T --> R["Resources / APIs"]
    T --> P["Políticas"]
    T --> M["Métodos de autenticación"]
    R --> S["Scopes"]
```

La interfaz concreta cambia entre proveedores; las relaciones conceptuales permanecen.

---

## 8. Flujo de responsabilidad

```mermaid
sequenceDiagram
    actor U as Usuario
    participant C as Cliente
    participant I as IDaaS
    participant G as Gateway
    participant A as API

    U->>C: Iniciar sesión
    C->>I: Flujo OIDC / OAuth2
    I-->>C: Tokens
    C->>G: Request + access token
    G->>G: Validación técnica
    G->>A: Request autenticada
    A->>A: Regla de negocio
    A-->>C: Resultado
```

---

## 9. Mini actividad independiente

Clasifica cada responsabilidad entre **cliente, IdP/IDaaS, gateway o API**:

1. validar contraseña;
2. solicitar MFA;
3. emitir access token;
4. enviar `Authorization: Bearer ...`;
5. validar issuer/audience;
6. comprobar scope;
7. validar propiedad de un recurso;
8. decidir si una operación de negocio es válida;
9. recuperar una cuenta;
10. revocar una sesión comprometida.

Después discute cuáles podrían ubicarse en más de una capa y qué trade-off aparece.

## Cierre

El estudiante debe poder explicar:

- diferencia entre IAM, IdP, IDaaS y CIAM;
- qué responsabilidades se delegan;
- cuáles siguen perteneciendo a la aplicación;
- por qué identidad y entidad de negocio no son lo mismo;
- cómo encajan tenant, cliente, API, scopes y políticas.

→ [Profundización opcional](./02-idaas-ciam/README.md)
