<%--
  enroll.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Enrollment </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
</head>
<body>

<%@ include file="/jsp/tracking/i_header.jspf"%>
 
 <%@ include file="i_menu.jspf"%>

<div id="container"> 
<br>
<form:form commandName="modifyCustomerFormBean" action="${env.prefix}/viewaccount" method="post">
   <spring:hasBindErrors name="modifyCustomerFormBean">
      <div id="ErrorContainer">
	      <div id="Error"><form:errors path="*" cssClass="text12red" /></div>
      </div>  
   </spring:hasBindErrors>
   <br/>
      
   <table border="0">
      <tr> 
         <td colspan="2" align="center"> <b>View/Modify Account </b>  </td>
       
      </tr>
	  <tr> 
         <td colspan="2"> &nbsp; </td>
       
      </tr>
      <tr> 
         <td> <label for="groupId"><fmt:message key='label.GroupId'/></label>:   </td>
         <td> <form:hidden id="groupId" path="groupId"/> ${modifyCustomerFormBean.groupId}  </td>
      </tr>
      <tr> 
         <td> <label for="groupName"><fmt:message key='label.groupname'/></label>:   </td>
         <td> <form:input id="groupName" path="groupName" size="30" maxlength="50"/>   </td>
      </tr>      
      <tr> 
           <td> <label for="userName"><fmt:message key='label.userName'/></label>:   </td>
           <td> <form:hidden id="userName" path="userName"/> ${modifyCustomerFormBean.userName}  </td>
      </tr>

      <tr> 
         <td> <label for="firstName"><fmt:message key='label.First.Name'/></label>:   </td>
         <td> <form:input id="firstName" path="firstName" size="30" maxlength="30"/>   </td>
      </tr>
      <tr> 
         <td> <label for="lastName"><fmt:message key='label.Last.Name'/></label>:   </td>
         <td> <form:input id="lastName" path="lastName" size="30" maxlength="30"/>   </td>
      </tr>
      
      <tr> 
          <td> <label for="phoneNumber"><fmt:message key='label.phoneNumber'/></label>:   </td>
          <td> <form:input id="phoneNumber" path="phoneNumber" size="30" maxlength="100"/>   </td>
      </tr>
      
      <tr> 
          <td> <label for="email"><fmt:message key='label.email.address'/></label>:   </td>
          <td> <form:input id="email" path="email" size="30" maxlength="100"/>   </td>
      </tr>
      <tr> 
         <td colspan="2"> &nbsp; </td>
       
      </tr>
      <tr> 
            <td>&nbsp;</td>
            <td colspan="1" align="center">   
			  <input type="submit" name="continue" value="Update Account"  border="0" align="center"> 
			</td>
       </tr>
   </table>
</form:form>
</div>

 
<p>
<div id="container"> 
   <a href="${env.prefix}/changepassword" style="text-decoration:none">  <b>Change Password </b>  </a>  
 
</div>



      <div id="container">
          <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/tracking/i_footer.jspf"%></div>
      </div>    

 <%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>