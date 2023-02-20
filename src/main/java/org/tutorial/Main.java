package org.tutorial;

import org.tutorial.examples.Simple;

/**
 * The main entry point of the application
 */
public class Main {
    /** The topic name to use for the examples */
    final private static String topicName = "quickstart-events";

    /**
     * The entry point of the application
     * @param args The command line arguments (You must specify the topic name as first argument)
     */
    public static void main(String[] args) {
        //if(args.length == 0) throw new Exception("You need to pass a topic name as the first command line argument");
        //String topicName = "quickstart-events";//args[0];

        //Simple.producerExample(topicName);
        Simple.consumer(topicName);
    }
}