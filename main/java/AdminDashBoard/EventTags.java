package AdminDashBoard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnectionProvider;

//import postgreConn.DataGripConnection;

public class EventTags {

		
		public void searchEventTags(String[] alltags,int changedid){
				ConnectionProvider provider = new ConnectionProvider();
				Connection myCon = provider.getCon();
				//list which saves the id's of each tagName
				List<Integer> listofID = new ArrayList<Integer>();
				PreparedStatement submitSearchTags = null;
				PreparedStatement submitEventIdTagId = null;
				ResultSet myRes=null;
				try{
							
							//Connection myCon = DataGripConnection.getCon();
							
							//tag is the name of tags
							String findIDofTag ="SELECT tagid FROM tags WHERE tag =?";
							 submitSearchTags = myCon.prepareStatement(findIDofTag);
							String sEventIdTags  = "INSERT INTO tagevent (eventid,tagid) VALUES (?,?)";
							 submitEventIdTagId = myCon.prepareStatement(sEventIdTags);
							
														
							//Adding id's for each nameTag in A list called listofID
							for (int i=0; i < alltags.length;i++){
								submitSearchTags.setString(1,alltags[i]);
								 myRes = submitSearchTags.executeQuery();
								if (myRes.next()){
									listofID.add(myRes.getInt("tagid"));
								}
							}
							
							submitEventIdTagId.setInt(1,changedid);
							//submiting all EventId and TagId
							if(listofID != null){
								for(int i =0;i<listofID.size();i++){
									submitEventIdTagId.setInt(2,listofID.get(i));
									submitEventIdTagId.executeUpdate();
								}
							}
							
							}catch(Exception e){ 
								e.printStackTrace();
							}finally{
								if(submitSearchTags!=null){
									try {
										submitSearchTags.close();
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}if(myRes!=null){
									try {
										myRes.close();
									} catch (SQLException e) {
										e.printStackTrace();
								}
								}if(submitEventIdTagId!=null){
									try {
										submitEventIdTagId.close();
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


