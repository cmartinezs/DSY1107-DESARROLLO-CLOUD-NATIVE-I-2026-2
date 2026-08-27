#!/usr/bin/env python3
"""Validación integral local de la guía integrada EV1 de DSY1107.

Uso recomendado, desde la raíz del repositorio:

    python3 scripts/validate_ev1.py

El comando ejecuta, en orden:

1. preflight del entorno (Python, Git, Java, Node y npm);
2. comprobaciones básicas del repositorio Git;
3. validador estático de `guias-integradas/`;
4. build/test del backend si existe `guia/ev1/backend/`;
5. instalación reproducible + build del frontend si existe `guia/ev1/frontend/`;
6. resumen único PASS/WARN/FAIL con tiempos.

Por defecto, la ausencia de los proyectos materializados `guia/ev1/backend` y
`guia/ev1/frontend` se informa como SKIP, porque este repositorio docente contiene
la guía que enseña a crearlos. Use `--require-projects` para exigirlos como parte
de una validación funcional completa.

Este script NO crea recursos AWS/Entra, NO modifica configuración cloud y NO usa
GitHub Actions. Las pruebas E2E con identidad/cloud siguen requiriendo ejecución
manual sobre un sandbox real.
"""

from __future__ import annotations

import argparse
import json
import os
import platform
import re
import shutil
import subprocess
import sys
import time
from dataclasses import asdict, dataclass
from datetime import datetime, timezone
from pathlib import Path
from typing import Sequence

ROOT = Path(__file__).resolve().parents[1]
STATIC_VALIDATOR = ROOT / "scripts" / "validate_integrated_guides.py"
DEFAULT_BACKEND = ROOT / "guia" / "ev1" / "backend"
DEFAULT_FRONTEND = ROOT / "guia" / "ev1" / "frontend"


@dataclass
class Result:
    name: str
    status: str
    detail: str
    seconds: float


class Ui:
    def __init__(self, color: bool) -> None:
        self.color = color

    def _c(self, code: str, text: str) -> str:
        if not self.color:
            return text
        return f"\033[{code}m{text}\033[0m"

    def title(self, text: str) -> None:
        print(self._c("1;36", f"\n== {text} =="))

    def pass_(self, text: str) -> None:
        print(self._c("1;32", f"PASS  {text}"))

    def warn(self, text: str) -> None:
        print(self._c("1;33", f"WARN  {text}"))

    def fail(self, text: str) -> None:
        print(self._c("1;31", f"FAIL  {text}"))

    def skip(self, text: str) -> None:
        print(self._c("2", f"SKIP  {text}"))

    def info(self, text: str) -> None:
        print(f"INFO  {text}")


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Validación integral local de la guía integrada EV1."
    )
    parser.add_argument(
        "--require-projects",
        action="store_true",
        help="Falla si no existen guia/ev1/backend y guia/ev1/frontend.",
    )
    parser.add_argument(
        "--strict",
        action="store_true",
        help="Convierte warnings operacionales (por ejemplo working tree sucio) en fallo final.",
    )
    parser.add_argument(
        "--skip-builds",
        action="store_true",
        help="Ejecuta preflight + Git + validación estática, pero omite Maven/npm.",
    )
    parser.add_argument(
        "--backend",
        type=Path,
        default=DEFAULT_BACKEND,
        help="Ruta del backend a validar (default: guia/ev1/backend).",
    )
    parser.add_argument(
        "--frontend",
        type=Path,
        default=DEFAULT_FRONTEND,
        help="Ruta del frontend a validar (default: guia/ev1/frontend).",
    )
    parser.add_argument(
        "--json-report",
        type=Path,
        help="Guarda además un reporte JSON en la ruta indicada.",
    )
    parser.add_argument(
        "--no-color",
        action="store_true",
        help="Desactiva colores ANSI.",
    )
    return parser.parse_args()


def run_capture(command: Sequence[str], cwd: Path = ROOT) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        list(command),
        cwd=cwd,
        text=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.STDOUT,
        check=False,
    )


def run_stream(command: Sequence[str], cwd: Path) -> int:
    printable = " ".join(command)
    print(f"$ {printable}")
    completed = subprocess.run(list(command), cwd=cwd, check=False)
    return completed.returncode


def first_line(text: str) -> str:
    lines = [line.strip() for line in text.splitlines() if line.strip()]
    return lines[0] if lines else "sin salida"


def major_from(text: str) -> int | None:
    match = re.search(r"(?:^|\s)(\d+)(?:[.\s]|$)", text)
    return int(match.group(1)) if match else None


def record(results: list[Result], name: str, status: str, detail: str, started: float) -> None:
    results.append(Result(name, status, detail, round(time.monotonic() - started, 3)))


