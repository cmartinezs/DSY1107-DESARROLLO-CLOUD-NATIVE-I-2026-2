# 01A · Crear el backend Spring Boot con IntelliJ

## Objetivo

Crear **desde cero** el backend que se utilizará durante toda la guía EV1, usando IntelliJ IDEA y Spring Initializr.

En esta etapa no se implementa OAuth2, JWT, roles, AWS ni lógica compleja. El único objetivo es terminar con un proyecto Spring Boot reproducible, con Maven Wrapper y un endpoint mínimo que permita comprobar que el backend funciona.

> El foco de DSY1107 no es aprender a construir un CRUD avanzado con Spring. Por eso el código de negocio se mantendrá deliberadamente mínimo.

---

## Resultado esperado

Al finalizar debe existir:

```text
cloudtasks/
└── backend/
    ├── .mvn/
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/...
        │   └── resources/
        └── test/
```

Y debe responder:

```http
GET http://localhost:8080/api/public/health
```

con:

```json
{
  "status": "UP",
  "service": "cloudtasks-api"
}
```

---

# 1. Crear la carpeta de trabajo

Crear una carpeta donde vivirá la aplicación completa:

```text
cloudtasks/
```

No crear todavía manualmente `backend/`: IntelliJ generará esa carpeta al crear el proyecto.

La estructura final tendrá luego:

```text
cloudtasks/
├── backend/
└── frontend/
```

---

# 2. Crear el proyecto desde IntelliJ IDEA

Abrir IntelliJ IDEA.

Seleccionar:

```text
File
→ New
→ Project
```

Elegir **Spring Boot** o **Spring Initializr**, según la versión de IntelliJ instalada.

Usar estos valores como referencia:

| Campo | Valor sugerido |
|---|---|
| Name | `backend` |
| Location | carpeta `cloudtasks/` |
| Language | Java |
| Type / Build system | Maven |
| Group | `cl.duoc` |
| Artifact | `cloudtasks-api` |
| Package name | `cl.duoc.cloudtasks` |
| JDK | 21 |
| Java | 21 |
| Packaging | Jar |

La carpeta física del proyecto debe quedar como:

```text
cloudtasks/backend/
```

## Importante sobre nombres

El nombre visual del proyecto y el `artifactId` no tienen que ser idénticos.

En esta guía se usa:

```text
carpeta: backend
artifactId: cloudtasks-api
package: cl.duoc.cloudtasks
```

para que la estructura del workspace sea simple.

---

# 3. Seleccionar solo las dependencias necesarias ahora

En la pantalla de dependencias agregar únicamente:

- **Spring Web**;
- **Spring Boot Actuator**.

No agregar todavía:

- Spring Security;
- OAuth2 Resource Server;
- JPA;
- base de datos;
- Lombok;
- WebFlux;
- Docker;
- mensajería.

Esas dependencias no aportan nada al objetivo de esta etapa y aumentan la cantidad de errores posibles.

> Spring Security y OAuth2 Resource Server se incorporarán recién cuando llegue la etapa JWT.

Finalizar con **Create**.

---

# 4. Esperar a que IntelliJ termine de importar Maven

No ejecutar el proyecto mientras IntelliJ todavía está descargando/importando dependencias.

Comprobar que desaparecieron los indicadores de carga de Maven y que `pom.xml` no muestra errores de dependencias.

La raíz debe contener al menos:

```text
.mvn/
mvnw
mvnw.cmd
pom.xml
src/
```

## ¿Por qué usamos Maven Wrapper?

El proyecto generado incluye Maven Wrapper.

Eso permite ejecutar Maven usando la versión/configuración asociada al proyecto, sin exigir que cada alumno tenga Maven instalado globalmente.

Windows:

```powershell
.\mvnw.cmd --version
```

Linux/macOS:

```bash
./mvnw --version
```

Debe mostrarse una versión de Maven y Java 21.

### Si `mvnw` no existe

No continuar usando simplemente `mvn` como sustituto silencioso.

Revisar primero que el proyecto se haya generado realmente como Maven mediante Spring Initializr. Si el entorno utilizado no generó wrapper, regenerar el proyecto con la configuración indicada o utilizar la opción de IntelliJ/Spring Initializr que lo incluya.

