$(document).ready(function(){
   checkUpload();	
	
	
	
});

$(window).on("resize",function(){
	var win=$(this);
	if(win.width()>499){
		$("#sidenav").attr("style","");
		
	}
	
});

function openNav() {
    /*cambia la dimensione della finestra aumentando la larghezza a 250 px*/
      document.getElementById("sidenav").style.width = "170px";
    /*e slitta di 250px tutto il contenuto*/
     document.getElementById("main").style.marginLeft = "170px";

  }

  function closeNav() {
    /*riporta la larghezza a 0 della barra laterale*/
      document.getElementById("sidenav").style.width = "0";
    /*e ricentra il contenuto centrale */
     document.getElementById("main").style.marginLeft= "0";
  }


function checkUpload(){
	var check=document.upload.type;
	var sell=document.upload.sellable;
	
	$(sell).on("change",function(e){
		if(sell.value==1)
			$(document.upload.price).attr("required",true);
		else
			$(document.upload.price).attr("required",false);
	});
	
	$(check).on("change",function(e){
		if(check.value==1)
			$(document.upload.src).attr("accept","application/zip");
		else
			$(document.upload.src).attr("accept","video/mp4,video/x-m4v");
	});
	
	//("#file").attr("accept",)
	
	
}