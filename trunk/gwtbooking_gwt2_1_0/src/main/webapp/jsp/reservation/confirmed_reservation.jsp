<%--
  confirmed_reservation.jsp
--%>
<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Reservation Confirmation </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
</head>
<body>

<%@ include file="i_header_menu.jspf"%>
 
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
    </table>
   
</div>
      <div id="container">
          <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="i_footer.jspf"%></div>
      </div> 
 <%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>
