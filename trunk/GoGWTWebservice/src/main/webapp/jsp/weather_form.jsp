<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title> Weather </title>
<style>
.error {
	color: #ff0000;
}
.errorblock{
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding:8px;
	margin:16px;
}

</style>
</head>
<body>
<h2>Spring MVC Annotation weather form </h2>

<form:form method="POST" commandName="weather">
    <form:errors path="*" cssClass="errorblock" element="div"/>

    <table>
      <tr>
        <td>Zip Code : </td>
        <td><form:input path="zip" /></td>
        <td><form:errors path="zip" cssClass="error" /></td>
      </tr>
      <tr>
        <td>Temperature Type : </td>
        <td><form:select path="temperatureType">
   	          <form:option value="NONE" label="--- Select ---"/>
	           <form:options items="${temperatureTypes}" />
            </form:select>
        </td>
        <td><form:errors path="temperatureType" cssClass="error" /></td>
      </tr>
      <tr>
         <td colspan="3"><input type="submit" value=" Get Weather "/></td>
      </tr>
    </table>
</form:form>

</body>
</html>
