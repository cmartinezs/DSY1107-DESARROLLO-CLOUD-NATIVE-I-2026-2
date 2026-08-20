# Banco de dinámicas vivas · DSY1107

Este banco reúne dinámicas reutilizables para mantener activa la asignatura sin convertir Cloud Native en una sucesión de tutoriales de consola. Las actividades deben reforzar **conceptos, responsabilidades y transferencia entre implementaciones**.

## Regla operativa

Por semana se recomienda elegir **1 dinámica principal** y, como máximo, una segunda muy breve. Deben apoyar el contenido institucional y el avance real de cada sección.

## Dinámicas disponibles

### Concepto sin marca · 5–15 min
Explicar una arquitectura o flujo sin mencionar AWS, Azure ni otro proveedor. Solo después se pide mapear cada capacidad al servicio concreto.

### Incidente de producción ficticio · 10–20 min
Presentar síntomas y evidencia mínima: 401, 403, CORS, timeout, ruta incorrecta, audience inválida, token equivocado, DNS, health check, etc. Los estudiantes deben proponer hipótesis y evidencia que buscarían.

### ¿Quién tiene la responsabilidad? · 5–10 min
Dado un problema, decidir si corresponde a cliente, gateway, IdP, backend, servicio de datos, infraestructura o lógica de negocio y justificarlo.

### Local → cloud → otro cloud · 15–30 min
Partir desde un lab neutral/local y mapear sus conceptos al servicio real de la asignatura y, cuando aporte valor, a un segundo proveedor. No se busca memorizar nombres sino reconocer equivalencias y diferencias.

### Arquitecto por 5 minutos · 5–10 min
Antes de abrir herramientas, dibujar componentes, flujo y responsabilidades para una necesidad concreta.

### Bug/config de la semana · 5–15 min
Analizar una configuración pequeña con un error deliberado: CORS, route, issuer, scope, Docker, variable de entorno, puerto, health check, etc.

### Mini defensa · 2–3 min
Sin PPT. Explicar una decisión del lab: qué hace el gateway, por qué 401 y no 403, quién emite el token, qué valida la API, qué cambió entre local y cloud.

### PR misterioso · 10–15 min
Revisar un cambio de infraestructura/configuración/documentación y decidir si debería integrarse. Se valora detectar riesgo operativo, secretos, reproducibilidad y claridad.

### README destacado
Reconocer documentación técnica especialmente reproducible: arquitectura, requisitos, pasos, pruebas, configuración sin secretos, decisiones y comparación local ↔ cloud. Puede recibir compensación/bonificación cuando el docente lo determine.

### Anti-pattern de la semana · 5–15 min
Mostrar una solución que funciona pero confunde responsabilidades, acopla innecesariamente al proveedor o expone secretos. Los alumnos deben explicar por qué es riesgosa.

### Boss fight · 30–60 min
Cada varias semanas, un incidente o requerimiento integrador sobre ReservApp que combine varios conceptos ya trabajados, sin introducir servicios nuevos por sorpresa.

### Pregunta que una IA podría responder mal · 5–15 min
Se entrega una afirmación técnicamente plausible sobre cloud, OAuth/OIDC, containers, storage, mensajería u observabilidad. Deben verificarla con documentación, pruebas o arquitectura.

### Mapa de portabilidad · 10–20 min
Completar una matriz:

| Capacidad | Local/neutral | Cloud real | Qué se mantiene |
|---|---|---|---|
| ... | ... | ... | ... |

Esta dinámica refuerza directamente la estrategia conceptual del curso.

## Rotación sugerida

```text
API Management → Concepto sin marca / Bug config
Identidad      → ¿Quién responde? / Incidente 401-403
Containers     → Arquitecto / Local→cloud
Datos/storage  → Mapa de portabilidad
Observabilidad → Incidente ficticio
Cierre unidad  → Boss fight / Mini defensa
```

## Criterio de éxito

La dinámica es útil si obliga a razonar sobre **qué problema existe, qué componente lo resuelve y cómo demostrarlo**. Si se convierte en una lista de nombres de servicios, debe rediseñarse.

## Registro mínimo docente por sección

```text
Sección:
Dinámica usada:
Concepto reforzado:
Qué error/duda apareció:
¿Repetir?: sí / no / con ajustes
```

002D y 003D pueden usar dinámicas diferentes si sus checkpoints reales son distintos.
