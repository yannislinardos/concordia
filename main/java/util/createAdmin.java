package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is to be used only on the back end in order to create admin accounts
 * @author Yannis
 *
 */
public class createAdmin {


	public static void main(String[] args) {
		PreparedStatement ps = null;
		ConnectionProvider provider = new ConnectionProvider();
	 	Connection con=provider.getCon();
	 	
	 	String name = "admin";
	 	String password = "Concordia";
	 		 	
	 	String query = "INSERT INTO admin (email, password) "
	 			+ "VALUES (?, ?);";
	 	
  			try {
				ps = con.prepareStatement(query);
				
				ps.setString(1, name); 
				ps.setString(2, password);
				
				ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
  			
  		 	System.out.println("Admin with name: " + name + " and password: " + password + " has been created!");

  			
	}
	
}
