<%--
  search_result.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

 
<html>
 <head>
    <title> Hotel Search Result </title>    
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>

    <script type="text/javascript">
      function initialize() {
        var latlng = new google.maps.LatLng(${searchResponse.hotelSearchRequest.lat}, ${searchResponse.hotelSearchRequest.lng});        
        var myOptions = {
          zoom: 8,
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
       };
       var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

       //add city center marker
       var cityCenterImg = '${env.contextPath}/images/google_green_arrow.png';    
       var beachMarker = new google.maps.Marker({
          position: latlng,
          map: map,
          icon: cityCenterImg
        });
        
       //add hotel marker
       <c:forEach var="hotel" items="${searchResponse.hotelList}" varStatus ="status">   
          var hotelMarkerIcon;
          
          <c:choose>
            <c:when test="${status.count<50}">
               hotelMarkerIcon = '${env.contextPath}/images/marker${status.count}.png';
            </c:when>
            <c:otherwise>
               hotelMarkerIcon = '${env.contextPath}/images/blank.png';
            </c:otherwise>
          </c:choose>
         
          latlng = new  google.maps.LatLng(${hotel.lat}, ${hotel.lng}); 
          var beachMarker = new google.maps.Marker({
             position: latlng,
             map: map,
             icon: hotelMarkerIcon
          });
          
       </c:forEach>        
      }
    </script>

 
    
  </head>

  <body onload="initialize()">
       <h>Show hotels in Map  ${searchResponse.hotelSearchRequest.lat} | ${searchResponse.hotelSearchRequest.lng} xxxx</h>
         
       <div id="map_canvas" style="width:600; height:400"></div>

       <hr>
          <c:forEach var="hotel" items="${searchResponse.hotelList}" varStatus ="status">  
              <a href="${env.prefix}/hoteldetail?propertyId=${hotel.id}"> ${hotel.name}</a>  
               
              <a href="${env.prefix}/guestinfo?id=${hotel.id}">
                <img width="63" height="24" border="0" align="top" name="reserve_9" alt="Reservar" src="${env.contextPath}/images/${env.languageId}-${fn:toUpperCase(env.countryId)}/reserve.gif"/> 
                
              </a> <br>
          </c:forEach>
          
          
       </hr>       
  </body>
  
  </html>