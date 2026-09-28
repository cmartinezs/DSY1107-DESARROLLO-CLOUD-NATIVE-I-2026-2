# 00A · Preparar el entorno de trabajo

## Objetivo

Instalar y verificar **antes de comenzar CloudTasks** las herramientas que se utilizarán en los pasos posteriores de la guía.

El objetivo es evitar que un problema de Git, Java, Node, Angular o del IDE termine confundiéndose después con un problema de CORS, OAuth2/OIDC, JWT o cloud.

> Instalar solo lo necesario. Cuando existan herramientas equivalentes, esta guía distingue entre **obligatoria**, **recomendada** y **alternativa**.

---

## 1. Resumen de herramientas

| Herramienta | Estado | Uso en la guía |
|---|---|---|
| Cuenta GitHub | obligatoria | repositorio personal y checkpoints |
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

Se necesita una cuenta personal de GitHub que el estudiante pueda utilizar durante el semestre.

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

## Windows · dos formas de instalación

Las dos alternativas instalan **Git for Windows**. El paquete `Git.Git` de `winget` automatiza la instalación de Git for Windows; no instala un “Git de PowerShell” distinto.

La diferencia práctica para el estudiante está en **cómo se realiza la instalación, qué opciones de integración ve y qué terminal decide utilizar después**.

### Opción A · instalador `.exe` de Git for Windows

Descargar el instalador desde el sitio oficial y ejecutarlo manualmente.

Esta alternativa muestra el asistente de Git for Windows y permite revisar explícitamente opciones como:

- integración con `PATH`;
- Git Bash;
- integración con Windows Terminal;
- editor por defecto;
- OpenSSH;
- manejo de finales de línea.

Git for Windows incluye **Git Bash**.

Después de instalar, puede abrirse:

```text
Git Bash
```

y comprobar:

```bash
git --version
pwd
ls
```

Git también puede utilizarse desde PowerShell si `git.exe` quedó disponible en el `PATH` de Windows.

### Opción B · instalación mediante `winget`

Desde PowerShell:

```powershell
winget install --id Git.Git -e --source winget
```

Cerrar y volver a abrir PowerShell después de instalar.

Verificar directamente:

```powershell
git --version
```

Si funciona, Git está disponible desde el `PATH` de Windows y puede utilizarse cómodamente desde:

```text
PowerShell
Command Prompt
terminal integrada de VS Code
terminal integrada de IntelliJ/WebStorm
```

Git Bash normalmente también queda instalado porque forma parte de Git for Windows.

## ¿Qué terminal usaremos?

Para evitar mezclar sintaxis, la guía diferencia los ejemplos:

```text
PowerShell
→ comandos Windows / winget / mvnw.cmd

Git Bash
→ comandos Bash / ./mvnw / ls / pwd
```

**PowerShell es suficiente** para la ruta base si `git --version` funciona correctamente.

Git Bash es útil cuando:

- el estudiante prefiere Bash;
- una instrucción utiliza sintaxis Unix;
- se quiere practicar comandos similares a Linux sin usar todavía WSL.

> **Git Bash no es WSL.** Git Bash entrega una shell Bash y utilidades Unix sobre Windows. WSL2 ejecuta una distribución Linux real, como Ubuntu. La ruta ★ Advanced Developer utiliza WSL2 + Ubuntu.

## Checkpoint de instalación de Git

### PowerShell

```powershell
git --version
where.exe git
```

Debe aparecer una versión de Git y una ruta válida hacia `git.exe`.

### Git Bash

```bash
git --version
which git
```

Debe aparecer una versión de Git y una ruta válida al ejecutable.

### SI FALLA

Si Git funciona en Git Bash pero no en PowerShell:

```text
Git está instalado
pero PowerShell no lo encuentra mediante PATH
```

No reinstalar todo a ciegas. Primero:

1. cerrar y volver a abrir PowerShell;
2. ejecutar `where.exe git`;
3. revisar la integración/PATH de Git for Windows.

Si `git --version` no funciona en ninguna terminal, la instalación todavía no está validada.

## Configurar identidad de commits

```powershell
git config --global user.name "Nombre Apellido"
git config --global user.email "correo@ejemplo.cl"
```

Los mismos comandos funcionan en Git Bash.

Comprobar:

```powershell
git config --global user.name
git config --global user.email
```

Estos datos quedan registrados en los commits.

---

# 4. Instalar GitHub Desktop

**Recomendado**, especialmente si todavía no se domina Git por terminal.

Sitio oficial:

https://desktop.github.com/

Documentación:

https://docs.github.com/desktop

## Instalación

1. instalar GitHub Desktop;
2. abrir la aplicación;
3. seleccionar **Sign in to GitHub.com**;
4. completar autenticación en navegador;
5. verificar nombre y correo de commits.

GitHub Desktop permite trabajar visualmente con:

```text
clone
pull
commit
push
branch
```

La interfaz reduce fricción de sintaxis, pero esos conceptos deben seguir siendo entendidos.

---

# 5. Instalar GitHub CLI (`gh`)

**Recomendado**, no obligatorio.

Sitio oficial:

https://cli.github.com/

Desde PowerShell:

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

Elegir una ruta equivalente a:

