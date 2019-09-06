<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="it.ubix.model.Utente, it.ubix.model.Catalogo, it.ubix.model.Product,
    it.ubix.support.XmlFormat,java.util.HashMap ,java.util.Vector,it.ubix.model.Serie,it.ubix.model.Category,it.ubix.model.Film" %>

 <%
   Utente ut=(Utente)session.getAttribute("user");
   String error=(String)request.getAttribute("ERROR");
   String action=request.getParameter("do");
   Product[] hots=null;
   Product[] prodotti=null;
   Category[] categories=null;
   if(error!=null){
		 response.getWriter().write("<div class='wrong center'> Ci sono più di due sessioni attivi</div>");
		 request.removeAttribute("ERROR");
		 return;
    }
   if(ut!=null){
     hots=(Product[])session.getAttribute("hot");
     prodotti=(Product[])session.getAttribute("prodotti");
     categories=(Category[])session.getAttribute("category");

     if(action==null && hots==null){
        response.sendRedirect("./Controller");
        return ;
     }
     //se lunghezza elementi carello non c'è chiedi!
   }
 %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="initial-scale=1">
  <title>Ubix Streaming Service</title>
  <!-- link di stile -->
  <link rel="stylesheet" href="css/style.css" type="text/css">
  <link rel="stylesheet" type="text/css" href="css/styleSlideshow.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="js/script.js" type="text/javascript"></script>
</head>
<body>
    <%= action %>
   <header>
    <nav>
      <div id="navMax">
		      <div id="logo">
		         <img src="image/logo.png" height="47px">
		      </div>
		      	<ul>
          <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
				  <li><a href="index.jsp">Home</a></li>
				  <%if(ut==null){ %>
				  <li><a href="index.jsp?do=price">Prezzi</a></li>
				   <li><a href="index.jsp?do=contact">Contatti</a></li>
				   <li class="menu-dx"><a  href="index.jsp?do=log">Login</a></li>
				 <% }else{   %>
				 	   <li><a href="bacheca.jsp">Bacheca</a></li>
				 	   <li><a href="Controller?do=film">Film</a>
				 	   <li><a href="Controller?do=serie-tv">Serie Tv</a>
				 	   <%if(ut.getLvl()>=2){ %>
				 	    <li><a href="admin.jsp">Pannello admin</a>
				 	   <%} %>
				 	   <li class="menu-dx" ><a  href="Controller?log=esci"><i class="fa fa-sign-out" ></i></a></li>
				
				 <%}if (ut!=null){   %>
				  <li class="menu-dx">
				    <a href="Controller?do=cart" target="popup" onclick="window.open('Controller?do=cart','popup','width=600,height=600'); return false;" id="cart" class="badge" data-badge=""><i class="fa fa-shopping-cart" aria-hidden="true"></i></a>
				  </li>
				  <li class="menu-dx">
					  <input type="text" id="search" placeholder="Search..">
				  </li>
				  
				 <% } %>

				</ul>
      </div>
      <div id="navMin">
        <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; </span>
      </div>
   </nav>
   </header>
  <div id="content">


