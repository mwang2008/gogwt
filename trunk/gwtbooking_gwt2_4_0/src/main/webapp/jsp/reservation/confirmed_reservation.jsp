<%--
  confirmed_reservation.jsp
--%>
<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Reservation Confirmation </title>       
   
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
   
   
</head>
<body>

<%@ include file="i_header_menu.jspf"%>
 
   <div id="terms" style="display:none;">
       All right preserved.
   </div>
   <script type="text/javascript">
      var jq = jQuery.noConflict();
      	 
	  jq(document).ready(function() {
	    //when click show whether, call REST webservice with POST, in turn call SOAP 
        jq('#showWeatherPost').click(function(){    
    	   jq.ajax({
              type: 'POST',
              contentType: 'application/json',
              url: '/gservice/en/restweather?callback=?',
              //dataType: "json",
		      data: '{"zip":"${reservation.selectHotel.zipCode}", "temperatureType":"F"}',
              success: function(data, textStatus, jqXHR){
  			     var dialogContent = '<table border="0" width="100%">';
			     if (data.success) {
			        dialogContent += "<tr><td><b>City </b></td><td>" + data.city + "</td></tr>";
				    dialogContent += "<tr><td><b>State</b></td><td>" + data.state + "</td></tr>";
				    dialogContent += "<tr><td><b>Zip</b></td><td>" + data.zip + "</td></tr>";
				    dialogContent += "<tr><td><b>Temperature</b></td><td>" + data.temperature +  data.temperatureType + "</td></tr>";
				    dialogContent += "<tr><td><b>Description</b></td><td>" + data.description + "</td></tr>";
			    }
			    else {
                    dialogContent += "<tr><td><b>Error: </b></td><td>" + data.responseText + "</td></tr>";
                }		
                dialogContent += "</table>"		
                jq('#termsDialog').html(dialogContent).dialog({modal:true, title:"  Weather near ${reservation.selectHotel.name}", width:500, height:300}).dialog('open');			 			
              },
              error: function(jqXHR, textStatus, errorThrown){
                 alert('addWine error: ' + textStatus);
              }
		   
            });
        });
	    /*
	    $(document).mousemove(function (e) {
          $("#d").dialog("option", { position: [e.pageX+5, e.pageY+5] 
		});
		
		$("#d").dialog({
           autoOpen: false,
           show: "blind",
           hide: "explode"
        });
        $("#c").bind("mouseover", function () {
           $("#d").dialog('open'); // open
        });
        $("#c").bind("mouseleave", function () {
           $("#d").dialog('close'); // open
        });
		*/
	});
	 
   </script>


   
<div id="container"> 
           
    <table> 
       <tr> 
          <td> <b> <fmt:message key='reservation.resnumber'/></b> </td> 
          <td> ${gogwtutil:dispReservationNum(reservation.reserveNum)}    </td>
       </tr>
    </table>
    
    <table> 
       <tr>
          <td class="text12blue"> Guest Information  </td>  
       </tr>
       <tr>
          <td> <b>${reservation.guestInfo.firstName} ${reservation.guestInfo.lastName}</b></td>  
       </tr>
       <tr>
          <td> ${gogwtutil:fullGuestAddress(reservation.guestInfo)} </td>  
       </tr>         
    </table>
    
    <br/>
    
    <table> 
       <tr>
          <td class="text12blue"> Hotel Information </td>  
       </tr>
       <tr>
          <td> <b>${reservation.selectHotel.name} </b></td>  
       </tr>
       <tr>
          <td> ${gogwtutil:fullHotelAddress(reservation.selectHotel)} </td>  
       </tr>         

       <tr>
          <td>  </td>  
       </tr>         
	   
       <tr>
          <td> <div id="termsDialog" style="display:none;"> </div>
		    Click <a id="showWeatherPost" href="#"> show whether </a> to get the whether information near the Hotel
		  </td>  
       </tr>         

    </table>
   
 
</div>
      <div id="container">
          <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="i_footer.jspf"%></div>
	 </div> 
     <div id="container">
	    <br>
	      <table  class="gwt-DialogBox" cellspacing="0" cellpadding="0" width="60%"> 
       <tr>
          <td class="text12blue"> *** Note: The Technical Detail of Show Whether Function *** </td>  
       </tr>
	    <tr>
          <td> when click show whether, a POST call is made to REST webservice (/gservice/en/restweather), in turn to call SOAP API[http://www.webservicex.net/usweather.asmx?WSDL] with CFX. JSON object is returned to be displayed in JQuery dialog.
		  <br><br>
		  <a href="/gservice/"> Other services hosted in gservice </a>
		  
		  <!--
             RestController.retrieveWeatherPOST(...)    RestDomainService.WeatherResponse getWeather(WeatherForm weatherForm)
          -->		  
		  </td>  
       </tr>    
	 </table>
	  </div>
 <%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>
