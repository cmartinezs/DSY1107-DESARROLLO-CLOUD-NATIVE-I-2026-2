# Desde labs\kafka-burst-incident: .\scripts\iniciar.ps1
$ErrorActionPreference = "Stop"
$lab = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$base = (Resolve-Path (Join-Path $lab "..\kafka-compras-webapp")).Path
Push-Location $base
try {
    $argsCompose = @("--project-name", "dsy1107-burst", "--env-file", (Join-Path $lab ".env.incident"), "-f", "compose.yaml", "-f", (Join-Path $lab "compose.incident.yaml"))
    & docker compose @argsCompose config --quiet
    if ($LASTEXITCODE -ne 0) { throw "Compose invalido; revisar configuracion antes de iniciar." }
    & docker compose @argsCompose --profile app --profile pipeline up -d --build
    if ($LASTEXITCODE -ne 0) { throw "No se pudo levantar el entorno." }
    & docker compose @argsCompose --profile app --profile pipeline ps
    if ($LASTEXITCODE -ne 0) { throw "Fallo al inspeccionar servicios." }
    Write-Host "Abrir: http://localhost:8083 (Kafka UI) y http://localhost:8081/api/compras/salud"
} finally { Pop-Location }
