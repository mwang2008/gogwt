<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="en">
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">    
   <title> More Detail </title>  
   <script type="text/javascript" src="/gwtbooking/script/jquery.js"></script>     
   <link rel="stylesheet" type="text/css"media="print, screen, tty, tv, projection, handheld, braille, aural" href="/gwtbooking/css/booking.css"/> 
</head>
<body>

<div id="wrapper">
  <div id="container">
     <%-- header --%>
     <div id="header" style="position: relative;">
       <table class="header" cellpadding="0" cellspacing="0">
       <tbody>
        <tr>
           <td style="vertical-align: top;" align="left">
             <div class="logo">             
              <a title="gogwt logo" href="/gwtbooking" class="gwt-Anchor" tabindex="0">
                  <img src="/gwtbooking/images/logo.JPG" class="gwt-Image">
               </a>
            </div>
          </td>
          <td style="vertical-align: top;" align="left">
             <table class="headerText" cellpadding="0" cellspacing="0">
             <tbody>
             <tr>
               <td style="vertical-align: top;" align="left">
                  <div class="gwt-Label rightLabel"> GWT, Spring MVC, Hibernate, AOP, SEO, Web Service (CFX and REST), Performance Tuneup, MySQL </div>
               </td>
             </tr>
             </tbody>
             </table>
          </td>
          
        </tr>
        </tbody>
       </table>
     </div>
     <%-- menu --%>
     <div id="menu" style="position: relative;">
         <table class="menu_table" cellpadding="0" cellspacing="0">
            <tbody>
              <tr align="right">
                 <td style="vertical-align: top;" align="left">
                    <table class="menu_inner" cellpadding="0" cellspacing="0">
                       <tbody>
                       <tr>
                          <td style="vertical-align: top;" align="left">
                             <div class="gwt-Label bw_top_menu"><a href="/gwtbooking/en-us/hotelsearch" style="text-decoration:none"><span class="gwt-Label bw_top_menu"> Find Hotel </span></a></div>
                          </td>
                          <td style="vertical-align: top;" align="left">
                            
                          </td>
                          <td style="vertical-align: top;" align="left">
                           
                          </td>
                           
                       </tr>
                       </tbody>
                    </table>
                 </td>                
              </tr>
            </tbody>
         </table>
    </div> 
   <%-- content --%>
   <div id="wrapperContent" style="position: relative">
      </br>
      <table> 
	     <tr>
 		    <td>
                This small app demonstrates the usages of GWT, Spring, Hibernate and JQuery.  A flow of reservation of property is presented.  Search can be performed by full address, airport code or geocode (latitude and longitude).  The search result is displayed in the list as well as in Google map.

				The same flow is given by four different technologies:
				<ul>
				<li>Pure Spring App
                <li>JQuery + Spring App
                <li>GWT + Spring App
                <li>GWT MVP + Spring App.
			    </ul>
				
				<p>
				<h3> GWT features: </h3>
				<ul>
			     <li><b>GWT latest version:</b> 2.4.0
                 <li><b>Multi-modules:</b> total having 3 modules
                 <li><b>Multi-language (internationalization):</b> English and Spanish
                 <li><b>RPC:</b> Integrated with Spring SL.
				 <li><b>REST+JSON:</b> Integrated with Spring REST and JSON.
                 <li><b>Google Map:</b> display result with Google map.
                 <li><b>Suggestion Box:</b> display the locations for the input.
                 <li><b>Code splitting:</b> performance enhance for display home page quickly
                 <li><b>MVP:</b> apply GWT formal MVP pattern.  
                 <li><b>Deferred Binding:</b> generate page flow.
                 <li><b>GWT Linker:</b> 
                 <li><b>GWT Spring SL:</b> Used for integrating GWT RPC call with Spring MVC framework. 
                 <li><b>SEO Consideration:</b> set page title, page description, page meta data
                 <li><b>Performance Consideration:</b>  inline nocache.js, code splitting, Load Google Map on demand with Google AjaxLoader 
                 <li><b>Client Session backup:</b> to support the action such as page refresh. For example, in search result page, when user clicks refresh button in browser, the search result data will be gone, so user will not see the search result anymore. In this app, a session backup RPC call will get search result data from server session and re-display data to the user.
			     <li> <b>Tech term</b> Spring MVC, Spring IOC, REST, CFX Webservice, AOP, Multi-thread, JSON, Hibernate, MySql, JQuery etc
                 </ul>
				 
				 <h3>Spring and other features:</h3>
				 <ul>
				 <li>Spring MVC 
                 <li>Spring IOC
                 <li>REST
                 <li>AOP: 
                   1)injecting to reservation flow, and create pipeline functions with different thread such as for sending email, send sms etc.
                   2)collecting data for performance metrics
                 <li>Multi-thread: send email in different thread. 
                 <li>JSON:
                 <li>Hibernate: 
                 <li>MySql
				 <li>JQuery
                 </ul>
			</td>
         </tr>
		 
         <tr>
            <td>		 
				 <p>
				 <h3>Screen demo:</h3>
		    </td>
		</tr>
		<tr>
		    <td>
				 Home page: http://www.allhotelmotel.com/gwtbooking/
			</td>
		 </tr>
         <tr>
            <td>		 
				 <a href="/gwtbooking/images/detail_home_big.png">
				   <img src="/gwtbooking/images/detail_home_big.png" class="gwt-Image" width="800" heigth="500">
				 </a>
 			</td>
		 </tr>
		 
		 <tr>
		    <td>
				 <h3>Suggestion box, Page title:</h3>
			</td>
		 </tr>
		 <tr>
            <td>	
                <a href="/gwtbooking/images/detail_suggestion.png">			
				    <img src="/gwtbooking/images/detail_suggestion.png" class="gwt-Image" width="800" heigth="500" >
				</a>
			</td>
		 </tr>
		 
		
		 <tr>
		    <td>
				 <h3> REST+JSON and dialog:  display weather in Dialog near the hotel</h3>
			</td>
		 </tr>
		 <tr>
            <td>	
                <a href="/gwtbooking/images/rest_dialog.png">			
				    <img src="/gwtbooking/images/rest_dialog.png" class="gwt-Image" width="800" heigth="500" >
				</a>
			</td>
		 </tr>
		 
		 <tr>
		    <td>
				 <h3>Search Result: with Google Map:</h3>
			</td>
		 </tr>
		 <tr>
            <td>	
                <a href="/gwtbooking/images/detail_search_result.png">						
				    <img src="/gwtbooking/images/detail_search_result.png" class="gwt-Image" width="800" heigth="500">
				</a>
			</td>
		 </tr>
		 
         <tr>
		    <td>
				 <h3>Performance Metrics: http://www.allhotelmotel.com/gwtbooking/en-us/admin/metrics:</h3>
			</td>
		 </tr>
		 <tr>
            <td>	
                 <a href="/gwtbooking/images/detail_metrics.png">			
				     <img src="/gwtbooking/images/detail_metrics.png" class="gwt-Image" width="800" heigth="500">
				 </a>
			</td>
		 </tr>
		 
	  </table>
   </div>
   
 </div>
   
   
</div>
 
</body>
</html>
