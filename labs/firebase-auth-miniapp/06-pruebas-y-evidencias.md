# Parte 6 · Pruebas, evidencias y criterio de término

← [Volver al índice](./README.md)

## Objetivo

Demostrar que la miniapp funciona como sistema completo y que el estudiante comprende lo que Firebase está resolviendo.

No basta con mostrar una captura del formulario.

---

# Matriz de pruebas obligatoria

| ID | Escenario | Resultado esperado |
|---|---|---|
| AUTH-01 | Abrir app sin sesión | Zona pública visible; zona privada oculta |
| AUTH-02 | Registrar email nuevo | Usuario creado en Firebase y sesión iniciada |
| AUTH-03 | Registrar email repetido | Firebase rechaza la operación |
| AUTH-04 | Login con credenciales válidas | Sesión autenticada; zona privada visible |
| AUTH-05 | Login con credenciales inválidas | Sesión no iniciada; zona privada oculta |
| AUTH-06 | Recargar con sesión vigente | Firebase restaura estado y zona privada vuelve a mostrarse |
| AUTH-07 | Logout | Sesión finaliza y zona privada se oculta |
| AUTH-08 | Solicitar password reset | Firebase envía el correo de recuperación |
| AUTH-09 | Cambiar password desde enlace Firebase | Password anterior falla; nueva password funciona |
| AUTH-10 | Login con Google | Sesión autenticada y misma zona privada |
| AUTH-11 | Cancelar popup Google | App sigue operativa y no inicia sesión |

---

# Evidencia mínima

Entrega evidencia suficiente para reconstruir lo que probaste:

1. captura de `Authentication > Sign-in method` mostrando Email/Password y Google;
2. captura de `Authentication > Users` con al menos un usuario de laboratorio;
3. zona pública sin autenticación;
4. Register exitoso;
5. Login exitoso;
6. zona privada autenticada;
7. Logout;
8. correo de recuperación recibido, ocultando datos innecesarios;
9. Google Sign-In exitoso;
10. matriz AUTH-01…AUTH-11 marcada PASS/FAIL;
11. breve DevLog de problemas encontrados y solución aplicada.

No publiques:

- passwords;
- tokens;
- service account keys;
- credenciales privadas;
- información personal innecesaria.

---

# Criterio de término

El laboratorio está terminado cuando:

```text
Email/Password funciona completo
AND
Password Reset funciona usando Firebase
AND
sesión y Logout funcionan
AND
Google fue agregado después del gate
AND
AUTH-01..AUTH-11 están verificados
AND
el estudiante puede explicar el flujo
```

Que la aplicación "se vea bonita" no reemplaza estas condiciones.

---

# Preguntas de comprensión

Responde brevemente:

1. ¿Qué responsabilidad delegamos a Firebase Authentication?
2. ¿Cuál es la diferencia entre Register y Login?
3. ¿Por qué `onAuthStateChanged` es preferible a mantener un booleano propio `isLoggedIn`?
4. ¿Por qué no debemos guardar passwords en `localStorage`?
5. ¿Por qué mostrar una sección privada en frontend no equivale a proteger una API?
6. ¿Qué cambia conceptualmente al agregar Google?
7. ¿Qué se mantiene igual entre Login Email/Password y Google Sign-In?
8. ¿Por qué el lab utiliza el reset administrado por Firebase en vez de una pantalla custom?
9. ¿Cuándo sí tendría sentido obtener un ID token en el frontend?
10. ¿Qué debería hacer un backend real al recibir ese token?

---

# Extensiones opcionales

Solo después de terminar todo lo obligatorio:

## A. Estado de carga

Evitar parpadeo de UI mientras Firebase restaura sesión.

## B. Mensajes amigables

Traducir códigos Firebase a mensajes orientados al usuario sin perder el código técnico en consola.

## C. Mostrar proveedor

Inspeccionar `user.providerData` y mostrar si la sesión proviene de password o Google.

## D. Preferencia local no sensible

Usar `localStorage` para una preferencia como tema claro/oscuro y contrastarlo con el estado de autenticación administrado por Firebase.

## E. Separar vistas

Convertir Register/Login/Private en vistas o rutas distintas, manteniendo Firebase como fuente de verdad.

---

# Rúbrica rápida de autoevaluación

## Nivel mínimo

- Register y Login funcionan.
- Zona privada responde a sesión.
- Logout funciona.

## Nivel esperado

- Todo lo anterior.
- Password Reset completo.
- Google Sign-In completo.
- Matriz AUTH completa.
- Explicación conceptual correcta.

## Nivel avanzado

- Todo lo anterior.
- Manejo claro de loading y errores.
- Código organizado sin duplicación innecesaria.
- Puede explicar la frontera entre autenticación frontend y autorización backend.

---

## Si algo falla

→ [Troubleshooting y FAQ general](./07-troubleshooting-faq.md)
