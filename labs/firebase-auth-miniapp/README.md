# Lab · Mini App con Firebase Authentication

**Asignatura:** DSY1107 Desarrollo Cloud Native I  
**Semana sugerida:** Semana 4 · Identity as a Service  
**Modalidad:** laboratorio guiado con proveedor cloud real  
**Proveedor:** Firebase Authentication  
**Frontend:** JavaScript + Vite  

← [Volver al índice de laboratorios](../README.md)

---

## 1. Propósito del laboratorio

En este laboratorio construirás una mini aplicación web que delega la autenticación de usuarios a **Firebase Authentication**.

La aplicación tendrá cuatro capacidades visibles:

1. **Zona pública:** cualquier persona puede verla sin autenticarse.
2. **Registro:** creación de cuenta usando correo electrónico y contraseña.
3. **Login:** ingreso con correo electrónico y contraseña.
4. **Recuperar contraseña:** envío de correo de recuperación mediante Firebase.
5. **Zona privada:** solo se muestra cuando Firebase confirma que existe una sesión autenticada.
6. **Login con Google:** se incorporará únicamente después de comprobar que todo el flujo anterior funciona correctamente.

> Regla del lab: **primero Email/Password completo; después Google**. No habilites Google al comienzo.

---

## 2. Qué estás aprendiendo realmente

El objetivo no es memorizar funciones de Firebase. El objetivo es comprender el patrón **Identity as a Service (IDaaS)**:

```text
Usuario
   |
   v
Aplicación web
   |
   | credenciales / proveedor externo
   v
Firebase Authentication
   |
   | identidad autenticada
   v
Aplicación web
   |
   +--> zona pública
   |
   +--> zona privada solo si existe sesión válida
```

Firebase se responsabiliza de tareas sensibles como:

- almacenar y verificar credenciales;
- crear identidades;
- autenticar usuarios;
- mantener el estado de sesión;
- enviar correos de recuperación;
- integrar proveedores federados como Google.

Tu aplicación **no debe guardar contraseñas**.

---

## 3. Resultado esperado

Al finalizar debes poder demostrar el siguiente flujo:

```text
VISITANTE
  |
  +--> ve zona pública
  |
  +--> Register
  |      |
  |      +--> crea usuario en Firebase
  |
  +--> Login Email/Password
         |
         +--> sesión autenticada
                 |
                 +--> zona privada
                 +--> Logout

RECUPERACIÓN
  |
  +--> usuario ingresa email
         |
         +--> Firebase envía correo para restablecer contraseña

EXTENSIÓN
  |
  +--> Login con Google
         |
         +--> misma zona privada
```

---

# PARTE A · Preparar el proyecto local

## 4. Requisitos previos

Debes tener instalado:

- Node.js LTS;
- npm;
- un navegador moderno;
- una cuenta Google para acceder a Firebase Console;
- VS Code, IntelliJ IDEA o editor equivalente.

Comprueba Node y npm:

```bash
node --version
npm --version
```

Si ambos comandos muestran una versión, continúa.

---

## 5. Crear la aplicación con Vite

Abre una terminal en la carpeta donde trabajarás.

Ejecuta:

```bash
npm create vite@latest firebase-auth-miniapp -- --template vanilla
```

Entra al proyecto:

```bash
cd firebase-auth-miniapp
```

Instala las dependencias:

```bash
npm install
```

Instala Firebase:

```bash
npm install firebase
```

Ejecuta la aplicación:

```bash
npm run dev
```

Vite mostrará una URL similar a:

```text
http://localhost:5173
```

Ábrela en el navegador.

### Checkpoint A1

Antes de continuar debes comprobar:

- [ ] el proyecto Vite inicia sin errores;
- [ ] puedes abrir `http://localhost:5173`;
- [ ] `firebase` aparece en `dependencies` dentro de `package.json`.

No continúes hasta cumplir este checkpoint.

---

# PARTE B · Crear y configurar Firebase

## 6. Crear un proyecto Firebase

1. Abre **Firebase Console**.
2. Selecciona **Crear un proyecto**.
3. Usa un nombre reconocible, por ejemplo:

```text
dsy1107-firebase-auth-tuapellido
```

4. Continúa con el asistente.
5. Google Analytics no es necesario para este laboratorio; puedes dejarlo deshabilitado.
6. Espera a que Firebase termine de crear el proyecto.
7. Ingresa al proyecto recién creado.

