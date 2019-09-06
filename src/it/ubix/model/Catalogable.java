package it.ubix.model;

import java.sql.SQLException;
import java.util.List;

public interface Catalogable<T extends Product , V extends Category> {
 
	T[] getProducts() throws SQLException;
	T[] getProductsByType(int type) throws SQLException; 
	T getProductByID(int id) throws SQLException;
    T[] getProductsByCategory(int cat) throws SQLException;
    T[] getHotProducts() throws SQLException;
    T[] getProductsByCategoryAndType(int cat,int type) throws SQLException;
    T[] getProductsByName(String name) throws SQLException;
    V[] getCategory() throws SQLException; 
   
    
    /*admin function*/
    void addProduct(T product) throws SQLException;
    
   
    
     /* admin function 
    void addProducts(List<? extends Product> prodotti);
    void deleteProductByID(int id);
    void editProductByID(int id,T edited);
    
    void addCategory(V e);
    void deleteCategoryByID(int id);
    void editCategoryByID(int id, V e);
	*/
}
