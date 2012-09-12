<%--
	exception.jsp
	Description: general error page.
--%>
<%@ include file="/jsp/common/i_global.jspf"%> 

<c:if test="${empty env.prefix}">
  <c:redirect url="/gwtbooking/en-us/errorPage"/>
</c:if>

<c:redirect url="${env.prefix}/errorPage"/>