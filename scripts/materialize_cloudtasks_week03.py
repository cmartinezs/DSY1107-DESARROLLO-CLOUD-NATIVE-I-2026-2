#!/usr/bin/env python3
"""Materializa un workspace local reproducible de CloudTasks hasta Semana 3.

Este script es para validación docente/técnica. No reemplaza el recorrido pedagógico
por IntelliJ + Spring Initializr + Angular CLI descrito en `guias-integradas/ev1/`.

Requiere acceso de red a start.spring.io y npm registry.

Uso, desde la raíz del repositorio:

    python3 scripts/materialize_cloudtasks_week03.py

Luego:

    python3 scripts/validate_ev1.py --require-projects --strict
"""

from __future__ import annotations

import argparse
import shutil
import subprocess
import tempfile
import urllib.parse
import urllib.request
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
WORKSPACE = ROOT / "guia" / "ev1"
BACKEND = WORKSPACE / "backend"
FRONTEND = WORKSPACE / "frontend"


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Materializa CloudTasks local hasta el checkpoint curricular Semana 3."
    )
    parser.add_argument(
        "--force",
        action="store_true",
        help="Elimina backend/frontend locales existentes antes de regenerarlos.",
    )
    return parser.parse_args()


def run(command: list[str], cwd: Path = ROOT) -> None:
    print("$ " + " ".join(command))
    completed = subprocess.run(command, cwd=cwd, check=False)
    if completed.returncode != 0:
        raise SystemExit(f"FAIL: comando terminó con exit {completed.returncode}")


def require_command(name: str) -> None:
    if shutil.which(name) is None:
        raise SystemExit(f"FAIL: no se encontró el comando requerido: {name}")


def prepare(args: argparse.Namespace) -> None:
    WORKSPACE.mkdir(parents=True, exist_ok=True)
    existing = [path for path in (BACKEND, FRONTEND) if path.exists()]
    if existing and not args.force:
        rendered = ", ".join(str(path.relative_to(ROOT)) for path in existing)
        raise SystemExit(
            "FAIL: ya existen artefactos locales: "
            f"{rendered}. Use --force solo si desea regenerarlos."
        )
    if args.force:
        for path in existing:
            print(f"INFO  eliminando {path.relative_to(ROOT)}")
            shutil.rmtree(path)


def spring_initializr_url() -> str:
    params = {
        "type": "maven-project",
        "language": "java",
        "javaVersion": "21",
        "groupId": "cl.duoc.reference",
        "artifactId": "cloudtasks-api",
        "name": "cloudtasks-api",
        "description": "CloudTasks reference workspace",
        "packageName": "cl.duoc.reference.cloudtasks",
        "packaging": "jar",
        "dependencies": "web,actuator",
    }
    return "https://start.spring.io/starter.zip?" + urllib.parse.urlencode(params)


def materialize_backend() -> None:
    print("\n== Backend Spring Boot ==")
    url = spring_initializr_url()
    with tempfile.TemporaryDirectory() as tmp:
        archive = Path(tmp) / "backend.zip"
        print("INFO  descargando scaffolding desde Spring Initializr")
        try:
            urllib.request.urlretrieve(url, archive)
        except Exception as exc:  # diagnóstico explícito de red
            raise SystemExit(f"FAIL: no se pudo descargar Spring Initializr: {exc}") from exc

        BACKEND.mkdir(parents=True, exist_ok=True)
        with zipfile.ZipFile(archive) as zf:
            zf.extractall(BACKEND)

    mvnw = BACKEND / "mvnw"
    if mvnw.exists():
        mvnw.chmod(mvnw.stat().st_mode | 0o111)

    applications = list((BACKEND / "src" / "main" / "java").rglob("*Application.java"))
    if len(applications) != 1:
        raise SystemExit(
            f"FAIL: se esperaba una clase *Application.java; encontradas {len(applications)}"
        )

    package_dir = applications[0].parent
    controller_dir = package_dir / "controller"
    controller_dir.mkdir(parents=True, exist_ok=True)
    controller = controller_dir / "PublicController.java"
    controller.write_text(
        """package cl.duoc.reference.cloudtasks.controller;

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
""",
        encoding="utf-8",
    )

    print("PASS  backend materializado")


