#!/bin/sh
# Destroy a topic with the given argument as name.
./bin/kafka_2.13-3.4.0/bin/kafka-topics.sh --delete --topic "$1" --bootstrap-server localhost:9092


