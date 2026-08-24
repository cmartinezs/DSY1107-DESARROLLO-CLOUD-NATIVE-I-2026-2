# Checklist operativo semanal · DSY1107

Este documento define el ciclo mínimo para preparar, ejecutar y cerrar cada semana de **DSY1107 · Desarrollo Cloud Native I**.

No reemplaza la planificación institucional. Su función es mantener alineados cronograma, repo, portal web, Drive, avance real de cada sección y la estrategia concepto → local/neutral → cloud real.

## Regla previa

DSY1107 mantiene dos verticales separadas:

```text
CONTENIDO
concepto
→ ejemplo independiente
→ mini práctica/lab independiente
→ evidencia
→ cloud cuando corresponda

DESAFÍO TRANSVERSAL
contenido comprendido
→ transferencia a RegistrApp
→ incremento
→ checkpoint
```

> **Primero se aprende fuera de RegistrApp. Después se transfiere a RegistrApp.**

→ [Definición canónica](./DESAFIO-TRANSVERSAL-REGISTRAPP.md)

## 1. Antes de liberar la semana

- [ ] Confirmar semana curricular, fechas y feriados.
- [ ] Revisar cronograma institucional, material AVA y evaluaciones próximas.
- [ ] Revisar por separado el avance real de 002D y 003D.
- [ ] Crear o reconciliar `semanas/semana-XX/`.
- [ ] Identificar los conceptos cloud de la semana antes de escoger servicios concretos.
- [ ] Preparar explicaciones y ejemplos autocontenidos **sin depender de RegistrApp**.
- [ ] Diseñar mini ejercicios/labs de contenido independientes del desafío transversal.
- [ ] Diseñar primero una experiencia local/neutral cuando sea pedagógicamente viable.
- [ ] Mantener el lab local más simple que el concepto que enseña.
- [ ] Preparar el laboratorio cloud real como transferencia del mismo patrón, no como una receta de consola.
- [ ] Definir por separado qué contenido ya comprendido puede transferirse a RegistrApp.
- [ ] Preparar ejemplos en `examples/` y labs en `labs/`, manteniendo una única fuente canónica.
- [ ] Verificar enlaces entre repo, portal y Material Público.
- [ ] Revisar que los Mermaid utilicen sintaxis compatible con GitHub: IDs simples, labels entre comillas y `<br/>` para saltos de línea.

## 2. Ruta mínima que debe ver el estudiante

La página principal debe responder:

1. **Qué concepto aprender esta semana.**
2. **Con qué ejemplo independiente comprenderlo.**
3. **Qué mini práctica/laboratorio independiente realizar.**
4. **Qué servicio cloud real representa después ese concepto.**
5. **Qué parte de RegistrApp puede evolucionar después de comprender el contenido.**
6. **Qué es obligatorio y qué es opcional.**
7. **Qué debería poder explicar sin depender del nombre del proveedor.**

## 3. Durante las clases

- [ ] Registrar el avance real por sección.
- [ ] No forzar sincronía entre 002D y 003D.
- [ ] No usar RegistrApp como ejemplo conductor del contenido.
- [ ] Pedir primero explicación de actores, responsabilidades y flujo antes de configurar infraestructura.
- [ ] Usar errores observables: 401, 403, CORS, route incorrecta, timeout, integración, etc.
- [ ] Evitar que programación accidental eclipse el concepto cloud.
- [ ] Documentar equivalencias concepto ↔ local ↔ cloud cuando el lab llegue a proveedor real.
- [ ] Habilitar trabajo en RegistrApp solo desde competencias efectivamente comprendidas.
- [ ] Registrar dudas frecuentes que merezcan una aclaración transversal.

## 4. Cierre semanal

- [ ] Publicar o actualizar el resumen real de cada sección.
- [ ] Reconciliar `semanas/`, `labs/`, `examples/`, `docs/` y `page/`.
- [ ] Confirmar que los labs de contenido tengan hogar canónico y no dependan del desafío transversal.
- [ ] Registrar qué parte local/neutral quedó validada y qué transferencia a cloud sigue pendiente.
- [ ] Actualizar checkpoint de RegistrApp **por separado** cuando corresponda.
- [ ] Comprobar que no existan secretos o credenciales versionadas.
- [ ] Revisar enlaces públicos y documentación reproducible.
- [ ] Registrar deuda pedagógica/técnica para la semana siguiente.
- [ ] Actualizar `data/weekly/semana-XX.yml` por sección con plan vs avance real, evidencias, DevLog agregado, estado de RegistrApp, bloqueos y foco siguiente.
- [ ] Mantener en `null` cualquier métrica sin evidencia confiable; no estimar ni completar datos personales.

## 5. Estadísticas semanales

El dataset se rige por [`ESTANDAR-ESTADISTICAS-SEMANALES.md`](./ESTANDAR-ESTADISTICAS-SEMANALES.md).

Al cerrar la semana, verificar que el YAML permita derivar al menos:

- cobertura curricular por sección;
- sesiones/bloques planificados vs realizados;
- participación agregada en DevLog cuando ya exista medición;
- evidencia formativa disponible;
- estado del desafío RegistrApp;
- diferencias de avance entre 002D y 003D;
- bloqueos y deuda para la semana siguiente.

El archivo estadístico es agregado y operacional: no contiene nombres ni notas individuales.

## 6. Criterio de calidad

Una semana está lista cuando un estudiante puede responder:

- qué concepto estoy aprendiendo;
- con qué ejemplo independiente lo comprendo;
- cómo lo observo localmente;
- qué servicio cloud lo implementa después;
- qué cambia y qué permanece al cambiar de proveedor;
- qué debería ser capaz de defender técnicamente al finalizar;
- qué puedo transferir después al desafío RegistrApp.

Y el docente puede responder, desde `data/weekly/`, qué se planificó, qué ocurrió realmente por sección y qué arrastre queda para la semana siguiente.

El objetivo es aprender capacidades cloud transferibles y luego aplicarlas, no memorizar consolas ni convertir el desafío transversal en una guía resuelta.
