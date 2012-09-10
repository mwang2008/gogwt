<%--
  http://localhost/gwtbooking/en-us/getsuggestion?searchKey=atl
  Spring REST template
 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Get Suggestion</title>
</head>
<body>
<h1>Get Suggestion: </h1>

<table style="border: 1px solid; width: 500px; text-align:center">
   <thead style="background:#fcf">
      <tr>
         <th>Keyword</th>
         <th>Latitude</th>
         <th>Longtitude</th>
         <th>Search Key</th>
         <th>Type</th>
      </tr>
   </thead>
   <tbody>
   <c:forEach items="${keywords}" var="keyword">         
      <tr>
         <td><c:out value="${keyword.keyword}" /></td>
         <td><c:out value="${keyword.lat}" /></td>
         <td><c:out value="${keyword.lng}" /></td>
         <td><c:out value="${keyword.searchkey}" /></td>
         <td><c:out value="${keyword.type}" /></td>
      </tr>
   </c:forEach>
   </tbody>
</table>

</body>