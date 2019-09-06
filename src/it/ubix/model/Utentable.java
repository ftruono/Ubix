package it.ubix.model;

import java.sql.SQLException;

public interface Utentable  {
	
	public String createUser(Utente t);
	public Utente login(String user,String password);
	
	/*ADIMN*/
	
	public void editUserByID(int id,Utente t) throws SQLException ;
	public int deleteUserById(int id);
	public int deleteAllUser();
	public Utente[] getAllUser() throws SQLException;
	public Utente getUserById(int id);
	

}
