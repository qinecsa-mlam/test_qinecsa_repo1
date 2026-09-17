package java_console_app1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;


public class GetDbConnection {


		public GetDbConnection() {}
		
		
		
		// for irt_database
		public Connection getConnection() {
 
	    	GetPropertiesValues GPV = new GetPropertiesValues();
	    	// 7/21 need to do this now
	    	GPV.getPropValues();
	    	
	    	String dbConnectStr =  GPV.getDbConnURL();
	    	String dbUser = GPV.getDbUser();
	    	// 4/17/2024 getDbPassword() is already decrypted        
	        String dbPw = GPV.getDbPassword();
	        
	    	String db_connect_str = dbConnectStr + "?user=" + dbUser + "&password=" + dbPw;	 
	    	 
	    	Connection Conn=null;
			
	        try {				
				//Class.forName("org.postgresql.Driver").newInstance();
	        	Class.forName("org.postgresql.Driver");
	            Conn = DriverManager.getConnection(db_connect_str);   
	          }
	        catch (SQLException e) {
	        System.out.println("conn error: " + e.toString() );
	        //} catch (InstantiationException e) {
			//e.printStackTrace();
	        //} catch (IllegalAccessException e) {
			//e.printStackTrace();
	        } catch (Exception e) {
			e.printStackTrace();
	        }		
	        
	        return Conn;

	        
		}

		

		// for broker db
		public Connection getBrokerConnection() {

	    	GetPropertiesValues GPV = new GetPropertiesValues();
	    	// 7/21 need to do this now
	    	GPV.getPropValues();
	    	
	    	String dbConnectStr =  GPV.getDbConnURLBroker();// instead of .getDbConnURL();
	    	String dbUser = GPV.getDbUser();
	    	String dbPw = GPV.getDbPassword();
	    	String db_connect_str = dbConnectStr + "?user=" + dbUser + "&password=" + dbPw;	 
	    	
	    	
	    	Connection Conn=null;
			
	        try {
				//Class.forName("org.postgresql.Driver").newInstance();
	        	Class.forName("org.postgresql.Driver");
	            Conn = DriverManager.getConnection(db_connect_str);   
	          }
	        catch (SQLException e) {
	        System.out.println("conn error: " + e.toString() );
	        //} catch (InstantiationException e) {
			//e.printStackTrace();
	        //} catch (IllegalAccessException e) {
			//e.printStackTrace();
	        //} catch (ClassNotFoundException e) {
	        } catch (Exception e) {
			e.printStackTrace();
	        }		
	        
	        return Conn;

	        
		}

		

		
		// for coding  db
		public Connection getCodingConnection() {

	    	GetPropertiesValues GPV = new GetPropertiesValues();
	    	// 7/21 need to do this now
	    	GPV.getPropValues();
	    	
	    	String dbConnectStr =  GPV.getDbConnURLCoding();  // instead of .getDbConnURLBroker(); 
	    	String dbUser = GPV.getDbUser();
	    	String dbPw = GPV.getDbPassword();
	    	String db_connect_str = dbConnectStr + "?user=" + dbUser + "&password=" + dbPw;	 
	    	
	    	
	    	Connection Conn=null;
			
	        try {				
				//Class.forName("org.postgresql.Driver").newInstance();
	        	Class.forName("org.postgresql.Driver");
	            Conn = DriverManager.getConnection(db_connect_str);   
	          }
	        catch (SQLException e) {
	        System.out.println("conn error: " + e.toString() );
	        //} catch (InstantiationException e) {
			//e.printStackTrace();
	        //} catch (IllegalAccessException e) {
			//e.printStackTrace();
	        } catch (Exception e) {
			e.printStackTrace();
	        }		
	        
	        return Conn;

	        
		}

		
		
		
}
