# IdP, IAM e IDaaS: qué es cada cosa

← [Volver a la profundización](./README.md)

Estos conceptos se relacionan, pero no son sinónimos.

## IAM

**Identity and Access Management** es la disciplina que engloba procesos, políticas y tecnologías para gestionar identidades y accesos.

Incluye, entre otros:

- ciclo de vida de identidades;
- autenticación;
- autorización;
- roles y permisos;
- auditoría;
- gobierno de acceso.

IAM es más amplio que cualquier producto concreto.

## Identity Provider — IdP

Un **Identity Provider** es un sistema que autentica sujetos y entrega información o artefactos en los que otras aplicaciones pueden confiar.

En un flujo OIDC, por ejemplo, el IdP puede autenticar al usuario y emitir un ID Token.

```text
Usuario → IdP → identidad verificable → Cliente
```

## IDaaS

**Identity as a Service** describe capacidades de identidad entregadas como un servicio administrado.

Un IDaaS puede proporcionar:

- directorio de usuarios;
- login;
- MFA;
- recuperación de cuenta;
- federación;
- registro de aplicaciones;
- emisión de tokens;
- políticas y auditoría.

## Cómo se relacionan

```text
IAM
└── disciplina amplia
    ├── puede apoyarse en un IdP
    └── puede implementarse mediante un IDaaS
```

Un producto IDaaS puede actuar como IdP y ofrecer capacidades IAM, pero los tres términos describen cosas diferentes.

## Pregunta de comprobación

Si ReservApp usa un proveedor administrado para login, MFA y emisión de tokens, ¿está usando IAM, un IdP o IDaaS?

La respuesta correcta puede involucrar los tres conceptos, pero cada uno describe una capa distinta del problema.