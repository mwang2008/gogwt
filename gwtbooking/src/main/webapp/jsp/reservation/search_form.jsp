<%--
 search_form.jsp
--%>
<%@ include file="/jsp/common/i_global.jspf"%>


<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Reservation </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
</head>
<body>

  <%@ include file="i_header_menu.jspf"%>

   <div id="container">

<form:form commandName="searchFormBean" action="${env.contextPath}/${env.languageId}-${fn:toUpperCase(env.countryId)}/hotelsearch" method="post">
 <spring:hasBindErrors name="searchFormBean">
   <div id="ErrorContainer">
       <div id="Error"><form:errors path="*" cssClass="text12red" /></div>
   </div>
       
</spring:hasBindErrors>

<div id="FormLabel"><label for="location"><fmt:message key='label.location'/></label></div>
<div id="ContentCopy">
  <form:input id="location" path="location" size="30" maxlength="100"/>
</div>

<div id="FormLabel"><label for="radius"><fmt:message key='label.radius'/></label></div>
<div id="ContentCopy">
<form:select id="radius" path="radius" size="1">
  <c:forEach var="index" begin="1" end="30">
     <form:option value="${index}" label="${index}"/>
  </c:forEach>
</form:select>
<div>				
<div id="ContentCopy">
   <div><input type="submit" value=" Find A Hotel " /></div>
</div>
				
</form:form>
  </div>
  
   <%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>

