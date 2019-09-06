$(document).ready(function(){
      init();
   }
);

$(window).on("resize",function(){
	 var win = $(this);
	if(win.width()>499){
		$("#navMax").attr("style","");
	}
});


function init(){
	showSlidesAutomatic();
	$("#search").on("keypress",function(e){
		var sc=$("#search").val();
		if(e.which == 13){
			$.ajax({
				 type:'GET',
		         url: "/Ubix/Controller",
		         data: {search:sc },
		         success: function (data) {
		        	 $(".slideshow-container").html("");
		        	 $(".catalogo").html("");
		        	   if(data.products.length>=1){
			         	   for(var i=0;i<data.products.length;++i){
			        		   makeGraphicProduct(data.products[i]);
			        	   }
		        	   }else{
		        		   $(".catalogo").html("<div id='wrong'>Nessun risultato trovato</div>");
		        	   }
		         },
		         error: function () {
		             
		         }
		     });
			
			
		}
		
	});
	loadLengthCart();
	//hideSearch();
	//map();
}


function openNav() {
    /*cambia la dimensione della finestra aumentando la larghezza a 250 px*/
      $("#navMax").css("width","170px");
      $("#content").css("margin-left","150px");
  }

  function closeNav() {
      $("#navMax").css("width","0");
      $("#content").css("margin-left","0");
 }

function loadLengthCart(){
	$.ajax({
		 type:'GET',
        url: "/Ubix/Controller",
        data: {"do":"lengthCart" },
        success: function (data) {
       	  var value=parseInt(data);
       	  $("#cart").attr('data-badge',value);
        },
        error: function () {
            
        }
    });
	
}

function  openTab(tabName){
	    var i;
	    var x = $(".log");
	    for (i = 0; i < x.length; i++) {
	       x[i].style.display = "none";
	    }
	  $("#" + tabName).css("display","block");
}

function showEP(id){
	var val=$("#s-" + id).val()-1;
	 $.ajax({
		 type:'GET',
         url: "/Ubix/Controller",
         data: {prod:id ,season:val },
         success: function (data) {
        	 var ep=$("#e-" + id);
             $("#e-" + id + " option").each(function() {
            	    $(this).remove();
             });
             
             try{
             var obj=JSON.parse(JSON.stringify(data));
	          for(var i=0;i<obj.lista.episode_file.length;++i){
	             ep.append("<option value='" + obj.lista.episode_file[i] + "'>" + obj.lista.episode_title[i] + "</option>");
	          }
             } catch(e) {
                // alert(e);
             }
             
             
             
         },
         error: function () {
             
         }
     });
	
}

function addOnCart(id){
	
	
	$.ajax({
		 type:'GET',
        url: "/Ubix/Controller",
        data: {"do":"checkCart",prod_id:id },
        success: function (data) {
       	   var value=parseInt(data);
       	   $("#cart").attr('data-badge',value);
        },
        error: function () {
            
        }
    });
	
	//ajax call per verifire duplicato e aggiornare dati , restituisce un intero
	//var value=parseInt($("#cart").attr('data-badge'));
	//value+=1;
	
	
	
	
}

function showVideo(id,type){
	var modal=$(".modal");
	var span = $(".modal .close")[0];
	span.onclick = function() {
		//close function
		 $("body").css("overflow","auto");
	     $(".modal-content").attr("src",""); 
	     modal.css("display", "none");
	        	   
	}
	var path=$("#path-" + id).val();
	$(".modal-content").contextmenu(function() {
	    return false;
	});
    if(type==1){ //serie tv
       var ep=$("#e-" + id).val();
       $(".modal-content").attr("src","resource/serie/" + path + "/" + ep); 
   }else
       $(".modal-content").attr("src","resource/film/" + path);  
        			
   modal.css("display","block");
   $("body").css("overflow","hidden");
}