def preflight(ui: Ui, results: list[Result]) -> None:
    ui.title("1/5 · Entorno")
    checks = [
        ("Python", [sys.executable, "--version"], 3),
        ("Git", ["git", "--version"], None),
        ("Java", ["java", "-version"], 21),
        ("Node", ["node", "--version"], None),
        ("npm", ["npm", "--version"], None),
    ]

    for name, command, expected_major in checks:
        started = time.monotonic()
        executable = command[0]
        if shutil.which(executable) is None and executable != sys.executable:
            ui.fail(f"{name}: comando no encontrado ({executable})")
            record(results, f"tool:{name}", "FAIL", "comando no encontrado", started)
            continue

        completed = run_capture(command)
        output = completed.stdout.strip()
        if completed.returncode != 0:
            ui.fail(f"{name}: {first_line(output)}")
            record(results, f"tool:{name}", "FAIL", first_line(output), started)
            continue

        if name == "Python":
            major = sys.version_info.major
        elif name == "Java":
            java_match = re.search(r'version\s+"(\d+)', output)
            major = int(java_match.group(1)) if java_match else None
        else:
            major = major_from(output)

        if expected_major is not None and major != expected_major:
            ui.fail(f"{name}: {first_line(output)} (se requiere major {expected_major})")
            record(
                results,
                f"tool:{name}",
                "FAIL",
                f"{first_line(output)}; se requiere major {expected_major}",
                started,
            )
        else:
            ui.pass_(f"{name}: {first_line(output)}")
            record(results, f"tool:{name}", "PASS", first_line(output), started)

    ui.info(f"SO: {platform.system()} {platform.release()} ({platform.machine()})")


def git_checks(ui: Ui, results: list[Result], strict: bool) -> None:
    ui.title("2/5 · Repositorio Git")

    started = time.monotonic()
    inside = run_capture(["git", "rev-parse", "--is-inside-work-tree"])
    if inside.returncode != 0 or inside.stdout.strip() != "true":
        ui.fail("No se está ejecutando dentro de un repositorio Git")
        record(results, "git:worktree", "FAIL", first_line(inside.stdout), started)
        return
    ui.pass_("Repositorio Git detectado")
    record(results, "git:worktree", "PASS", str(ROOT), started)

    branch = run_capture(["git", "branch", "--show-current"]).stdout.strip() or "DETACHED_HEAD"
    ui.info(f"Branch: {branch}")

    head = run_capture(["git", "rev-parse", "--short=12", "HEAD"])
    if head.returncode == 0:
        ui.info(f"HEAD: {head.stdout.strip()}")

    remote = run_capture(["git", "remote", "get-url", "origin"])
    if remote.returncode == 0:
        origin = remote.stdout.strip()
        if origin.startswith("git@github.com:"):
            ui.pass_(f"origin usa SSH: {origin}")
            results.append(Result("git:origin", "PASS", origin, 0.0))
        else:
            ui.warn(f"origin no usa SSH: {origin}")
            results.append(Result("git:origin", "WARN", origin, 0.0))
    else:
        ui.warn("No se pudo resolver remote origin")
        results.append(Result("git:origin", "WARN", "origin no disponible", 0.0))

    started = time.monotonic()
    status = run_capture(["git", "status", "--porcelain"])
    dirty = bool(status.stdout.strip())
    if dirty:
        level = "FAIL" if strict else "WARN"
        message = "working tree tiene cambios locales"
        if strict:
            ui.fail(message)
        else:
            ui.warn(message)
        record(results, "git:clean", level, message, started)
    else:
        ui.pass_("working tree limpio")
        record(results, "git:clean", "PASS", "sin cambios locales", started)


def static_validation(ui: Ui, results: list[Result]) -> None:
    ui.title("3/5 · Guías integradas")
    started = time.monotonic()

    if not STATIC_VALIDATOR.exists():
        ui.fail(f"No existe {STATIC_VALIDATOR.relative_to(ROOT)}")
        record(results, "docs:static", "FAIL", "validador estático ausente", started)
        return

    code = run_stream([sys.executable, str(STATIC_VALIDATOR)], ROOT)
    if code == 0:
        ui.pass_("Validación estática de documentación")
        record(results, "docs:static", "PASS", "validador estático terminó con exit 0", started)
    else:
        ui.fail(f"Validación estática terminó con exit {code}")
        record(results, "docs:static", "FAIL", f"exit {code}", started)


def backend_validation(
    ui: Ui,
    results: list[Result],
    backend: Path,
    require_projects: bool,
    skip_builds: bool,
) -> None:
    ui.title("4/5 · Backend")
    started = time.monotonic()
    backend = backend.resolve()

    if skip_builds:
        ui.skip("Build backend omitido por --skip-builds")
        record(results, "backend", "SKIP", "--skip-builds", started)
        return

    if not backend.exists():
        status = "FAIL" if require_projects else "SKIP"
        detail = f"no existe {backend.relative_to(ROOT) if backend.is_relative_to(ROOT) else backend}"
        if require_projects:
            ui.fail(detail)
        else:
            ui.skip(detail)
        record(results, "backend", status, detail, started)
        return

    pom = backend / "pom.xml"
    wrapper = backend / "mvnw"
    if not pom.exists() or not wrapper.exists():
        ui.fail("Backend existe, pero faltan pom.xml y/o Maven Wrapper")
        record(results, "backend", "FAIL", "pom.xml o mvnw ausente", started)
        return

    code = run_stream(["bash", "./mvnw", "-B", "clean", "verify"], backend)
    if code == 0:
        ui.pass_("Backend: Maven Wrapper clean verify")
        record(results, "backend", "PASS", "./mvnw -B clean verify", started)
    else:
        ui.fail(f"Backend: Maven terminó con exit {code}")
        record(results, "backend", "FAIL", f"Maven exit {code}", started)


