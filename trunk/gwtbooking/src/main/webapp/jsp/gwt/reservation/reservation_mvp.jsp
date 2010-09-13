<%--
   reservation.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>



<html lang="${env.languageId}">
<head>
   <title> Reservation </title>
   <script type="text/javascript" language="javascript" src="${env.contextPath}/com.gogwt.app.booking.gwt.mvpreservation.ReservationMVPModule/com.gogwt.app.booking.gwt.mvpreservation.ReservationMVPModule.nocache.js"></script>
    
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
    
   <script type="text/javascript" >
      var envJson = '${fn:replace(envJson,"'","\\'")}';
   
   </script>

   <script src="http://maps.google.com/maps?gwt=1&file=api&v=2&key=ABQIAAAAmdQW6A4k5xzDjjGKjkBoGxTNnYUpi0vJmWXtC66R5nqEvj2QuxSWoIfZsVFbr3by_GoWAub_tmLJLA"></script>  

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

