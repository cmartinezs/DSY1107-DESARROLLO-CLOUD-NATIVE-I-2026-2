# 01B · Crear el frontend Angular

## Objetivo

Crear **desde cero** el frontend que se utilizará durante toda la guía EV1.

En esta etapa no se implementa autenticación, MSAL, OAuth2, JWT ni lógica visual compleja. El objetivo es obtener una SPA Angular funcional en `http://localhost:4200` que más adelante pueda:

- iniciar sesión mediante Microsoft Entra External ID;
- recibir tokens;
- llamar a AWS API Gateway;
- demostrar CORS y consumo de una API protegida.

> DSY1107 no evalúa diseño visual avanzado ni arquitectura compleja de frontend. El frontend existe para demostrar integración Cloud Native.

---

## Resultado esperado

Al finalizar debe existir:

```text
cloudtasks/
├── backend/
└── frontend/
    ├── package.json
    ├── angular.json
    ├── src/
    └── ...
```

Y debe abrirse:

```text
http://localhost:4200
```

mostrando una pantalla mínima de CloudTasks.

---

# 1. Verificar Node.js

Abrir una terminal.

Ejecutar:

```bash
node --version
npm --version
```

La guía requiere una versión LTS vigente compatible con la versión de Angular utilizada en el curso.

Si ambos comandos funcionan, continuar.

Si `node` o `npm` no existen, instalar Node.js LTS antes de seguir.

---

# 2. Instalar Angular CLI

Si `ng` todavía no existe:

```bash
npm install -g @angular/cli
```

Validar:

```bash
ng version
```

No continuar hasta que Angular CLI responda correctamente.

---

# 3. Crear el proyecto

Desde la carpeta:

```text
cloudtasks/
```

Ejecutar:

```bash
ng new frontend --routing --style=css --skip-git
```

Cuando Angular CLI consulte opciones adicionales, mantener la configuración simple y evitar incorporar capacidades que no aporten a EV1.

La intención es obtener una SPA Angular convencional.

> `--skip-git` evita crear un segundo repositorio Git dentro del workspace de la actividad.

---

# 4. Entrar al frontend

```bash
cd frontend
```

Instalar dependencias si Angular CLI no lo hizo automáticamente:

```bash
npm install
```

---

# 5. Ejecutar Angular

```bash
npm start
```

Si el proyecto generado no tiene script `start`, usar:

```bash
ng serve
```

Abrir:

```text
http://localhost:4200
```

Debe verse la aplicación Angular.

---

# 6. Reemplazar la pantalla inicial por una versión mínima

No se necesita diseñar una interfaz completa.

La pantalla debe comunicar únicamente que el frontend existe y está operativo.

Contenido mínimo sugerido:

```text
CloudTasks
Frontend operativo
Backend: pendiente de integración
Identidad: pendiente
```

Puede utilizarse directamente el componente raíz generado por Angular.

No crear todavía:

- navbar;
- dashboard complejo;
- componentes visuales adicionales;
- formularios extensos;
- servicios de dominio;
- librerías CSS externas.

Todo eso agrega trabajo que no forma parte de EV1.

---

# 7. Estructura funcional mínima que tendrá más adelante

La interfaz final solo necesitará:

```text
CloudTasks
├── Iniciar sesión / Cerrar sesión
├── Mi identidad
├── Tareas
│   ├── listar
│   ├── crear
│   └── eliminar
└── estado de integración
```

No implementar esas capacidades todavía.

La guía las agregará cuando exista una razón evaluativa para hacerlo.

---

# 8. Validar que el puerto sea 4200

La guía utiliza:

```text
http://localhost:4200
```

como origen local del frontend.

Ese valor será utilizado posteriormente para:

- redirect URI de la SPA en Microsoft Entra;
- pruebas de CORS;
- configuración temporal/local del backend;
- comparación con la URL cloud definitiva.

Si Angular solicita otro puerto porque `4200` está ocupado, cerrar la aplicación que lo esté utilizando antes de aceptar otro puerto.

Evitar cambiar el origen sin necesidad, porque después debe coincidir exactamente en configuraciones de CORS y autenticación.

---

# 9. Verificar desde DevTools

Abrir el navegador en:

```text
http://localhost:4200
```

Abrir DevTools.

Revisar:

```text
Console
```

No deben existir errores rojos de ejecución de Angular.

En:

```text
Network
```

la página debe cargar sus recursos correctamente.

---

# 10. Qué NO tiene que programar el alumno aquí

No se espera que el alumno implemente manualmente:

- OAuth2;
- PKCE;
- generación de JWT;
- almacenamiento de tokens;
- un sistema de usuarios;
- un formulario de login propio;
- una API falsa en Angular;
- lógica de seguridad;
- un framework CSS;
- una aplicación de negocio real.

Más adelante MSAL resolverá el protocolo OAuth2/OIDC y el frontend solo configurará lo necesario para utilizarlo correctamente.

---

# Errores frecuentes

## `ng` no se reconoce

Cerrar y volver a abrir la terminal después de instalar Angular CLI.

Validar:

```bash
ng version
```

## `npm install` falla

Antes de borrar archivos o cambiar versiones arbitrariamente:

1. comprobar conexión a Internet;
2. revisar la primera línea real del error;
3. verificar versiones de Node y npm;
4. intentar nuevamente desde la carpeta `frontend/`.

## Puerto 4200 ocupado

Cerrar el proceso que está usando el puerto.

No cambiar a `4201`, `4300` u otro puerto si no existe una razón real, porque todas las configuraciones posteriores usan el origen exacto `http://localhost:4200`.

## Pantalla en blanco

Abrir DevTools → Console.

Corregir primero el error mostrado antes de continuar hacia identidad o cloud.

---

# Puerta de validación 01B

No continuar hasta demostrar:

- [ ] Node y npm funcionan;
- [ ] Angular CLI funciona;
- [ ] existe `cloudtasks/frontend/`;
- [ ] `npm start` o `ng serve` inicia la aplicación;
- [ ] `http://localhost:4200` abre correctamente;
- [ ] DevTools no muestra errores de ejecución;
- [ ] la pantalla identifica claramente que CloudTasks frontend está operativo.

## Evidencia mínima recomendada

Una captura donde se observe:

```text
http://localhost:4200
+ pantalla CloudTasks
+ DevTools sin errores relevantes
```

No se requiere evidencia de código complejo porque todavía no existe una competencia EV1 que justifique escribirlo.
