# Desafío transversal del semestre · RegistrApp

## Propósito

**RegistrApp** es el desafío transversal formativo de DSY1107 durante el semestre 2026-2.

Su objetivo es que el estudiante tome lo aprendido semana a semana y lo transfiera a una solución que evoluciona durante el semestre.

RegistrApp **no es el dominio usado para explicar los contenidos de clase** y tampoco debe convertirse en el ejemplo conductor de cada concepto.

## Regla pedagógica principal

La asignatura mantiene dos verticales que se relacionan, pero no se mezclan.

### Vertical A · contenido de la semana

El contenido se enseña por sí mismo.

Secuencia recomendada:

```text
concepto
→ explicación
→ ejemplo pequeño y autocontenido
→ mini ejercicio o laboratorio independiente
→ evidencia de comprensión
```

Los ejemplos pueden usar dominios mínimos y distintos entre sí. Deben elegirse por su capacidad de explicar el concepto con claridad, no por su relación con RegistrApp.

### Vertical B · desafío transversal RegistrApp

Después de aprender y practicar el contenido, el estudiante analiza cómo aplicarlo a RegistrApp.

Secuencia recomendada:

```text
contenido comprendido
→ identificar qué aporta a RegistrApp
→ diseñar o implementar el incremento
→ registrar decisiones y evidencia
→ dejar checkpoint para la semana siguiente
```

El profesor puede orientar el objetivo técnico y los criterios, pero RegistrApp debe conservar un componente importante de trabajo y decisión del estudiante.

## Qué NO hacer

No usar este patrón:

```text
concepto
→ ejemplo sobre RegistrApp
→ demostración sobre RegistrApp
→ laboratorio sobre RegistrApp
→ desafío RegistrApp
```

Eso elimina la separación entre aprender una competencia y transferirla a un problema transversal.

Tampoco se debe construir el contenido semanal alrededor de las entidades o reglas de negocio de RegistrApp.

## Qué SÍ hacer

Ejemplo para una semana de OAuth2/OIDC:

```text
Contenido
- explicar actores OAuth2/OIDC con un caso mínimo independiente;
- mostrar Authorization Code + PKCE con un cliente y una API de ejemplo;
- practicar access token vs ID token con tokens sintéticos;
- resolver casos 401/403 fuera de RegistrApp.

RegistrApp
- decidir qué actor cumple cada responsabilidad;
- definir qué recursos requieren protección;
- proponer scopes/claims adecuados;
- actualizar la arquitectura y dejar evidencia.
```

Ejemplo para una semana de JWT:

```text
Contenido
- leer JWT sintéticos;
- identificar iss, aud, exp y scope;
- distinguir decodificación de verificación;
- ejecutar una matriz de autorización sobre una API mínima.

RegistrApp
- aplicar las mismas decisiones al desafío;
- definir audience y permisos;
- documentar dónde validar token y dónde aplicar reglas de negocio;
- actualizar el checkpoint transversal.
```

## Continuidad

RegistrApp puede avanzar cada semana, pero no está obligado a consumir todos los minutos de clase ni a aparecer en cada ejemplo.

Cada checkpoint debe indicar:

1. contenido semanal que se está transfiriendo;
2. decisión tomada por el estudiante;
3. artefacto creado o modificado;
4. evidencia reproducible;
5. deuda o siguiente incremento.

## Separación con evaluaciones sumativas

RegistrApp es formativo y transversal.

- No debe resolver indirectamente una evaluación sumativa.
- No reutiliza el caso de una evaluación como plantilla.
- Puede pausarse durante semanas de evaluación.
- Las competencias técnicas aprendidas sí se transfieren.

## Regla canónica

> **Primero se aprende fuera de RegistrApp. Después se transfiere a RegistrApp.**

Esta regla debe respetarse al crear material, ejemplos, laboratorios, planificación por sección y actividades semanales.