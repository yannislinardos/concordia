package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Submit {

	
	String name;
	String surname;
	String email;
	String birthday;
	
	public static void submitMe(String name,String surname,String email,String hash, 
			String secQuestion, String secAnswer){
		Connection con = null;
		ConnectionProvider provider = new ConnectionProvider();

		try{
			
			con = provider.getCon();
			
			//myCon.setAutoCommit(false);
			String sEmailNameSurname  = "INSERT INTO person (email,name,surname) VALUES (?,?,?)";
			String sEmailPassword = "INSERT INTO member (email,hashpass,secquestion,secanswer) VALUES (?,?,?,?)";
		

			PreparedStatement submitEmailNameSurname = con.prepareStatement(sEmailNameSurname);
			PreparedStatement submitEmailPassword= con.prepareStatement(sEmailPassword);
			
			//convert hashpassword to string 
			String pass = hash;
			
			submitEmailNameSurname.setString(3,Queries.sanitize(name));
			submitEmailNameSurname.setString(2,Queries.sanitize(surname));
			submitEmailNameSurname.setString(1,Queries.sanitize(email));
			submitEmailPassword.setString(1,Queries.sanitize(email));
			submitEmailPassword.setString(2,pass);
			submitEmailPassword.setString(3,secQuestion);
			submitEmailPassword.setString(4,secAnswer);


			submitEmailNameSurname.executeUpdate();
			submitEmailPassword.executeUpdate();
			
			
			
			}catch(Exception e){ 
				e.printStackTrace();
			} finally {
			    if (con != null) {
			        try {
			            con.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
		
	}
	
}
