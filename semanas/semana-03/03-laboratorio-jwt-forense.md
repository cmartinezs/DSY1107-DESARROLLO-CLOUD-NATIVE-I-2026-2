# 3 · Laboratorio · JWT forense

**Objetivo:** interpretar y defender decisiones de acceso antes de configurar un proveedor cloud.

## Parte 1 · anatomía

Usa JWT locales/sintéticos preparados para el laboratorio. Identifica header, payload y signature. Decodifica header/payload y registra los claims encontrados.

## Parte 2 · tres sospechosos

Analiza tres tokens: uno con audience incorrecta, uno expirado y uno válido pero sin `reservations.write`. Para cada uno responde qué condición falla y en qué capa convendría detectarla.

## Parte 3 · matriz HTTP

Ejecuta llamadas contra el starter/local de ReservApp cuando esté disponible:

1. sin `Authorization`;
2. bearer mal formado;
3. token inválido;
4. token válido con scope de lectura sobre lectura;
5. token válido sin scope de escritura sobre escritura;
6. token válido con scope correcto.

Registra request mínimo, status, body y componente que produjo la respuesta.

## Parte 4 · decodificar no es confiar

Modifica manualmente el payload de un token de laboratorio sin regenerar correctamente la firma. Comprueba que poder leer el payload modificado no lo convierte en un token confiable. Esta experiencia se realiza únicamente con claves/tokens locales del laboratorio.

## Parte 5 · arquitectura

Actualiza el diagrama:

```text
usuario → cliente → identidad
                ↓ token
cliente → gateway → reservapp-api
```

Agrega `iss`, `aud`, scopes y responsabilidades de validación/autorización.

## Dinámica viva

Antes de ejecutar, cada equipo recibe tres payloads y debe emitir un «dictamen»: **aceptable, no aceptable o información insuficiente**, justificando con claims. Luego se revela la evidencia de firma/configuración para mostrar por qué mirar el payload nunca basta.

## Evidencia

- tabla de casos;
- respuestas HTTP reproducibles;
- diagrama;
- explicación decodificación vs verificación;
- DevLog técnico.

Nunca subir secretos ni tokens reales reutilizables.