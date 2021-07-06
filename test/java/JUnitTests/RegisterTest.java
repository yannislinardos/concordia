package JUnitTests;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.LoginBean;
import util.ConnectionProvider;
import util.LoginDao;
import util.Password;
import util.Submit;

public class RegisterTest {
	
	  static SecureRandom random = new SecureRandom();
	  
	  static String email = (new BigInteger(130, random).toString(32)) + "@gmail.com";
	  
	  static String password = new BigInteger(130, random).toString(32);
	
	/**
	 * we will use the Submit class to create a new user
	 */
	@BeforeClass
	public static void before() {
		Submit.submitMe("test", "test", email, Password.hashCode(password), "What is your favorite FC?", "AJAX");
	}

	/**
	 * It checks if the user is created
	 * (we have already tested the LoginDao class)
	 */
	@Test
	public void login(){
		LoginBean bean = new LoginBean();
		bean.setEmail(email);
		bean.setPass(password);
		
		assertTrue(LoginDao.validate(bean));

	}
	
	/**
	 * It deletes the user
	 */
	@AfterClass
	public static void after() {
		
		ConnectionProvider provider = new ConnectionProvider();
		
		PreparedStatement ps;
	    Connection con=provider.getCon();

	    String query = "DELETE FROM member WHERE email=?;";
	    
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, email); 
			
			ps.executeUpdate();

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
}
