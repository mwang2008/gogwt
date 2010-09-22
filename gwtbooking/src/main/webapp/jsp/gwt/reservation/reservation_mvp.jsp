<%--
   reservation.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>



<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">
   <meta name="gwt:property" content="locale=${env.languageId}_${fn:toLowerCase(env.countryId)}" /> 
   
   <title> Reservation </title>
   <script type="text/javascript" language="javascript" src="${env.contextPath}/com.gogwt.app.booking.gwt.mvpreservation.ReservationMVPModule/com.gogwt.app.booking.gwt.mvpreservation.ReservationMVPModule.nocache.js"></script>
     
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
   
   
    
   <script type="text/javascript" >
      var envJson = '${fn:replace(envJson,"'","\\'")}';
   
   </script>

   <%@ include file="i_google_mapapi_key.jspf"%>

</head>
<body>
<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>

<style type="text/css">
.myfooter
{
position:relative;
border:5px solid gray;
margin:0px;
top:150px;
}
div.ex
{
width:220px;
padding:10px;
border:5px solid gray;
margin:0px;
}
</style>

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

