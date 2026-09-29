# Evidencia y trazabilidad · cierre Parcial 1

## Evidencias útiles

- commits;
- README del proyecto;
- diagrama;
- requests/responses;
- logs;
- configuración sin secretos;
- DevLog;
- capturas solo cuando aportan algo que no puede reconstruirse mejor desde código/configuración.

## Pregunta de control

> ¿Otra persona puede entender qué se construyó, qué funcionó y qué quedó pendiente sin depender de la memoria del autor?


## Evidencia como historia técnica

La evidencia debería permitir reconstruir:

1. qué se intentó construir;
2. cómo evolucionó;
3. qué estado alcanzó;
4. qué falló;
5. qué quedó pendiente.

Por eso commits, README, DevLog, diagramas y pruebas se complementan.

## Commit no es solo “respaldo”

Un historial útil muestra decisiones e incrementos.

Ejemplos de commits informativos:

- integrar Resource Server;
- validar audience de API;
- agregar protección de endpoint;
- corregir scope incorrecto.

Un único commit final con todo el semestre reduce mucho la trazabilidad.

## Diagramas

El diagrama debe reflejar la arquitectura real, no una arquitectura aspiracional.

Si el Gateway aún no participa del flujo, no debería dibujarse como si estuviera operativo sin una nota explícita.

## Logs y requests

Los logs ayudan a demostrar comportamiento, pero deben estar sanitizados.

Nunca incluir:

- tokens completos;
- secrets;
- passwords;
- credenciales cloud.

## DevLog

El DevLog debería responder:

- qué cambió;
- por qué;
- cómo se verificó;
- qué problema apareció;
- qué queda pendiente.

## Matriz de estado

Una forma útil de cierre:

| Capacidad | Estado | Evidencia | Deuda |
|---|---|---|---|
| Login | Operativo | prueba/commit | — |
| Access Token | Operativo | claims sanitizados | — |
| Gateway | Parcial | routing | authorizer pendiente |
| Backend | Operativo | 401/403/2xx | — |

## Criterio de trazabilidad

Otra persona debería poder reconstruir el estado sin necesitar una explicación oral privada del autor.
