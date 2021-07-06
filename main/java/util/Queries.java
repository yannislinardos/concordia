package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import beans.Article;
import beans.Event;
import beans.Ticket;

public class Queries {
	
    
    public static String sanitize(String input) {
    	
    	if (input == null) {
    		input = "";
    	}
    	
    	String safe = Jsoup.clean(input, Whitelist.basic());
    	
        return safe;
    }


    public static boolean userExists(String string) {
    	String email = sanitize(string);
    	boolean ret = false;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
    	try {
    		
			ps = con.prepareStatement(  
				  "select * from member where email=?");
			
			ps.setString(1,email);  
		    	                  
		    rs=ps.executeQuery();  
		    
		    ret = rs.next();  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
    	
    	return ret;
    }

    public static String getPassword(String string) {
    	String email = sanitize(string);
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String password = null;
    	
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con= null;

    	
    	if (!userExists(email)) {
    		return null;
    	}
    	
    	else {
    		try {
    			con=provider.getCon();
    			ps = con.prepareStatement(  
    				  "select hashpass from member where email=?");
    			
    			ps.setString(1,email);  
    		    	                  
    		    rs = ps.executeQuery();  
    		    
    		    rs.next();
    		    password = rs.getString("hashpass");
    		    
        	} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} finally {
    		    if (rs != null) {
    		        try {
    		            rs.close();
    		        } catch (SQLException e) { /* ignored */}
    		    }
    		    if (ps != null) {
    		        try {
    		            ps.close();
    		        } catch (SQLException e) { /* ignored */}
    		    }
    		    if (con != null) {
    		        try {
    		            con.close();
    		        } catch (SQLException e) { /* ignored */}
    		    }
    		}
    		return password;
    	}
    
    	
    }
    
