<%--
   enroll_success.jsp
   http://local.www.gogwt.com/tracking/en-us/disptracks
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> History Tracks </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
   
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
   
   <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
   <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>

<script type="text/javascript">
    var lineImg = '${env.contextPath}/images/square.png';
    var startImg = '${env.contextPath}/images/dd-start.png';
    var endImg = '${env.contextPath}/images/dd-end.png';
</script>

<script type="text/javascript">
  
   var jq = jQuery.noConflict();
   var map;
   var gpolys = [];
   var gmarkers = [];
   var side_bar_html = ""; 
   var infowindow = null;
  
   var bounds = null;
   var lineIcon = null;
   var startIcon = null;
   var endIcon = null;
   
   jq(document).ready(function() {
      var latlng;
      var g = google.maps;
      
      
      latlng = new g.LatLng(34.03, -84.19);
      
      var myOptions = {
        zoom: 6,
        center: latlng,
        mapTypeControl: true,
	//mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
	navigationControl: true,
        mapTypeId: g.MapTypeId.ROADMAP
      };
      
      map = new g.Map(document.getElementById("map_canvas"), myOptions);     
   
      
      lineIcon = new g.MarkerImage(lineImg,
                                         new google.maps.Size(11, 11),
                                         new google.maps.Point(0,0),                                  
                                         new google.maps.Point(5, 5));
      startIcon = new g.MarkerImage(startImg,
                                         new google.maps.Size(20, 34),
                                         new google.maps.Point(0,0),                                  
                                         new google.maps.Point(10, 30));
      /*
      endIcon = new g.MarkerImage(endImg,
                                         new google.maps.Size(20, 34),
                                         new google.maps.Point(0,0),                                  
                                         new google.maps.Point(10, 30));
      */
                     
      bounds = new g.LatLngBounds();
      infowindow = new g.InfoWindow({size: new google.maps.Size(150,50)});
      
      
      showLocationPaths();
      
     
      
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
               	
               	color = line.color;
		
		var html = line.html;
		var label = line.label;
		var length = 0;
		var point = null;
		
		//alert(" index=" + index + ",color=" +color );
		
		var pts = [];
		var startPt = null, endPt = null;
		
	        jq.each(locs, function(locIndex, loc) {				 
		    dispLocations += " loc.latitude=" + loc.latitude;
		    pts[locIndex] = new g.LatLng(loc.latitude/1.0e6, loc.longitude/1.0e6);
		    if (startPt == null) {
		        startPt = pts[locIndex];
		    }
		    
		    /*
		    if (locIndex >0) {
		       length += pts[locIndex-1].distanceFrom(pts[locIndex]);
		       if (isNaN(length)) { alert("["+locIndex+"] length="+length+" segment="+pts[locIndex-1].distanceFrom(pts[locIndex])) };
		    }
		    */
		   
		    //alert(" index=" + index + ",locIndex=" + locIndex + ", lat=" + loc.latitude/1.0e6 + ", lng=" + loc.longitude/1.0e6);			              				
		    bounds.extend(pts[locIndex]);
		    point = pts[parseInt(locIndex/2)];
		    
		    endPt = pts[locIndex];
                });
           
                
                
                var startMarker = new google.maps.Marker({
	   		   position: startPt,
	   		   map: map, 	
	   		   title: label,
	   		   icon: startIcon
                });
          
                /*
                var endMarker = new google.maps.Marker({
	  	   	   position: endPt,
	  	   	   map: map, 	
	  	   	   title: label,
	  	   	   icon: endIcon
                });
                */
                
	        var poly = new g.Polyline({
			 map: map,
			 path: pts,
			 strokeColor: color,
			 strokeOpacity: 1.0,
			 strokeWeight: 3
			 		       		             	
	        });
 	        
 	        createClickablePolyline(poly, html, label, point, length);
 	        
 	        	       
            }); <%-- end of data.dispLocations --%>
            
            
	    document.getElementById("side_bar").innerHTML = side_bar_html;
            map.fitBounds(bounds);
            
         }); //end of getJSON         
      }  <%-- end of function of showLocationPaths --%>
      
    }); <%-- end of ready --%>  
    
   /**
    *  createClickablePolyline
    *
    */
   function createClickablePolyline(poly, html, label, point, length) {
         
         gpolys.push(poly);
      
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
             
        }); 
         
        google.maps.event.addListener(poly,'mousemove', function(event) {
	     if (event) {
	          point = event.latLng;
	     }     
	     
	     
	     if (gmarkers) {
                for (var i=0; i < gmarkers.length; i++) {
                   gmarkers[i].setMap(null);
                }                     
                gmarkers.length = 0;
             }

	     var polyMarker = new google.maps.Marker({
		   position: point,
		   map: map, 	
		   draggable:true,
		   icon: lineIcon
             });
                          
             gmarkers.push(polyMarker);
             
             
             infowindow.setContent(contentString);	     	      
	     infowindow.setPosition(point);	      
	     infowindow.open(map);	                       
        }); 
        
      
        if (!label) {
            label = "polyline #"+poly_num;
        }
      
        label = "<a href='javascript:google.maps.event.trigger(gpolys["+poly_num+"],\"click\");'>"+label+"</a>";
      
        // add lines to sidebar html
        side_bar_html += '<input type="checkbox" id="poly'+poly_num+'" checked="checked" onclick="togglePoly('+poly_num+');">' + label + '<br />';      
        
      }  <%-- end of createClickablePolyline --%>
         
   
   
   /**
    * show/hide polyline
    *
    */
   function togglePoly(poly_num) {         
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
  <c:set var="fromPage" value="Show History Tracks"/>
<%@ include file="i_menu.jspf"%>

<div id="container"> 
   <table border="1">
       <tr> 
          <td width="200" valign="top"> 
            Left <div id="side_bar" style="height: 450px; overflow:auto"></div>
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
    <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/tracking/i_footer.jspf"%></div>
</div>    

 <%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>

