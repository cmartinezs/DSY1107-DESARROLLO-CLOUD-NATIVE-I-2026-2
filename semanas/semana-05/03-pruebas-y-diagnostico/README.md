# Contenido extendido · Pruebas y diagnóstico

Complementa [Pruebas 401 / 403 / 2xx y diagnóstico](../03-pruebas-y-diagnostico.md).

## 1. Probar seguridad significa probar rechazo

Un flujo seguro no se demuestra solamente con un 2xx.

También debe verificarse que condiciones inválidas sean rechazadas de forma coherente.

## 2. Casos mínimos

- sin token;
- token expirado;
- issuer incorrecto;
- audience incorrecta;
- scope insuficiente;
- token válido y autorizado.

## 3. Último checkpoint verificable

Cuando aparece un fallo, volver al último punto que sabemos que funciona.

No cambiar simultáneamente frontend, IdP, Gateway y backend.

## 4. Trazabilidad de una prueba

Registrar:

- input;
- token/claims relevantes sin exponer secretos;
- endpoint;
- status;
- frontera que respondió;
- hipótesis;
- cambio;
- resultado.

## 5. 401 vs 403

401: no existe autenticación aceptable.

403: existe autenticación aceptable, pero falta autorización.

Confundirlos hace perder tiempo y suele conducir a “arreglos” en la capa equivocada.

## 6. Árbol mental

1. ¿hay credencial?;
2. ¿es válida?;
3. ¿es para este recurso?;
4. ¿está vigente?;
5. ¿tiene permiso?;
6. ¿la regla de negocio permite?;

## 7. Ejercicio

Construye cinco requests deliberadamente distintas y predice el status antes de ejecutarlas. Luego compara predicción y resultado.

## 8. Idea clave

Diagnosticar es formular hipótesis por frontera y verificarlas, no cambiar configuración hasta que algo funcione.
