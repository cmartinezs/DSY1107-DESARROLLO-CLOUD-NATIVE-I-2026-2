param(
    [ValidateRange(1, 20000)]
    [int]$Count = 1000,

    [string]$ApiUrl = "http://localhost:8081/api/compras"
)

$ErrorActionPreference = "Stop"
$stopwatch = [System.Diagnostics.Stopwatch]::StartNew()
$success = 0

for ($i = 1; $i -le $Count; $i++) {
    $body = @{
        cliente = "cliente-$($i % 25)"
        monto = 100 + ($i % 4900)
    } | ConvertTo-Json -Compress

    try {
        Invoke-RestMethod -Uri $ApiUrl -Method Post -ContentType "application/json" -Body $body | Out-Null
        $success++
    }
    catch {
        Write-Error "Falló la publicación de la compra $i. $($_.Exception.Message)"
        throw
    }

    if ($i % 100 -eq 0 -or $i -eq $Count) {
        Write-Progress -Activity "Generando compras" -Status "$i de $Count" -PercentComplete (($i / $Count) * 100)
    }
}

$stopwatch.Stop()
$rate = if ($stopwatch.Elapsed.TotalSeconds -gt 0) {
    [math]::Round($success / $stopwatch.Elapsed.TotalSeconds, 2)
} else {
    0
}

Write-Progress -Activity "Generando compras" -Completed
Write-Host "Publicación completada: $success/$Count solicitudes."
Write-Host "Tiempo HTTP total: $([math]::Round($stopwatch.Elapsed.TotalSeconds, 2)) s."
Write-Host "Solicitudes por segundo: $rate."
Write-Host "Nota: esta tasa mide el recorrido HTTP + confirmación de publicación; no equivale por sí sola al throughput interno de Kafka."
