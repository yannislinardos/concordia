package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Queries;

public class BuyTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyTicket() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int dateid = Integer.parseInt(request.getParameter("dateid"));
		String email = request.getParameter("email");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		if(quantity <= Queries.getSeatsLeft(dateid)) {
			
			for (int i = 0; i < quantity; i++) {
				Queries.addTicket(dateid, email);
			}
			
	    	request.setAttribute("success", true);
			
			request.getRequestDispatcher("TicketsBought.jsp").forward(request, response);
			
		} else {
			
	    	request.setAttribute("success", false);
			
			request.getRequestDispatcher("TicketsBought.jsp").forward(request, response);
			
		}
		

	}

}
