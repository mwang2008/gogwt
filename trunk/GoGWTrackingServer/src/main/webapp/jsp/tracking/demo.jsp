<%--
  demo.jsp
--%>
<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> DEMO </title>  
   
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
   <link rel="stylesheet" type="text/css" media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>


</head>
<body>
  <div id="wrapper">
  <div id="container">
    <%@ include file="i_header.jspf"%>
	<c:choose>
	   <c:when test="${env.customerProfile != null}">
	      <%@ include file="i_menu.jspf"%>
	   </c:when>
	   <c:otherwise>
	      <%@ include file="i_home_menu.jspf"%>
	   </c:otherwise>
	</c:choose>
    
    <div id="wrapperContent" style="position: relative">
	   <%@ include file="i_demo.jspf"%>
      
    </div> 
    <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="i_footer.jspf"%></div>
  </div>
</div>

<%@ include file="/jsp/common/i_analytics.jspf"%>