function category(combobox,action){
	var value=combobox.value;
	 $.ajax({
		 type:'GET',
         url: "/Ubix/Controller",
         data: {"do":action, cat:value },
         success: function (data) {
        	   $(".catalogo").html("");
        	   if(data.products.length>=1){
	         	   for(var i=0;i<data.products.length;++i){
	        		   makeGraphicProduct(data.products[i]);
	        		   
	        	   }
        	   }else{
        		   $(".catalogo").html("<div id='wrong'>Nessun " + action + "disponibile nella categoria richiesta</div>");
        	   }
        	   
         },
         error: function () {
             
         }
     });
	
}
//creazione dinamica del prodotto
function makeGraphicProduct(product){
	var article=$("<article>");
	article.attr("id",product.id);
	
	$(".catalogo").append(article);
	
	article.append("<div class='cover'>");
	article.append("<div class='content'>");
	
	article.find(".cover").append("<img src='image/" + product.img + 
			"' onclick='showVideo(" + product.id + "," + product.type + ")' />");
	
	var content=article.find(".content");
	content.append("<h2 id='title' onclick='showVideo(" + product.id + "," + product.type + ")'>" + product.nome + "</h2>");
	content.append("<p id='desc'>" + product.descrizione + "</p>");
	if(product.type==1){
		content.append("<input id='path-" + product.id + "' type='hidden' value='" + product.relativeSource + "' />");
		content.append("<label for='s-" + product.id + "'>Stagione :</label>");
		var sel = $("<select>").appendTo(content); //aggiunge un select al content
		sel.attr("id","s-" + product.id);
		var ep=Object.values(product.src);
		for(i=0;i<ep.length;++i){
			var j=i+1;
			sel.append($("<option>").attr('value',j).text("Stagione : " + j));
		}
		sel.on('change',function(){showEP(product.id)});
		content.append("<label for='e-" + product.id + "'>Episodio</label>");
		var selEp=$("<select>").appendTo(content);
		selEp.attr("id","e-" + product.id);
		for(i=0;i<ep[0].length;++i){
			selEp.append($("<option>").attr("value",ep[0][i].path).text(ep[0][i].name));
		}
	}else{
		content.append("<input id='path-" + product.id + "' type='hidden' value='" + product.src + "' />")
		
	}
	if(product.sellable){
		content.append("<div class='buy'><p>Prodotto disponibile nello store :</p>"
				+"<img src='image/buy.png' onclick='addOnCart(" + product.id + ")'/> </div>");
	}
	
}

