<!--              FOOTER                -->
   <div class="footer">
    <div id="left">

      <div class="block">
        <p>Info</p>
        <p>Ubix Stream Service &copy 2018 </p>

      </div>
      <div class="block">
       <p>Social</p>
         <span class="fa fa-facebook-official"></span>
         <span class="fa fa-twitter"></span>
      </div>
      <div class="block">
       <p>Contatti</p>
       <a><span class="fa fa-envelope"></span> f.truono5@studenti.unisa.it</a>
       <a><span class="fa fa-envelope"></span> a.petrosino@studenti.unisa.it</a>
     </div>
    </div>

    <div id="right">
      <div class="map-side">
         <div id="map"></div>
      </div>
      <div class="info-side">
        <div class="block">
	      <p><span class="fa fa-map-marker"></span>Indirizzo :</p>
	      <p>Via Giovanni Paolo II, 132
			 84084 Fisciano SA</p>
	     </div>
      </div>

    </div>
   </div>
  
  <script type="text/javascript">

  function myMap() {
	  //40.774690, 14.789112
	  var myCenter = new google.maps.LatLng(40.774447, 14.789015);
	  var mapOptions = {center: myCenter, zoom: 18};
	  var map = new google.maps.Map(document.getElementById("map"), mapOptions);
	  var marker = new google.maps.Marker({position:myCenter});
	  marker.setMap(map);
  }
 </script>
 <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA1DriUWIweGxg1lKo0Aqwa64Z3oqYFSs4&callback=myMap"></script>