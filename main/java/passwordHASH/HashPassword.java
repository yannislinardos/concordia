package passwordHASH;
/*
 * USED BY SERVLET SignUpNew
 */
public class HashPassword {

	String password;
	
	public int hashCode(String password){
		int hash = 0;
		for(int i=0;i<password.length(); i++){
			hash = hash * 31 + password.charAt(i);
		}
		return hash;
	}
	
}
