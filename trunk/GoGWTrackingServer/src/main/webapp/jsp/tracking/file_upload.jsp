<%@ include file="/jsp/common/i_global.jspf"%>

<html lang="${env.languageId}">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> Import Page </title>       
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="${env.contextPath}/css/booking.css"/>
   <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
   <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
   
    <style type="text/css">
       div.fileinputs {
	      position: relative;
       }

       div.fakefile {
	      position: absolute;
	      top: 0px;
	      left: 0px;
	      z-index: 1;
       }

       input.file {
	      position: relative;
	      text-align: right;
	      -moz-opacity:0 ;
	      filter:alpha(opacity: 0);
	      opacity: 0;
	      z-index: 2;
        }
    </style>
  
</head>
<body>

<%@ include file="/jsp/tracking/i_header.jspf"%>
<c:set var="importtrack" value="selected"/>
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
                <div class="fileinputs">
                    File:  <input type="file" name="fileData"/> 
					<%--
					<form:input path="fileData" type="file"/>
					--%>
                    </p>
 
                    <p>
                      <input type="submit" value="Select File" />
                    </p>
				</div>
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