<%--
  enroll.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Change Passwords </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
</head>
<body>

<%@ include file="/jsp/tracking/i_header.jspf"%>
 
 <%@ include file="i_menu.jspf"%>

 
 <div id="container"> 
<br>
<form:form commandName="passwordFormBean" action="${env.prefix}/changepassword" method="post">
   <spring:hasBindErrors name="passwordFormBean">
      <div id="ErrorContainer">
	      <div id="Error"><form:errors path="*" cssClass="text12red" /></div>
      </div>  
   </spring:hasBindErrors>
   <br/>
   <form:hidden id="customerId" path="customerId"/> 
   <form:hidden id="groupId" path="groupId"/>
   <form:hidden id="userName" path="userName"/> 
   <table border="0">
      <tr> 
         <td colspan="2" align="center"> <b>Change Password </b></td>       
      </tr>
	  <tr> 
         <td colspan="2" align="center"> &nbsp;</td>       
      </tr>
      <tr> 
         <td> <label for="oldPass"><fmt:message key='label.old.password'/></label>:   </td>
         <td>  <form:input id="oldPass" path="oldPass" size="30" maxlength="50"/>  </td>
      </tr>
      <tr> 
         <td> <label for="newPass"><fmt:message key='label.new.password'/></label>:   </td>
         <td> <form:input id="newPass" path="newPass" size="30" maxlength="50"/>   </td>
      </tr>          
      <tr> 
         <td> <label for="newPassConfirm"><fmt:message key='label.new.confirmPassword'/></label>:   </td>
         <td> <form:input id="newPassConfirm" path="newPassConfirm" size="30" maxlength="30"/>   </td>
      </tr>
     
	  <tr> 
         <td colspan="2" align="center"> &nbsp;</td>       
      </tr>
      <tr> 
            <td>&nbsp;</td>
            <td colspan="1" align="center">   
			  <input type="submit" name="continue" value="Change Password"  border="0" align="center"> 
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