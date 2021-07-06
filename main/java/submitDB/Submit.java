package submitDB;

import java.sql.Connection;
import java.sql.PreparedStatement;



import postgreConn.DataGripConnection;

public class Submit {

	
	String name;
	String surname;
	String email;
	String birthday;
	
	public void submitDetails(String name,String surname,String email,String pass){
		
		
		try{
			
			Connection myCon = DataGripConnection.getCon();
			
			//myCon.setAutoCommit(false);
			System.out.println(myCon);
			//String for preparing the insert queries PERSON TABLE
			String sEmailNameSurname  = "INSERT INTO person (email,name,surname) VALUES (?,?,?)";
			//Strings for TABLE MEMBER
			String sEmailPassword = "INSERT INTO member (email,hashpass) VALUES (?,?)";
		

			
			//preparedStaments
			PreparedStatement submitEmailNameSurname = myCon.prepareStatement(sEmailNameSurname);
			PreparedStatement submitEmailPassword= myCon.prepareStatement(sEmailPassword);
			
			//convert hashpassword to string 
			String password = pass;
			
			//setting the values of the queries
			submitEmailNameSurname.setString(3,name);
			submitEmailNameSurname.setString(2,surname);
			submitEmailNameSurname.setString(1,email);
			submitEmailPassword.setString(1,email);
			submitEmailPassword.setString(2,password);

			submitEmailNameSurname.executeUpdate();
			submitEmailPassword.executeUpdate();
			
			
			
			}catch(Exception e){ 
				e.printStackTrace();
			}
		
	}
	
	
	
}
