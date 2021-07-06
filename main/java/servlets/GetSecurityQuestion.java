package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Queries;

public class GetSecurityQuestion extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String email = request.getParameter("email");
		String secQuestion = Queries.getSecurityQuestion(email);
		
		request.setAttribute("secQuestion", secQuestion);
		request.setAttribute("email", email);
		
		request.getRequestDispatcher("PasswordReset.jsp").forward(request, response);

	}
}
