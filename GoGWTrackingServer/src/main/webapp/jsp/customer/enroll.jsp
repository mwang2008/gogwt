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
<c:set var="fromPage" value="Enroll"/>

<%@ include file="/jsp/tracking/i_home_menu.jspf"%>
<%-- @ include file="i_menu.jspf" --%>


<div id="container"> 

<table width="972" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <%-- left --%>
    <td align="left" width="500"> 
 
<br>
<form:form commandName="enrollCustomerFormBean" action="${env.prefix}/enroll" method="post">
   <spring:hasBindErrors name="enrollCustomerFormBean">
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
         <td> <label for="groupName"><fmt:message key='label.groupname'/></label>:   </td>
         <td> <form:input id="groupName" path="groupName" size="30" maxlength="50"/>   </td>
      </tr>      
      <tr> 
           <td> <label for="userName"><fmt:message key='label.userName'/></label>:   </td>
           <td> <form:input id="userName" path="userName" size="30" maxlength="30"/>   </td>
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
           <td> <label for="password"><fmt:message key='label.password'/></label>:   </td>
           <td> <form:password id="password" path="password" size="30" maxlength="30" showPassword="true"/>   </td>
       </tr>
       <tr> 
           <td> <label for="confirmPassword"><fmt:message key='label.confirmPassword'/></label>:   </td>
           <td> <form:password id="confirmPassword" path="confirmPassword" size="30" maxlength="100" showPassword="true"/>   </td>
       </tr>
        <tr> 
            <td>&nbsp;</td>
            <td colspan="1" align="center"> <input type="image" name="continue" src="${env.contextPath}/images/${env.languageId}-${env.countryId}/enroll.png" alt="<fmt:message key='button.Alt.Enroll'/>"  border="0" align="center">  </td>
       </tr>
   </table>
</form:form>
 
	</td>
	
	<td align="left">
	<table class="bw_content_area_border_4side" cellpadding="2" cellspacing="2" border="0">
     <tr>
       <td valign="top">   
          <table class="bw_promobox">
   	        <tr>
    		  <td class="bw_content_area_text12">
      		     <b>More Description:</b> <br>
      		     1. Once enroll, you will be the group owner.<br>
				 2. Mobile Phone should be your Anroid phone number.<br>
      		</td>
      	     </tr>
   	     </table>
   	  </td>
    </tr>
   </table>   
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