package org.tutorial;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Consumes (reads) data from the Apache Kafka queue
 */
public class Consumer implements AutoCloseable {
    final private KafkaConsumer<String, String> kafkaConsumer;

    /**
     * Instantiate a Consumer
     * @param server The server e.g. localhost:9092
     * @param groupId The group id where the consumer belongs to
     * @param autoCommit If true, every offset change will be updated to ZooKeeper
     * @param autoCommitInterval How often information will be updated to ZooKeeper
     * @param sessionTimeout How many milliseconds Kafka will wait for a ZooKeeper response
     */
    public Consumer(
        final String server,
        final String groupId,
        final Boolean autoCommit,
        final Integer autoCommitInterval,
        final Integer sessionTimeout
    ) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        this.kafkaConsumer = new KafkaConsumer<>(properties);
    }

    /**
     * Cleanup
     */
    @Override
    public void close() {
        this.kafkaConsumer.close();
    }

    /**
     * Subscribe to the given topic
     * @param topic The topic name to subscribe
     */
    public void subscribe(final String topic) {
        this.kafkaConsumer.subscribe(
            Collections.singletonList(topic)
        );
    }

    /**
     * Start reading messages from the beginning of the queue
     */
    public void seekToBeginning() {
        this.kafkaConsumer.poll(
            Duration.ofMillis(0)
        );
        this.kafkaConsumer.seekToBeginning(
            this.kafkaConsumer.assignment()
        );
    }

    /**
     * Check if there are new messages and return them
     * @param timeoutMilliseconds The number of milliseconds to block
     * @return The new messages since the last call
     */
    public ConsumerRecords<String, String> poll(final Integer timeoutMilliseconds) {
        return this.kafkaConsumer.poll(
            Duration.ofMillis(timeoutMilliseconds)
        );
    }
}
