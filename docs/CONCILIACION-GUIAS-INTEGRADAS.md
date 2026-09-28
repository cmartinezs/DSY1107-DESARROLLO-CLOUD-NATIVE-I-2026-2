# Conciliación arquitectónica · Guías integradas

## Contexto

La guía integrada EV1 fue rescatada primero, sin mezclar ese rescate con una decisión
arquitectónica. Esta rama existe exclusivamente para resolver dónde debe vivir y cómo debe
relacionarse ese material con las superficies canónicas actuales del curso.

## Objetivo

Conciliar:

```text
guias-integradas/
labs/
examples/
docs/
semanas/
proyecto-formativo/
page/
data/weekly/
```

sin perder conocimiento, sin duplicar fuentes de verdad y sin convertir una práctica
integrada en una tercera trayectoria curricular por accidente.

## Principios de decisión

1. `semanas/` responde qué se aprende y cuándo.
2. `examples/` contiene ejemplos acotados.
3. `labs/` contiene experiencias reproducibles, locales o provider-backed.
4. `docs/` conserva conocimiento transversal por dominio.
5. `proyecto-formativo/` contiene la transferencia a RegistrApp.
6. `page/` es una vista derivada.
7. Una guía integrada puede enlazar varias superficies, pero no debe duplicarlas.
8. CloudTasks es una implementación de referencia para preparar competencias; no es EV1 ni
   sustituye las instrucciones institucionales.

## Preguntas que debe resolver esta rama

- ¿`guias-integradas/` permanece como superficie canónica propia o se reclasifica?
- Si se reclasifica, ¿qué parte pertenece a `labs/` y qué parte a `docs/`?
- ¿Dónde deben vivir starters y materializadores?
- ¿Cómo se indexa la ruta end-to-end sin romper la regla concepto primero?
- ¿Qué documentos son fuente canónica y cuáles vistas derivadas?
- ¿Cómo se mantiene la trazabilidad histórica de Semana 3 sin presentar ese estado como vigente?
- ¿Qué cambios necesita la web para navegar la guía sin alterar Semana 8?
- ¿Qué validadores deben quedar transversales y cuáles específicos de una guía?

## Trabajo esperado

### Fase 1 · inventario

Clasificar cada archivo rescatado como:

```text
concepto transversal
ejemplo
laboratorio
guía operativa
starter
tooling
checkpoint histórico
troubleshooting
vista derivada
```

### Fase 2 · mapa de destinos

Definir destino canónico antes de mover archivos.

### Fase 3 · migración

Mover o consolidar material preservando enlaces y trazabilidad.

### Fase 4 · deduplicación

Eliminar únicamente duplicados cuya fuente canónica esté confirmada.

### Fase 5 · navegación

Actualizar README, índices y web.

### Fase 6 · validación

Comprobar:

- enlaces;
- Mermaid;
- ausencia de secretos;
- ausencia de fuentes paralelas contradictorias;
- separación contenido / RegistrApp;
- correspondencia con AVA;
- vigencia temporal.

## Restricción

Esta rama no debe reabrir ni reconstruir el PR histórico #2. Parte del rescate ya realizado
en `feat/rescue-ev1-integrated-guide`.
