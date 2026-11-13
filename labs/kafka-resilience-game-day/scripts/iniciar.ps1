# Ejecucion: powershell -ExecutionPolicy Bypass -File .\scripts\iniciar.ps1
$ErrorActionPreference = "Stop"
$lab = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$base = (Resolve-Path (Join-Path $lab "..\kafka-compras-webapp")).Path
$extra = Join-Path $lab "compose.game-day.yaml"
$envFile = Join-Path $lab ".env.game-day"
Push-Location $base
try {
    docker compose --env-file $envFile -f compose.yaml -f $extra --profile app --profile pipeline up -d --build
    if ($LASTEXITCODE -ne 0) { throw "Fallo Docker Compose; revisar logs antes de inyectar carga." }
    docker compose --env-file $envFile -f compose.yaml -f $extra --profile app --profile pipeline ps
    if ($LASTEXITCODE -ne 0) { throw "No fue posible consultar estado." }
    Write-Host "Verificar: http://localhost:8083 (Kafka UI), http://localhost:5173 (web), http://localhost:8081/api/compras/salud"
} finally {
    Pop-Location
}
