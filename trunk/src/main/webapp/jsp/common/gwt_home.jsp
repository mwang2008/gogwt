<%--
  home.jsp
  Extract from GWT: com.ihg.dec.apps.hi.gwt.common.HomeEntry
--%>
<%@ include file="i_global.jspf"%>
<html>
<head>
  <meta name="gwt:property" content="locale=${env.languageId}_${env.countryId}" />

  <title>Wrapper HTML for HomeEntry</title>

  <!--                                           -->
  <!-- Use normal html, such as style            -->
  <!--                                           -->
  <style>
    body,td,a,div,.p{font-family:arial,sans-serif}
    div,td{color:#000000}
    a:link,.w,.w a:link{color:#0000cc}
    a:visited{color:#551a8b}
    a:active{color:#ff0000}
  </style>	 
</head>


<body>
   <!-- OPTIONAL: include this if you want history support -->
   <iframe src="${env.contextPath}/jsp/empty_iframe.html" id="__gwt_historyFrame" style="width: 0; height: 0; border: 0"></iframe>
		
   <script type="text/javascript" language="javascript" src="${env.contextPath}/org.gwt.tutorial.gwt.homepage.HomeEntry/org.gwt.tutorial.gwt.homepage.HomeEntry.nocache.js"></script>
   		
   	<!-- 
      <iframe src="http://wangm1-wxp.www.holidayinn.com/web-spring/jsp/empty_iframe.html" id="__gwt_historyFrame" style="width: 0; height: 0; border: 0"></iframe>
		
   <script type="text/javascript" language="javascript" src="http://wangm1-wxp.www.holidayinn.com/web-spring/org.gwt.tutorial.gwt.homepage.HomeEntry/org.gwt.tutorial.gwt.homepage.HomeEntry.nocache.js"></script>
    -->
             
   <hr>
     <fmt:message key='label.welcome'/> 
   <hr>
   <div id="wrapperContent" style="position: relative"></div>
   <div id="gwt_section" style="position: relative"></div>
</body>
</html>
