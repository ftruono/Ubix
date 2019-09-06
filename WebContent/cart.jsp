<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="it.ubix.model.Utente,it.ubix.model.Cart,it.ubix.model.Product,java.util.HashMap,
    java.util.Map,java.util.Map"%>
    
<%
 Utente t=(Utente)session.getAttribute("user");
 Cart cart=null;
 HashMap<Product,Integer> map;
 String action=request.getParameter("response");
 if(t!=null){
	 cart=(Cart)session.getAttribute("cart");
	 map=cart.getMapCart();
 }
 else{
	 response.sendRedirect("index.jsp");
     return;
 }
	 
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1">
<link rel="stylesheet" href="css/style.css" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/scriptCart.js" type="text/javascript"></script>

<title>Ubix Streaming Service-Cart</title>
<title>Cart</title>
</head>
<body>
  <div class="cart-container">
  <h1>Carello</h1>
   <% for(Map.Entry<Product,Integer> s: map.entrySet()){ %>
   <div class="prodotto">
     <div class="cover">
       <img src="image/<%=s.getKey().getImg()%>" />
       <p><%=s.getKey().getNome()%></p>
    </div>
    <div class="content">
      
      <p><%=s.getKey().getDescrizione()%></p>
      <input type="number" value="<%=s.getValue()%>" onchange="changeQuantityProduct(<%= s.getKey().getId()%>)" id="qnt-<%=s.getKey().getId()%>" />
      <a href="Controller?do=cart&p_id=<%=s.getKey().getId()%>" target="_self">Rimuovi</a>
   </div>
   
   </div>
   <%} %>
  </div>
  
  <div id="finalize">
     <input type="submit" class="button buy" id="buy" value="Concludi acquisti" <% if(map.size()==0){%> disabled <%}%>  />  
    
  </div>
</body>
</html>