```text
GitHub.com
HTTPS
Login with a web browser
```

Comprobar:

```powershell
gh auth status
```

`gh` y GitHub Desktop pueden coexistir.

---

# 6. Instalar JDK 21

Spring Boot utilizará **Java 21**.

Puede utilizarse una distribución OpenJDK 21 mantenida, por ejemplo:

- Microsoft Build of OpenJDK;
- Eclipse Temurin;
- OpenJDK configurado por IntelliJ.

Verificar:

```powershell
java -version
javac -version
```

Ambos deben indicar Java 21.

## Si existen varias versiones de Java

No desinstalar otras versiones sin necesidad. En IntelliJ se seleccionará explícitamente **JDK 21** para CloudTasks.

---

# 7. Instalar IntelliJ IDEA

**Recomendado y utilizado por esta guía para el backend Spring Boot.**

Sitio oficial:

https://www.jetbrains.com/idea/download/

Si el estudiante dispone de acceso académico de JetBrains, puede utilizar las capacidades habilitadas por ese programa.

Información:

https://www.jetbrains.com/academy/student-pack/

## Checkpoint IntelliJ

- [ ] IntelliJ abre correctamente;
- [ ] puede seleccionarse JDK 21;
- [ ] no se han instalado plugins innecesarios para CloudTasks.

---

# 8. Instalar Node.js LTS y npm

Sitio oficial:

https://nodejs.org/

Instalar una versión **LTS** compatible con la versión de Angular utilizada en el curso.

`npm` se instala junto con Node.js.

Cerrar y volver a abrir la terminal.

Verificar:

```powershell
node --version
npm --version
```

No continuar si alguno falla.

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

## Si PowerShell bloquea scripts

No modificar `ExecutionPolicy` indiscriminadamente.

Probar primero:

- una terminal nueva;
- Command Prompt;
- Git Bash.

Si persiste, revisar el mensaje exacto antes de cambiar una política de seguridad.

---

# 10. Elegir editor para Angular

Elegir **uno**.

## Opción A · Visual Studio Code

https://code.visualstudio.com/

Extensiones útiles:

- Angular Language Service;
- ESLint, si el proyecto lo utiliza;
- EditorConfig, si corresponde.

## Opción B · WebStorm

https://www.jetbrains.com/webstorm/

Es una alternativa equivalente para Angular/TypeScript, especialmente si ya se dispone de acceso JetBrains.

> No es necesario instalar VS Code y WebStorm por obligación. Basta uno.

---

# 11. Navegador y DevTools

Usar un navegador moderno, por ejemplo:

- Chrome;
- Edge;
- Firefox.

El estudiante debe saber abrir DevTools (`F12`) y ubicar:

```text
Console
Network
Application / Storage
```

Se utilizarán para observar:

- CORS;
- redirects OAuth/OIDC;
- requests al API Gateway;
- status HTTP;
- headers `Authorization`;
- errores del frontend.

> Postman no aplica la Same-Origin Policy del navegador. Por eso una llamada exitosa en Postman no demuestra que CORS esté correctamente configurado.

---

# 12. Instalar Postman

**Recomendado**, no obligatorio si se utiliza `curl` correctamente.

https://www.postman.com/downloads/

Postman sirve para aislar una prueba HTTP de los problemas propios del navegador.

Ejemplo conceptual:

```text
Postman → 200
navegador → bloqueo CORS
```

indica que conviene revisar primero la política cross-origin.

---

# 13. Qué NO instalar por adelantado

La ruta base no necesita inicialmente:

- Maven global;
- Docker Desktop;
- Kubernetes;
- MySQL/PostgreSQL;
- AWS CLI;
- Azure CLI;
- Terraform;
- Gradle;
- Keycloak.

Docker Desktop aparece posteriormente solo en **★ Advanced Developer**.

Si una etapa necesita otra herramienta, se introducirá cuando exista una razón concreta.

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

`gh` puede omitirse si no fue instalado.

Maven no aparece porque se verificará posteriormente mediante el Wrapper generado por Spring Initializr.

Comprobar también:

- [ ] GitHub permite iniciar sesión;
- [ ] Git funciona desde la terminal elegida;
- [ ] se distingue PowerShell de Git Bash;
- [ ] se entiende que Git Bash no es WSL;
- [ ] GitHub Desktop abre y está autenticado, si se utilizará;
- [ ] IntelliJ abre y reconoce JDK 21;
- [ ] VS Code o WebStorm abre;
- [ ] navegador abre DevTools;
- [ ] Postman abre, si se utilizará.

---

# Puerta de validación 00A

No comenzar CloudTasks hasta que:

- [ ] Git funciona;
- [ ] existe acceso a GitHub;
- [ ] Java 21 funciona;
- [ ] IntelliJ puede utilizar JDK 21;
- [ ] Node y npm funcionan;
- [ ] Angular CLI funciona;
- [ ] existe un editor para Angular;
- [ ] se pueden abrir DevTools;
- [ ] se distingue Git Bash de WSL2/Ubuntu;
- [ ] no se han agregado herramientas innecesarias.

## Resultado esperado

El entorno queda preparado para que los errores posteriores pertenezcan realmente al componente que se está aprendiendo y no a una instalación incompleta.
