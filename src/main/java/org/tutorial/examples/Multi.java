package org.tutorial.examples;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.tutorial.Consumer;
import org.tutorial.Producer;

/**
 * A simple produce and consume example
 */
public class Multi {
    /**
     * An example on how to use the producer
     * @param topic The topic name where the messages will be created in
     */
    public static void producer(final String topic) {
        try (Producer producer = new Producer(
            "localhost:9092",
            "all",
            0,
            16384,
            33554432
        )) {
            for(int i=100; i<110; i++) {
                producer.send(
                    topic,
                    Integer.toString(i),
                    Integer.toString(i)
                );
            }
        }
    }

    /**
     * An example on how to use the consumer
     * @param topic The topic name from whom to read messages
     */
    public static void consumer(final String topic) {
        try (Consumer consumer = new Consumer(
            "localhost:9092",
            "test",
            false,
            1000,
            30000
        )) {
            consumer.subscribe(topic);

            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf(
                        "Read message offset %d with key %s and value %s\n",
                        record.offset(),
                        record.key(),
                        record.value()
                    );
                }
            }
        }
    }
}
