# Parte 5 · Agregar autenticación con Google

← [Volver al índice](./README.md)

## Objetivo

Agregar autenticación federada con Google **solo después de tener completamente funcional Email/Password**.

La idea pedagógica es comparar dos mecanismos que terminan entregando a la aplicación el mismo concepto: un usuario autenticado por Firebase.

```text
Email/Password ─┐
                ├─→ Firebase Authentication ─→ user ─→ zona privada
Google ─────────┘
```

---

## 1. Habilitar Google en Firebase Console

1. `Authentication`.
2. `Sign-in method`.
3. Seleccionar `Google`.
4. Activarlo.
5. Seleccionar/configurar el email de soporte si Firebase lo solicita.
6. Guardar.

### Checkpoint 5.1

Ahora debe verse:

```text
Email/Password    Enabled
Google            Enabled
```

---

## 2. Importar proveedor y popup

```javascript
import {
  GoogleAuthProvider,
  signInWithPopup
} from "firebase/auth";
```

---

## 3. Agregar botón

Dentro de la zona de autenticación:

```html
<hr />
<p>O continúa con:</p>
<button id="google-login-button" type="button">
  Continuar con Google
</button>
```

---

## 4. Implementar Google Sign-In

```javascript
const googleLoginButton = document.querySelector("#google-login-button");

const googleProvider = new GoogleAuthProvider();

googleLoginButton.addEventListener("click", async () => {
  try {
    const credential = await signInWithPopup(auth, googleProvider);

    console.log("Google user:", credential.user.uid);
    showMessage("Sesión iniciada con Google.");
  } catch (error) {
    if (
      error.code === "auth/popup-closed-by-user" ||
      error.code === "auth/cancelled-popup-request"
    ) {
      showMessage("Inicio con Google cancelado.");
      return;
    }

    console.error(error);
    showMessage(`No fue posible iniciar con Google: ${error.code}`);
  }
});
```

No necesitas escribir código especial para mostrar la zona privada.

El mismo `onAuthStateChanged` de la Parte 3 recibirá al usuario autenticado.

---

## 5. Qué debes observar

Con Email/Password:

```text
signInWithEmailAndPassword
→ Firebase
→ user
→ onAuthStateChanged
→ zona privada
```

Con Google:

```text
signInWithPopup
→ Google
→ Firebase
→ user
→ onAuthStateChanged
→ zona privada
```

La UI privada **no necesita saber cómo se autenticó el usuario** para decidir si existe sesión.

---

## 6. Probar ambos proveedores

Prueba, como mínimo:

### Caso A · Email/Password

```text
Logout
→ Login con email/password
→ zona privada
```

### Caso B · Google

```text
Logout
→ Continuar con Google
→ seleccionar cuenta
→ zona privada
```

### Caso C · cancelar popup

```text
Continuar con Google
→ cerrar popup
→ aplicación sigue operativa
→ usuario continúa no autenticado
```

---

# Errores comunes

## `auth/operation-not-allowed`

Google no está habilitado en `Authentication > Sign-in method`.

## `auth/popup-closed-by-user`

El usuario cerró el popup. No lo trates como un fallo grave de la aplicación.

## `auth/cancelled-popup-request`

Puede ocurrir si se disparan varios intentos de popup al mismo tiempo. Evita múltiples clicks mientras una operación está activa.

## Popup bloqueado por el navegador

`signInWithPopup` debe iniciarse directamente desde una acción del usuario, como un click.

## Google funciona pero no veo el email

El objeto `user` puede entregar distintos atributos dependiendo del proveedor. Inspecciona:

```javascript
console.log(credential.user);
```

Para mostrar identidad usa un fallback razonable:

```javascript
user.email ?? user.displayName ?? user.uid
```

## Estoy obteniendo el ID token para guardarlo en localStorage

No lo hagas en este laboratorio. No existe un backend que necesite ese token y Firebase SDK ya administra su sesión.

En una arquitectura real, un frontend puede obtener un ID token y enviarlo a una API para que el backend valide identidad/autorización. Esa frontera está deliberadamente fuera de esta miniapp.

---

# FAQ

## ¿Google reemplaza Email/Password?

No. Son proveedores distintos habilitados simultáneamente.

## ¿Necesito un Client Secret de Google en JavaScript?

No debes colocar client secrets en una SPA. Firebase gestiona la integración configurada para este flujo.

## ¿Por qué usamos `signInWithPopup`?

Para esta miniapp local es un flujo sencillo y observable que evita agregar routing de retorno al ejercicio.

## ¿Register con Google y Login con Google son distintos?

Desde la perspectiva del botón de autenticación, Firebase puede crear la identidad en el primer acceso y autenticarla en accesos posteriores. En una aplicación con dominio propio podría existir una etapa adicional de onboarding en backend; este laboratorio no la necesita.

## ¿Debo validar el usuario Google en un backend?

No para este ejercicio. Si hubiera recursos protegidos en una API real, el backend sí tendría que validar el token y aplicar autorización.

---

# Checkpoint 5

- [ ] Google aparece Enabled en Firebase.
- [ ] El popup abre desde el botón.
- [ ] Una cuenta Google válida entra a la misma zona privada.
- [ ] Logout funciona también para sesión iniciada con Google.
- [ ] Cancelar el popup no rompe la aplicación.
- [ ] Email/Password continúa funcionando después de agregar Google.
- [ ] No se persiste manualmente ningún token.

---

## Continúa

→ [Parte 6 · Pruebas, evidencias y criterios de término](./06-pruebas-y-evidencias.md)
