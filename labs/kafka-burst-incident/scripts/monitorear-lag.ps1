# Ejecutar en una SEGUNDA PowerShell mientras JMeter envia la oleada.
param([int]$Snapshots = 35, [int]$EverySeconds = 10, [string]$RunId = "antes")
$ErrorActionPreference = "Stop"
if ($RunId -notmatch '^[a-zA-Z0-9_-]+$') { throw "RunId invalido." }
if ($Snapshots -lt 1 -or $Snapshots -gt 100 -or $EverySeconds -lt 2 -or $EverySeconds -gt 120) { throw "Argumentos fuera de rango." }
$lab = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$results = Join-Path $lab "results"
New-Item -ItemType Directory -Force -Path $results | Out-Null
$file = Join-Path $results ("lag-" + $RunId + ".txt")
if (Test-Path $file) { throw "El archivo $file ya existe. Usa un RunId nuevo." }
for ($i = 0; $i -lt $Snapshots; $i++) {
    $header = ("=== SNAPSHOT {0}, {1} ===" -f ($i + 1), (Get-Date -Format o))
    $header | Tee-Object -FilePath $file -Append
    & docker exec dsy1107-kafka-s12 /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group procesador-s13 --describe 2>&1 | Tee-Object -FilePath $file -Append
    if ($LASTEXITCODE -ne 0) { Write-Warning "No se pudo leer grupo; revisar el estado del stack." }
    if ($i -lt ($Snapshots - 1)) { Start-Sleep -Seconds $EverySeconds }
}
Write-Host "Historial de lag: $file"
