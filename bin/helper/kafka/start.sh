#!/bin/sh
# Start Zookeeper
#./bin/apache-zookeeper-3.7.1-bin/bin/zkServer.sh start
./bin/kafka_2.13-3.4.0/bin/zookeeper-server-start.sh bin/kafka_2.13-3.4.0/config/zookeeper.properties

# Start Kafka
./bin/kafka_2.13-3.4.0/bin/kafka-server-start.sh bin/kafka_2.13-3.4.0/config/server.properties