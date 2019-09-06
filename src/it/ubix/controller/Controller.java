package it.ubix.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;



import it.ubix.model.Catalogo;
import it.ubix.model.UserDM;
import it.ubix.model.Utente;
import it.ubix.support.Security;
import it.ubix.support.Utility;

@WebServlet("/Controller")
public class Controller extends HttpServlet {

	public Controller() {
		super();
	}
	
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  Processing process=new Processing(getServletContext(),req,resp);
	  try {
	    process.doGetProcess();
	    
	  } catch (SQLException e) {	
	    e.printStackTrace();
	 } catch (JSONException e) {
		e.printStackTrace();
	}
		
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Utente ut=(Utente)req.getSession().getAttribute("user");
		if(ut==null) {
			UserDM uf=new UserDM();
			String response;
			String form=req.getParameter("tab");
			switch(form) {
				case "reg":
					String user=req.getParameter("user");
					String psw=req.getParameter("psw");
					String email=req.getParameter("email");
					String name=req.getParameter("name");
					String cognome=req.getParameter("surname");
					String cf=req.getParameter("cf");
					String city=req.getParameter("city");
					String via=req.getParameter("via");
					String cap=req.getParameter("cap");
					String tel=req.getParameter("tel");
					String card=req.getParameter("card");
					int lvl=Integer.parseInt(req.getParameter("lvl"));
					if( Utility.validName(name) && Utility.validPsw(psw) && Utility.validEmail(email) && 
					    Utility.validCreditCard(card) && Utility.validName(cognome) && Utility.validCF(cf) && Utility.validCity(city)
					    && Utility.validRow(via) && Utility.validCAP(cap) && Utility.validTel(tel) && !user.equals("")) {
		
						 response=uf.createUser(new Utente(user,Security.hashMD5(psw),cf,"",name,cognome,cap,city,via,tel,email,card,lvl));
						resp.sendRedirect("index.jsp?do=reg&mex=" + response);
						
					}else {
						resp.sendRedirect("index.jsp?do=reg&mex=Campi mancanti o errati");
					}
					
					
					break;
					
				case "log":
					String user2=req.getParameter("user");
					String psw2=req.getParameter("psw");
					if(!user2.equals("")  && !psw2.equals("") ){
						Utente t=uf.login(user2, Security.hashMD5(psw2));
						if(t!=null) {
							req.getSession().setAttribute("user", t);
							//controllo lvl e n�sessioni
							if(t.getLvl()==0 && SessionListener.getSize(t.getIdutente())>2) {
							    req.getSession().removeAttribute("user");
								req.setAttribute("ERROR", "numero di sessioni attive maggiori di 2");
								RequestDispatcher disp=getServletContext().getRequestDispatcher("//index.jsp");
								disp.forward(req, resp);
							    
							}
							resp.sendRedirect("index.jsp");
						}else
							resp.sendRedirect("index.jsp?do=log&mex=Username o password errata");
						
					}else
						resp.sendRedirect("index.jsp?do=reg&mex=Campi mancanti ");
					
					break;
					}

		}else {
			
			//bacheca
			if(req.getParameter("bacheca")!=null) {
				if(req.getParameter("back")!=null) //se è stato premuto indietro
					resp.sendRedirect("bacheca.jsp");
				if(req.getParameter("edit")!=null) {
					
					String wt=req.getParameter("edit");
					String psw=Security.hashMD5(req.getParameter("psw"));
					if(psw.equals(ut.getPassword())) {
						try {
							bachecaEdit(wt,req,ut);
							resp.sendRedirect("bacheca.jsp");
						} catch (SQLException e) {
							
							e.printStackTrace();
						}	
					}else {
						resp.sendRedirect("bacheca.jsp?do=mod&wt=" + wt + "&wrong=1");
					}
			 }
			
		   }
			
		}
	}
	
	
	private void bachecaEdit(String param,HttpServletRequest request,Utente ute) throws SQLException {
		UserDM udm=new UserDM();
		Utente temp=ute.clone();
		switch(param) {
			case "email":
				String new_mail=request.getParameter("email");
				temp.setEmail(new_mail);
				udm.editUserByID(temp.getIdutente(), temp);
				break;
			case "password":
				String new_psw=request.getParameter("psw");
				temp.setPassword(Security.hashMD5(new_psw));
				udm.editUserByID(temp.getIdutente(), temp);
				break;
			case "tel":
				String new_telefono=request.getParameter("tel");
				temp.setCell(new_telefono);
				udm.editUserByID(temp.getIdutente(), temp);
				break;
			case"card":
				String new_credit_card=request.getParameter("card");
				temp.setCredit(new_credit_card);
				udm.editUserByID(temp.getIdutente(), temp);
				break;
			case"lvl":
				int new_lvl=Integer.parseInt(request.getParameter("plane"));
				temp.setLvl(new_lvl);
				udm.editUserByID(temp.getIdutente(), temp);
				break;
		}
		 request.getSession().setAttribute("user", temp);
		
		
	}
	
   
	
	
}
