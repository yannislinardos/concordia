package JUnitTests;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import util.ConnectionProvider;

/**
 * Tests the connection between the application and the database
 * @author Yannis
 *
 */

public class DBConnectionTest {
	
	private static Connection con;
	
	@BeforeClass
	public static void before() {
		ConnectionProvider provider = new ConnectionProvider();
		con = provider.getCon();

	}

	

	@Test
	public void ReadTest() {
		boolean isReadWrite = false;
		try {
			isReadWrite = !con.isReadOnly();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(isReadWrite);
	}

	@Test
	public void CloseTest() {
		try {
			con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean isClosed = false;
		try {
			isClosed = con.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(isClosed);
	}
	
	@AfterClass
	public static void after(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
