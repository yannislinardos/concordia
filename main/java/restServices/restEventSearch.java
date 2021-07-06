package restServices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import beans.*;
import util.*;

@Path("/search")
public class restEventSearch {
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Event> findAll() {
		
		return Queries.getAllEvents();
	}
	
	@GET
	@Path("/{searchWords}")
	@Produces(MediaType.APPLICATION_JSON)
	public Event[] findBySearchWords(@PathParam("searchWords") String searchWords) {	
		
		if(searchWords.equals("ALL") || searchWords.equals("FILM") || searchWords.equals("THEATER") || searchWords.equals("EXPOSITION")) {
			
			return Queries.getByType(searchWords).toArray(new Event[0]);
		} else if (isInteger(searchWords)){
			return new Event[] {Queries.getEvent(searchWords)};
		} else {
			return Queries.searchEvents(searchWords, "ALL").toArray(new Event[0]);

		}
		
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	@GET
	@Path("{from}/{until}/{searchWords}")
	@Produces(MediaType.APPLICATION_JSON)
	public Event[] findBysearchEventsDate(
			@PathParam("from") String from,
			@PathParam("until") String until,
			@PathParam("searchWords") String searchWords) {
		
		if(searchWords.equals("ALL") || searchWords.equals("FILM") || searchWords.equals("THEATER") || searchWords.equals("EXPOSITION")) {
			
			return Queries.searchOnlyDates(from, until, searchWords).toArray(new Event[0]);
		} else {
			
			return Queries.searchEventsDate(searchWords, from, until, "ALL").toArray(new Event[0]);

		}

	}
	
	@GET
	@Path("{from}/{until}/{searchWords}/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Event[] findBysearchEventsDateWords(
			@PathParam("from") String from,
			@PathParam("until") String until,
			@PathParam("type") String type,
			@PathParam("searchWords") String searchWords) {
		return Queries.searchEventsDate(searchWords, from, until, type).toArray(new Event[0]);

		}
	
	@GET
	@Path("{from}/{until}")
	@Produces(MediaType.APPLICATION_JSON)
	public Event[] findBysearchEventsDate(
			@PathParam("from") String from,
			@PathParam("until") String until) {
		if(until.equals("ALL") || until.equals("FILM") || until.equals("THEATER") || until.equals("EXPOSITION")) {	
		
			return	 Queries.searchEvents(from, until).toArray(new Event[0]);
		
		}else {
			return Queries.searchOnlyDates(from, until, "ALL").toArray(new Event[0]);

		}
	}
	
}



