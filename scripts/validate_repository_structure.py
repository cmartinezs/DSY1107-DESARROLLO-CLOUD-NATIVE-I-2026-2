#!/usr/bin/env python3
"""Validación estructural de DSY1107.

Comprueba la superficie mínima del repositorio hasta la semana curricular vigente
materializada en este contrato (Semana 08). No valida infraestructura cloud ni
declara ejecución de aula.
"""

from __future__ import annotations

from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]

REQUIRED_PATHS = [
    "README.md",
    "docs/README.md",
    "docs/PDA-RESUMEN.md",
    "docs/RESULTADOS-DE-APRENDIZAJE.md",
    "docs/RUTA-DE-APRENDIZAJE.md",
    "docs/CRONOGRAMA.md",
    "docs/ESTRUCTURA-CANONICA.md",
    "semanas/README.md",
    "examples/README.md",
    "ejercicios/README.md",
    "labs/README.md",
    "evaluaciones/README.md",
    "proyecto-formativo/README.md",
    "proyecto-formativo/REQUERIMIENTOS.md",
    "data/weekly/README.md",
    "governance/repository.yaml",
    "governance/repository-site.adel",
]

for week in range(1, 9):
    REQUIRED_PATHS.append(f"semanas/semana-{week:02d}/README.md")
    REQUIRED_PATHS.append(f"data/weekly/semana-{week:02d}.yml")
    REQUIRED_PATHS.append(f"proyecto-formativo/semana-{week:02d}/README.md")

CONTENT_SURFACES = {
    "examples/semana-01": 1,
    "examples/semana-02": 1,
    "examples/semana-03": 1,
    "examples/semana-04": 1,
    "examples/semana-05": 1,
    "examples/semana-08": 3,
    "ejercicios/semana-01": 3,
    "ejercicios/semana-02": 3,
    "ejercicios/semana-03": 3,
    "ejercicios/semana-04": 3,
    "ejercicios/semana-05": 3,
    "ejercicios/semana-08": 6,
    "labs/jwt-forense": 6,
    "labs/rabbitmq-spring-amqp": 7,
    "proyecto-formativo/semana-01": 3,
    "proyecto-formativo/semana-02": 3,
    "proyecto-formativo/semana-03": 3,
    "proyecto-formativo/semana-05": 3,
    "proyecto-formativo/semana-06": 2,
    "proyecto-formativo/semana-07": 2,
    "proyecto-formativo/semana-08": 4,
}

FORBIDDEN_ACTIVE_ROOTS = [
    "guia",
    "guias-integradas",
]


def count_content_markdown(folder: Path) -> int:
    return sum(1 for path in folder.glob("*.md") if path.name != "README.md")


def main() -> int:
    errors: list[str] = []

    for relative in REQUIRED_PATHS:
        if not (ROOT / relative).exists():
            errors.append(f"falta ruta requerida: {relative}")

    for relative, minimum in CONTENT_SURFACES.items():
        folder = ROOT / relative
        if not folder.exists():
            errors.append(f"falta superficie: {relative}")
            continue
        actual = count_content_markdown(folder)
        if actual < minimum:
            errors.append(
                f"{relative}: se esperaban al menos {minimum} archivos .md de contenido; encontrados {actual}"
            )

    for root_name in FORBIDDEN_ACTIVE_ROOTS:
        if (ROOT / root_name).exists():
            errors.append(
                f"raíz legacy activa: {root_name}/; el contenido debe estar clasificado en superficies canónicas"
            )

    if errors:
        print(f"FAIL · estructura DSY1107 · {len(errors)} problema(s)")
        for error in errors:
            print(f"- {error}")
        return 1

    print("PASS · estructura DSY1107 hasta Semana 08")
    print(f"Rutas requeridas: {len(REQUIRED_PATHS)}")
    print(f"Superficies con contenido mínimo: {len(CONTENT_SURFACES)}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
