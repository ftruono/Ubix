package it.ubix.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.ubix.model.Cart;
import it.ubix.model.Catalogo;
import it.ubix.model.Product;

import it.ubix.model.UserDM;
import it.ubix.model.Utente;
import it.ubix.support.XmlFormat;

import org.json.JSONException;
import org.json.JSONObject;
public class Processing {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext context;
	private Catalogo cat;
	private Cart cart;
	public Processing(ServletContext context,HttpServletRequest req, HttpServletResponse resp) {
		this.context=context;
		request=req; //riferimento allo stesso oggetto della richiesta
		response=resp;
		cat=new Catalogo(context.getRealPath(""));
		cart=(Cart) request.getSession().getAttribute("cart");
		if(cart==null) {
			cart=new Cart();
		}

	}

	private void removeAttributes() {
		request.getSession().removeAttribute("category");
		request.getSession().removeAttribute("prodotti");
		request.getSession().removeAttribute("hot");
		request.getSession().removeAttribute("users");
      
	}


	private void route(String page) throws ServletException, IOException {
		RequestDispatcher disp=context.getRequestDispatcher("//" + page);
		disp.forward(request, response);
	}

   private boolean isConnected() {
	   return request.getSession().getAttribute("user") !=null;
   }
	
	public void doGetProcess() throws SQLException, ServletException, IOException, JSONException {
        
		
		if(isConnected()) {
			removeAttributes();
			String doVerbs=request.getParameter("do");
			if(doVerbs!=null && (doVerbs.equals("serie-tv") || doVerbs.equals("film"))) {
				if(request.getParameter("cat")!=null) { //ajax call
				    try {
					   int type=doVerbs.equals("film") ? 0 : 1;
					   int cat=Integer.parseInt(request.getParameter("cat")); //parametro aggiuntivo ajax
					   genere(cat,type);
				    }catch(NumberFormatException e) {
				    	//redirect a una pagina predefinita di errore interno.
				    	
				    }
				}else { //prima richiesta
					int tp=doVerbs.equals("film") ? 0 : 1;
					request.getSession().setAttribute("category",cat.getCategory());
					request.getSession().setAttribute("prodotti", cat.getProductsByType(tp));
					route("index.jsp");
				}
				
	
		    
			}else if(request.getParameter("log")!=null){
				request.getSession().removeAttribute("user");
				request.getSession().invalidate();
				response.sendRedirect("index.jsp");
				
			}else if(request.getParameter("prod")!=null) { //ajax call function : showEP
				int prod=Integer.parseInt(request.getParameter("prod"));
				int id_s=Integer.parseInt(request.getParameter("season"));
				HashMap<Integer,Vector<XmlFormat>> map=(HashMap<Integer,Vector<XmlFormat>>)cat.getProductByID(prod).getSrc(); //sicuramente sar� un serie-tv
				response.setContentType("application/json");
				JSONObject temp = new JSONObject();
				JSONObject json=new JSONObject();
				for(XmlFormat ep: map.get(id_s)) {
					try {
						temp.append("episode_file",ep.getPath());
						temp.append("episode_title",ep.getName());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				try {
					json.put("lista", temp);
				} catch (JSONException e) {
					
				}
				response.getWriter().print(json.toString());
				
				
			}else if(request.getParameter("search")!=null) {
				search(request.getParameter("search"));	
			}else if(doVerbs !=null && doVerbs.equals("cart")) { //do=cart&p_id=[ID]&qnt=[qnt]
				if(request.getParameter("p_id")!=null) {
					int id=Integer.parseInt(request.getParameter("p_id"));
			        if(request.getParameter("qnt")!=null) {
			        	int qnt=Integer.parseInt(request.getParameter("qnt"));
			        	cart.edit(cat.getProductByID(id), qnt);
			        }else {
			        	cart.remove(cat.getProductByID(id));
			        }
		     }else if(request.getParameter("buy")!=null) {
		    	 cart.clear();
		     }
				request.getSession().setAttribute("cart", cart);
				response.sendRedirect("cart.jsp");
			}else if(doVerbs!=null && doVerbs.equals("lengthCart")) { //ajax loadLengthCart
				response.getWriter().print(cart.getLength());
			}else if(doVerbs!=null && doVerbs.equals("checkCart")) { //ajax addOnCart
				int id=Integer.parseInt(request.getParameter("prod_id"));
				Product d=cat.getProductByID(id);
				cart.add(d);
				response.getWriter().print(cart.getLength());
			/**admin function **/	
			}else if(doVerbs!=null && doVerbs.equals("print_utenti")) {
				UserDM us=new UserDM();
				request.getSession().setAttribute("users", us.getAllUser());
				response.sendRedirect("admin.jsp");
			}else if(doVerbs!=null && (doVerbs.equals("print_serie-tv") || doVerbs.equals("print_film"))) {
				int tp=doVerbs.equals("print_film") ? 0 : 1;
				request.getSession().setAttribute("prodotti", cat.getProductsByType(tp));
				response.sendRedirect("admin.jsp");
			}
			
			else { // logged index
				defaultIndex();
	
			}
	     }else //se non connesso
	    	 response.sendRedirect("index.jsp");
	     
		  
		}
	

	/**
	 * Metodo per gestire la visualizzazione del genere
	 * @param category : rappresenta la categoria [serie-tv o film]
	 * @param type : rappresenta l'id del genere scelto (esempio 1: Azione etc..) 
	 * @throws SQLException
	 * @throws IOException 
	 */
	private void genere(int category,int type) throws SQLException, IOException {
		Product res[]=null;
		if(category==-1) 
			res=cat.getProductsByType(type);
		else
			res=cat.getProductsByCategoryAndType(category, type);
		JSONObject json=new JSONObject();	
		try {
			json.put("products", res);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.getWriter().write(json.toString());


	}
	
	
	
	
	private synchronized void search(String search) throws SQLException, JSONException, IOException {
		Product rs[]=cat.getProductsByName(search);
		JSONObject json=new JSONObject();	
		json.put("products", rs);
		response.setContentType("application/json");
		response.getWriter().write(json.toString());
		
	}
	

	private void defaultIndex() throws SQLException, ServletException, IOException {
		request.getSession().setAttribute("prodotti",cat.getProducts()); //lista prodotti
		request.getSession().setAttribute("hot", cat.getHotProducts()); // slide
		request.getSession().setAttribute("lengthCart", cart.getLength());
		request.getSession().setAttribute("cart", cart);
		route("index.jsp");
	}

}
