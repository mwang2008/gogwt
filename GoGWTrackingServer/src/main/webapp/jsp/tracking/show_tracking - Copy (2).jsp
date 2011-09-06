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
        <c:choose>
           <c:when test="${hasResult}">
              latlng = new google.maps.LatLng((${res.maxLat}+${res.minLat})/2000000.00, (${res.maxLng}+${res.minLng})/2000000.00);               
           </c:when>
           <c:otherwise>
               latlng = new google.maps.LatLng(34.03, -84.19);
           </c:otherwise>
        </c:choose>
          
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
        
           
       var polyOptions = {
          strokeColor: '#000000',
          strokeOpacity: 1.0,
          strokeWeight: 3
       }
      
       var poly = new google.maps.Polyline(polyOptions);
       poly.setMap(map);
              
       var path = poly.getPath();

       //add gps location marker      
       var lat, lng, latlngLoc;
       var myKeyTime, myDisp, myKeyWithTime;
       
       alert("here2");
       <c:forEach var="entry" items="${res.locDataMap}" varStatus="status">
            alert("status=" + ${status.count});
            myKeyTime = '${entry.key}';
            var myKeyWithTime = myKeyTime.split('|');
            var myDisp = myKeyWithTime[0];
            alert(myDisp);
            
            <c:forEach var="loc" items="${entry.value}" varStatus="valStatus">
                lat = ${loc.latitude}/1.0e6;
                lng = ${loc.longitude}/1.0e6;
                latlngLoc = new google.maps.LatLng(lat, lng);                
                path.push(latlngLoc);
            </c:forEach>
  
       </c:forEach> 
       alert("here3");
         
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
 