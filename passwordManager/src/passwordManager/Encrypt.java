package passwordManager;

public class Encrypt {
	  public static String encrypt(String s) {
	        StringBuilder encrypted = new StringBuilder();
	        int key1 = 3; // First shift key
	        int key2 = 5; // Second shift key

	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);
	            if (i % 2 == 0) {
	                c += key1; // Apply first key shift for even index
	            } else {
	                c += key2; // Apply second key shift for odd index
	            }
	            encrypted.append(c);
	        }

	        // Append the keys to the end of the encrypted string
	        encrypted.append((char) ('A' + key1));
	        encrypted.append((char) ('A' + key2));

	        return encrypted.toString();
	    }

	    // Decrypt a string using an alternating Caesar cipher
	    public static String decrypt(String s) {
	        // Extract the keys from the last two characters
	        int key1 = s.charAt(s.length() - 2) - 'A';
	        int key2 = s.charAt(s.length() - 1) - 'A';

	        // Remove the last two characters (keys)
	        s = s.substring(0, s.length() - 2);

	        StringBuilder decrypted = new StringBuilder();

	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);
	            if (i % 2 == 0) {
	                c -= key1; // Revert first key shift for even index
	            } else {
	                c -= key2; // Revert second key shift for odd index
	            }
	            decrypted.append(c);
	        }

	        return decrypted.toString();
	    }
}
