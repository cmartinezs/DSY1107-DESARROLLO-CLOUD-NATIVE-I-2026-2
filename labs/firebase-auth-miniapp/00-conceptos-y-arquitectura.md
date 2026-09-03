# Parte 0 · Conceptos y arquitectura del laboratorio

← [Volver al índice](./README.md)

## Qué problema resolvemos

La miniapp necesita distinguir entre personas anónimas y personas autenticadas sin implementar por cuenta propia almacenamiento de passwords, validación de credenciales ni integración OAuth con Google.

La solución elegida es **Firebase Authentication como Identity as a Service (IDaaS)**.

```text
Usuario
  │
  ▼
Miniapp web
  │
  ├── Email/Password ─────┐
  │                       │
  └── Google ─────────────┤
                          ▼
                Firebase Authentication
                          │
                          ▼
                    sesión / user
                          │
                 onAuthStateChanged
                          │
                ┌─────────┴─────────┐
                ▼                   ▼
            no hay user          hay user
                │                   │
         zona pública +        zona pública +
         autenticación         zona privada
```

---

## Autenticación vs autorización

### Autenticación

Responde:

> ¿Quién es el usuario?

Ejemplos del lab:

- email/password;
- Google.

### Autorización

Responde:

> ¿Qué puede hacer ese usuario?

Este laboratorio solo representa una autorización muy simple en UI: si existe sesión, se muestra una zona privada.

Eso **no reemplaza autorización real de backend**.

---

## Actor y responsabilidades

| Componente | Responsabilidad |
|---|---|
| Navegador | ejecutar la miniapp |
| Miniapp | presentar formularios y reaccionar al estado de sesión |
| Firebase SDK | conectar la app con Firebase Authentication |
| Firebase Authentication | crear identidades, autenticar, mantener sesión, password reset, Google |
| Google | autenticar al usuario dentro del flujo federado |

---

## Qué deliberadamente NO construiremos

Para reducir complejidad accidental, no incorporamos:

- backend propio;
- Spring Boot;
- API REST;
- validación manual de ID token;
- roles;
- Firestore;
- Firebase Hosting;
- email verification custom;
- pantalla custom de password reset;
- React/Vue/Angular;
- router.

Cada uno de esos elementos puede ser correcto en un producto real, pero no es necesario para comprender el objetivo de este ejercicio.

---

## Fuente de verdad

La sesión de Firebase es la única fuente de verdad de autenticación.

### Correcto

```text
Firebase Auth
→ onAuthStateChanged
→ UI
```

### Incorrecto

```text
Login exitoso
→ localStorage.isLoggedIn = true
→ confiar en localStorage
```

Un booleano local puede falsificarse y además puede quedar desincronizado respecto de Firebase.

---

## Evolución pedagógica

La secuencia es intencional:

```text
1. configurar Firebase
2. Register Email/Password
3. Login Email/Password
4. observar sesión
5. zona privada
6. Logout
7. Password Reset
-------------------- GATE --------------------
8. habilitar Google
9. Google Sign-In
10. probar ambos mecanismos
```

No adelantes Google porque ocultaría parte del aprendizaje sobre el ciclo tradicional de credenciales.

---

## Relación con una aplicación productiva

Una aplicación real puede extender el flujo:

```text
Firebase Authentication
→ frontend obtiene ID token
→ API/backend
→ backend valida token
→ consulta perfiles/permisos
→ aplica reglas de negocio
```

Este laboratorio termina antes de esa frontera.

---

# Preguntas antes de empezar

Deberías poder responder al terminar:

1. ¿Qué delegamos a Firebase?
2. ¿Qué sigue siendo responsabilidad de nuestra miniapp?
3. ¿Por qué una API no debería confiar únicamente en que el frontend muestre una zona privada?
4. ¿Qué tienen en común Email/Password y Google una vez autenticados?
5. ¿Por qué no necesitamos `localStorage.isLoggedIn`?

---

## Continúa

→ [Parte 1 · Preparación del proyecto y Firebase](./01-preparacion-y-firebase.md)
