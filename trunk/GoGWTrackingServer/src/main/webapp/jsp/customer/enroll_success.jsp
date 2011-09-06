<%--
   enroll_success.jsp
   http://local.www.gogwt.com/tracking/en-us/disptracks
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Enrollment </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
   
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
   
   <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
   <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>


<script>
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
             
      var lastLocMarker = new google.maps.Marker({
          position: latlng,
          map: map                 
      });
 
      var bounds = null;
      bounds = new g.LatLngBounds();
      
      //showPoly();
      //showPolyWithLines();
      showLocationPaths();
      
      map.fitBounds(bounds);
      
      
      /**
       *  Show location
       *
       */
      function showLocationPaths() {
          
         var color = '#FF0088';
         
         jq.getJSON('${env.prefix}/displaylocation?groupId=gg1&days=5', function(data) {
            
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
		//alert("mod="+index%2 + ", index="+index + " color = " + color);
		
		var pts = [];
 
	        jq.each(locs, function(locIndex, loc) {				 
		    dispLocations += " loc.latitude=" + loc.latitude;
		    pts[locIndex] = new g.LatLng(loc.latitude/1.0e6, loc.longitude/1.0e6);
		    //alert(" index=" + index + ",locIndex=" + locIndex + ", lat=" + loc.latitude/1.0e6 + ", lng=" + loc.longitude/1.0e6);			              				
		    bounds.extend(pts[locIndex]);
                });
           
	        var poly = new g.Polyline({
			 map: map,
			 path: pts,
			 strokeColor: color,
			 strokeOpacity: 1.0,
			 strokeWeight: 3
			 		       		             	
	        });
/*	        
                map.addOverlay(poly);		
*/		
            }); <%-- end of data.dispLocations --%>
            
         }); //end of getJSON
         
      }
      
      function showPolyWithLines() {
         alert(" === showPolyWithLines");
         
        
         var lat, lng, latlngLoc;
	 var myKeyTime, myDisp, myKeyWithTime;
	 	    
 
	 	    
	 	                myKeyTime = 'g1 dis|0';
	 	                var myKeyWithTime = myKeyTime.split('|');
	 	                var myDisp = myKeyWithTime[0];
 	                
	 	            var i=0;
	 	            var pts = [];
	 	            
	 	            
	 	                lat = 33299998/1.0e6;
	 	                lng = -83700000/1.0e6;
	 	                latlngLoc = new google.maps.LatLng(lat, lng);                
	 	                //path2.push(latlngLoc);
	 	                
	 	     	    pts[i++] = latlngLoc;
	 	     	    
	 	                lat = 33599998/1.0e6;
	 	                lng = -83500000/1.0e6;
	 	                latlngLoc = new google.maps.LatLng(lat, lng);                
	 	                //path2.push(latlngLoc);
	 	            pts[i++] = latlngLoc;
	 	    	        
	 	                lat = 33299998/1.0e6;
	 	                lng = -83800000/1.0e6;
	 	                latlngLoc = new google.maps.LatLng(lat, lng);                
	 	                //path2.push(latlngLoc);
	 	           pts[i++] = latlngLoc;
	 	             
	 	                lat = 34299998/1.0e6;
	 	                lng = -84700000/1.0e6;
	 	                latlngLoc = new google.maps.LatLng(lat, lng);                
	 	                //path2.push(latlngLoc);
	 	          pts[i++] = latlngLoc;
	 	     
	 	                lat = 34599998/1.0e6;
	 	                lng = -84500000/1.0e6;
	 	                latlngLoc = new google.maps.LatLng(lat, lng);                
	 	                //path2.push(latlngLoc);
	 	          pts[i++] = latlngLoc; 
	 	    	           
	 	                lat = 34299998/1.0e6;
	 	                lng = -84800000/1.0e6;
	 	                latlngLoc = new google.maps.LatLng(lat, lng);                
	 	                //path2.push(latlngLoc);
	 	          pts[i++] = latlngLoc;    
	 	          
	 	          alert("i=" + i);
	 	            
	 	          var color = '#FF0088';
	 	                   
	 	          var poly = new google.maps.Polyline({
	 		       		              map: map,
	 		       		              path: pts,
	 		       		              strokeColor: color,
	 		       		              strokeOpacity: 1.0,
	 		       		              strokeWeight: 3
	 		       		             	
	                  });
	                  
	                  map.addOverlay(poly);
	        
      }
      
         function showPoly() {
            alert(" === showPoly");
             
	            
	           
	            var polyOptions2 = {
	                     strokeColor: '#FF0088',
	                     strokeOpacity: 1.0,
	                     strokeWeight: 3
	                   }      
	           
	            var poly2 = new google.maps.Polyline(polyOptions2);       
	            poly2.setMap(map);              
	            var path2 = poly2.getPath(); 
	            
	      
	           //add gps location marker      
	    
	           var lat, lng, latlngLoc;
	           var myKeyTime, myDisp, myKeyWithTime;
	    
	                
	    
	                myKeyTime = 'g1 dis|0';
	                var myKeyWithTime = myKeyTime.split('|');
	                var myDisp = myKeyWithTime[0];
	                
	             
	                lat = 33299998/1.0e6;
	                lng = -83700000/1.0e6;
	                latlngLoc = new google.maps.LatLng(lat, lng);                
	                path2.push(latlngLoc);
	                
	     	     
	                lat = 33599998/1.0e6;
	                lng = -83500000/1.0e6;
	                latlngLoc = new google.maps.LatLng(lat, lng);                
	                path2.push(latlngLoc);
	                 
	                lat = 33299998/1.0e6;
	                lng = -83800000/1.0e6;
	                latlngLoc = new google.maps.LatLng(lat, lng);                
	                path2.push(latlngLoc);
	              
	                lat = 34299998/1.0e6;
	                lng = -84700000/1.0e6;
	                latlngLoc = new google.maps.LatLng(lat, lng);                
	                path2.push(latlngLoc);
	                 lat = 34599998/1.0e6;
	                lng = -84500000/1.0e6;
	                latlngLoc = new google.maps.LatLng(lat, lng);                
	                path2.push(latlngLoc);
	                    
	                lat = 34299998/1.0e6;
	                lng = -84800000/1.0e6;
	                latlngLoc = new google.maps.LatLng(lat, lng);                
	                path2.push(latlngLoc);
	             
	               
	                   
	                    
           alert("inner status=" + 2);	
         }
         
   }); <%-- end of rady --%>
</script>
</head>
<body>

<%@ include file="/jsp/tracking/i_header.jspf"%>
<%@ include file="i_menu.jspf"%>

<div id="container"> 
<br>
     Test JSON
  

  
     
     
</div>

<table> 
  <tr> 
     <td> <div id="dispLocations"></div></td> 
     <td> <div id="map_canvas" style="width:700px; height:450px"></div> </td>
  <tr>
</table>
Data from server:<br>
<div id="side_bar">
</div>
<hr>

---------------<br>
dispLocations<br>
<div id="dispLocations">
</div>
<br>============
<hr>
<div id="mylist">
</div>

<div id="mymap">
</div>



<div id="container">
    <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/tracking/i_footer.jspf"%></div>
</div>    

 <%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>