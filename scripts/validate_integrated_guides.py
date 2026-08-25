#!/usr/bin/env python3
"""Validación estática local de guías integradas DSY1107.

No usa red ni GitHub Actions. Ejecutar desde la raíz del repositorio:

    python scripts/validate_integrated_guides.py

Comprueba enlaces Markdown relativos, fences de código, bloques Mermaid básicos y
regresiones semánticas que no deben volver a introducirse en la guía EV1.
"""

from __future__ import annotations

import re
import sys
from pathlib import Path
from urllib.parse import unquote

ROOT = Path(__file__).resolve().parents[1]
GUIDES = ROOT / "guias-integradas"
EV1 = GUIDES / "ev1"

FORBIDDEN_TEXT = {
    "evaluaciones/ep1/": "La práctica integrada debe vivir en guia/ev1/, no evaluaciones/ep1/.",
    "00b-git-github-flujo-evaluacion.md": "Se renombró a flujo-guia.",
    "10-evidencias-y-defensa.md": "Se reemplazó por verificación integrada.",
    "10a-plan-evidencias-ev1.md": "Se reemplazó por mapa de cobertura.",
    "10b-runbook-dia-defensa.md": "Se reemplazó por simulación de presentación técnica.",
    "REQUERIDO EV1": "La guía no debe presentarse como la evaluación.",
}

ALLOWED_EVALUATION_PHRASE = "esta guía **no es la evaluación E1**"
MARKDOWN_LINK = re.compile(r"(?<!!)\[[^\]]*\]\(([^)]+)\)")
FENCE = re.compile(r"^\s*```([^`]*)$")
GLOBAL_MAVEN_COMMAND = re.compile(r"^\s*mvn(?:\s|$)")


def markdown_files() -> list[Path]:
    return sorted(GUIDES.rglob("*.md"))


def validate_links(path: Path, text: str, errors: list[str]) -> None:
    for match in MARKDOWN_LINK.finditer(text):
        target = match.group(1).strip()
        if not target or target.startswith(("http://", "https://", "mailto:", "#")):
            continue

        target = target.split("#", 1)[0].split("?", 1)[0]
        target = unquote(target)
        if not target:
            continue

        resolved = (path.parent / target).resolve()
        try:
            resolved.relative_to(ROOT.resolve())
        except ValueError:
            errors.append(f"{path.relative_to(ROOT)}: enlace sale del repositorio: {target}")
            continue

        if not resolved.exists():
            errors.append(f"{path.relative_to(ROOT)}: enlace relativo inexistente: {target}")


def validate_fences(path: Path, text: str, errors: list[str]) -> None:
    opened: tuple[int, str] | None = None
    for number, line in enumerate(text.splitlines(), start=1):
        match = FENCE.match(line)
        if not match:
            continue
        language = match.group(1).strip()
        if opened is None:
            opened = (number, language)
        else:
            opened = None

    if opened is not None:
        errors.append(
            f"{path.relative_to(ROOT)}:{opened[0]}: bloque ```{opened[1]} sin cierre"
        )


def validate_mermaid(path: Path, text: str, errors: list[str]) -> None:
    blocks = re.findall(r"```mermaid\s*\n(.*?)```", text, flags=re.DOTALL)
    for index, block in enumerate(blocks, start=1):
        first = next((line.strip() for line in block.splitlines() if line.strip()), "")
        if not first.startswith(("flowchart ", "sequenceDiagram", "graph ", "classDiagram", "stateDiagram")):
            errors.append(
                f"{path.relative_to(ROOT)}: Mermaid #{index} no comienza con un tipo reconocido: {first!r}"
            )


def validate_semantics(path: Path, text: str, errors: list[str]) -> None:
    rel = path.relative_to(ROOT)

    for forbidden, reason in FORBIDDEN_TEXT.items():
        if forbidden in text:
            errors.append(f"{rel}: texto prohibido {forbidden!r}. {reason}")

    if path.is_relative_to(EV1) and path != EV1 / "README.md":
        if "evaluación E1" in text:
            errors.append(
                f"{rel}: la distinción guía vs evaluación debe declararse solo en ev1/README.md"
            )

    if path == EV1 / "README.md":
        count = text.count(ALLOWED_EVALUATION_PHRASE)
        if count != 1:
            errors.append(
                f"{rel}: se esperaba una única aclaración canónica guía≠evaluación; encontradas {count}"
            )


def validate_commands(path: Path, text: str, errors: list[str]) -> None:
    in_fence = False
    for number, line in enumerate(text.splitlines(), start=1):
        if line.lstrip().startswith("```"):
            in_fence = not in_fence
            continue
        if in_fence and GLOBAL_MAVEN_COMMAND.match(line):
            errors.append(
                f"{path.relative_to(ROOT)}:{number}: comando Maven global detectado; usar Wrapper"
            )


def validate_required_files(errors: list[str]) -> None:
    required = [
        EV1 / "README.md",
        EV1 / "00a-preparar-entorno.md",
        EV1 / "00b-git-github-flujo-guia.md",
        EV1 / "00c-matriz-valores-y-checkpoints.md",
        EV1 / "00d-scaffolding-vs-codigo-estudiante.md",
        EV1 / "00-mapa-y-prerequisitos.md",
        EV1 / "01a-crear-backend-intellij.md",
        EV1 / "01b-crear-frontend-angular.md",
        EV1 / "01-cloudtasks-local.md",
        EV1 / "02-entra-external-id.md",
        EV1 / "03-angular-msal.md",
        EV1 / "03a-starter-angular-msal.md",
        EV1 / "04-jwt-y-backend.md",
        EV1 / "04a-starter-spring-security.md",
        EV1 / "05-aws-backend.md",
        EV1 / "05a-ec2-paso-a-paso.md",
        EV1 / "06-api-gateway-jwt.md",
        EV1 / "07-cors.md",
        EV1 / "08-frontend-cloud-e2e.md",
        EV1 / "08a-hosting-frontend-https.md",
        EV1 / "09-pruebas-y-troubleshooting.md",
        EV1 / "09a-runbook-checkpoints-estado-conocido.md",
        EV1 / "10-verificacion-integrada.md",
        EV1 / "10a-mapa-cobertura-conocimientos.md",
        EV1 / "10b-simulacion-presentacion-tecnica.md",
        EV1 / "11-costos-y-cleanup.md",
        EV1 / "advanced-developer" / "README.md",
        EV1 / "advanced-developer" / "00-wsl2-ubuntu.md",
        EV1 / "advanced-developer" / "01-docker-local.md",
        EV1 / "advanced-developer" / "02-docker-ec2.md",
        EV1 / "reference" / "README.md",
    ]
    for path in required:
        if not path.exists():
            errors.append(f"falta archivo canónico: {path.relative_to(ROOT)}")


def main() -> int:
    errors: list[str] = []

    if not GUIDES.exists():
        print("ERROR: no existe guias-integradas/", file=sys.stderr)
        return 2

    validate_required_files(errors)

    files = markdown_files()
    for path in files:
        text = path.read_text(encoding="utf-8")
        validate_links(path, text, errors)
        validate_fences(path, text, errors)
        validate_mermaid(path, text, errors)
        validate_semantics(path, text, errors)
        validate_commands(path, text, errors)

    print(f"Guías revisadas: {len(files)}")

    if errors:
        print(f"Errores: {len(errors)}")
        for error in errors:
            print(f"- {error}")
        return 1

    print("PASS: enlaces relativos, fences, Mermaid básico y reglas semánticas.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
