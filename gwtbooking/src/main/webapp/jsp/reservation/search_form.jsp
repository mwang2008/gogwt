<%--
 search_form.jsp
--%>
<%@ include file="/jsp/common/i_global.jspf"%>

<form:form commandName="searchFormBean" action="${env.contextPath}/${env.languageId}-${fn:toUpperCase(env.countryId)}/hotelsearch" method="post">
 <spring:hasBindErrors name="searchFormBean">
   <div id="ErrorContainer">
		<div id="Error"><form:errors path="*" cssClass="errorBox" /></div>
	</div>
       
</spring:hasBindErrors>

<div id="FormLabel"><label for="location"><fmt:message key='label.location'/></label></div>
<div id="ContentCopy">
  <form:input id="location" path="location" size="30" maxlength="100"/>
</div>

<div id="FormLabel"><label for="location"><fmt:message key='label.location'/></label></div>
<div id="ContentCopy">
<form:select id="radius" path="radius" size="1">
  <c:forEach var="index" begin="1" end="40">
     <form:option value="${index}" label="${index}"/>
  </c:forEach>
</form:select>
<div>				
<div id="ContentCopy">
   <input type="image" name="continue" src="${env.contextPath}/images/${env.languageId}-${fn:toUpperCase(env.countryId)}/findHotel.gif" alt="<fmt:message key='button.Alt.FindHotel'/>"  border="0" align="center">
</div>
				
</form:form>