def angular_write(path: str, content: str) -> None:
    destination = FRONTEND / path
    destination.parent.mkdir(parents=True, exist_ok=True)
    destination.write_text(content, encoding="utf-8")


def materialize_frontend() -> None:
    print("\n== Frontend Angular + MSAL ==")
    require_command("node")
    require_command("npm")
    require_command("npx")

    run(
        [
            "npx",
            "-y",
            "@angular/cli@22",
            "new",
            "frontend",
            "--directory",
            str(FRONTEND),
            "--standalone=true",
            "--routing=false",
            "--style=css",
            "--skip-git",
            "--package-manager=npm",
            "--defaults",
        ],
        ROOT,
    )

    run(
        [
            "npm",
            "install",
            "@azure/msal-angular@^6",
            "@azure/msal-browser@^5",
        ],
        FRONTEND,
    )

    angular_write(
        "src/app/auth-config.ts",
        """export const authConfig = {
  clientId: '<SPA_CLIENT_ID>',
  authority: '<MSAL_AUTHORITY>',
  redirectUri: window.location.origin,
  postLogoutRedirectUri: window.location.origin
};

export const apiConfig = {
  readScope: '<SCOPE_READ>',
  writeScope: '<SCOPE_WRITE>'
};
""",
    )

    angular_write(
        "src/app/app.config.ts",
        """import { ApplicationConfig } from '@angular/core';
import {
  BrowserCacheLocation,
  IPublicClientApplication,
  PublicClientApplication
} from '@azure/msal-browser';
import {
  MsalBroadcastService,
  MsalService,
  MSAL_INSTANCE
} from '@azure/msal-angular';
import { authConfig } from './auth-config';

export function msalInstanceFactory(): IPublicClientApplication {
  return new PublicClientApplication({
    auth: {
      clientId: authConfig.clientId,
      authority: authConfig.authority,
      redirectUri: authConfig.redirectUri,
      postLogoutRedirectUri: authConfig.postLogoutRedirectUri
    },
    cache: {
      cacheLocation: BrowserCacheLocation.SessionStorage
    },
    system: {
      allowPlatformBroker: false
    }
  });
}

export const appConfig: ApplicationConfig = {
  providers: [
    {
      provide: MSAL_INSTANCE,
      useFactory: msalInstanceFactory
    },
    MsalService,
    MsalBroadcastService
  ]
};
""",
    )

    angular_write(
        "src/app/app.component.ts",
        """import { JsonPipe } from '@angular/common';
import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MsalBroadcastService, MsalService } from '@azure/msal-angular';
import { AuthenticationResult, InteractionStatus } from '@azure/msal-browser';
import { filter } from 'rxjs/operators';
import { apiConfig } from './auth-config';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [JsonPipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  private readonly destroyRef = inject(DestroyRef);
  private readonly msal = inject(MsalService);
  private readonly broadcast = inject(MsalBroadcastService);

  readonly loggedIn = signal(false);
  readonly status = signal('Listo para configurar Microsoft Entra External ID');
  readonly claims = signal<Record<string, unknown> | null>(null);

  ngOnInit(): void {
    this.msal.handleRedirectObservable().subscribe({
      next: result => {
        if (result?.account) {
          this.msal.instance.setActiveAccount(result.account);
        }
      },
      error: error => {
        console.error(error);
        this.status.set('Error procesando redirect de autenticación');
      }
    });

    this.broadcast.inProgress$
      .pipe(
        filter(status => status === InteractionStatus.None),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe(() => {
        const current = this.msal.instance.getActiveAccount();
        const accounts = this.msal.instance.getAllAccounts();
        if (!current && accounts.length > 0) {
          this.msal.instance.setActiveAccount(accounts[0]);
        }
        this.loggedIn.set(this.msal.instance.getActiveAccount() !== null);
      });
  }

  login(): void {
    this.msal.loginRedirect({
      scopes: [apiConfig.readScope, apiConfig.writeScope]
    });
  }

  logout(): void {
    this.msal.logoutRedirect({
      postLogoutRedirectUri: window.location.origin
    });
  }

  obtenerAccessToken(): void {
    const account = this.msal.instance.getActiveAccount();
    if (!account) {
      this.status.set('Primero debe iniciar sesión');
      return;
    }

    this.msal.acquireTokenSilent({
      account,
      scopes: [apiConfig.readScope]
    }).subscribe({
      next: result => this.procesarToken(result),
      error: error => {
        console.error(error);
        this.status.set('No fue posible adquirir el Access Token');
      }
    });
  }

  private procesarToken(result: AuthenticationResult): void {
    const payload = this.decodeJwtPayload(result.accessToken);
    this.claims.set({
      iss: payload['iss'],
      aud: payload['aud'],
      sub: payload['sub'],
      exp: payload['exp'],
      scp: payload['scp'] ?? payload['scope'],
      roles: payload['roles'] ?? []
    });
    this.status.set('Access Token adquirido y payload JWT decodificado');
  }

  private decodeJwtPayload(token: string): Record<string, unknown> {
    const parts = token.split('.');
    if (parts.length !== 3) {
      throw new Error('El token no tiene formato JWT compacto de tres segmentos');
    }
    const normalized = parts[1].replace(/-/g, '+').replace(/_/g, '/');
    const padded = normalized.padEnd(Math.ceil(normalized.length / 4) * 4, '=');
    return JSON.parse(atob(padded)) as Record<string, unknown>;
  }
}
""",
    )

    angular_write(
        "src/app/app.component.html",
        """<main class="shell">
  <h1>CloudTasks · Semana 3</h1>
  <p>IDaaS · usuarios externos · OAuth2/OIDC · JWT · Claims</p>

  @if (!loggedIn()) {
    <button type="button" (click)="login()">Iniciar sesión con External ID</button>
  } @else {
    <button type="button" (click)="obtenerAccessToken()">Obtener y decodificar Access Token</button>
    <button type="button" (click)="logout()">Cerrar sesión</button>
  }

  <p><strong>Estado:</strong> {{ status() }}</p>

  @if (claims(); as value) {
    <h2>Claims observados</h2>
    <pre>{{ value | json }}</pre>
    <p>El token completo no se muestra ni se persiste.</p>
  }
</main>
""",
    )

    angular_write(
        "src/app/app.component.css",
        """.shell {
  max-width: 760px;
  margin: 3rem auto;
  padding: 0 1rem;
  font-family: system-ui, sans-serif;
}

button {
  margin-right: .75rem;
  margin-bottom: .75rem;
}

pre {
  overflow: auto;
  padding: 1rem;
  background: #f4f4f4;
}
""",
    )

    print("PASS  frontend materializado")


def print_next_steps() -> None:
    print("\n" + "=" * 72)
    print("CLOUDTASKS WEEK 03 MATERIALIZADO")
    print("=" * 72)
    print("Backend : guia/ev1/backend")
    print("Frontend: guia/ev1/frontend")
    print()
    print("Siguiente gate local:")
    print("  python3 scripts/validate_ev1.py --require-projects --strict")
    print()
    print("Después del PASS de build, configure valores reales en:")
    print("  guia/ev1/frontend/src/app/auth-config.ts")
    print()
    print("Luego siga:")
    print("  guias-integradas/ev1/02-entra-external-id.md")
    print("  guias-integradas/ev1/03-angular-msal.md")
    print("  guias-integradas/ev1/03b-checkpoint-semana-03-idaas-jwt-api-manager.md")


def main() -> int:
    args = parse_args()
    require_command("java")
    require_command("git")
    prepare(args)
    materialize_backend()
    materialize_frontend()
    print_next_steps()
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
