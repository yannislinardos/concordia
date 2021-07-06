package AdminDashBoard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import beans.Event;
import util.Queries;

/**
 * Servlet implementation class EditEvents
 */
//@WebServlet("/EditEvents")
@MultipartConfig
public class AdminEventsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEventsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AdminActions createEvent = new AdminActions();
		//List<String> namesP = new ArrayList<String>();
		HashMap<String,InputStream> map = new HashMap<String,InputStream>();
		boolean dodelete = false;
		List<String> images = new ArrayList<String>();

		// Details
		int id = 0;
		String eventid =  request.getParameter("Eventid");
		System.out.println("what you get from request = " + eventid);
		boolean ifstat = (eventid.equals("-1"));
		System.out.println("before if-statement : "+id);
		if(!ifstat){
			 id = Integer.parseInt(request.getParameter("Eventid"));
			 System.out.println("IN if-statement : "+id);
		}
		System.out.println("after if-statement : "+id);
		String delete = request.getParameter("delete");
		if(delete != null){
			dodelete =delete.equals("Delete");
		}
		String location = request.getParameter("location");
		int capacity = Integer.parseInt(request.getParameter("capacity"));
		double ticketprice = Double.parseDouble(request.getParameter("ticketprice"));
		String description = request.getParameter("description");
		String name = request.getParameter("name");
		String artist =request.getParameter("artist");
		
		
		
		
		
//		//IMAGES
//
//		// gets absolute path of the web application
//        String appPath = request.getServletContext().getRealPath("");
//        // constructs path of the directory to save uploaded file
//        String savePath = appPath + File.separator ;
//        System.out.println("save PAth = "+savePath);
//        File fileSaveDir = new File(savePath);
//        if (!fileSaveDir.exists()) {
//        	System.out.println("in create directory!!");
//            fileSaveDir.mkdir();
//        }
//
//		List<Part> parts = request.getParts().stream().filter(part -> "images[]".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">
//		if(parts != null){
//	        for (Part part : parts) {
//	        	System.out.println("in forloop picture!!");
//	            String fileName = extractFileName(part);
//	            System.out.println("Path of pictures :  "+savePath + File.separator + fileName);
//	            part.write(savePath + File.separator + fileName);
//	            System.out.println("This is the file name: "+ fileName);
//	            images.add(fileName);
//	        }
//		}
                
		        
		//TYPE
		String types = request.getParameter("type");
		//DATES
		String[] date = request.getParameterValues("StartDate[]");
		String[] starttime = request.getParameterValues("StartTime[]");
		String[] endtime = request.getParameterValues("EndTime[]");
		
	
		System.out.println("capacity   = "+capacity);
		
		String[] alltags = request.getParameterValues("allTags[]");
		String[] mytags =request.getParameterValues("myTags[]");

		if(mytags !=null){
			for(int i = 0;i<mytags.length;i++){
				System.out.println(mytags[i]);
			}
			}else{
				System.out.println("is nuuuul");
			}
			System.out.println(types);
			 
			 System.out.println("id in servlet "+ id);
			 System.out.println(delete);
			if (id == 0 && (name!=null || name != "")){
			 //EXCEPT FROM DATE AND TIMES
			 createEvent.doCreateEvent(alltags, name, location, description, ticketprice, types, artist);
			 createEvent.addDateCapacity(date, capacity, starttime, endtime);
			 createEvent.uploadImages(images);
			System.out.println("in the create event"); 
			}else if(id != 0 && !dodelete){
				System.out.println("in the Update event");
				String idString = Integer.toString(id);
				
				createEvent.doUpdateName(name,idString);
				createEvent.doUpdateLocation(location, idString);
				createEvent.doUpdateDescription(description, idString);
				if(alltags != null){
					createEvent.doUpdateAddTags(alltags, eventid);
				}
				if(mytags != null){
					createEvent.doUpdateDeleteTags(mytags, eventid);
				}
				createEvent.doUpdateTciketPrice(ticketprice, idString);
				createEvent.doUpdateArtist(artist, idString);
				createEvent.doUpdateType(types,idString);
				createEvent.doUpdatetsVector(idString);
				createEvent.doUpdateCapacity(capacity, idString);

		 }else if(dodelete){
				 createEvent.doDetele(id);
				 System.out.println("in Delete");
			 }
			response.sendRedirect("AdminPage.jsp");
	}
	
	private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
 
}
