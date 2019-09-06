package it.ubix.model;

import java.util.HashMap;

public class Cart {

	private HashMap<Product,Integer> map;
	
	
	public Cart() {
		map=new HashMap<>();	
		
	}
	
	public synchronized void add(Product t) {
		Product temp=t.clone();
		Product v=contains(temp);
		if(v!=null) {
		   int value=map.get(v);	
		   map.put(v, ++value);
		}else 
		   map.put(temp,1);
		
	}
	
	public synchronized void edit(Product t,int value) {
		Product v=null;
		v=contains(t.clone());
		if(v!=null) {
		  map.put(v, value);	
		}
			
		
		
	}
	public synchronized void clear() {
		map.clear();  
	}
	public synchronized void remove(Product t) {
		Product v=null;
		v=contains(t.clone());
		if(v!=null)  
			map.remove(v);
		
	}
	
	public synchronized HashMap<Product,Integer> getMapCart(){
		return map;
	}
	
	public synchronized int getLength() {
		return map.size();
	}
	
	private Product contains(Product t) {
		
		for(Product v : map.keySet()) {
			if(v.equals(t))
				return v;
		}
		return null;
	}
	
	
		
}
