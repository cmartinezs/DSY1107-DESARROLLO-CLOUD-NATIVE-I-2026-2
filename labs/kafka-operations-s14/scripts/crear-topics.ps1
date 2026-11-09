param(
    [string]$Container = "dsy1107-kafka-s12",
    [string]$BootstrapServer = "localhost:9092"
)

$ErrorActionPreference = "Stop"

function Invoke-KafkaTopics {
    param([string[]]$Arguments)

    & docker exec $Container /opt/kafka/bin/kafka-topics.sh --bootstrap-server $BootstrapServer @Arguments
    if ($LASTEXITCODE -ne 0) {
        throw "Falló kafka-topics.sh. Revisa que el broker '$Container' esté activo."
    }
}

function Set-TopicRetention {
    param(
        [string]$Topic,
        [long]$RetentionMs,
        [long]$RetentionBytes
    )

    $config = "retention.ms=$RetentionMs,retention.bytes=$RetentionBytes,cleanup.policy=delete"
    & docker exec $Container /opt/kafka/bin/kafka-configs.sh --bootstrap-server $BootstrapServer --entity-type topics --entity-name $Topic --alter --add-config $config
    if ($LASTEXITCODE -ne 0) {
        throw "No se pudo configurar la retención del topic '$Topic'."
    }
}

Write-Host "Verificando broker Kafka..."
& docker exec $Container /opt/kafka/bin/kafka-broker-api-versions.sh --bootstrap-server $BootstrapServer
if ($LASTEXITCODE -ne 0) {
    throw "Kafka no está disponible. Inicia primero el laboratorio kafka-compras-webapp."
}

$topics = @(
    @{ Name = "compras"; RetentionMs = 604800000; RetentionBytes = 104857600 },
    @{ Name = "compras-validas"; RetentionMs = 259200000; RetentionBytes = 52428800 },
    @{ Name = "compras-rechazadas"; RetentionMs = 604800000; RetentionBytes = 52428800 },
    @{ Name = "rendimiento-base-s14"; RetentionMs = 3600000; RetentionBytes = 104857600 },
    @{ Name = "rendimiento-tuned-s14"; RetentionMs = 3600000; RetentionBytes = 104857600 }
)

foreach ($topic in $topics) {
    Write-Host "Creando/verificando topic $($topic.Name)..."
    Invoke-KafkaTopics -Arguments @(
        "--create", "--if-not-exists",
        "--topic", $topic.Name,
        "--partitions", "3",
        "--replication-factor", "1"
    )

    Write-Host "Aplicando retención a $($topic.Name)..."
    Set-TopicRetention -Topic $topic.Name -RetentionMs $topic.RetentionMs -RetentionBytes $topic.RetentionBytes
}

Write-Host "`nTopics configurados. Revisa las particiones y el replication factor:"
foreach ($topic in $topics) {
    Write-Host "`n--- $($topic.Name) ---"
    Invoke-KafkaTopics -Arguments @("--describe", "--topic", $topic.Name)
    & docker exec $Container /opt/kafka/bin/kafka-configs.sh --bootstrap-server $BootstrapServer --entity-type topics --entity-name $topic.Name --describe
    if ($LASTEXITCODE -ne 0) {
        throw "No se pudo leer la configuración de $($topic.Name)."
    }
}

Write-Host "`nListo. Recuerda: RF=1 en este broker único no proporciona tolerancia a fallos."
