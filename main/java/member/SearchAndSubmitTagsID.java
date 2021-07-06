package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import util.ConnectionProvider;

/**
 * 
 * @author Christodoulos
 * This method will take a list of strings in order to return a list of id's
 * After it retrieve the id's from the database the class submit the changes in the database
 * 
 * TESTED IN MINE AND IT WORKS FINE
 *
 */
public class SearchAndSubmitTagsID {

	
	//THIS METHOD IS USED IN THE SERVLET MEMBERACTIONS TO ADD TAGS
	public void searchTags(String[] tags ,String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement submitSearchTags=null;
		PreparedStatement submitEmailTagId=null;
		ResultSet myRes=null;
		List<Integer> listofID = new ArrayList<Integer>();
		
		try{
					String findIDofTag ="SELECT tagid FROM tags WHERE tag =?";
					submitSearchTags = myCon.prepareStatement(findIDofTag);
					String sEmailTags  = "INSERT INTO tagperson (email,tagid) VALUES (?,?)";
					submitEmailTagId = myCon.prepareStatement(sEmailTags);
					
					//The method has a parameter a List of names of tags 
					//So this is just a test list
					
					
					
					//Adding id's for each nameTag
					for (int i=0; i < tags.length;i++){
						submitSearchTags.setString(1,tags[i]);
						myRes = submitSearchTags.executeQuery();
						if (myRes.next()){
							listofID.add(myRes.getInt("tagid"));
						}
					}
					
					//take the real EMAIL FROM Cookies
					submitEmailTagId.setString(1,email);
					//submiting all tags
					if(listofID != null){
						for(int i =0;i<listofID.size();i++){
							submitEmailTagId.setInt(2,listofID.get(i));
							submitEmailTagId.executeUpdate();
						}
					}
					
					}catch(Exception e){ 
						e.printStackTrace();
					}finally{
						if(submitSearchTags != null){
							try {
								submitSearchTags.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}if(submitEmailTagId!=null){
							try {
								submitEmailTagId.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}if(myRes!=null){
							try {
								myRes.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}if(myCon!=null){
							try {
								myCon.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
		
		
	}
	
	
	//THIS EMAIL MUST BE TAKEN FROM COOKIES
	/**
	 * IS USED IN THE JSP OF PROFILE SETTINGS
	 * @param email
	 * @return
	 */
	public HashMap<Integer,String> searchMemberTags(String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement search=null;
		ResultSet tags=null;
		HashMap<Integer,String> tagTable = new HashMap<Integer,String>();
		try{

			
			String searchTags  = "SELECT p.tagid,t.tag FROM tagperson p , tags t WHERE p.tagid = t.tagid AND p.email = ?";
			search = myCon.prepareStatement(searchTags);
			search.setString(1,email);
			tags = search.executeQuery();
			if(tags != null){
				while (tags.next()){
					tagTable.put(tags.getInt("tagid"),tags.getString("tag"));
				}
			}
				System.out.println(tagTable);
				}catch(Exception e){ 
					e.printStackTrace();
				}finally{
					if(search != null){
						try {
							search.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}if(tags!=null){
						try {
							tags.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}if(myCon!=null){
						try {
							myCon.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
		
		return tagTable;
	}
	
	public List<String> getAllTagNames(){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement allTags=null;
		ResultSet result=null;
		
		List<String> tagNames = new ArrayList<String>();
		try {
			
			String getAll = "SELECT tag FROM tags";
			allTags = myCon.prepareStatement(getAll);
		
			result = allTags.executeQuery();
			
			while(result.next()){
				tagNames.add(result.getString("tag"));
			}
		
		System.out.println(tagNames);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(allTags != null){
				try {
					allTags.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(result!=null){
				try {
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(myCon!=null){
				try {
					myCon.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	
	return tagNames; 
	}
	
	public void deleteTags(String[] tags, String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement submitSearchTags=null;
		PreparedStatement submitEmailTagId=null;
		ResultSet myRes=null;
		List<Integer> listofID = new ArrayList<Integer>();
		
		try{
					String findIDofTag ="SELECT tagid FROM tags WHERE tag =?";
					submitSearchTags = myCon.prepareStatement(findIDofTag);
					String sEmailTags  = "DELETE FROM tagperson WHERE email=? AND tagid=?";
					submitEmailTagId = myCon.prepareStatement(sEmailTags);
					
					//The method has a parameter a List of names of tags 
					//So this is just a test list
					
					
					if(tags != null){
					//Adding id's for each nameTag
						for (int i=0; i < tags.length;i++){
							submitSearchTags.setString(1,tags[i]);
							myRes = submitSearchTags.executeQuery();
							if (myRes.next()){
								listofID.add(myRes.getInt("tagid"));
							}
						}
					}
					System.out.println("TAAAGAA +    = "+listofID);

					//take the real EMAIL FROM Cookies
					submitEmailTagId.setString(1,email);
					//submiting all tags
					if(listofID != null){
						for(int i =0;i<listofID.size();i++){
							submitEmailTagId.setString(2,listofID.get(i).toString());
							submitEmailTagId.executeUpdate();
						}
					}
					
					}catch(Exception e){ 
						e.printStackTrace();
					}finally{
						if(submitSearchTags != null){
							try {
								submitSearchTags.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}if(submitEmailTagId!=null){
							try {
								submitEmailTagId.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}if(myRes!=null){
							try {
								myRes.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}if(myCon!=null){
							try {
								myCon.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
		
	}
}
