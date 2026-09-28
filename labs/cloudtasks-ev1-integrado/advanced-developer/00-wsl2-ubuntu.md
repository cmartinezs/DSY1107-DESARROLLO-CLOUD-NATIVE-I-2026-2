# ★ 00 · WSL2 + Ubuntu para desarrollo

## Objetivo

Preparar un entorno Linux real dentro de Windows usando **WSL2 + Ubuntu**, sin convertirlo en requisito de EV1.

Microsoft permite instalar WSL y Ubuntu directamente desde PowerShell y ejecutar herramientas Linux sin una VM tradicional. Docker Desktop puede integrarse con la distribución WSL2 para utilizar Docker directamente desde Ubuntu.

## 1. Verificar Windows

La ruta requiere Windows con soporte WSL2.

Abrir **PowerShell como Administrador** y ejecutar:

```powershell
wsl --status
wsl --version
```

Si WSL no está instalado:

```powershell
wsl --install
```

El flujo actual instala WSL2 y Ubuntu por defecto. Reiniciar Windows si lo solicita.

Si WSL ya existe pero Ubuntu no:

```powershell
wsl --list --online
wsl --install -d Ubuntu
```

## 2. Confirmar WSL2

```powershell
wsl -l -v
```

Debe aparecer algo equivalente a:

```text
NAME      STATE    VERSION
Ubuntu    Running  2
```

Si Ubuntu está en versión 1:

```powershell
wsl --set-version Ubuntu 2
```

Y como valor por defecto para futuras distribuciones:

```powershell
wsl --set-default-version 2
```

## 3. Crear usuario Linux

Abrir Ubuntu desde Windows Terminal o menú Inicio.

La primera ejecución solicita:

```text
username Linux
password Linux
```

La contraseña no muestra caracteres mientras se escribe. Es comportamiento normal de Linux.

No utilizar `root` como usuario cotidiano.

## 4. Actualizar Ubuntu

Dentro de Ubuntu:

```bash
sudo apt update
sudo apt upgrade -y
```

Verificar:

```bash
uname -a
cat /etc/os-release
```

## 5. Crear workspace Linux

```bash
mkdir -p ~/dev
cd ~/dev
```

Los repositorios de esta ruta deberían vivir bajo:

```text
/home/<usuario>/dev/
```

Ejemplo:

```bash
~/dev/cloudtasks
```

### Evitar como workspace principal

```text
/mnt/c/Users/...
```

Windows puede acceder a los archivos Linux y Linux puede acceder al disco Windows, pero para desarrollo Linux/Docker se recomienda mantener código y `node_modules` en el filesystem Linux.

## 6. Git dentro de Ubuntu

Verificar:

```bash
git --version
```

Si falta:

```bash
sudo apt install -y git
```

Configurar identidad:

```bash
git config --global user.name "Nombre Apellido"
git config --global user.email "correo@ejemplo.cl"
```

La ruta avanzada usa **HTTPS** con GitHub. SSH es válido profesionalmente, pero no se convierte en una dependencia adicional de EV1.

GitHub CLI puede instalarse/configurarse también dentro de WSL; una vez disponible:

```bash
gh auth login
```

Elegir GitHub.com + HTTPS + autenticación mediante navegador.

## 7. Finales de línea

En el entorno Linux:

```bash
git config --global core.autocrlf false
```

En el repositorio se recomienda `.gitattributes`:

```gitattributes
* text=auto
*.sh text eol=lf
Dockerfile text eol=lf
*.yml text eol=lf
*.yaml text eol=lf
*.java text eol=lf
*.ts text eol=lf
*.md text eol=lf
*.cmd text eol=crlf
*.bat text eol=crlf
*.ps1 text eol=crlf
```

Esto evita errores clásicos como scripts Docker/Linux con CRLF.

## 8. Editor conectado a WSL

### VS Code

Instalar en Windows la extensión **WSL** de Microsoft.

Desde Ubuntu, dentro del proyecto:

```bash
code .
```

