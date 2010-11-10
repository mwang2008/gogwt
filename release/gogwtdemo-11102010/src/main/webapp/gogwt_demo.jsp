<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="http://www.gogwt.com/tags/gwt" prefix="gogwt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
    <title>GWT DEMO</title>
    <meta name="fragment" content="!">

   
  </head>

  <body>
  
 
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
    
   <c:choose>
    <c:when test="${nocacheSwitch eq 'case1'}">
       <b>  <c:out value="${nocacheSwitch}"/>: using javascirpt nocache.js </b><br>
       <script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/com.gogwt.demo.gwt.mvp.MVPUiBinderModule/com.gogwt.demo.gwt.mvp.MVPUiBinderModule.nocache.js"></script>    
    </c:when>    
    
    <c:when test="${nocacheSwitch eq 'case2'}">
       <b>  <c:out value="${nocacheSwitch}"/>: using server include nocache.js </b><br>
       <%-- 1. Use this base url for inlining nocache.js, 2. reset base to the actual URL --%>
       <base href="<%=request.getContextPath()%>/com.gogwt.demo.gwt.navigation.NavigationModule/" />
       <script type="text/javascript" language="javascript">
          <%@ include file="/com.gogwt.demo.gwt.navigation.NavigationModule/com.gogwt.demo.gwt.navigation.NavigationModule.nocache.js" %>
       </script>
       <base href="<%=request.getContextPath()%>/show" /> 
    </c:when>
    
    <c:when test="${nocacheSwitch eq 'case3'}">       
       <b>  <c:out value="${nocacheSwitch}"/>: using server side permutation selection </b><br>
       <gogwt:permutation module="com.gogwt.demo.gwt.navigation.NavigationModule" >
          <gogwt:param name="locale" value="en_us" />
          <gogwt:param name="log_level" value="off" />
       </gogwt:permutation>        
      
    </c:when>
    <c:otherwise>
       <script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/com.gogwt.demo.gwt.navigation.NavigationModule/com.gogwt.demo.gwt.navigation.NavigationModule.nocache.js"></script>    
    </c:otherwise>
  </c:choose>
  
   
  <div id="container">
     <div id="header" style="position: relative"></div>
     <div id="wrapperContent" style="position: relative"></div>  
     <div id="footer" style="margin-top: 25px; position: relative"></div>
  </div>
         
  </body>
</html>