package it.ubix.support;

import java.util.regex.Pattern;

public class Utility {

	public static boolean validName(String name) {
      if(name.equals("") || name.matches(".*\\d+.*")) {
    	  return false;
      }
	  return true;	
	}
	
	
	public static boolean validPsw(String psw) {
		boolean upper,special;
		special=upper=false;
		if(psw.equals("") && psw.length()<6)
		   return false;
		for(char c: psw.toCharArray()) {
			if(Character.isUpperCase(c)) {
				upper=true;
			}
			if((c>=33 && c<=47) || (c>=58 && c<65))
				special=true;
		}		
		return special && upper;
	}
	
	
	public static boolean validEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
                 
	    Pattern pat = Pattern.compile(emailRegex);
		if (email.equals(""))
		  return false;
	    
        return pat.matcher(email).matches();
		  
	}
	
	public static boolean validCreditCard(String card) {
		//non sono implementati ulteriori controlli , inoltre si suppone che preautorizzazione e pagamento vadano sempre a buon fine.
	    if(card.length()>=14 && card.length()<=16 && card.matches("[0-9]+"))
		   return true;
	    
	    return false;
	}
	
	public static boolean validCF(String cf) {
		if(cf.equals("")  || !cf.matches("[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]"))
		   return false;
	   return true;
	}
	
	public static boolean validCAP(String cap) {
		if(cap.equals("") || !cap.matches("[0-9]+") || cap.length()>5)
			return false;
	    return true;	
	
		
	}
	
	
	public static boolean validCity(String city) {
		if(city.equals("") || city.matches(".*\\d+.*") || city.length()<3 )
		  return false;
		return true;
	}
	
	public static boolean validRow(String row) {
		if(row.equals("") || row.length()<3)
			return false;
		return true;
	}
	
	public static boolean validTel(String cell) {
		if(cell.equals("") || !cell.matches("[0-9]+"))
			return false;
		return true;
	}
	
}
