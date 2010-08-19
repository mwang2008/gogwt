<%
   /*
    * meta tab with name of gwt:property is required to change GWT language 
    * Here is the sample code to display ES and EN. 
    * The value is from resource bundle.
    */
   String lang = request.getParameter("lang");
   String gwtLocale = "en_us";
   if (lang != null && lang.equalsIgnoreCase("es")) {
      gwtLocale = "es_es";
   }

%>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta name="gwt:property" content="locale=<%=gwtLocale%>" />
    <title>MyFirstGWT</title>
    <script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/com.skeleton.example.gwt.MyFirstGWTModule/com.skeleton.example.gwt.MyFirstGWTModule.nocache.js"></script>
  </head>

  <body>
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
     
     <p>
    <a href="<%=request.getContextPath()%>"> Back to Home </a>
  </body>
</html>
