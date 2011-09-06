<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
  
<html>
 <head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Show Tracks </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
     
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>

    <script type="text/javascript">
      var latlng;
      function initialize() {
        
        latlng = new google.maps.LatLng((35999998+33299998)/2000000.00, (-83500000+-85500000)/2000000.00);               
   

        var myOptions = {
          zoom: 9,
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
 
        var lastLocMarker = new google.maps.Marker({
           position: latlng,
           map: map                 
        });
 
        var polyOptions1 = {
          strokeColor: '#000000',
          strokeOpacity: 1.0,
          strokeWeight: 3
        }      

        var poly1 = new google.maps.Polyline(polyOptions1);       
        poly1.setMap(map);              
        var path1 = poly1.getPath();
       
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

       alert("here2ssss");
 
           

            myKeyTime = 'g1 dis|0';
            var myKeyWithTime = myKeyTime.split('|');
            var myDisp = myKeyWithTime[0];

                   
alert("myDisp=" + myDisp);
	    alert("inner status=" + 1);	        
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
    </script>    
  </head>

  <body onload="initialize()">

<%@ include file="/jsp/common/i_header.jspf"%>
  <c:set var="fromPage" value="Show Tracks"/>
<%@ include file="/jsp/common/i_menu.jspf"%>

<div id="container"> 
   <table border="1">
       <tr> 
          <td width="200" valign="top"> 
            Left 11
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
 