# 00A · Preparar el entorno de trabajo

## Objetivo

Instalar y verificar **antes de comenzar la EV1** las herramientas que se utilizarán en las guías posteriores.

La intención no es convertir la instalación de herramientas en parte de la evaluación. El objetivo es evitar que un alumno llegue a CORS, OAuth2, JWT o AWS con un entorno incompleto y termine diagnosticando problemas que en realidad pertenecen a Git, Java, Node o al IDE.

> Instalar solo lo necesario. Cuando existan herramientas equivalentes, esta guía distingue entre **obligatoria**, **recomendada** y **alternativa**.

---

## 1. Resumen de herramientas

| Herramienta | Estado | Uso en EV1 |
|---|---|---|
| Cuenta GitHub | obligatoria | repositorio y entrega |
| Git | obligatoria | control de versiones |
| GitHub Desktop | recomendada | Git mediante interfaz gráfica |
| GitHub CLI (`gh`) | recomendada | autenticación y operaciones GitHub desde terminal |
| JDK 21 | obligatoria | Spring Boot |
| IntelliJ IDEA | recomendada para backend | crear y ejecutar Spring Boot |
| Node.js LTS + npm | obligatoria | Angular |
| Angular CLI | obligatoria | crear y ejecutar frontend |
| VS Code | alternativa recomendada para frontend | edición Angular |
| WebStorm | alternativa a VS Code | edición Angular/TypeScript |
| Navegador Chromium/Firefox | obligatoria | SPA, DevTools y CORS |
| Postman | recomendada | pruebas HTTP independientes del navegador |

No es necesario instalar Maven globalmente: el backend utilizará **Maven Wrapper** generado con el proyecto (`mvnw` / `mvnw.cmd`).

---

# 2. Crear o verificar cuenta GitHub

Se necesita una cuenta personal de GitHub que el estudiante pueda utilizar durante todo el semestre.

Sitio oficial:

https://github.com/

Verificar que sea posible iniciar sesión desde navegador antes de instalar clientes adicionales.

## Configuración recomendada

Activar autenticación multifactor cuando corresponda y conservar acceso al método de recuperación.

No compartir tokens de acceso, códigos MFA ni credenciales en el repositorio.

---

# 3. Instalar Git

Git es obligatorio incluso cuando se use GitHub Desktop.

Sitio oficial:

https://git-scm.com/downloads

### Windows

Descargar **Git for Windows** desde el sitio oficial e instalarlo con las opciones por defecto salvo instrucción del docente.

También puede instalarse con `winget`:

```powershell
winget install --id Git.Git -e --source winget
```

Cerrar y volver a abrir PowerShell después de instalar.

### Verificar

```powershell
git --version
```

Debe aparecer una versión de Git.

## Configurar identidad de commits

Usar el nombre real y el correo asociado a GitHub o el correo académico elegido para el curso:

```powershell
git config --global user.name "Nombre Apellido"
git config --global user.email "correo@ejemplo.cl"
```

Comprobar:

```powershell
git config --global user.name
git config --global user.email
```

> Estos datos quedan registrados en los commits. No utilizar correos ajenos ni identidades ficticias.

---

# 4. Instalar GitHub Desktop

**Recomendado**, especialmente para estudiantes que todavía no dominan Git por terminal.

Sitio oficial:

https://desktop.github.com/

Documentación oficial:

https://docs.github.com/desktop

GitHub Desktop permite clonar, revisar cambios, hacer commits, pull, push y trabajar con branches desde una interfaz gráfica.

## Instalación

1. Descargar GitHub Desktop.
2. Ejecutar el instalador.
3. Abrir la aplicación.
4. Seleccionar **Sign in to GitHub.com**.
5. Completar la autenticación en navegador.
6. Verificar nombre y correo configurados para commits.

## Importante

GitHub Desktop no sustituye la necesidad de comprender al menos:

```text
clone
pull
commit
push
branch
```

pero evita que la sintaxis de terminal sea una barrera innecesaria para EV1.

---

# 5. Instalar GitHub CLI (`gh`)

**Recomendado**. No es obligatorio para construir CloudTasks, pero facilita autenticación, repositorios y diagnóstico desde terminal.

Sitio oficial:

https://cli.github.com/

En Windows puede instalarse con:

```powershell
winget install --id GitHub.cli
```

Cerrar y volver a abrir la terminal.

Verificar:

```powershell
gh --version
```

Autenticar:

```powershell
gh auth login
```

Elegir:

```text
GitHub.com
HTTPS
Login with a web browser
```

Comprobar:

```powershell
gh auth status
```

> `gh` y GitHub Desktop pueden coexistir. No son reemplazos obligatorios entre sí.

---

# 6. Instalar JDK 21

Spring Boot utilizará **Java 21** durante la guía.

Puede utilizarse una distribución OpenJDK 21 mantenida, por ejemplo:

- Microsoft Build of OpenJDK;
- Eclipse Temurin;
- OpenJDK provisto/configurado por IntelliJ.

La condición importante es que el proyecto y Maven Wrapper ejecuten con Java 21.

## Verificar

```powershell
java -version
javac -version
```

Ambos deben indicar Java 21.

### Si existen varias versiones de Java

No desinstalar versiones utilizadas por otras asignaturas sin necesidad.

En IntelliJ se seleccionará explícitamente JDK 21 para el proyecto backend.

---

# 7. Instalar IntelliJ IDEA

**Recomendado y utilizado por esta guía para el backend Spring Boot.**

Sitio oficial:

https://www.jetbrains.com/idea/download/

A partir de las versiones actuales IntelliJ IDEA utiliza una distribución unificada: las funciones esenciales de Java/Kotlin siguen disponibles gratuitamente y las funciones avanzadas se habilitan con una suscripción Ultimate.

