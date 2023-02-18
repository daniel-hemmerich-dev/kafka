#!/bin/sh
# Read every event from a topic by the given argument.
./bin/kafka_2.13-3.4.0/bin/kafka-console-consumer.sh --topic "$1" --from-beginning --bootstrap-server localhost:9092

