# Corre JMeter por 300s. Docker Desktop debe estar funcionando.
# No ejecutar sobre URLs remotas ni entornos compartidos.
param([string]$RunId = "antes", [string]$LoadScale = "1")
$ErrorActionPreference = "Stop"
if ($RunId -notmatch '^[a-zA-Z0-9_-]+$') { throw "RunId invalido (solo letras, numeros, - y _)." }
try {
    $scale = [double]::Parse($LoadScale, [System.Globalization.CultureInfo]::InvariantCulture)
} catch { throw "LoadScale invalido: usa punto decimal, por ejemplo 0.5." }
if ($scale -le 0 -or $scale -gt 1) { throw "LoadScale debe estar en (0,1]." }
$lab = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$base = (Resolve-Path (Join-Path $lab "..\kafka-compras-webapp")).Path
$results = Join-Path $lab "results"
New-Item -ItemType Directory -Force -Path $results | Out-Null
$file = Join-Path $results ("oleada-" + $RunId + ".jtl")
if (Test-Path $file) { throw "Ya existe $file; usa otro RunId para no mezclar evidencias." }
Push-Location $base
try {
    $argsCompose = @("--project-name", "dsy1107-burst", "--env-file", (Join-Path $lab ".env.incident"), "-f", "compose.yaml", "-f", (Join-Path $lab "compose.incident.yaml"))
    & docker compose @argsCompose --profile load-wave run --no-deps --build --rm jmeter-wave -t /tests/oleada-300s.jmx -Jhost=compras-api -Jport=8080 -JclientePrefix=LAB-OK- "-JloadScale=$LoadScale" -l ("/results/oleada-" + $RunId + ".jtl")
    if ($LASTEXITCODE -ne 0) { throw "JMeter termino con error; conservar logs/resultados para diagnostico." }
    Write-Host "Resultado JMeter: $file"
} finally { Pop-Location }
