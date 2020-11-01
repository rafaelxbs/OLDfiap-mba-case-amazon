Subir containers: docker-compose up -d
Criar tópico Kafka: docker-compose exec kafka kafka-topics --create --bootstrap-server :9092 --replication-factor 1 --partitions 1 --topic productEventTopic
docker-compose exec kafka kafka-topics -bootstrap-server :9092 --list
