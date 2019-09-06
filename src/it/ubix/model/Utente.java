package it.ubix.model;

import java.io.Serializable;

import it.ubix.support.Security;

public class Utente implements Serializable,Cloneable{
	private int idutente,lvl;
	private String user, password,codice_fiscale, lpayment,name,surname,cap,city,via,cell,email,credit;
	
	public Utente() {
		
	}
	
	//la password viene passata già hashata da md5
	public Utente(String user,String psw,String code,String lpay,String name,String surname,
			String cap,String city,String via,String cell,String email,String credit_card,int lvl) {
		setLvl(lvl);
		idutente=-1;
		setUser(user);
		setPassword(psw);
		setCodice_fiscale(code);
		setLpayment(lpay);
		setName(name);
		setSurname(surname);
		setCap(cap);
		setCity(city);
		setVia(via);
		setCell(cell);
		setEmail(email);
		setCredit(credit_card);		
	}
	
	public Utente(int id,String user,String psw,int lvl,String code,String lpay,String name,String surname,
			String cap,String city,String via,String cell,String email,String credit_card) {

		this(user,psw,code,lpay,name,surname,cap,city,via,cell,email,credit_card,lvl);
		setIdutente(id);
		
		
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public int getIdutente() {
		return idutente;
	}
	public void setIdutente(int idutente) {
		this.idutente = idutente;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}
	public String getLpayment() {
		return lpayment;
	}
	public void setLpayment(String lpayment) {
		this.lpayment = lpayment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public Utente clone() {
		try {
			Utente clone=(Utente)super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	

	
}