function formValidation(mode){
	if(mode){
		var check=true;
		var resp="<ul>";
		var user=validUser(document.reg.user);
		var psw=validPsw(document.reg.psw);
		var mail=validEmail(document.reg.email);
		var name=validName(document.reg.name);
		var surname=validName(document.reg.surname);
		var cf=validCF(document.reg.cf);
		var city=validCity(document.reg.city);
		var via=validRow(document.reg.via);
		var cap=validCAP(document.reg.cap);
		var tel=validTel(document.reg.tel);
		var card=validCreditCard(document.reg.card);
		if(!user){
	      resp+="<li>Username non valido</li>";
	      check=false;
		}
	    if(!psw){
	    	resp+="<li>La password deve essere lunga almeno 6 caratteri e contenere un carattere speciale";
	        check=false;
	    }
	    if(!mail){
	    	resp+="<li>Mail non valida</li>"
	        check=false;
	    }
	    if(!name){
	    	resp+="<li>Nome non valido , il nome non può contenere numeri</li>";
	    	check=false;
	    }
	    if(!surname){
	    	resp+="<li>Cognome non valido , il cognome non può contenere numeri </li>";
	    	check=false;
	    }
	    if(!cf){
	    	resp+="<li>Codice Fiscale non valido";
	    	check=false;
	    }
	    if(!city){
	    	resp+="<li>Città non valida</li>";
	    	check=false;
	    }
	    if(!cap){
	    	resp+="<li>CAP non valido</li>";
	    	check=false;
	    }
	    if(!tel){
	    	resp+="<li>Numero di telefono non valido</li>";
	    	check=false;
	    }
	    if(!card){
	    	resp+="<li>Carta di credito non valida</li>";
	    	check=false;
	    }
	    resp+="</ul>"
	    $(".response").css("display","block");
	    $(".response").html(resp);

	   return check;

	}
	else{

		return true;
	}

}
function validName(name){
	name=name.value;
	if(name!="" && ! /.*\\d+.*/.test(name))
		return true;
	return false;

}
function validUser(user){
	if(user.value!="")
		return true
	return false;
}
function validPsw(psw){
	var special,upper;
	special=upper=false;
	if(psw.value.length<6 || psw.value=="")
		return false;
	for (var i = 0; i < psw.value.length; i++) {
		  var c=psw.value.charAt(i);
		  var value=c.charCodeAt(0);
		  if((value>=33 && value<=47) || (value>=58 && value<65))
			  special=true;
		  else if(isNaN(c) && c==c.toUpperCase())
			   upper=true
	}
	return (upper && special);
}
function validEmail(email){
	email=email.value;
	var regex=new RegExp("^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$");
	return regex.test(email);

}
function validCreditCard(card){
	card=card.value;
	if(card.length>=14 && card.length<=16 && new RegExp("[0-9]+").test(card))
		return true;
	return false;
}
function validCF(cf){
	cf=cf.value;
	if(cf!="" && new RegExp("[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]").test(cf))
		return true;
	return false;
}
function validCAP(cap){
	cap=cap.value;
	if(cap!="" && cap.length<=5 && new RegExp("[0-9]+").test(cap))
		return true;
	return false;

}
function validCity(city){
	city=city.value;
	if(city!="" && city.length>2 && !new RegExp(".*\\d+.*").test(city))
		return true;
	return false;
}
function validRow(row){
	row=row.value;
	if(row!="" && row.length>2)
		return true;
	return false;
}
function validTel(tel){
	tel=tel.value;
	if(tel!="" && new RegExp("[0-9]+").test(tel))
		return true;
	return false;
}
function hideSearch(){
  $('body').on("click",function(){
	  var open=$("#searchBox-show");
	  if(open.length !== 0){
		  var inp=$(".search");
		  console.log(open.find(inp).val().length);

	  }

	  /*
	  $("#seachBox-show").attr("id","searchBox");
	  $("#searchBox").html("");
	  */
  });


}
function searchShow(){
	$("#searchBox").click(function(){
		$("#searchBox").html("");
		var input="<div class='sBox'>" +
          "<span class='fa fa-search'></span>" +
         "<input placeholder='Search..' class='search' name='search'>" +
         "<span class='fa fa-remove'></span>" +
         "</div>";
		$("#searchBox").attr("id","searchBox-show");
		$("#searchBox-show").html(input);
		$(this).unbind(); //rimuovi evento

    });

}




var slideIndex = 1;

function showSlidesBotton(n) {
    var i;
    var slides = $(".mySlides");
    var dots =  $(".dot");
    if (n > slides.length) {slideIndex = 1}    
    if (n < 1) {slideIndex = slides.length}
    for (i = 0; i < slides.length; i++) {
    	$(slides[i]).css("display","none");   
    }
    for (i = 0; i < dots.length; i++) {
        $(dots[i]).attr("class", "");
    }
    $(slides[slideIndex-1]).css("display","block");  
    $(dots[slideIndex-1]).attr("class","active");
 }

  function showSlidesAutomatic() {
    var i;
    var slides = $(".mySlides");
    var dots = $(".dot");
    for (i = 0; i < slides.length; i++) {
       $(slides[i]).css("display","none");  
    }
    slideIndex++;
    if (slideIndex > slides.length) {slideIndex = 1}    
    for (i = 0; i < dots.length; i++) {
    	$(dots[i]).attr("class", "");
    }
    $(slides[slideIndex-1]).css("display","block");  
    $(dots[slideIndex-1]).attr("class","active");
    setTimeout(showSlidesAutomatic, 20000); // Change image every 20 seconds
  }

  function plusSlides(n) {
      showSlidesBotton(slideIndex += n);
  }
  
