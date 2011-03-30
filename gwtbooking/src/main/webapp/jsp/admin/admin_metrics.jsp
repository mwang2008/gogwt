<%--
  admin_metrics.jsp - displays metrics information related to the usage of the web application 
--%>
<%--  Always include the global include file from apps-common --%>
<%@ include file="/jsp/common/i_global.jspf"%>
<html>
<head>
<title>gogwt - Performance Metrics</title>
<meta name="robots" content="noindex">
</head>
<body>
<h1>Performance Metrics</h1>
<h3>Remote Procedure Method Calls</h3>
<table  align="center" border="1">
   <tr>
      <th>Method Name</th>
      <th>Call Count</th>
      <th>Exception Count</th>
      <th>Avg Latency (ms)</th>
      <th>Min Latency (ms)</th>
      <th>Max Latency (ms)</th>
   </tr>
   <c:forEach var="metricsEntry"
      items="${performanceMetricsInfoMap}">
      <c:if test="${!fn:containsIgnoreCase(metricsEntry.key.className, 'modelbean')}">
         <tr>
            <td>${metricsEntry.key.className}.${metricsEntry.key.methodName}</td>
            <td>${metricsEntry.value.callCount == 0 ? '-' : metricsEntry.value.callCount}</td>
            <td>${metricsEntry.value.exceptionCount == 0 ? '' : metricsEntry.value.exceptionCount}</td>
            <td>
              <c:set var="averageCount">
                <fmt:parseNumber value="${metricsEntry.value.callLatency / metricsEntry.value.callCount}" integerOnly="true" />
              </c:set>
              ${averageCount == 0 ? '-' : averageCount}
            </td>
            <td>${metricsEntry.value.minCallLatency == 1000000 ? '-' : metricsEntry.value.minCallLatency}</td>
            <td>${metricsEntry.value.maxCallLatency == 0 ? '-' : metricsEntry.value.maxCallLatency}</td>
         </tr>
      </c:if>
   </c:forEach>
</table>
<h3>Model Bean Method Calls</h3>
<table  align="center" border="1">
  <tr>
    <th>Method Name</th>
    <th>Call Count</th>
    <th>Exception Count</th>
    <th>Avg Latency (ms)</th>
    <th>Min Latency (ms)</th>
    <th>Max Latency (ms)</th>
  </tr>
  <c:forEach var="metricsEntry"
    items="${performanceMetricsInfoMap}">
    <c:if test="${fn:containsIgnoreCase(metricsEntry.key.className, 'modelbean')}">
      <tr>
        <td>${metricsEntry.key.className}.${metricsEntry.key.methodName}</td>
        <td>${metricsEntry.value.callCount == 0 ? '-' : metricsEntry.value.callCount}</td>
        <td>${metricsEntry.value.exceptionCount == 0 ? '' : metricsEntry.value.exceptionCount}</td>
        <td>
          <c:set var="averageCount">
            <fmt:parseNumber value="${metricsEntry.value.callLatency / metricsEntry.value.callCount}" integerOnly="true" />
          </c:set>
          ${averageCount == 0 ? '-' : averageCount}        
        </td>
        <td>${metricsEntry.value.minCallLatency == 0 ? '-' : metricsEntry.value.minCallLatency}</td>
        <td>${metricsEntry.value.maxCallLatency == 0 ? '-' : metricsEntry.value.maxCallLatency}</td>
      </tr>
    </c:if>
  </c:forEach>
</table>
  <br /><br />
  <div><b>'-' indicates a zero value</b></div>
</body>
</html>