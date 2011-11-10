<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Error Page </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
</head>
<body>

<%@ include file="/jsp/tracking/i_header.jspf"%>
<%@ include file="i_menu.jspf"%>

<div id="container"> 
   <table border="0">
       <tr> 
          <td width="200" valign="top"> 
              ${session_error_msg}
          </td>
       </tr>
   </table>
</div>
<div id="container">
    <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/tracking/i_footer.jspf"%></div>
</div>    

 <%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>