package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.LoginBean;
import util.LoginDao;

public class LoginProcess extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        PrintWriter out = response.getWriter();
        LoginBean bean = new LoginBean();
        bean.setEmail(email);
        bean.setPass(pass);
        boolean status=LoginDao.validate(bean);
        if(status){
            Cookie token = LoginDao.tokenBuilder(email);
            token.setMaxAge(60*60);
            response.addCookie(token);
            response.sendRedirect("HomePage.jsp");

        }
        else
        {
            response.sendRedirect("LoginFailed.html");
        }
    }
}