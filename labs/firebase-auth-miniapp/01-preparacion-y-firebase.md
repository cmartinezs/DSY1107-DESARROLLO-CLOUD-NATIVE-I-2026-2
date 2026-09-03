# Parte 1 · Preparación del proyecto y configuración de Firebase

← [Volver al índice del laboratorio](./README.md)

## Objetivo de esta parte

Preparar una aplicación web mínima con Vite, instalar Firebase, crear el proyecto cloud y dejar disponible una instancia de `Auth` lista para usar.

Al terminar esta parte todavía **no habrá login**. El objetivo es separar claramente la configuración de infraestructura cliente del comportamiento de autenticación.

---

## 1. Prerrequisitos

Necesitas:

- Node.js LTS;
- npm;
- navegador moderno;
- una cuenta Google;
- acceso a Firebase Console;
- VS Code, IntelliJ IDEA o editor equivalente.

Comprueba:

```bash
node --version
npm --version
```

Si alguno no responde, resuelve ese problema antes de continuar.

---

## 2. Crear el proyecto Vite

En una terminal:

```bash
npm create vite@latest firebase-auth-miniapp -- --template vanilla
cd firebase-auth-miniapp
npm install
npm install firebase
npm run dev
```

Deberías obtener una URL similar a:

```text
http://localhost:5173
```

### Checkpoint 1.1

- [ ] Vite levanta sin errores.
- [ ] La aplicación abre en el navegador.
- [ ] `firebase` aparece en `dependencies`.

---

## 3. Crear proyecto en Firebase

En Firebase Console:

1. Crear proyecto.
2. Nombre sugerido: `dsy1107-firebase-auth-tuapellido`.
3. Google Analytics no es necesario para este laboratorio.
4. Esperar a que Firebase termine la creación.

### Error común · crear varios proyectos sin necesidad

No crees un proyecto Firebase distinto para cada intento. Si algo falla en tu código, normalmente el problema está en configuración o implementación, no en que el proyecto cloud esté "dañado".

---

## 4. Registrar una Web App

Dentro del proyecto:

1. `Project Overview`.
2. Seleccionar Web `</>`.
3. Nickname: `firebase-auth-miniapp`.
4. No es necesario Firebase Hosting.
5. Registrar la aplicación.
6. Copiar la configuración `firebaseConfig`.

Ejemplo:

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

## ¿Es `firebaseConfig` un secreto?

No debe confundirse con una clave privada de backend. Esta configuración identifica la aplicación web y necesariamente termina en el navegador.

Eso **no significa** que puedas publicar cualquier credencial Firebase. Nunca subas:

- service account JSON;
- claves privadas;
- passwords;
- tokens reutilizables;
- secretos de backend.

---

## 5. Crear `src/firebase.js`

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

### Qué hace cada parte

- `initializeApp(...)`: inicializa Firebase SDK para tu aplicación.
- `getAuth(app)`: obtiene el servicio Firebase Authentication asociado.
- `auth`: será la dependencia central de los siguientes pasos.

---

## 6. Activar Email/Password

En Firebase Console:

1. `Authentication`.
2. `Get started`.
3. `Sign-in method`.
4. `Email/Password`.
5. Activar **Email/Password**.
6. No activar `Email link`.
7. Guardar.

Todavía **no habilites Google**.

### Checkpoint 1.2

Debe quedar:

```text
Email/Password    Enabled
Google            Disabled
```

---

# Errores comunes

## `FirebaseError: Firebase: Error (auth/operation-not-allowed)`

**Causa probable:** Email/Password no está habilitado en Firebase Console.

**Solución:** revisa `Authentication > Sign-in method`.

## `FirebaseError: Firebase: Error (auth/api-key-not-valid)`

**Causa probable:** copiaste mal `firebaseConfig` o mezclaste datos de otro proyecto.

**Solución:** vuelve a copiar la configuración exacta desde Firebase Console.

## La aplicación compila, pero Firebase no funciona

Comprueba:

1. que importaste `firebase/app` y `firebase/auth`;
2. que `auth` se exporta desde `src/firebase.js`;
3. que el archivo que lo utiliza importa desde `./firebase`;
4. que no hay errores en la consola del navegador.

---

# FAQ

## ¿Necesito Firebase Hosting?

No. Vite en localhost es suficiente para este laboratorio.

## ¿Necesito backend?

No. En este laboratorio Firebase Authentication es suficiente para estudiar Identity as a Service desde el frontend.

## ¿Puedo usar React?

Técnicamente sí, pero no para esta guía. Se utiliza Vite Vanilla para minimizar tecnología accidental y mantener el foco en autenticación.

## ¿Por qué Google todavía está deshabilitado?

Porque queremos comprender primero todo el ciclo Email/Password. La autenticación federada se agrega después como evolución del sistema, no como atajo.

---

## Continúa

→ [Parte 2 · Registro y login con Email/Password](./02-email-password.md)