---

## 7. Registrar la aplicación web

Dentro del proyecto Firebase:

1. Ve a **Project Overview**.
2. Selecciona el icono **Web `</>`**.
3. En *App nickname* escribe, por ejemplo:

```text
firebase-auth-miniapp
```

4. No necesitas configurar Firebase Hosting para este laboratorio.
5. Presiona **Register app**.
6. Firebase mostrará un objeto `firebaseConfig`.

Será similar a:

```javascript
const firebaseConfig = {
  apiKey: "...",
  authDomain: "...",
  projectId: "...",
  storageBucket: "...",
  messagingSenderId: "...",
  appId: "..."
};
```

> La configuración web identifica tu proyecto Firebase. **No pongas contraseñas, claves privadas de service accounts ni secretos de backend dentro del repositorio.**

---

## 8. Crear `src/firebase.js`

Dentro de `src/`, crea:

```text
src/firebase.js
```

Agrega:

```javascript
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
  apiKey: "REEMPLAZAR",
  authDomain: "REEMPLAZAR",
  projectId: "REEMPLAZAR",
  storageBucket: "REEMPLAZAR",
  messagingSenderId: "REEMPLAZAR",
  appId: "REEMPLAZAR"
};

const app = initializeApp(firebaseConfig);

export const auth = getAuth(app);
```

Reemplaza los valores por los entregados por Firebase Console.

### Checkpoint B1

- [ ] existe `src/firebase.js`;
- [ ] Firebase se inicializa con `initializeApp`;
- [ ] Authentication se obtiene con `getAuth`;
- [ ] `npm run dev` sigue funcionando sin errores.

---

# PARTE C · Habilitar Email/Password

## 9. Activar autenticación por correo y contraseña

En Firebase Console:

1. Abre **Authentication**.
2. Presiona **Get started** si aparece.
3. Ve a **Sign-in method**.
4. Selecciona **Email/Password**.
5. Activa **Email/Password**.
6. No actives *Email link* para este laboratorio.
7. Guarda los cambios.

> Todavía **NO habilites Google**.

### Checkpoint C1

En Firebase Console debe aparecer:

```text
Email/Password    Enabled
Google            Disabled
```

---

# PARTE D · Construir la interfaz

## 10. Reemplazar `src/main.js`

Elimina el contenido de ejemplo generado por Vite y crea la estructura de la aplicación.

Usaremos una sola página para que el foco esté en autenticación y estado de sesión.

```javascript
import "./style.css";
import { auth } from "./firebase";
import {
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  sendPasswordResetEmail,
  onAuthStateChanged,
  signOut
} from "firebase/auth";

const app = document.querySelector("#app");

app.innerHTML = `
  <main class="container">
    <section class="card public-zone">
      <h1>DSY1107 · Firebase Auth Mini App</h1>
      <h2>Zona pública</h2>
      <p>Este contenido puede verlo cualquier visitante.</p>
    </section>

    <section id="auth-zone" class="card">
      <h2>Autenticación</h2>

      <form id="register-form">
        <h3>Crear cuenta</h3>
        <input id="register-email" type="email" placeholder="Correo" required />
        <input id="register-password" type="password" placeholder="Contraseña" required minlength="6" />
        <button type="submit">Registrarme</button>
      </form>

      <hr />

      <form id="login-form">
        <h3>Ingresar</h3>
        <input id="login-email" type="email" placeholder="Correo" required />
        <input id="login-password" type="password" placeholder="Contraseña" required />
        <button type="submit">Ingresar</button>
      </form>

      <hr />

      <form id="reset-form">
        <h3>Recuperar contraseña</h3>
        <input id="reset-email" type="email" placeholder="Correo" required />
        <button type="submit">Enviar correo de recuperación</button>
      </form>
    </section>

    <section id="private-zone" class="card private-zone" hidden>
      <h2>Zona privada</h2>
      <p>Solo un usuario autenticado puede ver este contenido.</p>
      <p id="current-user"></p>
      <button id="logout-button" type="button">Cerrar sesión</button>
    </section>

    <p id="message" class="message" aria-live="polite"></p>
  </main>
