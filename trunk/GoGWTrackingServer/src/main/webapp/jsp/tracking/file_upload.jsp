<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Import Page </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
</head>
<body>

<%@ include file="/jsp/tracking/i_header.jspf"%>
<%@ include file="i_menu.jspf"%>

<div id="container"> 
   <table border="0">
       <tr> 
          <td width="200" valign="top"> 
             <form:form commandName="uploadFormBean" method="post" action="${env.prefix}/importtrack" enctype="multipart/form-data">
            
                <legend>Upload Tracking cvs File</legend>
             <p>
			 <spring:hasBindErrors name="uploadFormBean">
                <div id="ErrorContainer">
	                <div id="Error"><form:errors path="*" cssClass="text12red" /></div>
                </div>  
             </spring:hasBindErrors>
                
                    File: <form:input path="fileData" type="file"/>
                </p>
 
                <p>
                    <input type="submit" value="Select File" />
                </p>
            </form:form>
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