package org.tutorial;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

/**
 * Produces (creates) messages in the Apache Kafka queue.
 */
public class Producer implements AutoCloseable {
    final private KafkaProducer<String, String> kafkaProducer;

    /**
     * Instantiate a producer
     * @param server The server e.g. localhost:9092
     * @param acknowledgements The acknowledgement type e.g. all
     * @param retries The number of retries in case of failure e.g. 3
     * @param batchSize The maximum number of bytes a message can have
     * @param bufferMemory The amount of memory to buffer before sending
     */
    public Producer(
        final String server,
        final String acknowledgements,
        final Integer retries,
        final Integer batchSize,
        final Integer bufferMemory
    ) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        properties.put(ProducerConfig.ACKS_CONFIG, acknowledgements);
        properties.put(ProducerConfig.RETRIES_CONFIG, retries);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        this.kafkaProducer = new KafkaProducer<>(properties);
    }

    /**
     * Cleanup
     */
    @Override
    public void close() {
        this.kafkaProducer.close();
    }

    /**
     * Sends a message given by the key and value parameter to the given topic
     * @param topic The name of the topic
     * @param key The key of the message
     * @param value The value of the message
     */
    public void send(
        final String topic,
        final String key,
        final String value
    ) {
        this.kafkaProducer.send(
            new ProducerRecord<>(
                topic,
                key,
                value
            )
        );
    }
}
