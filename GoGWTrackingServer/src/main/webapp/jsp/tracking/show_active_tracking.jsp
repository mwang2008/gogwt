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
    
<script language=javascript>
     
   var jq = jQuery.noConflict();
   var map;
   var gpolys = [];
   var gmarkers = [];
   
   var side_bar_html = ""; 
   var infowindow = null;
   var bounds = null;
   
   var timer; 
   var totalRuntime = 0;
   var mylocs = "";
   
   jq(document).ready(function() {
     
      
      //hidden auto refersh button            
      document.getElementById('autoRefersh').style.visibility='hidden';
       
      startTimer();
      
      var latlng;
      var g = google.maps;
  
      //latlng = new g.LatLng(34.03, -84.19);
      latlng = new g.LatLng(41.30, 122.00);
                    
      var myOptions = {
                     zoom: 6,
                     center: latlng,
                     mapTypeId: g.MapTypeId.ROADMAP
             };
       
      map = new g.Map(document.getElementById("map_canvas"), myOptions); 
             
      bounds = new g.LatLngBounds();
      infowindow = new google.maps.InfoWindow({size: new google.maps.Size(150,50)});
                                       
      showMaps(map);
      
  
 

      /*------------------------------------------------------+
       | Actions                                              | 
       +------------------------------------------------------*/       
      jq('#autoRefersh').click(function(){           
           document.getElementById('autoRefersh').style.visibility='hidden';
           startTimer();
      }); 
      
      /*------------------------------------------------------+
       | Functions                                            | 
       +------------------------------------------------------*/ 
      
      function showLines(map, data) {
         
         var dispLocations = "";
	 var j = 0;
	 
	 if (!data.dispLocations) {
	     //alert(" ---- No tracking available ---- ");	
	     side_bar_html = "No tracking yet";
	     document.getElementById("side_bar").innerHTML = side_bar_html;
	 }
	 
	 
	 
	 jq.each(data.dispLocations, function(index, dispItem) {
	     var line = dispItem.line;
	     var locs = dispItem.locs;
	     		               						
	     color = line.color;
	     	       		
	     var html = line.html;
	     var label = line.label;
	     var length = 0;
	     var point = null;
	     	       
	     mylocs = "== data=" + data.dispLocations.length + ", locs="+locs.length; 		
	     document.getElementById("mylocs").innerHTML = mylocs;
	     
	     var pts = [];
	     var lastPoint;
	     jq.each(locs, function(locIndex, loc) {				 
	        dispLocations += " loc.latitude=" + loc.latitude;
	        pts[locIndex] = new g.LatLng(loc.latitude/1.0e6, loc.longitude/1.0e6);
	        //alert(" index=" + index + ",locIndex=" + locIndex + ", lat=" + loc.latitude/1.0e6 + ", lng=" + loc.longitude/1.0e6);			              				
	        bounds.extend(pts[locIndex]);
	        lastPoint = pts[locIndex];
	        point = pts[parseInt(locIndex/2)];
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
	        
	     createClickablePolyline(poly, lastLocMarker, html, label, point, length);
	     
             
	 }); <%-- end of jq.each(data.dispLocations --%>
	 
	 document.getElementById("side_bar").innerHTML = side_bar_html;
	 map.fitBounds(bounds);

      }


      function createClickablePolyline(poly, polyMarker, html, label, point, length) {              
          gpolys.push(poly);
          gmarkers.push(polyMarker);
          
         var poly_num = gpolys.length - 1;
               
                  if (!html) {html = "";}
                  else { html += "<br>";}
               
               	 length = length * 0.000621371192; // convert meters to miles
               
                  html += "length="+length.toFixed(2)+" miles";
               
                 
                  var contentString = html;
               
                  
                 google.maps.event.addListener(poly,'click', function(event) {
               
                   infowindow.setContent(contentString);
             
                   if (event) {
                      point = event.latLng;
                   }
               
                   infowindow.setPosition(point);
               
                   infowindow.open(map);
                      // map.openInfoWindowHtml(point,html); 
                 }); 
                  
                    
                  google.maps.event.addListener(poly,'click', function(event) {
      	            
      	                infowindow.setContent(contentString);
      	          
      	                if (event) {
      	                   point = event.latLng;
      	                }
      	            
      	                infowindow.setPosition(point);
      	            
      	                infowindow.open(map);
      	                   // map.openInfoWindowHtml(point,html); 
                 }); 
                  
                google.maps.event.addListener(poly,'mouseover', function(event) {
      	 	            
      	 	                infowindow.setContent(contentString);
      	 	          
      	 	                if (event) {
      	 	                   point = event.latLng;
      	 	                }
      	 	            
      	 	                infowindow.setPosition(point);
      	 	            
      	 	                infowindow.open(map);
      	 	                   // map.openInfoWindowHtml(point,html); 
                 }); 
               
                 /*
                 google.maps.event.addListener(poly, 'mouseout', function() {
      	     infowindow.close();
                 });
                 */
                 if (!label) {
                     label = "polyline #"+poly_num;
                 }
               
                 label = "<a href='javascript:google.maps.event.trigger(gpolys["+poly_num+"],\"mouseover\");'>"+label+"</a>";
               
                 // add lines to sidebar html
                 side_bar_html += '<input type="checkbox" id="poly'+poly_num+'" checked="checked" onclick="togglePoly('+poly_num+');">' + label + '<br />';      
                 
               }  <%-- end of createClickablePolyline --%>
                  
            
            
      /**
       * show/hide polyline
       */
       /*
      function togglePoly(poly_num) {    
         alert(" togglePoly poly_num="+poly_num + ", gpolys.length=" + gpolys.length);
         if (document.getElementById('poly'+poly_num)) {
            if (document.getElementById('poly'+poly_num).checked) {
                gpolys[poly_num].setMap(map);
             } else {
                gpolys[poly_num].setMap(null);
            }
         }
      }    
      */
      function resetMap(map) {
         side_bar_html = "";
        
         if (gpolys && gpolys.length>0) {        
            for (var i=0; i < gpolys.length; i++) {
               gpolys[i].setMap(null);
	    }                     
            gpolys.length = 0;
         }
         
         if (gmarkers && gmarkers.length>0) {
           for (var i=0; i < gmarkers.length; i++) {
	      gmarkers[i].setMap(null);
	   }                     
           gmarkers.length = 0;
         }
      }
      
      function showMaps(map) {         
         var color = '#FF0088';
         var dispLocations = "";
         
         jq.getJSON('${env.prefix}/displaycurrentlocation?groupId=gg1&days=5', function(data) {
              //alert(" -- before showLines");              
              showLines(map, data);
         }); <%-- end of getJSON --%>         
      }
      
      function nextCycle() {         
         totalRuntime++;
         document.getElementById("xtimer").innerHTML = "totalRuntime=" + totalRuntime;
         
         
         showMaps(map);
         resetMap(map);
         if (totalRuntime > 40) { 
             totalRuntime = 0;
	     document.getElementById('autoRefersh').style.visibility='visible';
             stopRotation(); 
         }
      }
      
      function stopRotation() {
         totalRuntime = 0;
      	 clearInterval(timer);
      }
      
      function startTimer() {
      	timer = setInterval(nextCycle, 3000);
      }
      
       
   });<%-- jq(document).ready --%>
   
   /**
    * show/hide polyline
    */
   function togglePoly(poly_num) {    
      //alert(" togglePoly poly_num="+poly_num + ", gpolys.length=" + gpolys.length);
      if (document.getElementById('poly'+poly_num)) {
         if (document.getElementById('poly'+poly_num).checked) {
            gpolys[poly_num].setMap(map);
         } else {
            gpolys[poly_num].setMap(null);
         }
      }
   }    
   
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
             <form name=xcv>
	        <input id="autoRefersh" type=button onClick="startTimerBtn()" value="Start Auto Refresh">	      
             </form>
            <div id="xtimer"> starting auto refresh </div><hr>
            <div id="mylocs">locations </div><hr>
             <div id="side_bar" style="height: 450px; overflow:auto"></div>
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
 