`;

const authZone = document.querySelector("#auth-zone");
const privateZone = document.querySelector("#private-zone");
const currentUser = document.querySelector("#current-user");
const message = document.querySelector("#message");

function showMessage(text) {
  message.textContent = text;
}
```

Guarda y verifica que la interfaz se muestre.

### Checkpoint D1

Sin estar autenticado:

- [ ] ves la zona pública;
- [ ] ves Register;
- [ ] ves Login;
- [ ] ves Recuperar contraseña;
- [ ] la zona privada está oculta.

---

# PARTE E · Implementar Register

## 11. Crear usuarios con Email/Password

Debajo del código anterior agrega:

```javascript
const registerForm = document.querySelector("#register-form");

registerForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.querySelector("#register-email").value.trim();
  const password = document.querySelector("#register-password").value;

  try {
    await createUserWithEmailAndPassword(auth, email, password);
    registerForm.reset();
    showMessage("Usuario creado correctamente.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible crear el usuario: ${error.code}`);
  }
});
```

### Qué ocurre aquí

`createUserWithEmailAndPassword(...)` no crea un usuario en tu JavaScript. La aplicación envía la solicitud a Firebase Authentication y Firebase crea la identidad.

Además, cuando el registro es exitoso, Firebase deja al usuario autenticado automáticamente.

### Probar

1. Usa un correo real al que tengas acceso.
2. Ingresa una contraseña de al menos 6 caracteres.
3. Presiona **Registrarme**.
4. En Firebase Console abre:

```text
Authentication > Users
```

5. Verifica que el usuario aparezca.

### Checkpoint E1

- [ ] el usuario aparece en Firebase Console;
- [ ] no guardaste la contraseña en ninguna variable persistente, archivo o base de datos propia;
- [ ] puedes explicar quién almacena y valida la contraseña.

---

# PARTE F · Implementar Login

## 12. Iniciar sesión con Email/Password

Agrega:

```javascript
const loginForm = document.querySelector("#login-form");

loginForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.querySelector("#login-email").value.trim();
  const password = document.querySelector("#login-password").value;

  try {
    await signInWithEmailAndPassword(auth, email, password);
    loginForm.reset();
    showMessage("Sesión iniciada correctamente.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible iniciar sesión: ${error.code}`);
  }
});
```

No programes todavía una redirección manual hacia la zona privada. Primero debes resolver correctamente el **estado de autenticación**.

---

# PARTE G · Zona pública y zona privada

## 13. Observar el estado de autenticación

Firebase puede informar cuando la sesión cambia mediante `onAuthStateChanged`.

Agrega:

```javascript
onAuthStateChanged(auth, (user) => {
  if (user) {
    authZone.hidden = true;
    privateZone.hidden = false;
    currentUser.textContent = `Sesión activa: ${user.email ?? user.displayName ?? user.uid}`;
  } else {
    authZone.hidden = false;
    privateZone.hidden = true;
    currentUser.textContent = "";
  }
});
```

### Qué debes observar

Si `user` existe:

```text
Firebase confirma sesión
→ ocultar formularios de autenticación
→ mostrar zona privada
```

Si `user` es `null`:

```text
no existe sesión
→ mostrar autenticación
→ ocultar zona privada
```

> Ocultar una sección en el frontend **NO protege un backend ni una API**. Este laboratorio demuestra control de acceso visual basado en sesión. En una arquitectura completa, cada recurso protegido debe validar también la identidad/autorización del lado servidor.

---

## 14. Implementar Logout

Agrega:

```javascript
const logoutButton = document.querySelector("#logout-button");

