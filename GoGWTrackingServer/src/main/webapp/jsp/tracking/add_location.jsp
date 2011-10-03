<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> History Tracks </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
</head>
<body>
   Add location
   <form:form commandName="gLocation" action="${env.prefix}/addlocation" method="post">
   
   <table>
       <tr>
          <td> groupId </td>  <td> <form:input id="groupId" path="groupId" size="30" maxlength="30"/>  </td>       
       </tr>
       <tr>
           <td> displayname </td>  <td> <input type="input" name="displayName"> </td>       
       </tr>
       <tr>
          <td> latitude(int) </td>  <td> <form:input id="latitude" path="latitude" size="30" maxlength="30"/> </td>       
       </tr>
        <tr>
          <td> longitude(int) </td>  <td> <form:input id="longitude" path="longitude" size="30" maxlength="30"/>  </td>       
       </tr>
        <tr>
          <td> altitude(double) </td>  <td> <form:input id="altitude" path="altitude" size="30" maxlength="30"/>   </td>       
       </tr>
        <tr>
          <td> provider </td>  <td> <form:input id="provider" path="provider" size="30" maxlength="30"/> </td>       
       </tr>
       <tr>
          <td> accuracy </td>  <td> <form:input id="accuracy" path="accuracy" size="30" maxlength="30"/> </td>       
       </tr>
       <tr>
          <td> bearing </td>  <td> <form:input id="bearing" path="bearing" size="30" maxlength="30"/>   </td>       
       </tr>
       <tr>
          <td> distance </td>  <td> <form:input id="distance" path="distance" size="30" maxlength="30"/> </td>       
       </tr>
       <tr>
          <td> speed </td>  <td> <form:input id="speed" path="speed" size="30" maxlength="30"/>   </td>       
       </tr>
       <tr>
          <td> time </td>  <td> <form:input id="time" path="time" size="30" maxlength="30"/>   </td>       
       </tr>
       <tr>
          <td> startTime </td>  <td> <form:input id="startTime" path="startTime" size="30" maxlength="30"/> </td>       
       </tr>
       <tr>
          <td> totalDistance </td>  <td> <form:input id="totalDistance" path="totalDistance" size="30" maxlength="30"/> </td>       
       </tr>
        
       <tr>
           <td> &nbsp; </td> <td> <input type="submit" value="Submit" /> </td>
                 
       </tr>
   
  </table>
  </form:form>
</body>
</html>