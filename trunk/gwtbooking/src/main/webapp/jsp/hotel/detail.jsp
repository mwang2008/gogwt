<%--
  detail.jsp
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<html>
<head>
   <title> Hotel detail </title>
</head>

<body>
<c:choose> 
  <c:when test="${not empty detail }">
    <%@ include file="i_detail.jspf"%>  
  </c:when>
  <c:otherwise>
    No propertyId or invalidate propertyId
  </c:otherwise>
</c:choose>
    
  
</body>
</html> 
