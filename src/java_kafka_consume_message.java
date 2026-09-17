package java_console_app1;

 
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;


// for consumer
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

// calling rest api with key
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

// test entity

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 

 
import java.net.InetSocketAddress;
 

 
  
public class java_kafka_consume_message {

	 
	    public static void main(String[] args) {
	        // new KafkaConsumerWithTreadDemo().run();
	    	new java_kafka_consume_message().run();
	    }

	    
	    
	    public java_kafka_consume_message() {
	    }

	    
	    private void run() {
	        Logger logger = LoggerFactory.getLogger(java_kafka_consume_message.class);
	        

	    	GetPropertiesValues GPV = new GetPropertiesValues();
	    	Map<String, String> propMap = GPV.getPropValues();
	    	
			String kafka_server_and_port = propMap.get("kafka_server_and_port");
			String kafka_topic_name = propMap.get("kafka_topic_name");
			
	    	//String kafka_server_and_port  = "localhost:9092";
	    	//String kafka_topic_name = "localhost-events";

			
			
	        String bootstrapServer = kafka_server_and_port;  // "molegion:9092";
	        String groupId = "my-third-gfg-group";
	        String topic = kafka_topic_name;  // "quickstart-events";

	        // CountDownLatch for dealing with multiple threads
	        CountDownLatch latch = new CountDownLatch(1);

	        // Create the Consumer Runnable
	        logger.info("Creating the consumer thread");
	        ConsumerThread myConsumerThread = new ConsumerThread(topic, bootstrapServer, groupId, latch);

	        // Start the Thread
	        Thread myThread = new Thread(myConsumerThread);
	        myThread.start();

	        // Add a Shutdown Hook
	        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	            logger.info("Caught shutdown hook");
	            myConsumerThread.shutDown();
	            try {
	                latch.await();
	            } catch (InterruptedException e) {
	                throw new RuntimeException(e);
	            }
	            logger.info("Application has exited");
	        }

	        ));

	        try {
	            latch.await();
	        } catch (InterruptedException e) {
	            logger.error("Application got interrupted", e);
	        } finally {
	            logger.info("Application is Closing");
	        }
	    }

	    public class ConsumerThread implements Runnable {

	        private final CountDownLatch latch;
	        KafkaConsumer<String, String> consumer;
	        private final Logger logger = LoggerFactory.getLogger(ConsumerThread.class);
	        
 
			

	        public ConsumerThread(String topic, String bootstrapServer, String groupId, CountDownLatch latch) {

	            this.latch = latch;

	            // Create Consumer Properties
	            Properties properties = new Properties();
	            properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
	            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	            properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	            properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

	            // Create Consumer
	            consumer = new KafkaConsumer<>(properties);

	            // Subscribe Consumer to Our Topics
	            consumer.subscribe(List.of(topic));
	        }

	        @Override
	        public void run() {
	            try {
	                // Poll the data
	                while (true) {
	                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

	                    for (ConsumerRecord<String, String> record : records) {
	                    	System.out.println(record.key());
	                    	System.out.println(record.value());
	                    	System.out.println(record.offset());
	                    	
	                        logger.info("Key: " + record.key() +
	                                " Value: " + record.value() +
	                                " Partition: " + record.partition() +
	                                " Offset: " + record.offset()
	                        );
	                        
	                        
	                        try {
	                        	Thread.sleep(100);
	                        } catch (Exception e)
	                        {}
	                        
	                    }
	                }
	            } catch (WakeupException e) {
	                logger.info("Received shutdown signal");
	            } finally {
	                consumer.close();
	                // Tell our main code
	                // We are done
	                // with the consumer
	                latch.countDown();
	            }
	        }

	        public void shutDown() {
	            // The wakeup() method is used
	            // to interrupt consumer.poll()
	            // It will throw WakeUpException
	            consumer.wakeup();
	        }
	    }		 

 
 




}
