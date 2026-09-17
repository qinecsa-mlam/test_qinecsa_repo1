package java_console_app1;

import java.sql.Connection; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
 

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;  
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

 
public class java_console_json {

	public static void main(String[] args) throws IOException {
    
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
 
		 
		
		//test_rest_api();
		//test_irt_api();
		
		
		
		//test_api_using_properties_file(); 
		
		/* for testing with input from console
		System.out.print("Enter api key name: ");
	    String _key = br.readLine();
	    System.out.println(_key);
	    
	    System.out.print("Enter api key value: ");
	    String _key_value = br.readLine();
	    System.out.println(_key_value);
	    
	    System.out.print("Enter api URL: ");
	    String _url = br.readLine();
	    System.out.println(_url);
	    
	    System.out.print("Enter method (GET or POST) must be all upper case: ");
	    String _method = br.readLine();
	    System.out.println(_method);
	    
	    System.out.print("Enter POST data: ");
	    String _post_param = br.readLine();
	    System.out.println(_post_param);
	    
	    //GET
	    //X-Token
	    //C3AB8FF13720E8AD9047DD39466B3C8974E592C2FA383D4A3960714CAEF0C4F2
	    //http://localhost:8080/SpringMVCSecurityJavaConfig/api/irt_array/2:999:get-30-rows
		

	    //POST
		// use this URL http://localhost:8080/SpringMVCSecurityJavaConfig/api/irt_rest_api_single
		// use this POST DATA:   
	    // {"formid":"3219"}
	    
	    
		

		test_api_using_input( _key, _key_value, _url, _method, _post_param);
		*/
		
		
		
	}

	 
	
	 
	
	public static void test_api_using_input(String _key, String _key_value, String _url, String _method, String _post_param) {
		
		System.out.println(" inside test_api_using_input() ...");
		
	    JSONArray _array = get_jsonarray_input(_key, _key_value, _url, _method, _post_param);  //, param_value, condition); 
	    
	    System.out.println(" size of _array ::: " + _array.length() ); 
	    
	    
	    System.out.println("test_api_using_input() done...");
	    
	    
	}
	


	 
	public static JSONArray  get_jsonarray_input(String _key, String _key_value, String _url, String _method, String POST_PARAMS) { 
		
		JSONArray _array = null;
        
        try 
        { 
        	System.out.println(" inside get_jsonarray_input() ...");
        	
	        URL url = new URL(_url);   
	        
	        // URLConnection urlc = url.openConnection();	
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        
	        //urlc.setRequestProperty(api_key,  api_key_value);
	        con.setRequestProperty(_key,  _key_value);
	        con.setRequestMethod(_method);
	        con.setRequestProperty("Content-Type", "application/json; charset=utf8");
	        
	        
	        if (_method.equals("POST"))
	        { 
	        	System.out.println("this is a POST...");
				con.setDoOutput(true);				
				byte[] outputBytes = POST_PARAMS.getBytes("UTF-8");
				OutputStream os = con.getOutputStream();
				os.write(outputBytes);
				os.close();
	        }
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())); 
	        
	        
	        
	        String _output = null;
	        String _data = null;	  
	       
