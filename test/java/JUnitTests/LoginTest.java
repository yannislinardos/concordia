//package JUnitTests;
//import static org.junit.Assert.*;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import beans.LoginBean;
//import util.CheckEmail;
//import util.LoginDao;
//
///**
// * it tests the login functionality
// * @author Yannis
// *
// */
//public class LoginTest {
//
//	private static LoginBean correctBean = new LoginBean();
//	private static LoginBean wrongBean = new LoginBean();
//
//
//	/**
//	 * we know that in the database there is a user with email:janlin@gmail.com
//	 * and password:janlin
//	 */
//	@BeforeClass
//	public static void preparation() {
//		correctBean.setEmail("janlin@gmail.com");
//		correctBean.setPass("janlin");
//
//		wrongBean.setEmail("janlin@gmail.com");
//		wrongBean.setPass("NOTjanlin");
//	}
//
//	/**
//	 * It checks if the email is in the database
//	 */
//
//	public void checkEmailExists() {
//		assertTrue(CheckEmail.checkMe("janlin@gmail.com"));
//	}
//
//	/**
//	 * It checks if the email/password combination is correct
//	 */
//	@Test
//	public void validateCorrectTest() {
//		assertTrue(LoginDao.validate(correctBean));
//	}
//
//	/**
//	 * checks whether an email does NOT exist in the DB
//	 */
//	public void checkEmailNotExists() {
//		assertFalse(CheckEmail.checkMe("NOTjanlin@gmail.com"));
//	}
//
//	/**
//	 * verifies that someone cannot login with a wrong password
//	 */
//	@Test
//	public void validateWrongTest() {
//		assertFalse(LoginDao.validate(wrongBean));
//	}
//}
