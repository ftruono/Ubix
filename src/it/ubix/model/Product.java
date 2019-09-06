package it.ubix.model;

import java.io.Serializable;
import java.util.List;


public abstract class Product implements Serializable,Cloneable{
 
	private String nome,descrizione,cast,img;
	private int type,id; //0 : film - 1: serie tv
	private boolean sellable,hot;
	private float price;
	
	public abstract <T> T getSrc();
	public abstract <T> void setSrc(String path,String src);
	

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCast() {
		return cast;
	}
	public void setCast(String cast) {
		this.cast = cast;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isSellable() {
		return sellable;
	}
	public void setSellable(boolean sellable) {
		this.sellable = sellable;
	}
	public boolean isHot() {
		return hot;
	}
	public void setHot(boolean hot) {
		this.hot = hot;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean equals(Object o) {
		if (o==null) return false;
		if(o.getClass().equals(this.getClass())) {
			Product temp=(Product)o;
			return temp.id==this.id;
		}
		return false;
		
	}
	
	public Product clone() {
		Product v1;
		try {
			v1 = (Product) super.clone();
			return v1;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
 

}
