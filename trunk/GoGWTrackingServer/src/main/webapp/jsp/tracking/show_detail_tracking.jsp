<%--  
   show_detail_tracking.jsp 
   http://localhost/tracking/en-us/trackdetail?groupId=g5&displayName=show+5&startTime=1
   http://localhost/tracking/en-us/displaytrackdetail?groupId=g5&displayName=show+5&startTime=1
--%>

<%@ include file="/jsp/common/i_global.jspf"%>

<c:url value="/${env.languageId}-${env.countryId}/displaytrackdetail" var="detailUrl">
   <c:choose>
      <c:when test="${not empty param.from}">
          <%-- from import function --%>	     
		  <c:param name="from" value="${param.from}"/>
          <c:param name="groupId" value=""/>
          <c:param name="displayName" value=""/>
 	      <c:param name="startTime" value=""/>		
	  </c:when>
	  <c:otherwise>
	      <%-- from the link of Show Old Tracks --%>	         		  
         <c:param name="groupId" value="${param.groupId}"/>
         <c:param name="displayName" value="${param.displayName}"/>
	     <c:param name="startTime" value="${param.startTime}"/>		
         <c:param name="from" value=""/>		 
      </c:otherwise>
   </c:choose>

</c:url>		

<html>
 <head> 
 
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Show Track Detail: ${param.groupId}   </title>  
   
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>     
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>   
   <script type="text/javascript" src="http://www.google.com/jsapi"></script>
   <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
   <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
   
   <script language=javascript>
       var MULTIPLE_RETRIEVE = false;
       var ajaxUrl = '${detailUrl}';  
       var startAddress = '${param.startAddress}';
       var endAddress = '${param.endAddress}';	   
	   
	   
   </script>
  
   <script type="text/javascript" src="${env.contextPath}/jsp/tracking/trackingscript_history.jsp"></script>
     
   <style type="text/css">
      .buttonSubmitHide {
         display: none;
      }
   </style> 

</head>

<body>
 
<%@ include file="/jsp/common/i_header.jspf"%>
 
<c:set var="fromPage" value="trackdetail"/>
<%@ include file="i_menu.jspf"%>
 
<div id="container"> 
   <table border="1">
       <tr> 
          <!-- left bar -->
		  <td width="200" valign="top">  
            <form name=xcv>
	            
	            <input id="showTraffic" type="button" value="Show Traffic">
			    <input id="clearDebugPanel" type="button" value="Clear Log" style="display: none">	         
            </form>			
             
            <div id="mylocs">locations </div><hr>
			<div id=""> 
				<c:url value="/${env.languageId}-${env.countryId}/export" var="exportUrl">
				    <c:param name="groupId" value="${env.customerProfile.groupId}"/>
				    <c:param name="displayName" value="${param.displayName}"/>
					<c:param name="startTime" value="${param.startTime}"/>
				</c:url>								  
				&nbsp;&nbsp;&nbsp; <a href="${exportUrl}" > Export Track </a>
				<br>
        		 <c:url value="/${env.languageId}-${env.countryId}/trackdelete" var="deleteUrl">
				     <c:param name="groupId" value="${env.customerProfile.groupId}"/>
				     <c:param name="displayName" value="${param.displayName}"/>
					 <c:param name="startTime" value="${param.startTime}"/>
				 </c:url>								  
				&nbsp;&nbsp;&nbsp; <a href="${deleteUrl}" > Delete Track </a> 
 			</div>
			<hr>
            <div id="side_bar" style="height: 450px; overflow:auto;"></div>
			<div id="thelog"/>
			<div id="loadingImg">&nbsp;&nbsp;&nbsp; <img id="loadingImg" src="${env.contextPath}/images/loading.gif" style="display: none;"/></div>
         </td>
		 
         <td valign="top" width="760" align="left">		  
		     <div id="container">  	   
			    <div id="locInfo" style="width:740px; overflow:auto; background-color:lightgrey;"/>	                          
             </div> 			 
             <div id="container">  	   			    
			    				
	            <div id="map_canvas" style="width:740px; height:350px"></div>
             </div> 
			  <div id="container">  	   
			    <table border="0" cellspacing="5" cellpadding="2">
  				 <tr>			         
				    <td valign="center">&nbsp;&nbsp;&nbsp; <div id="chartLocInfo" style="width:720px;height:20px; overflow:auto; background-color:lightgrey;"/> </td>                     
				 </tr>
         		</table>
                			
             </div> 
             <div id="container">
             	<table border="0" cellspacing="5" cellpadding="2">
                   <tr align="center">
				      <td align="left"> &nbsp;&nbsp;&nbsp;<img id="arrowLeft"  src="${env.contextPath}/images/arrow-left.png"> 
					  <img id="loadingImgLeft" src="${env.contextPath}/images/loading.gif" style="display: none;"/>
					  </td>
				      <td align="center"><div id="chart_div" style="width:600px; height:200px" onmouseout="clearMatchMousemarker()"></div> </td>
					  <td align="right"> <img id="arrowRight" src="${env.contextPath}/images/arrow-right.png"> 
					  <img id="loadingImgRight" src="${env.contextPath}/images/loading.gif" style="display: none;"/>
					  </td>
			    </table>
             </div>
			 <div id="container">
			     <div style="width:740px;height:20px; overflow:auto; text-align: center; background-color:lightgrey;"/> <b>Messaging</b> </div>                     
			 </div>
			 <div id="container">  
	           <div id="sms_div" style="width:740px;"></div>
             </div>
          </td>
		  
		   
       </tr>
	   
   </table>
</div>
      
        
<div id="container">
    <div id="footer" style="margin-top: 65px; position: relative""><%@ include file="/jsp/common/i_footer.jspf"%></div>
</div> 

  
<%@ include file="/jsp/common/i_analytics.jspf"%>
</body>
</html>