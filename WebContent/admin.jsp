<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="it.ubix.model.Utente,it.ubix.model.UserDM,it.ubix.model.Product" %>

<%
  Utente ut=(Utente)session.getAttribute("user");
  Product[] prodotti=null;
  Utente[] users=null;
  String action=(request.getParameter("do")==null) ? "" : request.getParameter("do");;
  users=(Utente[])session.getAttribute("users");
  prodotti=(Product[])session.getAttribute("prodotti");
  if(ut==null)
	  response.sendRedirect("index.jsp");
  else{
	  if(ut.getLvl()<2 )
		  response.sendRedirect("index.jsp");
  }
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/adminstyle.css">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/admin.js" type="text/javascript"></script>
</head>
<body>



<div id="sidenav">
  <a href="javascript:void(0)" class="closebtn"  onclick="closeNav()">&times;</a>
	 <div id="logo">
		<a href="admin.jsp" id="logo"><img src="image/logo.png" ></a>
	 </div>
  <button class="bar"><i class="fa fa-user-circle">	UTENTI</i></button>
  <ul class="list">
  	<li class="panel"><li><a href="Controller?do=print_utenti">Lista Utenti</a></li>
  </ul>
  <button class="bar"><i class="fa fa-film">FILM</i></button>
    <ul class="list">
  	<li class="panel"><li><a href="Controller?do=print_film">Lista film</a></li>
  	<li class="panel"><li><a href="admin.jsp?do=carica">Aggiungi film</a></li>
  </ul>
  <button class="bar"><i class="fa fa-television">SERIE TV</i></button>
    <ul class="list">
  	<li class="panel"><li><a href="Controller?do=print_serie-tv">Lista serie</a></li>
  	<li class="panel"><li><a href="admin.jsp?do=carica">Aggiungi serie</a></li>
  </ul>

</div>
      <div id="navMin">
        <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; </span>
       </div>
<script>
var acc = document.getElementsByClassName("bar");
var i;

for (i = 0; i < acc.length; i++) {
    acc[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var panel = this.nextElementSibling;
        if (panel.style.display === "block") {
            panel.style.display = "none";
        } else {
            panel.style.display = "block";
        }
    });
}
</script>
<div id="main">
  <%if(action.equals("carica")){
     prodotti=null;
     users=null; %>
  	<h2>Aggiungi Serie</h2>
  	<%if(request.getParameter("message")!=null){ %>
  	<div class="done">
  	  <p>Upload completato con successo</p>
  	
  	</div>
  	<%} %>
  	
  	<div id="input" >
  	      
		  <form name="upload" action="UploadServlet"  method="post" enctype="multipart/form-data">
			<input type="hidden" name="admin">
			<label class="check">Film
            	<input type="radio" name="type" value="0" checked> 
            	
			</label>
          	<label class="check">Serie
            	<input type="radio" name="type" value="1">
          </label>
			  <div class="input-container">
			    <input class="input-field" type="text" placeholder="nome" name="nome" >
			  </div>

			  <div class="input-container">
			    <input class="input-field" type="text" placeholder="cast" name="cast" >
			  </div>

			  <div class="input-container">
			    <input class="input-field" type="text" placeholder="descrizione" name="descrizione">
			  </div>
			  <div class="input-container">
			   <label class="check">In vendita
            	 <input type="radio" name="sellable" value="1" checked> 
			  </label>
          	  <label class="check">Non in vendita
            	<input type="radio" name="sellable" value="0">
              </label>
			  </div>
			  <div class="input-container">
			    <label class="check">In evidenza
            	  <input type="radio" name="hot" value="1" checked> 
			    </label>
          	  <label class="check">non in evidenza
            	<input type="radio" name="hot" value="0">
              </label>
			  </div>
			  <div class="input-container">
			    <input class="input-field" type="text" placeholder="price" name="price" required>
			  </div>
			  
			  <div class="input-container">
			   <label>Carica Immagine</label>
			   <input class="input-field" type="file" accept="image/*" name="preview_img" required>
			  </div>
			  
			  <div class="input-container">
			  <label>Carica File</label>
			  <input class="input-field" type="file" name="src" accept="video/mp4" required/>
  			 </div>
  			 

			  <button type="submit" class="button">Carica</button>
		  </form>

		</div>
  <%} %>
  	<div class="tabella">
  	<table>	 
		<%if(users!=null){ %>
		    <tr>
		    	<th>Id Utenti</th>
		    	<th>Nome utente</th>
		    	<th>Nome</th>
		    	<th>Cognome</th>
		    	<th>Codice Fiscale</th>
		    	<th>CAP</th>
		    	<th>Cellulare</th>
		    	<th>Città</th>
		    	<th>Carta di credito</th>
		    	<th>Email</th>
		        <th>Password criptata</th>
		     </tr>
		  <%for(Utente u:users){ %>
			<tr>
				<td><%=u.getIdutente() %></td>
				<td><%=u.getUser() %></td>
				<td><%=u.getName() %></td>
				<td><%=u.getSurname() %></td>
				<td><%=u.getCodice_fiscale() %></td>
				<td><%=u.getCap() %></td>
				<td><%=u.getCell() %></td>
				<td><%=u.getCity() %></td>
				<td><%=u.getCredit() %></td>
				<td><%=u.getEmail() %></td>
				<td><%=u.getPassword() %></td>
			</tr>
			<%} } %>
		<%if(prodotti!=null){ %> 
		 <tr>
		  <th>Preview</th>
		  <th>ID prodotto</th>
		  <th>Nome</th>
		  <th>Descrizione</th>
		  <th>Cast</th>
		 </tr>
		 <%for(Product el : prodotti){ %>
			<tr>
				<td> <img src="image/<%= el.getImg() %>"style="width: 70px;height: 70px;"/></td>
				<td><%=el.getId() %></td>
				<td><%=el.getNome() %></td>
				<td><%=el.getDescrizione() %></td>
				<td><%=el.getCast() %></td>
			</tr>
		<%} } %>
  		</table>
  	</div>
</div>

</body>
</html>