logoutButton.addEventListener("click", async () => {
  try {
    await signOut(auth);
    showMessage("Sesión cerrada.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible cerrar la sesión: ${error.code}`);
  }
});
```

### Checkpoint G1

Prueba esta secuencia completa:

```text
abrir app
→ zona privada oculta
→ login correcto
→ zona privada visible
→ logout
→ zona privada oculta nuevamente
```

- [ ] funciona exactamente así;
- [ ] al recargar la página Firebase restaura correctamente el estado de sesión;
- [ ] puedes explicar para qué sirve `onAuthStateChanged`.

---

# PARTE H · Recuperar contraseña

## 15. Implementar Password Reset

Agrega:

```javascript
const resetForm = document.querySelector("#reset-form");

resetForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.querySelector("#reset-email").value.trim();

  try {
    await sendPasswordResetEmail(auth, email);
    resetForm.reset();
    showMessage("Si la cuenta corresponde, revisa el correo para continuar con la recuperación.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible solicitar la recuperación: ${error.code}`);
  }
});
```

### Probar recuperación

1. Cierra sesión.
2. Escribe el correo del usuario creado anteriormente.
3. Presiona **Enviar correo de recuperación**.
4. Revisa la bandeja de entrada y spam.
5. Abre el correo enviado por Firebase.
6. Sigue el enlace.
7. Define una nueva contraseña.
8. Regresa a la aplicación.
9. Comprueba que puedes iniciar sesión con la nueva contraseña.

### Checkpoint H1 · Email/Password terminado

Antes de habilitar Google, deben funcionar **TODOS** estos casos:

| Caso | Resultado esperado |
|---|---|
| visitante entra | ve zona pública y no ve zona privada |
| registro válido | Firebase crea usuario |
| login válido | aparece zona privada |
| login inválido | no aparece zona privada |
| recarga autenticado | Firebase conserva/restaura sesión |
| logout | vuelve al estado público |
| recuperar contraseña | Firebase envía correo |
| nueva contraseña | permite volver a ingresar |

> **Gate obligatorio:** si cualquiera de estos casos falla, NO avances a Google.

---

# PARTE I · Recién ahora agregar Google

## 16. Habilitar proveedor Google en Firebase

Solo después de aprobar el Checkpoint H1:

1. Abre Firebase Console.
2. Ve a **Authentication**.
3. Abre **Sign-in method**.
4. Selecciona **Google**.
5. Activa el proveedor.
6. Selecciona/configura el correo de soporte solicitado por Firebase.
7. Guarda.

Ahora Firebase debe mostrar al menos:

```text
Email/Password    Enabled
Google            Enabled
```

---

## 17. Importar GoogleAuthProvider

Modifica los imports de `firebase/auth` para incorporar:

```javascript
GoogleAuthProvider,
signInWithPopup
```

El import completo quedará conceptualmente así:

```javascript
import {
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
  sendPasswordResetEmail,
  onAuthStateChanged,
  signOut,
  GoogleAuthProvider,
  signInWithPopup
} from "firebase/auth";
```

---

## 18. Agregar botón de Google

Dentro de `#auth-zone`, después del formulario de login, agrega:

```html
<button id="google-login-button" type="button">
  Ingresar con Google
</button>
```

Luego obtén el botón:

```javascript
const googleLoginButton = document.querySelector("#google-login-button");
```

---

## 19. Implementar login con Google

Agrega:

```javascript
const googleProvider = new GoogleAuthProvider();

googleLoginButton.addEventListener("click", async () => {
  try {
    await signInWithPopup(auth, googleProvider);
    showMessage("Sesión iniciada con Google.");
  } catch (error) {
    console.error(error);
    showMessage(`No fue posible iniciar sesión con Google: ${error.code}`);
  }
});
```

Prueba:

1. Cierra sesión si estabas autenticado.
2. Presiona **Ingresar con Google**.
3. Selecciona una cuenta Google.
4. Completa el consentimiento si se solicita.
5. Verifica que aparezca la zona privada.
6. Revisa el usuario en **Authentication > Users**.
7. Verifica el proveedor asociado.

### Checkpoint I1

- [ ] Email/Password sigue funcionando;
- [ ] Register sigue funcionando;
- [ ] Password Reset sigue funcionando;
- [ ] Google Login funciona;
- [ ] ambos mecanismos terminan en la misma zona privada;
- [ ] `onAuthStateChanged` sigue siendo el punto central para decidir qué UI mostrar.

---

# PARTE J · Estilos mínimos

## 20. Crear una interfaz legible

Puedes utilizar tu propio diseño. Como mínimo, `src/style.css` debe diferenciar visualmente:

- zona pública;
- autenticación;
- zona privada;
- mensajes de éxito/error.

Ejemplo base:

