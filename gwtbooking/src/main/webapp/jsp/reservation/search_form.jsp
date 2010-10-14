<%--
 search_form.jsp
--%>
<%@ include file="/jsp/common/i_global.jspf"%>


<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Reservation </title>  
   <script type="text/javascript" src="${env.contextPath}/script/jquery.js"></script>     
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
   
</head>
<body>


 
<div id="wrapper">
  <div id="container">
    <%@ include file="i_header_menu.jspf"%>
    <div id="wrapperContent" style="position: relative"><%@ include file="i_search_form.jspf"%></div> 
    <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="i_footer.jspf"%></div>
  </div>
</div>

<%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>

