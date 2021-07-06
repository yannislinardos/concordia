package servlets;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import beans.LoginBean;
import util.Password;
import util.Queries;

public class AdminLogin extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public static String getToken(Cookie[] cookies) {
    	String cookieData = null;
    	String name = null;
    	if (cookies != null) {
    	    for (Cookie cookie : cookies) {
    	    	if (cookie.getName().equals("adminToken")) {
    	    		cookieData = cookie.getValue();
    	    	}
    	    	
    	    }
    	    if (cookieData != null) {
    	    	name = getEmailCookie(cookieData);
    	    }
    	}
    	return name;
    }
    
    public static String getEmailCookie(String cookieData) {

        String[] splitCookie = cookieData.split("&&&&&");
        String passAndCookieSalt = splitCookie[0];
        String email = splitCookie[1];
        String cookieSalt = splitCookie[2];
        
        String databaseData = "";
        if(Queries.getAdminPassword(email) != null) {
        	databaseData = Queries.getAdminPassword(email);
        }
        String[] splitDatabase = databaseData.split("&&&&&");
        String passwordSalt = splitDatabase[1];
        
        if (Password.hashString(passwordSalt + "&&&&&" + cookieSalt).equals(passAndCookieSalt)) {
        	return email;
        } else {
        	return null;
        }
    }
	
    public static Cookie tokenAdminBuilder(String email) {
    	
    	String fromDatabase = Queries.getAdminPassword(email);
    	String[] split = fromDatabase.split("&&&&&");
    	String passwordSalt = split[1];
        byte[] cookieSaltByte = new byte[Password.saltSize];
        new SecureRandom().nextBytes(cookieSaltByte);
        String cookieSalt = DatatypeConverter.printBase64Binary(cookieSaltByte);
        String token = Password.hashString(passwordSalt + "&&&&&" + cookieSalt) + "&&&&&" + email + "&&&&&" + cookieSalt;
        Cookie cookie = new Cookie("adminToken", token);
        return cookie;
    }
    
	
	public static boolean validate(LoginBean bean) {
		
    	String email = bean.getEmail();
    	String givenPassword = bean.getPass();
    	
    	if (!Queries.adminExists(email)) {
    		return false;
    	} else {
    		String correctHashPassword = Queries.getAdminPassword(email);
    		
    		if (correctHashPassword == null) {
    			return false;
    		}
    		
    		return checkAdminPassword(givenPassword, correctHashPassword);
    	}
    
    }
	
    public static boolean checkAdminPassword(String givenPassword, String correctHashPassword) {
    	
    	String[] split = correctHashPassword.split("&&&&&");
        byte[] correctHash = DatatypeConverter.parseBase64Binary(split[0]);
        byte[] salt = Password.encryptSalt(DatatypeConverter.parseBase64Binary(split[1]), false);
        byte[] givenHash = Password.hashFunction(givenPassword, salt);
        
        for (int i = 0; i < givenHash.length && i < correctHash.length; i++) {
            if (givenHash[i] != correctHash[i]) {
                return false;
            }
        }
        return true;

    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("admin");
		String pass = request.getParameter("password");

		LoginBean bean = new LoginBean();
		bean.setEmail(email);
		bean.setPass(pass);

		boolean status=validate(bean);  
		if(status){  
			Cookie token = tokenAdminBuilder(email);
			token.setMaxAge(60*60);
			response.addCookie(token);
			response.sendRedirect("AdminPage.jsp");
			
		} else {  
			response.sendRedirect("AdminLogin.jsp");

		}  
	}


}
