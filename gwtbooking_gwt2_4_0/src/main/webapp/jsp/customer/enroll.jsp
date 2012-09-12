<%--
  enroll.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Enroll Customer </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
</head>
<body>

<%@ include file="/jsp/reservation/i_header_menu.jspf"%>
 
<div id="container"> 
<br>
<form:form commandName="enrollCustomerFormBean" action="${env.prefix}/enroll" method="post">
   <spring:hasBindErrors name="enrollCustomerFormBean">
      <div id="ErrorContainer">
	      <div id="Error"><form:errors path="*" cssClass="text12red" /></div>
      </div>  
   </spring:hasBindErrors>
   <br/>
      
   <div id="FormLabel">
       <label for="firstName"><fmt:message key='label.Title'/></label>:  
       <form:select id="title" path="title">
	        <form:option value="">-- <fmt:message key='label.Title'/> --</form:option>
	        <form:options items="${titlePopulator}" itemLabel="display" itemValue="code"/>
       </form:select>	      
   </div>
   
   <div id="FormLabel"><label for="firstName"><fmt:message key='label.First.Name'/></label>:  <form:input id="firstName" path="firstName" size="30" maxlength="100"/> </div>
   
   <div class="clear"></div>
   <div id="FormLabel"><label for="lastName"><fmt:message key='label.Last.Name'/></label>:  <form:input id="lastName" path="lastName" size="30" maxlength="100"/> </div>
   <br/>
   <div id="FormLabel"><label for="dateOfBirth"><fmt:message key='label.Date.Birth'/></label>: 
    <form:select id="month" path="month">
	   <form:option value="-1"> Month </form:option>
	   <form:options items="${monthPopulator}" itemLabel="display" itemValue="code"/>
    </form:select>		
    &nbsp;
    <form:select id="day" path="day">
	   <form:option value="-1"> Day </form:option>
	   <form:options items="${dayPopulator}" itemLabel="display" itemValue="code"/>
    </form:select>	
    &nbsp;
    <form:select id="year" path="year">
	   <form:option value="-1"> Year </form:option>
	   <form:options items="${yearPopulator}" itemLabel="display" itemValue="code"/>
    </form:select>
   </div>
   <div id="FormLabel"><label for="Gender"><fmt:message key='label.Gender'/></label>:  
      <form:select id="gender" path="gender">
	      <form:option value=""> Select Gender </form:option>
	      <form:options items="${genderPopulator}" itemLabel="display" itemValue="code"/>
      </form:select>        
   </div>
   
   <div id="FormLabel"><label for="email"><fmt:message key='label.email.address'/></label>:  
       <form:input id="email" path="email" size="30" maxlength="100"/>
   </div>
   
   <div id="FormLabel"><label for="confirmEmail"><fmt:message key='label.confirmEmail'/></label>:  
       <form:input id="confirmEmail" path="confirmEmail" size="30" maxlength="100"/>
   </div>

   <div id="FormLabel"><label for="userName"><fmt:message key='label.userName'/></label>:  
       <form:input id="userName" path="userName" size="30" maxlength="100"/>
       <div style="color: rgb(50, 50, 50); font-size: 80%;"> 
          &nbsp;&nbsp;&nbsp; Usernames must be at least 5 characters, and alphanumeric only (A-Z / 0-9). No spaces or special characters are allowed.     
       </div>
   </div>
   
   <div id="FormLabel"><label for="password"><fmt:message key='label.password'/></label>:  
       <form:password path="password" size="30" maxlength="100"   />
   </div>
   <div id="FormLabel"><label for="confirmPassword"><fmt:message key='label.confirmPassword'/></label>:  
       <%--   <form:password  path="confirmPassword" size="30" maxlength="100"  showPassword="true"/> --%>
       <form:password  path="confirmPassword" size="20" maxlength="20"/>
   </div>
   
   <div style="color: rgb(50, 50, 50); font-size: 80%;"> 
      &nbsp;&nbsp;&nbsp; Your password should be at least 6 characters long, and include a capital letter(s) and/or number(s).
   </div>
   
   <div id="FormLabel"> <form:checkbox id="agree" path="agree" value="yes"/> <label for="agree"><fmt:message key='enroll.agree.term'/></label>:  
      
   </div>
   
   <br/>
   <div id="ContentCopy">
      <input type="image" name="continue" src="${env.contextPath}/images/${env.languageId}-${fn:toUpperCase(env.countryId)}/enroll.png" alt="<fmt:message key='button.Alt.Enroll'/>"  border="0" align="center">     
   </div>
   
</form:form>
 
</div>


      <div id="container">
          <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/reservation/i_footer.jspf"%></div>
      </div>    

 <%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>