```css
:root {
  font-family: system-ui, sans-serif;
  background: #f5f7fb;
  color: #202124;
}

body {
  margin: 0;
}

.container {
  width: min(900px, 92%);
  margin: 40px auto;
  display: grid;
  gap: 20px;
}

.card {
  background: white;
  border: 1px solid #dfe3e8;
  border-radius: 12px;
  padding: 24px;
}

form {
  display: grid;
  gap: 12px;
}

input,
button {
  font: inherit;
  padding: 10px 12px;
}

button {
  cursor: pointer;
}

.private-zone {
  border-width: 2px;
}

.message {
  min-height: 24px;
}
```

---

# PARTE K · Casos de prueba obligatorios

## 21. Matriz de pruebas

Ejecuta y registra evidencia de al menos estos casos:

| ID | Prueba | Resultado esperado |
|---|---|---|
| AUTH-01 | abrir sin sesión | pública visible, privada oculta |
| AUTH-02 | registrar nuevo usuario | cuenta creada y sesión iniciada |
| AUTH-03 | registrar correo existente | operación rechazada |
| AUTH-04 | login correcto | privada visible |
| AUTH-05 | login incorrecto | privada oculta |
| AUTH-06 | recargar autenticado | sesión restaurada |
| AUTH-07 | logout | privada oculta |
| AUTH-08 | password reset | correo enviado |
| AUTH-09 | usar nueva contraseña | login correcto |
| AUTH-10 | Google login | sesión autenticada |
| AUTH-11 | logout después de Google | vuelve a estado público |

---

# PARTE L · Evidencia que debes entregar

## 22. Evidencia mínima

Incluye en tu DevLog o entrega:

1. captura de Firebase con **Email/Password habilitado y Google todavía deshabilitado**;
2. captura del registro funcionando;
3. captura de `Authentication > Users` mostrando el usuario creado;
4. captura de login Email/Password;
5. captura de zona privada;
6. captura o evidencia del correo de recuperación, ocultando datos sensibles cuando corresponda;
7. evidencia de logout;
8. captura posterior con Google habilitado;
9. captura de Google Login funcionando;
10. matriz AUTH-01 a AUTH-11 con resultado PASS/FAIL;
11. explicación breve de las preguntas de reflexión.

No publiques:

- contraseñas;
- cookies de sesión;
- tokens completos reutilizables;
- claves privadas;
- credenciales de service accounts.

---

# PARTE M · Preguntas de reflexión

Responde con tus palabras:

1. ¿Qué responsabilidad queda en Firebase y cuál queda en la aplicación?
2. ¿Por qué la aplicación no debe almacenar la contraseña del usuario?
3. ¿Qué diferencia existe entre **registrar** e **iniciar sesión**?
4. ¿Qué representa el objeto `user` recibido por `onAuthStateChanged`?
5. ¿Por qué ocultar una zona en JavaScript no es suficiente para proteger una API real?
6. ¿Qué ventaja ofrece incorporar Google como proveedor federado?
7. ¿Por qué en este laboratorio implementamos primero Email/Password y después Google?
8. Si mañana agregáramos GitHub, Microsoft o Apple como proveedor, ¿qué parte conceptual del flujo seguiría siendo la misma?

---

# PARTE N · Criterio de término

El laboratorio está terminado únicamente cuando puedes ejecutar sin ayuda esta secuencia:

```text
1. abrir aplicación sin sesión
2. ver zona pública
3. registrar usuario con email/password
4. entrar a zona privada
5. cerrar sesión
6. iniciar sesión nuevamente
7. cerrar sesión
8. solicitar recuperación de contraseña
9. cambiar contraseña desde el correo
10. iniciar sesión con la nueva contraseña
11. cerrar sesión
12. habilitar Google
13. iniciar sesión con Google
14. entrar a la misma zona privada
15. cerrar sesión
```

Y puedes explicar:

```text
la aplicación NO autentica por sí sola
                |
                v
      delega autenticación
                |
                v
       Firebase Authentication
                |
                v
       devuelve estado de identidad
                |
                v
     la app adapta su comportamiento
```

---

## Referencias oficiales

- Firebase Authentication para Web: https://firebase.google.com/docs/auth/web/start
- Google Sign-In con Firebase: https://firebase.google.com/docs/auth/web/google-signin
- Gestión de usuarios y recuperación de contraseña: https://firebase.google.com/docs/auth/web/manage-users

> Si la interfaz de Firebase Console cambia, conserva el objetivo técnico: registrar una Web App, habilitar Email/Password, comprobar el flujo completo y recién después habilitar Google como proveedor adicional.
