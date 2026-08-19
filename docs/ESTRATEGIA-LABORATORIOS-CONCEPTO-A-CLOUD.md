# Estrategia de laboratorios · concepto → implementación cloud

DSY1107 busca que el estudiante aprenda **conceptos, patrones y responsabilidades Cloud Native**, no que memorice una secuencia de clics de un proveedor.

Por eso, cada tema práctico que dependa de infraestructura cloud seguirá, cuando sea razonable, una progresión en dos etapas.

## 1. Etapa conceptual: laboratorio local o neutral

Primero se implementa o simula el concepto en un entorno controlado, reproducible y de bajo costo.

Puede utilizar:

- infraestructura local;
- contenedores;
- servicios open source;
- simuladores didácticos;
- servicios SaaS gratuitos o alternativos;
- componentes equivalentes que permitan observar claramente el patrón.

El objetivo no es replicar cada detalle del servicio cloud. El objetivo es reconocer:

- actores y componentes;
- flujo de una petición;
- responsabilidades de cada pieza;
- configuración esencial;
- errores frecuentes;
- decisiones de arquitectura;
- evidencias observables: HTTP, headers, tokens, logs, rutas, permisos, etc.

### Regla de sencillez

El laboratorio conceptual debe utilizar la **menor cantidad posible de tecnología accidental**.

No se agrega un framework, lenguaje, protocolo o herramienta solo porque exista. Si un starter o simulador permite observar el concepto sin programar lógica irrelevante, se prefiere ese camino.

## 2. Etapa de transferencia: laboratorio real de la asignatura

Después de comprender el patrón, se realiza el laboratorio institucional o equivalente usando la nube y el proveedor que corresponda al contenido oficial.

En esta segunda etapa el estudiante debe poder mapear conscientemente:

```text
concepto aprendido → elemento del proveedor
```

Por ejemplo:

| Concepto | Laboratorio conceptual | Implementación cloud |
|---|---|---|
| API Gateway | Spring Cloud Gateway local | Amazon API Gateway / servicio indicado |
| Route | Path + destino configurado | Route / resource / integration |
| Integración | URI backend | Integration target |
| Política transversal | filter/header/CORS | policy/configuración gestionada |
| IdP / Authorization Server | simulador o proveedor neutral | IDaaS indicado en la asignatura |
| Client registration | configuración local observable | app/client registration real |
| Scope / claim | datos observables en el flujo | configuración real del proveedor |

La tecnología cambia; **la explicación conceptual debe sobrevivir al cambio**.

## 3. Qué no buscamos

Un laboratorio no está bien resuelto si el estudiante solo puede decir:

> “Hice clic aquí porque la guía decía que lo hiciera”.

Debe poder explicar, al menos:

1. qué problema resuelve el componente;
2. quién lo utiliza;
3. qué recibe;
4. qué decisión toma o qué transforma;
5. qué entrega al siguiente componente;
6. qué equivalente encontró luego en el proveedor cloud.

## 4. Evidencia de transferencia

Cuando exista el par local → cloud, el README de la entrega cloud debe incluir una sección breve:

```markdown
## Del laboratorio conceptual al laboratorio cloud

| Concepto | En local/neutral | En cloud |
|---|---|---|
| ... | ... | ... |

### Qué cambió
...

### Qué se mantuvo
...
```

No se espera una comparación comercial de proveedores. Se espera demostrar que el estudiante reconoce el mismo patrón bajo implementaciones distintas.

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

Luego estos conceptos se transfieren al laboratorio real de API Management/API Gateway en cloud.

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

`mock-identity` es deliberadamente un simulador didáctico. Posteriormente se reemplaza por un proveedor real sin cambiar el modelo mental aprendido.

## 6. Regla para nuevos laboratorios

Antes de crear un nuevo laboratorio dependiente de AWS, Azure u otro proveedor, revisar estas preguntas:

1. ¿Cuál es el concepto independiente del proveedor?
2. ¿Puede observarse localmente o con una alternativa neutral de forma sencilla?
3. ¿Qué partes deben simularse y cuáles deben ser reales?
4. ¿Qué evidencia demuestra que el concepto fue comprendido?
5. ¿Cuál será el laboratorio cloud que permitirá transferir lo aprendido?
6. ¿Qué tabla de equivalencias podrá construir el estudiante entre ambas experiencias?

Si la etapa local agrega más complejidad que el propio concepto, debe simplificarse o descartarse.

## 7. Principio rector

> **Primero el concepto. Después el servicio. Finalmente, la capacidad de reconocer el mismo patrón en otra tecnología.**

El objetivo de DSY1107 no es producir operadores de una consola específica, sino estudiantes capaces de comprender y transferir patrones Cloud Native entre implementaciones.