package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.DetailsBean;
import detailsFormat.FormatEmail;
import util.CheckEmail;
import util.Password;
import util.Submit;


/**
 * Servlet implementation class singupnew
 */
public class SignUpNew extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				FormatEmail format = new FormatEmail();
				HttpSession session = request.getSession();
				
				boolean isInDB = false;
				boolean isCorrectFormat = false;
				
				String name = request.getParameter("name");
				String surname = request.getParameter("surname");
				String email = request.getParameter("email");
				String confEmail = request.getParameter("confirm_email");
				String password = request.getParameter("password");
				String confPass = request.getParameter("confirm_password");
				
				DetailsBean details = new DetailsBean(name,surname,email);
				session.setAttribute("sessionperson",details);	
				DetailsBean sessionperson = (DetailsBean) session.getAttribute("sessionperson");
				String sessionemail = sessionperson.getEmail();
				////////////PASSWORD RESET FUNCTIONALITY///////////////////////////////
				String securityQuestion = request.getParameter("securityQuestion");
				String securityAnswer = request.getParameter("securityAnswer");

				//////////////////////////////////////////////////////////////////////////
				
				session.setAttribute("sessionperson",details);
				
				
				//value take the boolean from the class CheckEmail which is checking whether the email is in the database
				
				isInDB = CheckEmail.checkMe(sessionemail);
				isCorrectFormat = format.emailFormat(sessionemail);
				//If email already exist in database redirect and notify client whats missing 
				
				
				 if((confEmail.equals(email)) && (password.equals(confPass))){
					 if ((isInDB || !isCorrectFormat) && (password != null)){
						response.sendRedirect("LoginFailed.html");	
					}else {
						//
						Submit.submitMe(name,surname,email,Password.hashCode(password), 
								securityQuestion, Password.hashCode(securityAnswer));

						response.sendRedirect("ProfileDetails.jsp");
					}
				}
				
	}

}
