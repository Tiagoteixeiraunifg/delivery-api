package com.delivery.tiago.domain.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class BcryptUtil {
	
	private BcryptUtil() {}
	


	public static String getHash(String password) {
		
		if(password == null) {
			return null;
		}
		
		if(BcryptUtil.isEncrypted(password)) {
			return password;
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	

	public static boolean isEncrypted(String password) {
		return password.startsWith("$2a$");
	}
	

	public static String decode(String password, String encrypted) throws Exception {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		boolean isPasswordMatches = bcrypt.matches(password, encrypted);
		
		if(!isPasswordMatches)
			throw new Exception("Password does not match.");
		
		return password;
	}

}