	        while ((_output=br.readLine())!=null) {	        	
	            System.out.println(_output);
	            _data = _output;
	        }
	        br.close();	        
	        _array = new JSONArray(_data); 	      
	         
	         
	    }
		catch(Exception ex)
		{
			System.out.println(" problem: "); 
		    System.out.println(ex.getMessage());
		    System.exit(0);
		}	
	 
        return _array;
	}
	
	
	

	public static void test_rest_api() { //get_irt_output_jsonarray(String param_value, String condition) {    		
		String api_key="X-Token";
		String api_key_value="C3AB8FF13720E8AD9047DD39466B3C8974E592C2FA383D4A3960714CAEF0C4F2"; 
		String api_host_name = "https://randomuser.me/api/";   
        String CONNECT_API_URL =  api_host_name;             
        String CONNECT_API_KEY = api_key_value;  
        JSONArray _array = null;        
        
        try {
        	
            HttpClient client = HttpClientBuilder.create().build();
            HttpUriRequest httpUriRequest = new HttpGet(CONNECT_API_URL);             
            HttpResponse response = client.execute(httpUriRequest);   
            String result = EntityUtils.toString(response.getEntity());            
            JSONObject myObject = new JSONObject(result);             
            //System.out.println(myObject.toString());
            //{"results":
            //[{"nat":"UA","gender":"female","phone":"(099) M31-2291",
            //"dob":{"date":"1949-12-29T09:07:46.822Z","age":74},
            //"name":{"last":"Bogun","title":"Ms","first":"Gafiya"},
            //"registered":{"date":"2008-03-23T07:40:29.965Z","age":16},
            //"location":{"country":"Ukraine","city":"Toreck",
            //"street":{"number":2671,"name":"Naberezhno-Livoberezhna"},
            //"timezone":{"offset":"+4:30","description":"Kabul"},"postcode":22151,
            //"coordinates":{"latitude":"74.9804","longitude":"89.7318"},"state":"Rivnenska"},
            //"id":{"name":"","value":null},
            //"login":{"sha1":"0ec3d0a1fa3b4429251e084888b79143c2d7c501",
            //"password":"splash","salt":"QYMxw4KR",
            //"sha256":"b233b9ce98fa78c7427d607898a2f9cd195f33c41fb23c42c1826962f38a6c10",
            //"uuid":"31ea49eb-2bd9-455e-9bd2-58145f75234f",
            //"username":"blackfrog600","md5":"29e3f8c8894a50c87edbe24f2c27657e"},
            //"cell":"(097) A57-6005",
            //"email":"gafiya.bogun@example.com",
            //"picture":{"thumbnail":"https://randomuser.me/api/portraits/thumb/women/30.jpg",
            //"large":"https://randomuser.me/api/portraits/women/30.jpg",
            //"medium":"https://randomuser.me/api/portraits/med/women/30.jpg"}}],
            //"info":{"seed":"b9dd7a6ca9f62b2c","page":1,"results":1,"version":"1.4"}}

            _array = new JSONArray(myObject.getJSONArray("results"));
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
        String condition ="get-30-rows";
		 
	    JSONArray _array = get_irt_output_jsonarray(param_value, condition); 
	    
	    System.out.println(" size of _array ::: " + _array.length() );
	    
	   	    
	    try
	    {
		        
		    for(int i=0; i < _array.length(); i++)   
		    {  
		    	JSONObject object = _array.getJSONObject(i);  
		    	System.out.println(object.toString() ); //.getInt("formid"));
		    	System.out.println(object.getInt("formid"));
		    	
			    postJsonData = object.toString(); 
			    
		        System.out.println(i + " ::: " + object.getInt("formid") + " ::: " + object.getString("subjectnumber") + " ::: " + object.getString("formvalue1") + " ::: " + object.getString("formdate") );  
		        Thread.sleep(200);
		    }  
	    } catch (Exception e)
	    {}
	    
	    
	    
	    System.out.println("test_rest_api done...");
	    
	    
	}
	
	
 
	

	 
	public static JSONArray  get_irt_output_jsonarray(String param_value, String condition) {    		
		String api_key="X-Token";
		String api_key_value="C3AB8FF13720E8AD9047DD39466B3C8974E592C2FA383D4A3960714CAEF0C4F2";
		param_value=param_value + ":" + condition;
		String api_host_name = "http://localhost:8080/IRTAdapter";
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
			System.out.println(" problem..."); 
		    System.out.println(ex.getMessage());
		    System.exit(0);
		}	
	 
        return _array;
	}
	
	
	
	
	
	 
	
	
	
		 


	
}
