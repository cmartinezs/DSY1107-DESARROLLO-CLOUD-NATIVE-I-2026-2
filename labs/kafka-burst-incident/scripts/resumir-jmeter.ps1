# Resumen por intervalos de 15 segundos de un CSV JMeter (-l).
param([Parameter(Mandatory=$true)][string]$RunId, [int]$BucketSeconds = 15)
$ErrorActionPreference = "Stop"
if ($RunId -notmatch '^[a-zA-Z0-9_-]+$') { throw "RunId invalido." }
if ($BucketSeconds -lt 1 -or $BucketSeconds -gt 60) { throw "BucketSeconds fuera de rango." }
$lab = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$file = Join-Path $lab ("results\oleada-" + $RunId + ".jtl")
if (-not (Test-Path $file)) { throw "No se encontro $file" }
$rows = @(Import-Csv -Path $file)
if ($rows.Count -eq 0 -or -not ($rows[0].PSObject.Properties.Name -contains 'timeStamp')) {
    throw "JTL vacio o sin timeStamp: revisar configuracion de guardado CSV."
}
$start = [long](($rows | Measure-Object -Property timeStamp -Minimum).Minimum)
$aggregated = @($rows | Group-Object -Property {
    $elapsedS = ([long]$_.timeStamp - $start) / 1000.0
    [int][math]::Floor($elapsedS / $BucketSeconds) * $BucketSeconds
} | Sort-Object { [int]$_.Name } | ForEach-Object {
    $bucket = [int]$_.Name
    $count = $_.Count
    $errors = @($_.Group | Where-Object { $_.success -ne "true" }).Count
    $average = ($_.Group | Measure-Object -Property elapsed -Average).Average
    [PSCustomObject]@{
        'Desde (s)' = $bucket
        'Hasta (s)' = $bucket + $BucketSeconds
        'Requests' = $count
        'Req/s' = [math]::Round($count / $BucketSeconds, 2)
        'Errores HTTP/asercion' = $errors
        'Latencia media HTTP (ms)' = [math]::Round($average, 1)
    }
})
$aggregated | Format-Table -AutoSize
$out = Join-Path $lab ("results\resumen-" + $RunId + ".csv")
$aggregated | Export-Csv -Path $out -Encoding UTF8 -NoTypeInformation
Write-Host "Resumen de tramos guardado: $out"
