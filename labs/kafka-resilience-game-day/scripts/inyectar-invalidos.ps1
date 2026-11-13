# Publicar directamente en Kafka: HTTP @Valid rechaza estos contratos ANTES del broker.
# Requiere Game Day iniciado. Produce 3 registros con claves.
$ErrorActionPreference = "Stop"
$eventos = @(
    'ERR-NEGATIVO|{"id":"negativo-01","cliente":"Ana","monto":-5,"fechaEvento":null}',
    'ERR-JSON|{json-malformado',
    'OK-DESPUES|{"id":"posterior-01","cliente":"Ana","monto":250,"fechaEvento":null}'
)
$eventos | docker exec -i dsy1107-kafka-s12 /opt/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic compras --property parse.key=true --property 'key.separator=|'
if ($LASTEXITCODE -ne 0) { throw "Falló la publicación directa a Kafka." }
Write-Host "Revisar compras-rechazadas (2 casos), compras-validas (caso posterior) y offsets en Kafka UI."
