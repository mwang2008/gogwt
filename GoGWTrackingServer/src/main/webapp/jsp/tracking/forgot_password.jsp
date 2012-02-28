<%--
  forgot_password.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Forgot password/goupdId </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>      

</head>
<body>

<%@ include file="/jsp/tracking/i_header.jspf"%>
<c:set var="fromPage" value="Enroll"/>

<%@ include file="/jsp/tracking/i_home_menu.jspf"%>
<%-- @ include file="i_menu.jspf" --%>


<div id="container"> 

<table width="972" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr><td> &nbsp;</td></tr>
  <tr><td> <h3> Forgot Password </h3></td></tr>
  <tr>
     <td class="bw_content_area_text12">
	   If you have forgotten or lost your password, <br>we can email your password to the email address in your profile.	  
	 </td>
  </tr>
  <tr><td> &nbsp;</td></tr>
  <tr>
    <%-- left --%>
    <td align="left" width="500"> 
<form:form commandName="forgotPasswordFormBean" action="${env.prefix}/forgotpassword" method="post">
    <input type="hidden" name="action"  value="forgetpassword"/>
   <spring:hasBindErrors name="forgotPasswordFormBean">
      <div id="ErrorContainer">
	      <div id="Error"><form:errors path="*" cssClass="text12red" /></div>
      </div>  
   </spring:hasBindErrors>
   <br/>
      
   <table border="0">    
      <tr> 
         <td> <label for="groupId"><fmt:message key='label.GroupId'/></label>:   </td>
         <td> <form:input id="groupId" path="groupId" size="30" maxlength="30"/>   </td>
      </tr>
        
      <tr> 
           <td> <label for="userName"><fmt:message key='label.userName'/></label>:   </td>
           <td> <form:input id="userName" path="userName" size="30" maxlength="30"/>   </td>
      </tr>        
      <tr> 
          <td> <label for="email"><fmt:message key='label.email.address'/></label>:   </td>
          <td> <form:input id="email" path="email" size="30" maxlength="100"/>   </td>
      </tr>
      <tr> 
          <td>&nbsp;</td>
          <td colspan="1" align="center"> 
			   <input type="submit" name="continue" value="Request New Password"  border="0" align="center"> 
		  </td>
       </tr>
   </table>
</form:form>
 
	</td>
  <tr>
</table>
</div>

   	

      <div id="container">
          <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/tracking/i_footer.jspf"%></div>
      </div>    

 <%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>