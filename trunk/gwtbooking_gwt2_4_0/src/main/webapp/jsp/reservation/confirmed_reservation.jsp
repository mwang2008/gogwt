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
       Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
   </div>
   <script type="text/javascript">
      var jq = jQuery.noConflict();
	  jq(document).ready(function() {
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
		    Click <a id="showWeatherPost" href="#"> here </a> to show whether near the Hotel
		  </td>  
       </tr>         

    </table>
   
</div>
      <div id="container">
          <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="i_footer.jspf"%></div>
      </div> 
 <%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>
