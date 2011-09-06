<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
  
<html>
 <head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Show Active Tracks </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
     
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
   
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
     <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    
<script type="text/javascript">
   var jq = jQuery.noConflict();
   jq(document).ready(function() {
      var latlng;
      var g = google.maps;
      
      latlng = new g.LatLng(34.03, -84.19);
      
      var myOptions = {
        zoom: 6,
        center: latlng,
        mapTypeId: g.MapTypeId.ROADMAP
      };
      
      var map = new g.Map(document.getElementById("map_canvas"), myOptions);     
       
 
      var bounds = null;
      bounds = new g.LatLngBounds();
      
      showLocationPaths();
            
      //map.fitBounds(bounds);

      /**
       *  Show locations
       *
       */
      function showLocationPaths() {
         alert(" ---- start showLocationPaths");
         var color = '#FF0088';
         var dispLocations = "";
         jq.getJSON('${env.prefix}/displaycurrentlocation?groupId=gg1&days=5', function(data) {
            var dispLocations = "";
	    var j = 0;
            jq.each(data.dispLocations, function(index, dispItem) {
               var line = dispItem.line;
	       var locs = dispItem.locs;
		               						
	       if (index%2 == 1) {
	          color = '#FF0000';   //red
	       }
	       else {
		  color = '#0000FF'    //blue
	       }
	       alert(" == here ");
	       
	       var pts = [];
	       var lastPoint;
	       jq.each(locs, function(locIndex, loc) {				 
		    dispLocations += " loc.latitude=" + loc.latitude;
		    pts[locIndex] = new g.LatLng(loc.latitude/1.0e6, loc.longitude/1.0e6);
		    //alert(" index=" + index + ",locIndex=" + locIndex + ", lat=" + loc.latitude/1.0e6 + ", lng=" + loc.longitude/1.0e6);			              				
		    bounds.extend(pts[locIndex]);
		    lastPoint = pts[locIndex];
               });

               var lastLocMarker = new google.maps.Marker({
                  position: lastPoint,
                  map: map                 
               });        
               
	       var poly = new g.Polyline({
	    			 map: map,
	    			 path: pts,
	    			 strokeColor: color,
	    			 strokeOpacity: 1.0,
	    			 strokeWeight: 3
	    			 		       		             	
	        });
	        
	        
            }); <%-- end of data.dispLocations --%>
                
         }); <%-- end of getJSON --%>
      
         alert(" ---- end showLocationPaths");
      } <%-- end ofshowLocationPaths --%>
      
   }); <%-- end of ready --%>
</script>
</head>

<body>

<%@ include file="/jsp/common/i_header.jspf"%>
  <c:set var="fromPage" value="Show Active Tracks"/>
<%@ include file="/jsp/common/i_menu.jspf"%>

<div id="container"> 
   <table border="1">
       <tr> 
          <td width="200" valign="top"> 
            Left
          </td>
          <td valign="top" width="760" align="left">
             <div id="container">  
	   
	       <div id="map_canvas" style="width:700px; height:450px"></div>
             </div> 
          </td>
       </tr>
   </table>
</div>
      
        
<div id="container">
    <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/common/i_footer.jspf"%></div>
</div>    
    
<%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>
 