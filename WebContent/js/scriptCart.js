$(document).ready(function(){
	endBuy();
});


function endBuy(){
	$("#buy").on("click",function(){
		 $.ajax({
			 type:'GET',
	         url: "/Ubix/Controller",
	         data: {"do":"cart" ,"buy":"end" },
	         success: function (data) {
	        	 $(".prodotto").remove();
	        	 alert("Acquisti completati con successo");
	         },
	         error: function () {
	             
	         }
	     });
		
	});
	
}

function changeQuantityProduct(id){
	var value=$("#qnt-" + id).val();
	 $.ajax({
		 type:'GET',
         url: "/Ubix/Controller",
         data: {"do":"cart" ,p_id:id,"qnt":value },
         success: function (data) {
        	 
         },
         error: function () {
             
         }
     });
	
}