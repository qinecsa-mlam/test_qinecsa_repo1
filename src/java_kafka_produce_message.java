package java_console_app1;

 
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;


//replay message
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map; 
import java.util.Collections;


// calling rest api with key
import java.io.*;
import java.net.*; 

import org.json.JSONObject;  

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

 


// This will send messages to molegion desktop.  Kafka must be running there and must have created a topic called 'quickstart-events'
// msg comes from IRTAdapter: 
// http://localhost:8080/IRTAdapter/api/irt_array/2:1:get-30-rows (just pulls 30 recs into JSON array...
// 
// To test on the client: 
// C:\kafka_2.12-3.9.1\bin\windows>kafka-console-producer.bat --topic quickstart-events --bootstrap-server molegion:9092

  
public class java_kafka_produce_message {

	public static void main(String[] args) throws IOException {
		 
		 
		//test_rest_api2b_send_msg_to_topic(1); // pulls from irt_database  
		
		
		
		// loop thru x and call test_send_to_topic() 
		for (int x=1;x<=50;x++)
		{
			int counter = x;
			// this is the message to send:
			String _json = "{\"quickstart-events_topic_index " + x + "\" : \"Message 1002 June/02 to quickstart-events topic on moubuntu3 vm...\"}";
			
			String _hardcode_topic_name = "quickstart-events";  // instead of getting from jdbc.properties
			
			test_send_to_topic(_json, counter, _hardcode_topic_name);  
			try {
				Thread.sleep(900);   //sleep for 3 secs or half min
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		// replay messages with the word 'Error'
		//replay_message_to_topic("Error");
		
		
	}
 
	

	public static void test_send_to_topic(String _json, int _counter, String kafka_topic_name) {


    	GetPropertiesValues GPV = new GetPropertiesValues();
    	Map<String, String> propMap = GPV.getPropValues();
    	
		String kafka_server_and_port = propMap.get("kafka_server_and_port");
		//String kafka_topic_name = propMap.get("kafka_topic_name");
    	
    	 
		
		System.out.println(" inside test_send_to_topic() " + kafka_server_and_port + " : " + kafka_topic_name);
		
		
        Properties props = new Properties();
        props.put("bootstrap.servers", kafka_server_and_port);   // "molegion:9092");  molegion is the legion desktop, hosts file should have correct ip address
        props.put("acks", "all"); // Acknowledge all replicas
        props.put("retries", 0); // Number of retries on failed sends
        props.put("batch.size", 16384); // Batch size for sending records
        props.put("linger.ms", 1); // Delay before sending batches
        props.put("buffer.memory", 33554432); // Producer buffer size
        props.put("key.serializer", StringSerializer.class.getName()); // Serializer for the key
        props.put("value.serializer", StringSerializer.class.getName()); // Serializer for the value
 
        
	 
        System.out.println(_counter + " : " + _json);
        
        
        
		try (Producer<String, String> producer = new KafkaProducer<>(props)) 
			{
		        
		        String topic = kafka_topic_name;   // "quickstart-events"  name of topic created in kafka
		        
		        System.out.println(" publishing to this topic: " + topic);
		         
		        
		        
		        String key = "key-" + _counter;
		        String value = _json;  // "Hello Kafka from cii laptop! Message " + i;
		        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
		
		        // Asynchronously send the record
		        producer.send(record, (RecordMetadata metadata, Exception e) -> {
		                if (e != null) {
		                    e.printStackTrace();
		                } else {
		                    System.out.printf("Sent record to topic %s, partition %d, offset %d%n",
		                            metadata.topic(), metadata.partition(), metadata.offset());
		                }
		        });
		     
		
		        
		        
		        producer.flush(); 
		    } 
		    catch (Exception e) {
		        e.printStackTrace();
		    }
	 
		
	}



	
	 
	public static void replay_message_to_topic(String keyword) {

    	GetPropertiesValues GPV = new GetPropertiesValues();
    	Map<String, String> propMap = GPV.getPropValues();
    	String kafka_server_and_port = propMap.get("kafka_server_and_port"); 
		
    	

	      Properties consumerProperties = new Properties();
	      consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka_server_and_port);
	      consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "quickstart-events");
	      consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
	      consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
	      KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
	      consumer.subscribe(Collections.singletonList("quickstart-events-replay"));   //quickstart-events-replay ??
	      Properties producerProperties = new Properties();
	      producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka_server_and_port);
	      producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
	      producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

