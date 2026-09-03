# Troubleshooting y FAQ · Firebase Auth Mini App

← [Volver al índice](./README.md)

Este archivo concentra problemas transversales del laboratorio. Antes de cambiar código al azar, identifica **en qué frontera ocurre el fallo**.

```text
navegador/UI
→ código JavaScript
→ Firebase SDK
→ configuración Firebase Console
→ proveedor Email/Password o Google
```

---

# Método de diagnóstico

Cuando algo falle:

1. lee el mensaje visible de la app;
2. abre DevTools > Console;
3. identifica `error.code`;
4. determina si el problema es de UI, código, SDK o configuración;
5. revisa un cambio a la vez;
6. vuelve a ejecutar el escenario exacto.

No soluciones errores borrando código indiscriminadamente.

---

# Problemas frecuentes

## La página queda en blanco

Revisa primero la consola JavaScript.

Causas típicas:

- error de sintaxis;
- selector que devuelve `null`;
- import incorrecto;
- archivo `firebase.js` mal referenciado;
- template string sin cerrar.

## `auth/operation-not-allowed`

El proveedor no está habilitado.

Revisa:

```text
Firebase Console
→ Authentication
→ Sign-in method
```

## `auth/email-already-in-use`

La identidad ya existe. Usa Login o prueba Register con otra cuenta.

## `auth/weak-password`

La contraseña no cumple la política exigida.

## `auth/invalid-email`

El valor enviado no tiene formato válido de email.

## `auth/invalid-credential`

Las credenciales suministradas no pudieron autenticarse.

No escribas mensajes que revelen innecesariamente si el email existe.

## `auth/too-many-requests`

Firebase puede limitar temporalmente operaciones ante muchos intentos sospechosos o repetitivos. No sigas ejecutando el mismo intento en bucle.

## `auth/network-request-failed`

Revisa conexión de red, extensiones de navegador, VPN/proxy y consola.

## `auth/popup-blocked`

El navegador bloqueó el popup. El llamado debe originarse directamente desde una interacción del usuario.

## `auth/popup-closed-by-user`

El usuario cerró el popup. Trátalo como cancelación, no como corrupción de la app.

## `auth/cancelled-popup-request`

Se iniciaron operaciones de popup que se superponen. Deshabilita temporalmente el botón mientras la autenticación está en curso si necesitas robustecer la UX.

## Login funciona pero la zona privada no cambia

Verifica:

- `onAuthStateChanged` registrado;
- misma instancia `auth`;
- IDs HTML correctos;
- ausencia de errores anteriores en consola.

## Al recargar parece que la sesión desaparece y vuelve

La restauración es asíncrona. Puedes agregar un estado inicial `loading` como mejora opcional.

## Password Reset no llega

Revisa:

- spam;
- dirección escrita;
- proyecto Firebase activo;
- proveedor Email/Password;
- que estés probando el flujo esperado.

## Cambié `localStorage` y pude mostrar la zona privada

Eso demuestra precisamente por qué `localStorage` no debe representar autenticación. La UI puede manipularse desde el navegador.

Firebase Auth sigue siendo la fuente de verdad.

---

# FAQ conceptual

## ¿Autenticación y autorización son lo mismo?

No.

**Autenticación:** ¿quién eres?

**Autorización:** ¿qué puedes hacer?

Este lab se concentra principalmente en autenticación y en una representación visual simple de acceso privado.

## ¿La API key de Firebase es una contraseña?

No. La configuración web debe llegar al navegador. No la confundas con credenciales privadas de servidor.

## ¿Entonces cualquiera puede usar mi Firebase?

La seguridad no se basa en ocultar `firebaseConfig`. Se basa en la configuración de servicios, reglas, autenticación y controles apropiados. En este lab solo usamos Authentication.

## ¿Por qué no guardamos el usuario completo en localStorage?

Porque Firebase SDK ya administra la sesión y entrega el usuario mediante su API. Duplicar ese estado genera inconsistencias.

## ¿Puedo mostrar `user.uid`?

Sí, con fines didácticos. Es un identificador, no una contraseña.

## ¿Qué es `UserCredential`?

Es el resultado de operaciones de autenticación como Register/Login y contiene, entre otros datos, el objeto `user` asociado.

## ¿Qué es `onAuthStateChanged`?

Es un observador que Firebase ejecuta cuando el estado de autenticación se resuelve o cambia.

## ¿Por qué Register deja al usuario autenticado?

Porque crear exitosamente la identidad mediante `createUserWithEmailAndPassword` también establece una sesión para ese usuario.

## ¿Google Sign-In necesita otro sistema de zona privada?

No. Una vez Firebase entrega un usuario autenticado, la misma lógica de estado puede reaccionar sin importar el proveedor.

## ¿Qué pasa si una misma persona usa el mismo email con proveedores distintos?

La vinculación de proveedores y resolución de cuentas puede requerir decisiones adicionales según configuración y caso de producto. No es requisito de este laboratorio.

## ¿Por qué no usamos backend?

Porque añadirlo mezclaría dos aprendizajes: autenticación mediante IDaaS y validación/autorización del lado servidor. Aquí aislamos el primero.

## ¿Cómo sería en una app real con backend?

De forma conceptual:

```text
usuario
→ Firebase Auth
→ frontend obtiene identidad/token
→ request a API
→ backend valida token
→ backend aplica autorización/reglas de negocio
```

No implementes esa extensión dentro del lab obligatorio.

## ¿Puedo copiar código de una aplicación productiva que ya use Firebase?

Úsalo como referencia, no como plantilla ciega. Una aplicación productiva puede incluir backend, onboarding, verificación de email, telemetría, guards, perfiles y reglas que este laboratorio deliberadamente no necesita.

---

# FAQ de entrega

## ¿Debo subir capturas con mi contraseña?

No.

## ¿Debo subir `node_modules`?

No. Debe estar en `.gitignore`.

## ¿Puedo ocultar mi email en las capturas?

Sí. La evidencia debe probar el comportamiento, no exponer datos personales innecesarios.

## ¿Debo entregar el proyecto Firebase?

Entrega la evidencia indicada por el docente. Nunca entregues service-account credentials ni claves privadas.

## ¿Qué hago si una prueba queda FAIL?

Documenta el fallo, diagnostícalo y corrígelo antes de declarar terminado el laboratorio. Un FAIL conocido no debe ocultarse.

---

# Ruta de regreso

→ [Parte 1 · Preparación](./01-preparacion-y-firebase.md)  
→ [Parte 2 · Email/Password](./02-email-password.md)  
→ [Parte 3 · Sesión y zona privada](./03-sesion-zona-privada.md)  
→ [Parte 4 · Recuperar password](./04-recuperar-password.md)  
→ [Parte 5 · Google](./05-google-sign-in.md)  
→ [Parte 6 · Pruebas y evidencias](./06-pruebas-y-evidencias.md)
