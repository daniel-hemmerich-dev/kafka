#!/bin/sh
# Write to a topic by the given argument. Each line will result in a separate event written to the topic.
./bin/kafka_2.13-3.4.0/bin/kafka-console-producer.sh --topic "$1" --bootstrap-server localhost:9092

