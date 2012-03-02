<%--
  forgot_password.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Contact US </title>       
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
  <tr><td> <h3> Contact US </h3></td></tr>
  <tr>
     <td class="bw_content_area_text12">
	   Please fill in following form to contact us, thanks.
	 </td>
  </tr>
  <tr><td> &nbsp;</td></tr>
  <tr>
    <%-- left --%>
    <td align="left" width="500"> 
<form:form commandName="contactUsFormBean" action="${env.prefix}/contactus" method="post">
    <input type="hidden" name="action"  value="forgetpassword"/>
   <spring:hasBindErrors name="contactUsFormBean">
      <div id="ErrorContainer">
	      <div id="Error"><form:errors path="*" cssClass="text12red" /></div>
      </div>  
   </spring:hasBindErrors>
   <br/>
      
   <table border="0">    
      <tr> 
         <td> <label for="groupId">Your Name</label>:   </td>
         <td> <form:input id="name" path="name" size="30" maxlength="60"/>   </td>
      </tr>
        
      <tr> 
           <td> <label for="userName">Your Email </label>:   </td>
           <td> <form:input id="email" path="email" size="30" maxlength="100"/>   </td>
     </tr>
     <tr> 
           <td> <label for="userName">Your Phone </label>:   </td>
           <td> <form:input id="phone" path="phone" size="30" maxlength="100"/>   </td>
     </tr>	 
     <tr> 
           <td> <label for="userName">Subject </label>:   </td>
           <td> <form:input id="subject" path="subject" size="30" maxlength="200"/>   </td>
     </tr>          
     <tr> 
          <td> <label for="email">Comments </label>:   </td>
          <td valign="middle"> <form:textarea  id="comment" path="comment"  rows="5" cols="30" />  (max 2000 characters) </td>
      </tr>
      <tr> 
          <td>&nbsp;</td>
          <td colspan="1" align="left"> 
			   <input type="submit" name="continue" value="Submit"  border="0" align="center"> 
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