<%if(ut==null){
	if(action==null){ %>
        <section id="wall">
	       <img src="image/wall.jpg">
	       <p>Uno tra i miglior cataloghi d'europa con le serie più seguite e viste di sempre</p>
	       <button><a href="index.jsp?do=reg">Iscriviti Ora</a></button>
     	</section>
    <%}else if(action.equals("price")){%>
	     <section id="price" >
	     <h1>Le nostre offerte!</h1>
	     <p>Scegli un piano e guarda tutto e dovunque su Ubix</p>
	        <div class="columns">
			    <ul class="price">
			    <li class="header">Basic User</li>
			    <li class="grey">€ 10.00 / mese</li>
			    <li>HD/Ultra HD</li>
			    <li>2 Schermi contemporanei</li>
			    <li>Accesso al catalogo dvd</li>
			    <li>Assistenza 24/7</li>
			    <li class="grey"><a href="#" class="button">Entra adesso</a></li>
          </ul>
		</div>
	     <div class="columns">
			    <ul class="price">
			    <li class="header" style="background-color:#fd4513">Premium User</li>
			    <li class="grey">€ 25.00 / mese</li>
			    <li>HD/Ultra HD</li>
			    <li>Schermi illimitati</li>
			    <li>Accesso al catalogo dvd e 3 dvd omaggio</li>
			    <li>Assistenza 24/24</li>
			    <li class="grey"><a href="#" class="button">Entra adesso</a></li>
          </ul>
		</div>
	    </section>
     <%}else if(action.equals("contact")){ %>
	      <section id="contatti">
			<p>  </p>
	      </section>
     <%}else if(action.equals("reg") || action.equals("log")){ %>
      <section id="reg">
	      <div class="bar">
			  <button class="bar-item button" onclick="openTab('login')" id="button-login">Login</button>
			  <button class="bar-item button" onclick="openTab('regs')">Registrazione</button>
	      </div>
         <div id="login" class="log" style="display:<%= (action.equals("log")) ? "block" : "none" %>">
			  <h2>Login</h2>
			  <form action="Controller" method="post" >
				  <div class="input-container">
				    <i class="fa fa-user icon"></i>
				    <input class="input-field" type="text" placeholder="Username" name="user" required>
				  </div>

				  <div class="input-container">
				    <i class="fa fa-key icon"></i>
				    <input class="input-field" type="password" placeholder="Password" name="psw" required>
				  </div>

			  <input type="hidden" name="tab" value="log">
			  <input type="submit" class="button" value="Login" />
			  </form>

	      <div class="response" style="display:<%= (request.getParameter("mex")!=null) ? "block" : "none" %>">
		    <%= (request.getParameter("mex")!=null) ? request.getParameter("mex") : "" %>
		  </div>
		</div>
		<div id="regs" class="log" style="display:<%= (action.equals("reg")) ? "block" : "none" %>">
		  <h2>Registrazione</h2>
		  <form action="Controller" name="reg" method="post" onSubmit="return formValidation(true);">
			  <div class="input-container">
			    <i class="fa fa-user icon"></i>
			    <input class="input-field" type="text" placeholder="Username" name="user" required>
			  </div>

			  <div class="input-container">
			    <i class="fa fa-envelope icon"></i>
			    <input class="input-field" type="text" placeholder="Email" name="email" required>
			  </div>

			  <div class="input-container">
			    <i class="fa fa-key icon"></i>
			    <input class="input-field" type="password" placeholder="Password" name="psw" required>
			  </div>

			  <div class="input-container">
			   <i class="fa fa-address-card icon"></i>
			   <input class="input-field" type="text" placeholder="Nome" name="name" required>
			  </div>

			  <div class="input-container">
			   <i class="fa fa-address-card icon"></i>
			   <input class="input-field" type="text" placeholder="Cognome" name="surname" required>
			  </div>

			  <div class="input-container">
			   <i class="fa fa-id-card icon"></i>
			   <input class="input-field" type="text" placeholder="codice fiscale" name="cf" required>
			  </div>

			  <div class="input-container">
			   <i class="fa fa-home icon"></i>
			   <input class="input-field" type="text" placeholder="Città" name="city" required>
			  </div>

			  <div class="input-container">
			   <i class="fa fa-ellipsis-h icon"></i>
			   <input class="input-field" type="text" placeholder="Via" name="via" required>
			  </div>

			  <div class="input-container">
			   <i class="fa fa-id-badge icon"></i>
			   <input class="input-field" type="text" placeholder="CAP" name="cap" required>
			  </div>

			  <div class="input-container">
			   <i class="fa fa-mobile icon"></i>
			   <input class="input-field" type="text" placeholder="cellulare" name="tel" required>
			  </div>

			  <div class="input-container">
			   <i class="fa fa-credit-card icon"></i>
			   <input class="input-field" type="text" placeholder="carta di credito" name="card" required>
			  </div>
			  <div class="input-container">
			     <input type="radio" name="lvl" value="0" checked> Standard<br>
  				 <input type="radio" name="lvl" value="1" >Premium
			  </div>
			  
			  <input type="hidden" name="tab" value="reg">
			  <button type="submit" class="button">Registrati</button>
		  </form>
		  <div class="response" style="display:<%= (request.getParameter("mex")!=null) ? "block" : "none" %>">
		   <%= (request.getParameter("mex")!=null) ? request.getParameter("mex") : "" %>
		  </div>
		</div>
      </section>
     <% }
        
       	}else{ //login effettuato
        	 
      %>
      <div id="home">
      <!-- zona dove verrà visualizzato il prodotto -->
        <div class="modal">
		    <span class="close">&times;</span>
		    <video src="" class="modal-content" controls></video>
		 
	    </div>

       <% if(action==null){ %>
         <div class="slideshow-container">
           <% for(int i=0;i<hots.length;++i){ %>
				<div class="mySlides fade">
				   <img src="image/<%= hots[i].getImg().toString() %>" style="width:100%">
				   <div class="text"><%= hots[i].getNome() %></div>
				  
				</div>
			<% } %>
		     <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
			 <a class="next" onclick="plusSlides(1)">&#10095;</a>
		 </div>
		 <% }else{%>
		     <div class="chooser">
		       <label for="cat">Categorie Disponibili : </label>
		       <select id="cat" onchange="category(this,'<%=action%>')">
		         <option value="-1">Tutti</option>
		       <%for(Category cat : categories){ %>
		        <option value="<%= cat.getId()%>"><%=cat.getName()%></option>
		       <%} %>
		       </select> 
		     </div>
		 
		 <% 
		        }%> 
		
		 <div class="catalogo">
		    
		    <% for(Product el : prodotti){ %>
		       <article>
		        <div class="cover">
		           <img src="image/<%= el.getImg() %>" onclick="showVideo(<%=el.getId()%>,<%= el.getType() %>)"/>
		        </div>
		        <div class="content">
		           <h2 id="title" onclick="showVideo(<%=el.getId()%>,<%= el.getType() %>)"><%= el.getNome() %></h2>
		           <p id="desc">
		            <strong>Cast/Autore : <%= el.getCast() %></strong>
		            <%= el.getDescrizione() %>
		           </p>
		           <% if(el.getType()==1){ 
		        	   //serie tv
		               HashMap<Integer,Vector<XmlFormat>> map=(HashMap<Integer,Vector<XmlFormat>>)el.getSrc();  
		               Serie s=(Serie)el;
		            %>
		           <input id="path-<%=el.getId()%>" type="hidden" value="<%=s.getRelativeSource()%>" />
		           <label for="s-<%=el.getId()%>">Stagione :</label>
		           <select id="s-<%=el.getId() %>" onchange="showEP(<%=el.getId()%>)">
		               <% for(int v : map.keySet()){
		            	   v+=1; %>
		                  <option value="<%=v%>">Stagione <%=v%></option>
		               <% } %>
		           </select>
		           <label for="e-<%=el.getId()%>">Episodio</label>
		           <select id="e-<%=el.getId() %>">
		           
		             <% Vector<XmlFormat> eps=map.get(0);
		             for(XmlFormat ep : eps){ %>
		                    <option value="<%=ep.getPath()%>"><%=ep.getName()%></option>
		              <%} %>  
		           </select>
		          
		           
		           <%}else{
		              Film fm=(Film)el; %>
		        	  <input id="path-<%=el.getId()%>" type="hidden" value="<%=fm.getSrc()%>" /> 
		           <%} %>
		           <%if(el.isSellable()){%>
		             <div class="buy">
		               <p>Prodotto disponibile nello store :</p>
		               <img src="image/cart.png"  width="120px"; onclick="addOnCart(<%= el.getId() %>)"/>
		             
		             </div>
		           
		           
		           <%} %> 
		        </div>
		 
		     </article>
		    <%} %>
		   
		 </div>
		 
		 
	</div>

   <%}%>
  </div>
   <%if(ut==null){%>
       <%@include file="footer.jsp"%>
   
    <%}%>
     
  
</body>
</html>
