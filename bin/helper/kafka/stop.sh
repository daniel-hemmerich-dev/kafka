#!/bin/sh
# Stop Kafka
./bin/kafka_2.13-3.4.0/bin/kafka-server-stop.sh bin/kafka_2.13-3.4.0/config/server.properties

# Stop Zookeeper
#./bin/apache-zookeeper-3.7.1-bin/bin/zkServer.sh stop
./bin/kafka_2.13-3.4.0/bin/zookeeper-server-stop.sh bin/kafka_2.13-3.4.0/config/zookeeper.properties

