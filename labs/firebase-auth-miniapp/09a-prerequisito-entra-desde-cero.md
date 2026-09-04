# Parte 9A · Prerrequisito Entra desde cero

Antes de ejecutar la integración MSAL de la Parte 9, completar la guía canónica de Microsoft Entra ID:

→ **[Microsoft Entra ID · guía completa por etapas](../../docs/identity/entra-guia-completa/README.md)**

## Por qué este paso existe

La Parte 9 supone que el entorno Entra está utilizable. Este prerequisito evita que errores administrativos se confundan con errores de JavaScript/MSAL.

Completar primero:

```text
Cuenta Duoc + Azure for Students
→ directorio/tenant correcto
→ permisos para App Registration
→ SPA single-tenant
→ API + scopes
→ Guest/B2B manual
→ self-service sign-up
→ recién después MSAL
```

## Gate antes de abrir `src/msal.js`

- [ ] Azure for Students visible y activo;
- [ ] Tenant ID conocido;
- [ ] directorio correcto seleccionado;
- [ ] App Registration SPA creada;
- [ ] redirect URI SPA definido;
- [ ] API registration y scope definidos si se probará backend;
- [ ] al menos un compañero Guest invitado manualmente y aceptado;
- [ ] se comprende el flujo Guest manual;
- [ ] self-service sign-up habilitado si los permisos del tenant lo permiten;
- [ ] user flow creado y asociado a la SPA;
- [ ] al menos un usuario externo nuevo se auto-registró y quedó como Guest;
- [ ] no existe client secret en frontend.

Solo después continuar con:

→ [Parte 9 · Microsoft Entra ID + MSAL](./09-microsoft-entra-msal.md)

## Comparación con Firebase

Aquí está uno de los contrastes principales del laboratorio.

### Firebase Email/Password

```mermaid
sequenceDiagram
    actor U as Usuario
    participant SPA as SPA
    participant F as Firebase Authentication

    U->>SPA: Completar Register
    SPA->>F: createUserWithEmailAndPassword
    F->>F: Crear identidad Firebase
    F-->>SPA: UserCredential / sesión
```

### Entra External ID self-service

```mermaid
sequenceDiagram
    actor U as Usuario externo
    participant SPA as SPA
    participant E as Entra External ID
    participant UF as User flow
    participant T as Tenant

    U->>SPA: Iniciar acceso
    SPA->>E: Authorization request
    E->>UF: Ejecutar user flow
    UF->>U: Registro + atributos
    U->>UF: Completar alta
    UF->>T: Aprovisionar Guest
    E-->>SPA: Resultado de autenticación
```

## Mismo problema, modelos diferentes

```mermaid
flowchart TB
    IDAAS[Identity as a Service]
    IDAAS --> F[Firebase]
    IDAAS --> E[Entra ID]

    F --> FR[Register Email/Password]
    FR --> FU[Usuario Firebase]

    E --> EM[Guest manual]
    E --> ES[Guest self-service]
    EM --> EG[Usuario Guest en tenant]
    ES --> EG

    FU --> SDK1[Firebase SDK]
    EG --> SDK2[MSAL]
```

El aprendizaje esperado no es memorizar botones del portal, sino explicar:

1. quién inicia el alta;
2. quién crea/provisiona la identidad;
3. dónde vive esa identidad;
4. qué SDK observa la sesión;
5. qué cambia cuando luego se solicita un token para una API propia.
