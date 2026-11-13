# ATENCION: este broker no tiene volumen persistente. Capturar evidencias antes de bajar.
$ErrorActionPreference = "Stop"
$lab = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$base = (Resolve-Path (Join-Path $lab "..\kafka-compras-webapp")).Path
Push-Location $base
try {
    & docker compose --project-name dsy1107-burst --env-file (Join-Path $lab ".env.incident") -f compose.yaml -f (Join-Path $lab "compose.incident.yaml") --profile app --profile pipeline down
    if ($LASTEXITCODE -ne 0) { throw "Error deteniendo Compose." }
} finally { Pop-Location }
