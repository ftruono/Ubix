<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="it.ubix.model.Utente" %>

 <%
   Utente ut=(Utente)session.getAttribute("user");
   String action=request.getParameter("do");
   String wt=request.getParameter("wt"); //[password/email/telefono/]
   if(ut==null){
	   response.sendRedirect("index.jsp");
       return;
   }
	   
 %>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="initial-scale=1">
  <title>Bacheca <%= ut.getName() %></title>
  <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
	
	<body>
	   
		<div id="bacheca">
		<%if (action==null){ %>
		 <h1 >Account</h1>
         <div class="content-user">
	           <div id="title">
	            <h2>Dati utenti & Fatturazione</h2>
	           </div>
         
	           <div id="dati">
	              <p><%= ut.getEmail() %></p>
	              <p id="psw">Password : ********</p>
	              <p id="phone">Telefono :<%= ut.getCell() %></p>
	              <p id="credit-card">Carta di credito : <%= ut.getCredit() %></p>
	           </div>
           
	           <div id="edit">
	             <ul>
	              <li><a href="bacheca.jsp?do=mod&wt=email">Modifica email</a></li>
	              <li><a href="bacheca.jsp?do=mod&wt=pass">Modifica password</a>
	              <li><a href="bacheca.jsp?do=mod&wt=tel">Modifica Cellulare</a>
	              <li id="credit"><a href="bacheca.jsp?do=mod&wt=card">Modifica Carta di credito</a>
	             </ul>
	           </div>
         
         </div>
         
         <div class="content-user">
           <div id="title">
            <h2>Abbonamento</h2>
           </div>
         
           <div id="dati">
              <p id="abbonamento">Tipo di abbonamento <%= (ut.getLvl()>1) ? "Premium" : "Standard" %></p>
           </div>
           <div id="edit">
             <ul>
             	<li><a href="bacheca.jsp?do=mod&wt=plane" >Modifica piano</a>
             </ul>
           </div>

         </div>
         
         
         <% }else if(action.equals("mod")){ %>
            <div id="modifica">
               <%if(wt!=null){ %>
                <p> Inserisci la password attuale </p>
	                <form method="post" action="Controller">
	                <input type="hidden" name="bacheca" />
	                <input type="hidden" name="edit" value="<%=wt%>" />
			    <div class="input-container">
			     <input class="input-field" type="password" placeholder="Password" name="psw">
			    </div>
                <% if(wt.equals("email")){ %>
                  <p> la tua mail è <%= ut.getEmail() %></p>
				  <p> Inserire la tua nuova mail </p>
			  	  
			  	  <div class="input-container">
			   		<input type="email" class="input-field" type="text" placeholder="email" name="email">
			  	  </div>
                  
                <% }else if(wt.equals("pass")){%>
                     <p> Nuova Password </p>
					 <div class="input-container">
					    <input type="password" class="input-field" type="password" placeholder="Password" name="psw" >
					  </div>
                
                <% }else if(wt.equals("tel")){ %>
                      <p> Il tuo numero di cellulare è <%=ut.getCell() %></p>
					  <p> Inserire il nuovo numero di cellulare </p>
			  		  <div class="input-container">
			   			<input type="cel" class="input-field" type="text" placeholder="cellulare" name="tel">
			  		</div>
                
                <% }else if(wt.equals("card")){ %>
                     <div class="input-container">
			   			<input type="text" class="input-field" type="text" placeholder="carta di credito" name="card">
			  		</div>
                
                
                <%}else if(wt.equals("plane")){%>
                    <div class="input-container">
                        <input type="radio" name="plane" value="0"> Standard<br>
  						<input type="radio" name="plane" value="1"> Premium<br>
                    
                    </div>
                
                <%} %>
               <input type="submit" name="save" value="Salva ">
				<input type="submit" name="back" onclick="location.href=bacheca.jsp" value="Indietro ">
               </form>
               <%if(request.getParameter("wrong")!=null){ %>
                   <div class="wrong">
                     <p>Password errata</p>
                   </div>
                 
               
               
               <%} %>
            
            </div>
         
         
         
         
         <%} } %>
         </div>
   		
	</body>
</html>