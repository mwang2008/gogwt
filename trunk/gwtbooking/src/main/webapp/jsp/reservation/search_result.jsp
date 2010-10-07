<%--
  search_result.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>
 
 
<html>
 <head>
    <title> Hotel Search Result </title>    
    <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
    
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>

    <script type="text/javascript">
      function initialize() {
        var latlng = new google.maps.LatLng(${searchResponse.centerGeocode.lat}, ${searchResponse.centerGeocode.lng});        
        var myOptions = {
          zoom: 9,
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
       <%@ include file="i_header_menu.jspf"%>
       
       <div id="container">  
          <br>
          <div id="map_canvas" style="width:500px; height:300px"></div>
       </div> 
       <br>
       <div id="container"> 
          <c:forEach var="hotel" items="${searchResponse.hotelList}" varStatus ="status">  
              <div class="result_area_border"> 
                 <div class='item'> <B>${status.count}, ${hotel.name} </B> <span class="distance"> <fmt:formatNumber value="${hotel.distance}" maxFractionDigits="2"/> Miles </span></div>
                 <div class='item'> ${hotel.address} ${gogwtutil:fullHotelAddress(hotel)} </div>
                 <div class='item'> <b>Amenities:</b> ${gogwtutil:fillHotelAmenities(hotel)} </div>
                 <div class='btnSelect'> 
                    <a href="${env.prefix}/guestinfo?id=${hotel.id}&index=${status.count}">
                      <img width="63" height="24" border="0" align="top" name="reserve_9" alt="Reservar" src="${env.contextPath}/images/${env.languageId}-${fn:toUpperCase(env.countryId)}/reserve.gif"/> 
                    </a>
                 </div>
             </div>
          </c:forEach>
      </div>    
          
       </hr>       
       
  <%@ include file="/jsp/common/i_analytics.jspf"%>
  </body>
  
  </html>
  
 