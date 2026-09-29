# Contenido extendido · Gateway + backend protegido

Complementa [Gateway + backend protegido](../02-gateway-backend-seguro.md).

## 1. Seguridad perimetral y seguridad de aplicación

El Gateway es una frontera técnica compartida. El backend conoce el recurso y la aplicación.

Por eso sus responsabilidades se superponen parcialmente, pero no son idénticas.

## 2. Gateway

Puede encargarse de:

- autenticación perimetral;
- validación de issuer/audience;
- routing;
- rate limiting;
- observabilidad;
- políticas comunes.

## 3. Backend

Debe conservar:

- validación del recurso;
- scopes/roles;
- autorización de endpoint;
- reglas de aplicación;
- reglas de negocio.

## 4. Bypass y defensa en profundidad

Si el backend queda accesible por una ruta distinta al Gateway, una API sin protección propia pierde su frontera de seguridad.

Por eso resulta razonable que el Resource Server siga protegido.

## 5. Qué no mover

No llevar al Gateway reglas que dependen de estado de negocio o propiedad del recurso.

Ejemplo: “solo el propietario de la reserva puede cancelarla”.

## 6. Diagnóstico

Separar:

- error de autenticación;
- error de routing;
- error de autorización;
- error de negocio.

El mismo status puede aparecer en fronteras diferentes; por eso los logs y la trazabilidad importan.

## 7. Ejercicio

Para una request rechazada, identifica:

- quién respondió;
- qué validación falló;
- qué evidencia lo demuestra;
- qué componente NO deberías modificar todavía.

## 8. Idea clave

Gateway y backend colaboran, pero la seguridad de aplicación no se delega completamente a infraestructura.
