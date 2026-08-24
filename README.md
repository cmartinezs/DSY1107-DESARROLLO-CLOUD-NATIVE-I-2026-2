# DSY1107 · Desarrollo Cloud Native I · 2026-2

Repositorio de apoyo para la asignatura **DSY1107 Desarrollo Cloud Native I**.

Este repositorio reúne contenido de clases, ejemplos, laboratorios, guías y recursos complementarios utilizados durante el semestre 2026-2 para las secciones **DSY1107-002D** y **DSY1107-003D**.

## Acceso rápido

- [`semanas/`](semanas/) — índice y contenido consolidado de cada semana.
- [`docs/`](docs/) — conocimientos y guías transversales.
- [`page/`](page/) — portal web del curso.
- [**Desafío transversal RegistrApp**](docs/DESAFIO-TRANSVERSAL-REGISTRAPP.md) — aplicación progresiva de lo aprendido durante el semestre.
- [**Estrategia de laboratorios: concepto → cloud**](docs/ESTRATEGIA-LABORATORIOS-CONCEPTO-A-CLOUD.md) — primero patrón local/neutral; después implementación real en cloud.
- [**Estándar de repositorio del estudiante**](docs/ESTANDAR-REPOSITORIO-ESTUDIANTE.md) — nombre, estructura, packages, Markdown, labs Cloud Native y entregas colaborativas.
- [**Material público del curso**](https://drive.google.com/drive/folders/1UOZMZcEbtfKFq4ygWKj3Yi8VEHkW7hx1?usp=sharing) — biblioteca pública organizada semana a semana.

## Regla pedagógica canónica

DSY1107 mantiene **dos verticales distintas**.

### Vertical de contenido

```text
concepto
→ explicación
→ ejemplo pequeño y autocontenido
→ mini ejercicio/laboratorio independiente
→ evidencia de comprensión
```

El contenido no depende de RegistrApp. Los ejemplos pueden cambiar de dominio si eso permite explicar mejor una competencia.

### Vertical transversal · RegistrApp

```text
contenido comprendido
→ transferencia a RegistrApp
→ incremento
→ evidencia
→ checkpoint
```

> **Primero se aprende fuera de RegistrApp. Después se transfiere a RegistrApp.**

RegistrApp es el desafío transversal del semestre; **no es el ejemplo conductor del material de clases**.

## Cómo se organiza el material

Cada semana mantiene:

1. contenido común de la semana;
2. ejemplos y prácticas independientes;
3. cuando corresponde, un checkpoint separado de RegistrApp;
4. planificación y cierre real por sección.

El directorio [`semanas/`](semanas/) mantiene un README general como índice del semestre. Cada carpeta semanal mantiene su propio `README.md` como punto de entrada.

## Material original

Los archivos originales de la asignatura utilizados durante cada semana se mantienen en la [biblioteca pública de Google Drive](https://drive.google.com/drive/folders/1UOZMZcEbtfKFq4ygWKj3Yi8VEHkW7hx1?usp=sharing), organizados en carpetas semanales de solo lectura.

## Filosofía de laboratorios

Cuando un contenido dependa de infraestructura cloud, se busca primero una experiencia **local, neutral o con un servicio alternativo sencillo** que permita observar el patrón sin quedar amarrado al proveedor.

```text
comprender el concepto
→ probarlo localmente o de forma neutral
→ observar flujos, errores y responsabilidades
→ repetir el objetivo usando el servicio cloud real
→ mapear concepto ↔ servicio
```

El laboratorio de contenido debe ser entendible por sí mismo. Si después la misma competencia se aplica a RegistrApp, esa aplicación pertenece al desafío transversal y se documenta por separado.

→ [Ver estrategia completa](docs/ESTRATEGIA-LABORATORIOS-CONCEPTO-A-CLOUD.md)

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

según su sección.

Para código Java/Kotlin se usa `cl.duoc.<usuario-duoc-sin-puntos>` como raíz de package. Las entregas se documentan con Markdown y cada lab/proyecto debe poder reproducirse desde otra máquina sin depender del IDE del autor.

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

Ambas secciones utilizan el mismo repositorio y persiguen los mismos resultados de aprendizaje, aunque el avance real de cada sesión puede variar.

No se sincronizan artificialmente: cada sección registra su último checkpoint demostrable y continúa desde ahí.

## Semana actual

**Semana 3 · 24 al 29 de agosto de 2026**

**Seguridad de APIs, JWT y usuarios externos**.

Contenido:

- JWT y claims;
- access token;
- decodificación vs verificación;
- `iss`, `aud`, `exp` y scopes;
- 401 vs 403;
- responsabilidades de identidad, gateway y backend;
- seguridad de API/gateway;
- CIAM y mapeo posterior a cloud.

Desafío transversal:

- RegistrApp recibe un checkpoint separado **solo después** de trabajar los contenidos correspondientes.

Consulta [`semanas/semana-03/`](semanas/semana-03/) y la planificación específica de cada sección.

---

> AVA continúa siendo la plataforma oficial para comunicaciones, actividades y recursos institucionales que deban gestionarse desde el entorno académico.
