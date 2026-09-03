# Estrategia de laboratorios · concepto primero, proveedor cuando aporta la competencia

DSY1107 busca que el estudiante aprenda **conceptos, patrones y responsabilidades Cloud Native** evitando tecnología accidental. Por defecto, los laboratorios del repositorio son locales/autocontenidos; sin embargo, se permiten laboratorios **provider-backed** cuando el uso de una capacidad administrada real constituye parte esencial del aprendizaje.

## 1. Frontera canónica

Existen tres superficies pedagógicas relacionadas pero distintas:

```text
Repositorio docente · lab local
→ explicación y ejemplos
→ ejecución autocontenida
→ checkpoints de comprensión

Repositorio docente · lab provider-backed
→ concepto
→ configuración guiada de proveedor real
→ implementación mínima
→ checkpoints verificables

AVA institucional
→ contenido oficial
→ actividades y evidencias institucionales
```

Un lab propio del repositorio nunca reemplaza una actividad institucional obligatoria del AVA.

## 2. Laboratorios locales del repositorio

Cuando la competencia puede observarse sin infraestructura cloud real, el laboratorio debe preferir una solución:

- autocontenida;
- reproducible desde otra máquina con prerrequisitos razonables;
- independiente del Proyecto Formativo;
- asociada al contenido de una semana o competencia concreta;
- diseñada para hacer observable el concepto, no para enseñar tecnología accidental.

Puede utilizar:

- infraestructura local;
- contenedores;
- servicios open source;
- simuladores didácticos;
- backends públicos simples;
- componentes equivalentes que permitan observar claramente el patrón.

## 3. Laboratorios provider-backed

Un laboratorio puede requerir un proveedor real cuando **simularlo eliminaría una parte sustantiva de la competencia**. Casos válidos incluyen, por ejemplo:

- Identity as a Service;
- autenticación federada;
- capacidades administradas específicas que el estudiante debe configurar y consumir;
- integración con un servicio cloud cuya responsabilidad sea precisamente delegar una capacidad de plataforma.

Un lab provider-backed debe:

1. declarar de forma explícita el proveedor y la razón pedagógica;
2. utilizar, cuando sea razonable, un tier gratuito o recurso sin costo obligatorio;
3. incluir cada paso de consola necesario, sin saltos implícitos;
4. separar claramente configuración cloud, código local y evidencia;
5. incorporar checkpoints que impidan avanzar si la etapa anterior no funciona;
6. evitar claves privadas, service-account keys, contraseñas o tokens reutilizables en el repositorio;
7. mantenerse independiente de RegistrApp salvo transferencia posterior documentada.

### Regla de progresión

Si el laboratorio agrega mecanismos incrementales, se implementan uno por uno y se verifica cada etapa antes de avanzar.

Ejemplo:

```text
Email/Password completo
→ Register
→ Login
→ sesión
→ zona privada
→ Password Reset
→ Logout
→ checkpoint PASS
→ recién entonces proveedor federado Google
```

## 4. Regla de sencillez

Todo laboratorio debe utilizar la **menor cantidad posible de tecnología accidental**.

No se agrega un framework, lenguaje, protocolo o herramienta solo porque exista. Si JavaScript + Vite basta para observar Firebase Authentication, no se agrega un backend artificial. Si un simulador local permite observar completamente otro concepto, se prefiere el simulador.

## 5. Actividades cloud del AVA

Cuando el programa institucional contemple ejercicios, guías o laboratorios sobre AWS, Azure u otro proveedor, esos recursos pertenecen al **AVA** y mantienen su carácter oficial.

El repositorio docente puede:

- indicar qué competencia se relaciona con una actividad del AVA;
- explicar equivalencias conceptuales de forma complementaria;
- preparar al estudiante para comprender la actividad oficial;
- incluir un lab propio provider-backed cuando tenga una intención pedagógica diferenciada y no pretenda reemplazar la actividad institucional.

El repositorio docente no debe:

- copiar literalmente una actividad cloud del AVA;
- hacer pasar una actividad propia por sustituto de una actividad institucional obligatoria;
- mezclar credenciales institucionales o secretos en el material público.

## 6. Correspondencia conceptual

| Concepto | Lab local posible | Lab provider-backed posible |
|---|---|---|
| API Gateway | Spring Cloud Gateway | API Gateway / API Management real si la competencia lo exige |
| IdP / Authorization Server | simulador local | Firebase Auth, Entra External ID u otro IDaaS |
| Client registration | configuración observable | app/client registration real |
| Scope / claim | datos observables en flujo local | configuración/claims reales del proveedor |
| Autenticación federada | simulación conceptual | Google/Microsoft/otro proveedor real |

La elección depende de qué parte de la competencia se quiere hacer observable.

## 7. Laboratorios que fijan el patrón

### Lab · API Gateway local

Utiliza Spring Cloud Gateway, JSONPlaceholder y un cliente web simple para estudiar:

- cliente → gateway → backend;
- routing;
- integration/destino;
- versionado;
- HTTP nivel 2;
- políticas transversales;
- CORS.

### Lab · Identidad y autorización local

Utiliza un simulador didáctico para estudiar de forma observable:

- actores OAuth2/OIDC;
- Authorization Code + PKCE;
- ID Token vs Access Token;
- issuer, audience y expiración;
- scopes y roles;
- 401 vs 403.

### Lab · Firebase Authentication Mini App

Utiliza Firebase Authentication real porque la competencia es consumir **Identity as a Service**. El estudiante implementa primero Email/Password de extremo a extremo y luego agrega Google como proveedor federado.

## 8. Regla para nuevos laboratorios

Antes de crear un nuevo laboratorio en `labs/`, revisar:

1. ¿Qué competencia semanal practica?
2. ¿Puede observarse completamente de forma local?
3. Si requiere proveedor real, ¿el proveedor es parte sustantiva del aprendizaje o solo complejidad accidental?
4. ¿Cuál es el mínimo de tecnología necesario?
5. ¿Qué evidencia demuestra comprensión?
6. ¿Qué checkpoint impide avanzar con una etapa rota?
7. ¿Es independiente de RegistrApp?
8. ¿Existe una actividad cloud relacionada en AVA que debamos distinguir explícitamente?

## 9. Relación con el Proyecto Formativo

Los labs no son incrementos automáticos de RegistrApp.

```text
contenido comprendido
→ lab independiente
→ evidencia de comprensión

──────── frontera ────────

transferencia posterior
→ proyecto-formativo/
→ incremento de RegistrApp cuando corresponda
```

## 10. Principio rector

> **Primero hacer observable la competencia con el mínimo de tecnología. Se usa cloud real cuando la capacidad administrada forma parte del aprendizaje; AVA conserva sus actividades oficiales y el Proyecto Formativo recibe la transferencia después.**