La guía asumirá desde este punto que existen:

```text
mvnw
mvnw.cmd
```

---

# 5. Revisar el proyecto generado antes de programar

Debe existir una clase principal similar a:

```java
@SpringBootApplication
public class CloudtasksApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudtasksApiApplication.class, args);
    }
}
```

No modificarla.

La intención es aprovechar al máximo el scaffolding generado por Spring Initializr.

---

# 6. Crear un único controller mínimo

Dentro del package:

```text
cl.duoc.cloudtasks
```

crear un package:

```text
controller
```

Luego crear:

```text
PublicController.java
```

Código completo:

```java
package cl.duoc.cloudtasks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "service", "cloudtasks-api"
        );
    }
}
```

Este es prácticamente todo el código manual de backend necesario en esta primera etapa.

## Qué aporta este código a EV1

Permite comprobar separadamente:

```text
Spring Boot funciona
→ existe una ruta HTTP
→ devuelve JSON
→ más adelante puede ser expuesta por API Gateway
```

No se está evaluando diseño de dominio ni persistencia.

---

# 7. Ejecutar desde IntelliJ

Abrir la clase principal generada por Spring Boot.

Presionar el botón **Run** junto al método `main`, o ejecutar la configuración Spring Boot creada por IntelliJ.

En consola debe aparecer un mensaje equivalente a:

```text
Started ...Application
```

Y Tomcat debe quedar escuchando en el puerto `8080`.

---

# 8. Validar desde navegador

Abrir:

```text
http://localhost:8080/api/public/health
```

Debe aparecer JSON equivalente a:

```json
{"status":"UP","service":"cloudtasks-api"}
```

También puede validarse con terminal.

Windows PowerShell:

```powershell
Invoke-RestMethod http://localhost:8080/api/public/health
```

Linux/macOS/Git Bash:

```bash
curl http://localhost:8080/api/public/health
```

---

# 9. Validar con el Maven Wrapper

Detener temporalmente la ejecución de IntelliJ.

Desde la carpeta `backend/`:

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Volver a probar:

```text
http://localhost:8080/api/public/health
```

Esto demuestra que el proyecto es reproducible fuera del botón Run del IDE.

> Desde este punto la guía usará siempre `./mvnw` o `.\mvnw.cmd`. No se exigirá una instalación global de Maven.

---

# 10. Errores frecuentes

## Java distinto de 21

Síntomas posibles:

```text
invalid target release
unsupported class file
```

Revisar:

```text
File → Project Structure → Project SDK
```

y la configuración de Maven Runner/JDK en IntelliJ.

Validar además:

```powershell
.\mvnw.cmd --version
```

Debe reportar Java 21.

## Puerto 8080 ocupado

Si aparece algo equivalente a:

```text
Port 8080 was already in use
```

cerrar la aplicación que ocupa ese puerto antes de cambiar arbitrariamente el puerto del backend.

La guía completa supone:

```text
backend = http://localhost:8080
```

## `404 Not Found`

Comprobar:

1. que Spring arrancó correctamente;
2. que `PublicController` está bajo `cl.duoc.cloudtasks` o uno de sus subpackages;
3. que la URL sea exactamente `/api/public/health`.

## Error al importar dependencias

Antes de modificar `pom.xml` manualmente:

1. verificar conexión a Internet;
2. usar **Reload All Maven Projects**;
3. esperar la descarga completa;
4. volver a ejecutar el wrapper.

---

# Puerta de validación 01A

No continuar hasta demostrar las cuatro condiciones:

- [ ] el proyecto fue creado mediante IntelliJ + Spring Initializr;
- [ ] usa Java 21 y Maven;
- [ ] existen `mvnw`, `mvnw.cmd` y `.mvn/`;
- [ ] `GET http://localhost:8080/api/public/health` devuelve JSON correctamente tanto desde IntelliJ como ejecutando el Maven Wrapper.

## Evidencia mínima recomendada

Una captura o registro que permita observar:

```text
proyecto backend
+ wrapper Maven
+ aplicación ejecutándose
+ respuesta JSON de /api/public/health
```

No incluir credenciales ni datos sensibles.
