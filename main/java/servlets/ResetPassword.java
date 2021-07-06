package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import util.Password;
import util.Queries;

public class ResetPassword extends HttpServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 public static boolean checkSecurityAnswer(String givenAnswer, String correctAnswer) {
	    	
	    	String[] split = correctAnswer.split("&&&&&");
	        byte[] correctHash = DatatypeConverter.parseBase64Binary(split[0]);
	        byte[] salt = Password.encryptSalt(DatatypeConverter.parseBase64Binary(split[1]), false);
	        byte[] givenHash = Password.hashFunction(givenAnswer, salt);
	        
	        for (int i = 0; i < givenHash.length && i < correctHash.length; i++) {
	            if (givenHash[i] != correctHash[i]) {
	                return false;
	            }
	        }
	        return true;

	    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String newPass = request.getParameter("newPass");
		String reNewPass = request.getParameter("reNewPass");
		String email = request.getParameter("email");
		String question =(String) request.getAttribute("secQuestion");
		request.setAttribute("secQuestion", question);

		
			if (!newPass.equals(reNewPass)) {
				
				request.setAttribute("notTheSame", true);
				
				request.getRequestDispatcher("PasswordReset.jsp").forward(request, response);

			} else {
			
			String givenAnswer = request.getParameter("answer");
			
			String correctAnswer = Queries.getSecurityAnswer(email);
			
			if (!checkSecurityAnswer(givenAnswer,correctAnswer)) {
				request.setAttribute("notCorrect", true);
				
				request.getRequestDispatcher("PasswordReset.jsp").forward(request, response);
			} else {
				
				request.setAttribute("passwordReset", true);
				
				Queries.changePassword(newPass, email);
				
				request.getRequestDispatcher("PasswordReset.jsp").forward(request, response);
			}
			
			
		}	
			
		
	}

}
