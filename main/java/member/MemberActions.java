package member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.LoginDao;

/**
 * Servlet implementation class MemberActions
 */
@WebServlet("/MemberActions")
public class MemberActions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberActions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberQueries memberQueries = new MemberQueries();
		SearchAndSubmitTagsID submit = new SearchAndSubmitTagsID();
		
		// NOTE THAT: where emailCookie is used is only for SQL to define email of person in queries!!!!!!!! 
		String emailCookie = LoginDao.getToken(request.getCookies());
		
		String oldPass = request.getParameter("OldPassword");
		String newPass = request.getParameter("NewPassword");
		String checkNew = request.getParameter("RepeatedNewPassword");
		String name = request.getParameter("Name");
		String surname = request.getParameter("Surname");
		String birthday = request.getParameter("Birthday");
		System.out.println("THIS IS BIRTHDAY : "+birthday);
		String city = request.getParameter("City");
		String country = request.getParameter("Country");
		System.out.println("in memberActions country :" + country);
		String mobilePhone = request.getParameter("MobilePhone");
		
		String[] alltags = request.getParameterValues("allTags[]");
		
		String[] mytags =request.getParameterValues("myTags[]");
		
		if(mytags !=null){
		for(int i = 0;i<mytags.length;i++){
			System.out.println(mytags[i]);
		}
		}else{
			System.out.println("is nuuuul");
		}
		// CHANGE Pass
		System.out.println("OLD PASS : "+ oldPass);
		System.out.println("New PASS : "+ newPass);
		System.out.println("repnew PASS : "+ checkNew);

		if(newPass != null && newPass != ""){
				if(MemberQueries.checkOldPassword(oldPass, emailCookie)){
					if(newPass.equals(checkNew)){
						MemberQueries.sumbitNewPasswords(emailCookie, newPass);
					}
				}
			}
			
		//CHANGE REST OF OPTIONS
				if(name!=null && name!= ""){
					memberQueries.doUpdateName(name, emailCookie);
				}if(surname!=null && surname!=""){
					memberQueries.doUpdateSurname(surname, emailCookie);
				}if(city!=null && city!=""){
					memberQueries.doUpdateCity(city,emailCookie);
				}if(country!=null && country!=""){
					memberQueries.doUpdateCountry(country,emailCookie);
				}if(mobilePhone!=null && mobilePhone!=""){
					memberQueries.doUpdateMobilePhone(mobilePhone,emailCookie);
				}if(birthday!=null && birthday!="" && !birthday.equals("1900-01-01")){
					memberQueries.doUpdateBirthday(birthday,emailCookie);
				}
				
				
				//TAGS
			if(alltags != null){
					submit.searchTags(alltags, emailCookie);
			}
			if(mytags != null){
					submit.deleteTags(mytags, emailCookie);
			}
			response.sendRedirect("ProfileSettings.jsp");
		}
	
		
	}


