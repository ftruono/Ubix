package it.ubix.support;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
   
   public static String hashMD5(String psw) {
		
		MessageDigest md=null;
		try {
			md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(psw.getBytes());
		    StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < array.length; ++i) {
		       sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		    }
		   return sb.toString();
		} catch (NoSuchAlgorithmException e1) {
			
			e1.printStackTrace();
		}		
		
		return null;
	}
	
}
