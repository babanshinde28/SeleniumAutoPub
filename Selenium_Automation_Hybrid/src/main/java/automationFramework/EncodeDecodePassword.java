package automationFramework;

import java.util.*;

public class EncodeDecodePassword {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String password="";
		System.out.println("Original Password :"+password);
		String encodedPassword=Base64.getEncoder().encodeToString(password.getBytes());
		System.out.println("Encoded Password is:"+encodedPassword);
		
		System.out.println("Decoding Password Process Started For:"+encodedPassword);
		byte[] decodedBytes=Base64.getDecoder().decode(encodedPassword);
		String decodedPassword=new String(decodedBytes);
		System.out.println("Decoded Password is:"+decodedPassword);
		
	}

}
