<html>
<body>
<h2>GoGWT Official Site</h2>
<hr>
<p>
<h3>
This is demo site for using Spring IOC and Spring MVC, Hibernate, GWT, in memory cache of Ehcache.  Three versions are given list below:
</h3>
<p>

<table border="1">
  <tr>
    <td width="45%">
	<table>
<tr> 
  <td>
     <a href="<%=request.getContextPath()%>/en-us/hotelsearch"> Spring MVC Reservation</a> <br>
     &nbsp;&nbsp;&nbsp; Purly Spring MVC, Spring IOC with Hibernate.
     <br>
  </td>
</tr>
<tr> 
  <td>
     &nbsp;&nbsp;&nbsp; 
     <br>
  </td>
</tr>
<tr> 
  <td>
     <a href="<%=request.getContextPath()%>/en-us/jhotelsearch"> Spring MVC Reservation + JQuery</a> <br>
     &nbsp;&nbsp;&nbsp; Purly Spring MVC, Spring IOC with Hibernate Plus JQuery.
     <br>
  </td>
</tr>

<tr> 
  <td>
     &nbsp;&nbsp;&nbsp; 
     <br>
  </td>
</tr>
<tr>
  <td>
     <a href="<%=request.getContextPath()%>/en-us/gwtreservation"> GWT Widgets Reservation </a> <br>
     &nbsp;&nbsp;&nbsp; GWT widgets, Spring MVC, Spring IOC with Hibernate.
     <br>
  </td>
</tr>   
<tr> 
  <td>
     &nbsp;&nbsp;&nbsp; 
     <br>
  </td>
</tr>

<tr>
  <td>
     <a href="<%=request.getContextPath()%>/en-us/mvpreservation"> GWT uiBinder MVP Reservation </a> <br>
     &nbsp;&nbsp;&nbsp; GWT uiBinder, GWT MVP pattern, Spring MVC, Spring IOC with Hibernate.
     <br>
  </td>
</tr>
<tr> 
  <td>
     &nbsp;&nbsp;&nbsp; 
     <br>
  </td>
</tr>

<tr>
  <td>
     <a href="<%=request.getContextPath()%>/en-us/admin/metrics"> Admin console </a> <br>
     &nbsp;&nbsp;&nbsp; View performance data to display RPC and Business Service number of calls and time used.
  </td>
</tr>

</table>   

	</td>
	<td>
	   <table> 
	     <tr>
		    <td>
			 <b>Features:</b>
			<ul>
			   <li>	<b>GWT latest version:</b> 2.4.0
               <li>	<b>Multi-modules:</b> total having 3 modules
               <li> <b>Multi-language (internationalization):</b> English and Spanish
               <li>	<b>RPC:</b> Integrated with Spring SL.
               <li>	<b>Google Map:</b> display result with Google map.
               <li> <b>Suggestion Box:</b> display the locations for the input.
               <li> <b>Code splitting:</b> performance enhance for display home page quickly
               <li>	<b>MVP:</b> apply GWT formal MVP pattern.  
               <li>	<b>Deferred Binding:</b> generate page flow.
               <li>	<b>GWT Linker:</b> 
               <li>	<b>GWT Spring SL:</b> Used for integrating GWT RPC call with Spring MVC framework. 
               <li>	<b>SEO Consideration:</b> set page title, page description, page meta data
               <li>	<b>Performance Consideration:</b>  inline nocache.js, code splitting, Load Google Map on demand with Google AjaxLoader 
               <li>	<b>Client Session backup:</b> to support the action such as page refresh. For example, in search result page, when user clicks refresh button in browser, the search result data will be gone, so user will not see the search result anymore. In this app, a session backup RPC call will get search result data from server session and re-display data to the user.
			   
               <li> <b>Tech term</b> Spring MVC, Spring IOC, REST, AOP, Multi-thread, JSON, Hibernate, MySql, JQuery etc

          </ul>
		    Click <a href="http://gogwt.googlecode.com/svn/trunk/gwtbooking_gwt2_4_0/gwtdemo.docx"> here </a> for more details.
			</td>
		 </tr>
	   </table>
	</td>
  </tr>
</table>

<h4>Please note: it is under development. </h4> 
The code is under Google SVN: <br>
can be extracted with: <i> svn checkout http://gogwt.googlecode.com/svn/trunk/gwtbooking_gwt2_4_0 </i>or <br>
can be viewed with:    <a href="http://gogwt.googlecode.com/svn/trunk/gwtbooking_gwt2_4_0/"> click here</a>

</body>
</html>
