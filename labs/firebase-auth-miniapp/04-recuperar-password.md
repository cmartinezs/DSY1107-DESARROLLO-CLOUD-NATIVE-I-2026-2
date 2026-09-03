# Parte 4 · Recuperación de contraseña con Firebase

← [Volver al índice](./README.md)

## Objetivo

Implementar recuperación de contraseña usando el flujo **estándar administrado por Firebase**.

En este laboratorio **no construiremos una pantalla propia para definir la nueva contraseña**.

El flujo será:

```text
miniapp
→ sendPasswordResetEmail(...)
→ Firebase envía email
→ usuario abre enlace
→ pantalla administrada por Firebase
→ usuario define nueva contraseña
→ vuelve a la miniapp
→ Login con nueva contraseña
```

---

## 1. Agregar formulario

Dentro de la zona de autenticación:

```html
<form id="reset-form">
  <h2>Recuperar contraseña</h2>
  <input id="reset-email" type="email" placeholder="Correo" required />
  <button type="submit">Enviar correo de recuperación</button>
</form>
```

---

## 2. Importar la operación Firebase

```javascript
import { sendPasswordResetEmail } from "firebase/auth";
```

---

## 3. Enviar solicitud

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

---

## 4. Probar correctamente

1. Cierra sesión.
2. Usa un correo real registrado en el proyecto Firebase.
3. Solicita recuperación.
4. Revisa bandeja de entrada.
5. Revisa spam si no aparece.
6. Abre el email de Firebase.
7. Sigue el enlace.
8. Usa la pantalla administrada por Firebase para definir una nueva contraseña.
9. Regresa a la miniapp.
10. Intenta Login con la contraseña antigua: debe fallar.
11. Intenta Login con la nueva contraseña: debe funcionar.

---

## Por qué no construimos nuestra propia pantalla de reset

Firebase permite personalizar flujos más avanzados, pero hacerlo introduciría conceptos adicionales:

- action codes;
- validación del código;
- rutas de recuperación;
- manejo de expiración;
- confirmación manual de nueva contraseña.

Eso es válido en un producto real, pero no es necesario para alcanzar el objetivo de este laboratorio.

Aquí interesa observar que un servicio IDaaS puede administrar de extremo a extremo una capacidad sensible.

---

# Checkpoint 4

- [ ] el email de recuperación llega;
- [ ] el enlace abre el flujo de Firebase;
- [ ] puedes cambiar la contraseña;
- [ ] la contraseña anterior deja de funcionar;
- [ ] la nueva contraseña funciona;
- [ ] no implementaste una pantalla custom de nueva contraseña.

---

# Errores comunes

## No llega el correo

Comprueba:

1. bandeja spam;
2. que el email sea correcto;
3. que estás usando el proyecto Firebase esperado;
4. que Authentication está habilitado;
5. que no estás probando con una cuenta diferente a la que creaste.

## El correo llega pero el enlace expiró

Solicita un nuevo correo. Los enlaces de recuperación no deben tratarse como enlaces permanentes.

## Estoy intentando cambiar la contraseña directamente desde JavaScript sin abrir el correo

Ese no es el flujo de recuperación que estamos estudiando. La prueba debe demostrar posesión/acceso al correo mediante el enlace enviado por Firebase.

## Quiero hacer una pantalla `/reset-password`

No para este lab. Eso corresponde a una evolución posterior y agregaría complejidad accidental.

---

# FAQ

## ¿Firebase conoce mi nueva contraseña?

Firebase Authentication administra la credencial. Tu aplicación cliente no debe almacenarla.

## ¿Puedo cambiar el texto del email?

Firebase ofrece personalización de plantillas en la consola. No es requisito del laboratorio.

## ¿Necesito configurar SMTP?

No para el flujo básico de Firebase Authentication utilizado aquí.

## ¿Por qué el mensaje dice "Si la cuenta corresponde"?

En interfaces de autenticación conviene evitar revelar innecesariamente si una dirección específica existe o no en el sistema.

---

## Gate obligatorio antes de Google

Antes de avanzar deben estar en PASS:

- [ ] Register Email/Password;
- [ ] Login Email/Password;
- [ ] sesión restaurada al recargar;
- [ ] zona privada;
- [ ] Logout;
- [ ] recuperación de contraseña completa.

Si alguno falla, **no habilites Google todavía**.

→ [Parte 5 · Agregar Google Sign-In](./05-google-sign-in.md)
