package util;

import java.security.SecureRandom;
import javax.servlet.http.Cookie;
import javax.xml.bind.DatatypeConverter;
//import org.apache.tomcat.util.codec.binary.Base64;
import beans.LoginBean;

public class LoginDao {

    public static boolean validate(LoginBean bean) {

        String email = bean.getEmail();
        String givenPassword = bean.getPass();

        if (!Queries.userExists(email)) {
            return false;
        } else {
            String correctHashPassword = Queries.getPassword(email);

            if (correctHashPassword == null) {
                return false;
            }

            return Password.checkPassword(givenPassword, correctHashPassword);
        }



//    boolean status=false;
//
//    Connection con = ConnectionProvider.getCon();
//
//    PreparedStatement ps;
//	try {
//
//		ps = con.prepareStatement(
//		    "select * from member where email=? and hashpass=?");
//
//		String pass = Password.hashCode(bean.getPass());
//
//	    ps.setString(1,bean.getEmail());
//	    ps.setString(2, pass);
//
//	    ResultSet rs=ps.executeQuery();
//	    status=rs.next();
//
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//
//    return status;
//

    }


    public static Cookie tokenBuilder(String email) {

        String fromDatabase = Queries.getPassword(email);
        String[] split = fromDatabase.split("&&&&&");
        String passwordSalt = split[1];
        byte[] cookieSaltByte = new byte[Password.saltSize];
        new SecureRandom().nextBytes(cookieSaltByte);
        String cookieSalt = DatatypeConverter.printBase64Binary(cookieSaltByte);
        String token = Password.hashString(passwordSalt + "&&&&&" + cookieSalt) + "&&&&&" + email + "&&&&&" + cookieSalt;
        Cookie cookie = new Cookie("token", token);
        return cookie;
    }

    public static String getEmailCookie(String cookieData) {
        String[] splitCookie = cookieData.split("&&&&&");
        String passAndCookieSalt = splitCookie[0];
        String email = splitCookie[1];
        String cookieSalt = splitCookie[2];

        String databaseData = Queries.getPassword(email);
        String[] splitDatabase = databaseData.split("&&&&&");
        String passwordSalt = splitDatabase[1];

        if (Password.hashString(passwordSalt + "&&&&&" + cookieSalt).equals(passAndCookieSalt)) {
            return email;
        } else {
            return null;
        }
    }

    public static String getToken(Cookie[] cookies) {
        String cookieData = null;
        String email = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    //cookieData = new String(DatatypeConverter.parseBase64Binary(cookie.getValue()));
                    cookieData = cookie.getValue();
                }

            }
            if (cookieData != null) {
                email = getEmailCookie(cookieData);
            }
        }
        return email;
    }
}