    public static String getName(String string) {
    	
    	String email = sanitize(string);
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String name = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con= null;

    	
    	if (!userExists(email)) {
    		return null;
    	}
    	
    	else {
    		try {
    			con = provider.getCon();
    			ps = con.prepareStatement(  
    				  "SELECT name||' '||surname AS nnnn FROM person WHERE email=?");
    			
    			ps.setString(1,email);  
    		    	                  
    		    rs = ps.executeQuery();  
    		    
    		    rs.next();
    		    name = rs.getString("nnnn");
    		    
        	} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} finally {
    		    if (rs != null) {
    		        try {
    		            rs.close();
    		        } catch (SQLException e) { /* ignored */}
    		    }
    		    if (ps != null) {
    		        try {
    		            ps.close();
    		        } catch (SQLException e) { /* ignored */}
    		    }
    		    if (con != null) {
    		        try {
    		            con.close();
    		        } catch (SQLException e) { /* ignored */}
    		    }
    		}
    		return name;
    		
    	}
    }
    
    
    public static boolean containsEvent(List<Event> events, int eventId) {
        for(Event event : events) {
            if(event != null && event.getId() == eventId) {
                return true;
            }
        }
        return false;
    }
    
    public static List<Event> searchEvents(String searchWords1, String type1) {
    	
    	String searchWords = sanitize(searchWords1);
    	String type = sanitize(type1);
    	List<Event> events = new ArrayList<Event>();
    	PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=null;

	    if (searchWords == null) {
	    	return events;
	    }

	    try {
	    	con=provider.getCon();
			if("ALL".equals(type)) {
				ps = con.prepareStatement(  
					    "SELECT *,ts_rank(ts,to_tsquery(?)) "
					    + "AS score FROM event, eventdate WHERE ts @@ to_tsquery(?) "
					    + "AND event.eventid = eventdate.eventid "		
					    + "ORDER BY score DESC");
			} else {
				ps = con.prepareStatement(  
					    "SELECT *,ts_rank(ts,to_tsquery(?)) "
					    + "AS score FROM event, eventdate WHERE ts @@ to_tsquery(?) "
					    + "AND event.eventid = eventdate.eventid "
					    + "AND type=? "
					    + "ORDER BY score DESC");
			}

			
			String[] splitOnSpaces = searchWords.split(" ");
			
			String tsquery = " ";
			
			for (int i = 0; i < splitOnSpaces.length; i++) {
				
				if (i == 0) {
					tsquery = splitOnSpaces[i];
				} else {
					tsquery = tsquery + " | " + splitOnSpaces[i];
					
				}
				
			}
			
			ps.setString(1,tsquery); 
			ps.setString(2,tsquery); 
			
			if(!"ALL".equals(type)) {
				ps.setString(3,type); 

			}
			
		    results=ps.executeQuery();  
		   					
			while(results.next()){
			
			if (!containsEvent(events, Integer.parseInt(results.getString("eventid")))) {
				Event event = new Event();

				event.setDate(getDateRange(results.getString("eventid")));
				event.setDescription(results.getString("description"));
				event.setLocation(results.getString("location"));
				event.setName(results.getString("name"));
				event.setTicketPrice(results.getFloat("ticketprice"));
				event.setType(results.getString("type"));
				event.setId(Integer.parseInt(results.getString("eventid")));
				
				events.add(event);

			}
						
		}
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}

		return events;
    }
    public static List<Event> searchEventsDate (String words, String from1, String until1, String type1) {
    	String searchWords = sanitize(words);
    	String type = sanitize(type1);
    	if (type == null || "".equals(type)) {
    		type = "ALL";
    	}
    	if (from1 == null || until1 == null) {
    		return searchEvents(searchWords, type);

    	}
    	
    	String[] splitUntil = sanitize(until1).split("/");
    	String[] splitFrom = sanitize(from1).split("/");
    	  	
    	if (splitUntil.length != 3 || splitFrom.length != 3) {
    		return searchEvents(searchWords, type);
    	}
    	
    	String from = splitFrom[2]+"-"+splitFrom[0]+"-"+splitFrom[1];
    	String until = splitUntil[2]+"-"+splitUntil[0]+"-"+splitUntil[1];
    	
    	if (words == null || words.equals("")) {
    		return searchOnlyDates(from, until, type);
    	}
    	
    	List<Event> events = new ArrayList<Event>();
    	PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();

    	Connection con=provider.getCon();

		try {
			
			if("ALL".equals(type)) {
				ps = con.prepareStatement(  
					    "SELECT *,ts_rank(ts,to_tsquery(?)) "
					    + "AS score FROM event, eventdate WHERE ts @@ to_tsquery(?) "
					    + "AND eventdate.eventid=event.eventid "
					    + "AND eventdate.date BETWEEN ?::DATE AND ?::DATE "
					    + "ORDER BY score DESC");
			} else {
				ps = con.prepareStatement(  
					    "SELECT *,ts_rank(ts,to_tsquery(?)) "
					    + "AS score FROM event, eventdate WHERE ts @@ to_tsquery(?) "
					    + "AND eventdate.eventid=event.eventid "
					    + "AND eventdate.date BETWEEN ?::DATE AND ?::DATE "
					    + "AND type=? "
					    + "ORDER BY score DESC");
			}
			
			
			String[] splitOnSpaces = searchWords.split(" ");
			
			String tsquery = " ";
			
			for (int i = 0; i < splitOnSpaces.length; i++) {
				
				if (i == 0) {
					tsquery = splitOnSpaces[i];
				} else {
					tsquery = tsquery + " | " + splitOnSpaces[i];
					
				}
				
			}
			
			ps.setString(1,tsquery); 
			ps.setString(2,tsquery);
			ps.setString(3,from); 
			ps.setString(4,until); 
			
			if(!"ALL".equals(type)) {
				ps.setString(5,type); 

			}
			
		    results=ps.executeQuery();  
		   					
			while(results.next()){
				
			Event event = new Event();

			event.setDate(getDateRange(results.getString("eventid")));
			event.setDescription(results.getString("description"));
			event.setLocation(results.getString("location"));
			event.setName(results.getString("name"));
			event.setTicketPrice(results.getFloat("ticketprice"));
			event.setType(results.getString("type"));
			event.setId(Integer.parseInt(results.getString("eventid")));
			
			if (!containsEvent(events, event.getId())) {
				events.add(event);
			}
			
			
		}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (con != null) {
	        try {
	            con.close();
	        } catch (SQLException e) { /* ignored */}
	    }
		}


		return events;
    }
    
    public static List<Event> searchOnlyDates(String from, String until, String type1) {
    	String type = sanitize(type1);
    	List<Event> events = new ArrayList<Event>();
    	PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
	    String query = "";
		try {
			if("ALL".equals(type)) {
				query=   
					    "SELECT * "
					    + "FROM event, eventdate WHERE "
					    + "eventdate.eventid=event.eventid "
					    + "AND eventdate.date BETWEEN ?::DATE AND ?::DATE "
					    + "ORDER BY date DESC";
			} else {
				query =   
					    "SELECT * "
					    + "FROM event, eventdate WHERE "
					    + "eventdate.eventid=event.eventid "
					    + "AND eventdate.date BETWEEN ?::DATE AND ?::DATE "
					    + "AND type=? "
					    + "ORDER BY date DESC";
			}
			
			ps = con.prepareStatement(query);
			ps.setString(1,from); 
			ps.setString(2,until); 
			
			if(!"ALL".equals(type)) {
				ps.setString(3,type); 

			}
			
		    results=ps.executeQuery();  
		   					
			while(results.next()){
				
			Event event = new Event();

			event.setDate(getDateRange(results.getString("eventid")));
			event.setDescription(results.getString("description"));
			event.setLocation(results.getString("location"));
			event.setName(results.getString("name"));
			event.setTicketPrice(results.getFloat("ticketprice"));
			event.setType(results.getString("type"));
			event.setId(Integer.parseInt(results.getString("eventid")));
			
			if (!containsEvent(events, event.getId())) {
				events.add(event);
			}
			
		}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}


		return events;
    }
    
    public static String getDateRange(String eventid) {
    	PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
	    String query = "";
	    String ret="";
	    
	    
	    try {
		    query = "SELECT MAX(date) AS max, MIN(date) AS min "
		    		+ "FROM eventdate "
		    		+ "WHERE eventid=? "
		    		+ "GROUP BY eventid;";
		    
			ps = con.prepareStatement(query);
			
			ps.setString(1, eventid); 

			
		    results=ps.executeQuery();  

		    while(results.next()){
		    	ret = results.getString("min") + " t/m " + results.getString("max");
		    }
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}

    	return ret;
    }
    
    public static Event getEvent(int id) {
    	Event event = new Event();
    	PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
	    String query = "SELECT * FROM event, eventdate WHERE event.eventid=? "
	    		+ "AND event.eventid = eventdate.eventid;";
	    
	    try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, Integer.toString(id)); 

		    results=ps.executeQuery();  
		    if(results.next()) {
			    
			    event.setArtist(results.getString("artist"));
				event.setDate(getDateRange(results.getString("eventid")));
				event.setDescription(results.getString("description"));
				event.setLocation(results.getString("location"));
				event.setName(results.getString("name"));
				event.setTicketPrice(results.getFloat("ticketprice"));
				event.setType(results.getString("type"));
				event.setId(Integer.parseInt(results.getString("eventid")));
		    }
		    	
		 

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}


    	return event;
    }
    
    public static Event getEvent(String string) {
    	
    	String sId = sanitize(string);
    	
    	int id = Integer.parseInt(sId);
    	
    	return getEvent(id);
    	
    }
    
    public static List<String[]> getEventDates(String eventId) {
    	
    	String id = sanitize(eventId);
    	List<String[]> instances = new ArrayList<>();
    	
    	PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
	    String query = "SELECT dateid, date, timestart, timeend FROM eventdate WHERE eventid=?;";
	    
	    try {
	    	
			ps = con.prepareStatement(query);
			ps.setString(1, id); 
		    results=ps.executeQuery(); 
	    	String dateNcapacity = "";
	    	int seatsLeft = 0;
		    while(results.next()) {
		    	
		    	seatsLeft = Queries.getSeatsLeft(results.getString("dateid"));

		    	dateNcapacity = "Date: "+ results.getString("date") + " Time: " + 
		    	results.getString("timestart") + " - " + results.getString("timeend") +" Seats Left: " + seatsLeft;
		    	
		    	String[] entry = {results.getString("dateid"), dateNcapacity};
		    	
		    	instances.add(entry);
		    			
		    }

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}

    	
    	return instances;
 	
    }
    
    public static List<Event> getUserSuggestions(String email) {
    	
    	List<Event> events = new ArrayList<>();
    	
    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
    	
    	PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
	    String query = "SELECT DISTINCT event.eventid, COUNT(DISTINCT tagevent.tagid) AS count "
	    				+ "FROM event, tagperson, tagevent, eventdate "
	    				+ "WHERE tagperson.email=? AND tagperson.tagid=tagevent.tagid "
	    				+ "AND tagevent.eventid=event.eventid "
	    				+ "AND eventdate.eventid=event.eventid "
	  	    		    + "AND eventdate.date > ?::DATE "
	  	    		    + "GROUP BY event.eventid "
	  	    		    + "ORDER BY count DESC;";
	    
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, email); 
			ps.setString(2, dateFormat.format(date)); 


		    results=ps.executeQuery(); 

		    while(results.next()) {
		    	
		    	Event event = getEvent(results.getString("eventid"));
		    			    	
		    	events.add(event);
		    }

		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
    	return events;
    }
    
    public static List<Event> getEventSuggestions(String eventid) {
    	
    	List<Event> events = new ArrayList<>();
    	
    	PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
	    String query = "SELECT DISTINCT suggestedevent.eventid, "
	    		  + "COUNT(DISTINCT giventag.tagid) AS count FROM event AS givenevent, "
	    		  + "event AS suggestedevent, eventdate, "
	    		  + "tagevent AS giventag, tagevent AS suggestedtag "
	    		  + "WHERE givenevent.eventid=? "
	    		  + "AND givenevent.eventid=giventag.eventid "
	    		  + "AND giventag.tagid=suggestedtag.tagid "
	    		  + "AND suggestedtag.eventid=suggestedevent.eventid "
	    		  + "AND suggestedevent.eventid <> ? "
	    		  + "AND eventdate.eventid=suggestedevent.eventid "
	    		  + "GROUP BY suggestedevent.eventid "
	    		  + "ORDER BY count DESC; ";

	    
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, eventid); 
			ps.setString(2, eventid); 
	 

		    results=ps.executeQuery(); 

		    while(results.next()) {
		    	
		    	Event event = getEvent(results.getString("eventid"));
		    	
		    	events.add(event);
		    }

		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}

	    
    	
    	return events;
    }
    
    public static List<Event> getUpcomingEvents() {
    	
    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        List<Event> upcomingEvents = new ArrayList<Event>();
		upcomingEvents = Queries.searchOnlyDates(dateFormat.format(date), "01/01/2100", "ALL");
		
		return upcomingEvents;
    }
    
 public static List<Event> getRecentEvents() {
    	
    	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        List<Event> upcomingEvents = new ArrayList<Event>();
		upcomingEvents = Queries.searchOnlyDates("01/01/2000", dateFormat.format(date), "ALL");
		
		return upcomingEvents;
    }
    
 	public static List<String> getEventTags(String eventId) {
	 
	 List<String> tags = new ArrayList<>();
	 
 	PreparedStatement ps = null;
 	ResultSet results = null;
	ConnectionProvider provider = new ConnectionProvider();
 	Connection con=provider.getCon();
	    String query = "SELECT tags.tag FROM tags, tagevent "
	    			  +"WHERE tags.tagid=tagevent.tagid "
	    			  +"AND tagevent.eventid=?;";
	 
	    
	  		try {
	  			ps = con.prepareStatement(query);
	  			
				ps.setString(1, eventId); 
	  			
	  		    results=ps.executeQuery(); 
	  		    
	  		  while(results.next()) {
			    	
			    	String tag = results.getString("tag");
			    	
			    	tags.add(tag);
			    }

	  		    
	  		} catch (SQLException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		} finally {
			    if (results != null) {
			        try {
			            results.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (ps != null) {
			        try {
			            ps.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (con != null) {
			        try {
			            con.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}

	  	    
	 
	 return tags;

 	}
 
 	public static boolean isUpcoming(String eventid) {
	 
	 boolean toRet = false;
	 
	 int id = Integer.parseInt(sanitize(eventid));
	 
	 List<Event> upcoming = getUpcomingEvents();
	 
	 for (Event e: upcoming) {
		 
		 if (e.getId() == id) {
			 toRet = true;
		 }
	 }	 
	 
	 return toRet;
	 
 	}

    public static List<Event> getUpcoming(String type) {
        List<Event> upcomingEvents = new ArrayList<Event>();

        PreparedStatement ps = null;
        ResultSet results = null;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();

        ConnectionProvider provider = new ConnectionProvider();
        Connection con=provider.getCon();

        String query = "SELECT * "
                + "FROM event, eventdate WHERE "
                + "eventdate.eventid=event.eventid "
                + "AND eventdate.date >= ?::DATE "
                + "AND type=? "
                + "ORDER BY date ASC";


        try {
            ps = con.prepareStatement(query);

            ps.setString(1, dateFormat.format(date));


            ps.setString(2, type);

            results=ps.executeQuery();

            while(results.next()) {

                Event event = Queries.getEvent(results.getString("eventid"));


                if (!containsEvent(upcomingEvents, Integer.parseInt(results.getString("eventid")))) {
                    upcomingEvents.add(event);

                }

            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        }


        return upcomingEvents;
    }
 
 	public static List<Event> getRecent(String type) {
	 List<Event> recentEvents = new ArrayList<Event>();
	 
	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
     Date date = new Date();
     recentEvents = Queries.searchOnlyDates(dateFormat.format(date), "01/01/2100",  type);
		
		return recentEvents;
 	}
 	
 	 public static boolean adminExists(String string) {
     	String email = sanitize(string);
     	boolean ret = false;
     	PreparedStatement ps = null;
     	ResultSet rs = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();

     	try {
 			ps = con.prepareStatement(  
 				  "select * from admin where email=?");
 			
 			ps.setString(1,email);  
 		    	                  
 		    rs=ps.executeQuery();  
 		    
 		    ret = rs.next();  
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} finally {
 		    if (rs != null) {
 		        try {
 		            rs.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		    if (ps != null) {
 		        try {
 		            ps.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		   if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
 		}
     	
     	return ret;
     }
    
 	 public static String getAdminPassword(String string) {
     	String email = sanitize(string);
     	PreparedStatement ps = null;
     	ResultSet rs = null;
     	String password = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();

     	
     	if (!adminExists(email)) {
     		return null;
     	}
     	
     	else {
     		try {
     			ps = con.prepareStatement(  
     				  "select password from admin where email=?");
     			
     			ps.setString(1,email);  
     		    	                  
     		    rs = ps.executeQuery();  
     		    
     		    rs.next();
     		    password = rs.getString("password");
     		    
         	} catch (SQLException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		} finally {
     		    if (rs != null) {
     		        try {
     		            rs.close();
     		        } catch (SQLException e) { /* ignored */}
     		    }
     		    if (ps != null) {
     		        try {
     		            ps.close();
     		        } catch (SQLException e) { /* ignored */}
     		    }
     		   if (con != null) {
   		        try {
   		            con.close();
   		        } catch (SQLException e) { /* ignored */}
   		    }
     		}
     		return password;
     	}
     }
 	 
 	 public static String getSecurityQuestion(String email1) {
 		 String email = sanitize(email1);
 		 String secQuestion = null;
 		 
 		PreparedStatement ps = null;
     	ResultSet rs = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
    	
    	try {
 			ps = con.prepareStatement(  
 				  "select secquestion from member where email=?");
 			
 			ps.setString(1,email);  
 		    	                  
 		    rs = ps.executeQuery();  
 		    
 		   if (rs.next()) {
 	 		   secQuestion = rs.getString("secquestion");
 		   }
 		    
     	} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} finally {
 		    if (rs != null) {
 		        try {
 		            rs.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		    if (ps != null) {
 		        try {
 		            ps.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		   if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
 		}
 		 return secQuestion;
 		 
 	 }
 	 
 	 public static String getSecurityAnswer(String email1) {
 		 String email = sanitize(email1);
 		 String secanswer = "";
 		 
 		PreparedStatement ps = null;
     	ResultSet rs = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
    	
    	try {
 			ps = con.prepareStatement(  
 				  "select secanswer from member where email=?");
 			
 			ps.setString(1,email);  
 		    	                  
 		    rs = ps.executeQuery();  
 		    
 		   if (rs.next()) {
 	 		   secanswer = rs.getString("secanswer");
   
 		   }
 		    
     	} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} finally {
 		    if (rs != null) {
 		        try {
 		            rs.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		    if (ps != null) {
 		        try {
 		            ps.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		   if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
 		}
 		 return secanswer;
 		 
 	 }
 	 
 	 public static void changePassword(String password1, String Email) {
 		String password = sanitize(password1);
 		String email = sanitize(Email);
		 
		PreparedStatement ps = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
   	
   	try {
			ps = con.prepareStatement(  
				  "UPDATE member SET hashpass=? WHERE email=?;");
			
			ps.setString(1,Password.hashCode(password));  
			ps.setString(2,email);  
		    	                  
		    ps.executeUpdate();  
		    
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		   if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
 	 }

    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet results = null;
        ConnectionProvider provider = new ConnectionProvider();
        Connection con=provider.getCon();
        String query = "SELECT DISTINCT * FROM event, eventdate WHERE event.eventid=eventdate.eventid ORDER BY eventdate.date DESC;";


        try {
            ps = con.prepareStatement(query);

            results=ps.executeQuery();

            while(results.next()) {

                Event event = getEvent(results.getString("eventid"));

                if (!containsEvent(events, Integer.parseInt(results.getString("eventid")))) {
                    events.add(event);

                }

            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (final SQLException e) { /* ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (final SQLException e) { /* ignored */}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        }


        return events;

    }
 	 
 	 public static List<Event> getByType(String Type) {
 		 
 	
 		 List<Event> events = new ArrayList<Event>();
 		 
 		String type = sanitize(Type);
 		
 		if (type == null || "".equals(type)) {
 			type = "ALL";
 		}
 		
 		if (type.equals("ALL")) {
 			return getAllEvents();
 		}
 		
     	PreparedStatement ps = null;
     	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
     	Connection con=provider.getCon();
 	    String query = "SELECT DISTINCT * FROM event WHERE type=?;";

 	    
 		try {
 			ps = con.prepareStatement(query);
 			
 			ps.setString(1, type);
 	 
 		    results=ps.executeQuery(); 

 		    while(results.next()) {
 		    	
 		    	Event event = getEvent(results.getString("eventid"));
 		    	events.add(event);
 		    }

 		    
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} finally {
 		    if (results != null) {
 		        try {
 		            results.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (ps != null) {
 		        try {
 		            ps.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (con != null) {
 		        try {
 		            con.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		 
 	 }
 		return events;
 	 }
 	 
 	 public static List<Article> getArticles() {
 	 	
 		 List<Article> articles = new ArrayList<Article>();
 		
     	PreparedStatement ps = null;
     	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
     	Connection con=provider.getCon();
     	
 	    String query = "SELECT DISTINCT * FROM articles ORDER BY date DESC;";

 	    
 		try {
 			ps = con.prepareStatement(query);
 	 
 		    results=ps.executeQuery(); 

 		    while(results.next()) {
 		    	
 		    	Article article = new Article();
 		    	
 		    	article.setArticleid(results.getInt("articleid"));
 		    	article.setBody(results.getString("body"));
 		    	article.setDate(results.getString("date"));
 		    	article.setImageid(results.getInt("imageid"));
 		    	article.setTitle(results.getString("title"));
 		    	
 		    	articles.add(article);
 		    }

 		    
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} finally {
 		    if (results != null) {
 		        try {
 		            results.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (ps != null) {
 		        try {
 		            ps.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (con != null) {
 		        try {
 		            con.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		 
 	 }
 		return articles;
 	 }

    public static Article getArticle(String ID) {

        Article article = new Article();
        String id = sanitize(ID);

        PreparedStatement ps = null;
        ResultSet results = null;
        ConnectionProvider provider = new ConnectionProvider();
        Connection con=provider.getCon();

        String query = "SELECT DISTINCT * FROM articles WHERE articleid=?::INTEGER;";


        try {
            ps = con.prepareStatement(query);

            ps.setString(1, id);

            results=ps.executeQuery();

            if(results.next()) {


                article.setArticleid(results.getInt("articleid"));
                article.setBody(results.getString("body"));
                article.setDate(results.getString("date"));
                article.setImageid(results.getInt("imageid"));
                article.setTitle(results.getString("title"));

            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (final SQLException e) { /* ignored */}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (final SQLException e) { /* ignored */}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }

        }
        return article;
    }

 	 public static List<Article> getArticlesSince(String Date) {
  	 	
 		 List<Article> articles = new ArrayList<Article>();
 		 
 		 String date = sanitize(Date);
 		
     	PreparedStatement ps = null;
     	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
     	Connection con=provider.getCon();
     	
 	    String query = "SELECT DISTINCT * FROM articles WHERE date > ?::DATE ORDER BY date DESC;";

 	    
 		try {
 			ps = con.prepareStatement(query);
 			
 			ps.setString(1, date);
 	 
 		    results=ps.executeQuery(); 

 		    while(results.next()) {
 		    	
 		    	Article article = new Article();
 		    	
 		    	article.setArticleid(results.getInt("articleid"));
 		    	article.setBody(results.getString("body"));
 		    	article.setDate(results.getString("date"));
 		    	article.setImageid(results.getInt("imageid"));
 		    	article.setTitle(results.getString("title"));
 		    	
 		    	articles.add(article);
 		    }

 		    
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} finally {
 		    if (results != null) {
 		        try {
 		            results.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (ps != null) {
 		        try {
 		            ps.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (con != null) {
 		        try {
 		            con.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		 
 	 }
 		return articles;
 	 }
 	 
 	 public static void postArticle(Article article) {
 		 
 		PreparedStatement ps = null;
     	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
     	Connection con=provider.getCon();
     	
 	    String query =  "INSERT INTO articles (articleid, imageid, title, body, date) "
 	    		+ "VALUES (?,?,?,?,?::DATE);";

 	    
 	    
 		try {
 			ps = con.prepareStatement(query);
 			
 			ps.setInt(1, getNewArticleId());
 			ps.setInt(2, article.getImageid());
 			ps.setString(3, article.getTitle());
 			ps.setString(4, article.getBody());
 			ps.setString(5, article.getDate());

 		    ps.executeUpdate(); 

 		    
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} finally {
 		    if (results != null) {
 		        try {
 		            results.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (ps != null) {
 		        try {
 		            ps.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (con != null) {
 		        try {
 		            con.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		 
 		}
 		 
 	 }
 	 
 	 public static int getNewArticleId() {
 		 
 		PreparedStatement ps = null;
     	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
     	Connection con=provider.getCon();
     	int id = 1;
     	
 	    String query =  "SELECT MAX(articleid) FROM articles;";
 	    
 	    
 		try {
 			ps = con.prepareStatement(query);
 			
 		   results =  ps.executeQuery(); 
 		   
 		   while (results.next()) {
 			   id = results.getInt("max") + 1;
 		   }

 		    
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} finally {
 		    if (results != null) {
 		        try {
 		            results.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (ps != null) {
 		        try {
 		            ps.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (con != null) {
 		        try {
 		            con.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		 
 		}
		return id;
 		 
 	 }
 	 
 	 public static int getSeatsLeft(String dateid) {
 		 
 		 String id = sanitize(dateid);
 		 
 		PreparedStatement ps = null;
     	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
     	Connection con=provider.getCon();
     	
 	    String query =  "SELECT capacity FROM eventdate WHERE dateid=?::int;";
 	    
 	    int returnValue = 0;
 	    
 		try {
 			ps = con.prepareStatement(query);
 			
 			ps.setString(1, id);
 			
 		   results =  ps.executeQuery(); 
 		   
 		   while (results.next()) {
 			  returnValue = results.getInt("capacity");
 		   }

 		    
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		} finally {
 		    if (results != null) {
 		        try {
 		            results.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (ps != null) {
 		        try {
 		            ps.close();
 		        } catch (final SQLException e) { /* ignored */}
 		    }
 		    if (con != null) {
 		        try {
 		            con.close();
 		        } catch (SQLException e) { /* ignored */}
 		    }
 		 
 		}
		return returnValue;
 		 
 	 }
     
 	public static int getSeatsLeft(int dateid) {
 		
 		return getSeatsLeft(Integer.toString(dateid));
 	}
 	 
 	 
	public static Event getEventFromDate(String dateid) {
		Event event = new Event();
		
		 String id = sanitize(dateid);
		
    	PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
    	
	    String query = "SELECT DISTINCT eventid FROM eventdate WHERE dateid=?::int";

	    
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(query);
			
			ps.setString(1, id);
	 
		    results=ps.executeQuery(); 

		    if(results.next()) {
		    	
		    	event = getEvent(results.getString("eventid"));
		    }

		    con.commit();
		    
		} catch (SQLException e) {
			
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (final SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (final SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		 
	 }
		
		return event;
	}
	
	public static String getDate(String dateid) {
		
		String retValue = "";
		 String id = sanitize(dateid);
		
    	PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
    	
	    String query = "SELECT DISTINCT * FROM eventdate WHERE dateid=?::int";

	    
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, id);
	 
		    results=ps.executeQuery(); 

		    if(results.next()) {
		    	
		    	retValue = "Date: " + results.getString("date")+ ", Time: " + results.getString("timestart") +
		    			" - " + results.getString("timeend");
		    }

		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (final SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (final SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		 
	 }
		
		return retValue;
	}



	public static int getNewTicketid() {
		int id = 0;

		PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
    	
	    String query = "SELECT MAX(ticketid) from ticket;";

	    
		try {
			ps = con.prepareStatement(query);
				 
		    results=ps.executeQuery(); 

		    if(results.next()) {
		    	
		    	id = results.getInt("max") + 1;
		    }

		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (final SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (final SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		return id;
	}
	
	public static void addTicket(int dateid, String Email) {
		String email = sanitize(Email);
		PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
    	
	    String query = "INSERT INTO ticket (email, ticketid, dateid) VALUES (?,?,?);";

	    
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, email);
			ps.setInt(2, getNewTicketid());
			ps.setInt(3, dateid);
				 
		    ps.executeUpdate(); 
	    
		    decreaseSeatsLeft(dateid);
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (final SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (final SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
	}
	
	public static void decreaseSeatsLeft(int dateid) {
		PreparedStatement ps = null;
    	ResultSet results = null;
		ConnectionProvider provider = new ConnectionProvider();
    	Connection con=provider.getCon();
    	
	    String query = "UPDATE eventdate SET capacity = capacity - 1 WHERE dateid=?;";

	    
		try {
			ps = con.prepareStatement(query);

			ps.setInt(1, dateid);
				 
		    ps.executeUpdate(); 
	    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (results != null) {
		        try {
		            results.close();
		        } catch (final SQLException e) { /* ignored */}
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (final SQLException e) { /* ignored */}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
	}
	
}
