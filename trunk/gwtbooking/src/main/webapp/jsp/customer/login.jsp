<%--
  login.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Login </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
</head>
<body>

<%@ include file="/jsp/reservation/i_header_menu.jspf"%>
 
<div id="container"> 
<br>
 <form:form commandName="loginFormBean" action="${env.prefix}/login" method="post">
   <spring:hasBindErrors name="loginFormBean">
      <div id="ErrorContainer">
	      <div id="Error"><form:errors path="*" cssClass="text12red" /></div>
      </div>  
   </spring:hasBindErrors>
   <br/>
    
   <div id="FormLabel"><label for="userName"><fmt:message key='label.userName'/></label>:  
       <form:input id="userName" path="userName" size="30" maxlength="100"/>
   </div>
   <div class="clear"></div>

   <div id="FormLabel"><label for="password"><fmt:message key='label.password'/></label>:  
       <form:password path="password" size="30" maxlength="100"/>
   </div>

   <div id="ContentCopy">
   <div id="FormLabel"> </div>
      <input type="image" name="continue" src="${env.contextPath}/images/${env.languageId}-${fn:toUpperCase(env.countryId)}/login.png" alt="<fmt:message key='button.Alt.Login'/>"  border="0" align="center">     
   </div>  
</form:form>

 
<div id="container">
    <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/reservation/i_footer.jspf"%></div>
</div>    

</div>

<%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>