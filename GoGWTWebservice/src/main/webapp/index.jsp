<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:url var="thisURL" value="en/hello">
</c:url>
<li><a href="<c:out value="${thisURL}"/>"> Spring MVC Annotation Controller [get]  </a>

<br><br>
<c:url var="thisURL" value="en/weather">
</c:url>
<li><a href="<c:out value="${thisURL}"/>"> Spring MVC Annotation Controller Whether Form [get/post] </a>
<br> &nbsp;&nbsp;&nbsp; Once get user's zip code and temperature type, another webservice call with CFX/SOAP is made to outside host weather service: <a href="http://www.webservicex.net/usweather.asmx?WSDL"> webservicex.net </a>

<!--
<br><br>
<c:url var="thisURL" value="en/resttest">
</c:url>
<li><a href="<c:out value="${thisURL}"/>"> Spring REST Webservice </a>
-->
 

<br><br>
<c:url var="thisURL" value="ws/">
</c:url>
<li><a href="<c:out value="${thisURL}"/>"> Local hosted Spring WS (CFX) Webservice </a>

