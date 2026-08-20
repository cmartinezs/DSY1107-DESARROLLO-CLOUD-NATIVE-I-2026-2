# Checklist operativo semanal · DSY1107

Este documento define el ciclo mínimo para preparar, ejecutar y cerrar cada semana de **DSY1107 · Desarrollo Cloud Native I**.

No reemplaza la planificación institucional. Su función es mantener alineados cronograma, repo, portal web, Drive, avance real de cada sección y la estrategia concepto → local/neutral → cloud real.

## 1. Antes de liberar la semana

- [ ] Confirmar semana curricular, fechas y feriados.
- [ ] Revisar cronograma institucional, material AVA y evaluaciones próximas.
- [ ] Revisar por separado el avance real de 002D y 003D.
- [ ] Crear o reconciliar `semanas/semana-XX/`.
- [ ] Identificar los conceptos cloud de la semana antes de escoger servicios concretos.
- [ ] Diseñar primero una experiencia local/neutral cuando sea pedagógicamente viable.
- [ ] Mantener el lab local más simple que el concepto que enseña.
- [ ] Preparar el laboratorio cloud real como transferencia del mismo patrón, no como una receta de consola.
- [ ] Actualizar ReservApp cuando el contenido permita reutilizar el dominio transversal.
- [ ] Preparar ejemplos en `examples/` y labs en `labs/`, manteniendo una única fuente canónica.
- [ ] Verificar enlaces entre repo, portal y Material Público.

## 2. Ruta mínima que debe ver el estudiante

La página principal debe responder:

1. **Qué concepto aprender esta semana.**
2. **Qué laboratorio local/neutral realizar.**
3. **Qué servicio cloud real representa después ese concepto.**
4. **Qué parte de ReservApp se utiliza o evoluciona.**
5. **Qué es obligatorio y qué es opcional.**
6. **Qué debería poder explicar sin depender del nombre del proveedor.**

## 3. Durante las clases

- [ ] Registrar el avance real por sección.
- [ ] No forzar sincronía entre 002D y 003D.
- [ ] Pedir primero explicación de actores, responsabilidades y flujo antes de configurar infraestructura.
- [ ] Usar errores observables: 401, 403, CORS, route incorrecta, timeout, integración, etc.
- [ ] Evitar que programación accidental eclipse el concepto cloud.
- [ ] Documentar equivalencias concepto ↔ local ↔ cloud cuando el lab llegue a proveedor real.
- [ ] Registrar dudas frecuentes que merezcan una aclaración transversal.

## 4. Cierre semanal

- [ ] Publicar o actualizar el resumen real de cada sección.
- [ ] Reconciliar `semanas/`, `labs/`, `examples/`, `docs/` y `page/`.
- [ ] Confirmar que los labs tengan hogar canónico en `labs/` y las semanas actúen como navegación curricular.
- [ ] Registrar qué parte local/neutral quedó validada y qué transferencia a cloud sigue pendiente.
- [ ] Actualizar checkpoint de ReservApp cuando corresponda.
- [ ] Comprobar que no existan secretos o credenciales versionadas.
- [ ] Revisar enlaces públicos y documentación reproducible.
- [ ] Registrar deuda pedagógica/técnica para la semana siguiente.

## 5. Criterio de calidad

Una semana está lista cuando un estudiante puede entrar al portal y responder:

- qué concepto estoy aprendiendo;
- cómo lo observo localmente;
- qué servicio cloud lo implementa después;
- qué cambia y qué permanece al cambiar de proveedor;
- dónde está el lab;
- qué debería ser capaz de defender técnicamente al finalizar.

El objetivo es aprender capacidades cloud transferibles, no memorizar consolas o nombres de servicios.