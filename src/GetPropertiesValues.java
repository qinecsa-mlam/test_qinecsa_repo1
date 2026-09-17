package java_console_app1;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class GetPropertiesValues {

 	InputStream inputStream;

 	static String globalParamJDBCFilePath = "";
  	
 		
	// 6/29
    public String getAPIUrl() {
    	String api_url = "";   	
    	Map<String, String> propMap = getPropValues();		
		   for (String key : propMap.keySet())
		   {   	 
		   		if (propMap.containsKey("api_url"))
		   			api_url=propMap.get("api_url");   
		   }		   
    	return api_url;
    }
	

    public String getAPIMethod() {
    	String api_method = "";   	
    	Map<String, String> propMap = getPropValues();		
		   for (String key : propMap.keySet())
		   {   	 
		   		if (propMap.containsKey("api_method"))
		   			api_method=propMap.get("api_method");   
		   }		   
    	return api_method;
    }
 	

    public String getAPIPostData() {
    	String api_post_data = "";   	
    	Map<String, String> propMap = getPropValues();		
		   for (String key : propMap.keySet())
		   {   	 
		   		if (propMap.containsKey("api_post_data"))
		   			api_post_data=propMap.get("api_post_data");   
		   }		   
    	return api_post_data;
    }
 	
 	
    
    
 	// Rest-api
    public String getAPIHostName() {
    	String api_key = "";   	
    	Map<String, String> propMap = getPropValues();		
		   for (String key : propMap.keySet())
		   {   	 
		   		if (propMap.containsKey("api_host_name"))
		   			api_key=propMap.get("api_host_name");   
		   }		   
    	return api_key;
    }

 	// Rest-api
    public String getAPIKey() {
    	String api_key = "";   	
    	Map<String, String> propMap = getPropValues();		
		   for (String key : propMap.keySet())
		   {   	 
		   		if (propMap.containsKey("api_key"))
		   			api_key=propMap.get("api_key");   
		   }		   
    	return api_key;
    }
    
    
 	// Rest-api
    public String getAPIKeyValue() {
    	String api_key_value = "";   	
    	Map<String, String> propMap = getPropValues();		
		   for (String key : propMap.keySet())
		   {   	 
		   		if (propMap.containsKey("api_key_value"))
		   			api_key_value=propMap.get("api_key_value");   
		   }		   
    	return api_key_value;
    }

    
    

 	
 	
    // IRT
    public String getDbConnURL() {
    	String conn_string = "";
    	Map<String, String> propMap = getPropValues();
		   for (String key : propMap.keySet())
		   {
		   		if (propMap.containsKey("conn_string"))
		   			conn_string=propMap.get("conn_string");
		   }
    	return conn_string;
    }


    // Synch/Broker
    public String getDbConnURLBroker() {
    	String conn_string_broker = "";
    	Map<String, String> propMap = getPropValues();
		   for (String key : propMap.keySet())
		   {
		   		if (propMap.containsKey("conn_string_broker"))
		   			conn_string_broker=propMap.get("conn_string_broker");
		   }
    	return conn_string_broker;
    }

    // Coding/Safety
    public String getDbConnURLCoding() {
    	String conn_string_coding = "";
    	Map<String, String> propMap = getPropValues();
		   for (String key : propMap.keySet())
		   {
		   		if (propMap.containsKey("conn_string_coding"))
		   			conn_string_coding=propMap.get("conn_string_coding");
		   }
    	return conn_string_coding;
    }





    public String getDbUser() {

    	String postgres_user = "";

    	Map<String, String> propMap = getPropValues();

		   for (String key : propMap.keySet())
		   {

		   		if (propMap.containsKey("postgres_user"))
		   			postgres_user=propMap.get("postgres_user");
		   }

    	return postgres_user;
    }



    public String getDbPassword() {

    	String postgres_password = "";
    	String postgres_password_decrypted = "";
    	

    	Map<String, String> propMap = getPropValues();

		   for (String key : propMap.keySet())
		   {
		   		if (propMap.containsKey("postgres_password"))
		   			postgres_password_decrypted=propMap.get("postgres_password");  // already decrypted
		   } 
 
    	postgres_password  = postgres_password_decrypted;  
    	return postgres_password;
    }




    public String getDbSelect() {

    	String sql_select = "";

    	Map<String, String> propMap = getPropValues();

		   for (String key : propMap.keySet())
		   {
		   		if (propMap.containsKey("sql_select"))
		   			sql_select=propMap.get("sql_select");
		   }

    	return sql_select;
    }

    
    
    
    
    

	public Map<String, String> getPropValues()
	{


		// System.out.println(" GetPropertiesValues.getPropValues() ... globalParamJDBCFilePath = " + globalParamJDBCFilePath);


		Map<String, String> propMap = new HashMap<String, String>();

		try {

			String propFileName = "c:\\Temp\\jdbc.properties";
			//String propFileName = "c:\\temp\\jdbc.safety.properties";
			
			// System.out.println(" Reading prop file " +  propFileName);
			//System.out.println(" Reading prop file " +  propFileName);
			//System.out.println(" Reading prop file " +  propFileName);
			//System.out.println(" Reading prop file " +  propFileName);
			
			
			 


			Properties prop = new Properties(); 

			File configFile = new File(propFileName);
			inputStream = new FileInputStream(configFile);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 

			String sql_select = prop.getProperty("sql_select");

			// IRT
			String conn_string = prop.getProperty("conn_string");

			// Synch/broker
			String conn_string_broker = prop.getProperty("conn_string_broker");

			// Coding/Safety
			String conn_string_coding = prop.getProperty("conn_string_coding");


			String postgres_user = prop.getProperty("postgres_user"); 
			
			// 4/17/2024
			// now need to decrypt the pw from jdbc.properties
	    	String secretKey = prop.getProperty("aes_secret_key");    
	        String salt = prop.getProperty("aes_salt");        
	        //String postgres_password = prop.getProperty("postgres_password");
	        String postgres_password_encrypted = prop.getProperty("postgres_password");
	    	//String postgres_password  = AES256.decrypt( postgres_password_encrypted, secretKey, salt);
			String postgres_password  = prop.getProperty("postgres_password");			
			

			String wait_time = prop.getProperty("wait_time");

			// 2 factor authentication
			String nexmo_api_key=prop.getProperty("nexmo.api.key");
			String nexmo_api_secret=prop.getProperty("nexmo.api.secret");
			String two_factor_authentication=prop.getProperty("two_factor_authentication");

			// email smtp with SendGrid
			String sendgrid_api_key=prop.getProperty("sendgrid_api_key");
			String sendgrid_password=prop.getProperty("sendgrid_password");
			String email_notification_flag=prop.getProperty("email_notification_flag");

			// 7/14/2023 putting load dictionary back here:

			String cygwin_path = prop.getProperty("cygwin_path");
			String psql_path = prop.getProperty("psql_path");
			String meddra_load_path = prop.getProperty("meddra_load_path");
			String meddra_un_sed_files = prop.getProperty("meddra_un_sed_files");
			String meddra_load_log_path =  prop.getProperty("meddra_load_log_path");
			String meddra_dict_raw_file = prop.getProperty("meddra_dict_raw_file");
			//String meddra_ver = prop.getProperty("meddra_ver");

			String who_load_path =  prop.getProperty("who_load_path");
			String who_load_log_path  = prop.getProperty("who_load_log_path");
			String who_un_sed_files = prop.getProperty("who_un_sed_files");
			String who_dict_raw_file = prop.getProperty("who_dict_raw_file");


			
			// rest api
			String api_key = prop.getProperty("api_key");
			String api_key_value = prop.getProperty("api_key_value");
			String api_host_name = prop.getProperty("api_host_name");
			

			String subj_pagination_limit =  prop.getProperty("subj_pagination_limit");
			
			String initial_async_startup_ws = prop.getProperty("initial_async_startup_ws");
			
			// 4/17/2024 AES encryption
			String aes_secret_key = prop.getProperty("aes_secret_key");
			String aes_salt       = prop.getProperty("aes_salt");

			
			// 6/29
			String api_url = prop.getProperty("api_url");
			String api_method=prop.getProperty("api_method");
			String api_post_data=prop.getProperty("api_post_data");
			
			
			
			// IRTAdapter, BrokerAdapter, CodingAdapter
			String api_host_name_irt_adapter = prop.getProperty("api_host_name_irt_adapter");
			String api_host_name_broker_adapter = prop.getProperty("api_host_name_broker_adapter");
			String api_host_name_coding_adapter = prop.getProperty("api_host_name_coding_adapter");
			
			// 7/16/2025 for Apache Kafka streaming
			String kafka_server_and_port= prop.getProperty("kafka_server_and_port");
			String kafka_topic_name=prop.getProperty("kafka_topic_name");
			
			
			String oracle_host=prop.getProperty("oracle_host");
			String oracle_user=prop.getProperty("oracle_user");
			String oracle_password=prop.getProperty("oracle_password");
			
			
			propMap.put("sql_select", sql_select);

			// IRT
			propMap.put("conn_string", conn_string);

			// Synch/broker
			propMap.put("conn_string_broker", conn_string_broker);

			// Coding/Safety
			propMap.put("conn_string_coding", conn_string_coding);



			propMap.put("postgres_user", postgres_user);
			propMap.put("postgres_password", postgres_password);
			propMap.put("wait_time",  wait_time);


			propMap.put("nexmo_api_key", nexmo_api_key);
			propMap.put("nexmo_api_secret", nexmo_api_secret);
			propMap.put("two_factor_authentication", two_factor_authentication);


			propMap.put("sendgrid_api_key", sendgrid_api_key);
			propMap.put("sendgrid_password", sendgrid_password);
			propMap.put("email_notification_flag", email_notification_flag);





			// 7/14/2023 put load dictionary back:
			propMap.put("cygwin_path", cygwin_path);
			propMap.put("psql_path", psql_path);

			propMap.put("meddra_load_path", meddra_load_path);
			propMap.put("meddra_un_sed_files", meddra_un_sed_files);
			propMap.put("meddra_load_log_path", meddra_load_log_path);
			propMap.put("meddra_dict_raw_file", meddra_dict_raw_file);

			//propMap.put("meddra_ver", meddra_ver);

			propMap.put("who_load_path", who_load_path);
			propMap.put("who_load_log_path", who_load_log_path);
			propMap.put("who_un_sed_files", who_un_sed_files);
			propMap.put("who_dict_raw_file", who_dict_raw_file);
			
			// rest api
			propMap.put("api_key",  api_key);
			propMap.put("api_key_value",  api_key_value);
			propMap.put("api_host_name",  api_host_name);
			

			propMap.put("subj_pagination_limit", subj_pagination_limit );
			
			
			// 3/22/2024
			propMap.put("initial_async_startup_ws", initial_async_startup_ws);
			

			// 4/17/2024
			propMap.put("aes_secret_key",  aes_secret_key);
			propMap.put("aes_salt",        aes_salt);
					
			
			// 6/29
			propMap.put("api_url", api_url);
			propMap.put("api_method", api_method);
			propMap.put("api_post_data", api_post_data);
						
			
			
			propMap.put("api_host_name_irt_adapter",    api_host_name_irt_adapter );
			propMap.put("api_host_name_broker_adapter", api_host_name_broker_adapter );
			propMap.put("api_host_name_coding_adapter", api_host_name_coding_adapter );
			
			
			
			// 7/16/2025 Apache Kafka streaming
			propMap.put("kafka_server_and_port",  kafka_server_and_port);
			propMap.put("kafka_topic_name",  kafka_topic_name);
			
			
			propMap.put("oracle_host",  oracle_host);
			propMap.put("oracle_user",  oracle_user);
			propMap.put("oracle_password", oracle_password);
			
						
			
			inputStream.close();

			//System.out.println(" meddra_dict_raw_file from jdbc.properties: " +  meddra_dict_raw_file);
			//System.out.println(" meddra_load_log_path    from jdbc.properties: " +  meddra_load_log_path);
		    inputStream.close();

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

		return propMap;  // result;
	}




	//  12/29/2022 set the path of jdbc.safety.properties via Context.xml...
	public void setGlobalParam(String path_name)
	{
		//System.out.println("Inside setGlobalParam() ....");
		globalParamJDBCFilePath = path_name;

		// System.out.println(" path_name   = " + path_name);
		System.out.println(" GetPropertiesValues.setGlobalParam()  ... globalParamJDBCPath = " + globalParamJDBCFilePath.replace("\\\\", "\\"));

	}




}


