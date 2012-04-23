<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
  
<html>
 <head> 
 
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Show Active Tracks: ${env.customerProfile.groupId} </title>  
      
   
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>     
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>      
      
   <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />    
   <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
   
   <script language=javascript>
       var MULTIPLE_RETRIEVE = true;
       var ajaxUrl = '${env.prefix}/displaycurrentlocation?groupId=${env.customerProfile.groupId}&days=5';         	   
   </script>
 
   <script type="text/javascript" src="${env.contextPath}/jsp/tracking/trackingscript.jsp"></script>
   
   
</head>

<body>

<%@ include file="/jsp/common/i_header.jspf"%>

 
<c:set var="fromPage" value="ShowActiveTracks"/>
<%@ include file="i_menu.jspf"%>
 
 
<div id="container"> 
   <table border="1" cellspacing="2" cellpadding="2">
       <tr> 
          <td width="200" valign="top"> 
            <form name=xcv>
			
			<img id="autoRefersh" src="${env.contextPath}/images/start_auto_refresh.png" />	         
	        <input id="showTraffic" type="button" value="Show Traffic">
			<input id="clearDebugPanel" type="button" value="Clear Log" style="display: none">
	         
            </form>			
            <div id="xtimer"> </div><hr>
            <div id="mylocs">locations </div><hr>
            <div id="side_bar" style="height: 450px; overflow:auto;"></div>
			 <div id="thelog"/>
			 <div id="loadingImg"> &nbsp;&nbsp;&nbsp; <img id="loadingImg" src="${env.contextPath}/images/loading.gif" style="display: none;"/></div>
          </td>
          <td valign="top" width="760" align="left">		  
		     <div id="container">  	   
			    <div id="locInfo" style="width:740px; overflow:auto; background-color:lightgrey;"/>	            
             </div> 			 
             <div id="container">  	
                							 
	            <div id="map_canvas" style="width:740px; height:350px"></div>
             </div> 
			 <div id="container">  	   			    
	            &nbsp;
             </div>
			 <div id="container">
			     <div style="width:740px;height:20px; overflow:auto; text-align: center; background-color:lightgrey;"/> <b>Messaging</b>  </div>                    
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
 