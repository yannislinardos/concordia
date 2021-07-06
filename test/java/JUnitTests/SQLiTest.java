package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import beans.LoginBean;
import util.LoginDao;
/**
 * Here we will try to see if we can "hack" the login process by doing an SQL Injection
 * @author Yannis
 *
 */
public class SQLiTest {
	
	private static LoginBean bean = new LoginBean();
	
	/**
	 * We use the usual SQL injection input
	 * Of course it will fail to login because, first of all, our queries do not allow it 
	 * (we use different query to get the email, to get the hashed password, to validate etc)
	 * and secondly,  we use prepared statements 
	 */
	@Test
	public void testSQLi(){
		bean.setEmail("' OR '0'='0");
		bean.setPass("' OR '0'='0");
		
		assertFalse(LoginDao.validate(bean));
	}

}