VS Code debe mostrar que está conectado a **WSL: Ubuntu**. El servidor/editor remoto se ejecuta en el contexto Linux mientras la UI permanece en Windows.

### JetBrains

IntelliJ/WebStorm pueden trabajar con proyectos en WSL y toolchains Linux. Para EV1 no es obligatorio migrar el IDE completo a Linux si esto agrega fricción: puede mantenerse IntelliJ para generar inicialmente Spring Boot y luego trabajar/ejecutar desde WSL.

## 9. Instalar Java 21 en Ubuntu

Si el backend se ejecutará desde WSL, comprobar:

```bash
java -version
javac -version
```

Si no existe Java 21, instalar una distribución OpenJDK 21 disponible para Ubuntu.

Después comprobar nuevamente que ambos comandos reportan Java 21.

El proyecto sigue usando Maven Wrapper:

```bash
./mvnw --version
```

No instalar Maven global por costumbre.

## 10. Node.js y Angular dentro de WSL

Si se desea que **todo** el frontend se ejecute en Linux, instalar Node LTS dentro de Ubuntu y después Angular CLI.

Verificar:

```bash
node --version
npm --version
ng version
```

No mezclar en el mismo proyecto `node_modules` generado unas veces con Node Windows y otras con Node Linux.

Elegir una ejecución principal y mantenerla consistente.

## 11. Docker Desktop + WSL2

Instalar Docker Desktop en Windows desde:

https://docs.docker.com/desktop/setup/install/windows-install/

En Docker Desktop comprobar:

```text
Settings
→ General
→ Use WSL 2 based engine
```

Luego:

```text
Settings
→ Resources
→ WSL Integration
→ Ubuntu = Enabled
```

Aplicar cambios.

Desde Ubuntu:

```bash
docker version
docker run --rm hello-world
```

La CLI Docker se ejecuta desde Ubuntu utilizando el engine administrado por Docker Desktop.

### No instalar dos Docker Engines por accidente

Si se usa Docker Desktop + WSL Integration, **no instalar además Docker Engine dentro de Ubuntu solo porque un tutorial lo indica**. Dos instalaciones diferentes complican sockets, contextos, permisos y diagnóstico.

## 12. `.wslconfig`

No crear ni tunear `.wslconfig` inicialmente.

WSL2 maneja recursos de forma dinámica y para EV1 la configuración por defecto es suficiente en la mayoría de los equipos.

Solo introducir límites de memoria/CPU si aparece un problema concreto y documentado. Si una personalización deja WSL inestable, volver al estado base y ejecutar desde PowerShell:

```powershell
wsl --shutdown
```

## 13. Verificación final

PowerShell:

```powershell
wsl -l -v
```

Ubuntu:

```bash
git --version
java -version
node --version
npm --version
ng version
docker version
pwd
```

El `pwd` del proyecto debe estar bajo `/home/...`, no `/mnt/c/...` si se sigue esta ruta avanzada.

## Puerta de validación ★00

- [ ] Ubuntu corre sobre WSL2.
- [ ] existe un usuario Linux no-root.
- [ ] paquetes Ubuntu actualizados.
- [ ] workspace bajo `~/dev`.
- [ ] Git funciona dentro de Ubuntu.
- [ ] Java 21 funciona dentro de Ubuntu si se ejecutará backend ahí.
- [ ] Node/npm/Angular funcionan dentro de Ubuntu si se ejecutará frontend ahí.
- [ ] Docker responde desde Ubuntu mediante Docker Desktop WSL integration.
- [ ] el estudiante distingue Windows host, distribución Ubuntu y contenedor Docker.

## Concepto que debe poder explicar

```text
Windows
└── WSL2
    └── Ubuntu
        ├── Git
        ├── Java / Node
        ├── repo CloudTasks
        └── Docker CLI
             ↓
        Docker Desktop / WSL2 engine
             ↓
        containers Linux
```

La ruta avanzada no busca memorizar comandos Linux: busca comprender **dónde se ejecuta cada proceso**.
