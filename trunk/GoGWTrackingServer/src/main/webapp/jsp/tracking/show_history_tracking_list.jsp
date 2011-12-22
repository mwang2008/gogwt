<%--   
   http://localhost/tracking/en-us/retrievetracks
--%>

<%@ include file="/jsp/common/i_global.jspf"%>


<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> History Tracks List : ${env.customerProfile.groupId} </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
   
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  
</head>
<body>


<%@ include file="/jsp/common/i_header.jspf"%>
  <c:set var="fromPage" value="ShowHistoryTracks"/>
<%@ include file="i_menu.jspf"%>

<div id="container"> 
   <table width="972px">
       <tr> 	   
          <td valign="top" width="960" align="left">
             <div id="container">  
	           <c:choose >
			     <c:when test="${hasResult}">
				    <table border="0" cellpadding="5" width="100%">
				        <c:forEach var="track" items="${trackHistoryGroupList}" varStatus="status">
						  <c:if test="${status.first}">	
						  <tr>
						      <td>  <b>Group Id:</b> ${track.start.groupId} </td>
						  </tr>
						  </c:if>
						  <tr>   
						      <td valign="middle"> 		
                                 <table  class="bw_result_area_border" cellspacing="0" cellpadding="2" border="0" width="100%">
                                   <tr class="bw_result_name_row"> <td colspan="2">								 
								 <b>Display Name:</b> ${track.start.displayName}  
								 <c:url value="/${env.languageId}-${env.countryId}/trackdetail" var="detailUrl">
							        <c:param name="groupId" value="${env.customerProfile.groupId}"/>
								    <c:param name="displayName" value="${track.start.displayName}"/>
								    <c:param name="startTime" value="${track.start.startTime}"/>
							     </c:url>							  
							  
							     &nbsp;&nbsp;&nbsp; <a href="${detailUrl}" > View Track Detail </a>    
						         <c:url value="/${env.languageId}-${env.countryId}/trackdelete" var="deleteUrl">
							        <c:param name="groupId" value="${env.customerProfile.groupId}"/>
								    <c:param name="displayName" value="${track.start.displayName}"/>
								    <c:param name="startTime" value="${track.start.startTime}"/>
							     </c:url>								  
							     &nbsp;&nbsp;&nbsp; <a href="${deleteUrl}" > Delete Track </a> 
							  
							     <c:url value="/${env.languageId}-${env.countryId}/export" var="exportUrl">
							        <c:param name="groupId" value="${env.customerProfile.groupId}"/>
								    <c:param name="displayName" value="${track.start.displayName}"/>
								    <c:param name="startTime" value="${track.start.startTime}"/>
							     </c:url>								  
							     &nbsp;&nbsp;&nbsp; <a href="${exportUrl}" > Export Track </a> 
							     </td>
								 </tr>
								 
								 <tr>
								      <td>
									     <c:choose>  
							                <c:when test="${track.start.address != null}">
								               <b>Start Address:</b> ${track.start.address}
								            </c:when>
								            <c:otherwise>
								               <b>Start Location </b>(latitude,longitude) <b>:</b> (${track.start.latitude/1.0e6}, ${track.start.longitude/1.0e6})  
								            </c:otherwise>
    							         </c:choose>
									   </td>
									   <td>
									      at ${gogwtutil:formatTimestamp(track.start.time)}
									   </td>
								   </tr>
							       <tr>
								       <td>
									      <c:choose>  
							                 <c:when test="${track.end.address != null}">
								                 <b>End Address:</b> ${track.end.address}
								             </c:when>
								             <c:otherwise>
								                 <b>End Location </b>(latitude,longitude) <b>:</b> (${track.end.latitude/1.0e6}, ${track.end.longitude/1.0e6})  
								             </c:otherwise>
							              </c:choose>
									   </td>
									   <td>
									      at ${gogwtutil:formatTimestamp(track.end.time)}
									   </td>
								   </tr>
							     </table> 
							    
								 
								 
							  </td>
                              <%--						  
                              <td>${track.key.displayName} ${trackMapEntry.key.time} - ${trackMapEntry.value.time} </td>
                              <td>${gogwtutil:formatTimestamp(trackMapEntry.key.time)} - ${gogwtutil:formatTimestamp(trackMapEntry.value.time)}</td>
                              <td>${trackMapEntry.key.class}</td>
							  --%>
                          </tr>
						</c:forEach>
					</table>
				 </c:when>
				 <c:otherwise>
				    No track available for viewing
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

