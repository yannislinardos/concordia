package detailsFormat;

public class FormatEmail {
			
			//This method does not allow EMPTY input
		   public boolean emailFormat(String email) {
			     
			   String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			   Boolean b = email.matches(EMAIL_REGEX);
			   
		      return b;
		   }
		
}
