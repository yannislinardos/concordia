package util;

import java.sql.*;

import beans.Provider;  
      
public class ConnectionProvider { 
	
    private Connection con=null;  
    
    /*
     * Isolation level REAS_COMMITED so that we can
     * prevent a user to read about an event while an admin
     * writes it. Our application will not have many
     * "WRITES" in the database, so it is not heavy in 
     * isolation level requirements
     * 
     */
    public Connection getCon(){  
    	
    	if (con == null) {
        	try {  
        		Class.forName(Provider.DRIVER);  
        		con=DriverManager.getConnection(Provider.CONNECTION_URL,Provider.USERNAME,Provider.PASSWORD); 
        		
    			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    			con.setAutoCommit(true);

    			
        	} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}  
    	}
    	
    	
        return con;  
    }  
    
    
}  