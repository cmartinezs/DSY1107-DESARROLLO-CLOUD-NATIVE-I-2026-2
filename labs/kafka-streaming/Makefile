SHELL := /bin/bash
C := docker exec dsy1107-kafka-s12
K := /opt/kafka/bin
BS := localhost:9092
.PHONY: up down logs check topics describe producer consumer group-a group-b groups reset clean
up:
	docker compose up -d
check:
	$(C) $(K)/kafka-broker-api-versions.sh --bootstrap-server $(BS) >/dev/null && echo 'Kafka responde correctamente'
topics:
	$(C) $(K)/kafka-topics.sh --bootstrap-server $(BS) --create --if-not-exists --topic compras --partitions 3 --replication-factor 1
	$(C) $(K)/kafka-topics.sh --bootstrap-server $(BS) --create --if-not-exists --topic compras-resumen --partitions 3 --replication-factor 1
describe:
	$(C) $(K)/kafka-topics.sh --bootstrap-server $(BS) --describe --topic compras
producer:
	$(C) -i $(K)/kafka-console-producer.sh --bootstrap-server $(BS) --topic compras --property parse.key=true --property key.separator=:
consumer:
	$(C) $(K)/kafka-console-consumer.sh --bootstrap-server $(BS) --topic compras --from-beginning --property print.key=true --property print.partition=true --property print.offset=true
group-a:
	$(C) $(K)/kafka-console-consumer.sh --bootstrap-server $(BS) --topic compras --group analytics --property print.key=true --property print.partition=true --property print.offset=true
group-b:
	$(C) $(K)/kafka-console-consumer.sh --bootstrap-server $(BS) --topic compras --group notificaciones --property print.key=true --property print.partition=true --property print.offset=true
groups:
	$(C) $(K)/kafka-consumer-groups.sh --bootstrap-server $(BS) --describe --group analytics
reset:
	$(C) $(K)/kafka-consumer-groups.sh --bootstrap-server $(BS) --group analytics --topic compras --reset-offsets --to-earliest --execute
logs:
	docker compose logs -f kafka
down:
	docker compose down
clean:
	docker compose down -v --remove-orphans
