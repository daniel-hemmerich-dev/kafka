#!/bin/sh
# Stop Kafka
./bin/kafka_2.13-3.4.0/bin/kafka-server-stop.sh bin/kafka_2.13-3.4.0/config/server-one.properties
./bin/kafka_2.13-3.4.0/bin/kafka-server-stop.sh bin/kafka_2.13-3.4.0/config/server-two.properties
./bin/kafka_2.13-3.4.0/bin/kafka-server-stop.sh bin/kafka_2.13-3.4.0/config/server-three.properties