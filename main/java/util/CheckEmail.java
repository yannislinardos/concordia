package util;

import java.sql.*;

public class CheckEmail {
		
	public static boolean checkMe(String emailreceived){
		
		boolean alithia = false;
		ConnectionProvider provider = new ConnectionProvider();
		Connection con = null;
		
			try{
				con = provider.getCon();
				//myCon.setAutoCommit(false);
				String query = "SELECT email FROM person WHERE email =?";
				
				PreparedStatement myStat = con.prepareStatement(query);
				
				myStat.setString(1,emailreceived);
				
				ResultSet myRes = myStat.executeQuery();
				
				
				
				if (myRes.next()){
					String result = myRes.getString("email");
					if (result.equals(emailreceived)){
						alithia = true;
					}
				}
				
				//check for email if is in database
				
				}catch(Exception e){ 
					e.printStackTrace();
				} finally {
				    if (con != null) {
				        try {
				            con.close();
				        } catch (SQLException e) { /* ignored */}
				    }
				}
			return alithia;
		}
	}
	


