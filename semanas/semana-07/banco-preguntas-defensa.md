# Banco de preguntas · Defensa técnica EV1

Este banco es una base reutilizable para defensas breves. No constituye un cuestionario que deba aplicarse completo.

## Regla operativa

Para grupos de tres integrantes y una ventana aproximada de cinco minutos:

- una pregunta por integrante;
- respuesta objetivo: 60–75 segundos;
- una repregunta corta solo si hace falta evidencia adicional;
- seleccionar preguntas que cubran áreas distintas de la rúbrica.

---

## API Manager y arquitectura

1. ¿Por qué usar API Manager en vez de exponer directamente el backend al frontend?
2. Sigue una solicitud desde el frontend hasta el backend pasando por API Manager. ¿Qué responsabilidad cumple cada componente?
3. Si mañana reemplazamos el backend pero queremos evitar cambios en el frontend, ¿qué rol puede cumplir API Manager?
4. ¿Qué problemas resuelve CORS y qué riesgo tiene configurarlo con permisos excesivamente amplios?
5. ¿Qué debería ocurrir si una ruta del backend existe pero no fue configurada correctamente en API Manager?
6. ¿Qué ventajas aporta mantener una URL pública estable aunque cambie la ubicación real del backend?
7. Si una API comienza a recibir demasiadas solicitudes, ¿qué política podrías aplicar en el gateway antes de llegar al backend?

## IDaaS, OAuth y OIDC

8. ¿Qué diferencia existe entre autenticación y autorización dentro de su solución?
9. ¿Qué responsabilidad cumple el IDaaS y por qué el backend no necesita almacenar contraseñas de usuarios?
10. ¿Qué diferencia hay entre un access token y la identidad del usuario autenticado?
11. ¿Qué agrega OpenID Connect sobre OAuth 2.0?
12. ¿Por qué una SPA no debería guardar un client secret?
13. ¿Por qué Authorization Code + PKCE es apropiado para una SPA?
14. Si un atacante intercepta el authorization code pero no conoce el code verifier, ¿qué debería ocurrir?
15. ¿Qué función cumplen redirect URI, client ID y scopes en el registro de la aplicación?
16. ¿Qué responsabilidad tiene MSAL al integrar autenticación en una SPA?

## JWT y autorización

17. ¿Qué debe validar un servicio antes de confiar en un JWT?
18. ¿Por qué no basta con comprobar que el token está correctamente firmado?
19. ¿Qué significan issuer y audience?
20. Un token es válido y no está expirado, pero fue emitido para otra API. ¿Debería aceptarse? ¿Por qué?
21. Un usuario inició sesión correctamente, pero intenta acceder a una operación para la que no tiene permisos. ¿Qué debería ocurrir?
22. ¿Qué diferencia conceptual existe entre una respuesta 401 y una 403?
23. ¿Cómo pueden utilizarse claims o roles para tomar decisiones de autorización?

## Integración y evidencia

24. Describe el flujo completo: login → token → frontend → API Manager → backend → respuesta.
25. ¿Cómo demostrarías que una ruta protegida funciona correctamente y no solo que responde cuando todo está bien?
26. ¿Qué pruebas mínimas realizarías para evidenciar respuestas 200, 401 y 403?
27. Si el frontend funciona al llamar directamente al backend pero falla al utilizar API Manager, ¿dónde comenzarías a diagnosticar?
28. ¿Cómo comprobarías que API Manager está reenviando la solicitud al backend correcto?
29. ¿Qué evidencia mostrarías para demostrar que el JWT realmente está siendo validado por la arquitectura?
30. Si una ruta devuelve el JSON correcto pero acepta cualquier token, ¿considerarías completa la integración? Fundamenta.

## Repreguntas ultracortas

Usar solo cuando la respuesta principal es demasiado general:

- ¿Dónde ocurre eso en su arquitectura?
- ¿Quién emite el token?
- ¿Quién lo valida?
- ¿Qué campo del token revisarías?
- ¿Qué respuesta HTTP esperarías?
- ¿Qué cambiaría si quitamos el gateway?
- ¿Dónde mostrarías esa configuración?
- ¿Cómo lo demostrarías en una prueba?
