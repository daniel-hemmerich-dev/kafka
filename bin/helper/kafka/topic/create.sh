#!/bin/sh
# Create a topic with the given argument as topic name
./bin/kafka_2.13-3.4.0/bin/kafka-topics.sh --create --topic "$1" --bootstrap-server localhost:9092
