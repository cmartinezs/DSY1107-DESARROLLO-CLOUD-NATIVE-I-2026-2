# Estrategia de laboratorios · local en repo, cloud en AVA

DSY1107 busca que el estudiante aprenda **conceptos, patrones y responsabilidades Cloud Native** sin convertir los laboratorios del repositorio docente en ejercicios dependientes de infraestructura cloud.

## 1. Frontera canónica

Existen dos superficies pedagógicas distintas:

```text
Repositorio docente
→ explicación y ejemplos
→ labs locales/autocontenidos
→ checkpoints de comprensión

AVA institucional
→ contenido oficial
→ ejercicios/labs cloud
→ actividades y evidencias institucionales
```

Ambas superficies pueden abordar una misma competencia, pero no forman una única secuencia técnica ni comparten la misma raíz documental.

## 2. Laboratorios del repositorio

Todo laboratorio bajo `labs/` debe ser:

- ejecutable sin infraestructura cloud real;
- autocontenido;
- reproducible desde otra máquina con prerrequisitos razonables;
- independiente del Proyecto Formativo;
- asociado al contenido de una semana o competencia concreta;
- diseñado para hacer observable el concepto, no para enseñar una consola de proveedor.

Puede utilizar:

- infraestructura local;
- contenedores;
- servicios open source;
- simuladores didácticos;
- backends públicos simples;
- componentes equivalentes que permitan observar claramente el patrón.

El objetivo es reconocer:

- actores y componentes;
- flujo de una petición;
- responsabilidades de cada pieza;
- configuración esencial;
- errores frecuentes;
- decisiones de arquitectura;
- evidencias observables: HTTP, headers, tokens, logs, rutas, permisos, etc.

### Regla de sencillez

El laboratorio debe utilizar la **menor cantidad posible de tecnología accidental**.

No se agrega un framework, lenguaje, protocolo o herramienta solo porque exista. Si un simulador o componente local permite observar el concepto sin programar lógica irrelevante, se prefiere ese camino.

## 3. Actividades cloud del AVA

Cuando el programa institucional contemple ejercicios, guías o laboratorios sobre AWS, Azure u otro proveedor, esos recursos pertenecen al **AVA** y mantienen su carácter oficial.

El repositorio docente puede:

- indicar qué competencia del lab local se relaciona con una actividad del AVA;
- explicar equivalencias conceptuales de forma complementaria;
- preparar al estudiante para comprender la actividad oficial.

El repositorio docente no debe:

- copiar la actividad cloud del AVA dentro de `labs/`;
- crear una segunda fase obligatoria llamada “lab cloud”;
- exigir credenciales o recursos cloud para completar un lab del repo;
- hacer pasar una actividad propia por sustituto de una actividad institucional obligatoria.

## 4. Correspondencia conceptual

Puede existir una correspondencia como esta:

| Concepto | Lab local del repo | Manifestación posible en AVA/cloud |
|---|---|---|
| API Gateway | Spring Cloud Gateway local | API Gateway / API Management del proveedor indicado |
| Route | path + destino configurado | route/resource/integration |
| Integración | URI backend | integration target |
| Política transversal | filter/header/CORS | políticas gestionadas |
| IdP / Authorization Server | simulador local | IDaaS indicado en la asignatura |
| Client registration | configuración observable | app/client registration real |
| Scope / claim | datos observables en el flujo | configuración real del proveedor |

Esta tabla expresa **equivalencia pedagógica**, no una dependencia de ejecución entre ambos recursos.

## 5. Laboratorios iniciales que fijan el patrón

### Lab 1 · API Gateway local

Utiliza Spring Cloud Gateway, JSONPlaceholder y un cliente web simple para estudiar:

- cliente → gateway → backend;
- routing;
- integration/destino;
- versionado;
- HTTP nivel 2;
- políticas transversales;
- CORS.

El estudiante puede después reconocer estos mismos conceptos en actividades cloud oficiales del AVA, si corresponden a la semana.

### Lab 2 · Identidad y autorización local

Utiliza ReservApp y `mock-identity` para estudiar de forma observable:

- actores OAuth2/OIDC;
- Authorization Code + PKCE;
- ID Token vs Access Token;
- issuer, audience y expiración;
- scopes y roles;
- 401 vs 403;
- autorización técnica vs reglas de negocio;
- tenant y app registration a nivel conceptual.

`mock-identity` es deliberadamente un simulador didáctico. Su función es permitir comprender el modelo sin requerir un proveedor cloud para completar el laboratorio.

## 6. Regla para nuevos laboratorios

Antes de crear un nuevo laboratorio en `labs/`, revisar:

1. ¿Qué competencia semanal practica?
2. ¿Puede ejecutarse completamente sin infraestructura cloud real?
3. ¿Cuál es el mínimo de tecnología necesario para observar el concepto?
4. ¿Qué evidencia demuestra comprensión?
5. ¿Es independiente de RegistrApp?
6. ¿Existe una actividad cloud relacionada en AVA que solo debamos referenciar, no replicar?

Si un laboratorio requiere obligatoriamente AWS, Azure u otro proveedor para funcionar, **no pertenece a `labs/` bajo este canon**.

## 7. Relación con el Proyecto Formativo

Los labs tampoco son incrementos de RegistrApp.

```text
contenido comprendido
→ lab local independiente
→ evidencia de comprensión

──────── frontera ────────

transferencia posterior
→ proyecto-formativo/
→ incremento de RegistrApp cuando corresponda
```

La transferencia al Proyecto Formativo se documenta en su vertical propia.

## 8. Principio rector

> **El lab del repo hace observable el concepto sin depender de cloud; el AVA conserva los ejercicios cloud institucionales; el Proyecto Formativo recibe después la transferencia cuando corresponda.**
