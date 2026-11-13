# Validacion de configuracion y servicios; no crea ni destruye recursos.
$ErrorActionPreference = "Stop"
$lab = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$base = (Resolve-Path (Join-Path $lab "..\kafka-compras-webapp")).Path
Push-Location $base
try {
    $argsCompose = @("--env-file", (Join-Path $lab ".env.game-day"), "-f", "compose.yaml", "-f", (Join-Path $lab "compose.game-day.yaml"))
    & docker compose @argsCompose config --quiet
    if ($LASTEXITCODE -ne 0) { throw "Compose config INVALIDA." }
    $running = @(& docker compose @argsCompose --profile app --profile pipeline ps --services --status running)
    if ($LASTEXITCODE -ne 0) { throw "No fue posible consultar servicios en ejecucion." }
    foreach ($svc in @("kafka", "kafka-ui", "compras-api", "resumen-api", "procesador-api", "web")) {
        if ($running -notcontains $svc) { throw "Servicio no disponible: $svc" }
    }
    $salud = Invoke-RestMethod -Method Get -Uri "http://localhost:8081/api/compras/salud" -TimeoutSec 10
    if ($salud.estado -ne "ok") { throw "API sin estado OK." }
    $topics = @(& docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list)
    if ($LASTEXITCODE -ne 0) { throw "No es posible listar topics de Kafka." }
    foreach ($topic in @("compras", "compras-validas", "compras-rechazadas", "compras-dlt")) {
        if ($topics -notcontains $topic) { throw "Topic faltante: $topic" }
    }
    Write-Host "PREFLIGHT PASS (config, 6 servicios, API HTTP y 4 topics)." -ForegroundColor Green
} finally {
    Pop-Location
}
