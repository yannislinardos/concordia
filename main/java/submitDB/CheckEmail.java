package submitDB;

import java.sql.*;

import postgreConn.DataGripConnection;

public class CheckEmail {
	
	String emailreceived ;
	
	public boolean checkMe(String emailreceived){
		
		boolean alithia = false;
		
			try{
				
				Connection myCon = DataGripConnection.getCon();
				
				//myCon.setAutoCommit(false);
				System.out.println(myCon);
				String query = "SELECT email FROM person WHERE email =?";
				
				
				
				PreparedStatement myStat = myCon.prepareStatement(query);
				
				System.out.println("STATE OK");
				myStat.setString(1,emailreceived);
				
				ResultSet myRes = myStat.executeQuery();
				
				
				
				if (myRes.next()){
					String result = myRes.getString("email");
					if (result.equals(emailreceived)){
						alithia = true;
					}
				}
				
				
				System.out.println("PRINTG ALITHIA =" +alithia);
				//check for email if is in database
				
				
				
				}catch(Exception e){ 
					e.printStackTrace();
				}
			return alithia;
		}
	}
	


