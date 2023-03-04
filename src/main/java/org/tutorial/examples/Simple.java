package org.tutorial.examples;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.tutorial.Consumer;
import org.tutorial.Producer;

/**
 * A simple produce and consume example
 */
public class Simple {
    /**
     * An example on how to use the producer. Generates 10 random messages.
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
            String eventKey;
            String eventValue;

            RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
            for(int messageNumber=0; messageNumber<10; messageNumber++) {
                eventKey = randomStringGenerator.generate(6);
                eventValue = randomStringGenerator.generate(24);

                producer.send(
                    topic,
                    eventKey,
                    eventValue
                );

                System.out.printf(
                    "Created event in topic %s with key %s and value %s\n",
                    topic,
                    eventKey,
                    eventValue
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
                        "Read event from topic %s with offset %d with key %s and value %s\n",
                        topic,
                        record.offset(),
                        record.key(),
                        record.value()
                    );
                }
            }
        }
    }
}
