# 01B · Crear el frontend Angular

## Objetivo

Crear **desde cero** el frontend que se utilizará durante toda la guía integrada.

En esta etapa no se implementa autenticación, MSAL, OAuth2, JWT ni lógica visual compleja. El objetivo es obtener una SPA Angular funcional en `http://localhost:4200`.

> El frontend existe para hacer observable la integración Cloud Native, no para introducir arquitectura frontend avanzada.

## Antes de comenzar

Completar:

- [00A · Preparar el entorno](./00a-preparar-entorno.md)
- [00B · Git/GitHub aplicado a la guía](./00b-git-github-flujo-guia.md)
- [00D · Scaffolding vs código del estudiante](./00d-scaffolding-vs-codigo-estudiante.md)

Verificar:

```bash
node --version
npm --version
ng version
```

Registrar qué versión mayor de Angular CLI se utilizará. La etapa MSAL seleccionará una versión compatible con ese Angular en vez de instalar dependencias al azar.

---

## Resultado esperado

```text
guia/ev1/
├── backend/
└── frontend/
    ├── package.json
    ├── package-lock.json
    ├── angular.json
    ├── src/
    └── ...
```

Y:

```text
http://localhost:4200
```

debe mostrar CloudTasks.

---

# 1. Verificar Angular CLI

Si `ng` no existe:

```bash
npm install -g @angular/cli
```

Cerrar/reabrir terminal y comprobar:

```bash
ng version
```

No continuar con un CLI que no pueda ejecutarse.

---

# 2. Crear el proyecto

Desde:

```text
guia/ev1/
```

Ejecutar:

```bash
ng new frontend --routing --style=css --skip-git
```

## Respuestas a prompts adicionales

Las preguntas exactas cambian entre versiones de Angular. Aplicar estas reglas:

```text
Routing                    → sí
Styles                     → CSS
SSR / SSG / prerendering   → NO
Git interno                → NO (--skip-git)
framework CSS              → ninguno
```

### Por qué no usar SSR/SSG aquí

CloudTasks utiliza APIs de navegador y un flujo SPA con MSAL. Incorporar server-side rendering agrega otra frontera de ejecución y no aporta al aprendizaje de esta guía.

La aplicación debe ser una **SPA de navegador** simple.

---

# 3. Confirmar un solo repositorio Git

Después de generar:

```bash
cd frontend
git rev-parse --show-toplevel
```

Debe devolver la raíz del repositorio personal DSY1107, no `frontend/`.

Si aparece:

```text
frontend/.git
```

se creó accidentalmente un repo anidado. Corregirlo antes de seguir.

---

# 4. Revisar versiones generadas

Desde `frontend/`:

```bash
ng version
npm list @angular/core --depth=0
```

Registrar la versión mayor, por ejemplo:

```text
ANGULAR_MAJOR=22
```

No editar `package.json` para “subir todo a latest” después de generar el proyecto.

---

# 5. Ejecutar Angular

```bash
npm start
```

Si el proyecto generado no incluye script `start`:

```bash
ng serve
```

Abrir:

```text
http://localhost:4200
```

**CHECKPOINT 01B-0 · scaffolding**

- [ ] Angular compila.
- [ ] puerto 4200 activo.
- [ ] Console sin errores críticos.
- [ ] no existe repo Git anidado.
- [ ] versión Angular conocida.

---

# 6. Dejar una UI mínima

Usar el componente raíz generado y reemplazar el contenido visual por algo equivalente a:

```html
<main>
  <h1>CloudTasks</h1>
  <p>Frontend operativo</p>
  <p>Backend: pendiente de integración</p>
  <p>Identidad: pendiente</p>
</main>
```

No crear todavía:

```text
navbar
dashboard
routing adicional
formularios complejos
servicios de dominio
state manager
framework CSS
login propio
```

---

# 7. Validar nuevamente

```bash
npm start
```

DevTools:

```text
Console → sin errores de ejecución
Network → bundles cargan correctamente
```

**CHECKPOINT 01B-1 · frontend mínimo**

- [ ] pantalla CloudTasks visible.
- [ ] frontend sigue en `http://localhost:4200`.
- [ ] DevTools sin error crítico.

---

# 8. Mantener puerto 4200

Ese origen se utilizará posteriormente en:

```text
CORS local
redirect URI Entra
MSAL
comparación con URL cloud
```

Si Angular propone 4201 porque 4200 está ocupado, identificar y detener primero el proceso conflictivo en vez de aceptar un puerto nuevo por comodidad.

---

# 9. Qué genera Angular y qué hará el estudiante

| Pieza | Origen |
|---|---|
| `angular.json` | Angular CLI |
| `package.json` | Angular CLI |
| `package-lock.json` | npm/Angular CLI |
| `src/main.ts` | Angular CLI |
| estructura del componente raíz | Angular CLI |
| UI mínima | estudiante adapta |
| `HttpClient` local | etapa 01C |
| MSAL Angular | etapa 03 |
| token Bearer | `MsalInterceptor` |

No reescribir archivos generados si no existe una razón concreta.

---

# 10. Errores frecuentes

## `ng` no se reconoce

Cerrar/reabrir terminal y ejecutar:

```bash
ng version
```

## `npm install` / generación falla

Revisar, en orden:

```text
conectividad
Node version
npm version
primer error real
compatibilidad Angular/Node
```

No borrar `package-lock.json` como primera reacción.

## Se activó SSR accidentalmente

Para esta práctica es preferible regenerar correctamente una SPA simple antes de arrastrar archivos/configuración server que después compliquen MSAL.

## Puerto 4200 ocupado

Detener proceso conflictivo y conservar el origen canónico.

## Pantalla en blanco

DevTools → Console antes de cambiar dependencias.

---

# Puerta de validación 01B

```text
Node/npm PASS
Angular CLI PASS
proyecto generado PASS
SPA sin SSR PASS
repo Git único PASS
Angular major conocido PASS
localhost:4200 PASS
DevTools PASS
UI mínima PASS
```

No continuar a 01C hasta que este estado sea reproducible.
