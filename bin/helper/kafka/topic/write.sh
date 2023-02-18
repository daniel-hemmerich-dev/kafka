#!/bin/sh
# List all topics
./bin/kafka_2.13-3.4.0/bin/kafka-topics.sh --list --bootstrap-server localhost:9092

# Create a topic
./bin/kafka_2.13-3.4.0/bin/kafka-topics.sh --create --topic "$1" --bootstrap-server localhost:9092