	      KafkaProducer<String, String> producer = new KafkaProducer<>(producerProperties);
 

	      while (true) {
	    	  
	    	  try 
	    	  {
		          ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
		          for (ConsumerRecord<String, String> record : records) {
		              if (record.value().contains(keyword)) {
		                  System.out.println("==============> Replaying filtered message: " + record.value());
		                  Thread.sleep(3000);   //sleep for 3 secs  
		                  producer.send(new ProducerRecord<>("quickstart-events", record.value()));
		              }
		          }
	    	  } catch (Exception ex)
	    	  {
	    		  System.out.println(" error with replaying message : " + ex.toString() );
	    	  }
	      }		
		
	}
	
	

	 
	
	
	
    

	public static JSONArray  get_irt_output_jsonarray(GetPropertiesValues properties, String param_value, String condition) {   
		System.out.println(" inside get_irt_output_jsonarray() ...param_value and condition ::: " + param_value + " : " +  condition); 
		
		String api_key=properties.getAPIKey();
		
		String api_key_value = properties.getAPIKeyValue(); //unencrypted_api_key_value;
  
		
		param_value=param_value + ":" + condition;
	 

		Map<String, String> propMap = properties.getPropValues();
		String api_host_name_irt_adapter = propMap.get("api_host_name_irt_adapter");  // "http://localhost:8080/IRTAdapter";  
		
		
		System.out.println( api_host_name_irt_adapter);
		
		
		// 7/21 need to do this now!
		properties.getPropValues();
		

		
        String CONNECT_API_URL =  api_host_name_irt_adapter + "/api/irt_array/" + param_value;   
        System.out.println(CONNECT_API_URL);
        
        
        String CONNECT_API_KEY = api_key_value;  
        JSONArray _array = null;
        
        try 
        { 
	        URL url = new URL(CONNECT_API_URL);
	        URLConnection urlc = url.openConnection();
	        
	        urlc.setRequestProperty("X-Token", CONNECT_API_KEY);
	        
	
	        BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
	        String _output = null;
	        String _data = null;
	        
	        while ((_output=br.readLine())!=null) {
	            // System.out.println(_output);
	            _data = _output;
	        }
	        br.close();	        

	        _array = new JSONArray(_data); 
	        
	        
	        System.out.println("  _array.length() :::  " + _array.length());
	         
	    }
		catch(Exception ex)
		{
		    System.out.println(ex.getMessage());
		}	
	
        return _array;
	}
	
	
	
	
	public static void test_rest_api2b_send_msg_to_topic(int _counter) {  
		
 

		String param_value="2:1";    // use to store synch_id and job_id combo
	
		int synch_id=0;
		int job_id=0;
	         
		//param_value = parameter.toString();
		System.out.println(" param_value :: " + param_value);
		    
		String[] string_array = param_value.split(":");
		synch_id = Integer.parseInt( string_array[0]);
		job_id   = Integer.parseInt( string_array[1]);
	
		
		// 7/22 optimize sync by passing in GetPropertiesValues 
		GetPropertiesValues properties = new GetPropertiesValues();
		properties.getPropValues();		
		
		String condition = "get-30-rows";  // just get me 30 rows...
		
        JSONArray _array = get_irt_output_jsonarray(properties, param_value, condition);   // get from irt db  

        try {

        	int x = 1;
        	
        	/* 
            for(int i=0; i < _array.length(); i++)
 		    {
 		    	JSONObject object = _array.getJSONObject(i);
 		    
 		    	
 		    	test_send_to_topic(object.toString(), i);  // _counter);
 		    	Thread.sleep(2000);
 		  

 		    }*/




        }
        catch (Exception e) {
            System.out.println(" Exception: "+ e.getMessage());
        }





	}
	
	


	public static void test_rest_api2_send_msg_to_topic(int _counter) {  

		String api_host_name = "https://randomuser.me/api/";
        JSONArray _array = null;

        try {

            HttpClient client = HttpClientBuilder.create().build();
     
            HttpUriRequest httpUriRequest = new HttpGet(api_host_name);  //CONNECT_API_URL);
    
            HttpResponse response = client.execute(httpUriRequest);
   
            String result = EntityUtils.toString(response.getEntity());
  
            JSONObject myObject = new JSONObject(result);
 
            _array = new JSONArray(myObject.getJSONArray("results"));
             
            for(int i=0; i < _array.length(); i++)
 		    {
 		    	JSONObject object = _array.getJSONObject(i);
 		    
 		    	//test_send_to_topic(object.toString(), _counter);
 		    	
 		    	
 		    	/*
 		    	System.out.println(object.getString("gender"));
 		    	System.out.println(object.getJSONObject("name").getString("first") );
 		    	System.out.println(object.getJSONObject("name").getString("last") );
 		    	System.out.println(object.getJSONObject("dob").getString("date") );
 		    	System.out.println(object.getJSONObject("dob").getInt("age") );
                */

 		    }




        }
        catch (Exception e) {
            System.out.println(" Exception: "+ e.getMessage());
        }





	}
	
	 

	 



	public static List<SubjectVisitForm> getIRTTerms(int siteid)
	{

		List<SubjectVisitForm> list_of_ae_terms = new ArrayList<SubjectVisitForm> ();


		try {
		    GetDbConnection cn = new GetDbConnection();
		    Connection Conn = cn.getConnection();

	        long now = System.currentTimeMillis();
	        Timestamp sqlTimestamp = new Timestamp(now);

		    String sqlCommand = "select a.subjectid, a.visitid, a.visitidkey, a.visitdate::text from subjectvisit a, visit b, subject c where a.visitidkey=b.visitid and b.visitname like 'DV%' and a.subjectid = c.subjectid and c.siteid = " + siteid + " order by a.subjectid, a.visitidkey";
		    System.out.println(sqlCommand);
		    PreparedStatement preparedStatement = Conn.prepareStatement(sqlCommand);
	        ResultSet rs  = preparedStatement.executeQuery();
	        while (rs.next()) {
	          SubjectVisitForm SVF = new SubjectVisitForm();
	          SVF.setSubjectid(rs.getInt("subjectid"));
	          SVF.setVisitid(rs.getInt("visitid"));
	          SVF.setVisitkeyid(rs.getInt("visitidkey"));
	          SVF.setFormdate(rs.getString("visitdate"));
	          SVF.setSiteid(siteid);

	          list_of_ae_terms.add(SVF);
	        }

		    preparedStatement.close();
		    Conn.close();



		}
		catch (SQLException E) {
		    System.out.println("SQLException: " + E.getMessage());
		}
		catch(Exception ex)
		{
		    System.out.println(ex.getMessage());
		}

		return list_of_ae_terms;

	}



 


	public static List<Integer> getUncodedTermsFormid(int codingset_id, String codingset_name)
	{

		List<Integer> uncoded_list_of_formid = new ArrayList<> ();


		try {
		    GetDbConnection cn = new GetDbConnection();
		    Connection Conn = cn.getCodingConnection();
		    System.out.println(" connected to coding_dict ...");
	        long now = System.currentTimeMillis();
	        Timestamp sqlTimestamp = new Timestamp(now);

		    String sqlCommand = "select formid from " + codingset_name + " where formid not in ( select id from codingset_data where codingset_id= " + codingset_id + ")";
		    System.out.println(sqlCommand);
		    PreparedStatement preparedStatement = Conn.prepareStatement(sqlCommand);
	        ResultSet rs  = preparedStatement.executeQuery();
	        while (rs.next()) {
	          uncoded_list_of_formid.add(   rs.getInt("formid") );
	        }

		    System.out.println(" num of uncoded terms in codingset_bm_ae2 ::: " + uncoded_list_of_formid.size());
		    preparedStatement.close();
		    Conn.close();



		}
		catch (SQLException E) {
		    System.out.println("SQLException: " + E.getMessage());
		}
		catch(Exception ex)
		{
		    System.out.println(ex.getMessage());
		}

		return uncoded_list_of_formid;

	}




 


	// not sure why this is now throwing error: Exception: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	public static void test_rest_api() { //get_irt_output_jsonarray(String param_value, String condition) {
		String api_key="X-Token";
		String api_key_value="C3AB8FF13720E8AD9047DD39466B3C8974E592C2FA383D4A3960714CAEF0C4F2";
		String api_host_name = "https://randomuser.me/api/";
        String CONNECT_API_URL =  api_host_name;
        String CONNECT_API_KEY = api_key_value;
        JSONArray _array = null;

        try {

            HttpClient client = HttpClientBuilder.create().build();
            System.out.println("1001");
            HttpUriRequest httpUriRequest = new HttpGet(api_host_name);  //CONNECT_API_URL);
            System.out.println("1002");
            HttpResponse response = client.execute(httpUriRequest);
            System.out.println("1003");
            String result = EntityUtils.toString(response.getEntity());
            System.out.println("1004");
            JSONObject myObject = new JSONObject(result);
            System.out.println("1005");
            _array = new JSONArray(myObject.getJSONArray("results"));
            System.out.println("1006");
            for(int i=0; i < _array.length(); i++)
 		    {
 		    	JSONObject object = _array.getJSONObject(i);
 		    	System.out.println(object.toString());
 		    	System.out.println(object.getString("gender"));
 		    	System.out.println(object.getJSONObject("name").getString("first") );
 		    	System.out.println(object.getJSONObject("name").getString("last") );
 		    	System.out.println(object.getJSONObject("dob").getString("date") );
 		    	System.out.println(object.getJSONObject("dob").getInt("age") );


 		    }




        }
        catch (Exception e) {
            System.out.println(" Exception: "+ e.getMessage());
        }





	}



	public static void test_irt_api() {

		String postJsonData=null;
	    String param_value="2:999";  // siteid 2
        //String condition ="get-uncoded-terms";
	    String condition="get-30-rows";

	    JSONArray _array = get_irt_output_jsonarray(param_value, condition);

	    System.out.println(" size of _array ::: " + _array.length() );

	    try
	    {

		    for(int i=0; i < _array.length(); i++)
		    {
		    	JSONObject object = _array.getJSONObject(i);
			    postJsonData = object.toString();
		        System.out.println(i + " ::: " + object.getInt("formid") + " ::: " + object.getString("subjectnumber") + " ::: " + object.getString("formvalue1") + " ::: " + object.getString("formdate") );
		        Thread.sleep(100);
		    }
	    } catch (Exception e)
	    {}

	    System.out.println("test_rest_api done...");


	}






	public static JSONArray  get_irt_output_jsonarray(String param_value, String condition) {
		String api_key="X-Token";
		String api_key_value="C3AB8FF13720E8AD9047DD39466B3C8974E592C2FA383D4A3960714CAEF0C4F2";
		param_value=param_value + ":" + condition;
		String api_host_name = "http://localhost:8080/SpringMVCSecurityJavaConfig";

        String CONNECT_API_URL =  api_host_name + "/api/irt_array/" + param_value;

        System.out.println(" inside get_irt_output_jsonarray() ::: " + CONNECT_API_URL);

        String CONNECT_API_KEY = api_key_value;
        JSONArray _array = null;

        try
        {
	        URL url = new URL(CONNECT_API_URL);
	        URLConnection urlc = url.openConnection();

	        urlc.setRequestProperty("X-Token", CONNECT_API_KEY);


	        BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
	        String _output = null;
	        String _data = null;

	        while ((_output=br.readLine())!=null) {
	            //System.out.println(_output);
	            _data = _output;
	        }
	        br.close();

	        _array = new JSONArray(_data);



	    }
		catch(Exception ex)
		{
			System.out.println(" !!!!!!!! connection issue ...");
		    System.out.println(ex.getMessage());
		    System.exit(0);
		}

        return _array;
	}







 
 




}
