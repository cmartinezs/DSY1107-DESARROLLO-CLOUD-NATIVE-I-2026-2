# Parte 2 · Registro y login con Email/Password

← [Volver al índice](./README.md)  
← [Parte 1 · Preparación](./01-preparacion-y-firebase.md)

## Objetivo

Implementar primero el flujo tradicional completo de credenciales:

```text
usuario
→ Register
→ Firebase crea identidad
→ Login
→ Firebase autentica identidad
```

Google continúa deshabilitado durante toda esta parte.

---

## 1. Preparar la interfaz

En `src/main.js` importa:

```javascript
import "./style.css";
import { auth } from "./firebase";
import {
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword
} from "firebase/auth";
```

Crea una zona pública y formularios independientes de Register y Login.

```javascript
const app = document.querySelector("#app");

app.innerHTML = `
  <main class="container">
    <section class="card">
      <h1>DSY1107 · Firebase Auth Mini App</h1>
      <h2>Zona pública</h2>
      <p>Este contenido puede verlo cualquier visitante.</p>
    </section>

    <section id="auth-zone" class="card">
      <form id="register-form">
        <h2>Crear cuenta</h2>
        <input id="register-email" type="email" placeholder="Correo" required />
        <input id="register-password" type="password" placeholder="Contraseña" required minlength="6" />
        <button type="submit">Registrarme</button>
      </form>

      <hr />

      <form id="login-form">
        <h2>Ingresar</h2>
        <input id="login-email" type="email" placeholder="Correo" required />
        <input id="login-password" type="password" placeholder="Contraseña" required />
        <button type="submit">Ingresar</button>
      </form>
    </section>

    <p id="message" aria-live="polite"></p>
  </main>
`;

const message = document.querySelector("#message");

function showMessage(text) {
  message.textContent = text;
}
```

---

## 2. Implementar Register

```javascript
const registerForm = document.querySelector("#register-form");

registerForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.querySelector("#register-email").value.trim();
  const password = document.querySelector("#register-password").value;

  try {
    const credential = await createUserWithEmailAndPassword(auth, email, password);

    console.log("Usuario creado:", credential.user.uid);
    registerForm.reset();
    showMessage("Usuario creado correctamente.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible crear el usuario: ${error.code}`);
  }
});
```

## Qué debes entender

`createUserWithEmailAndPassword`:

1. envía email y password a Firebase Authentication;
2. Firebase valida la solicitud;
3. Firebase crea una identidad;
4. Firebase devuelve un `UserCredential`;
5. el usuario queda autenticado automáticamente.

Tu aplicación **no crea ni almacena la contraseña**.

### Verificación en Firebase Console

Ve a:

```text
Authentication > Users
```

El usuario debe aparecer ahí.

---

## 3. Implementar Login

```javascript
const loginForm = document.querySelector("#login-form");

loginForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.querySelector("#login-email").value.trim();
  const password = document.querySelector("#login-password").value;

  try {
    const credential = await signInWithEmailAndPassword(auth, email, password);

    console.log("Usuario autenticado:", credential.user.uid);
    loginForm.reset();
    showMessage("Sesión iniciada correctamente.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible iniciar sesión: ${error.code}`);
  }
});
```

No hagas todavía esto:

```javascript
window.location.href = "/private";
```

La aplicación aún debe aprender a reaccionar correctamente al **estado de autenticación**. Eso se implementa en la siguiente parte.

---

# Checkpoint 2

Debes poder demostrar:

- [ ] crear una cuenta nueva;
- [ ] verla en Firebase Console;
- [ ] cerrar la sesión desde Firebase Console o esperar al paso siguiente para implementar logout;
- [ ] autenticar un usuario existente;
- [ ] recibir error con password incorrecta;
- [ ] impedir cuentas con password demasiado corta;
- [ ] explicar qué componente almacena las credenciales.

---

# Errores comunes

## `auth/email-already-in-use`

El correo ya tiene una identidad registrada.

**No es un error de Firebase:** es una regla de identidad esperada.

Para probar Register usa otro correo o elimina el usuario de Firebase Console.

## `auth/weak-password`

La contraseña no cumple la política mínima configurada.

No elimines validaciones para "hacer que funcione". Corrige la entrada.

## `auth/invalid-credential`

Las credenciales no son válidas. Dependiendo de la versión/configuración, Firebase puede utilizar este código sin revelar si falló específicamente email o password.

Esto reduce enumeración de cuentas.

## Register funciona, pero después Login parece innecesario

Es normal: `createUserWithEmailAndPassword` deja al usuario autenticado. Para estudiar ambos comportamientos de forma aislada, primero implementaremos Logout y observación de sesión.

## Estoy guardando email/password en `localStorage` para no escribirlos de nuevo

No lo hagas. Nunca persistas passwords.

`localStorage` puede usarse más adelante para datos auxiliares no sensibles de la miniapp, pero **Firebase Auth debe ser la fuente de verdad de autenticación**.

---

# FAQ

## ¿Register y Login crean dos usuarios distintos?

No. Register crea la identidad. Login autentica una identidad que ya existe.

## ¿Firebase cifra la contraseña en mi JavaScript?

No debes implementar criptografía de passwords en el frontend. Firebase Authentication se encarga del proceso de credenciales.

## ¿Debo guardar el UID?

No para este laboratorio. Puedes mostrarlo con fines didácticos, pero no necesitas persistirlo manualmente.

## ¿Por qué no llamamos a un backend después del registro?

Porque el objetivo del lab es estudiar Firebase como IDaaS. En aplicaciones reales puede existir un backend que cree perfiles, valide permisos o inicialice dominio, pero esa responsabilidad está fuera del alcance de esta miniapp.

---

## Continúa

→ [Parte 3 · Sesión, zona privada y Logout](./03-sesion-zona-privada.md)
