# Referencia · Ensamblaje final de la miniapp

← [Volver al índice](./README.md)

> Este archivo es una referencia de integración. **No debe usarse para saltarse las partes anteriores.** La intención es que primero implementes y pruebes cada etapa por separado y luego puedas comparar tu solución completa.

## Imports esperados

Al terminar el laboratorio, `src/main.js` necesitará operaciones equivalentes a:

```javascript
import "./style.css";
import { auth } from "./firebase";
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

## Responsabilidades que debería contener tu solución

```text
main.js
├── construir UI
├── showMessage(...)
├── Register Email/Password
├── Login Email/Password
├── Password Reset
├── onAuthStateChanged
├── Logout
└── Google Sign-In

firebase.js
└── inicializar Firebase + exportar auth
```

No necesitas:

- backend;
- API propia;
- base de datos propia;
- almacenamiento manual de tokens;
- pantalla custom de reset;
- router;
- framework frontend adicional.

---

## Orden recomendado dentro de `main.js`

1. imports;
2. construcción de HTML;
3. referencias DOM;
4. funciones utilitarias;
5. listener Register;
6. listener Login;
7. listener Password Reset;
8. listener Logout;
9. observer `onAuthStateChanged`;
10. listener Google.

El orden exacto puede variar, pero la solución debe ser legible y evitar duplicación innecesaria.

---

## Invariante principal

La UI no debe decidir autenticación mediante un booleano inventado.

```javascript
// NO
let isLoggedIn = false;
```

La decisión debe provenir de Firebase:

```javascript
onAuthStateChanged(auth, (user) => {
  // user !== null => sesión autenticada
});
```

---

## Ejemplo de información visible del usuario

```javascript
function getUserLabel(user) {
  return user.email ?? user.displayName ?? user.uid;
}
```

No es necesario guardar esta información para controlar la sesión.

---

## Manejo mínimo de errores

Durante el laboratorio conviene conservar el código Firebase para facilitar diagnóstico:

```javascript
catch (error) {
  console.error(error);
  showMessage(`Operación fallida: ${error.code}`);
}
```

Como extensión puedes mapear errores técnicos a mensajes amigables sin eliminar la evidencia técnica de consola.

---

## Regla de seguridad

Nunca agregues al proyecto:

```text
password en localStorage
ID token en localStorage
refresh token manual
service account JSON
private key
client secret
```

---

## Validación final

Si tu código ensamblado parece correcto pero algún caso falla, no reescribas todo.

Vuelve a:

→ [Matriz AUTH-01…AUTH-11](./06-pruebas-y-evidencias.md)  
→ [Troubleshooting y FAQ](./07-troubleshooting-faq.md)
