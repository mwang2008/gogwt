<%@ taglib uri="http://www.gogwt.com/tags/gwt" prefix="gogwt"%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
    <title>GWT DEMO</title>
    <meta name="fragment" content="!">

    <script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/com.gogwt.demo.gwt.navigation.NavigationModule/com.gogwt.demo.gwt.navigation.NavigationModule.nocache.js"></script>    
  </head>

  <body>
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
    
   
  <gogwt:permutation base="com.gogwt.demo.gwt.navigation.NavigationModule" >
     <gogwt:param name="locale" value="en_us" />
     <gogwt:param name="log_level" value="off" />
     
  </gogwt:permutation>
   
  <div id="container">
     <div id="header" style="position: relative"></div>
     <div id="wrapperContent" style="position: relative"></div>  
     <div id="footer" style="margin-top: 25px; position: relative"></div>
  </div>
         
  </body>
</html>