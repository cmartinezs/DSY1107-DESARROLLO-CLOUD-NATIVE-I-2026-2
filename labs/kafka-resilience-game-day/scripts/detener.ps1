# Detener al finalizar y DESPUES de capturar evidencias.
# IMPORTANTE: el Kafka del laboratorio no tiene volumen persistente. 'down' elimina los eventos.
$ErrorActionPreference = "Stop"
$lab = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$base = (Resolve-Path (Join-Path $lab "..\kafka-compras-webapp")).Path
Push-Location $base
try {
    docker compose --env-file (Join-Path $lab ".env.game-day") -f compose.yaml -f (Join-Path $lab "compose.game-day.yaml") --profile app --profile pipeline down
    if ($LASTEXITCODE -ne 0) { throw "No fue posible detener Compose correctamente." }
} finally {
    Pop-Location
}
