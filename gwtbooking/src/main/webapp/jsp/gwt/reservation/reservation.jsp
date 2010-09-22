<%--
   reservation.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>



<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">
   <meta name="gwt:property" content="locale=${env.languageId}_${fn:toLowerCase(env.countryId)}" />
   
   <title> Reservation </title>
   <script type="text/javascript" language="javascript" src="${env.contextPath}/com.gogwt.app.booking.gwt.reservation.ReservationEntry/com.gogwt.app.booking.gwt.reservation.ReservationEntry.nocache.js"></script>
      
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
 
   
  
   <script type="text/javascript" >
      var envJson = '${fn:replace(envJson,"'","\\'")}';
   
   </script>

</head>
<body>

<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>


<div id="wrapper">
  <div id="container">
     <div id="header" style="position: relative"></div>
     <div id="menu" style="position: relative"></div>    
     <div id="wrapperContent" style="position: relative"></div>
   
     <div id="footer" style="position: relative"></div>
  </div>
</div>   

 

</body>
</html>