## Estudiantes con licencia académica

JetBrains ofrece acceso educativo gratuito a estudiantes y docentes elegibles.

Información:

https://www.jetbrains.com/academy/student-pack/

La licencia educativa puede habilitar las capacidades avanzadas de IntelliJ y otras herramientas JetBrains.

## Instalación recomendada

1. Descargar IntelliJ IDEA para Windows.
2. Ejecutar el instalador.
3. Mantener las asociaciones `.java` y opciones de launcher solo si se desean.
4. Abrir IntelliJ.
5. Iniciar sesión con JetBrains Account si se utilizará licencia académica.
6. Confirmar que JDK 21 puede seleccionarse al crear un proyecto.

No instalar plugins aleatorios antes de comenzar la guía.

---

# 8. Instalar Node.js LTS y npm

Angular depende de Node.js.

Sitio oficial:

https://nodejs.org/

Instalar una versión **LTS** compatible con la versión vigente de Angular utilizada en el curso.

`npm` se instala junto con Node.js.

Cerrar y volver a abrir la terminal.

Verificar:

```powershell
node --version
npm --version
```

No continuar si alguno de los dos comandos falla.

---

# 9. Instalar Angular CLI

Documentación oficial:

https://angular.dev/tools/cli/setup-local

Después de instalar Node/npm:

```powershell
npm install -g @angular/cli
```

Verificar:

```powershell
ng version
```

Debe aparecer información de Angular CLI y Node.js.

## Si PowerShell bloquea scripts

No modificar políticas de seguridad de forma indiscriminada.

Probar primero desde **Command Prompt**, Git Bash o una terminal nueva. Si el bloqueo persiste, revisar el mensaje exacto antes de cambiar `ExecutionPolicy`.

---

# 10. Elegir editor para Angular

El frontend no requiere un IDE específico. Elegir **una** de estas alternativas.

## Opción A · Visual Studio Code

Recomendación por defecto por ser gratuito y liviano.

Sitio oficial:

https://code.visualstudio.com/

Extensiones útiles, no obligatorias:

- Angular Language Service;
- ESLint, solo si el proyecto lo utiliza;
- EditorConfig, si se requiere.

Evitar instalar múltiples extensiones que formateen el mismo código.

## Opción B · WebStorm

Alternativa excelente para estudiantes que prefieran JetBrains.

Sitio oficial:

https://www.jetbrains.com/webstorm/

WebStorm dispone de opciones gratuitas/no comerciales y forma parte de las herramientas disponibles mediante programas educativos de JetBrains cuando corresponda.

Si el estudiante ya tiene Student Pack, puede utilizar WebStorm en vez de VS Code sin cambiar ningún paso funcional de la guía.

> **No instalar ambos por obligación.** Basta con VS Code o WebStorm.

---

# 11. Instalar navegador y conocer DevTools

Se necesita un navegador moderno con herramientas de desarrollo.

Recomendados:

- Chrome;
- Edge;
- Firefox.

El estudiante debe poder abrir DevTools con `F12` y ubicar como mínimo:

```text
Console
Network
Application / Storage
```

Esto es esencial para diagnosticar:

- CORS;
- redirects OAuth/OIDC;
- requests al API Gateway;
- códigos HTTP;
- headers `Authorization`;
- errores de frontend.

> Para CORS, la evidencia válida proviene del navegador. Postman no aplica la política Same-Origin del navegador.

---

# 12. Instalar Postman

**Recomendado**, no obligatorio si el estudiante maneja `curl` correctamente.

Sitio oficial:

https://www.postman.com/downloads/

Postman se utilizará para aislar pruebas HTTP del frontend.

Ejemplo:

```text
si Postman → 200
pero navegador → error CORS
```

entonces el backend probablemente responde correctamente y el problema está en la política aplicada al navegador.

---

# 13. Qué NO instalar para EV1

No es necesario instalar por adelantado:

- Maven global;
- Docker Desktop;
- Kubernetes;
- MySQL/PostgreSQL;
- AWS CLI;
- Azure CLI;
- Terraform;
- Gradle;
- Keycloak.

Si una etapa posterior requiere una herramienta adicional, la guía la introducirá en ese momento.

Esto reduce conflictos, consumo de recursos y diagnósticos innecesarios.

---

# 14. Verificación completa del entorno

Abrir una terminal nueva y ejecutar:

```powershell
git --version
gh --version
java -version
javac -version
node --version
npm --version
ng version
```

`gh` puede omitirse si el alumno optó por no instalarlo.

Maven **no aparece** en esta validación porque no se exige Maven global. Se verificará mediante `mvnw` después de generar el proyecto Spring Boot.

También comprobar manualmente:

- [ ] GitHub abre y permite iniciar sesión;
- [ ] GitHub Desktop abre y está autenticado, si se utilizará;
- [ ] IntelliJ IDEA abre;
- [ ] JDK 21 aparece disponible en IntelliJ;
- [ ] VS Code o WebStorm abre;
- [ ] navegador abre DevTools;
- [ ] Postman abre, si se utilizará.

---

# Puerta de validación 00A

No comenzar la creación de CloudTasks hasta que:

- [ ] Git funciona;
- [ ] existe acceso a GitHub;
- [ ] Java 21 funciona;
- [ ] IntelliJ puede utilizar JDK 21;
- [ ] Node y npm funcionan;
- [ ] Angular CLI funciona;
- [ ] existe un editor para Angular;
- [ ] el estudiante sabe abrir DevTools;
- [ ] no se han instalado herramientas cloud o frameworks adicionales sin necesidad.

## Resultado esperado

El entorno queda preparado para que los errores posteriores pertenezcan realmente a la arquitectura EV1 y no a una instalación incompleta.
