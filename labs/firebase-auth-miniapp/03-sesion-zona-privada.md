# Parte 3 · Estado de sesión, zona privada y Logout

← [Volver al índice](./README.md)

## Objetivo

Hacer que la interfaz reaccione al estado real de Firebase Authentication y distinguir con claridad:

- contenido público;
- interfaz de autenticación;
- contenido privado visible solo para un usuario autenticado.

---

## 1. Agregar zona privada

En la interfaz agrega:

```html
<section id="private-zone" class="card" hidden>
  <h2>Zona privada</h2>
  <p>Solo un usuario autenticado puede ver este contenido.</p>
  <p id="current-user"></p>
  <button id="logout-button" type="button">Cerrar sesión</button>
</section>
```

Obtén las referencias:

```javascript
const authZone = document.querySelector("#auth-zone");
const privateZone = document.querySelector("#private-zone");
const currentUser = document.querySelector("#current-user");
```

---

## 2. Escuchar cambios de autenticación

Importa:

```javascript
import { onAuthStateChanged, signOut } from "firebase/auth";
```

Luego:

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

## Por qué usamos `onAuthStateChanged`

No queremos que el botón Login decida por sí mismo si la aplicación está autenticada.

La fuente de verdad es Firebase:

```text
acción del usuario
→ Firebase Authentication
→ estado de sesión cambia
→ onAuthStateChanged recibe el nuevo estado
→ UI reacciona
```

Esto también permite restaurar correctamente la interfaz al recargar la página.

---

## 3. Implementar Logout

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

No necesitas ocultar manualmente la zona privada después de `signOut`. El cambio será observado por `onAuthStateChanged`.

---

## 4. ¿Debemos usar `localStorage`?

Para autenticación, **no como fuente de verdad**.

Evita patrones como:

```javascript
localStorage.setItem("isLoggedIn", "true");
```

Un usuario puede cambiar ese valor desde DevTools. Además tendrías dos estados potencialmente contradictorios:

```text
Firebase: no autenticado
localStorage: isLoggedIn=true
```

Si deseas practicar `localStorage`, úsalo solo para estado auxiliar no sensible, por ejemplo:

```javascript
localStorage.setItem("theme", "dark");
localStorage.setItem("lastSection", "private-zone");
```

Nunca guardes manualmente:

- password;
- ID token;
- refresh token;
- `isAuthenticated` como mecanismo de autorización.

---

## 5. Importante: visible no significa protegido

Este laboratorio protege **la experiencia visual**, no un recurso servidor.

Un usuario podría abrir DevTools y quitar el atributo `hidden` de una sección HTML. Eso no significa que Firebase haya sido vulnerado; significa que el contenido ya estaba descargado en el navegador.

Por eso una aplicación real suele tener otra frontera:

```text
frontend autenticado
→ envía token
→ backend/API valida token
→ backend decide acceso al recurso
```

Ese backend no es necesario en este lab porque queremos aislar el aprendizaje de IDaaS en frontend.

---

# Checkpoint 3

Ejecuta exactamente:

```text
abrir app sin sesión
→ zona pública visible
→ autenticación visible
→ zona privada oculta
→ Login
→ zona privada visible
→ recargar navegador
→ zona privada continúa visible
→ Logout
→ autenticación vuelve a mostrarse
→ zona privada desaparece
```

Debes marcar:

- [ ] el flujo completo funciona;
- [ ] no existe `isLoggedIn` manual;
- [ ] no se persiste password ni token manualmente;
- [ ] puedes explicar el rol de `onAuthStateChanged`;
- [ ] puedes explicar por qué ocultar HTML no protege una API.

---

# Errores comunes

## Al recargar se muestra Login durante un instante

Firebase necesita restaurar el estado de autenticación de forma asíncrona. En una app más elaborada se suele utilizar un estado `loading` mientras se resuelve la sesión.

Para este lab puedes aceptar el comportamiento o agregar un indicador de carga como extensión.

## Login funciona pero la zona privada no aparece

Revisa:

1. que `onAuthStateChanged` esté registrado;
2. que `privateZone` exista;
3. que no exista un error JavaScript anterior;
4. que uses la misma instancia `auth` en todo el módulo.

## Logout no actualiza la UI

No agregues código duplicado de ocultamiento. Primero verifica que `signOut(auth)` se complete y que `onAuthStateChanged` siga activo.

## Guardé `isLoggedIn` en localStorage y funciona

Funciona visualmente, pero el diseño es incorrecto. Estás simulando autenticación en vez de observar la sesión real de Firebase.

---

# FAQ

## ¿Firebase guarda la sesión en localStorage?

El SDK administra internamente la persistencia según su configuración y entorno. Para el lab no necesitas manipular ese almacenamiento directamente.

## ¿Puedo consultar `auth.currentUser`?

Sí, pero para reaccionar a inicialización y cambios de sesión es preferible `onAuthStateChanged`.

## ¿Por qué la zona pública sigue visible estando autenticado?

Porque es pública. Autenticarse no obliga a ocultar contenido público; solo habilita contenido adicional.

## ¿Una zona privada debería ser otra página?

Puede serlo en una aplicación real. Aquí usamos una única página para minimizar routing y mantener el foco en identidad y sesión.

---

## Continúa

→ [Parte 4 · Recuperación de contraseña](./04-recuperar-password.md)
