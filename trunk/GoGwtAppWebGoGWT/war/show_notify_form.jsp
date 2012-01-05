<%--
show_notify_form.jsp
--%>
<html>
<head>
    <title> Send Notification Email </title>
</head>

<body>
  <img src="/images/logo.jpg" atl="GoGWT Logo"><br>
  <form action="/notifyemail" method="post">
     <table border="1">
       <tr>
          <td> to </td> <td><input type="text" name="to"> </td>
       </tr>
       <tr>
          <td> subject </td> <td><input type="text" name="subject"> </td>
       </tr>
       <tr>
          <td> body </td> <td><input type="text" name="body"> </td>
       </tr>
       <tr>
          <td colspan="2"> <input type="submit" value="Send" /></td>
       </tr>
    </table>
  </form>
  </body>
 </html>