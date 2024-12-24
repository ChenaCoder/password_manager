package passwordManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Hash {
	public String hashString(String s) {
		//use messagedigest default sha-256 hashing function
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(s.getBytes());
			byte[] digest = md.digest();
			//convert array of hashed bytes back into string
			StringBuffer sb = new StringBuffer();
			for(byte b:digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		return s;
		
	}

}
