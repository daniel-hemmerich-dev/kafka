# Apache Kafka Tutorial

## What is Apache Kafka?
Apache Kafka was created at LinkedIn and released on January 2011. It is written in Scala and Java. Apache Kafka is an event streaming platform.

Event streaming means capturing, storing and reacting to data from multiple sources. Think of a continuous flow and interpretation of data.

## Why to use Apache Kafka?
It is fast, scalable and distributed by design.
Kafka is a unified platform for handling real-time data feeds. Kafka supports low latency message delivery and gives guarantee for fault tolerance in the presence of machine failures.

Kafka can be deployed on bare-metal hardware, virtual machines, and containers, and on-premises as well as in the cloud. You can choose between self-managing your Kafka environments and using fully managed services offered by a variety of vendors.

## Use Cases of Apache Kafka
* Real-time transactions
* Tracking and monitoring
* Capturing and analyzing data from devices
* Collecting and reacting to customer interactions
* Distributing data

## The architecture of Apache Kafka
| Name                     | Description                                                           | Location        |
|--------------------------|-----------------------------------------------------------------------|-----------------|
| Event / Record / Message | Consists of a key and a value and optional metadata.                  | Client & Server |
| Topic                    | Events are organized in topics. Kinda files are orangized in folders. | Client & Server |
| Producer / Publisher     | Create events.                                                        | Client          |
| Consumer / Subscriber    | Read and process events from Kafka.                                   | Client          |
| Broker                   | Save events on storage.                                               | Server |
| Connector                | Import and export events from legacy systems.               | Server |
| Cluster                  | Can have multiple brokers and connectors.                             | Server |

## The Command Line Interface of Apache Kafka
In this example you will learn the basic Kafka usage for the terminal. I built a lot of helper scripts for ease of use as always in my tutorials. The goal is that you always get a brief understanding as fast as possible without the need of remembering commands.
1. ### Start Kafka with ZooKeeper
     Open a terminal and run:
     ```shell
     ./bin/helper/zookeeper/start.sh
     ```
2. ### Start Kafka Broker service
    Open another terminal session and run:
    ```shell
    ./bin/helper/kafka/start.sh
    ```
3. ### Create a topic
   Open another terminal session and run:
    ```shell
    ./bin/helper/kafka/topic/create.sh "topic-name"
    ```
4. ### Create some events (Producer)
    Run the following command in the terminal:
    ```shell
    ./bin/helper/kafka/topic/write.sh "topic-name"
    ```
    Wait for the prompt. Each line you enter will result in an event added to the topic.
5. ### Read the events (Consumer)
    Open another terminal session and run:
    ```shell
    ./bin/helper/kafka/topic/read.sh "topic-name"
    ```
    The events you added in the previous step should appear.
6. ### Have a look at the helper scrips
   Have a look inside the helper scripts and what they are doing to get more familiar with the Kafka commands.
7. ### Stop the write and read scripts
   In the terminal sessions of step 4. and 5. use `CTRL-C` command to stop them. 

## Producer & consumer application with Kafka
Let us have a look at how it does look in Java code (Gradle).
1. ### Add Kafka as a dependency
   Add the following line to the dependencies in your `build.gradle.kts` file.
   ```kotlin
   dependencies {
    implementation ("org.apache.kafka:kafka-clients:3.0.0")
    //...
   }
   ```
2. ### Create a producer and create an event
   Create a Main file and add the following code to it.
   ```java
   package org.tutorial;
   
   import org.apache.kafka.clients.producer.KafkaProducer;
   import org.apache.kafka.clients.producer.ProducerConfig;
   import org.apache.kafka.clients.producer.ProducerRecord;
   import java.util.Properties;
   
   public class Main {
       public static void main(String[] args) {
           // producer settings
           Properties properties = new Properties();
           properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // The server e.g. localhost:9092
           properties.put(ProducerConfig.ACKS_CONFIG, "all"); // The acknowledgement type e.g. all
           properties.put(ProducerConfig.RETRIES_CONFIG, 0); // The number of retries in case of failure e.g. 3
           properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384); // The maximum number of bytes a message can have
           properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432); // The amount of memory to buffer before sending
           properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
           properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
           properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
   
           // create producer
           try (KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties)) {
               // create an event
               kafkaProducer.send(
                   new ProducerRecord<>(
                      "topic-name",
                      "event-key",
                      "event-content"
                   )
               );
           }
       }
   }
   ```
3. ### Create a consumer and read events
   Create a Main file and add the following code to it.
   ```java
   package org.tutorial;
   
   import java.util.Properties;
   import org.apache.kafka.clients.consumer.ConsumerConfig;
   import org.apache.kafka.clients.consumer.KafkaConsumer;
   import java.util.Collections;
   import org.apache.kafka.clients.consumer.ConsumerRecords;
   import java.time.Duration;
   import org.apache.kafka.clients.consumer.ConsumerRecord;
   
   public class Main {
      public static void main(String[] args) {
         // consumer settings
         Properties properties = new Properties();
         properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // The server e.g. localhost:9092
         properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test"); // The group id where the consumer belongs to
         properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // If true, every offset change will be updated to ZooKeeper
         properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000); // How often information will be updated to ZooKeeper
         properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000); // How many milliseconds Kafka will wait for a ZooKeeper response
         properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
         properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
         properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
   
         // create consumer
         try (KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties)) {
            // subscribe to a topic
            kafkaConsumer.subscribe(Collections.singletonList("topic-name"));
   
            // read events from the topic
            while(true) {
               ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
               for (ConsumerRecord<String, String> record : records) {
                  System.out.printf(
                    "Read event with offset %d with key %s and value %s\n",
                    record.offset(),
                    record.key(),
                    record.value()
                  );
               }
            }
         }
      }
   }
   ```

[//]: # (ToDo: Add streaming part to the tutorial.)
   
## Running the Kafka examples
1. ### Select an example 
   Open the file located under:
   >src/main/java/org.tutorial/Main.
   
   In the main function uncomment this line. 
   ```java
   public class Main {
      public static void main(String[] args) {
           // Examples (uncomment for usage)
           //Simple.producer(topicName);
           //Simple.consumer(topicName);
      }
   }
   ```
   Build the application by running the following command in the terminal
   ```shell
   ./bin/helper/build.sh
   ```
   Finally run the application.
   ```shell
   ./bin/helper/run.sh
   ```
   
2. ### Have a look at the source code
   Look at the source code from the examples to understand how to use Kafka in a Java application.

## Stop Apache Kafka and delete all created data
If not already done you should stop all running terminal sessions with `CTRL-C`. Use the following order:
1. Stop the Java application.
2. Stop the consumer (reading process).
3. Stop the producer (writing process).
4. Stop the Kafka broker.
5. Stop the ZooKeeper.

### Delete all created topics and events
Open the terminal and enter the following command.
```shell
rm -rf /tmp/kafka-logs /tmp/zookeeper /tmp/kraft-combined-logs
```
All topics and events you created during this tutorial are deleted.

## What to do next?
### Official Kafka documentation
### Check out more Kafka tutorials
### Make a Kafka certification
### Support me
### Other tutorials that might interest you
 