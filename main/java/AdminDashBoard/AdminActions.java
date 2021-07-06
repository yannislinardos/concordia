package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import beans.*;
import database.*;

public class Queries {

	public enum Rights {admin, user};

	public static String sanitize(String input) {

		if (input == null) {
			input = "";
		}

		String safe = Jsoup.clean(input, Whitelist.basic());

		return safe;
	}


	public static boolean userExists(String input) {
		String username = sanitize(input);
		boolean ret = false;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		try {

			ps = con.prepareStatement(  
					"select * from users where username=?");

			ps.setString(1,username);  

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

	public static String getPassword(String input) {
		String username = sanitize(input);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String hashPassword = null;
		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		if (!userExists(username)) {
			return null;
		}

		else {
			try {

				ps = con.prepareStatement(  
						"select password from users where username=?");

				ps.setString(1,username);  

				rs = ps.executeQuery();  

				rs.next();
				hashPassword = rs.getString("password");

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
			return hashPassword;
		}

	}

	public static Rights getRights(String input) {
		String username = sanitize(input);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String stringRights = null;
		Rights rights = null;
		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		if (!userExists(username)) {
			return null;
		}

		else {
			try {

				ps = con.prepareStatement(  
						"select rights from users where username=?");

				ps.setString(1,username);  

				rs = ps.executeQuery();  

				rs.next();
				stringRights = rs.getString("rights");

				if(stringRights.equals("admin")){
					rights = Rights.admin;
				} else if (stringRights.equals("user")) {
					rights = Rights.user;
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
			return rights;
		} 	
	}


	public static User getUser(String input) {
		String username = sanitize(input);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String stringRights = null;
		Rights rights = null;
		String firstName = null;
		String lastName = null;
		String email = null;
		int id = -1;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		if (!userExists(username)) {
			return null;
		}

		else {
			try {

				ps = con.prepareStatement(  
						"select * from users where username=?");

				ps.setString(1,username);  

				rs = ps.executeQuery();  

				rs.next();
				stringRights = rs.getString("rights");
				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				email = rs.getString("email");
				id = rs.getInt("userid");

				if(stringRights.equals("admin")){
					rights = Rights.admin;
				} else if (stringRights.equals("user")) {
					rights = Rights.user;
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

			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setRights(rights);
			user.setId(id);


			return user;
		}

	}

	public static User setUser(User user, String hashPass) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String username = sanitize(user.getUsername());

		Rights rights = user.getRights();
		String firstName = sanitize(user.getFirstName());
		String lastName = sanitize(user.getLastName());
		String email = sanitize(user.getEmail());

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "INSERT INTO AnnotationTool.users (username, password, rights, firstname, lastname, email) "
				+ "VALUES (?,?,?::enumRights,?,?, ?);";

		if (userExists(username)) {
			return null;
		}

		else {
			try {

				ps = con.prepareStatement(query);

				ps.setString(1,username);  
				ps.setString(2,hashPass); 
				ps.setString(3,rights.toString()); 
				ps.setString(4,firstName); 
				ps.setString(5,lastName);
				ps.setString(6,email); 

				ps.executeUpdate(); 


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

		}	
		return user;
	}

	public static User modifyMyself(User user, String hashPass) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String username = sanitize(user.getUsername());

		String firstName = sanitize(user.getFirstName());
		String lastName = sanitize(user.getLastName());
		String email = sanitize(user.getEmail());

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		if (!userExists(username)) {
			return null;
		}

		else {
			try {

				if (hashPass != null){
					String query =  "UPDATE AnnotationTool.users "
							+ "SET  password = ?,"
							+ "firstname = ?,"
							+ "lastname = ?,"
							+ "email = ?"
							+ " WHERE username = ?;";

					ps = con.prepareStatement(query);

					ps.setString(1,hashPass); 
					ps.setString(2,firstName); 
					ps.setString(3,lastName);
					ps.setString(4,email); 
					ps.setString(5,username);

					ps.executeUpdate(); 
				} else {
					String query =  "UPDATE AnnotationTool.users "
							+ "SET firstname = ?,"
							+ "lastname = ?,"
							+ "email = ?"
							+ " WHERE username = ?;";

					ps = con.prepareStatement(query);

					ps.setString(1,firstName); 
					ps.setString(2,lastName);
					ps.setString(3,email); 
					ps.setString(4,username);

					ps.executeUpdate(); 
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

		}	
		return user;
	}

	public static User modifyUser(User user, String hashPass) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String username = sanitize(user.getUsername());
		String firstName = sanitize(user.getFirstName());
		String lastName = sanitize(user.getLastName());
		String email = sanitize(user.getEmail());
		Rights rights = user.getRights();

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		if (!userExists(username)) {
			return null;
		}

		else {
			try {

				if (hashPass != null){
					String query =  "UPDATE AnnotationTool.users "
							+ "SET  password = ?,"
							+ "firstname = ?,"
							+ "lastname = ?,"
							+ "email = ?,"
							+ "rights = ?::enumrights"
							+ " WHERE username = ?;";

					ps = con.prepareStatement(query);

					ps.setString(1,hashPass); 
					ps.setString(2,firstName); 
					ps.setString(3,lastName);
					ps.setString(4,email); 
					ps.setString(5, rights == Rights.user ? "user" : "admin");
					ps.setString(6,username);

					ps.executeUpdate(); 
				} else {
					String query =  "UPDATE AnnotationTool.users "
							+ "SET firstname = ?,"
							+ "lastname = ?,"
							+ "email = ?,"
							+ "rights = ?::enumrights"
							+ " WHERE username = ?;";

					ps = con.prepareStatement(query);

					ps.setString(1,firstName); 
					ps.setString(2,lastName);
					ps.setString(3,email); 
					ps.setString(4, rights == Rights.user ? "user" : "admin");
					ps.setString(5,username);

					ps.executeUpdate(); 
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

		}	
		return user;
	}



	public static List<User> getAllUsers(){

		List<User> users = new ArrayList<User>();

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "select * from AnnotationTool.users;";

		try {
			ps = con.prepareStatement(query);

			results = ps.executeQuery();

			while(results.next()){

				User user = new User();

				user.setUsername(results.getString("username"));
				user.setFirstName(results.getString("firstname"));
				user.setLastName(results.getString("lastname"));
				user.setEmail(results.getString("email"));
				user.setRights(results.getString("rights").equals("user") ? Rights.user : Rights.admin);

				users.add(user);

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
		return users;

	}
	
	public static int getCollectionID(String collectionName){
		
		int id = -1;
		PreparedStatement ps = null;
		ResultSet results = null;
		
		String query = "select collection_id from AnnotationTool.collection where name = ?;";

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1,collectionName);
			
			results = ps.executeQuery();

			id = results.getInt("collection_id");
			

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

		return id;
	}
	
	public static List<Integer> getReportIDs(int collectionID){
		
		List<Integer> reportIDs = new ArrayList<Integer>();

		PreparedStatement ps = null;
		ResultSet results = null;
		
		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();
		
		String query = "select * from AnnotationTool.reports_to_collections where collection_id = ?;" ;


		try {
			ps = con.prepareStatement(query);
			
			ps.setInt(1, collectionID);

			results = ps.executeQuery();
			
			while(results.next()){
				reportIDs.add(results.getInt("report_id"));
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
		
		return reportIDs;
		
	}
	
	public static Report getReport(int id){
		Report report = new Report();
		
		String query = "select * from AnnotationTool.reports where reports_pk = ?;" ;

		PreparedStatement ps = null;
		ResultSet results = null;
		
		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();
		
		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,id);

			results = ps.executeQuery();
				
			report.setPk(results.getInt("reports_pk"));
			report.setContent(results.getString("report_content"));
			report.setDate(results.getString("date"));
			report.setHospital_id(results.getInt("hospital_report_id"));
			report.setUploader(results.getString("uploader"));
			
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
		
		
		return report;
	}
	
	public static List<Report> getAllReportsWithoutCollection(){
		//TODO Yannis
		List<Report> reports = new ArrayList<Report>();
		
		return reports;

	}

	public static List<Collection> getAllCollections(){

		List<Collection> collections = new ArrayList<Collection>();

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "select * from AnnotationTool.collection;";

		try {
			ps = con.prepareStatement(query);

			results = ps.executeQuery();

			while(results.next()){

				Collection collection = new Collection();

				collection.setId(results.getInt("collection_id"));
				collection.setName(results.getString("name"));
				collection.setUploader(results.getString("uploader"));
			
				collections.add(collection);

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

		return collections;

	}

	public static void deleteUser(String username){

		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.users WHERE username = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setString(1,sanitize(username));

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

		public static int insertReport(Report report) {

		int id = -1;
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		int hospital_id = report.getHospital_id();
		String uploader = sanitize(report.getUploader());
		String content = sanitize(report.getContent());

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query_report =  "INSERT INTO AnnotationTool.reports (hospital_report_id, uploader, date, "
				+ "report_content) "
				+ "VALUES (?,?,CURRENT_TIMESTAMP,?);";

		try {

			ps = con.prepareStatement(query_report, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1,hospital_id);  
			ps.setString(2,uploader); 
			ps.setString(3,content); 
			
			ps.executeUpdate(); 
			
			rs = ps.getGeneratedKeys();
			
			rs.next();
			
			id = rs.getInt("reports_pk");


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
		
		return id;
	}

	public static int insertCollection(Collection collection) {
		
		int id = -1;

		PreparedStatement ps = null;
		ResultSet rs = null;

		String uploader = sanitize(collection.getUploader());
		String name = sanitize(collection.getName());

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "INSERT INTO AnnotationTool.collection (name, uploader, date) "
				+ "VALUES (?,?,CURRENT_TIMESTAMP);";

		try {

			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1,name); 
			ps.setString(2,uploader); 

			ps.executeUpdate(); 
			
			rs = ps.getGeneratedKeys();
			
			rs.next();
			
			id = rs.getInt(1);

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
		
		return id;
	}
	
	

	public static void insertStandard(Standard standard) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String name = sanitize(standard.getName());
		String json = standard.getJson();

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "INSERT INTO AnnotationTool.standards (standard_name, standard_json) "
				+ "VALUES (?,?);";

		try {

			ps = con.prepareStatement(query);

			ps.setString(1,name);
			ps.setString(2, json);

			ps.executeUpdate(); 

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
	}
	
	//neww
    public static List<Standard> getAllStandards(){

		List<Standard> standards = new ArrayList<Standard>();

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "select * from AnnotationTool.standards order by standard_name asc;";

		try {
			ps = con.prepareStatement(query);

			results = ps.executeQuery();

			while(results.next()){

				Standard standard = new Standard();
				
				standard.setPk(results.getInt("standard_pk"));
				standard.setName(results.getString("standard_name"));
				standard.setJson(results.getString("standard_json"));
			
				standards.add(standard);

			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return standards;

	}


	public static void deleteStandard(String name) {
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.standards"
				+ " WHERE standard_name = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setString(1,sanitize(name));

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}


	public static void modifyStandard(int pk, String name, String json) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		int standard_pk = pk;
		String standard_name = sanitize(name);
		String standard_json = sanitize(json);


		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		
			try {

					String query =  "UPDATE AnnotationTool.standards "
							+ "SET  standard_name = ?,"
							+ "standard_json = ?"
							+ " WHERE standard_pk = ?;";

					ps = con.prepareStatement(query);

					ps.setString(1,standard_name); 
					ps.setString(2,standard_json); 
					ps.setInt(3,standard_pk);

					ps.executeUpdate();

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
	
		
	}
	
//	public static void insertProject(Project project) {
//		
//		//insert into projects
//		insertProjectInProjects(project);
//		//insert into projects users
//		insertProjectInProjectUsers(project);
//		//insert into projects standards
//		insertProjectInProjectStandards(project);
//		//insert into projects standards
//		insertProjectInProjectReports(project);
//		
//	}
	
	public static void insertKeysInReportsCollections(int collection_id, int report_id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "INSERT INTO AnnotationTool.reports_to_collections (collection_id, report_id) "
				+ "VALUES (?,?);";

		try {

			ps = con.prepareStatement(query);

			ps.setInt(1, collection_id);
			ps.setInt(2, report_id);

			ps.executeUpdate(); 

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
	}
	
//	public static void insertProjectInProjectReports(Project project) {
//		for(String collectionname : project.getCollections()) {
//			collectionname = sanitize(collectionname);
//			int collectionid = getCollectionID(collectionname);
//			int projectId = project.getPk();
//			
//			List<Integer> reportids = getReportIDs(collectionid);
//			for(int reportid : reportids) {
//				PreparedStatement ps = null;
//				ResultSet rs = null;
//
//				DBConnection DBCon = new DBConnection();
//				Connection con = DBCon.getCon();
//
//				String query =  "INSERT INTO AnnotationTool.project_reports (project_id, report_id) "
//						+ "VALUES (?,?);";
//
//				try {
//
//					ps = con.prepareStatement(query);
//
//					ps.setInt(1, projectId);
//					ps.setInt(2, reportid);
//
//					ps.executeUpdate(); 
//
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} finally {
//					if (rs != null) {
//						try {
//							rs.close();
//						} catch (SQLException e) { /* ignored */}
//					}
//					if (ps != null) {
//						try {
//							ps.close();
//						} catch (SQLException e) { /* ignored */}
//					}
//					if (con != null) {
//						try {
//							con.close();
//						} catch (SQLException e) { /* ignored */}
//					}
//				}	
//			}
//		}
//		
//		
//		for(String reportname : project.getReports()) {
//			reportname = sanitize(reportname);
//			int reportid = Integer.parseInt(reportname);
//			int projectId = project.getPk();
//			
//			PreparedStatement ps = null;
//			ResultSet rs = null;
//
//			DBConnection DBCon = new DBConnection();
//			Connection con = DBCon.getCon();
//
//			String query =  "INSERT INTO AnnotationTool.project_reports (project_id, report_id) "
//					+ "VALUES (?,?);";
//
//			try {
//
//				ps = con.prepareStatement(query);
//
//				ps.setInt(1, projectId);
//				ps.setInt(2, reportid);
//
//				ps.executeUpdate(); 
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException e) { /* ignored */}
//				}
//				if (ps != null) {
//					try {
//						ps.close();
//					} catch (SQLException e) { /* ignored */}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (SQLException e) { /* ignored */}
//				}
//			}	
//		
//		}
//		
//	}
	
	public static int getStandardKey(String standardname) {
		int id = 0;
		PreparedStatement ps = null;
		ResultSet results = null;
		
		String query = "select standard_pk from AnnotationTool.standards where standard_name = ?;";

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1,standardname);
			
			results = ps.executeQuery();

			id = results.getInt("standard_pk");
			

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

		return id;
	}
	
//	public static void insertProjectInProjectStandards(Project project) {
//		for(String standardname : project.getStandards()) {
//			standardname = sanitize(standardname);
//			int standardkey = getStandardKey(standardname);
//			int projectId = project.getPk();
//			
//			PreparedStatement ps = null;
//			ResultSet rs = null;
//
//			DBConnection DBCon = new DBConnection();
//			Connection con = DBCon.getCon();
//
//			String query =  "INSERT INTO AnnotationTool.project_standards (project_id, standard_id) "
//					+ "VALUES (?,?);";
//
//			try {
//
//				ps = con.prepareStatement(query);
//
//				ps.setInt(1, projectId);
//				ps.setInt(2, standardkey);
//
//				ps.executeUpdate(); 
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException e) { /* ignored */}
//				}
//				if (ps != null) {
//					try {
//						ps.close();
//					} catch (SQLException e) { /* ignored */}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (SQLException e) { /* ignored */}
//				}
//			}	
//		
//		}
//		
//	}
//	
//	public static void insertProjectInProjectUsers(Project project) {
//		for(String username : project.getUsernames()) {
//			username = sanitize(username);
//			User user = getUser(username);
//			
//			int userId = user.getId();
//			int projectId = project.getPk();
//			
//			PreparedStatement ps = null;
//			ResultSet rs = null;
//
//			DBConnection DBCon = new DBConnection();
//			Connection con = DBCon.getCon();
//
//			String query =  "INSERT INTO AnnotationTool.project_users (project_id, user_id) "
//					+ "VALUES (?,?);";
//
//			try {
//
//				ps = con.prepareStatement(query);
//
//				ps.setInt(1, projectId);
//				ps.setInt(2, userId);
//
//				ps.executeUpdate(); 
//
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				if (rs != null) {
//					try {
//						rs.close();
//					} catch (SQLException e) { /* ignored */}
//				}
//				if (ps != null) {
//					try {
//						ps.close();
//					} catch (SQLException e) { /* ignored */}
//				}
//				if (con != null) {
//					try {
//						con.close();
//					} catch (SQLException e) { /* ignored */}
//				}
//			}	
//		
//		}
//		
//	}
//	
//	public static void insertProjectInProjects(Project project) {
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		String projectname = sanitize(project.getName());
//		String creator = sanitize(project.getCreator());
//		boolean finished = project.isFinished();
//
//		DBConnection DBCon = new DBConnection();
//		Connection con = DBCon.getCon();
//
//		String query =  "INSERT INTO AnnotationTool.projects (name, creator, finished) "
//				+ "VALUES (?,?,?);";
//
//		try {
//
//			ps = con.prepareStatement(query);
//
//			ps.setString(1,projectname);
//			ps.setString(2,creator);
//			ps.setBoolean(3, finished);
//
//			ps.executeUpdate(); 
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) { /* ignored */}
//			}
//			if (ps != null) {
//				try {
//					ps.close();
//				} catch (SQLException e) { /* ignored */}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) { /* ignored */}
//			}
//		}
//		
//	}
	
	//chris queries
	public String getReport(){
    	//String username = sanitize();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String report = null;

    	
    	DBConnection DBCon = new DBConnection();
    	Connection con = DBCon.getCon();

    
    		try {
    			
    			ps = con.prepareStatement(  
    				  "select * from reports where hospital_report_id=?");
    			
    			ps.setInt(1,1111);  
    		    	                  
    		    rs = ps.executeQuery();  
    		    
    		    rs.next();	
    		    report = rs.getString("report_content");
    		   
    		   
    		    
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
    		
    		return report;
    		
    }
    
    
    
    
    public String getCategory(){
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String json = null;

    	
    	DBConnection DBCon = new DBConnection();
    	Connection con = DBCon.getCon();

    
    		try {
    			
    			ps = con.prepareStatement(  
    				  "select * from standards where standard_name=?");
    			
    			ps.setString(1,"report1");  
    		    	                  
    		    rs = ps.executeQuery();  
    		    
    		    rs.next();	
    		    json = rs.getString("standard_json");
    		   
    		   
    		    
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
    		
    		return json;
    		
    }

}