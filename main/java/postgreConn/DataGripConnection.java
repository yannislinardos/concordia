package postgreConn;

import java.sql.*;

public class DataGripConnection {
	
	public static Connection con = null;
	
	
	
	public static Connection getCon(){
		if(con==null){
				try{
					System.out.println("before init class");
					Class.forName(Provider.DRIVER);
					con = DriverManager.getConnection(Provider.CONNECTION_URL, Provider.USERNAME, Provider.PASSWORD);
				} catch(Exception e){
					System.out.println("ConnnectionError!!!");
				}
			
		}
		return con;
		
		
	}
	
}
