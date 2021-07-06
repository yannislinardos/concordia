package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Article;
import util.Queries;


public class addArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public addArticle() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String title = request.getParameter("title");
		String Date = request.getParameter("date");


	    StringBuffer text = new StringBuffer(request.getParameter("body"));

	    int loc = (new String(text)).indexOf('\n');
	    while(loc > 0){
	        text.replace(loc, loc+1, "<BR>");
	        loc = (new String(text)).indexOf('\n');
	   }
	    
	    
	 	String[] dateSplit = Date.split("/");
    	String date = dateSplit[2]+"-"+dateSplit[0]+"-"+dateSplit[1];
    	
    	Article article = new Article();
    	
    	article.setBody(text.toString());
    	article.setDate(date);
    	article.setTitle(title);
    	
    	Queries.postArticle(article);
    	
    	request.setAttribute("success", true);
		
		request.getRequestDispatcher("addArticle.jsp").forward(request, response);

	}

}
