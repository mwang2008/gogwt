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
   </script>
  
   <script type="text/javascript" src="${env.contextPath}/jsp/tracking/trackingscript_history.jsp"></script>
   <%-- <script type="text/javascript"> <%@ include file="/jsp/tracking/trackingscript.jsp"%>  </script>  --%>
   
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
          <td width="200" valign="top"> 
            <form name=xcv>
	            <input id="autoRefersh" type="button"  value="Start Auto Refresh">
	            <input id="showTraffic" type="button" value="Show Traffic">
			    <input id="clearDebugPanel" type="button" value="Clear Log" style="display: none">	         
            </form>			
            <div id="xtimer"> starting auto refresh </div><hr>
            <div id="mylocs">locations </div><hr>
            <div id="side_bar" style="height: 450px; overflow:auto;"></div>
			 <div id="thelog"/>
          </td>
          <td valign="top" width="760" align="left">		  
		     <div id="container">  	   
			    <div id="locInfo" style="width:740px; overflow:auto; background-color:lightgrey;"/>	            
             </div> 			 
             <div id="container">  	   			    
	            <div id="map_canvas" style="width:740px; height:350px"></div>
             </div> 
             <div id="container">  
	           <div id="chart_div" style="width:740px; height:200px" onmouseout="clearMatchMousemarker()"></div>
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