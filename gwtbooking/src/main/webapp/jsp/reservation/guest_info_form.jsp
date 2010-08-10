<%--
  guest_info_form.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<form:form commandName="guestInfoFormBean" action="${env.prefix}/guestinfo" method="post">
<form:hidden path="id" />
 <spring:hasBindErrors name="guestInfoFormBean">
   <div id="ErrorContainer">
		<div id="Error"><form:errors path="*" cssClass="errorBox" /></div>
	</div>  
</spring:hasBindErrors>

<div id="FormLabel"><label for="firstName"><fmt:message key='label.First.Name'/></label></div>
<div id="ContentCopy">
  <form:input id="firstName" path="firstName" size="30" maxlength="100"/>
</div>

<div id="FormLabel"><label for="lastName"><fmt:message key='label.Last.Name'/></label></div>
<div id="ContentCopy">
  <form:input id="lastName" path="lastName" size="30" maxlength="100"/>
</div>

<div id="FormLabel"><label for="address"><fmt:message key='Label.address'/></label></div>
<div id="ContentCopy">
  <form:input id="address" path="address" size="30" maxlength="100"/>
</div>
<div id="FormLabel"><label for="city"><fmt:message key='Label.city'/></label></div>
<div id="ContentCopy">
  <form:input id="city" path="city" size="30" maxlength="100"/>
</div>
<div id="FormLabel"><label for="state"><fmt:message key='Label.state'/></label></div>
<div id="ContentCopy">
   <form:select id="stateId" path="stateId">
	<form:option value=""><fmt:message key='Label.state'/></form:option>
	<form:options items="${statePopulator}" itemLabel="stateName" itemValue="stateId"/>
   </form:select>			
</div>

<div id="FormLabel"><label for="zip"><fmt:message key='Label.zip'/></label></div>
<div id="ContentCopy">
  <form:input id="zipCode" path="zipCode" size="10" maxlength="10"/>
</div>


<div id="FormLabel"><label for="email"><fmt:message key='label.email'/></label></div>
<div id="ContentCopy">
  <form:input id="email" path="email" size="30" maxlength="100"/>
</div>

 			
<div id="ContentCopy">
   <input type="image" name="continue" src="${env.contextPath}/images/${env.languageId}-${fn:toUpperCase(env.countryId)}/confirm.gif" alt="<fmt:message key='button.Alt.Book'/>"  border="0" align="center">
</div>
				
</form:form>