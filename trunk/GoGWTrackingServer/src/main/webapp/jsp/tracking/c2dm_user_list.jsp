<%--   
   http://localhost/tracking/en-us/c2dmuserlist
--%>

<%@ include file="/jsp/common/i_global.jspf"%>


<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> C2DM User List : ${env.customerProfile.groupId} </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>   
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  
</head>
<body>


<%@ include file="/jsp/common/i_header.jspf"%>
  <c:set var="fromPage" value="ShowHistoryTracks"/>
  <c:set var="c2dmuserlist" value="selected"/>
<%@ include file="i_menu.jspf"%>

<div id="container">
   <table width="972px">
       <tr> 	   
          <td valign="top" width="960" align="left">
             <div id="container">  
	           <c:choose >
			     <c:when test="${hasResult}">
                    <table border="0" cellpadding="5" width="100%">
					<form:form method="POST" commandName="c2dmSendMessageFormBean">		                
					    <div id="ErrorContainer">
	                      <div id="Error"><form:errors path="*" cssClass="text12red" /></div>
                        </div> 
				        <c:forEach var="track" items="${c2dmRegList}" varStatus="status">
						  <c:if test="${status.first}">	
						  <tr>
						      <td> <b>Group Id:</b> ${track.groupId} </td>
						  </tr>						   
						  </c:if>
						   <tr>   
						      <td valign="middle"> 		
                                 <table   cellspacing="0" cellpadding="1" border="0" width="100%">
                                   <tr> 
								     <td colspan="3"> <form:checkbox path="recipientList" value="${track.phone}" />  ${track.phone}   </td>
								   </tr>
								 </table>
							   </td>
							</tr>
						</c:forEach>
						
						<tr> 
						   <td>                       
							  <label for="message"><fmt:message key='label.Message'/></label>:
						   </td>
					    </tr>
						<tr> 
						   <td>
						      <form:textarea path="message" rows="5" cols="30" />
						   </td>
					    </tr>

						<tr> 
						   <td>
						      <input type="submit" value="Send Message"/>
						   </td>
					    </tr>
						
					</form:form>
					</table>
                 </c:when>
			     <c:otherwise>
				    No user is registered C2DM with your group 
				 </c:otherwise>
			  </c:choose>			 
		    </div>
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

