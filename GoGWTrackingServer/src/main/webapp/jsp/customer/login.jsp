﻿<%--
  login.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Login </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>      

</head>
<body>

<c:set var="pageName" value="login"/>
<%@ include file="/jsp/tracking/i_header.jspf"%>
<c:set var="fromPage" value="Login"/>

<c:choose>
   <c:when test="${env.customerProfile != null}">
      <%@ include file="/jsp/tracking/i_menu.jspf"%>
   </c:when>
   <c:otherwise>
      <%@ include file="/jsp/tracking/i_home_menu.jspf"%>
   </c:otherwise>
</c:choose>

<div id="container"> 
<br>
 <form:form commandName="loginFormBean" action="${env.prefix}/login" method="post">
    <input type="hidden" name="successURL" value="${param.successURL}"/>
    <table> 
       <tr>
          <td colspan="2"> 
             <spring:hasBindErrors name="loginFormBean">
                <div id="ErrorContainer">
	                <div id="Error"><form:errors path="*" cssClass="text12red" /></div>
                </div>  
             </spring:hasBindErrors></td>
       </tr>
	   <c:choose>
	      <c:when test="${env.customerProfile != null && param.from == 'viewaccount'}">
	         <tr> 
                <td> <label for="groupId"><fmt:message key='label.GroupId'/></label>: </td>
                <td> <input type="hidden" name="groupId" value="${env.customerProfile.groupId}"/> ${env.customerProfile.groupId} </td>
             </tr>
             <tr> 
                <td> <label for="userName"><fmt:message key='label.userName'/></label>: </td>
                <td> <input type="hidden" name="userName" value="${env.customerProfile.userName}"/> ${env.customerProfile.userName}</td>
            </tr>	  
		  </c:when>
		  <c:otherwise>
		     <tr> 
                <td> <label for="groupId"><fmt:message key='label.GroupId'/></label>: </td>
                <td> <form:input id="groupId" path="groupId" size="30" maxlength="30"/></td>
             </tr>
             <tr> 
                <td> <label for="userName"><fmt:message key='label.userName'/></label>: </td>
                <td> <form:input id="userName" path="userName" size="30" maxlength="30" /></td>
            </tr>
		  </c:otherwise>
	   </c:choose>
  
        <tr> 
          <td> <label for="password"><fmt:message key='label.password'/></label>: </td>
          <td> <form:password path="password" size="30" maxlength="30" showPassword="true" /></td>
       </tr>
       <tr>
          <td> &nbsp; </td>
          <td colspan="1" align="center"> <input type="image" name="continue" src="${env.contextPath}/images/${env.languageId}-${env.countryId}/login.png" alt="<fmt:message key='button.Alt.Login'/>"  border="0" align="center">  </td>
       </tr>
	   
	   <tr>
          <td> &nbsp; </td>
          <td colspan="1" align="center">  
		   <br>
		    <div class="rTeaser"> Not a member?
	          <a class="quickEnrollPanel" href="/tracking/en-us/enroll" title="Join Now!">Join Now!</a>
            </div>	
		    <div class="rTeaser"> 
			  <a class="quickEnrollPanel" href="${env.prefix}/forgotpassword" title="Forgot Password">Forgot Password</a>
		    </div>				   	  
		  </td>
       </tr>
    </table>
</form:form>
</div>

 
<div id="container">
   <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/tracking/i_footer.jspf"%></div>
</div>    



<%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>