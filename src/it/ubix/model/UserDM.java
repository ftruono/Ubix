package it.ubix.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.tomcat.util.buf.UDecoder;

import it.ubix.support.Security;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDM implements Utentable {
	private Connection conn;

	
	@Override
	public Utente[] getAllUser() throws SQLException {
		Statement stmt;
		Utente[] ute = null;
		
	    Vector<Utente> vect=new Vector<Utente>();
		conn=DriverManagerConnectionPool.getConnection();
		PreparedStatement prep=null;
		String sql="SELECT * FROM utenti inner join dati_anagrafici on(utenti.codice_fiscale=dati_anagrafici.codice_fiscale)";
		prep=conn.prepareStatement(sql);
		ResultSet rs=prep.executeQuery();
		while(rs.next()) {
			vect.add(new Utente(rs.getInt("idutenti"), rs.getString("user"),rs.getString("password") , rs.getInt("lvl"), rs.getString("codice_fiscale"),rs.getString("lpayment") ,rs.getString("nome") , rs.getString("cognome"),rs.getString("cap") ,rs.getString("citta") ,rs.getString("via") ,rs.getString("telefono") ,rs.getString("email") ,rs.getString("carta_di_credito")));		
		}
			
		prep.close();
		DriverManagerConnectionPool.releaseConnection(conn);
        ute=new Utente[vect.size()];
        ute=(Utente[])vect.toArray(ute);
		return  ute;
	}

	@Override
	public Utente getUserById(int id) {
		String sql="SELECT * FROM ubix.utenti inner join dati_anagrafici on(utenti.codice_fiscale=dati_anagrafici.codice_fiscale)"
				+ "where idutenti=?";
		PreparedStatement preparedStatament=null;
		Utente ute=new Utente();
		try {
			conn=DriverManagerConnectionPool.getConnection();
			preparedStatament=conn.prepareStatement(sql);
			preparedStatament.setInt(1, id);
			ResultSet rs = preparedStatament.executeQuery();
			while(rs.next()) {
				ute.setCodice_fiscale(rs.getString("codice_fiscale"));
				ute.setIdutente(rs.getInt("idutenti"));
				ute.setLpayment(rs.getString("lpayment"));
				ute.setLvl(rs.getInt("lvl"));
				ute.setPassword(rs.getString("password"));
				ute.setUser(rs.getString("user"));
			}
			preparedStatament.close();
			DriverManagerConnectionPool.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ute;
	}

	@Override 
	public void editUserByID(int id,Utente t) throws SQLException {		
		
		String sql="Update utenti,dati_anagrafici set email=?,password=?,dati_anagrafici.telefono=?,dati_anagrafici.carta_di_credito=?,"
				+ "lvl=? where(idutenti=? and dati_anagrafici.codice_fiscale=utenti.codice_fiscale)";	
		conn=DriverManagerConnectionPool.getConnection();
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1,t.getEmail());
		ps.setString(2, t.getPassword());
		ps.setString(3, t.getCell());
	    ps.setString(4, t.getCredit());
	    ps.setInt(5,t.getLvl());
	    ps.setInt(6, t.getIdutente());
		ps.executeUpdate();
		conn.commit();
		ps.close();
		DriverManagerConnectionPool.releaseConnection(conn);
			
			

		
	}

	@Override
	public int deleteUserById(int id) {
		int rs=0;
		String deleteSQL = "DELETE FROM ubix.utenti WHERE CODE = ?";
		PreparedStatement preparedStatement;
		try {
			conn=DriverManagerConnectionPool.getConnection();
			preparedStatement = conn.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id);
			rs=preparedStatement.executeUpdate(deleteSQL);
			preparedStatement.close();
			DriverManagerConnectionPool.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public int deleteAllUser() {
		Statement stmt;
		int rs = 10;
		String delete="delete * from Ubix.utenti ";
		try {
			conn=DriverManagerConnectionPool.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeUpdate(delete);
			stmt.close();
			DriverManagerConnectionPool.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
		
		
	}

	@Override
	public String createUser(Utente t) {
		String[] sql=  {"INSERT INTO utenti(user,password,email,lvl,codice_fiscale) values(?,?,?,?,?)",
				       "INSERT INTO dati_anagrafici values(?,?,?,?,?,?,?,?)"};
		
		int i=0;
		try {
			conn=DriverManagerConnectionPool.getConnection();
		while(i<sql.length) {
			PreparedStatement prep=conn.prepareStatement(sql[i]);
			if(i==0) { 
				prep.setString(1, t.getUser());
				prep.setString(2, t.getPassword());
				prep.setString(3, t.getEmail());
				prep.setInt(4, t.getLvl());
				prep.setString(5, t.getCodice_fiscale());
				int value=prep.executeUpdate();
				conn.commit();
				prep.close();
		    }
			else {
				prep.setString(1, t.getCodice_fiscale());
				prep.setString(2, t.getName());
				prep.setString(3, t.getSurname());
				prep.setString(4, t.getCap());
				prep.setString(5, t.getCity());
				prep.setString(6, t.getVia());
				prep.setString(7, t.getCell());
				prep.setString(8, t.getCredit());
				prep.executeUpdate();
				conn.commit();
				prep.close();
			}
			i++;
		 }
			DriverManagerConnectionPool.releaseConnection(conn);
			
			
		} catch (SQLException e) {
			return e.getMessage();
		}
		
		return "Utente Creato con successo";
	}

	@Override
	public Utente login(String user, String password) {
		String loginSQL= "SELECT * FROM Ubix.utenti inner join dati_anagrafici on(utenti.codice_fiscale=dati_anagrafici.codice_fiscale) Where ((user=? || email=?) && password=?);";
		PreparedStatement preparedStatement;
			try {
				Utente t=null;
				conn=DriverManagerConnectionPool.getConnection();
				preparedStatement = conn.prepareStatement(loginSQL);
				preparedStatement.setString(1, user);
				preparedStatement.setString(2, user);
				preparedStatement.setString(3, password);
				ResultSet rs=preparedStatement.executeQuery();
			   if(rs.next())
				   t=new Utente(rs.getInt("idutenti"),rs.getString("user"),rs.getString("password"),rs.getInt("lvl"),rs.getString("codice_fiscale"),
						   rs.getString("lpayment"),rs.getString("nome"),rs.getString("cognome"),rs.getString("cap"),rs.getString("citta"),rs.getString("via"),rs.getString("telefono"),
						   rs.getString("email"),rs.getString("carta_di_credito"));
				   
			   
			   rs.close();
			   DriverManagerConnectionPool.releaseConnection(conn);
			   return t;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return null;
	}
	

}
