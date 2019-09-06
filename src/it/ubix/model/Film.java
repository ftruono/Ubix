package it.ubix.model;

public class Film extends Product {


	
    private String src;
	@Override
	public String getSrc() {
			return src;
	
	}

	@Override
	public <T> void setSrc(String path,String src) {
		this.src= src.toString();
		
	}
	

	
}
