# DSY1107 · Desarrollo Cloud Native I · 2026-2

Repositorio de apoyo para la asignatura **DSY1107 Desarrollo Cloud Native I**.

Este repositorio reúne contenido de clases, ejemplos, laboratorios, guías y recursos complementarios utilizados durante el semestre 2026-2 para las secciones **DSY1107-002D** y **DSY1107-003D**.

## Acceso rápido

- [`semanas/`](semanas/) — índice y contenido consolidado de cada semana.
- [`docs/`](docs/) — conocimientos y guías transversales.
- [`labs/`](labs/) — ejercicios y laboratorios prácticos.
- [`examples/`](examples/) — ejemplos de código desarrollados en clases.
- [`page/`](page/) — portal web del curso.
- [**Estándar de repositorio del estudiante**](docs/ESTANDAR-REPOSITORIO-ESTUDIANTE.md) — nombre, estructura, packages, Markdown, labs Cloud Native y entregas colaborativas.
- [**Material público del curso**](https://drive.google.com/drive/folders/1UOZMZcEbtfKFq4ygWKj3Yi8VEHkW7hx1?usp=sharing) — biblioteca pública organizada semana a semana.

## Cómo se organiza el material

Cada semana mantiene dos fuentes complementarias:

### Contenido consolidado

Se publica en este repositorio dentro de `semanas/semana-XX/`.

El contenido consolidado incorpora:

- los contenidos definidos para la semana;
- explicaciones y ejemplos;
- contexto técnico adicional;
- aclaraciones cuando el material original presenta ambigüedades o terminología inconsistente;
- ejercicios, laboratorios y evidencias cuando corresponda.

El directorio [`semanas/`](semanas/) mantiene un **README general** que funciona como índice del semestre. Además, cada carpeta semanal mantiene su propio `README.md` como punto de entrada a esa semana.

### Material original

Los archivos originales de la asignatura utilizados durante cada semana se mantienen en la [biblioteca pública de Google Drive](https://drive.google.com/drive/folders/1UOZMZcEbtfKFq4ygWKj3Yi8VEHkW7hx1?usp=sharing), organizados en carpetas semanales de solo lectura.

## Repositorio personal del estudiante

La estructura usada por el docente para publicar material no se copia en el repositorio del alumno.

Cada estudiante mantiene un único repo personal:

```text
DSY1107-002D-nombre-apellido
```

o

```text
DSY1107-003D-nombre-apellido
```

según su sección. Para código Java/Kotlin se usa `cl.duoc.<usuario-duoc-sin-puntos>` como raíz de package. Las entregas se documentan con Markdown y cada lab/proyecto debe poder reproducirse desde otra máquina sin depender del IDE del autor.

→ [Ver estándar completo](docs/ESTANDAR-REPOSITORIO-ESTUDIANTE.md)  
→ [Versión web](page/repositorio-estudiante.html)

## Cómo obtener el repositorio

```bash
git clone https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2.git
cd DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2
```

Para actualizar una copia existente:

```bash
git pull
```

## Organización del semestre

Ambas secciones utilizan el mismo repositorio y alcanzan los mismos objetivos semanales, aunque la distribución y avance de cada sesión puede variar.

## Semana actual

**Semana 2 · 17 al 22 de agosto de 2026**

**Gestión de APIs + fundamentos de identidad**.

- cierre de API Manager, versionado y CORS;
- OAuth2 y OpenID Connect;
- Identity as a Service / CIAM;
- diseño conceptual de tenant y aplicaciones;
- ReservApp como dominio formativo transversal.

Consulta [`semanas/semana-02/`](semanas/semana-02/) y la planificación específica de cada sección.

---

> AVA continúa siendo la plataforma oficial para comunicaciones, actividades y recursos institucionales que deban gestionarse desde el entorno académico.
