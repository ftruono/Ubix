package it.ubix.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Vector;



import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Catalogo implements Catalogable<Product,Category> {

	private Connection conn;
	private String webPath;
	
	public Catalogo(String webPath) {
		this.webPath=webPath;
	}
	
	@Override
	public Product[] getProducts() throws SQLException {
		Vector<Product> vect=new Vector<Product>();
		PreparedStatement prep=null;
		conn=DriverManagerConnectionPool.getConnection();
		prep=conn.prepareStatement("select * from prodotto");
		ResultSet rs=prep.executeQuery();
		while(rs.next()) {
			vect.add(makeProduct(rs));
		}
					
		Product f[]=new Product[vect.size()];
		prep.close();
		DriverManagerConnectionPool.releaseConnection(conn);
		return vect.toArray(f);
	}
	
	@Override 
	public Product[] getProductsByType(int type) throws SQLException {
		Vector<Product> vect=new Vector<Product>();
		PreparedStatement prep=null;
		conn=DriverManagerConnectionPool.getConnection();
		prep=conn.prepareStatement("select * from prodotto where type=?");
		prep.setInt(1, type);
		ResultSet rs=prep.executeQuery();
		while(rs.next()) {
			vect.add(makeProduct(rs));
		}
					
		Product f[]=new Product[vect.size()];
		prep.close();
		DriverManagerConnectionPool.releaseConnection(conn);
		return vect.toArray(f);
	}
	
	@Override
	public Product getProductByID(int id) throws SQLException {
		PreparedStatement prep=null;
		
		conn=DriverManagerConnectionPool.getConnection();
		prep=conn.prepareStatement("select * from prodotto where (ID=?)");
		prep.setInt(1, id);
		ResultSet rs=prep.executeQuery();
		rs.next();
		Product prod=makeProduct(rs);			
		prep.close();
		DriverManagerConnectionPool.releaseConnection(conn);
		return prod;
	}
	
	@Override
	public Product[] getProductsByCategory(int cat) throws SQLException {
		Vector<Product> vect=new Vector<Product>();
	    
		conn=DriverManagerConnectionPool.getConnection();
		PreparedStatement prep=null;
		String sql="select * from wrap_prodotto join prodotto on (ID_prod=ID) where (ID_cat=?)";
		prep=conn.prepareStatement(sql);
		prep.setInt(1, cat);
		ResultSet rs=prep.executeQuery();
		while(rs.next()) {
			vect.add(makeProduct(rs));
		}
		Product f[]=new Product[vect.size()];
		prep.close();
		DriverManagerConnectionPool.releaseConnection(conn);
		return vect.toArray(f);
	}

	@Override
	public Product[] getHotProducts() throws SQLException {
		Vector<Product> vect=new Vector<Product>();
		conn=DriverManagerConnectionPool.getConnection();
		PreparedStatement prep=null;
		String sql="select * from prodotto where (highlight=1)";
		prep=conn.prepareStatement(sql);
		ResultSet rs=prep.executeQuery();
		while(rs.next()) {
			vect.add(makeProduct(rs));			
		}
		Product f[]=new Product[vect.size()];
		prep.close();
		DriverManagerConnectionPool.releaseConnection(conn);
		return vect.toArray(f);
		
	}

	
	private Product makeProduct(ResultSet rs) throws SQLException {
		int type=rs.getInt("type");
		Product prod=null;
		if(type==0) 
			 prod=new Film();
		else 
			prod=new Serie();
		 prod.setId(rs.getInt("ID"));
		 prod.setNome(rs.getString("nome"));
		 prod.setCast(rs.getString("cast"));
		 prod.setDescrizione(rs.getString("descrizione"));
		 prod.setImg(rs.getString("preview_img"));
		 prod.setSrc(webPath,rs.getString("src"));
		 prod.setType(rs.getInt("type"));
		 prod.setSellable(rs.getInt("sellable")==1);
		 prod.setHot(rs.getInt("highlight")==1);
		 prod.setPrice(rs.getFloat("price"));
		 return prod;
	}

	@Override
	public Category[] getCategory() throws SQLException {
		Vector<Category> vect=new Vector<Category>();
		conn=DriverManagerConnectionPool.getConnection();
		PreparedStatement prep=null;
		String sql="select * from category";
		prep=conn.prepareStatement(sql);
		ResultSet rs=prep.executeQuery();
		while(rs.next()) {
			vect.add(new Category(rs.getInt("ID"),rs.getString("nome_categoria")));			
		}
		
		prep.close();
		DriverManagerConnectionPool.releaseConnection(conn);
		return vect.toArray(new Category[vect.size()]);
		
	}

	@Override
	public Product[] getProductsByCategoryAndType(int cat, int type) throws SQLException {
		Vector<Product> vect=new Vector<Product>();
		conn=DriverManagerConnectionPool.getConnection();
		PreparedStatement prep=null;
		String sql="select * from wrap_prodotto join prodotto on (ID_prod=ID) where (ID_cat=? and type=?)";
		prep=conn.prepareStatement(sql);
		prep.setInt(1, cat);
		prep.setInt(2, type);
		ResultSet rs=prep.executeQuery();
		while(rs.next()) 
			vect.add(makeProduct(rs));			
		
		prep.close();
		DriverManagerConnectionPool.releaseConnection(conn);
		return vect.toArray(new Product[vect.size()]);
	}

	@Override
	public Product[] getProductsByName(String name) throws SQLException {
		Vector<Product> vect=new Vector<Product>();
		conn=DriverManagerConnectionPool.getConnection();
		PreparedStatement prep=null;
		String sql="select * from prodotto where(nome like ?)";
		prep=conn.prepareStatement(sql);
		prep.setString(1, "%" + name + "%");
		ResultSet rs=prep.executeQuery();
		while(rs.next()) 
			vect.add(makeProduct(rs));			
		
		prep.close();
		DriverManagerConnectionPool.releaseConnection(conn);
		return vect.toArray(new Product[vect.size()]);
	}

	@Override
	public void addProduct(Product product) throws SQLException {
		String sql="INSERT INTO prodotto(nome,cast,descrizione,preview_img,src,type,sellable,highlight,price) values("
				+ "?,?,?,?,?,?,?,?,?)";
		conn=DriverManagerConnectionPool.getConnection();
		PreparedStatement prep=null;
		prep=conn.prepareStatement(sql);
		prep.setString(1,product.getNome());
		prep.setString(2,product.getCast());
		prep.setString(3,product.getDescrizione());
		prep.setString(4, product.getImg());
		if(product.getType()==1) {
			Serie s=(Serie)product;
			prep.setString(5,s.getRelativeSource());
			
		}
        prep.setInt(6, product.getType());
        prep.setInt(7, product.isSellable()==true ? 1:0);
        prep.setInt(8, product.isHot()==true ? 1:0);
        prep.setFloat(9, product.getPrice());
		prep.executeUpdate();
	    conn.commit();
		prep.close();
		DriverManagerConnectionPool.releaseConnection(conn);
		
		
		
	}

	
	
	

}