def frontend_validation(
    ui: Ui,
    results: list[Result],
    frontend: Path,
    require_projects: bool,
    skip_builds: bool,
) -> None:
    ui.title("5/5 · Frontend")
    started = time.monotonic()
    frontend = frontend.resolve()

    if skip_builds:
        ui.skip("Build frontend omitido por --skip-builds")
        record(results, "frontend", "SKIP", "--skip-builds", started)
        return

    if not frontend.exists():
        status = "FAIL" if require_projects else "SKIP"
        detail = f"no existe {frontend.relative_to(ROOT) if frontend.is_relative_to(ROOT) else frontend}"
        if require_projects:
            ui.fail(detail)
        else:
            ui.skip(detail)
        record(results, "frontend", status, detail, started)
        return

    package_json = frontend / "package.json"
    lockfile = frontend / "package-lock.json"
    if not package_json.exists():
        ui.fail("Frontend existe, pero falta package.json")
        record(results, "frontend", "FAIL", "package.json ausente", started)
        return
    if not lockfile.exists():
        ui.fail("Frontend existe, pero falta package-lock.json; no hay instalación reproducible con npm ci")
        record(results, "frontend", "FAIL", "package-lock.json ausente", started)
        return

    install_code = run_stream(["npm", "ci"], frontend)
    if install_code != 0:
        ui.fail(f"Frontend: npm ci terminó con exit {install_code}")
        record(results, "frontend", "FAIL", f"npm ci exit {install_code}", started)
        return

    build_code = run_stream(["npm", "run", "build"], frontend)
    if build_code == 0:
        ui.pass_("Frontend: npm ci + npm run build")
        record(results, "frontend", "PASS", "npm ci && npm run build", started)
    else:
        ui.fail(f"Frontend: build terminó con exit {build_code}")
        record(results, "frontend", "FAIL", f"npm run build exit {build_code}", started)


def write_json_report(path: Path, results: list[Result], exit_code: int) -> None:
    path = path.expanduser().resolve()
    path.parent.mkdir(parents=True, exist_ok=True)
    payload = {
        "schema": "dsy1107.ev1.validation.v1",
        "generated_at": datetime.now(timezone.utc).isoformat(),
        "repository": str(ROOT),
        "exit_code": exit_code,
        "results": [asdict(result) for result in results],
    }
    path.write_text(json.dumps(payload, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")


def summary(ui: Ui, results: list[Result], strict: bool) -> int:
    print("\n" + "=" * 72)
    print("RESUMEN EV1")
    print("=" * 72)

    for result in results:
        label = f"{result.status:<4}"
        print(f"{label}  {result.name:<20} {result.seconds:>7.2f}s  {result.detail}")

    failures = [r for r in results if r.status == "FAIL"]
    warnings = [r for r in results if r.status == "WARN"]
    skipped = [r for r in results if r.status == "SKIP"]

    print("-" * 72)
    print(
        f"PASS={sum(r.status == 'PASS' for r in results)}  "
        f"WARN={len(warnings)}  SKIP={len(skipped)}  FAIL={len(failures)}"
    )

    if failures:
        ui.fail("VALIDACIÓN EV1: FAIL")
        return 1
    if strict and warnings:
        ui.fail("VALIDACIÓN EV1: FAIL (--strict y existen WARN)")
        return 1

    ui.pass_("VALIDACIÓN EV1: PASS")
    if skipped:
        ui.info("Hay etapas SKIP; use --require-projects para exigir backend/frontend materializados.")
    return 0


def main() -> int:
    args = parse_args()
    use_color = not args.no_color and sys.stdout.isatty() and os.getenv("NO_COLOR") is None
    ui = Ui(use_color)
    results: list[Result] = []

    print("DSY1107 · EV1 Integrated Guide Validator")
    print(f"Repositorio: {ROOT}")

    preflight(ui, results)
    git_checks(ui, results, args.strict)
    static_validation(ui, results)
    backend_validation(ui, results, args.backend, args.require_projects, args.skip_builds)
    frontend_validation(ui, results, args.frontend, args.require_projects, args.skip_builds)

    exit_code = summary(ui, results, args.strict)

    if args.json_report:
        write_json_report(args.json_report, results, exit_code)
        ui.info(f"Reporte JSON: {args.json_report.expanduser().resolve()}")

    return exit_code


if __name__ == "__main__":
    raise SystemExit(main())
