# Proyecto formativo transversal · RegistrApp

RegistrApp es el **desafío transversal del semestre** de DSY1107. Vive en una raíz independiente del contenido semanal y evoluciona progresivamente aplicando lo aprendido en clases.

## Regla canónica

```text
semanas/                  → qué se aprende y practica
examples/ + labs/         → ejemplos y experiencias del contenido
proyecto-formativo/       → qué aplica el estudiante en RegistrApp
```

RegistrApp **no se usa como ejemplo para enseñar contenido nuevo**. Primero se aprende y practica con casos independientes; después se transfiere esa competencia al proyecto.

## Continuidad obligatoria

RegistrApp es **un único proyecto vivo** durante las 18 semanas.

```text
Semana 1
→ Semana 2 recibe ese estado
→ Semana 3 recibe el estado acumulado
→ ...
→ Semana 18 recibe todo el histórico anterior
```

Cada checkpoint debe evidenciar:

1. estado de entrada;
2. contenido nuevo transferible;
3. incremento realizado;
4. evidencia antes/después;
5. estado de salida;
6. deuda pendiente;
7. trazabilidad mediante DevLog y commits/archivos cuando corresponda.

→ [Contrato de arquitectura y continuidad](./ARQUITECTURA-Y-CONTINUIDAD.md)

## Progreso del semestre

| Semana | Checkpoint |
|---|---|
| 01 | [Inicio y arquitectura/API mínima](./semana-01/) |
| 02 | [Gateway, gestión de API e identidad según avance](./semana-02/) |
| 03 | [JWT, claims y seguridad según avance](./semana-03/) |
| 04 | [MSAL, Spring Security y arquitectura segura según avance](./semana-04/) |
| 05 | [Checkpoint reservado](./semana-05/) |
| 06 | [Checkpoint reservado](./semana-06/) |
| 07 | [Checkpoint reservado](./semana-07/) |
| 08 | [Checkpoint reservado](./semana-08/) |
| 09 | [Checkpoint reservado](./semana-09/) |
| 10 | [Checkpoint reservado](./semana-10/) |
| 11 | [Checkpoint reservado](./semana-11/) |
| 12 | [Checkpoint reservado](./semana-12/) |
| 13 | [Checkpoint reservado](./semana-13/) |
| 14 | [Checkpoint reservado](./semana-14/) |
| 15 | [Checkpoint reservado](./semana-15/) |
| 16 | [Checkpoint reservado](./semana-16/) |
| 17 | [Checkpoint reservado](./semana-17/) |
| 18 | [Cierre acumulativo del semestre](./semana-18/) |

→ [Ver roadmap detallado](./ROADMAP-SEMANAL.md)

## Avance clase a clase

Una semana no representa necesariamente un único incremento. Puede acumular varios avances pequeños:

```text
contenido clase 1 → incremento A
contenido clase 2 → incremento B
contenido clase 3 → incremento C

checkpoint semanal = estado acumulado A+B+C
```

Si una clase o semana no habilita un incremento, también se registra. El histórico no debe tener huecos silenciosos.

## Regla de autonomía

El profesor puede orientar, revisar y tensionar decisiones, pero no debe construir previamente la misma solución de RegistrApp que luego se espera que el estudiante reproduzca.

La evidencia debe demostrar **transferencia del aprendizaje y progreso incremental**, no copia de la ejemplificación de clase.
