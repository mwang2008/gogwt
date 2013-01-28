<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title> Weather </title>
</head>
<body>
<h2>Weather Result</h2>

<table border="1" width="70%"> 
  <tr> <td> City: </td> <td> ${weatherResult.city} </td></tr>
  <tr> <td> State: </td> <td> ${weatherResult.state} </td></tr>
  <tr> <td> Zip: </td> <td> ${weatherResult.zip} </td></tr>
  <tr> <td> Temperature: </td> <td> ${weatherResult.temperature} &nbsp; ${weatherResult.temperatureType}</td></tr>
  <tr> <td> RelativeHumidity: </td> <td> ${weatherResult.relativeHumidity} </td></tr>
  <tr> <td> Description: </td> <td> ${weatherResult.description} </td></tr>
  
</table>
</body>
</html>