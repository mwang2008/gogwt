<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:url var="thisURL" value="en/hello">
</c:url>
<li><a href="<c:out value="${thisURL}"/>"> Spring MVC Annotation </a>

<c:url var="thisURL" value="en/resttest">
</c:url>
<li><a href="<c:out value="${thisURL}"/>"> Spring REST Webservice </a>

<c:url var="thisURL" value="ws/">
</c:url>
<li><a href="<c:out value="${thisURL}"/>"> Spring WS (CFX) Webservice </a>

