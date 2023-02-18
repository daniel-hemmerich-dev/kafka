package org.tutorial;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Main {
    public static void main(String[] args) throws Exception {
        //if(args.length == 0) throw new Exception("You need to pass a topic name as a command line argument");
        String topicName = "quickstart-events";//args[0];

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092"); //Assign localhost id
        props.put("acks", "all"); //Set acknowledgements for producer requests.
        props.put("retries", 0); //If the request fails, the producer can automatically retry,
        props.put("batch.size", 16384); //Specify buffer size in config
        props.put("linger.ms", 1); //Reduce the no of requests less than 0
        props.put("buffer.memory", 33554432); //The buffer.memory controls the total amount of memory available to the producer for buffering.
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        for(int i = 0; i < 10; i++) {
            producer.send(
                new ProducerRecord<>(
                    topicName,
                    Integer.toString(i),
                    Integer.toString(i)
                )
            );
            System.out.println("Message sent successfully");
        }

        producer.close();
        System.out.println("Producer closed");
    }
}