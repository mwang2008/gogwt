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
   <table>
       <tr> 
	     <!--
          <td width="972" valign="top"> 
		      <c:choose>
			     <c:when test="${hasResult}">
				    <c:forEach var="track" items="${trackHistoryList}">
					    xx - ${track.groupId} | ${track.displayName} | ${track.latitude} | ${track.time} | ${track.startTime} <br>
					</c:forEach>
				 </c:when>
				 <c:otherwise>
				    No track available to view
				 </c:otherwise>
			  </c:choose>	 .toFixed(6)		  
          </td>
		  -->
          <td valign="top" width="960" align="left">
             <div id="container">  
	           <c:choose >
			     <c:when test="${hasResult}">
				    <table border="1" cellpadding="5">
				    <c:forEach var="trackGroup" items="${trackHistoryGroupList}">	    
                        <tr> 	
                        <td valign="top" width="960" align="left">						
					    <c:forEach var="track" items="${trackGroup}" varStatus="status"> 
						   <c:if test="${status.first}">						    
						      <b>Group Id:</b> ${track.groupId} <b>Display Name:</b> ${track.displayName}  
							  <c:url value="/${env.languageId}-${env.countryId}/trackdetail" var="detailUrl">
							      <c:param name="groupId" value="${env.customerProfile.groupId}"/>
								  <c:param name="displayName" value="${track.displayName}"/>
								  <c:param name="startTime" value="${track.startTime}"/>
							  </c:url>							  
							  
							  &nbsp;&nbsp;&nbsp; <a href="${detailUrl}" > View Track Detail </a>    
						      <c:url value="/${env.languageId}-${env.countryId}/trackdelete" var="deleteUrl">
							      <c:param name="groupId" value="${env.customerProfile.groupId}"/>
								  <c:param name="displayName" value="${track.displayName}"/>
								  <c:param name="startTime" value="${track.startTime}"/>
							  </c:url>								  
							  &nbsp;&nbsp;&nbsp; <a href="${deleteUrl}" > Delete Track </a> 
							  
							  <c:url value="/${env.languageId}-${env.countryId}/export" var="exportUrl">
							      <c:param name="groupId" value="${env.customerProfile.groupId}"/>
								  <c:param name="displayName" value="${track.displayName}"/>
								  <c:param name="startTime" value="${track.startTime}"/>
							  </c:url>								  
							  &nbsp;&nbsp;&nbsp; <a href="${exportUrl}" > Export Track </a> 
							  
							  <br>
							  <c:choose>  
							     <c:when test="${track.address != null}">
								      <b>Start Address:</b> ${track.address}
								 </c:when>
								 <c:otherwise>
								      <b>Start Location </b>(latitude,longitude) <b>:</b> (${track.latitude/1.0e6}, ${track.longitude/1.0e6})  
								 </c:otherwise>
							  </c:choose>
							  &nbsp;&nbsp; <b>Start Time:</b> 
 							  ${gogwtutil:formatTimestamp(track.time)}
				   </c:if>
                           
						   <c:if test="${status.last}">
						       <br>
						       <c:choose>  
							     <c:when test="${track.address != null}">
								      <b>End Address:</b> ${track.address}
								 </c:when>
								 <c:otherwise>
								      <b>End Location </b>(latitude,longitude)<b>:</b> (${track.latitude/1.0e6}, ${track.longitude/1.0e6})
								 </c:otherwise>
							  </c:choose>	
                              &nbsp;&nbsp; <b>End Time:</b> ${gogwtutil:formatTimestamp(track.time)}  							  
						   </c:if>
                        </c:forEach> 
                      </td>
                      </tr> 						
 					</c:forEach>
					
				 </c:when>
				 <c:otherwise>
				    No track available to view
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

