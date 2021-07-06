package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.*;

public class MemberQueries {
	
	 static Password passwordCheck = new Password();

	public static boolean checkOldPassword(String pass,String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement verifyEmail =null;
		ResultSet savedPass =null;
		String correctHash;
		boolean answer = false;
		try {

			//myCon.setAutoCommit(false);
			System.out.println(myCon);
			//String for preparing the insert queries
			String sEmail  = "SELECT hashpass FROM member where email=?";
			
			
			 verifyEmail = myCon.prepareStatement(sEmail);
			System.out.println("in check Pass checking email :" +email);
			 verifyEmail.setString(1, Queries.sanitize(email));
				
			 savedPass = verifyEmail.executeQuery();
			
			
			if (savedPass.next()){
				correctHash = savedPass.getString("hashpass");
				if(Password.checkPassword(pass, correctHash)){
					answer = true;
				}
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(verifyEmail != null){
					try {
						verifyEmail.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(savedPass!=null){
					try {
						savedPass.close();
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
		return answer;
		
	}
	public static void sumbitNewPasswords(String email,String newPass){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement submit =null;

		try {

			//myCon.setAutoCommit(false);
			System.out.println(myCon);
			//String for preparing the insert queries
			String submitNewPass  = "UPDATE member SET hashpass=? WHERE email=?";
			
			
			 submit = myCon.prepareStatement(submitNewPass);

			submit.setString(1, Password.hashCode(newPass));
			submit.setString(2, Queries.sanitize(email));
			
			submit.executeUpdate();
		
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(submit != null){
					try {
						submit.close();
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
	public void doUpdateName(String name,String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement checkname =null;
		ResultSet savedname = null;
		PreparedStatement submit=null;
		try {
			String oldname=null;
			//myCon.setAutoCommit(false);
			System.out.println(myCon);
			//String for preparing the insert queries
			 checkname = myCon.prepareStatement("SELECT name FROM person WHERE email =?");
			checkname.setString(1,Queries.sanitize(email));
			 savedname = checkname.executeQuery();
			if (savedname.next()){
				oldname=savedname.getString("name");
			}
			
			
			if(!name.equals(oldname)){
			String submitNewPass  = "UPDATE person SET name=? WHERE email=?";
			 submit = myCon.prepareStatement(submitNewPass);

			submit.setString(1, Queries.sanitize(name));
			submit.setString(2, Queries.sanitize(email));
			
			submit.executeUpdate();
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(checkname != null){
					try {
						checkname.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(savedname!=null){
					try {
						savedname.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(submit!=null){
					try {
						submit.close();
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
	public void doUpdateSurname(String surname,String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement checksurname =null;
		ResultSet savedsurname = null;
		PreparedStatement submit=null;
		try {
			String oldsurname=null;
			//myCon.setAutoCommit(false);
			System.out.println(myCon);
			//String for preparing the insert queries
			 checksurname = myCon.prepareStatement("SELECT surname FROM person WHERE email =?");
			checksurname.setString(1,Queries.sanitize(email));
			 savedsurname = checksurname.executeQuery();
			if (savedsurname.next()){
				oldsurname=savedsurname.getString("surname");
			}
			
			
			if(!surname.equals(oldsurname)){
			//myCon.setAutoCommit(false);
			System.out.println(myCon);
			//String for preparing the insert queries
			String submitNewPass  = "UPDATE person SET surname=? WHERE email=?";
			
			
			 submit = myCon.prepareStatement(submitNewPass);

			submit.setString(1, Queries.sanitize(surname));
			submit.setString(2, Queries.sanitize(email));
			
			submit.executeUpdate();
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(checksurname != null){
					try {
						checksurname.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(savedsurname!=null){
					try {
						savedsurname.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(submit!=null){
					try {
						submit.close();
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
	public void doUpdateCity(String city,String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement checkcity =null;
		ResultSet savedcity = null;
		PreparedStatement submit =null;
		PreparedStatement insertsubmit =null;
		try {
			String oldcity=null;
			//myCon.setAutoCommit(false);
			System.out.println(myCon);
			//String for preparing the insert queries
			checkcity = myCon.prepareStatement("SELECT city FROM userr WHERE email =?");
			checkcity.setString(1,Queries.sanitize(email));
			 savedcity = checkcity.executeQuery();
			if (savedcity.next()){
				oldcity=savedcity.getString("city");
				if(!city.equals(oldcity)){
					//myCon.setAutoCommit(false);
					System.out.println(myCon);
					//String for preparing the insert queries
					String submitNewCity = "UPDATE userr SET city=? WHERE email=?";
					
					
					submit = myCon.prepareStatement(submitNewCity);

					submit.setString(1, Queries.sanitize(city));
					submit.setString(2, Queries.sanitize(email));
					
					submit.executeUpdate();
					}
			}else{
				String insertNewCity= "INSERT INTO userr (email,city) VALUES (?,?)";
				insertsubmit = myCon.prepareStatement(insertNewCity);
				insertsubmit.setString(1, Queries.sanitize(email));
				insertsubmit.setString(2, Queries.sanitize(city));
				insertsubmit.executeUpdate();
			}
			
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(checkcity != null){
					try {
						checkcity.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(insertsubmit != null){
					try {
						insertsubmit.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(savedcity!=null){
					try {
						savedcity.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(submit!=null){
					try {
						submit.close();
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
	public void doUpdateCountry(String country,String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement checkcountry =null;
		ResultSet savedcountry = null;
		PreparedStatement submit =null;
		PreparedStatement insertsubmit =null;
		try {
			String oldcountry=null;
			//myCon.setAutoCommit(false);
			System.out.println(myCon);
			//String for preparing the insert queries
			checkcountry = myCon.prepareStatement("SELECT country FROM userr WHERE email =?");
			checkcountry.setString(1,Queries.sanitize(email));
			savedcountry = checkcountry.executeQuery();
			if (savedcountry.next()){
				oldcountry=savedcountry.getString("country");
				if(!country.equals(oldcountry)){
					//myCon.setAutoCommit(false);
					System.out.println(myCon);
					//String for preparing the insert queries
					String submitNewCountry = "UPDATE userr SET country=? WHERE email=?";
					
					
					 submit = myCon.prepareStatement(submitNewCountry);

					submit.setString(1, Queries.sanitize(country));
					submit.setString(2, Queries.sanitize(email));
					
					submit.executeUpdate();
					}
			}else{
				String insertNewCountry= "INSERT INTO userr (email,country) VALUES (?,?)";
				insertsubmit = myCon.prepareStatement(insertNewCountry);
				insertsubmit.setString(1, Queries.sanitize(email));
				insertsubmit.setString(2, Queries.sanitize(country));
				insertsubmit.executeUpdate();
			}
			
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(checkcountry != null){
					try {
						checkcountry.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(insertsubmit != null){
					try {
						insertsubmit.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(savedcountry!=null){
					try {
						savedcountry.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(submit!=null){
					try {
						submit.close();
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
	public void doUpdateMobilePhone(String number,String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement checknumber =null;
		ResultSet savednumber = null;
		PreparedStatement submit =null;
		PreparedStatement insertsubmit =null;

		try {
			String oldnumber=null;
			//myCon.setAutoCommit(false);
			System.out.println(myCon);
			//String for preparing the insert queries
			 checknumber = myCon.prepareStatement("SELECT phone_number FROM userr WHERE email =?");
			checknumber.setString(1,Queries.sanitize(email));
			 savednumber = checknumber.executeQuery();
			if (savednumber.next()){
				oldnumber=savednumber.getString("phone_number");
				if(!number.equals(oldnumber)){
					//myCon.setAutoCommit(false);
					System.out.println(myCon);
					//String for preparing the insert queries
					String submitNewNumber = "UPDATE userr SET phone_number=? WHERE email=?";
					
					
					 submit = myCon.prepareStatement(submitNewNumber);

					submit.setString(1, Queries.sanitize(number));
					submit.setString(2, Queries.sanitize(email));
					
					submit.executeUpdate();
					}
			}else{
				String insertNewCountry= "INSERT INTO userr (email,phone_number) VALUES (?,?)";
				insertsubmit = myCon.prepareStatement(insertNewCountry);
				insertsubmit.setString(1, Queries.sanitize(email));
				insertsubmit.setString(2, Queries.sanitize(number));
				insertsubmit.executeUpdate();
			}
			
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(checknumber != null){
					try {
						checknumber.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(insertsubmit != null){
					try {
						insertsubmit.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(savednumber!=null){
					try {
						savednumber.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(submit!=null){
					try {
						submit.close();
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
	public void doUpdateBirthday(String birthday,String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement checkbirthday =null;
		ResultSet savedbirthday = null;
		PreparedStatement submit =null;
		PreparedStatement insertsubmit =null;

		try {
			String oldbirthday=null;
			//myCon.setAutoCommit(false);
			System.out.println(myCon);
			//String for preparing the insert queries
			checkbirthday = myCon.prepareStatement("SELECT birthday FROM userr WHERE email =?");
			checkbirthday.setString(1,Queries.sanitize(email));
			savedbirthday = checkbirthday.executeQuery();
			if (savedbirthday.next()){
				oldbirthday=savedbirthday.getString("birthday");
				if(!birthday.equals(oldbirthday)){
					//myCon.setAutoCommit(false);
					System.out.println(myCon);
					//String for preparing the insert queries
					String submitNewBirthday = "UPDATE userr SET birthday=?::DATE WHERE email=?";
					
					
					submit = myCon.prepareStatement(submitNewBirthday);

					submit.setString(1,Queries.sanitize(birthday));
					submit.setString(2, Queries.sanitize(email));
					
					submit.executeUpdate();
					}
			}else{
				String insertNewBirthday= "INSERT INTO userr (email,birthday) VALUES (?,?::DATE)";
				insertsubmit = myCon.prepareStatement(insertNewBirthday);
				insertsubmit.setString(1, Queries.sanitize(email));
				insertsubmit.setString(2, Queries.sanitize(birthday));
				insertsubmit.executeUpdate();
			}
			
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(checkbirthday != null){
					try {
						checkbirthday.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(insertsubmit != null){
					try {
						insertsubmit.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(savedbirthday!=null){
					try {
						savedbirthday.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(submit!=null){
					try {
						submit.close();
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
	
	public static List<String> getAllDetails(String email){
		ConnectionProvider provider = new ConnectionProvider();
		Connection myCon = provider.getCon();
		PreparedStatement alldetails =null;
		PreparedStatement somedetails =null;
		ResultSet result2 = null;
		ResultSet result1 = null;

		List<String> all = new ArrayList<String>();
		
		try {
			System.out.println("in memberQueries in method getAllDetails");
			
			somedetails =myCon.prepareStatement("SELECT p,email,p.name,p.surname FROM person p WHERE email =? ");
			somedetails.setString(1, Queries.sanitize(email));
			result1 = somedetails.executeQuery();
			System.out.println("in member  after first query!!!");
			alldetails = myCon.prepareStatement("SELECT u.email , u.birthday , u.city , u.country , u.phone_number , p.name,p.surname FROM person p ,userr u WHERE p.email = ? AND u.email = ?");
			alldetails.setString(1,Queries.sanitize(email));
			alldetails.setString(2, Queries.sanitize(email));
			result2 = alldetails.executeQuery();
			
			
				if(result2.next()){
				
					all.add(result2.getString("email"));
					all.add(result2.getString("birthday"));
					all.add(result2.getString("city"));
					all.add(result2.getString("country"));
					all.add(result2.getString("phone_number"));
					all.add(result2.getString("name"));
					all.add(result2.getString("surname"));
				}else if (result1.next()){
				all.add(result1.getString("email"));
				all.add(result1.getString("name"));
				all.add(result1.getString("surname"));
			}
			for(int i=0;i<all.size();i++){
				System.out.println("list all details : "+all.get(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(alldetails != null){
				try {
					alldetails.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(somedetails != null){
				try {
					somedetails.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(result1!=null){
				try {
					result1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(result2!=null){
				try {
					result1.close();
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
		return all;
	}
	
}
